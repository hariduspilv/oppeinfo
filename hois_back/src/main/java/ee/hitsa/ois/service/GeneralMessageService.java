package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.GeneralMessage;
import ee.hitsa.ois.domain.GeneralMessageTarget;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.GeneralMessageForm;
import ee.hitsa.ois.web.commandobject.GeneralMessageSearchCommand;
import ee.hitsa.ois.web.dto.GeneralMessageDto;

@Transactional
@Service
public class GeneralMessageService {

    @Autowired
    private EntityManager em;

    public Page<GeneralMessageDto> show(HoisUserDetails user, Pageable pageable) {
        if(user.getSchoolId() == null) {
            // do now show general messages to users which have no school
            return new PageImpl<>(Collections.emptyList());
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from general_message g").sort(pageable);
        qb.requiredCriteria("g.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("(g.valid_from is null or g.valid_from <= :now) and (g.valid_thru is null or g.valid_thru >= :now)", "now", LocalDate.now());
        qb.requiredCriteria("g.id in (select gt.general_message_id from general_message_target gt where gt.role_code = :role)", "role", user.getRole());

        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, "g.id, g.title, g.content, g.inserted", em, pageable);
        return messages.map(d -> new GeneralMessageDto(resultAsLong(d, 0), resultAsString(d, 1), resultAsString(d, 2), resultAsLocalDateTime(d, 3)));
    }

    public Page<GeneralMessageDto> search(Long schoolId, GeneralMessageSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from general_message g").sort(pageable);

        qb.requiredCriteria("g.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("g.title", "title", criteria.getTitle());
        qb.optionalContains("g.content", "content", criteria.getContent());
        qb.optionalCriteria("(g.valid_thru is null or g.valid_thru >= :validFrom)", "validFrom", criteria.getValidFrom());
        qb.optionalCriteria("(g.valid_from is null or g.valid_from <= :validThru)", "validThru", criteria.getValidThru());
        qb.optionalCriteria("exists(select 1 from general_message_target gt where gt.role_code in (:targets) and gt.general_message_id = g.id)", "targets", criteria.getTargets());

        Page<GeneralMessageDto> messages = JpaQueryUtil.pagingResult(qb, "g.id, g.title, g.inserted, g.valid_from, g.valid_thru", em, pageable).map(r -> {
            GeneralMessageDto dto = new GeneralMessageDto(resultAsLong(r, 0), resultAsString(r, 1), null, resultAsLocalDateTime(r, 2));
            dto.setValidFrom(resultAsLocalDate(r, 3));
            dto.setValidThru(resultAsLocalDate(r, 4));
            return dto;
        });

        Set<Long> messageIds = StreamUtil.toMappedSet(GeneralMessageDto::getId, messages.getContent());
        if(!messageIds.isEmpty()) {
            // fetch targets
            Query q = em.createNativeQuery("select gt.general_message_id, gt.role_code from general_message_target gt where gt.general_message_id in (?1)");
            q.setParameter(1, messageIds);
            List<?> receivers = q.getResultList();
            Map<Long, List<String>> targets = receivers.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsString(r, 1), Collectors.toList())));
            for(GeneralMessageDto dto : messages.getContent()) {
                dto.setTargets(targets.get(dto.getId()));
            }
        }

        return messages;
    }

    public GeneralMessage create(HoisUserDetails user, GeneralMessageForm form) {
        GeneralMessage generalMessage = new GeneralMessage();
        generalMessage.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(generalMessage, form);
    }

    public GeneralMessage save(GeneralMessage generalMessage, GeneralMessageForm form) {
        EntityUtil.bindToEntity(form, generalMessage, "targets");
        List<GeneralMessageTarget> storedTargets = generalMessage.getTargets();
        if(storedTargets == null) {
            generalMessage.setTargets(storedTargets = new ArrayList<>());
        }
        EntityUtil.bindEntityCollection(storedTargets, gmt -> EntityUtil.getCode(gmt.getRole()),form.getTargets(), roleCode -> {
            // add new link
            GeneralMessageTarget sl = new GeneralMessageTarget();
            sl.setGeneralMessage(generalMessage);
            sl.setRole(EntityUtil.validateClassifier(em.getReference(Classifier.class, roleCode), MainClassCode.ROLL));
            return sl;
        });
        return EntityUtil.save(generalMessage, em);
    }

    public void delete(HoisUserDetails user, GeneralMessage generalMessage) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(generalMessage, em);
    }
}
