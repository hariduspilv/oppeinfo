package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import ee.hitsa.ois.repository.MessageTemplateRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.ControllerErrorHandler.ErrorInfo.Error;
import ee.hitsa.ois.web.commandobject.MessageTemplateForm;
import ee.hitsa.ois.web.commandobject.MessageTemplateSearchCommand;
import ee.hitsa.ois.web.dto.MessageTemplateDto;

@Transactional
@Service
public class MessageTemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;
    @Autowired
    private MessageTemplateRepository messageTemplateRepository;
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
        return messageTemplateRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if (schoolId != null) {
                filters.add(cb.equal(root.get("school").get("id"), schoolId));
            }
            LocalDate validFrom = criteria.getValidFrom();
            if(validFrom != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("validFrom"), validFrom));
            }
            LocalDate validThru = criteria.getValidThru();
            if(validThru != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("validThru"), validThru));
            }
            if(Boolean.TRUE.equals(criteria.getValid())) {
                LocalDate now = LocalDate.now();
                filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
                filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            }
            if(criteria.getType() != null && !criteria.getType().isEmpty()) {
                filters.add(root.get("type").get("code").in(criteria.getType()));
            }
            propertyContains(() -> root.get("headline"), cb, criteria.getHeadline(), filters::add);
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(MessageTemplateDto::of);
    }

    /**
     * Find valid template for given automatic message type.
     *
     * @param type
     * @param schoolId
     * @return
     */
    public MessageTemplate findValidTemplate(MessageType type, Long schoolId) {
        TypedQuery<MessageTemplate> q = em.createQuery(
                "select t from MessageTemplate t where t.school.id = ?1 and t.type.code = ?2 and (t.validFrom is null or t.validFrom <= ?3) and (t.validThru is null or t.validThru >= ?3)", MessageTemplate.class);
        q.setParameter(1, schoolId);
        q.setParameter(2, type.name());
        q.setParameter(3, LocalDate.now());
        List<MessageTemplate> templates = q.setMaxResults(2).getResultList();

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
        Set<String> set = StreamUtil.toMappedSet(mt -> EntityUtil.getCode(mt.getType()), messageTemplateRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            if(code != null) {
                filters.add(cb.notEqual(root.get("type").get("code"), code));
            }

            LocalDate now = LocalDate.now();
            filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
            filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }));
        return set;
    }

    private static void validateTemplateContent(MessageTemplate messageTemplate) {
        MessageType type = MessageType.valueOf(EntityUtil.getCode(messageTemplate.getType()));
        Object data = type.getDataBean() != null ? BeanUtils.instantiateClass(type.getDataBean()) : null;
        ExpressionParser spelParser = new SpelExpressionParser();
        try {
            StandardEvaluationContext ctx = new StandardEvaluationContext(data);
            ctx.setPropertyAccessors(Arrays.asList(new HoisReflectivePropertyAccessor()));
            spelParser.parseExpression(messageTemplate.getContent(), new TemplateParserContext()).getValue(ctx, String.class);
        } catch(@SuppressWarnings("unused") ExpressionException | IllegalStateException e) {
            throw new ValidationFailedException("content", "messageTemplate.invalidcontent");
        }
    }

    public static class HoisReflectivePropertyAccessor extends ReflectivePropertyAccessor {

        @Override
        protected String getPropertyMethodSuffix(String propertyName) {
            return super.getPropertyMethodSuffix(toCamelCase(propertyName));
        }

        // TODO utility function
        private static String toCamelCase(String value) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0, cnt = value.length(); i < cnt; i++) {
                char ch = value.charAt(i);
                if(ch == '_') {
                    if(++i < cnt) {
                        sb.append(Character.toUpperCase(value.charAt(i)));
                    }
                } else {
                    sb.append(Character.toLowerCase(ch));
                }
            }
            return sb.toString();
        }
    }
}
