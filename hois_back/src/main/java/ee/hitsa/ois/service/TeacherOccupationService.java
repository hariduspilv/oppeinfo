package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.repository.TeacherOccupationRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.TeacherOccupationForm;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;
import ee.hitsa.ois.web.dto.TeacherOccupationDto;

@Transactional
@Service
public class TeacherOccupationService {

    @Autowired
    private EntityManager em;
    @Autowired
    private TeacherOccupationRepository teacherOccupationRepository;

    public Page<TeacherOccupationDto> findAll(Long schoolId, TeacherOccupationSearchCommand criteria, Pageable pageable) {
        return teacherOccupationRepository.findAll((root, query, cb) -> {
            List<Predicate> filter = new ArrayList<>();
            filter.add(cb.equal(root.get("school").get("id"), schoolId));
            propertyContains(() -> root.get("occupationEt"), cb, criteria.getOccupationEt(), filter::add);
            propertyContains(() -> root.get("occupationEn"), cb, criteria.getOccupationEn(), filter::add);
            if(Boolean.TRUE.equals(criteria.getIsValid())) {
                filter.add(cb.equal(root.get("isValid"), Boolean.TRUE));
            }
            return cb.and(filter.toArray(new Predicate[filter.size()]));
        }, pageable).map(TeacherOccupationDto::of);
    }

    public List<TeacherOccupationDto> listAll(Long schoolId) {
        return StreamUtil.toMappedList(TeacherOccupationDto::of,
                teacherOccupationRepository.findAll((root, query, cb) -> cb.equal(root.get("school").get("id"), schoolId)));
    }

    public TeacherOccupation create(HoisUserDetails user, TeacherOccupationForm form) {
        TeacherOccupation teacherOccupation = new TeacherOccupation();
        teacherOccupation.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(teacherOccupation, form);
    }

    public TeacherOccupation save(TeacherOccupation teacherOccupation, TeacherOccupationForm form) {
        EntityUtil.bindToEntity(form, teacherOccupation);
        return EntityUtil.save(teacherOccupation, em);
    }

    public void delete(TeacherOccupation teacherOccupation) {
        EntityUtil.deleteEntity(teacherOccupation, em);
    }
}
