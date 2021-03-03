package ee.hitsa.ois.repository;

import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.repository.model.ITeacherLoadReportResult;
import ee.hitsa.ois.service.SubjectStudyPeriodPlanService;
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodCapacitiesService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonPlanRepository extends JpaRepository<LessonPlan, Long> {

    /*
    There was a case when vocational needed to get numbers in select instead of join (is_capacity_diff problem when checked for null values)
                    "coalesce((select sum(case when ssppc.is_contact then sspc.hours end) " +
                        "from subject_study_period_teacher sspt2 " +
                        "join subject_study_period ssp on ssp.id = sspt2.subject_study_period_id " +
                        "join subject_study_period_capacity sspc on sspc.subject_study_period_id = ssp.id " +
                        "join study_period sp on sp.id = ssp.study_period_id " +
                        "left join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp " +
                            "on ssp.id = sspp.ssp_id and sspp.priority != 999 " +
                        "left join subject_study_period_plan_capacity ssppc " +
                            "on sspp.plan_id = ssppc.subject_study_period_plan_id and sspc.capacity_type_code = ssppc.capacity_type_code " +
                        "where (ssp.is_capacity_diff is null or ssp.is_capacity_diff = false) " +
                            "and sp.study_year_id = :studyYearId and sspt2.id = sspt.id " +
                        "group by sspt2.id), 0) + " +
                    "coalesce((select sum(case when ssppc.is_contact then ssptc.hours end) " +
                        "from subject_study_period_teacher sspt2 " +
                        "join (" + SubjectStudyPeriodCapacitiesService.SQL_SELECT_TEACHER_CAPACITY + ") ssptc " +
                        "on ssptc.subject_study_period_teacher_id = sspt2.id " +
                        "join subject_study_period ssp2 on ssp2.id = sspt2.subject_study_period_id " +
                        "join study_period sp2 on sp2.id = ssp2.study_period_id " +
                        "join subject_study_period_capacity sspc on ssptc.subject_study_period_capacity_id = sspc.id " +
                        "left join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp " +
                        "on ssp2.id = sspp.ssp_id and sspp.priority != 999 " +
                        "left join subject_study_period_plan_capacity ssppc " +
                        "on sspp.plan_id = ssppc.subject_study_period_plan_id and sspc.capacity_type_code = ssppc.capacity_type_code " +
                        "where ssp2.is_capacity_diff = true and sp2.study_year_id = :studyYearId and sspt2.id = sspt.id " +
                        "group by sspt2.id), 0) as hoursContact " +
     */
    @Query(value =
            "select distinct ssp.id as itemId, " +
                    "s.name_et as nameEt, " +
                    "s.name_en as nameEn, " +
                    "(select string_agg(distinct sg.code, ', ' order by sg.code) " +
                        "from subject_study_period_student_group sspsg " +
                        "join student_group sg on sspsg.student_group_id = sg.id " +
                        "where sspsg.subject_study_period_id = ssp.id) as groups, " +
                    "(select string_agg(distinct subgroup.code, ', ' order by subgroup.code) " +
                        "from subject_study_period_subgroup subgroup " +
                        "where subgroup.subject_study_period_id = ssp.id) as subgroups, " +
                    "ssp.study_period_id as studyPeriodId, " +
                    "s.code as subjectCode," +
                    "s.credits, " +
                    "s.assessment_code as assessmentCode, " +
//                    "coalesce(ssp_hours.num, 0) + coalesce(sspt_hours.num, 0) as hoursAll, " +
                    "coalesce(ssp_hours.contact, 0) + coalesce(sspt_hours.contact, 0) as hoursContact " +
                    "from subject_study_period ssp " +
                    "join study_period sp on ssp.study_period_id = sp.id " +
                    "join subject_study_period_teacher sspt on ssp.id = sspt.subject_study_period_id " +
                    "join subject s on ssp.subject_id = s.id " +
                    "left join (" +
                        "select sum(sspc.hours) as num, " +
                            "sum(case when ssppc.is_contact then sspc.hours end) as contact, " +
                            "sspt.id as sspt_id " +
                        "from subject_study_period_teacher sspt " +
                        "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id " +
                        "join subject_study_period_capacity sspc on sspc.subject_study_period_id = ssp.id " +
                        "join study_period sp on sp.id = ssp.study_period_id " +
                        "left join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp " +
                            "on ssp.id = sspp.ssp_id and sspp.priority != 999 " +
                        "left join subject_study_period_plan_capacity ssppc " +
                            "on sspp.plan_id = ssppc.subject_study_period_plan_id and sspc.capacity_type_code = ssppc.capacity_type_code " +
                        "where (ssp.is_capacity_diff is null or ssp.is_capacity_diff = false) and sp.study_year_id = :studyYearId " +
                        "group by sspt.id) ssp_hours on sspt.id = ssp_hours.sspt_id " +
                    "left join (" +
                        "select sum(ssptc.hours) as num, " +
                        "sum(case when ssppc.is_contact then ssptc.hours end) as contact, " +
                        "sspt2.id as sspt_id " +
                        "from subject_study_period_teacher sspt2 " +
                        "join (" + SubjectStudyPeriodCapacitiesService.SQL_SELECT_TEACHER_CAPACITY + ") ssptc " +
                            "on ssptc.subject_study_period_teacher_id = sspt2.id " +
                        "join subject_study_period ssp2 on ssp2.id = sspt2.subject_study_period_id " +
                        "join study_period sp2 on sp2.id = ssp2.study_period_id " +
                        "join subject_study_period_capacity sspc on ssptc.subject_study_period_capacity_id = sspc.id " +
                        "left join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp " +
                            "on ssp2.id = sspp.ssp_id and sspp.priority != 999 " +
                        "left join subject_study_period_plan_capacity ssppc " +
                            "on sspp.plan_id = ssppc.subject_study_period_plan_id and sspc.capacity_type_code = ssppc.capacity_type_code " +
                        "where ssp2.is_capacity_diff = true and sp2.study_year_id = :studyYearId " +
                        "group by sspt2.id) sspt_hours on sspt.id = sspt_hours.sspt_id " +
                    "where sp.study_year_id = :studyYearId and sspt.teacher_id = :teacherId " +
                    "order by s.code, groups, subgroups nulls first, ssp.id",
            nativeQuery = true)
    List<ITeacherLoadReportResult> teacherLoadReportResultHigher(@Param("teacherId") Long teacherId, @Param("studyYearId") Long studyYearId);

    @Query(value =
            "select distinct j.id, " +
                    "j.name_et, " +
                    "j.name_et as name_en, " +
                    "(select string_agg(distinct sg.code, ', ' order by sg.code) " +
                        "from journal_omodule_theme jot " +
                        "left join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id " +
                        "left join lesson_plan lp on lpm.lesson_plan_id = lp.id " +
                        "left join student_group sg on lp.student_group_id = sg.id where j.id = jot.journal_id) as groups, " +
                    "null as subgroups, " +
                    "null as study_period_id, " +
                    "null as subject_code, " +
                    "null as subject_credits, " +
                    "j.assessment_code, " +
                    "coalesce((select sum(case when sct.is_contact then jc.hours end) " +
                        "from journal_teacher jt2 " +
                        "join journal j on j.id = jt2.journal_id " +
                        "join journal_capacity jc on j.id = jc.journal_id " +
                        "join journal_capacity_type jct on jc.journal_capacity_type_id = jct.id " +
                        "join classifier c on jct.capacity_type_code = c.code " +
                        "join school_capacity_type sct " +
                            "on sct.school_id = j.school_id and c.code = sct.capacity_type_code and sct.is_higher = false " +
                        "where (j.is_capacity_diff is null or j.is_capacity_diff = false) " +
                            "and j.study_year_id = :studyYearId " +
                            // if teacher
                            "and (:isNotTeacher or j.id in (select jot.journal_id " +
                            "from journal_omodule_theme jot " +
                            "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
                            "join lesson_plan lp on lp.id = lpm.lesson_plan_id " +
                            "where lp.is_usable = true)) " +
                            "and jt2.id = jt.id group by jt2.id), 0) + " +
                    "coalesce((select  sum(case when sct.is_contact then jtc.hours end) " +
                        "from journal_teacher jt2 " +
                        "join journal j2 on j2.id = jt2.journal_id " +
                        "join journal_teacher_capacity jtc on jt2.id = jtc.journal_teacher_id " +
                        "join journal_capacity_type jct on jtc.journal_capacity_type_id = jct.id " +
                        "join classifier c on jct.capacity_type_code = c.code " +
                        "join school_capacity_type sct on sct.school_id = j2.school_id " +
                            "and c.code = sct.capacity_type_code and sct.is_higher = false " +
                        "where j2.is_capacity_diff = true " +
                            "and j2.study_year_id = :studyYearId " +
                        // if teacher
                            "and (:isNotTeacher or j2.id in (select jot.journal_id " +
                                "from journal_omodule_theme jot " +
                                "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
                                "join lesson_plan lp on lp.id = lpm.lesson_plan_id " +
                                "where lp.is_usable = true)) " +
                            "and jt2.id = jt.id group by jt2.id), 0) as hours_contact " +
                    "from journal j " +
                    "join journal_teacher jt on j.id = jt.journal_id " +
                    "left join journal_omodule_theme jot on j.id = jot.journal_id " +
                    "left join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id " +
                    "left join lesson_plan lp on lpm.lesson_plan_id = lp.id " +
                    "left join student_group sg on lp.student_group_id = sg.id " +
                    "where j.study_year_id = :studyYearId " +
                    "and jt.teacher_id = :teacherId " +
                    "order by j.name_et, j.id",
            nativeQuery = true)
    List<ITeacherLoadReportResult> teacherLoadReportResultVocational(@Param("teacherId") Long teacherId,
                                                                     @Param("studyYearId") Long studyYearId,
                                                                     @Param("isNotTeacher") Boolean isNotTeacher);
}
