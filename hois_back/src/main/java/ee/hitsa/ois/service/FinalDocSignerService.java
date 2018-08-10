package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.FinalDocSigner;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.FinalDocSignerForm;
import ee.hitsa.ois.web.dto.FinalDocSignerDto;

@Transactional
@Service
public class FinalDocSignerService {
    
    @Autowired
    private EntityManager em;

    public Page<FinalDocSignerDto> search(Long schoolId, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from final_doc_signer fds").sort(pageable);
        qb.requiredCriteria("fds.school_id = :schoolId", "schoolId", schoolId);

        return JpaQueryUtil.pagingResult(qb, "fds.id, fds.name, fds.position, fds.position_en, fds.is_first, fds.is_valid", 
                em, pageable).map(r -> {
            FinalDocSignerDto dto = new FinalDocSignerDto();
            dto.setId(resultAsLong(r, 0));
            dto.setName(resultAsString(r, 1));
            dto.setPosition(resultAsString(r, 2));
            dto.setPositionEn(resultAsString(r, 3));
            dto.setIsFirst(resultAsBoolean(r, 4));
            dto.setIsValid(resultAsBoolean(r, 5));
            return dto;
        });
    }

    public FinalDocSigner create(HoisUserDetails user, FinalDocSignerForm form) {
        FinalDocSigner signer = new FinalDocSigner();
        signer.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(signer, form);
    }

    public FinalDocSigner save(FinalDocSigner signer, FinalDocSignerForm form) {
        signer.setName(form.getName());
        signer.setPosition(form.getPosition());
        signer.setPositionEn(form.getPositionEn());
        signer.setIsFirst(form.getIsFirst());
        signer.setIsValid(form.getIsValid());
        return EntityUtil.save(signer, em);
    }

    public void delete(HoisUserDetails user, FinalDocSigner signer) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(signer, em);
    }
}
