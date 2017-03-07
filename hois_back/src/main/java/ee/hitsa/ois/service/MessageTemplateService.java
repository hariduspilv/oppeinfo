package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.MessageTemplate;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.MessageTemplateRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.MessageTemplateForm;
import ee.hitsa.ois.web.commandobject.MessageTemplateSearchCommand;
import ee.hitsa.ois.web.dto.MessageTemplateDto;

@Transactional
@Service
public class MessageTemplateService {
    
    @Autowired
    private MessageTemplateRepository messageTemplateRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    
    public MessageTemplate save(MessageTemplate messageTemplate, MessageTemplateForm form) {
        EntityUtil.bindToEntity(form, messageTemplate, classifierRepository);
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
            if(!CollectionUtils.isEmpty(criteria.getType())) {
                filters.add(root.get("type").get("code").in(criteria.getType()));
            }
            propertyContains(() -> root.get("headline"), cb, criteria.getHeadline(), filters::add);
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(MessageTemplateDto::of);
    }

    public Set<String> getUsedTypeCodes(Long schoolId, String code) {
        Set<String> set = messageTemplateRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            if(code != null) {
                filters.add(cb.notEqual(root.get("type").get("code"), code));
            }
            
            LocalDate now = LocalDate.now();
            filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
            filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().map(mt -> EntityUtil.getCode(mt.getType())).collect(Collectors.toSet());
        return set;
    }
}
