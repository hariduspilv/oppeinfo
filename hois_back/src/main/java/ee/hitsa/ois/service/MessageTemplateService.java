package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.MessageTemplate;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.MessageTemplateRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.MessageTemplateForm;
import ee.hitsa.ois.web.commandobject.MessageTemplateSearchCommand;
import ee.hitsa.ois.web.dto.MessageTemplateDto;

@Transactional
@Service
public class MessageTemplateService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private MessageTemplateRepository messageTemplateRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    public MessageTemplate create(HoisUserDetails user, MessageTemplateForm form) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return save(messageTemplate, form);
    }

    public MessageTemplate save(MessageTemplate messageTemplate, MessageTemplateForm form) {
        EntityUtil.bindToEntity(form, messageTemplate, classifierRepository);
        validateTemplateContent(messageTemplate);
        return messageTemplateRepository.save(messageTemplate);
    }

    public void delete(MessageTemplate messageTemplate) {
        EntityUtil.deleteEntity(messageTemplateRepository, messageTemplate);
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

    public MessageTemplate findValidTemplate(MessageType type, Long schoolId) {
        List<MessageTemplate> templates = messageTemplateRepository.findAll((root, query, cb) -> {
            LocalDate now = LocalDate.now();
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
            filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            filters.add(cb.equal(root.get("type").get("code"), type.name()));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });

        if (templates.isEmpty()) {
            log.error("no {} templates found for school {}", type.name(), schoolId);
        } else if (templates.size() > 1) {
            log.error("Multiple {} templates found for school {}", type.name(), schoolId);
        }

        return templates.isEmpty() ? null : templates.get(0);
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
            spelParser.parseExpression(messageTemplate.getContent(), new TemplateParserContext()).getValue(data, String.class);
        } catch(@SuppressWarnings("unused") EvaluationException e) {
            throw new ValidationFailedException("content", "messageTemplate.invalidcontent");
        }
    }
}
