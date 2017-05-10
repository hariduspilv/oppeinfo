package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.repository.SaisAdmissionRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.sais.SaisAdmissionSearchCommand;
import ee.hitsa.ois.web.dto.sais.SaisAdmissionSearchDto;

@Transactional
@Service
public class SaisAdmissionService {

    @Autowired
    private SaisAdmissionRepository saisAdmissionRepository;

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

    public void delete(SaisAdmission saisAdmission) {
        EntityUtil.deleteEntity(saisAdmissionRepository, saisAdmission);
    }
}
