package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.EducationalSuccess;
import ee.hitsa.ois.enums.ExmatriculationReason;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.Sex;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.StudentAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.report.EducationalSuccessCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.report.BestResultStudentResultDto;
import ee.hitsa.ois.web.dto.report.EducationalSuccessDebtDto;
import ee.hitsa.ois.web.dto.report.EducationalSuccessDebtModule;
import ee.hitsa.ois.web.dto.report.EducationalSuccessDirectorDto;
import ee.hitsa.ois.web.dto.report.EducationalSuccessStudentGroupResultDto;
import ee.hitsa.ois.web.dto.report.EducationalSuccessStudentResultDto;

@Transactional
@Service
public class EducationalSuccessReportService {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private SchoolService schoolService;
    
    
    public Page<Object> educationalSuccess(HoisUserDetails user, EducationalSuccessCommand criteria, Pageable pageable) {
        
        EducationalSuccess queryType = EducationalSuccess.valueOf(criteria.getQueryType());
        
        switch (queryType) {
            case EDUCATIONAL_SUCCESS_HAS_DEBT:
                return studentDebt(user, criteria, pageable, true);
            case EDUCATIONAL_SUCCESS_NO_DEBT:
                return studentDebt(user, criteria, pageable, false);
            case EDUCATIONAL_SUCCESS_RESULTS:
                return studentOrStudentGroupResult(user, criteria, pageable);
            case EDUCATIONAL_SUCCESS_STUDY_DIRECTOR_REPORT:
                return studyDirectorReport(user, criteria, pageable);
            case EDUCATIONAL_SUCCESS_BEST_RESULTS:
                return bestResults(user, criteria, pageable);
        }
        return null;
    }

    private Page<Object> bestResults(HoisUserDetails user, EducationalSuccessCommand criteria, Pageable pageable) {
        String SEARCH_FROM = "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                    + "join person p on p.id = s.person_id "
                + "left join student_curriculum_completion scc on scc.student_id = s.id "
                + "left join (select count(svr.id) as count, svr.student_id "
                    + "from student_vocational_result svr "
                    + "where svr.grade_mark = 4 "
                    + "group by svr.student_id) modules on modules.student_id = s.id "
                + "left join (select sum(absences.absences) as totalAbsences, absences.id "
                    + "from (select s1.id, journalStudent.absences as absences "
                        + "from student s1 "
                        + "left join (select journalEntry.student_id, sum(journalEntry.absences) as absences "
                            + "from (select js.student_id, case when jes.is_lesson_absence = true then count(distinct jesla.id) else coalesce(je.lessons, 1) end as absences "
                                + "from journal j "
                                + "join journal_student js on js.journal_id = j.id "
                                + "join journal_entry je on je.journal_id = j.id "
                                + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                                + "left join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id and jes.is_lesson_absence = true "
                                + "where (jes.absence_code is not null or jesla.absence_code is not null) "
                                + "and ((jes.is_lesson_absence = true and jesla.absence_code in (:absenceCodes)) "
                                + "or (jes.is_lesson_absence != true and jes.absence_code in (:absenceCodes)))"
                                + "group by js.student_id, je.id, jes.id"
                            + ") journalEntry group by journalEntry.student_id"
                        + ") journalStudent on journalStudent.student_id = s1.id "
                    + ") absences group by absences.id "
                + ") absences on absences.id = s.id "
                + "left join (select sum(absences.absences) as totalAbsences, absences.id "
                    + "from (select s2.id, journalStudent.absences as absences "
                        + "from student s2 "
                        + "left join (select journalEntry.student_id, sum(journalEntry.absences) as absences "
                            + "from (select js.student_id, case when jes.is_lesson_absence = true then count(distinct jesla.id) else coalesce(je.lessons, 1) end as absences "
                                + "from journal j "
                                + "join journal_student js on js.journal_id = j.id "
                                + "join journal_entry je on je.journal_id = j.id "
                                + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                                + "left join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id and jes.is_lesson_absence = true "
                                + "where (jes.absence_code is not null or jesla.absence_code is not null) "
                                + "and ((jes.is_lesson_absence = true and jesla.absence_code = '" + Absence.PUUDUMINE_P.name() + "') "
                                + "or (jes.is_lesson_absence != true and jes.absence_code = '" + Absence.PUUDUMINE_P.name() + "')) "
                                + "group by js.student_id, je.id, jes.id"
                            + ") journalEntry group by journalEntry.student_id"
                        + ") journalStudent on journalStudent.student_id = s2.id "
                    + ") absences group by absences.id "
                + ") absencesP on absencesP.id = s.id ";
    
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("s.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("sg.id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroup());
        qb.optionalCriteria("c.id in (:curriculumIds)", "curriculumIds", criteria.getCurriculum());
        qb.optionalCriteria("sg.teacher_id in (:studentGroupTeachers)", "studentGroupTeachers", criteria.getStudentGroupTeacher());
        qb.filter("(c.is_higher = false "
                    + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                    + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                    + "))");
        qb.filter("(sh.id is not null "
                + "or "
                + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)))" );
        qb.filter("not exists(select 1 "
                + "from student_vocational_result svr "
                + "where svr.student_id = s.id "
                + "and svr.grade_code in (:negativeGrades))");
        qb.filter("exists(select 1 "
                + "from student_vocational_result svr "
                + "where svr.student_id = s.id "
                + "and svr.grade_code in (:positiveGrades))");
        if (criteria.getThru() != null) {
            qb.parameter("thruDate", criteria.getThru());
        }
        if (criteria.getWeightedAverageSign() != null) {
            qb.optionalCriteria("scc.average_mark " + criteria.getWeightedAverageSign() + " :weightedAverage", "weightedAverage", criteria.getWeightedAverage());
        }
        if (criteria.getModuleFourSign() != null) {
            qb.optionalCriteria("coalesce(modules.count, 0) " + criteria.getModuleFourSign() + " :moduleFour", "moduleFour", criteria.getModuleFour());
        }
        qb.parameter("absenceCodes", EnumUtil.toNameList(Absence.PUUDUMINE_P, Absence.PUUDUMINE_PR, Absence.PUUDUMINE_V));
        qb.optionalCriteria("coalesce(absences.totalAbsences, 0) <= :absenceNr", "absenceNr", criteria.getAbsence());
        qb.optionalCriteria("coalesce(absencesP.totalAbsences, 0) <= :causelessAbsence", "causelessAbsence", criteria.getCauselessAbsence());
        qb.parameter("activeStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.parameter("negativeGrades", EnumUtil.toNameList(OccupationalGrade.KUTSEHINDAMINE_1, OccupationalGrade.KUTSEHINDAMINE_2,
                OccupationalGrade.KUTSEHINDAMINE_3, OccupationalGrade.KUTSEHINDAMINE_MA, OccupationalGrade.KUTSEHINDAMINE_X));
        qb.parameter("positiveGrades", EnumUtil.toNameList(OccupationalGrade.KUTSEHINDAMINE_4, OccupationalGrade.KUTSEHINDAMINE_5
                , OccupationalGrade.KUTSEHINDAMINE_A));
        String SELECT = "s.id, p.firstname, p.lastname, sg.code, scc.average_mark, coalesce(modules.count, 0), coalesce(absences.totalAbsences, 0) as absences, coalesce(absencesP.totalAbsences, 0) as absencesP";
        qb.sort("scc.average_mark desc");

        return JpaQueryUtil.pagingResult(qb, SELECT, em, pageable).map(r -> {
            BestResultStudentResultDto dto = new BestResultStudentResultDto(r);
            return dto;
        });
    }

    private Page<Object> studyDirectorReport(HoisUserDetails user, EducationalSuccessCommand criteria,
            Pageable pageable) {
        String SELECT = "'report.studentSuccess.director.studentCount' as header, count(distinct s.id)\\:\\:text as data, true as bold, 0 as orderNr ";
        String SEARCH_FROM = "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                +"where (sh.id is not null "
                    + "or "
                    + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                    + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                        + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                        + ")) "
                + "union "
                + "select 'report.studentSuccess.director.studentAcademic' as header, count(distinct s.id)\\:\\:text as data, false as bold, 1 as orderNr "
                + "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code = '" + StudentStatus.OPPURSTAATUS_A.name() + "') "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_A.name() + "') "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_A.name() + "') "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                +"where (sh.id is not null "
                    + "or "
                    + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_A.name() + "'))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                        + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                        + ")) "
                + "union "
                + "select 'report.studentSuccess.director.studentForeign' as header, count(distinct s.id)\\:\\:text as data, false as bold, 2 as orderNr "
                + "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code = '" + StudentStatus.OPPURSTAATUS_V.name() + "') "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_V.name() + "') "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_V.name() + "') "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                +"where (sh.id is not null "
                    + "or "
                    + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_V.name() + "'))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                        + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                        + ")) "
                + "union "
                + "select 'report.studentSuccess.director.studentGuest' as header, count(distinct s.id)\\:\\:text as data, false as bold, 3 as orderNr "
                + "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                +"where (sh.id is not null "
                    + "or "
                    + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                    + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                        + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                        + ")) "
                    + "and s.type_code = '" + StudentType.OPPUR_K.name() + "' "
                + "union "
                + "select 'report.studentSuccess.director.studentStudying' as header, count(distinct s.id)\\:\\:text as data, true as bold, 4 as orderNr "
                + "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                +"where (sh.id is not null "
                    + "or "
                    + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "'))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                        + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                        + ")) "
                + "union "
                + "select 'report.studentSuccess.director.studentStudyingMale' as header, count(distinct s.id)\\:\\:text as data, false as bold, 6 as orderNr "
                + "from student s "
                + "join person p on p.id = s.person_id "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                    // data transer generated student history should be ignored
                    + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                    + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                    + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                    + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                    + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                +"where (sh.id is not null "
                    + "or "
                    + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "'))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                        + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                        + ")) "
                    + "and p.sex_code = :sexMale "
                + "union "
                + "select 'report.studentSuccess.director.studentStudyingFemale' as header, count(distinct s.id)\\:\\:text as data, false as bold, 7 as orderNr "
                + "from student s "
                + "join person p on p.id = s.person_id "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                +"where (sh.id is not null "
                    + "or "
                    + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "'))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                        + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                        + ")) "
                    + "and p.sex_code = :sexFemale "
                + "union "
                + "select 'report.studentSuccess.director.studentStudyingNominalEnded' as header, count(distinct s.id)\\:\\:text as data, false as bold, 8 as orderNr "
                + "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                        + "and sh.nominal_study_end < :thruDate "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                +"where (sh.id is not null "
                    + "or "
                    + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                        + "and s.status_code in (:activeStatus) and s.nominal_study_end < :thruDate))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                    + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                        + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                        + ")) "
                + "union "
                + "select 'report.studentSuccess.director.studentExmat' as header, count(distinct s.id)\\:\\:text as data, true as bold, 9 as orderNr "
                + "from student s "
                + "where exists(select 1 from student_history sh "
                        + "left join curriculum_version cv on cv.id = sh.curriculum_version_id "
                        + "left join curriculum c on c.id = cv.curriculum_id "
                        + "where sh.student_id = s.id "
                        + "and (cast(sh.valid_from as date) < :fromDate or sh.valid_from is null) "
                        + "and (cast(sh.valid_thru as date) > :fromDate or sh.valid_thru is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and sh.status_code in (:activeStatus)) "
                    + "and exists(select 1 from student_history sh "
                        + "left join curriculum_version cv on cv.id = sh.curriculum_version_id "
                        + "left join curriculum c on c.id = cv.curriculum_id "
                        + "where sh.student_id = s.id "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and sh.status_code = '" + StudentStatus.OPPURSTAATUS_K.name() + "') "
                    + "and exists(select 1 from directive d join directive_student ds on d.id = ds.directive_id "
                        + "where ds.student_id = s.id "
                        + "and d.type_code = '" + DirectiveType.KASKKIRI_EKSMAT.name() + "' "
                        + "and d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                        + "and ds.canceled != true "
                        + "and d.confirm_date between :fromDate and :thruDate) "
                + "union "
                + "select 'report.studentSuccess.director.studentExmatB' as header, count(distinct s.id)\\:\\:text as data, false as bold, 10 as orderNr "
                + "from student s "
                + "where exists(select 1 from student_history sh "
                        + "left join curriculum_version cv on cv.id = sh.curriculum_version_id "
                        + "left join curriculum c on c.id = cv.curriculum_id "
                        + "where sh.student_id = s.id "
                        + "and (cast(sh.valid_from as date) < :fromDate or sh.valid_from is null) "
                        + "and (cast(sh.valid_thru as date) > :fromDate or sh.valid_thru is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and sh.status_code in (:activeStatus)) "
                    + "and exists(select 1 from student_history sh "
                        + "left join curriculum_version cv on cv.id = sh.curriculum_version_id "
                        + "left join curriculum c on c.id = cv.curriculum_id "
                        + "where sh.student_id = s.id "
                        + "and (cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and sh.status_code = '" + StudentStatus.OPPURSTAATUS_K.name() + "') "
                    + "and exists(select 1 from directive d join directive_student ds on d.id = ds.directive_id "
                        + "where ds.student_id = s.id "
                        + "and d.type_code = '" + DirectiveType.KASKKIRI_EKSMAT.name() + "' "
                        + "and d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                        + "and ds.canceled != true "
                        + "and d.confirm_date between :fromDate and :thruDate "
                        + "and ds.reason_code = '" + ExmatriculationReason.EKSMAT_POHJUS_B.name() + "') "
                + "union "
                + "select 'report.studentSuccess.director.studentGroupCount' as header, count(distinct sg.id)\\:\\:text as data, true as bold, 11 as orderNr "
                + "from student_group sg "
                + "left join curriculum c on c.id = sg.curriculum_id "
                + "where exists(select 1 "
                    + "from student s "
                    + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                        + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                            // data transer generated student history should be ignored
                            + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                            + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                            + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                            + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                            + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                    + "left join curriculum_version cv on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                    + "left join curriculum c on cv.curriculum_id = c.id "
                    + "left join student_group sg1 on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.student_group_id else sh.student_group_id end = sg1.id "
                    +"where (sh.id is not null "
                        + "or "
                        + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                            + "and s.status_code in (:activeStatus)))" 
                        + "and s.school_id = :schoolId "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and sg1.id = sg.id) "
                    + "and case when sg.is_guest != true then c.is_higher = false else true end "
                + "union "
                + "select 'report.studentSuccess.director.studentGroupCountNoDebt' as header, count(distinct sg.id)\\:\\:text as data, false as bold, 12 as orderNr "
                + "from student_group sg "
                + "join curriculum c on c.id = sg.curriculum_id and c.is_higher = false "
                + "where "
                    + "not exists(select 1 from "
                        + "student s "
                        + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                            + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                // data transer generated student history should be ignored
                                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                                + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                        + "left join curriculum_version cv on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                        + "left join curriculum c on cv.curriculum_id = c.id "
                        + "left join student_group sg1 on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.student_group_id else sh.student_group_id end = sg1.id "
                        + "left join (select svr.student_id, count(svr.id) as amount "
                            + "from student_vocational_result svr "
                            + "where svr.grade_code not in (:positiveGrades) "
                            + "and 'SISSEKANNE_M' in (:gradeTypes) "
                            + "group by svr.student_id) svr on svr.student_id = s.id "
                        + "left join (select js.student_id, count(jes.id) as amount "
                            + "from journal j "
                            + "join journal_student js on js.journal_id = j.id "
                            + "join journal_entry je on je.journal_id = j.id "
                            + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                            + "where jes.grade_code not in (:positiveGrades) "
                            + "and je.entry_type_code in (:gradeTypes) "
                            + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                        + "left join (select scmor.student_id, count(scmor.id) as amount "
                            + "from student_curriculum_module_outcomes_result scmor "
                            + "where scmor.grade_code not in (:positiveGrades) "
                            + "and 'SISSEKANNE_O' in (:gradeTypes) "
                            + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                        +"where (sh.id is not null "
                            + "or "
                            + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                + "and s.status_code in (:activeStatus)))" 
                            + "and s.school_id = :schoolId "
                            + "and (c.is_higher = false "
                            + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                + ")) "
                            + "and sg1.id = sg.id "
                            + "and (svr.student_id is not null or journalStudent.student_id is not null or outcomes.student_id is not null)) "
                    + "and exists(select 1 "
                            + "from student s "
                            + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                                + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                    // data transer generated student history should be ignored
                                    + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                    + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                    + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                    + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                                    + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                            + "left join curriculum_version cv on "
                                + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                                + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                            + "left join curriculum c on cv.curriculum_id = c.id "
                            + "left join student_group sg1 on "
                                + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                                + "then s.student_group_id else sh.student_group_id end = sg1.id "
                            +"where (sh.id is not null "
                                + "or "
                                + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                    + "and s.status_code in (:activeStatus)))" 
                                + "and s.school_id = :schoolId "
                                + "and (c.is_higher = false "
                                + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                    + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                    + ")) "
                                + "and sg1.id = sg.id) "
                        + "and case when sg.is_guest != true then c.is_higher = false else true end "
                + "union "
                + "select 'report.studentSuccess.director.studentGroupCountDebt' as header, count(distinct sg.id)\\:\\:text as data, false as bold, 13 as orderNr "
                + "from student_group sg "
                + "where exists(select 1 "
                    + "from student s "
                    + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                        + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                            // data transer generated student history should be ignored
                            + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                            + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                            + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                            + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                            + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                    + "left join curriculum_version cv on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                    + "left join curriculum c on cv.curriculum_id = c.id "
                    + "left join student_group sg1 on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.student_group_id else sh.student_group_id end = sg1.id "
                    + "left join (select svr.student_id, count(svr.id) as amount "
                        + "from student_vocational_result svr "
                        + "where svr.grade_code not in (:positiveGrades) "
                        + "and 'SISSEKANNE_M' in (:gradeTypes) "
                        + "group by svr.student_id) svr on svr.student_id = s.id "
                    + "left join (select js.student_id, count(jes.id) as amount "
                        + "from journal j "
                        + "join journal_student js on js.journal_id = j.id "
                        + "join journal_entry je on je.journal_id = j.id "
                        + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                        + "where jes.grade_code not in (:positiveGrades) "
                        + "and je.entry_type_code in (:gradeTypes) "
                        + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                    + "left join (select scmor.student_id, count(scmor.id) as amount "
                        + "from student_curriculum_module_outcomes_result scmor "
                        + "where scmor.grade_code not in (:positiveGrades) "
                        + "and 'SISSEKANNE_O' in (:gradeTypes) "
                        + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                    +"where (sh.id is not null "
                        + "or "
                        + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                            + "and s.status_code in (:activeStatus)))" 
                        + "and s.school_id = :schoolId "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and sg1.id = sg.id "
                        + "and (svr.student_id is not null or journalStudent.student_id is not null or outcomes.student_id is not null) "
                    + "group by sg.id "
                    + "having count(distinct s.id) = 1) "
                + "union "
                + "select 'report.studentSuccess.director.debtCount' as header, sum(debtSum.added)\\:\\:text as data, true as bold, 14 as orderNr "
                + "from (select coalesce(svr.amount, 0) + coalesce(outcomes.amount, 0) + coalesce(journalStudent.amount, 0) as added "
                + "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join (select svr.student_id, count(svr.id) as amount "
                    + "from student_vocational_result svr "
                    + "where svr.grade_code not in (:positiveGrades) "
                    + "and 'SISSEKANNE_M' in (:gradeTypes) "
                    + "group by svr.student_id) svr on svr.student_id = s.id "
                + "left join (select js.student_id, count(jes.id) as amount "
                    + "from journal j "
                    + "join journal_student js on js.journal_id = j.id "
                    + "join journal_entry je on je.journal_id = j.id "
                    + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                    + "where jes.grade_code not in (:positiveGrades) "
                    + "and je.entry_type_code in (:gradeTypes) "
                    + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                + "left join (select scmor.student_id, count(scmor.id) as amount "
                    + "from student_curriculum_module_outcomes_result scmor "
                    + "where scmor.grade_code not in (:positiveGrades) "
                    + "and 'SISSEKANNE_O' in (:gradeTypes) "
                    + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                +"where (sh.id is not null "
                    + "or "
                    + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                        + "and s.status_code in (:activeStatus)))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                    + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                        + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                        + ")) "
                + "group by s.id, svr.amount, outcomes.amount, journalStudent.amount) debtSum "
                + "union "
                + "select 'report.studentSuccess.director.debtCountPerStudent' as header, to_char(floor(coalesce(aa.debtSumAdded, 0) * 100 / coalesce(aa.debtSumStudents, 1)) / 100, 'FM999999990.09') as data, false as bold, 15 as orderNr "
                + "from (select sum(debtSum.added) as debtSumAdded, count(debtSum.id) as debtSumStudents "
                    + "from (select distinct on(s.id) s.id, (coalesce(svr.amount, 0) + coalesce(outcomes.amount, 0) + coalesce(journalStudent.amount, 0)) as added "
                        + "from student s "
                        + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                            + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                // data transer generated student history should be ignored
                                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                                + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                        + "left join curriculum_version cv on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                        + "left join curriculum c on cv.curriculum_id = c.id "
                        + "left join (select svr.student_id, count(svr.id) as amount "
                            + "from student_vocational_result svr "
                            + "where svr.grade_code not in (:positiveGrades) "
                            + "and 'SISSEKANNE_M' in (:gradeTypes) "
                            + "group by svr.student_id) svr on svr.student_id = s.id "
                        + "left join (select js.student_id, count(jes.id) as amount "
                            + "from journal j "
                            + "join journal_student js on js.journal_id = j.id "
                            + "join journal_entry je on je.journal_id = j.id "
                            + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                            + "where jes.grade_code not in (:positiveGrades) "
                            + "and je.entry_type_code in (:gradeTypes) "
                            + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                        + "left join (select scmor.student_id, count(scmor.id) as amount "
                            + "from student_curriculum_module_outcomes_result scmor "
                            + "where scmor.grade_code not in (:positiveGrades) "
                            + "and 'SISSEKANNE_O' in (:gradeTypes) "
                            + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                        + "where (sh.id is not null "
                            + "or "
                            + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                + "and s.status_code in (:activeStatus)))" 
                            + "and s.school_id = :schoolId "
                            + "and (c.is_higher = false "
                            + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                + ")) "
                            + ") debtSum) aa "
                + "union "
                + "select 'report.studentSuccess.director.studentWithoutDebt' as header, count(distinct s.id)\\:\\:text as data, true as bold, 16 as orderNr "
                + "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                        + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                        + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "where "
                    + "not exists(select 1 from "
                        + "student s1 "
                        + "left join (select svr.student_id, count(svr.id) as amount "
                            + "from student_vocational_result svr "
                            + "where svr.grade_code not in (:positiveGrades) "
                            + "and 'SISSEKANNE_M' in (:gradeTypes) "
                            + "group by svr.student_id) svr on svr.student_id = s1.id "
                        + "left join (select js.student_id, count(jes.id) as amount "
                            + "from journal j "
                            + "join journal_student js on js.journal_id = j.id "
                            + "join journal_entry je on je.journal_id = j.id "
                            + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                            + "where jes.grade_code not in (:positiveGrades) "
                            + "and je.entry_type_code in (:gradeTypes) "
                            + "group by js.student_id) journalStudent on journalStudent.student_id = s1.id "
                        + "left join (select scmor.student_id, count(scmor.id) as amount "
                            + "from student_curriculum_module_outcomes_result scmor "
                            + "where scmor.grade_code not in (:positiveGrades) "
                            + "and 'SISSEKANNE_O' in (:gradeTypes) "
                            + "group by scmor.student_id) outcomes on outcomes.student_id = s1.id "
                        + "where s1.id = s.id "
                        + "and (svr.student_id is not null or journalStudent.student_id is not null or outcomes.student_id is not null)) "
                    + "and (sh.id is not null "
                        + "or "
                        + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                            + "and s.status_code in (:activeStatus)))" 
                    + "and s.school_id = :schoolId "
                    + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                + "union "
                + "select 'report.studentSuccess.director.averageOverFour' as header, count(distinct aa.id)\\:\\:text as data, true as bold, 17 as orderNr "
                + "from (select grades.id, floor(grades.addedGrades*100/coalesce(grades.gradeAmount, 1))/100 as average "
                    + "from (select distinct on (s.id) s.id, coalesce(journalStudent.addedGrades, 0) + coalesce(outcomes.addedGrades, 0) as addedGrades, coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0) as gradeAmount "
                        + "from student s "
                        + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                            + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                // data transer generated student history should be ignored
                                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                                + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                        + "left join curriculum_version cv on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                        + "left join curriculum c on cv.curriculum_id = c.id "
                        + "left join (select js.student_id, count(jes.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                            + "from journal j "
                            + "join journal_student js on js.journal_id = j.id "
                            + "join journal_entry je on je.journal_id = j.id "
                            + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                            + "join classifier c_grade on c_grade.code = jes.grade_code "
                            + "where c_grade.value in ('1', '2', '3', '4', '5') "
                            + "and je.entry_type_code in (:gradeTypes) "
                            + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                        + "left join (select scmor.student_id, count(scmor.id) as amount, sum(case c_grade.value when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                            + "from student_curriculum_module_outcomes_result scmor "
                            + "join classifier c_grade on c_grade.code = scmor.grade_code "
                            + "where c_grade.value in ('1', '2', '3', '4', '5') "
                            + "and 'SISSEKANNE_O' in (:gradeTypes) "
                            + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                        + "where (sh.id is not null "
                            + "or "
                            + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                + "and s.status_code in (:activeStatus)))" 
                            + "and s.school_id = :schoolId "
                            + "and (c.is_higher = false "
                            + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                + ")) "
                            + "and coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0) != 0) grades "
                        + "where floor(grades.addedGrades*100/coalesce(grades.gradeAmount, 1))/100 > 4) aa "
                + "union "
                + "select 'report.studentSuccess.director.firstCourseAverage' as header, aa.data as data, true as bold, 18 as orderNr "
                + "from (values (1)) as qid (query_id) "
                + "left join (select to_char(floor(coalesce(sum(grades.averageGrade), 0)*100/coalesce(count(grades.averageGrade), 1))/100, 'FM999999990.09') as data "
                + "from (select distinct on (s.id) s.id, floor((coalesce(journalStudent.addedGrades, 0) + coalesce(outcomes.addedGrades, 0)) * 100 / (coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0))) / 100 as averageGrade, sg.course "
                    + "from student s "
                    + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                        + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                            // data transer generated student history should be ignored
                            + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                            + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                            + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                            + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                            + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                    + "left join curriculum_version cv on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                    + "left join curriculum c on cv.curriculum_id = c.id "
                    + "left join student_group sg on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.student_group_id else sh.student_group_id end = sg.id "
                    + "left join (select js.student_id, count(jes.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                        + "from journal j "
                        + "join journal_student js on js.journal_id = j.id "
                        + "join journal_entry je on je.journal_id = j.id "
                        + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                        + "join classifier c_grade on c_grade.code = jes.grade_code "
                        + "where c_grade.value in ('1', '2', '3', '4', '5') "
                        + "and je.entry_type_code in (:gradeTypes) "
                        + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                    + "left join (select scmor.student_id, count(scmor.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                        + "from student_curriculum_module_outcomes_result scmor "
                        + "join classifier c_grade on c_grade.code = scmor.grade_code "
                        + "where c_grade.value in ('1', '2', '3', '4', '5') "
                        + "and 'SISSEKANNE_O' in (:gradeTypes) "
                        + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                    + "where (sh.id is not null "
                        + "or "
                        + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                            + "and s.status_code in (:activeStatus)))" 
                        + "and s.school_id = :schoolId "
                        + "and sg.course = '1' "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0) != 0) grades "
                    + "group by grades.course) aa on true "
                + "union "
                + "select 'report.studentSuccess.director.secondCourseAverage' as header, aa.data as data, true as bold, 19 as orderNr "
                + "from (values (1)) as qid (query_id) "
                + "left join (select to_char(floor(coalesce(sum(grades.averageGrade), 0)*100/coalesce(count(grades.averageGrade), 1))/100, 'FM999999990.09') as data "
                + "from (select distinct on (s.id) s.id, floor((coalesce(journalStudent.addedGrades, 0) + coalesce(outcomes.addedGrades, 0)) * 100 / (coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0))) / 100 as averageGrade, sg.course "
                    + "from student s "
                    + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                        + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                            // data transer generated student history should be ignored
                            + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                            + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                            + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                            + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                            + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                    + "left join curriculum_version cv on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                    + "left join curriculum c on cv.curriculum_id = c.id "
                    + "left join student_group sg on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.student_group_id else sh.student_group_id end = sg.id "
                    + "left join (select js.student_id, count(jes.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                        + "from journal j "
                        + "join journal_student js on js.journal_id = j.id "
                        + "join journal_entry je on je.journal_id = j.id "
                        + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                        + "join classifier c_grade on c_grade.code = jes.grade_code "
                        + "where c_grade.value in ('1', '2', '3', '4', '5') "
                        + "and je.entry_type_code in (:gradeTypes) "
                        + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                    + "left join (select scmor.student_id, count(scmor.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                        + "from student_curriculum_module_outcomes_result scmor "
                        + "join classifier c_grade on c_grade.code = scmor.grade_code "
                        + "where c_grade.value in ('1', '2', '3', '4', '5') "
                        + "and 'SISSEKANNE_O' in (:gradeTypes) "
                        + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                    + "where (sh.id is not null "
                        + "or "
                        + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                            + "and s.status_code in (:activeStatus)))" 
                        + "and s.school_id = :schoolId "
                        + "and sg.course = '2' "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0) != 0) grades "
                    + "group by grades.course) aa on true "
                + "union "
                + "select 'report.studentSuccess.director.thirdCourseAverage' as header, aa.data as data, true as bold, 20 as orderNr "
                + "from (values (1)) as qid (query_id) "
                + "left join (select to_char(floor(coalesce(sum(grades.averageGrade), 0)*100/coalesce(count(grades.averageGrade), 1))/100, 'FM999999990.09') as data "
                + "from (select distinct on (s.id) s.id, floor((coalesce(journalStudent.addedGrades, 0) + coalesce(outcomes.addedGrades, 0)) * 100 / (coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0))) / 100 as averageGrade, sg.course "
                    + "from student s "
                    + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                        + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                            // data transer generated student history should be ignored
                            + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                            + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                            + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                            + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                            + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                    + "left join curriculum_version cv on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                    + "left join curriculum c on cv.curriculum_id = c.id "
                    + "left join student_group sg on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.student_group_id else sh.student_group_id end = sg.id "
                    + "left join (select js.student_id, count(jes.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                        + "from journal j "
                        + "join journal_student js on js.journal_id = j.id "
                        + "join journal_entry je on je.journal_id = j.id "
                        + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                        + "join classifier c_grade on c_grade.code = jes.grade_code "
                        + "where c_grade.value in ('1', '2', '3', '4', '5') "
                        + "and je.entry_type_code in (:gradeTypes) "
                        + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                    + "left join (select scmor.student_id, count(scmor.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                        + "from student_curriculum_module_outcomes_result scmor "
                        + "join classifier c_grade on c_grade.code = scmor.grade_code "
                        + "where c_grade.value in ('1', '2', '3', '4', '5') "
                        + "and 'SISSEKANNE_O' in (:gradeTypes) "
                        + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                    + "where (sh.id is not null "
                        + "or "
                        + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                            + "and s.status_code in (:activeStatus)))" 
                        + "and s.school_id = :schoolId "
                        + "and sg.course = '3' "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0) != 0) grades "
                    + "group by grades.course) aa on true "
                + "union "
                + "select 'report.studentSuccess.director.fourthCourseAverage' as header, aa.data as data, true as bold, 21 as orderNr "
                + "from (values (1)) as qid (query_id) "
                + "left join (select to_char(floor(coalesce(sum(grades.averageGrade), 0)*100/coalesce(count(grades.averageGrade), 1))/100, 'FM999999990.09') as data "
                + "from (select distinct on (s.id) s.id, floor((coalesce(journalStudent.addedGrades, 0) + coalesce(outcomes.addedGrades, 0)) * 100 / (coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0))) / 100 as averageGrade, sg.course "
                    + "from student s "
                    + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                        + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                            // data transer generated student history should be ignored
                            + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                            + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                            + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                            + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                            + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                    + "left join curriculum_version cv on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                    + "left join curriculum c on cv.curriculum_id = c.id "
                    + "left join student_group sg on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.student_group_id else sh.student_group_id end = sg.id "
                    + "left join (select js.student_id, count(jes.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                        + "from journal j "
                        + "join journal_student js on js.journal_id = j.id "
                        + "join journal_entry je on je.journal_id = j.id "
                        + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                        + "join classifier c_grade on c_grade.code = jes.grade_code "
                        + "where c_grade.value in ('1', '2', '3', '4', '5') "
                        + "and je.entry_type_code in (:gradeTypes) "
                        + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                    + "left join (select scmor.student_id, count(scmor.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                        + "from student_curriculum_module_outcomes_result scmor "
                        + "join classifier c_grade on c_grade.code = scmor.grade_code "
                        + "where c_grade.value in ('1', '2', '3', '4', '5') "
                        + "and 'SISSEKANNE_O' in (:gradeTypes) "
                        + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                    + "where (sh.id is not null "
                        + "or "
                        + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                            + "and s.status_code in (:activeStatus)))" 
                        + "and s.school_id = :schoolId "
                        + "and sg.course = '4' "
                        + "and (c.is_higher = false "
                        + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                            + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                            + ")) "
                        + "and coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0) != 0) grades "
                    + "group by grades.course) aa on true "
                + "union "
                + "select 'report.studentSuccess.director.absenceSum' as header, aa.totalAbsences\\:\\:text as data, true as bold, 22 as orderNr "
                + "from (select sum(absences.absences) as totalAbsences "
                    + "from (select distinct on (s.id) s.id, journalStudent.absences as absences "
                        + "from student s "
                        + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                            + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                // data transer generated student history should be ignored
                                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                                + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                        + "left join curriculum_version cv on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                        + "left join curriculum c on cv.curriculum_id = c.id "
                        + "left join (select journalEntry.student_id, sum(journalEntry.absences) as absences "
                            + "from (select js.student_id, case when jes.is_lesson_absence = true then count(distinct jesla.id) else coalesce(je.lessons, 1) end as absences "
                                + "from journal j "
                                + "join journal_student js on js.journal_id = j.id "
                                + "join journal_entry je on je.journal_id = j.id "
                                + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                                + "left join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id and jes.is_lesson_absence = true "
                                + "where coalesce(jesla.absence_inserted, jes.absence_inserted, je.entry_date) >= :fromDate "
                                + "and coalesce(jesla.absence_inserted, jes.absence_inserted, je.entry_date) <= :thruDate "
                                + "and (jes.absence_code is not null or jesla.absence_code is not null) "
                                + "group by js.student_id, je.id, jes.id) journalEntry group by journalEntry.student_id"
                            + ") journalStudent on journalStudent.student_id = s.id "
                        + "where (sh.id is not null "
                            + "or "
                            + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                + "and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "'))" 
                            + "and s.school_id = :schoolId "
                            + "and (c.is_higher = false "
                            + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                + ")) "
                            + ") absences) aa "
                + "union "
                + "select 'report.studentSuccess.director.absenceSumPerStudent' as header, to_char(floor(aa.totalAbsences*100/aa.students)/100, 'FM999999990.09') as data, false as bold, 23 as orderNr "
                + "from (select sum(absences.absences) as totalAbsences, count(absences.id) as students "
                    + "from (select distinct on (s.id) s.id, journalStudent.absences as absences "
                    + "from student s "
                    + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                        + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                            // data transer generated student history should be ignored
                            + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                            + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                            + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                            + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                            + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                    + "left join curriculum_version cv on "
                        + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                        + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                    + "left join curriculum c on cv.curriculum_id = c.id "
                        + "left join (select journalEntry.student_id, sum(journalEntry.absences) as absences "
                            + "from (select js.student_id, case when jes.is_lesson_absence = true then count(distinct jesla.id) else coalesce(je.lessons, 1) end as absences "
                                + "from journal j "
                                + "join journal_student js on js.journal_id = j.id "
                                + "join journal_entry je on je.journal_id = j.id "
                                + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                                + "left join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id and jes.is_lesson_absence = true "
                                + "where coalesce(jesla.absence_inserted, jes.absence_inserted, je.entry_date) >= :fromDate "
                                + "and coalesce(jesla.absence_inserted, jes.absence_inserted, je.entry_date) <= :thruDate "
                                + "and (jes.absence_code is not null or jesla.absence_code is not null) "
                                + "group by js.student_id, je.id, jes.id) journalEntry group by journalEntry.student_id"
                            + ") journalStudent on journalStudent.student_id = s.id "
                    + "where (sh.id is not null "
                            + "or "
                            + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                + "and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "'))" 
                            + "and s.school_id = :schoolId "
                            + "and (c.is_higher = false "
                            + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                + ")) "
                        + ") absences) aa "
                + "union "
                + "select 'report.studentSuccess.director.absenceSumPerStudentGroup' as header, to_char(floor(aa.totalAbsences*100/aa.studentGroups)/100, 'FM999999990.09') as data, false as bold, 24 as orderNr "
                + "from (select sum(absences.absences) as totalAbsences, "
                    + "(select count(sg.id) "
                    + "from student_group sg "
                    + "left join curriculum c on c.id = sg.curriculum_id "
                    + "where exists(select 1 "
                        + "from student s "
                        + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                            + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                // data transer generated student history should be ignored
                                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                                + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                        + "left join curriculum_version cv on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                        + "left join curriculum c on cv.curriculum_id = c.id "
                        + "left join student_group sg1 on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.student_group_id else sh.student_group_id end = sg1.id "
                        +"where (sh.id is not null "
                            + "or "
                            + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                + "and s.status_code in (:activeStatus)))"
                            + "and s.school_id = :schoolId "
                            + "and (c.is_higher = false "
                            + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                + ")) "
                            + "and sg1.id = sg.id) "
                        + "and case when sg.is_guest != true then c.is_higher = false else true end "
                            + ") as studentGroups "
                    + "from (select distinct on (s.id) s.id, journalStudent.absences as absences "
                        + "from student s "
                        + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                            + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                // data transer generated student history should be ignored
                                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                                + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                        + "left join curriculum_version cv on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "') "
                            + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                        + "left join curriculum c on cv.curriculum_id = c.id "
                            + "left join (select journalEntry.student_id, sum(journalEntry.absences) as absences "
                                + "from (select js.student_id, case when jes.is_lesson_absence = true then count(distinct jesla.id) else coalesce(je.lessons, 1) end as absences "
                                    + "from journal j "
                                    + "join journal_student js on js.journal_id = j.id "
                                    + "join journal_entry je on je.journal_id = j.id "
                                    + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                                    + "left join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id and jes.is_lesson_absence = true "
                                    + "where coalesce(jesla.absence_inserted, jes.absence_inserted, je.entry_date) >= :fromDate "
                                    + "and coalesce(jesla.absence_inserted, jes.absence_inserted, je.entry_date) <= :thruDate "
                                    + "and (jes.absence_code is not null or jesla.absence_code is not null) "
                                    + "group by js.student_id, je.id, jes.id) journalEntry group by journalEntry.student_id"
                                + ") journalStudent on journalStudent.student_id = s.id "
                        + "where (sh.id is not null "
                                + "or "
                                + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                    + "and s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "'))" 
                                + "and s.school_id = :schoolId "
                                + "and (c.is_higher = false "
                                + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                    + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                    + ")) "
                            + ") absences) aa "
                + "union "
                + "select 'report.studentSuccess.director.tenBestStudentGroups' as header, null as data, true as bold, 25 as orderNr "
                + "union "
                + "select 'report.studentSuccess.director.tenBestStudentGroupsPerGrade' as header, string_agg(aa.studentGroupCode || ' ('  || aa.data || ')',  ' \n' order by aa.data desc) as data, false as bold, 26 as orderNr "
                + "from (select grades.studentGroupId, grades.studentGroupCode, to_char(floor(coalesce(sum(grades.averageGrade), 0)*100/coalesce(count(grades.averageGrade), 1))/100, 'FM999999990.09') as data "
                    + "from (select distinct on (s.id) s.id, floor((coalesce(journalStudent.addedGrades, 0) + coalesce(outcomes.addedGrades, 0)) * 100 / (coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0))) / 100 as averageGrade, sg.id as studentGroupId, sg.code as studentGroupCode "
                        + "from student s "
                        + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                            + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                // data transer generated student history should be ignored
                                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                                + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                        + "left join curriculum_version cv on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                        + "left join curriculum c on cv.curriculum_id = c.id "
                        + "left join student_group sg on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.student_group_id else sh.student_group_id end = sg.id "
                        + "left join (select js.student_id, count(jes.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                            + "from journal j "
                            + "join journal_student js on js.journal_id = j.id "
                            + "join journal_entry je on je.journal_id = j.id "
                            + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                            + "join classifier c_grade on c_grade.code = jes.grade_code "
                            + "where c_grade.value in ('1', '2', '3', '4', '5') "
                            + "and je.entry_type_code in (:gradeTypes) "
                            + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                        + "left join (select scmor.student_id, count(scmor.id) as amount, sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades "
                            + "from student_curriculum_module_outcomes_result scmor "
                            + "join classifier c_grade on c_grade.code = scmor.grade_code "
                            + "where c_grade.value in ('1', '2', '3', '4', '5') "
                            + "and 'SISSEKANNE_O' in (:gradeTypes) "
                            + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                        + "where (sh.id is not null "
                            + "or "
                            + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                + "and s.status_code in (:activeStatus)))" 
                            + "and s.school_id = :schoolId "
                            + "and (c.is_higher = false "
                            + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                + ")) "
                            + "and coalesce(journalStudent.amount, 0) + coalesce(outcomes.amount, 0) != 0) grades "
                        + "group by grades.studentGroupId, grades.studentGroupCode "
                        + "limit 10) aa "
                + "union "
                + "select 'report.studentSuccess.director.tenBestStudentGroupsPerDebt' as header, string_agg(aa.studentGroupCode || ' ('  || aa.data || ')',  ' \n') as data, false as bold, 27 as orderNr "
                + "from (select debts.studentGroupId, debts.studentGroupCode, to_char(floor(coalesce(sum(debts.debts), 0) * 100 / coalesce(count(debts.id), 1))/100, 'FM999999990.09') as data "
                    + "from (select distinct s.id, coalesce(svr.amount, 0) + coalesce(outcomes.amount, 0) + coalesce(journalStudent.amount, 0) as debts, sg.id as studentGroupId, sg.code as studentGroupCode "
                        + "from student s "
                        + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                            + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                // data transer generated student history should be ignored
                                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                                + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                        + "left join curriculum_version cv on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                        + "left join curriculum c on cv.curriculum_id = c.id "
                        + "left join student_group sg on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.student_group_id else sh.student_group_id end = sg.id "
                        + "left join (select svr.student_id, count(svr.id) as amount "
                            + "from student_vocational_result svr "
                            + "where svr.grade_code not in (:positiveGrades) "
                            + "and 'SISSEKANNE_M' in (:gradeTypes) "
                            + "group by svr.student_id) svr on svr.student_id = s.id "
                        + "left join (select js.student_id, count(jes.id) as amount "
                            + "from journal j "
                            + "join journal_student js on js.journal_id = j.id "
                            + "join journal_entry je on je.journal_id = j.id "
                            + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                            + "where jes.grade_code not in (:positiveGrades) "
                            + "and je.entry_type_code in (:gradeTypes) "
                            + "and jes.grade_code is not null "
                            + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                        + "left join (select scmor.student_id, count(scmor.id) as amount "
                            + "from student_curriculum_module_outcomes_result scmor "
                            + "where scmor.grade_code not in (:positiveGrades) "
                            + "and 'SISSEKANNE_O' in (:gradeTypes) "
                            + "group by scmor.student_id) outcomes on outcomes.student_id = s.id "
                        + "where (sh.id is not null "
                            + "or "
                            + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                + "and s.status_code in (:activeStatus)))" 
                            + "and s.school_id = :schoolId "
                            + "and (c.is_higher = false "
                            + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                + ")) "
                        + "group by s.id, outcomes.amount, journalStudent.amount, svr.amount, sg.id) debts "
                        + "group by debts.studentGroupId, debts.studentGroupCode "
                        + "order by floor(coalesce(sum(debts.debts), 0) * 100 / coalesce(count(debts.id), 1))/100 "
                        + "limit 10) aa "
                + "union "
                + "select 'report.studentSuccess.director.tenBestStudentGroupsPerAbsence' as header, string_agg(aa.studentGroupCode || ' ('  || aa.data || ')',  ' \n') as data, false as bold, 28 as orderNr "
                + "from (select absences.studentGroupId, absences.studentGroupCode, to_char(floor(coalesce(sum(absences.absences), 0) * 100 / coalesce(count(distinct absences.id), 1))/100, 'FM999999990.09') as data "
                    + "from (select distinct s.id, sum(journalStudent.absences) as absences, sg.id as studentGroupId, sg.code as studentGroupCode "
                        + "from student s "
                        + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                            + "where ((cast(sh.valid_from as date) <= :thruDate or sh.valid_from is null) "
                                // data transer generated student history should be ignored
                                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                                + "and (cast(sh.valid_thru as date) > :thruDate or sh.valid_thru is null) "
                                + "and (sh.study_start <= :thruDate or sh.study_start is null) "
                                + "and (sh.study_end > :thruDate or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                                + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                        + "left join curriculum_version cv on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                        + "left join curriculum c on cv.curriculum_id = c.id "
                        + "left join student_group sg on "
                            + "case when ((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) and s.status_code in (:activeStatus)) "
                            + "then s.student_group_id else sh.student_group_id end = sg.id "
                        + "left join (select js.student_id, case when jes.is_lesson_absence = true then count(distinct jesla.id) else coalesce(je.lessons, 1) end as absences "
                            + "from journal j "
                            + "join journal_student js on js.journal_id = j.id "
                            + "join journal_entry je on je.journal_id = j.id "
                            + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                            + "left join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id and jes.is_lesson_absence = true "
                            + "where (jes.absence_code is not null or jesla.absence_code is not null) "
                            + "and coalesce(jesla.absence_code, jes.absence_code) in (:absenceCodes) "
                            + "group by js.student_id, jes.id, je.id) journalStudent on journalStudent.student_id = s.id "
                        + "where (sh.id is not null "
                            + "or "
                            + "((s.study_start <= :thruDate or s.study_start is null) and (s.study_end > :thruDate or s.study_end is null) "
                                + "and s.status_code in (:activeStatus)))" 
                            + "and s.school_id = :schoolId "
                            + "and (c.is_higher = false "
                            + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                                + ")) "
                        + "group by s.id, sg.id) absences "
                        + "group by absences.studentGroupId, absences.studentGroupCode "
                        + "order by floor(coalesce(sum(absences.absences), 0) * 100 / coalesce(count(distinct absences.id), 1))/100 nulls first "
                        + "limit 10) aa ";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.sort("4");
        
        qb.parameter("schoolId", user.getSchoolId());
        qb.parameter("fromDate", criteria.getFrom());
        qb.parameter("thruDate", criteria.getThru());
        qb.parameter("sexMale", Sex.SUGU_M.name());
        qb.parameter("sexFemale", Sex.SUGU_N.name());
        qb.parameter("activeStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.parameter("positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        qb.parameter("gradeTypes", criteria.getGradeType());
        qb.parameter("absenceCodes", EnumUtil.toNameList(Absence.PUUDUMINE_P, Absence.PUUDUMINE_PR, Absence.PUUDUMINE_V));
        
        return JpaQueryUtil.pagingResult(qb, SELECT, em, new PageRequest(0, Integer.MAX_VALUE)).map(r -> {
            EducationalSuccessDirectorDto dto = new EducationalSuccessDirectorDto(r);
            return dto;
        });
    }

    private Page<Object> studentOrStudentGroupResult(HoisUserDetails user, EducationalSuccessCommand criteria, Pageable pageable) {
        if (Boolean.TRUE.equals(criteria.getPerGroup())) {
            return studentGroupResult(user, criteria, pageable);
        }
        return studentResult(user, criteria, pageable);
    }

    private Page<Object> studentResult(HoisUserDetails user, EducationalSuccessCommand criteria, Pageable pageable) {
        String SEARCH_FROM = "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :dateThru or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :dateThru or sh.valid_thru is null) "
                        + "and (sh.study_start <= :dateThru or sh.study_start is null) "
                        + "and (sh.study_end > :dateThru or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :dateThru or s.study_start is null) and (s.study_end > :dateThru or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :dateThru or s.study_start is null) and (s.study_end > :dateThru or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                + "join person p on p.id = s.person_id "
                + "left join (select svr.student_id, sum(case when 'SISSEKANNE_M' in (:gradeTypes) then 1 else 0 end) as amount, "
                + "sum(case when svr.grade_code in (:countableGrades) and 'SISSEKANNE_M' in (:gradeTypes) then 1 else 0 end) as countableGrades, "
                + "sum(case when svr.grade_code not in (:positiveGrades) and 'SISSEKANNE_M' in (:gradeTypes) then 1 else 0 end) as debts, "
                + "sum(case when svr.grade_code in (:positiveGrades) and 'SISSEKANNE_M' in (:gradeTypes) then 1 else 0 end) as positiveGrades, "
                + "sum(case when svr.grade_mark in (3, 4, 5) then svr.grade_mark * credits else 0 end) as multipliedGrades, "
                + "sum(case when svr.grade_mark in (3, 4, 5) then svr.credits else 0 end) as addedCredits "
                    + "from student_vocational_result svr "
                    + "where svr.grade_code is not null "
                    + (criteria.getFrom() != null ? "and svr.grade_date >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and svr.grade_date <= :dateThru " : "")
                    + "group by svr.student_id) svr on svr.student_id = s.id "
                + "left join (select js.student_id, count(jes.id) as amount, "
                + "sum(case when jes.grade_code in (:countableGrades) then 1 else 0 end) as countableGrades, "
                + "sum(case when jes.grade_code not in (:positiveGrades) then 1 else 0 end) as debts, "
                + "sum(case when jes.grade_code in (:positiveGrades) then 1 else 0 end) as positiveGrades, "
                + "string_agg(jes.grade_code, ', ') as gradeCodes "
                    + "from journal j "
                    + "join journal_student js on js.journal_id = j.id "
                    + "join journal_entry je on je.journal_id = j.id "
                    + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                    + "where jes.grade_code is not null "
                    + "and je.entry_type_code in (:gradeTypes) "
                    + (criteria.getFrom() != null ? "and coalesce(je.entry_date, jes.grade_inserted) >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and coalesce(je.entry_date, jes.grade_inserted) <= :dateThru " : "")
                    + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                + "left join (select scmor.student_id, count(scmor.id) as amount, "
                + "sum(case when scmor.grade_code in (:countableGrades) then 1 else 0 end) as countableGrades, "
                + "sum(case when scmor.grade_code not in (:positiveGrades) then 1 else 0 end) as debts, "
                + "sum(case when scmor.grade_code in (:positiveGrades) then 1 else 0 end) as positiveGrades, "
                + "string_agg(scmor.grade_code, ', ') as gradeCodes "
                    + "from student_curriculum_module_outcomes_result scmor "
                    + "where scmor.grade_code is not null "
                    + (criteria.getFrom() != null ? "and scmor.grade_date >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and scmor.grade_date <= :dateThru " : "")
                    + "group by scmor.student_id) outcomes on outcomes.student_id = s.id and 'SISSEKANNE_O' in (:gradeTypes)";
    
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("s.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("sg.id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroup());
        qb.optionalCriteria("c.id in (:curriculumIds)", "curriculumIds", criteria.getCurriculum());
        qb.optionalCriteria("sg.teacher_id in (:studentGroupTeachers)", "studentGroupTeachers", criteria.getStudentGroupTeacher());
        qb.filter("(c.is_higher = false "
                + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                + "))");
        qb.filter("(sh.id is not null "
                + "or "
                + "((s.study_start <= :dateThru or s.study_start is null) and (s.study_end > :dateThru or s.study_end is null) and s.status_code in (:activeStatus)))");
        qb.parameter("activeStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        if (criteria.getFrom() != null) {
            qb.parameter("dateFrom", criteria.getFrom());
        }
        if (criteria.getThru() != null) {
            qb.parameter("dateThru", criteria.getThru());
        }
        if (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty()) {
            qb.parameter("gradeTypes", criteria.getGradeType());
        }
        if (criteria.getCountableGrades() != null && !criteria.getCountableGrades().isEmpty()) {
            qb.parameter("countableGrades", criteria.getCountableGrades());
        }
        qb.parameter("positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        String SELECT = "s.id, p.firstname, p.lastname, sg.code, "
                + "(case when svr.countableGrades is not null then svr.countableGrades else 0 end + "
                    + "case when journalStudent.countableGrades is not null then journalStudent.countableGrades else 0 end + "
                    + "case when outcomes.countableGrades is not null then outcomes.countableGrades else 0 end) as countableGrades, "
                + "(case when svr.debts is not null then svr.debts else 0 end + "
                    + "case when journalStudent.debts is not null then journalStudent.debts else 0 end + "
                    + "case when outcomes.debts is not null then outcomes.debts else 0 end) as debts, "
                + "(case when svr.positiveGrades is not null then svr.positiveGrades else 0 end + "
                    + "case when journalStudent.positiveGrades is not null then journalStudent.positiveGrades else 0 end + "
                    + "case when outcomes.positiveGrades is not null then outcomes.positiveGrades else 0 end) as positiveGrades, "
                + "(case when svr.amount is not null then svr.amount else 0 end + "
                    + "case when journalStudent.amount is not null then journalStudent.amount else 0 end + "
                    + "case when outcomes.amount is not null then outcomes.amount else 0 end) as allGrades, "
                + "case when svr.addedCredits > 0 then floor(svr.multipliedGrades*100/svr.addedCredits)/100 else 0 end as weightedAverage, "
                + "journalStudent.gradeCodes as journalGradeCodes,"
                + "outcomes.gradeCodes";

        return JpaQueryUtil.pagingResult(qb, SELECT, em, pageable).map(r -> {
            EducationalSuccessStudentResultDto dto = new EducationalSuccessStudentResultDto(r);
            return dto;
        });
    }

    private Page<Object> studentGroupResult(HoisUserDetails user, EducationalSuccessCommand criteria,
            Pageable pageable) {
        String SEARCH_FROM = "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :dateThru or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :dateThru or sh.valid_thru is null) "
                        + "and (sh.study_start <= :dateThru or sh.study_start is null) "
                        + "and (sh.study_end > :dateThru or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :dateThru or s.study_start is null) and (s.study_end > :dateThru or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "join student_group sg on "
                    + "case when ((s.study_start <= :dateThru or s.study_start is null) and (s.study_end > :dateThru or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                + "join person p on p.id = s.person_id "
                + "left join (select svr.student_id, count(svr.id) as amount, "
                + "sum(case when svr.grade_code in (:countableGrades) then 1 else 0 end) as countableGrades, "
                + "sum(case when svr.grade_code not in (:positiveGrades) then 1 else 0 end) as debts, "
                + "sum(case when svr.grade_code in (:positiveGrades) then 1 else 0 end) as positiveGrades "
                    + "from student_vocational_result svr "
                    + "where svr.grade_code is not null "
                    + (criteria.getFrom() != null ? "and svr.grade_date >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and svr.grade_date <= :dateThru " : "")
                    + "group by svr.student_id) svr on svr.student_id = s.id and 'SISSEKANNE_M' in (:gradeTypes) "
                + "left join (select js.student_id, count(jes.id) as amount, "
                + "sum(case when jes.grade_code in (:countableGrades) then 1 else 0 end) as countableGrades, "
                + "sum(case when jes.grade_code not in (:positiveGrades) then 1 else 0 end) as debts, "
                + "sum(case when jes.grade_code in (:positiveGrades) then 1 else 0 end) as positiveGrades, "
                + "string_agg(jes.grade_code, ', ') as gradeCodes, "
                + "sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades, "
                + "sum(case when c_grade.value in ('1', '2', '3', '4', '5') then 1 else 0 end) as gradeAmount "
                    + "from journal j "
                    + "join journal_student js on js.journal_id = j.id "
                    + "join journal_entry je on je.journal_id = j.id "
                    + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                    + "join classifier c_grade on c_grade.code = jes.grade_code "
                    + "where jes.grade_code is not null "
                    + "and je.entry_type_code in (:gradeTypes) "
                    + (criteria.getFrom() != null ? "and coalesce(je.entry_date, jes.grade_inserted) >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and coalesce(je.entry_date, jes.grade_inserted) <= :dateThru " : "")
                    + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                + "left join (select scmor.student_id, count(scmor.id) as amount, "
                + "sum(case when scmor.grade_code in (:countableGrades) then 1 else 0 end) as countableGrades, "
                + "sum(case when scmor.grade_code not in (:positiveGrades) then 1 else 0 end) as debts, "
                + "sum(case when scmor.grade_code in (:positiveGrades) then 1 else 0 end) as positiveGrades, "
                + "string_agg(scmor.grade_code, ', ') as gradeCodes, "
                + "sum(case c_grade.value when '1' then 1 when '2' then 2 when '3' then 3 when '4' then 4 when '5' then 5 else 0 end) as addedGrades, "
                + "sum(case when c_grade.value in ('1', '2', '3', '4', '5') then 1 else 0 end) as gradeAmount "
                    + "from student_curriculum_module_outcomes_result scmor "
                    + "join classifier c_grade on c_grade.code = scmor.grade_code "
                    + "where scmor.grade_code is not null "
                    + (criteria.getFrom() != null ? "and scmor.grade_date >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and scmor.grade_date <= :dateThru " : "")
                    + "group by scmor.student_id) outcomes on outcomes.student_id = s.id and 'SISSEKANNE_O' in (:gradeTypes)";
    
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("s.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("sg.id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroup());
        qb.optionalCriteria("c.id in (:curriculumIds)", "curriculumIds", criteria.getCurriculum());
        qb.filter("(c.is_higher = false "
                    + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                    + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                    + "))");
        qb.optionalCriteria("sg.teacher_id in (:studentGroupTeachers)", "studentGroupTeachers", criteria.getStudentGroupTeacher());
        qb.filter("(sh.id is not null "
                + "or "
                + "((s.study_start <= :dateThru or s.study_start is null) and (s.study_end > :dateThru or s.study_end is null) and s.status_code in (:activeStatus)))");
        qb.parameter("activeStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        if (criteria.getFrom() != null) {
            qb.parameter("dateFrom", criteria.getFrom());
        }
        if (criteria.getThru() != null) {
            qb.parameter("dateThru", criteria.getThru());
        }
        if (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty()) {
            qb.parameter("gradeTypes", criteria.getGradeType());
        }
        if (criteria.getCountableGrades() != null && !criteria.getCountableGrades().isEmpty()) {
            qb.parameter("countableGrades", criteria.getCountableGrades());
        }
        qb.parameter("positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        String SELECT = "sg.id, sg.code, count(s.id), "
                + "sum(case when svr.countableGrades is not null then svr.countableGrades else 0 end + "
                    + "case when journalStudent.countableGrades is not null then journalStudent.countableGrades else 0 end + "
                    + "case when outcomes.countableGrades is not null then outcomes.countableGrades else 0 end) as countableGrades, "
                + "sum(case when svr.debts is not null then svr.debts else 0 end + "
                    + "case when journalStudent.debts is not null then journalStudent.debts else 0 end + "
                    + "case when outcomes.debts is not null then outcomes.debts else 0 end) as debts, "
                + "sum(case when svr.positiveGrades is not null then svr.positiveGrades else 0 end + "
                    + "case when journalStudent.positiveGrades is not null then journalStudent.positiveGrades else 0 end + "
                    + "case when outcomes.positiveGrades is not null then outcomes.positiveGrades else 0 end) as positiveGrades, "
                + "sum(case when svr.amount is not null then svr.amount else 0 end + "
                    + "case when journalStudent.amount is not null then journalStudent.amount else 0 end + "
                    + "case when outcomes.amount is not null then outcomes.amount else 0 end) as allGrades, "
                + "string_agg(journalStudent.gradeCodes, ', ') as journalGradeCodes,"
                + "string_agg(outcomes.gradeCodes, ', '), "
                + "floor(coalesce(sum(floor((coalesce(journalStudent.addedGrades, 0) + coalesce(outcomes.addedGrades, 0)) * 100 / case when (coalesce(journalStudent.gradeAmount, 0) + coalesce(outcomes.gradeAmount, 0)) = 0 then 1 else (coalesce(journalStudent.gradeAmount, 0) + coalesce(outcomes.gradeAmount, 0)) end) / 100), 0)*100"
                + "/ case when sum(case when (coalesce(journalStudent.addedGrades, 0) + coalesce(outcomes.addedGrades, 0)) > 0 then 1 else 0 end) > 0 then sum(case when (coalesce(journalStudent.addedGrades, 0) + coalesce(outcomes.addedGrades, 0)) > 0 then 1 else 0 end) else 1 end)/100"
                + " as averageGrade";
        qb.groupBy("sg.id, sg.code");

        return JpaQueryUtil.pagingResult(qb, SELECT, em, pageable).map(r -> {
            EducationalSuccessStudentGroupResultDto dto = new EducationalSuccessStudentGroupResultDto(r);
            return dto;
        });
    }

    private Page<Object> studentDebt(HoisUserDetails user, EducationalSuccessCommand criteria, Pageable pageable, boolean hasDebt) {
        String SEARCH_FROM = "from student s "
                + "left join (select distinct on (sh.student_id) sh.* from student_history sh "
                    + "where ((cast(sh.valid_from as date) <= :dateThru or sh.valid_from is null) "
                        // data transer generated student history should be ignored
                        + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                        + "and (cast(sh.valid_thru as date) > :dateThru or sh.valid_thru is null) "
                        + "and (sh.study_start <= :dateThru or sh.study_start is null) "
                        + "and (sh.study_end > :dateThru or sh.study_end is null) and sh.status_code in (:activeStatus)) "
                        + "order by sh.student_id, sh.valid_from) sh on s.id = sh.student_id "
                + "left join curriculum_version cv on "
                    + "case when ((s.study_start <= :dateThru or s.study_start is null) and (s.study_end > :dateThru or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.curriculum_version_id else sh.curriculum_version_id end = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join student_group sg on "
                    + "case when ((s.study_start <= :dateThru or s.study_start is null) and (s.study_end > :dateThru or s.study_end is null) and s.status_code in (:activeStatus)) "
                    + "then s.student_group_id else sh.student_group_id end = sg.id "
                + "join person p on p.id = s.person_id "
                + "left join (select svr.student_id, count(svr.id) as amount, string_agg(svr.id\\:\\:text, ', ') as ids "
                    + "from student_vocational_result svr "
                    + "where svr.grade_code not in (:positiveGrades) "
                    + (criteria.getFrom() != null ? "and svr.grade_date >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and svr.grade_date <= :dateThru " : "")
                    + (criteria.getTeacher() != null ? "and exists(select 1 from protocol_student ps "
                            + "join protocol_vdata pv on ps.protocol_id = pv.protocol_id "
                            + "where ps.id = svr.protocol_student_id and pv.teacher_id = :teacherId) " : "")
                    + (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty() ? "and 'SISSEKANNE_M' in (:gradeTypes) " : "")
                    + "group by svr.student_id) svr on svr.student_id = s.id "
                + "left join (select js.student_id, count(jes.id) as amount, string_agg(jes.id\\:\\:text, ', ') as ids "
                    + "from journal j "
                    + "join journal_student js on js.journal_id = j.id "
                    + "join journal_entry je on je.journal_id = j.id "
                    + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                    + "where jes.grade_code not in (:positiveGrades) "
                    + (criteria.getFrom() != null ? "and coalesce(je.entry_date, jes.grade_inserted) >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and coalesce(je.entry_date, jes.grade_inserted) <= :dateThru " : "")
                    + (criteria.getTeacher() != null ? "and exists(select 1 from journal_teacher jt where jt.teacher_id = :teacherId and jt.journal_id = j.id) " : "")
                    + (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty() ? "and je.entry_type_code in (:gradeTypes) " : "")
                    + "group by js.student_id) journalStudent on journalStudent.student_id = s.id "
                + "left join (select scmor.student_id, count(scmor.id) as amount, string_agg(scmor.id\\:\\:text, ', ') as ids "
                    + "from student_curriculum_module_outcomes_result scmor "
                    + "where scmor.grade_code not in (:positiveGrades) "
                    + (criteria.getFrom() != null ? "and scmor.grade_date >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and scmor.grade_date <= :dateThru " : "")
                    + (criteria.getTeacher() != null ? "and scmor.grade_inserted_teacher_id = :teacherId " : "")
                    + (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty() ? "and 'SISSEKANNE_O' in (:gradeTypes) " : "")
                    + "group by scmor.student_id) outcomes on outcomes.student_id = s.id";
    
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("s.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("sg.id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroup());
        qb.optionalCriteria("c.id in (:curriculumIds)", "curriculumIds", criteria.getCurriculum());
        qb.optionalCriteria("sg.teacher_id in (:studentGroupTeachers)", "studentGroupTeachers", criteria.getStudentGroupTeacher());
        qb.filter("(c.is_higher = false "
                + "or exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id "
                + "where ds.canceled != true and d.type_code = '" + DirectiveType.KASKKIRI_KYLALIS.name() + "' and ds.student_id = s.id and d.is_higher != true "
                + "))");
        qb.optionalCriteria("(case when svr.amount is not null then svr.amount else 0 end + "
                + "case when journalStudent.amount is not null then journalStudent.amount else 0 end + "
                + "case when outcomes.amount is not null then outcomes.amount else 0 end) "
                + criteria.getDebtSign() + " :debtAmount", "debtAmount", criteria.getDebt());
        qb.filter("(sh.id is not null "
                + "or "
                + "((s.study_start <= :dateThru or s.study_start is null) and (s.study_end > :dateThru or s.study_end is null) and s.status_code in (:activeStatus)))");
        qb.parameter("activeStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        if (hasDebt) {
            qb.filter("(svr.student_id is not null or journalStudent.student_id is not null or outcomes.student_id is not null)");
        } else {
            qb.filter("(svr.student_id is null and journalStudent.student_id is null and outcomes.student_id is null)");
        }
        qb.parameter("positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        if (criteria.getFrom() != null) {
            qb.parameter("dateFrom", criteria.getFrom());
        }
        if (criteria.getThru() != null) {
            qb.parameter("dateThru", criteria.getThru());
        }
        if (criteria.getTeacher() != null) {
            qb.parameter("teacherId", criteria.getTeacher());
        }
        if (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty()) {
            qb.parameter("gradeTypes", criteria.getGradeType());
        }
        String SELECT = "s.id as studentId, p.firstname, p.lastname, sg.id as groupId, sg.code, "
                + "(case when svr.amount is not null then svr.amount else 0 end + "
                + "case when journalStudent.amount is not null then journalStudent.amount else 0 end + "
                + "case when outcomes.amount is not null then outcomes.amount else 0 end) as amount, "
                + "svr.ids as moduleIds, journalStudent.ids as journalIds, outcomes.ids as outcomeIds";
        
        Page<Object> page = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable).map(r -> {
            EducationalSuccessDebtDto dto = new EducationalSuccessDebtDto(r);
            return dto;
        });
        List<Long> studentIds = page.getContent().stream().filter(p -> ((EducationalSuccessDebtDto)p).getStudent() != null)
                .map(p -> ((EducationalSuccessDebtDto)p).getStudent().getId()).collect(Collectors.toList());
        if (!studentIds.isEmpty()) {
            setStudentDebts(page, studentIds, criteria);
        }
        return page;
    }

    private void setStudentDebts(Page<Object> page, List<Long> studentIds, EducationalSuccessCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join (select svr.grade_date as gradeDate, c.name_et as studyYear, svr.module_name_et as nameEt, svr.module_name_en as nameEn, "
                + "svr.teachers as teachers, 'Moodul' as typeEt, 'Module' as typeEn, true as isModule, svr.grade as gradeValue, svr.student_id as studentId "
                    + "from student_vocational_result svr "
                    + "left join study_year sy on sy.id = svr.study_year_id "
                    + "left join classifier c on c.code = sy.year_code "
                    + "where svr.grade_code not in (:positiveGrades) "
                    + (criteria.getFrom() != null ? "and svr.grade_date >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and svr.grade_date <= :dateThru " : "")
                    + (criteria.getTeacher() != null ? "and exists(select 1 from protocol_student ps "
                            + "join protocol_vdata pv on ps.protocol_id = pv.protocol_id "
                            + "where ps.id = svr.protocol_student_id and pv.teacher_id = :teacherId) " : "")
                    + (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty() ? "and 'SISSEKANNE_M' in (:gradeTypes) " : "")
                    + ") modules on modules.studentId = s.id "
                + "where s.id in (:studentIds) "
                + "union "
                + "select coalesce(je.entry_date, jes.grade_inserted) as gradeDate, c.name_et as studyYear, j.name_et as nameEt, j.name_et as nameEn, "
                + "string_agg(p.firstname || ' ' || p.lastname, ', ') as teachers, entryType.name_et as typeEt, entryType.name_en as typeEn, false as isModule, "
                + "c_grade.value as gradeValue, js.student_id as studentId "
                    + "from journal j "
                    + "join journal_student js on js.journal_id = j.id "
                    + "join journal_entry je on je.journal_id = j.id "
                    + "join journal_entry_student jes on js.id = jes.journal_student_id and je.id = jes.journal_entry_id "
                    + "left join study_year sy on j.study_year_id = sy.id "
                    + "left join classifier c on c.code = sy.year_code "
                    + "left join journal_teacher jt on jt.journal_id = j.id "
                    + "left join teacher t on jt.teacher_id = t.id "
                    + "left join person p on t.person_id = p.id "
                    + "left join classifier entryType on entryType.code = je.entry_type_code "
                    + "left join classifier c_grade on c_grade.code = jes.grade_code "
                    + "where js.student_id in (:studentIds) "
                    + "and jes.grade_code not in (:positiveGrades) "
                    + (criteria.getFrom() != null ? "and coalesce(je.entry_date, jes.grade_inserted) >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and coalesce(je.entry_date, jes.grade_inserted) <= :dateThru " : "")
                    + (criteria.getTeacher() != null ? "and exists(select 1 from journal_teacher jt where jt.teacher_id = :teacherId and jt.journal_id = j.id) " : "")
                    + (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty() ? "and je.entry_type_code in (:gradeTypes) " : "")
                    + "group by js.student_id, coalesce(je.entry_date, jes.grade_inserted), c.name_et, j.name_et, entryType.name_et, entryType.name_en, false, "
                    + "c_grade.value, js.student_id "
                + "union "
                + "select scmor.grade_inserted as gradeDate, null as studyYear, cm.name_et as nameEt, cm.name_et as nameEn, p.firstname || ' ' || p.lastname as teachers, "
                + "'Õpiväljund' as typeEt, 'Outcome' as typeEn, false as isModule, c_grade.value as gradeValue, scmor.student_id as studentId "
                    + "from student_curriculum_module_outcomes_result scmor "
                    + "left join curriculum_module_outcomes cmo on cmo.id = scmor.curriculum_module_outcomes_id "
                    + "left join curriculum_module cm on cm.id = cmo.curriculum_module_id "
                    + "left join teacher t on t.id = scmor.grade_inserted_teacher_id "
                    + "left join person p on p.id = t.person_id "
                    + "left join classifier c_grade on c_grade.code = scmor.grade_code "
                    + "where scmor.grade_code not in (:positiveGrades) "
                    + (criteria.getFrom() != null ? "and scmor.grade_date >= :dateFrom " : "")
                    + (criteria.getThru() != null ? "and scmor.grade_date <= :dateThru " : "")
                    + (criteria.getTeacher() != null ? "and scmor.grade_inserted_teacher_id = :teacherId " : "")
                    + (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty() ? "and 'SISSEKANNE_O' in (:gradeTypes) " : "")
                    + "and scmor.student_id in (:studentIds)");

        qb.parameter("studentIds", studentIds);
        qb.parameter("positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        if (criteria.getFrom() != null) {
            qb.parameter("dateFrom", criteria.getFrom());
        }
        if (criteria.getThru() != null) {
            qb.parameter("dateThru", criteria.getThru());
        }
        if (criteria.getTeacher() != null) {
            qb.parameter("teacherId", criteria.getTeacher());
        }
        if (criteria.getGradeType() != null && !criteria.getGradeType().isEmpty()) {
            qb.parameter("gradeTypes", criteria.getGradeType());
        }
        List<?> data = qb
                .select("gradeDate, studyYear, nameEt, nameEn, teachers, typeEt, typeEn, isModule, gradeValue, studentId",
                        em)
                .getResultList();

        if (!data.isEmpty()) {
            Map<Long, List<EducationalSuccessDebtModule>> studentDebts = data.stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 9), Collectors.mapping(r -> {
                        EducationalSuccessDebtModule result = new EducationalSuccessDebtModule();
                        result.setGradeDate(resultAsLocalDate(r, 0));
                        result.setStudyYear(resultAsString(r, 1));
                        result.setModule(new AutocompleteResult(null, resultAsString(r, 2), resultAsString(r, 3)));
                        result.setTeachers(resultAsString(r, 4));
                        result.setResultType(new AutocompleteResult(null, resultAsString(r, 5), resultAsString(r, 6)));
                        result.setIsModule(resultAsBoolean(r, 7));
                        result.setGrade(resultAsString(r, 8));
                        return result;
                    }, Collectors.toList())));
            page.getContent().forEach(p -> ((EducationalSuccessDebtDto)p).setModules(studentDebts.get(((EducationalSuccessDebtDto)p).getStudent().getId())));
        }
    }

    public byte[] educationalSuccessAsExcel(HoisUserDetails user, EducationalSuccessCommand criteria,
            Pageable pageable) {
        List<Object> students = educationalSuccess(user, criteria, new PageRequest(0, Integer.MAX_VALUE, pageable.getSort())).getContent();
        Map<String, Object> data = new HashMap<>();
        SchoolService.SchoolType schoolType = schoolService.schoolType(user.getSchoolId());
        data.put("schoolType", schoolType);
        data.put("data", students);
        data.put("criteria", criteria);
        EducationalSuccess queryType = EducationalSuccess.valueOf(criteria.getQueryType());
        switch (queryType) {
            case EDUCATIONAL_SUCCESS_HAS_DEBT:
                return xlsService.generate("educationalsuccessdebt.xls", data);
            case EDUCATIONAL_SUCCESS_NO_DEBT:
                return xlsService.generate("educationalsuccessnodebt.xls", data);
            case EDUCATIONAL_SUCCESS_RESULTS:
                if (Boolean.TRUE.equals(criteria.getPerGroup())) {
                    return xlsService.generate("educationalsuccessstudentgroupresult.xls", data);
                }
                return xlsService.generate("educationalsuccessstudentresult.xls", data);
            case EDUCATIONAL_SUCCESS_STUDY_DIRECTOR_REPORT:
                return xlsService.generate("educationalsuccessdirector.xls", data);
            case EDUCATIONAL_SUCCESS_BEST_RESULTS:
                return xlsService.generate("educationalsuccessbestresults.xls", data);
        }
        return null;
    }

    public List<AutocompleteResult> students(Long schoolId, StudentAutocompleteCommand lookup) {
        List<?> data = autocompleteService.studentResults(schoolId, lookup);
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullnameAndIdcodeOptionalGuestForCertificate(resultAsString(r, 1), resultAsString(r, 2)
                    , null, resultAsString(r, 4), resultAsString(r, 5), null);
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

}
