package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.report.studentgroupteacher.NegativeResultsJournalReport;
import ee.hitsa.ois.report.studentgroupteacher.NegativeResultsReport;
import ee.hitsa.ois.report.studentgroupteacher.NegativeResultsStudentReport;
import ee.hitsa.ois.report.studentgroupteacher.ResultReport;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.report.StudentGroupTeacherCommand;
import ee.hitsa.ois.web.commandobject.student.StudentRemarkSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ModuleDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ModuleTypeDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ResultColumnDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentGroupTeacherDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentJournalEntryAbsenceDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentJournalEntryResultDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentJournalResultDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentModuleResultDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentResultColumnDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentResultDto;
import ee.hitsa.ois.web.dto.student.StudentRemarkDto;

@Transactional
@Service
public class StudentGroupTeacherReportService {

    @Autowired
    private EntityManager em;
    @Autowired
    private StudentRemarkService studentRemarkService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private XlsService xlsService;

    private static final List<String> NEGATIVE_GRADES = EnumUtil.toNameList(OccupationalGrade.KUTSEHINDAMINE_X,
            OccupationalGrade.KUTSEHINDAMINE_MA, OccupationalGrade.KUTSEHINDAMINE_1,
            OccupationalGrade.KUTSEHINDAMINE_2);

    public StudentGroupTeacherDto studentGroupTeacher(StudentGroupTeacherCommand criteria) {
        List<StudentDto> students = studentGroupStudents(criteria);
        Collection<ModuleTypeDto> moduleTypes = studentGroupModules(criteria, students);

        List<ModuleDto> modules = new ArrayList<>();
        for (ModuleTypeDto type : moduleTypes) {
            modules.addAll(type.getModules());
        }
        List<ResultColumnDto> resultColumns = resultColumns(criteria, modules);

        setStudentGroupStudentAbsences(criteria, students);
        setStudentGroupStudentResultColumns(criteria, students, resultColumns);
        if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
            setStudentGroupStudentRemarks(criteria, students);
            setStudentGroupStudentLessons(criteria, students);
            setStudentGroupStudentAverages(criteria, students);
        }

        StudentGroupTeacherDto dto = new StudentGroupTeacherDto();
        dto.getModuleTypes().addAll(moduleTypes);
        dto.setStudents(students);
        dto.setModules(modules);
        dto.setResultColumns(resultColumns);
        dto.setAverageGrade(studentAverage(criteria, students));
        dto.setShowModuleGrade(criteria.getModuleGrade());
        dto.setShowWeightedAverageGrade(criteria.getWeightedAverageGrade());
        dto.setShowOnlyModuleGrades(criteria.getOnlyModuleGrades());
        if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
            dto.setShowAverageGrade(criteria.getAverageGrade());
        }
        return dto;
    }

    private Collection<ModuleTypeDto> studentGroupModules(StudentGroupTeacherCommand criteria,
            List<StudentDto> students) {
        List<Long> studentIds = StreamUtil.toMappedList(s -> s.getId(), students);

        Set<Long> moduleIds = new HashSet<>();
        Set<Long> practiceModuleIds = new HashSet<>();
        Map<Long, List<AutocompleteResult>> journalsByModules = new HashMap<>();
        if (studentIds.size() > 0) {
            if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
                journalsByModules = studentGroupModuleJournals(criteria, studentIds);
                Set<Long> journalModuleIds = journalsByModules.keySet();
                moduleIds.addAll(journalModuleIds);

                practiceModuleIds = studentGroupModulePracticeModules(criteria, studentIds);
                moduleIds.addAll(practiceModuleIds);
            }

            Set<Long> gradedModuleIds = Boolean.TRUE.equals(criteria.getModuleGrade())
                    ? gradedModules(criteria, studentIds)
                    : new HashSet<>();
            moduleIds.addAll(gradedModuleIds);
        }

        Map<String, ModuleTypeDto> moduleTypes = new LinkedHashMap<>();
        if (moduleIds.isEmpty()) {
            return moduleTypes.values();
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from"
                + " (select cm.id, cm.name_et, cm.name_en, case when cm.id in"
                + " (select cm2.id from student_group sg "
                + " join curriculum_version cv on sg.curriculum_version_id = cv.id"
                + " join curriculum_version_omodule cvo on cv.id = cvo.curriculum_version_id"
                + " join curriculum_module cm2 on cvo.curriculum_module_id = cm2.id" + " where sg.id = :studentGroupId"
                + " and (coalesce(sg.speciality_code,'x')='x' or coalesce(sg.speciality_code,'x')!='x'"
                + " and exists(select 1 from curriculum_module_occupation cmo"
                + " left join classifier_connect ccc on cmo.occupation_code = ccc.connect_classifier_code"
                + " where cmo.curriculum_module_id = cm.id and"
                + " (cmo.occupation_code = sg.speciality_code or ccc.classifier_code=sg.speciality_code))))"
                + " then cm.module_code else upper('kutsemoodul_v') end as module_code,"
                + " cm.is_practice from curriculum_module cm" + " where cm.id in (:moduleIds)) modules");
        qb.parameter("moduleIds", moduleIds);
        qb.parameter("studentGroupId", criteria.getStudentGroup());
        qb.sort("case when lower(module_code) = 'kutsemoodul_p' then 1" + " when lower(module_code) = 'kutsemoodul_y'"
                + " then 2 when lower(module_code) = 'kutsemoodul_v' then 3" + " else 4 end, name_et, id");

        List<?> rows = qb.select("id, name_et, name_en, module_code, is_practice", em).getResultList();

        Map<Long, List<AutocompleteResult>> moduleThemesByModules = new HashMap<>();
        if (!practiceModuleIds.isEmpty()) {
            moduleThemesByModules = studentGroupModulePracticeModuleThemes(studentIds, practiceModuleIds);
        }

        for (Object r : rows) {
            String moduleCode = resultAsString(r, 3);
            ModuleTypeDto type = moduleTypes.get(moduleCode);
            if (type != null) {
                addModuleToType(criteria, r, type, journalsByModules, moduleThemesByModules);
            } else {
                type = new ModuleTypeDto();
                type.setCode(moduleCode);
                addModuleToType(criteria, r, type, journalsByModules, moduleThemesByModules);
                moduleTypes.put(moduleCode, type);
            }
        }
        for (ModuleTypeDto type : moduleTypes.values()) {
            Long colspan = type.getModules().stream().map(m -> m.getColspan()).reduce(Long.valueOf(0), Long::sum);
            type.setColspan(colspan.longValue() > 0 ? colspan : Long.valueOf(1));
        }
        return moduleTypes.values();
    }

    private static void addModuleToType(StudentGroupTeacherCommand criteria, Object row, ModuleTypeDto type,
            Map<Long, List<AutocompleteResult>> journalsByModules,
            Map<Long, List<AutocompleteResult>> moduleThemesByModules) {
        ModuleDto module = new ModuleDto();
        module.setId(resultAsLong(row, 0));
        module.setNameEt(resultAsString(row, 1));
        module.setNameEn(resultAsString(row, 2));
        module.setType(resultAsString(row, 3));
        module.setIsPracticeModule(resultAsBoolean(row, 4));

        if (journalsByModules.containsKey(module.getId())) {
            module.setJournals(journalsByModules.get(module.getId()));
        }
        if (moduleThemesByModules.containsKey(module.getId())) {
            module.setPracticeModuleThemes(moduleThemesByModules.get(module.getId()));
        }

        setModuleColspan(criteria, module);
        type.getModules().add(module);
    }

    private static void setModuleColspan(StudentGroupTeacherCommand criteria, ModuleDto module) {
        int count = 0;
        if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
            count += module.getJournals().size();
            count += module.getPracticeModuleThemes().size();
            count += Boolean.TRUE.equals(module.getIsPracticeModule()) ? 1 : 0;
        }
        count += Boolean.TRUE.equals(criteria.getModuleGrade()) ? 1 : 0;
        module.setColspan(Long.valueOf(count > 0 ? count : 1));
    }

    private Map<Long, List<AutocompleteResult>> studentGroupModuleJournals(StudentGroupTeacherCommand criteria,
            List<Long> studentIds) {
        String journalFrom = "from journal j" + " join journal_omodule_theme jot on jot.journal_id = j.id"
                + " join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id"
                + " join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id"
                + " join journal_student js on j.id = js.journal_id" + " join student s on js.student_id = s.id"
                + " join student_group sg on s.student_group_id = sg.id"
                + " join lesson_plan_module lpm on jot.lesson_plan_module_id=lpm.id"
                + " join lesson_plan lp on lpm.lesson_plan_id=lp.id";
        if (Boolean.TRUE.equals(criteria.getJournalsWithEntries())
                || Boolean.TRUE.equals(criteria.getNegativeResults())) {
            journalFrom += " join journal_entry je on j.id = je.journal_id"
                    + " join journal_entry_student jes on je.id = jes.journal_entry_id"
                    + " and js.id = jes.journal_student_id";
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(journalFrom);
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.filter("(lp.student_group_id=sg.id or j.id not in "
                + "(select jot2.journal_id from journal_omodule_theme jot2 "
                + "join lesson_plan_module lpm2 on jot2.lesson_plan_module_id = lpm2.id "
                + "join lesson_plan lp2 on lpm2.lesson_plan_id = lp2.id "
                + "where jot2.journal_id = j.id and lp2.student_group_id = sg.id))");
        qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());

        if (Boolean.TRUE.equals(criteria.getJournalsWithEntries())
                || Boolean.TRUE.equals(criteria.getNegativeResults())) {
            qb.optionalCriteria("je.entry_type_code in (:entryTypeCodes)", "entryTypeCodes", criteria.getEntryTypes());
        }

        if (Boolean.TRUE.equals(criteria.getJournalsWithEntries())) {
            qb.optionalCriteria("coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) >= :from", "from",
                    criteria.getFrom(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) <= :thru", "thru",
                    criteria.getThru(), DateUtils::lastMomentOfDay);

            if (criteria.getStudyPeriod() != null) {
                qb.filter("coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) >= :studyPeriodStart "
                        + "and coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) <= :studyPeriodEnd");
                qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
                qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
            }
            qb.filter("(jes.grade_code is not null" + (Boolean.TRUE.equals(criteria.getAbsencesPerJournals())
                    ? " or jes.absence_code is not null or jes.is_lesson_absence = true)"
                    : ")"));
        }

        if (Boolean.TRUE.equals(criteria.getNegativeResults())) {
            qb.optionalCriteria("jes.grade_code in (:negativeGrades)", "negativeGrades", NEGATIVE_GRADES);
        }

        qb.sort("name_et");
        List<?> data = qb.select("distinct cvo.curriculum_module_id, j.id, j.name_et", em).getResultList();

        if (data.isEmpty()) {
            return Collections.emptyMap();
        }
        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                        r -> new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 2)),
                        Collectors.toList())));
    }

    private Set<Long> studentGroupModulePracticeModules(StudentGroupTeacherCommand criteria, List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj"
                + " join practice_journal_module_subject pjms on pj.id = pjms.practice_journal_id"
                + " join curriculum_version_omodule cvo on pjms.curriculum_version_omodule_id = cvo.id"
                + " join student s on pj.student_id = s.id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.optionalCriteria("pj.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.filter("pj.grade_code is not null");

        if (Boolean.TRUE.equals(criteria.getJournalsWithEntries())) {
            qb.optionalCriteria("pj.grade_inserted >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("pj.grade_inserted <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);

            if (criteria.getStudyPeriod() != null) {
                qb.filter("pj.grade_inserted >= :studyPeriodStart and pj.grade_inserted <= :studyPeriodEnd");
                qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
                qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
            }
        }
        List<?> data = qb.select("cvo.curriculum_module_id", em).getResultList();

        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }

    private Map<Long, List<AutocompleteResult>> studentGroupModulePracticeModuleThemes(List<Long> studentIds,
            Set<Long> moduleIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj"
                + " join practice_journal_module_subject pjms on pj.id = pjms.practice_journal_id"
                + " join curriculum_version_omodule cvo on pjms.curriculum_version_omodule_id = cvo.id"
                + " join curriculum_version_omodule_theme cvot on pjms.curriculum_version_omodule_theme_id = cvot.id"
                + " join student s on pj.student_id = s.id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("cvo.curriculum_module_id in (:moduleIds)", "moduleIds", moduleIds);
        qb.filter("pj.grade_code is not null");

        qb.sort("name_et");
        List<?> data = qb.select("distinct cvo.curriculum_module_id, cvot.id, cvot.name_et", em).getResultList();

        if (data.isEmpty()) {
            return Collections.emptyMap();
        }
        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                        r -> new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 2)),
                        Collectors.toList())));
    }

    private Set<Long> gradedModules(StudentGroupTeacherCommand criteria, List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from student s" + " join student_vocational_result svr on s.id = svr.student_id"
                        + " join curriculum_version_omodule cvo on svr.curriculum_version_omodule_id = cvo.id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
            qb.optionalCriteria("svr.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
            qb.optionalCriteria("svr.grade_date >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("svr.grade_date <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);

            if (criteria.getStudyPeriod() != null) {
                qb.filter("svr.grade_date >= :studyPeriodStart and svr.grade_date <= :studyPeriodEnd");
                qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
                qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
            }
        }

        List<?> data = qb.select("cvo.curriculum_module_id", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }

    private static List<ResultColumnDto> resultColumns(StudentGroupTeacherCommand criteria, List<ModuleDto> modules) {
        List<ResultColumnDto> resultColumns = new ArrayList<>();
        for (ModuleDto module : modules) {
            if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
                for (AutocompleteResult journal : module.getJournals()) {
                    ResultColumnDto column = new ResultColumnDto();
                    column.setJournal(journal);
                    resultColumns.add(column);
                }

                if (Boolean.TRUE.equals(module.getIsPracticeModule())) {
                    for (AutocompleteResult theme : module.getPracticeModuleThemes()) {
                        ResultColumnDto column = new ResultColumnDto();
                        column.setPracticeModuleTheme(theme);
                        resultColumns.add(column);
                    }
                    ResultColumnDto column = new ResultColumnDto();
                    column.setFullPracticeModule(
                            new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn()));
                    resultColumns.add(column);
                }
            }

            if (Boolean.TRUE.equals(criteria.getModuleGrade())) {
                ResultColumnDto column = new ResultColumnDto();
                column.setModule(new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn()));
                column.setModuleType(module.getType());
                resultColumns.add(column);
            }
        }
        return resultColumns;
    }

    private static BigDecimal studentAverage(StudentGroupTeacherCommand criteria, List<StudentDto> students) {
        BigDecimal studentAverage = null;
        if (Boolean.TRUE.equals(criteria.getAverageGrade())) {
            studentAverage = BigDecimal.valueOf(students.stream().filter(s -> s.getAverageGrade() != null)
                    .mapToDouble(s -> s.getAverageGrade().doubleValue()).filter(ag -> ag != 0).average().orElse(0))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return studentAverage;
    }

    private List<StudentDto> studentGroupStudents(StudentGroupTeacherCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s" + " join person p on s.person_id = p.id"
                + " join student_group sg on s.student_group_id = sg.id"
                + " left join curriculum_version cv on s.curriculum_version_id = cv.id");
        qb.requiredCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalCriteria("s.id = :studentId", "studentId", criteria.getStudent());

        qb.sort("p.lastname, p.firstname");
        List<?> data = qb.select("s.id, p.firstname, p.lastname, s.status_code, cv.is_individual", em).getResultList();

        List<StudentDto> students = StreamUtil.toMappedList(r -> {
            StudentDto student = new StudentDto();
            student.setId(resultAsLong(r, 0));
            student.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            student.setStatus(resultAsString(r, 3));
            student.setIsIndividualCurriculum(resultAsBoolean(r, 4));
            return student;
        }, data);

        return students;
    }

    private void setStudentGroupStudentAverages(StudentGroupTeacherCommand criteria, List<StudentDto> students) {
        if (!students.isEmpty()) {
            Map<Long, BigDecimal> weightedAverages = new HashMap<>();
            if (Boolean.TRUE.equals(criteria.getWeightedAverageGrade())) {
                List<Long> studentIds = StreamUtil.toMappedList(s -> s.getId(), students);
                weightedAverages = studentWeightedAverages(studentIds);
            }

            for (StudentDto student : students) {
                if (Boolean.TRUE.equals(criteria.getAverageGrade())) {
                    double average = student.getResultColumns().stream().filter(c -> c.getJournalResult() != null)
                            .map(c -> c.getJournalResult()).flatMap(jr -> jr.getResults().stream())
                            .filter(r -> r.getGrade() != null)
                            .mapToInt(r -> OccupationalGrade.getGradeMark(r.getGrade())).filter(m -> m != 0).average()
                            .orElse(0);
                    student.setAverageGrade(BigDecimal.valueOf(average).setScale(2, BigDecimal.ROUND_HALF_UP));
                }

                if (Boolean.TRUE.equals(criteria.getWeightedAverageGrade())) {
                    student.setWeightedAverageGrade(weightedAverages.get(student.getId()));
                }
            }
        }
    }

    private Map<Long, BigDecimal> studentWeightedAverages(List<Long> studentIds) {
        List<?> data = em
                .createNativeQuery("select scc.student_id, scc.average_mark from student_curriculum_completion scc"
                        + " where scc.student_id in (:studentIds)")
                .setParameter("studentIds", studentIds).getResultList();
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsDecimal(r, 1), data);
    }

    private void setStudentGroupStudentRemarks(StudentGroupTeacherCommand criteria, List<StudentDto> students) {
        if (!students.isEmpty()) {
            List<Long> studentIds = StreamUtil.toMappedList(s -> s.getId(), students);
            StudyYear studyYear = criteria.getStudyYear() != null
                    ? em.getReference(StudyYear.class, criteria.getStudyYear())
                    : null;
            StudentRemarkSearchCommand remarkCriteria = new StudentRemarkSearchCommand();
            remarkCriteria.setStudents(studentIds);
            if (studyYear != null) {
                remarkCriteria.setStudyYearStart(studyYear.getStartDate());
                remarkCriteria.setStudyYearEnd(studyYear.getEndDate());
            }
            remarkCriteria.setFrom(criteria.getFrom());
            remarkCriteria.setThru(criteria.getThru());
            remarkCriteria.setShowJournalRemarks(Boolean.TRUE);
            Map<Long, List<StudentRemarkDto>> remarksByStudent = studentRemarkService.remarksByStudents(remarkCriteria);

            for (StudentDto student : students) {
                if (remarksByStudent.containsKey(student.getId())) {
                    student.setRemarks(remarksByStudent.get(student.getId()));
                }
            }
        }
    }

    private void setStudentGroupStudentAbsences(StudentGroupTeacherCommand criteria, List<StudentDto> students) {
        if (!students.isEmpty()) {
            List<Long> studentIds = StreamUtil.toMappedList(s -> s.getId(), students);
            Map<Long, List<StudentJournalEntryAbsenceDto>> absencesByStudent = studentGroupStudentAbsences(criteria,
                    studentIds);

            for (StudentDto student : students) {
                List<StudentJournalEntryAbsenceDto> studentAbsences = absencesByStudent.get(student.getId());
                if (studentAbsences != null) {
                    student.setAbsenceEntries(studentAbsences);
                }

                for (Absence absence : Absence.values()) {
                    student.getAbsenceTypeTotals().put(absence.name(),
                            absencesTypeCount(student.getAbsenceEntries(), absence));
                }
                setStudentAbsenceTotals(student);
            }
        }
    }

    private static void setStudentAbsenceTotals(StudentDto student) {
        Map<String, Long> studentAbsences = student.getAbsenceTypeTotals();
        Long absencesP = studentAbsences.get(Absence.PUUDUMINE_P.name());
        Long absencesV = studentAbsences.get(Absence.PUUDUMINE_V.name());
        Long absencesPR = studentAbsences.get(Absence.PUUDUMINE_PR.name());
        Long absencesH = studentAbsences.get(Absence.PUUDUMINE_H.name());
        student.setTotalAbsences(Long.valueOf((absencesP != null ? absencesP.longValue() : 0)
                + (absencesV != null ? absencesV.longValue() : 0) + (absencesPR != null ? absencesPR.longValue() : 0)));
        student.setWithoutReasonAbsences(Long.valueOf(absencesP != null ? absencesP.longValue() : 0));
        student.setWithReasonAbsences(Long.valueOf(
                (absencesV != null ? absencesV.longValue() : 0) + (absencesPR != null ? absencesPR.longValue() : 0)));
        student.setBeingLate(Long.valueOf(absencesH != null ? absencesH.longValue() : 0));
    }

    private Map<Long, List<StudentJournalEntryAbsenceDto>> studentGroupStudentAbsences(
            StudentGroupTeacherCommand criteria, List<Long> studentIds) {
        Map<Long, List<StudentJournalEntryAbsenceDto>> studentAbsences = new HashMap<>();

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join journal_student js on js.student_id = s.id "
                + "join journal_entry_student jes on js.id = jes.journal_student_id "
                + "left join journal_entry_student_lesson_absence jesla on jes.id = jesla.journal_entry_student_id "
                + "join journal_entry je on je.id = jes.journal_entry_id " + "join journal j on js.journal_id = j.id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.filter("(jes.absence_code is not null or jesla.absence_code is not null)");

        qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("coalesce(je.entry_date, jes.absence_inserted) >= :entryFrom", "entryFrom",
                criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("coalesce(je.entry_date, jes.absence_inserted) <= :entryThru", "entryThru",
                criteria.getThru(), DateUtils::lastMomentOfDay);

        if (criteria.getStudyPeriod() != null) {
            qb.filter("coalesce(je.entry_date, jes.absence_inserted) >= :studyPeriodStart "
                    + "and coalesce(je.entry_date, jes.absence_inserted) <= :studyPeriodEnd");
            qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
            qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
        }

        List<?> absences = qb
                .select("distinct s.id as student_id, j.id, j.name_et, jes.id as student_entry_id, je.entry_date, "
                        + "coalesce(jesla.absence_code, jes.absence_code) as absence_code, "
                        + "coalesce(jesla.absence_inserted, jes.absence_inserted) as absence_inserted, "
                        + "jesla.lesson_nr + coalesce(je.start_lesson_nr - 1, 0), je.lessons", em)
                .getResultList();
        if (!absences.isEmpty()) {
            studentAbsences = absences.stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                        StudentJournalEntryAbsenceDto absence = new StudentJournalEntryAbsenceDto();
                        absence.setJournal(
                                new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 2)));
                        absence.setStudentEntryId(resultAsLong(r, 3));
                        absence.setEntryDate(resultAsLocalDate(r, 4));
                        absence.setAbsence(resultAsString(r, 5));
                        absence.setAbsenceInserted(resultAsLocalDate(r, 6));
                        absence.setLessonNr(resultAsLong(r, 7));
                        absence.setLessons(resultAsLong(r, 8));
                        return absence;
                    }, Collectors.toList())));
        }
        return studentAbsences;
    }

    private void setStudentGroupStudentResultColumns(StudentGroupTeacherCommand criteria, List<StudentDto> students,
            List<ResultColumnDto> resultColumns) {
        if (!students.isEmpty() && !resultColumns.isEmpty()) {
            Map<Long, List<StudentResultColumnDto>> resultColumnsByStudent = studentGroupStudentResultColumns(criteria,
                    students, resultColumns);

            for (StudentDto student : students) {
                List<StudentResultColumnDto> studentResultColumns = resultColumnsByStudent.get(student.getId());
                student.setResultColumns(studentResultColumns);
            }
        }
    }

    private Map<Long, List<StudentResultColumnDto>> studentGroupStudentResultColumns(
            StudentGroupTeacherCommand criteria, List<StudentDto> students, List<ResultColumnDto> resultColumns) {
        List<Long> studentIds = StreamUtil.toMappedList(s -> s.getId(), students);
        Map<Long, List<StudentResultColumnDto>> studentResultColumns = emptyStudentResultColumns(studentIds,
                resultColumns);
        setStudentJournals(criteria, students, resultColumns, studentResultColumns);
        setStudentPracticeModuleResults(criteria, studentIds, resultColumns, studentResultColumns);
        setStudentModuleResults(criteria, studentIds, resultColumns, studentResultColumns);

        return studentResultColumns;
    }

    private static Map<Long, List<StudentResultColumnDto>> emptyStudentResultColumns(List<Long> studentIds,
            List<ResultColumnDto> resultColumns) {
        Map<Long, List<StudentResultColumnDto>> studentEmptyResultColumns = new HashMap<>();

        List<StudentResultColumnDto> emptyResultColumns = StreamUtil.toMappedList(c -> {
            StudentResultColumnDto column = new StudentResultColumnDto();
            if (c.getJournal() != null) {
                StudentJournalResultDto journalResult = new StudentJournalResultDto();
                journalResult.setId(c.getJournal().getId());
                column.setJournalResult(journalResult);
            } else if (c.getPracticeModuleTheme() != null) {
                StudentModuleResultDto practiceModuleThemeResult = new StudentModuleResultDto();
                practiceModuleThemeResult.setId(c.getPracticeModuleTheme().getId());
                column.setPracticeModuleThemeResult(practiceModuleThemeResult);
            } else if (c.getFullPracticeModule() != null) {
                StudentModuleResultDto practiceModuleResult = new StudentModuleResultDto();
                practiceModuleResult.setId(c.getFullPracticeModule().getId());
                column.setPracticeModuleResult(practiceModuleResult);
            } else if (c.getModule() != null) {
                StudentModuleResultDto moduleResult = new StudentModuleResultDto();
                moduleResult.setId(c.getModule().getId());
                column.setModuleResult(moduleResult);
            }
            column.setModuleType(c.getModuleType());
            return column;
        }, resultColumns);

        for (Long studentId : studentIds) {
            studentEmptyResultColumns.put(studentId, cloneEmptyResultColumns(emptyResultColumns));
        }
        return studentEmptyResultColumns;
    }

    private static List<StudentResultColumnDto> cloneEmptyResultColumns(
            List<StudentResultColumnDto> emptyResultColumns) {
        List<StudentResultColumnDto> clonedList = new ArrayList<>();
        for (StudentResultColumnDto column : emptyResultColumns) {
            clonedList.add(new StudentResultColumnDto(column));
        }
        return clonedList;
    }

    private void setStudentJournals(StudentGroupTeacherCommand criteria, List<StudentDto> students,
            List<ResultColumnDto> resultColumns, Map<Long, List<StudentResultColumnDto>> studentResultColumns) {
        List<Long> studentIds = StreamUtil.toMappedList(s -> s.getId(), students);
        List<Long> journalIds = resultColumns.stream().filter(c -> c.getJournal() != null)
                .map(c -> c.getJournal().getId()).collect(Collectors.toList());
        Map<Long, List<Long>> studentJournals = new HashMap<>();
        Map<Long, List<StudentJournalEntryResultDto>> studentJournalResults = new HashMap<>();

        if (!journalIds.isEmpty()) {
            studentJournals = studentJournals(studentIds, journalIds);
            studentJournalResults = journalResults(criteria, studentIds, journalIds);
        }
        if (!studentJournalResults.isEmpty()) {
            setStudentJournalSpecifics(criteria, students, studentResultColumns, studentJournals,
                    studentJournalResults);
        }
    }

    private Map<Long, List<Long>> studentJournals(List<Long> studentIds, List<Long> journalIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join journal_student js on js.student_id = s.id " + "join journal j on j.id = js.journal_id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalIds);

        List<?> data = qb.select("s.id, js.journal_id", em).getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toList())));
    }

    private Map<Long, List<StudentJournalEntryResultDto>> journalResults(StudentGroupTeacherCommand criteria,
            List<Long> studentIds, List<Long> journalIds) {
        Map<Long, List<StudentJournalEntryResultDto>> studentResults = new HashMap<>();

        if (criteria.getEntryTypes() != null) {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                    "from student s " + "join journal_student js on js.student_id = s.id "
                            + "join journal_entry_student jes on js.id = jes.journal_student_id "
                            + "join journal_entry je on je.id = jes.journal_entry_id "
                            + "join journal j on js.journal_id = j.id");
            qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
            qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalIds);

            qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
            qb.requiredCriteria("je.entry_type_code in (:entryTypeCodes)", "entryTypeCodes", criteria.getEntryTypes());

            qb.optionalCriteria("coalesce(je.entry_date, jes.grade_inserted) >= :entryFrom", "entryFrom",
                    criteria.getFrom(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("coalesce(je.entry_date, jes.grade_inserted) <= :entryThru", "entryThru",
                    criteria.getThru(), DateUtils::lastMomentOfDay);

            if (criteria.getStudyPeriod() != null) {
                qb.filter("coalesce(je.entry_date, jes.grade_inserted) >= :studyPeriodStart "
                        + "and coalesce(je.entry_date, jes.grade_inserted) <= :studyPeriodEnd");
                qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
                qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
            }

            qb.filter("jes.grade_code is not null");

            if (Boolean.TRUE.equals(criteria.getNegativeResults())) {
                qb.optionalCriteria("jes.grade_code in (:negativeGrades)", "negativeGrades", NEGATIVE_GRADES);
            }

            List<?> grades = qb.select(
                    "distinct s.id as student_id, j.id, j.name_et, jes.id as student_entry_id, je.entry_type_code, je.entry_date, "
                            + "jes.grade_code, jes.grade_inserted, "
                            + "coalesce(jes.grade_inserted_by, jes.changed_by, jes.inserted_by) as grade_inserted_by, "
                            + "jes.is_remark, jes.add_info",
                    em).getResultList();

            if (!grades.isEmpty()) {
                studentResults = grades.stream()
                        .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                            StudentJournalEntryResultDto result = new StudentJournalEntryResultDto();
                            result.setJournal(new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2),
                                    resultAsString(r, 2)));
                            result.setStudentEntryId(resultAsLong(r, 3));
                            result.setEntryType(resultAsString(r, 4));
                            result.setEntryDate(resultAsLocalDate(r, 5));
                            result.setGrade(resultAsString(r, 6));
                            result.setGradeInserted(resultAsLocalDate(r, 7));
                            result.setGradeInsertedBy(
                                    PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 8)));
                            if (Boolean.TRUE.equals(resultAsBoolean(r, 9))) {
                                result.setAddInfo(resultAsString(r, 10));
                            }
                            return result;
                        }, Collectors.toList())));
            }
        }
        return studentResults;
    }

    private static void setStudentJournalSpecifics(StudentGroupTeacherCommand criteria, List<StudentDto> students,
            Map<Long, List<StudentResultColumnDto>> resultColumns, Map<Long, List<Long>> studentJournals,
            Map<Long, List<StudentJournalEntryResultDto>> journalResults) {
        for (StudentDto student : students) {
            Long studentId = student.getId();
            List<Long> journals = studentJournals.containsKey(studentId) ? studentJournals.get(studentId)
                    : new ArrayList<>();
            List<StudentJournalEntryResultDto> results = journalResults.get(studentId);

            List<StudentResultColumnDto> journalColumns = StreamUtil.toFilteredList(r -> r.getJournalResult() != null,
                    resultColumns.get(studentId));
            for (StudentResultColumnDto journalColumn : journalColumns) {
                StudentJournalResultDto journal = journalColumn.getJournalResult();
                journal.setExistsInJournal(Boolean.valueOf(journals.contains(journal.getId())));
                journal.getResults().addAll(
                        StreamUtil.toFilteredList(e -> journal.getId().equals(e.getJournal().getId()), results));

                if (Boolean.TRUE.equals(criteria.getAbsencesPerJournals())) {
                    journal.getAbsences().addAll(StreamUtil.toFilteredList(
                            e -> journal.getId().equals(e.getJournal().getId()), student.getAbsenceEntries()));

                    if (!journal.getAbsences().isEmpty()) {
                        for (Absence absence : Absence.values()) {
                            Map<Object, List<StudentJournalEntryAbsenceDto>> absencesByDate = journal.getAbsences()
                                .stream().filter(e -> absence.name().equals(e.getAbsence()))
                                .sorted(Comparator.comparing(e -> e.getEntryDate(),
                                     Comparator.nullsLast(Comparator.reverseOrder())))
                                .collect(Collectors.groupingBy(e -> e.getEntryDate() == null ? "-" : e.getEntryDate(),
                                        LinkedHashMap::new, Collectors.toList()));
                            for (Object date : absencesByDate.keySet()) {
                                journal.getAbsencesByDate().get(absence.name()).put(date,
                                        absencesTypeCount(absencesByDate.get(date), absence));
                            }

                            journal.getAbsenceTotals().put(absence.name(), absencesTypeCount(journal.getAbsences(), absence));
                        }
                    }

                }
            }
        }
    }

    private static Long absencesTypeCount(List<StudentJournalEntryAbsenceDto> journalAbsences, Absence type) {
        List<StudentJournalEntryAbsenceDto> absences = StreamUtil
                .toFilteredList(a -> type.name().equals(a.getAbsence()), journalAbsences);
        return absencesCount(absences);
    }

    private static Long absencesCount(List<StudentJournalEntryAbsenceDto> journalAbsences) {
        long count = 0;
        for (StudentJournalEntryAbsenceDto absence : journalAbsences) {
            // lessonNr is not set when absence isn't inserted by lessons
            if (absence.getLessons() != null && absence.getLessonNr() == null) {
                count += absence.getLessons().longValue();
            } else {
                count++;
            }
        }
        return Long.valueOf(count);
    }

    private void setStudentPracticeModuleResults(StudentGroupTeacherCommand criteria, List<Long> studentIds,
            List<ResultColumnDto> resultColumns, Map<Long, List<StudentResultColumnDto>> studentResultColumns) {
        List<Long> practiceModuleIds = StreamUtil.toMappedList(m -> m.getFullPracticeModule().getId(),
                StreamUtil.toFilteredList(m -> m.getFullPracticeModule() != null, resultColumns));
        Map<Long, List<StudentResultDto>> studentPracticeModuleResults = new HashMap<>();

        if (!practiceModuleIds.isEmpty()) {
            studentPracticeModuleResults = studentPracticeModuleResults(criteria, studentIds, practiceModuleIds);
        }
        if (!studentResultColumns.isEmpty()) {
            for (Long studentId : studentResultColumns.keySet()) {
                List<StudentResultColumnDto> practiceThemeColumns = StreamUtil.toFilteredList(
                        r -> r.getPracticeModuleThemeResult() != null, studentResultColumns.get(studentId));
                for (StudentResultColumnDto column : practiceThemeColumns) {
                    StudentModuleResultDto practiceModuleThemeResult = column.getPracticeModuleThemeResult();
                    List<StudentResultDto> results = StreamUtil.toFilteredList(
                            r -> practiceModuleThemeResult.getId().equals(r.getModuleThemeId()),
                            studentPracticeModuleResults.get(studentId));
                    if (!results.isEmpty()) {
                        // if multiple grades, show latest
                        StudentResultDto result = results.get(0);
                        practiceModuleThemeResult.setGrade(result.getGrade());
                        practiceModuleThemeResult.setGradeInserted(result.getGradeInserted());
                    }
                }

                List<StudentResultColumnDto> fullPracticeColumns = StreamUtil
                        .toFilteredList(r -> r.getPracticeModuleResult() != null, studentResultColumns.get(studentId));
                for (StudentResultColumnDto column : fullPracticeColumns) {
                    StudentModuleResultDto fullPracticeModuleResult = column.getPracticeModuleResult();
                    List<StudentResultDto> results = StreamUtil
                            .toFilteredList(
                                    r -> fullPracticeModuleResult.getId().equals(r.getModuleId())
                                            && r.getModuleThemeId() == null,
                                    studentPracticeModuleResults.get(studentId));
                    if (!results.isEmpty()) {
                        // if multiple grades, show latest
                        StudentResultDto result = results.get(0);
                        fullPracticeModuleResult.setGrade(result.getGrade());
                        fullPracticeModuleResult.setGradeInserted(result.getGradeInserted());
                    }
                }
            }
        }
    }

    private Map<Long, List<StudentResultDto>> studentPracticeModuleResults(StudentGroupTeacherCommand criteria,
            List<Long> studentIds, List<Long> practiceModuleIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj"
                + " join practice_journal_module_subject pjms on pj.id = pjms.practice_journal_id"
                + " join curriculum_version_omodule cvo on pjms.curriculum_version_omodule_id = cvo.id"
                + " left join curriculum_version_omodule_theme cvot on pjms.curriculum_version_omodule_theme_id = cvot.id");

        qb.requiredCriteria("pj.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("cvo.curriculum_module_id in (:practiceModuleIds)", "practiceModuleIds", practiceModuleIds);

        qb.optionalCriteria("pj.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("pj.grade_inserted >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("pj.grade_inserted <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);

        if (criteria.getStudyPeriod() != null) {
            qb.filter("pj.grade_inserted >= :studyPeriodStart and pj.grade_inserted <= :studyPeriodEnd");
            qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
            qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
        }

        qb.filter("pj.grade_code is not null");
        qb.sort("pj.grade_inserted desc nulls last");
        List<?> data = qb
                .select("pj.student_id, cvo.curriculum_module_id, cvot.id theme_id, pj.grade_code, pj.grade_inserted",
                        em)
                .getResultList();

        Map<Long, List<StudentResultDto>> studentPracticeModuleResults = new HashMap<>();
        if (!data.isEmpty()) {
            studentPracticeModuleResults = data.stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                        StudentResultDto result = new StudentResultDto();
                        result.setModuleId(resultAsLong(r, 1));
                        result.setModuleThemeId(resultAsLong(r, 2));
                        result.setGrade(resultAsString(r, 3));
                        result.setGradeInserted(resultAsLocalDate(r, 4));
                        return result;
                    }, Collectors.toList())));
        }
        return studentPracticeModuleResults;
    }

    private void setStudentModuleResults(StudentGroupTeacherCommand criteria, List<Long> studentIds,
            List<ResultColumnDto> resultColumns, Map<Long, List<StudentResultColumnDto>> studentResultColumns) {
        List<Long> moduleIds = StreamUtil.toMappedList(c -> c.getModule().getId(),
                StreamUtil.toFilteredList(c -> c.getModule() != null, resultColumns));
        List<Long> curriculumFreeChoiceModuleIds = curriculumFreeChoiceModules(criteria.getStudentGroup());
        Map<Long, List<StudentResultDto>> studentModuleResults = new HashMap<>();

        if (!moduleIds.isEmpty()) {
            studentModuleResults = studentModuleResults(criteria, studentIds);
        }
        if (!studentResultColumns.isEmpty()) {
            for (Long studentId : studentResultColumns.keySet()) {
                List<StudentResultColumnDto> modules = StreamUtil.toFilteredList(r -> r.getModuleResult() != null,
                        studentResultColumns.get(studentId));

                for (StudentResultColumnDto column : modules) {
                    StudentModuleResultDto moduleResult = column.getModuleResult();
                    List<StudentResultDto> results = StreamUtil.toFilteredList(
                            r -> moduleResult.getId().equals(r.getModuleId()), studentModuleResults.get(studentId));
                    
                    if (moduleResult.getId().equals(Long.valueOf(6145))) {
                        System.out.println("test");
                    }
                    
                    if (!results.isEmpty()) {
                        // if multiple grades, show latest
                        StudentResultDto result = results.get(0);
                        moduleResult.setGrade(result.getGrade());
                        moduleResult.setGradeInserted(result.getGradeInserted());
                    } else if (CurriculumModuleType.KUTSEMOODUL_V.name().equals(column.getModuleType())
                            && !curriculumFreeChoiceModuleIds.contains(moduleResult.getId())) {
                        moduleResult.setItendedModule(Boolean.FALSE);
                    }
                }
            }
        }
    }

    private Map<Long, List<StudentResultDto>> studentModuleResults(StudentGroupTeacherCommand criteria,
            List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from student s" + " join student_vocational_result svr on s.id = svr.student_id"
                        + " join curriculum_version_omodule cvo on svr.curriculum_version_omodule_id = cvo.id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);

        if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
            qb.optionalCriteria("svr.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
            qb.optionalCriteria("svr.grade_date >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("svr.grade_date <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);

            if (criteria.getStudyPeriod() != null) {
                qb.filter("svr.grade_date >= :studyPeriodStart and svr.grade_date <= :studyPeriodEnd");
                qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
                qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
            }
        }

        qb.sort("svr.grade_date desc nulls last");
        List<?> data = qb.select(
                "s.id as student_id, cvo.curriculum_module_id as module_id," + " svr.grade_code, svr.grade_date", em)
                .getResultList();

        Map<Long, List<StudentResultDto>> studentModuleResults = new HashMap<>();
        if (!data.isEmpty()) {
            studentModuleResults = data.stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                        StudentResultDto result = new StudentResultDto();
                        result.setModuleId(resultAsLong(r, 1));
                        result.setGrade(resultAsString(r, 2));
                        result.setGradeInserted(resultAsLocalDate(r, 3));
                        return result;
                    }, Collectors.toList())));
        }
        return studentModuleResults;
    }

    private List<Long> curriculumFreeChoiceModules(Long studentGroupId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from student_group sg " + "join curriculum_version cv on cv.id = sg.curriculum_version_id "
                        + "join curriculum_version_omodule cvo on cvo.curriculum_version_id = cv.id "
                        + "join curriculum_module cm on cm.id = cvo.curriculum_module_id");
        qb.requiredCriteria("sg.id = :studentGroupId", "studentGroupId", studentGroupId);
        qb.requiredCriteria("cm.module_code = :moduleType", "moduleType", CurriculumModuleType.KUTSEMOODUL_V.name());

        List<?> data = qb.select("cm.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private void setStudentGroupStudentLessons(StudentGroupTeacherCommand criteria, List<StudentDto> students) {
        if (!students.isEmpty()) {
            List<Long> studentIds = StreamUtil.toMappedList(s -> s.getId(), students);

            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                    "from student s " + "join journal_student js on js.student_id = s.id "
                            + "join journal_entry_student jes on js.id = jes.journal_student_id "
                            + "join journal_entry je on je.id = jes.journal_entry_id "
                            + "join journal j on js.journal_id = j.id");
            qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);

            qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
            qb.optionalCriteria("je.entry_date >= :entryFrom", "entryFrom", criteria.getFrom(),
                    DateUtils::firstMomentOfDay);
            qb.optionalCriteria("je.entry_date <= :entryThru", "entryThru", criteria.getThru(),
                    DateUtils::lastMomentOfDay);

            if (criteria.getStudyPeriod() != null) {
                qb.filter("je.entry_date >= :studyPeriodStart and je.entry_date <= :studyPeriodEnd");
                qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
                qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
            }

            qb.groupBy("s.id");
            List<?> data = qb.select("s.id, sum(coalesce(je.lessons, 1))", em).getResultList();
            Map<Long, Long> enteredLessons = StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsLong(r, 1), data);

            for (StudentDto student : students) {
                student.setJournalEntryLessons(
                        enteredLessons.containsKey(student.getId()) ? enteredLessons.get(student.getId())
                                : Long.valueOf(0));
                if (!Long.valueOf(0).equals(student.getTotalAbsences())) {
                    double percentage = (double) student.getTotalAbsences().intValue()
                            / student.getJournalEntryLessons().intValue() * 100;
                    student.setLessonAbsencePercentage(
                            new BigDecimal(percentage).setScale(1, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
    }

    public byte[] studentGroupTeacherAsExcel(StudentGroupTeacherCommand criteria, ClassifierCache classifierCache) {
        StudentGroupTeacherDto dto = studentGroupTeacher(criteria);

        List<String> resultColumns = StreamUtil.toMappedList(
                rc -> ReportUtil.resultColumnAsString(rc, Boolean.TRUE, Language.ET), dto.getResultColumns());

        List<Map<String, Object>> students = StreamUtil.toMappedList(s -> {
            Map<String, Object> student = new HashMap<>();
            student.put("fullname", s.getFullname());
            student.put("status", s.getStatus());
            student.put("isIndividualCurriculum", s.getIsIndividualCurriculum());
            student.put("resultColumns",
                    StreamUtil.toMappedList(rc -> ReportUtil
                            .studentResultColumnAsString(criteria.getAbsencesPerJournals(), rc, classifierCache),
                            s.getResultColumns()));
            student.put("journalEntryLessons", s.getJournalEntryLessons());
            student.put("totalAbsences", s.getTotalAbsences());
            student.put("lessonAbsencePercentage", s.getLessonAbsencePercentage());
            student.put("withoutReasonAbsences", s.getWithoutReasonAbsences());
            student.put("withReasonAbsences", s.getWithReasonAbsences());
            student.put("beingLate", s.getBeingLate());
            student.put("averageGrade", s.getAverageGrade());
            student.put("weightedAverageGrade", s.getWeightedAverageGrade());
            return student;
        }, dto.getStudents());

        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("moduleTypes", dto.getModuleTypes());
        data.put("modules", dto.getModules());
        data.put("resultColumns", resultColumns);
        data.put("students", students);
        data.put("averageGrade", dto.getAverageGrade());

        String template = Boolean.TRUE.equals(criteria.getOnlyModuleGrades()) ? "student.group.teacher.modules.xls"
                : "student.group.teacher.xls";
        return xlsService.generate(template, data);
    }

    private List<ResultReport> negativeResults(StudentGroupTeacherCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s " + "join person p on p.id = s.person_id "
                + "join student_group sg on sg.id = s.student_group_id "
                + "join journal_student js on js.student_id = s.id " + "join journal j on j.id = js.journal_id "
                + "join journal_entry_student jes on jes.journal_student_id = js.id "
                + "join journal_entry je on je.id = jes.journal_entry_id");
        qb.requiredCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("je.entry_type_code in (:entryTypeCodes)", "entryTypeCodes", criteria.getEntryTypes());
        qb.optionalCriteria("jes.grade_code in (:negativeGrades)", "negativeGrades", NEGATIVE_GRADES);
        qb.optionalCriteria("je.entry_date >= :entryFrom", "entryFrom", criteria.getFrom(),
                DateUtils::firstMomentOfDay);
        qb.optionalCriteria("je.entry_date <= :entryThru", "entryThru", criteria.getThru(), DateUtils::lastMomentOfDay);

        if (criteria.getStudyPeriod() != null) {
            qb.filter("je.entry_date >= :studyPeriodStart and je.entry_date <= :studyPeriodEnd");
            qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
            qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
        }

        qb.sort("p.lastname, p.firstname, journal_name_et, jes.grade_inserted desc");
        List<?> data = qb
                .select("s.id, p.firstname, p.lastname, j.id journal_id, "
                        + "j.name_et journal_name_et, je.entry_type_code, jes.grade_code, jes.grade_inserted, "
                        + "coalesce(jes.grade_inserted_by, jes.changed_by, jes.inserted_by) as grade_inserted_by", em)
                .getResultList();

        return StreamUtil.toMappedList(r -> {
            ResultReport result = new ResultReport();
            result.setStudentId(resultAsLong(r, 0));
            result.setFirstname(resultAsString(r, 1));
            result.setLastname(resultAsString(r, 2));
            result.setJournal(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 4)));
            result.setEntryType(resultAsString(r, 5));
            result.setGrade(resultAsString(r, 6));
            result.setGradeInserted(resultAsLocalDate(r, 7));
            result.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 8)));
            return result;
        }, data);
    }

    public byte[] negativeResultsAsExcel(HoisUserDetails user, StudentGroupTeacherCommand criteria) {
        Map<String, Object> data = new HashMap<>();
        data.put("studentGroup",
                AutocompleteResult.of(em.getReference(StudentGroup.class, criteria.getStudentGroup())));
        data.put("rows", negativeResults(criteria));
        data.put("isHigherSchool", Boolean.valueOf(schoolService.schoolType(user.getSchoolId()).isHigher()));
        return xlsService.generate("student.group.teacher.negative.results.xls", data);
    }

    public NegativeResultsReport negativeResultsAsPdfData(StudentGroupTeacherCommand criteria,
            ClassifierCache classifierCache) {
        List<ResultReport> results = negativeResults(criteria);
        setNegativeResultClassifiers(results, classifierCache, Language.ET);

        List<NegativeResultsStudentReport> students = new ArrayList<>();
        Map<Long, List<ResultReport>> resultsByStudent = results.stream().collect(Collectors.groupingBy(
                r -> r.getStudentId(), LinkedHashMap::new, Collectors.mapping(r -> r, Collectors.toList())));

        for (Long studentId : resultsByStudent.keySet()) {
            List<ResultReport> studentResults = resultsByStudent.get(studentId);
            if (!studentResults.isEmpty()) {
                Map<Long, List<ResultReport>> studentResulstByJournals = StreamUtil.nullSafeList(studentResults)
                        .stream().collect(Collectors.groupingBy(r -> r.getJournal().getId(), LinkedHashMap::new,
                                Collectors.mapping(r -> r, Collectors.toList())));

                ResultReport studentData = studentResults.get(0);
                NegativeResultsStudentReport student = new NegativeResultsStudentReport();
                student.setFirstname(studentData.getFirstname());
                student.setLastname(studentData.getLastname());

                List<NegativeResultsJournalReport> studentJournals = new ArrayList<>();
                for (Long journalId : studentResulstByJournals.keySet()) {
                    NegativeResultsJournalReport journal = new NegativeResultsJournalReport();
                    journal.setName(studentResulstByJournals.get(journalId).get(0).getJournal().getNameEt());
                    journal.setResults(studentResulstByJournals.get(journalId));
                    studentJournals.add(journal);
                }
                student.setJournals(studentJournals);
                students.add(student);
            }
        }

        NegativeResultsReport report = new NegativeResultsReport();
        report.setStudentGroup(em.getReference(StudentGroup.class, criteria.getStudentGroup()).getCode());
        report.setStudents(students);
        return report;
    }

    private static void setNegativeResultClassifiers(List<ResultReport> results, ClassifierCache classifierCache,
            Language lang) {
        for (ResultReport result : results) {
            result.setGrade(ReportUtil.classifierValue(result.getGrade(), MainClassCode.KUTSEHINDAMINE.name(),
                    classifierCache));
            result.setEntryType(ReportUtil.classifierName(result.getEntryType(), MainClassCode.SISSEKANNE.name(),
                    classifierCache, lang));
        }
    }

}
