package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.GeneralMessage;
import ee.hitsa.ois.domain.GeneralMessageTarget;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.GeneralMessageRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.GeneralMessageSearchCommand;
import ee.hitsa.ois.web.dto.GeneralMessageDto;

@Transactional
@Service
public class GeneralMessageService {
    private static final String SHOW_QUERY_COMMON = "from general_message g where g.school_id=? "+
        "and (g.valid_from is null or g.valid_from <= now()) and (g.valid_thru is null or g.valid_thru >= cast(now() as date)) "+
        "and g.id in (select gt.general_message_id from general_message_target gt where gt.role_code in (select u.role_code from user_ u where u.id=?))";
    private static final String SHOW_QUERY = String.format("select g.id, g.title, g.inserted %s order by g.inserted, g.title", SHOW_QUERY_COMMON);
    private static final String SHOW_COUNT_QUERY = "select count(*) " + SHOW_QUERY_COMMON;

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private GeneralMessageRepository generalMessageRepository;

    public Page<GeneralMessageDto> show(HoisUserDetails user, Pageable pageable) {
        Query q = em.createNativeQuery(SHOW_QUERY);
        q.setParameter(1, user.getSchoolId());
        q.setParameter(2, user.getUserId());
        Page<Object[]> messages = JpaQueryUtil.pagingResult(q, pageable, () -> {
            Query cq = em.createNativeQuery(SHOW_COUNT_QUERY);
            cq.setParameter(1, user.getSchoolId());
            cq.setParameter(2, user.getUserId());
            return (Number)cq.getSingleResult();
        });
        return messages.map(d -> new GeneralMessageDto(Long.valueOf(((Number)d[0]).longValue()), (String)d[1], LocalDateTime.ofInstant(((java.sql.Timestamp)d[2]).toInstant(), ZoneId.systemDefault())));
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
                filters.add(cb.greaterThanOrEqualTo(root.get("validFrom"), validFrom));
            }
            LocalDate validThru = criteria.getValidThru();
            if(validThru != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("validThru"), validThru));
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

    public GeneralMessage save(GeneralMessage generalMessage, List<String> targetCodes) {
        if(targetCodes != null) {
            List<GeneralMessageTarget> storedTargets = generalMessage.getTargets();
            if(storedTargets == null) {
                generalMessage.setTargets(storedTargets = new ArrayList<>());
            }
            EntityUtil.bindClassifierCollection(storedTargets, gmt -> EntityUtil.getCode(gmt.getRole()),targetCodes, roleCode -> {
                // add new link
                Classifier c = classifierRepository.getOne(roleCode);
                // verify that domain code is from ROLL and raise IllegalArgumentException if wrong
                if(!MainClassCode.ROLL.name().equals(c.getMainClassCode())) {
                    throw new IllegalArgumentException("Wrong classifier code: "+c.getMainClassCode());
                }
                GeneralMessageTarget sl = new GeneralMessageTarget();
                sl.setGeneralMessage(generalMessage);
                sl.setRole(c);
                return sl;
            });
        }
        return generalMessageRepository.save(generalMessage);
    }

    public void delete(GeneralMessage generalMessage) {
        generalMessageRepository.delete(generalMessage);
    }
}
