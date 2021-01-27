package ee.hitsa.ois.service;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.message.MessageTemplateUserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.MessageTemplate;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.Error;
import ee.hitsa.ois.web.commandobject.message.MessageTemplateForm;
import ee.hitsa.ois.web.commandobject.message.MessageTemplateSearchCommand;
import ee.hitsa.ois.web.dto.message.MessageTemplateDto;

import static ee.hitsa.ois.util.JpaQueryUtil.*;

@Transactional
@Service
public class MessageTemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;

    public MessageTemplate create(HoisUserDetails user, MessageTemplateForm form) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(messageTemplate, form);
    }

    public MessageTemplate save(MessageTemplate messageTemplate, MessageTemplateForm form) {
        EntityUtil.bindToEntity(form, messageTemplate, classifierRepository);
        validateTemplateContent(messageTemplate);
        return EntityUtil.save(messageTemplate, em);
    }

    public void delete(HoisUserDetails user, MessageTemplate messageTemplate) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(messageTemplate, em);
    }

    public Page<MessageTemplateDto> search(Long schoolId, MessageTemplateSearchCommand criteria, Pageable pageable) {
        JpaQueryBuilder<MessageTemplate> qb = new JpaQueryBuilder<>(MessageTemplate.class, "mt").sort(pageable);

        qb.requiredCriteria("mt.school.id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("mt.validFrom >= :validFrom", "validFrom", criteria.getValidFrom());
        qb.optionalCriteria("mt.validThru <= :validThru", "validThru", criteria.getValidThru());
        if(Boolean.TRUE.equals(criteria.getValid())) {
            qb.validNowCriteria("mt.validFrom", "mt.validThru");
        }
        qb.optionalCriteria("mt.type.code in (:types)", "types", criteria.getType());
        qb.optionalContains("mt.headline", "headline", criteria.getHeadline());

        return JpaQueryUtil.pagingResult(qb, em, pageable).map(MessageTemplateDto::of);
    }

    /**
     * Find valid template for given automatic message type.
     *
     * @param type
     * @param schoolId
     * @return
     */
    public MessageTemplate findValidTemplate(MessageType type, Long schoolId) {
        List<MessageTemplate> templates = em.createQuery(
                "select t from MessageTemplate t where t.school.id = ?1 and t.type.code = ?2 and (t.validFrom is null or t.validFrom <= ?3) and (t.validThru is null or t.validThru >= ?3)", MessageTemplate.class)
                .setParameter(1, schoolId)
                .setParameter(2, type.name())
                .setParameter(3, LocalDate.now())
                .setMaxResults(2).getResultList();

        if (templates.isEmpty()) {
            LOG.error("No {} templates found for school {}", type.name(), schoolId);
        } else if (templates.size() > 1) {
            LOG.error("Multiple {} templates found for school {}", type.name(), schoolId);
        }

        return templates.isEmpty() ? null : templates.get(0);
    }

    /**
     * Check for template existence. If template is missing, add error to error list
     *
     * @param type
     * @param school
     * @param allErrors
     */
    public void requireValidTemplate(MessageType type, School school, List<Error> allErrors) {
        if(findValidTemplate(type, EntityUtil.getId(school)) == null) {
            allErrors.add(new Error("main.messages.error.configuration.missingAutomaticMessageTemplate",
                    Collections.singletonMap("template", em.getReference(Classifier.class, type.name()).getNameEt())));
        }
    }

    public Set<String> getUsedTypeCodes(Long schoolId, String code) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from message_template mt");
        qb.requiredCriteria("mt.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("mt.type_code <> :typeCode", "typeCode", code);
        qb.validNowCriteria("mt.valid_from", "mt.valid_thru");
        List<?> data = qb.select("mt.type_code", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsString(r, 0), data);
    }

    private static void validateTemplateContent(MessageTemplate messageTemplate) {
        MessageType type = MessageType.valueOf(EntityUtil.getCode(messageTemplate.getType()));
        Object data = type.getDataBean() != null ? BeanUtils.instantiateClass(type.getDataBean()) : null;
        ExpressionParser spelParser = new SpelExpressionParser();

        try {
            StandardEvaluationContext ctx = new StandardEvaluationContext(data);
            ctx.setPropertyAccessors(Collections.singletonList(new HoisReflectivePropertyAccessor()));
            String contentWithoutFor = forPatterns(spelParser, ctx, data, messageTemplate.getContent());
            spelParser.parseExpression(contentWithoutFor, new TemplateParserContext()).getValue(ctx, String.class);
        } catch(@SuppressWarnings("unused") ExpressionException | IllegalStateException
                | InvocationTargetException | IllegalAccessException e) {
            throw new ValidationFailedException("content", "messageTemplate.invalidcontent");
        }
    }

    public static String forPatterns(ExpressionParser parser, StandardEvaluationContext ctx, Object data, String content)
            throws InvocationTargetException, IllegalAccessException {
        if (data == null) {
            return content;
        }
        Pattern forPattern = Pattern.compile(".*?(#for\\{([\\da-zA-Z_]+?)}\\n?(.*?)#forend).*?", Pattern.DOTALL);
        Matcher matcher = forPattern.matcher(content);

        String nContent = content;
        Map<String, Method> mappedMethods = new HashMap<>();
        for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(data.getClass())) {
            Method pReadMethod = pd.getReadMethod();
            String pName = pd.getName();
            if (pReadMethod != null && Modifier.isPublic(pReadMethod.getDeclaringClass().getModifiers())
                    && Collection.class.isAssignableFrom(pReadMethod.getReturnType())) {
                mappedMethods.put(pName, pReadMethod);
            }
        }

        while (matcher.find()) {
            String variable = PersonUtil.toCamelCase(matcher.group(2));
            String spelExp = matcher.group(3);
            if (!mappedMethods.containsKey(variable)) {
                continue;
            }
            Method method = mappedMethods.get(variable);
            Collection<?> retValue = (Collection<?>) method.invoke(data);
            Object orig = ctx.getRootObject() != null ? ctx.getRootObject().getValue() : null;
            StringBuilder sb = new StringBuilder();
            for (Object obj : retValue) {
                ctx.setRootObject(obj);
                sb.append(parser.parseExpression(spelExp, new TemplateParserContext()).getValue(ctx, String.class));
            }
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\n') {
                sb.deleteCharAt(sb.length() - 1);
            }
            ctx.setRootObject(orig);
            nContent = nContent.replace(matcher.group(1), sb.toString());
        }
        return nContent;
    }

    public Page<AutocompleteResult> getUsersWithGivenUserRightsInSchool(Long schoolId,
                                                                        List<PermissionObject> permissionObjects,
                                                                        Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from user_ u" +
                " join person p on u.person_id = p.id" +
                " join user_rights ur on u.id = ur.user_id");
        qb.groupBy(" p.id, p.lastname, p.firstname ");
        qb.sort(" p.lastname, p.firstname ");

        qb.requiredCriteria("u.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("u.role_code in :roleCodes", "roleCodes",
                EnumUtil.toNameList(Role.ROLL_A, Role.ROLL_J, Role.ROLL_O));
        qb.validNowCriteria("u.valid_from", "u.valid_thru");
        qb.requiredCriteria("ur.permission_code = :permissionCode",
                "permissionCode", Permission.OIGUS_T.name());
        qb.requiredCriteria("ur.object_code in :objectCodes",
                "objectCodes", permissionObjects.stream()
                        .map(Enum::name).collect(Collectors.toList()));
        return JpaQueryUtil.pagingResult(qb, "p.id, p.firstname || ' ' || p.lastname as fullname," +
                " p.idcode, string_agg(distinct ur.object_code, ',') as objectcodes", em, pageable)
                .map(r -> new MessageTemplateUserResult(resultAsLong(r, 0),
                        resultAsString(r, 1), resultAsString(r, 1),
                        resultAsString(r, 2), resultAsStringList(r, 3, ",")));
    }

    public String getTemplateExample(String templateType) {
        MessageType type = EnumUtil.valueOf(MessageType.class, templateType);
        if (type == null || type.getTemplate() == null) {
            return null;
        }
        ClassPathResource resource = new ClassPathResource("templates/messages/" + type.getTemplate());
        String result;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            result = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new HoisException(String.format("Exception during %s template reading", type.getTemplate()), e);
        }
        return result;
    }

    public static class HoisReflectivePropertyAccessor extends ReflectivePropertyAccessor {

        @Override
        protected String getPropertyMethodSuffix(String propertyName) {
            return super.getPropertyMethodSuffix(PersonUtil.toCamelCase(propertyName));
        }
    }
}
