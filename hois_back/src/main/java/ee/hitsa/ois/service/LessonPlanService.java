package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.repository.LessonPlanRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanCreateForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchTeacherCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchTeacherDto;

@Transactional
@Service
public class LessonPlanService {

    @Autowired
    private EntityManager em;
    @Autowired
    private LessonPlanRepository lessonPlanRepository;

    public LessonPlan create(HoisUserDetails user, LessonPlanCreateForm form) {
        StudentGroup studentGroup = em.getReference(StudentGroup.class, form.getStudentGroup());
        UserUtil.assertSameSchool(user, studentGroup.getSchool());
        StudyYear studyYear = em.getReference(StudyYear.class, form.getStudyYear());
        UserUtil.assertSameSchool(user, studyYear.getSchool());

        LessonPlan lessonPlan = new LessonPlan();
        lessonPlan.setSchool(em.getReference(School.class, user.getSchoolId()));
        lessonPlan.setStudentGroup(studentGroup);
        lessonPlan.setStudyYear(studyYear);
        lessonPlan.setIsUsable(Boolean.FALSE);
        lessonPlan.setShowWeeks(Boolean.FALSE);
        // XXX for higher this is optional in student group
        lessonPlan.setCurriculumVersion(studentGroup.getCurriculumVersion());
        // TODO modules
        return lessonPlanRepository.save(lessonPlan);
    }

    public LessonPlan save(LessonPlan lessonPlan, LessonPlanForm form) {
        // TODO
        return lessonPlanRepository.save(lessonPlan);
    }

    public Page<LessonPlanSearchDto> search(Long schoolId, LessonPlanSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from lesson_plan lp inner join student_group sg on lp.student_group_id = sg.id " +
                "inner join curriculum_version cv on lp.curriculum_version_id = cv.id").sort(pageable);

        qb.requiredCriteria("lp.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("cv.school_department_id = :schoolDepartmentId", "schoolDepartmentId", criteria.getSchoolDepartment());
        qb.optionalCriteria("lp.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", criteria.getCurriculumVerison());
        qb.optionalCriteria("lp.student_group_id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());

        return JpaQueryUtil.pagingResult(qb, "lp.id, sg.code as student_group_code, cv.code", em, pageable).map(r -> {
            return new LessonPlanSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2));
        });
    }

    public Page<LessonPlanSearchTeacherDto> search(HoisUserDetails user, LessonPlanSearchTeacherCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from journal j inner join journal_teacher jt on j.id = jt.journal_id " +
                "inner join teacher t on jt.teacher_id = t.id inner join person p on t.person_id = p.id").sort(pageable);

        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        if(user.isTeacher()) {
            // TODO is_usable from lesson_plan
            // qb.filter("lp.is_usable = true");
            qb.requiredCriteria("t.person_id = :personId", "personId", user.getPersonId());
        } else {
            qb.optionalCriteria("jt.teacher_id = :teacherId", "teacherId", criteria.getTeacher());
        }

        return JpaQueryUtil.pagingResult(qb, "jt.teacher_id, p.firstname, p.lastname", em, pageable).map(r -> {
            return new LessonPlanSearchTeacherDto(resultAsLong(r, 0), PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
        });
    }

    public List<AutocompleteResult> studentgroupsForLessonPlan(Long schoolId) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
                "from student_group sg left outer join lesson_plan lp on lp.student_group_id = sg.id");

        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("lp.id is null");
        // TODO current study year (vocational) or study period (higher)

        List<?> data = qb.select("sg.id, sg.code", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 1);
            return new AutocompleteResult(resultAsLong(r, 0), code, code);
        }, data);
    }
}
