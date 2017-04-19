package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchTeacherCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchDto;

@Transactional
@Service
public class LessonPlanService {

    @Autowired
    private EntityManager em;

    public Page<LessonPlanSearchDto> search(Long schoolId, LessonPlanSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from lesson_plan lp inner join student_group sg on lp.student_group_id = sg.id", pageable);

        qb.requiredCriteria("lp.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        // TODO schoolDepartment
        qb.optionalCriteria("lp.student_group_id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());

        return JpaQueryUtil.pagingResult(qb, "lp.id, sg.id as student_group_id, sg.code", em, pageable).map(r -> {
            String groupCode = resultAsString(r, 2);
            return new LessonPlanSearchDto(resultAsLong(r, 0), new AutocompleteResult(resultAsLong(r, 1), groupCode, groupCode), null);
        });
    }

    public Page<LessonPlanSearchDto> search(Long schoolId, LessonPlanSearchTeacherCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from lesson_plan lp inner join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id " +
                "inner join teacher t on lpm.teacher_id = t.id inner join person p on t.person_id = p.id", pageable);

        qb.requiredCriteria("lp.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("lpm.teacher_id = :teacherId", "teacherId", criteria.getTeacher());

        return JpaQueryUtil.pagingResult(qb, "lp.id, p.firstname, p.lastname", em, pageable).map(r -> {
            return new LessonPlanSearchDto(resultAsLong(r, 0), null, PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
        });
    }
}
