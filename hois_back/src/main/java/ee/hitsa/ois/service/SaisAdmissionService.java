package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.SaisAdmissionSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SaisAdmissionSearchDto;

@Transactional
@Service
public class SaisAdmissionService {

    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;
    @Autowired
    private EntityManager em;

    public Page<SaisAdmissionSearchDto> search(HoisUserDetails user, SaisAdmissionSearchCommand criteria,
            Pageable pageable) {
        return saisAdmissionRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if (!StringUtils.isEmpty(criteria.getCode())) {
                filters.add(cb.equal(root.get("code"), criteria.getCode()));
            }

            if (criteria.getCurriculumVersion() != null) {
                filters.add(cb.equal(root.get("curriculumVersion").get("id"), criteria.getCurriculumVersion()));
            }

            if (!StringUtils.isEmpty(criteria.getStudyForm())) {
                filters.add(cb.equal(root.get("studyForm").get("code"), criteria.getStudyForm()));
            }

            if (!StringUtils.isEmpty(criteria.getFin())) {
                filters.add(cb.equal(root.get("fin").get("code"), criteria.getFin()));
            }

            filters.add(cb.equal(root.get("curriculumVersion").get("curriculum").get("school").get("id"), user.getSchoolId()));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SaisAdmissionSearchDto::of);
    }

    public List<AutocompleteResult> curriculumVersionsinUsedInSaisAdmissions(HoisUserDetails user) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("FROM sais_admission sa "
                + "INNER JOIN curriculum_version cv ON sa.curriculum_version_id = cv.id "
                + "INNER JOIN curriculum c ON cv.curriculum_id = c.id");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        List<?> data = qb.select("DISTINCT cv.id, cv.code, c.name_et, c.name_en", em).getResultList();
        return data.stream().map(r -> {
            String code = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), CurriculumUtil.versionName(code, resultAsString(r, 2)), CurriculumUtil.versionName(code, resultAsString(r, 3)));
        }).collect(Collectors.toList());
    }

}
