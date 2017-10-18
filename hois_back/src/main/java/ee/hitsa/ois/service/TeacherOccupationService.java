package ee.hitsa.ois.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.TeacherOccupationForm;
import ee.hitsa.ois.web.commandobject.TeacherOccupationSearchCommand;
import ee.hitsa.ois.web.dto.TeacherOccupationDto;

@Transactional
@Service
public class TeacherOccupationService {

    @Autowired
    private EntityManager em;

    public Page<TeacherOccupationDto> search(Long schoolId, TeacherOccupationSearchCommand criteria, Pageable pageable) {
        JpaQueryBuilder<TeacherOccupation> qb = new JpaQueryBuilder<>(TeacherOccupation.class, "t").sort(pageable);
        qb.requiredCriteria("t.school.id = :schoolId", "schoolId", schoolId);
        qb.optionalContains("t.occupationEt", "occupationEt", criteria.getOccupationEt());
        qb.optionalContains("t.occupationEn", "occupationEn", criteria.getOccupationEn());
        if(Boolean.TRUE.equals(criteria.getIsValid())) {
            qb.filter("t.isValid = true");
        }

        return JpaQueryUtil.pagingResult(qb, em, pageable).map(TeacherOccupationDto::of);
    }

    public List<TeacherOccupationDto> listAll(Long schoolId) {
        List<TeacherOccupation> occupations = em.createQuery("select t from TeacherOccupation t where t.school.id = ?1", TeacherOccupation.class)
                .setParameter(1, schoolId).getResultList();
        return StreamUtil.toMappedList(TeacherOccupationDto::of, occupations);
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
