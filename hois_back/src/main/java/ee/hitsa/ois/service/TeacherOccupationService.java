package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.TeacherOccupation;
import ee.hitsa.ois.repository.TeacherOccupationRepository;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;

@Transactional
@Service
public class TeacherOccupationService {

    @Autowired
    TeacherOccupationRepository teacherOccupationRepository;

    public Page<TeacherOccupation> findAll(Long schoolId, TeacherOccupationSearchCommand criteria, Pageable pageable) {
        return teacherOccupationRepository.findAll((root, query, cb) -> {
            List<Predicate> filter = new ArrayList<>();
            filter.add(cb.equal(root.get("school").get("id"), schoolId));
            propertyContains(() -> root.get("occupationEt"), cb, criteria.getOccupationEt(), filter::add);
            propertyContains(() -> root.get("occupationEn"), cb, criteria.getOccupationEn(), filter::add);
            if(Boolean.TRUE.equals(criteria.getIsValid())) {
                filter.add(cb.equal(root.get("isValid"), Boolean.TRUE));
            }
            return cb.and(filter.toArray(new Predicate[filter.size()]));
        }, pageable);
    }

    public TeacherOccupation save(TeacherOccupation teacherOccupation) {
        return teacherOccupationRepository.save(teacherOccupation);
    }

    public void delete(TeacherOccupation teacherOccupation) {
        teacherOccupationRepository.delete(teacherOccupation);
    }
}
