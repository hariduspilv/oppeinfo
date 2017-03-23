package ee.hitsa.ois.service;import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.repository.SaisApplicationRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.SaisApplicationSearchCommand;
import ee.hitsa.ois.web.dto.SaisApplicationSearchDto;

@Transactional
@Service
public class SaisApplicationService {

    @Autowired
    private SaisApplicationRepository saisApplicationRepository;

    public Page<SaisApplicationSearchDto> search(HoisUserDetails user, SaisApplicationSearchCommand criteria,
            Pageable pageable) {
        return saisApplicationRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            //TODO checks
            filters.add(cb.equal(root.get("saisAdmission").get("curriculumVersion").get("curriculum").get("school").get("id"), user.getSchoolId()));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(SaisApplicationSearchDto::of);
    }

}
