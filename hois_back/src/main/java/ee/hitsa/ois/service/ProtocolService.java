package ee.hitsa.ois.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;

@Transactional
@Service
public class ProtocolService {

    @Autowired
    private EntityManager em;

    public boolean hasStudentPositiveGradeInOccupationModule(Student student,
            CurriculumVersionOccupationModule module) {

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from protocol_student ps "
                + "inner join protocol p on p.id = ps.protocol_id "
                + "inner join protocol_vdata pvd on pvd.protocol_id = p.id ");

        qb.requiredCriteria("ps.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("pvd.curriculum_version_omodule_id = :curriculumVersionOmoduleId", "curriculumVersionOmoduleId", EntityUtil.getId(module));
        qb.requiredCriteria("ps.grade_code in :positiveGrades", "positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        return !qb.select("true", em).getResultList().isEmpty();
    }


}
