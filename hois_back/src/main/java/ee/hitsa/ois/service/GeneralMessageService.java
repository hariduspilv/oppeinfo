package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
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
import ee.hitsa.ois.repository.GeneralMessageRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.GeneralMessageForm;
import ee.hitsa.ois.web.commandobject.GeneralMessageSearchCommand;
import ee.hitsa.ois.web.dto.GeneralMessageDto;

@Transactional
@Service
public class GeneralMessageService {
    private static final String SHOW_MESSAGES_FROM = "from general_message g where g.school_id=:schoolId "+
        "and (g.valid_from is null or g.valid_from <= now()) and (g.valid_thru is null or g.valid_thru >= cast(now() as date)) "+
        "and g.id in (select gt.general_message_id from general_message_target gt where gt.role_code in (select u.role_code from user_ u where u.id=:userId))";

    @Autowired
    private EntityManager em;
    @Autowired
    private GeneralMessageRepository generalMessageRepository;

    public Page<GeneralMessageDto> show(HoisUserDetails user, Pageable pageable) {
        if(user.getSchoolId() == null) {
            // do now show general messages to users which have no school
            return new PageImpl<>(Collections.emptyList());
        }

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(SHOW_MESSAGES_FROM).sort(pageable);
        qb.parameter("schoolId", user.getSchoolId());
        qb.parameter("userId", user.getUserId());
        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, "g.id, g.title, g.content, g.inserted", em, pageable);
        return messages.map(d -> new GeneralMessageDto(resultAsLong(d, 0), resultAsString(d, 1), resultAsString(d, 2), resultAsLocalDateTime(d, 3)));
    }

    public Page<GeneralMessageDto> search(Long schoolId, GeneralMessageSearchCommand criteria, Pageable pageable) {
        return generalMessageRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            propertyContains(() -> root.get("title"), cb, criteria.getTitle(), filters::add);
            propertyContains(() -> root.get("content"), cb, criteria.getContent(), filters::add);
            // display periods should overlap
            LocalDate validFrom = criteria.getValidFrom();
            if(validFrom != null) {
                filters.add(cb.or(cb.isNull(root.get("validThru")), cb.greaterThanOrEqualTo(root.get("validThru"), validFrom)));
            }
            LocalDate validThru = criteria.getValidThru();
            if(validThru != null) {
                filters.add(cb.or(cb.isNull(root.get("validFrom")), cb.lessThanOrEqualTo(root.get("validFrom"), validThru)));
            }
            List<String> targets = criteria.getTargets();
            if(targets != null && !targets.isEmpty()) {
                Subquery<Long> targetQuery = query.subquery(Long.class);
                Root<GeneralMessageTarget> targetRoot = targetQuery.from(GeneralMessageTarget.class);
                targetQuery = targetQuery.select(targetRoot.get("generalMessage").get("id")).where(targetRoot.get("role").get("code").in(targets));
                filters.add(root.get("id").in(targetQuery));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(GeneralMessageDto::of);
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
        return generalMessageRepository.save(generalMessage);
    }

    public void delete(GeneralMessage generalMessage) {
        generalMessageRepository.delete(generalMessage);
    }
}
