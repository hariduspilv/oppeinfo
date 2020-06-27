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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeResult;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentProgressDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalStudyProgrammeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.report.studentgroupteacher.NegativeResultsJournalReport;
import ee.hitsa.ois.report.studentgroupteacher.NegativeResultsReport;
import ee.hitsa.ois.report.studentgroupteacher.NegativeResultsStudentReport;
import ee.hitsa.ois.report.studentgroupteacher.ResultReport;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.DateUtils;
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
    private StudentService studentService;
    @Autowired
    private XlsService xlsService;

    public StudentGroupTeacherDto studentGroupTeacher(StudentGroupTeacherCommand criteria) {
        if (Boolean.TRUE.equals(criteria.getAllModules()) || Boolean.TRUE.equals(criteria.getAllModulesAndOutcomes())) {
            return studentGroupTeacherProgressReport(criteria);
        }
        criteriaChanges(criteria);
        return studentGroupTeacherReport(criteria);
    }

    private StudentGroupTeacherDto studentGroupTeacherReport(StudentGroupTeacherCommand criteria) {
        List<StudentDto> students = studentGroupStudents(criteria);
        Set<Long> studentIds = StreamUtil.toMappedSet(StudentDto::getId, students);

        List<ModuleDto> modules = new ArrayList<>();
        Collection<ModuleTypeDto> moduleTypes = Collections.emptyList();
        List<ResultColumnDto> resultColumns = new ArrayList<>();

        if (!studentIds.isEmpty()) {
            Set<Long> outcomeIds = new HashSet<>();
            Map<Long, List<StudentResultDto>> outcomeResults = new HashMap<>();
            if (Boolean.TRUE.equals(criteria.getOutcomeResults())) {
                outcomeResults = studentOutcomeResults(criteria, studentIds);
                outcomeIds.addAll(outcomeResults.values().stream().flatMap(Collection::stream)
                        .map(StudentResultDto::getOutcomeId).collect(Collectors.toSet()));
            }
            Map<Long, List<StudentResultDto>> moduleResults = new HashMap<>();
            if (Boolean.TRUE.equals(criteria.getModuleGrade())) {
                moduleResults = studentModuleResults(criteria, studentIds);
            }
            Set<Long> moduleIds = new HashSet<>(resultModuleIds(outcomeResults, moduleResults));

            modules = studentGroupModules(criteria, studentIds, moduleIds, outcomeIds);
            moduleTypes = moduleTypes(modules);
            resultColumns = resultColumns(criteria, modules);

            setStudentGroupStudentAbsences(criteria, students);
            setStudentGroupStudentResultColumns(criteria, students, resultColumns, outcomeResults, moduleResults);
            if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
                setStudentGroupStudentRemarks(criteria, students);
                setStudentGroupStudentLessons(criteria, students);
                setStudentGroupStudentAverages(criteria, students);
            }
        }

        StudentGroupTeacherDto dto = new StudentGroupTeacherDto();
        dto.getModuleTypes().addAll(moduleTypes);
        dto.setStudents(students);
        dto.setModules(modules);
        dto.setResultColumns(resultColumns);
        dto.setAverageGrade(studentAverage(criteria, students));
        dto.setShowModuleGrade(criteria.getModuleGrade());
        dto.setShowWeightedAverageGrade(criteria.getWeightedAverageGrade());
        if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
            dto.setShowAverageGrade(criteria.getAverageGrade());
        }
        dto.setShowModuleResultTable(criteria.getOnlyModuleGrades());
        dto.setShowStudentProgress(Boolean.FALSE);
        return dto;
    }

    private Set<Long> resultModuleIds(Map<Long, List<StudentResultDto>> outcomeResults,
        Map<Long, List<StudentResultDto>> moduleResults) {
        Stream<StudentResultDto> results = Stream.of(moduleResults.values().stream(), outcomeResults.values().stream())
                .flatMap(m -> m.flatMap(Collection::stream));
        return results.map(StudentResultDto::getModuleId).collect(Collectors.toSet());
    }

    private StudentGroupTeacherDto studentGroupTeacherProgressReport(StudentGroupTeacherCommand criteria) {
        List<StudentDto> students = studentGroupStudents(criteria);
        Set<Long> studentIds = StreamUtil.toMappedSet(StudentDto::getId, students);

        List<ModuleDto> modules = new ArrayList<>();
        Collection<ModuleTypeDto> moduleTypes = Collections.emptyList();
        List<ResultColumnDto> resultColumns = new ArrayList<>();

        if (!studentIds.isEmpty()) {
            Set<Long> moduleIds = new HashSet<>();
            Map<Long, List<StudentResultDto>> moduleResults = studentModuleResults(criteria, studentIds);
            Map<Long, List<StudentResultDto>> outcomeResults = studentOutcomeResults(criteria, studentIds);

            Stream<StudentResultDto> allResults = Stream.of(moduleResults.values().stream(), outcomeResults.values().stream())
                    .flatMap(m -> m.flatMap(Collection::stream));
            Set<Long> curriculumVersionModules = studentGroupCurriculumModules(criteria.getStudentGroup());

            moduleIds.addAll(allResults.map(StudentResultDto::getModuleId).collect(Collectors.toSet()));
            moduleIds.addAll(curriculumVersionModules);

            Map<Long, List<CurriculumModuleOutcomeResult>> outcomesByModules = new HashMap<>();
            if (!moduleIds.isEmpty()) {
                modules = curriculumModules(criteria.getStudentGroup(), moduleIds);
                if (Boolean.TRUE.equals(criteria.getAllModulesAndOutcomes())) {
                    outcomesByModules = progressReportOutcomes(moduleIds);
                }
                setModulesData(modules, new HashMap<>(), new HashMap<>(), new HashMap<>(), outcomesByModules,
                        criteria.getModuleGrade());
            }

            moduleTypes = moduleTypes(modules);
            resultColumns = resultColumns(criteria, modules);

            setStudentGroupStudentResultColumns(criteria, students, resultColumns, outcomeResults, moduleResults);
            setStudentProgress(students);
        }

        StudentGroupTeacherDto dto = new StudentGroupTeacherDto();
        dto.getModuleTypes().addAll(moduleTypes);
        dto.setModules(modules);
        dto.setStudents(students);
        dto.setResultColumns(resultColumns);
        dto.setShowModuleResultTable(Boolean.FALSE.equals(criteria.getAllModulesAndOutcomes()));
        dto.setShowStudentProgress(Boolean.TRUE);
        return dto;
    }

    private Set<Long> studentGroupCurriculumModules(Long studentGroupId) {
        List<?> data = em.createNativeQuery("select cm.id from student_group sg"
                + " join curriculum_module cm on sg.curriculum_id = cm.curriculum_id"
                + " where sg.id = :studentGroupId")
                .setParameter("studentGroupId", studentGroupId)
                .getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }

    private void setStudentProgress(List<StudentDto> students) {
        Set<Long> studentIds = StreamUtil.toMappedSet(StudentDto::getId, students);
        Map<Long, StudentVocationalStudyProgrammeDto> programmes = studentService.studyProgrammesWithEarnedCredits(
                studentIds, false);
        Set<Long> cumLaudes = studentService.cumLaudes(studentIds, false);

        List<?> data = em.createNativeQuery("select scc.student_id, (scc.study_backlog = 0 and scc.is_modules_ok = true) fulfilled, scc.average_mark" +
                " from student_curriculum_completion scc where scc.student_id in (:studentIds)")
                .setParameter("studentIds", studentIds)
                .getResultList();
        Map<Long, Boolean> curriculumFulfilled = StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsBoolean(r, 1), data);
        Map<Long, BigDecimal> weighedAverageMarks = StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsDecimal(r, 2), data);
        Set<Long> studentsThatHavePassedOccupationExam = studentsThatHavePassedOccupationExam(studentIds);

        for (StudentDto student : students) {
            StudentProgressDto progress = new StudentProgressDto();

            StudentVocationalStudyProgrammeDto studentProgramm = programmes.get(student.getId());
            if (studentProgramm != null) {
                progress.setMissingGeneralStudies(studentProgramm.getGeneralStudies().subtract(studentProgramm.getEarnedGeneralStudies()));
                progress.setMissingCoreStudies(studentProgramm.getCoreStudies().subtract(studentProgramm.getEarnedCoreStudies()));
                progress.setMissingFreeChoice(studentProgramm.getFreeChoice().subtract(studentProgramm.getEarnedFreeChoice()));
            }
            progress.setIsOccupationExamPassed(Boolean.valueOf(studentsThatHavePassedOccupationExam.contains(student.getId())));
            progress.setIsCumLaude(Boolean.valueOf(cumLaudes.contains(student.getId())));
            progress.setIsCurriculumFulfilled(curriculumFulfilled.get(student.getId()));
            progress.setWeightedAverageGrade(weighedAverageMarks.get(student.getId()));
            student.setProgress(progress);
        }
    }

    private Set<Long> studentsThatHavePassedOccupationExam(Set<Long> studentIds) {
        List<?> data = em.createNativeQuery("select ps.student_id from protocol_student ps" +
                " join protocol p on p.id = ps.protocol_id" +
                " where ps.student_id in (:studentIds) and p.status_code = :protocolStatus" +
                " and p.is_final = true and p.is_final_thesis = false and ps.grade_code in (:positiveGrades)")
                .setParameter("studentIds", studentIds)
                .setParameter("protocolStatus", ProtocolStatus.PROTOKOLL_STAATUS_K.name())
                .setParameter("positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE)
                .getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }

    private void criteriaChanges(StudentGroupTeacherCommand criteria) {
        // outcomes are not saved as entries anymore
        if (criteria.getEntryTypes().remove(JournalEntryType.SISSEKANNE_O.name())) {
            if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
                criteria.setOutcomeResults(Boolean.TRUE);
            }
        }
        StudyYear studyYear = criteria.getStudyYear() != null
                ? em.getReference(StudyYear.class, criteria.getStudyYear()) : null;
        criteria.setStudyYearObject(studyYear);
    }

    private List<ModuleDto> studentGroupModules(StudentGroupTeacherCommand criteria,
            Set<Long> studentIds, Set<Long> moduleIds, Set<Long> outcomeIds) {
        Map<Long, Boolean> practiceModules = new HashMap<>();
        Map<Long, List<AutocompleteResult>> journalsByModules = new HashMap<>();
        Map<Long, List<CurriculumModuleOutcomeResult>> outcomesByModules = new HashMap<>();
        if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades())) {
            journalsByModules = studentGroupModuleJournals(criteria, studentIds);
            Set<Long> journalModuleIds = journalsByModules.keySet();
            moduleIds.addAll(journalModuleIds);

            if (criteria.getEntryTypes().contains(JournalEntryType.SISSEKANNE_L.name())) {
                practiceModules = studentGroupModulePracticeModules(criteria, studentIds);
                moduleIds.addAll(practiceModules.keySet());
            }
        }
        if (Boolean.TRUE.equals(criteria.getOutcomeResults()) && !outcomeIds.isEmpty()) {
            outcomesByModules = studentGroupModuleOutcomes(outcomeIds);
        }

        if (moduleIds.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, List<AutocompleteResult>> moduleThemesByModules = new HashMap<>();
        if (!practiceModules.isEmpty()) {
            moduleThemesByModules = studentGroupModulePracticeModuleThemes(criteria, studentIds, practiceModules.keySet());
        }

        List<ModuleDto> modules = curriculumModules(criteria.getStudentGroup(), moduleIds);
        setModulesData(modules, practiceModules, journalsByModules, moduleThemesByModules,
                outcomesByModules, criteria.getModuleGrade());
        return modules;
    }

    private static void setModulesData(List<ModuleDto> modules, Map<Long, Boolean> practiceModules,
            Map<Long, List<AutocompleteResult>> journals, Map<Long, List<AutocompleteResult>> moduleThemes,
            Map<Long, List<CurriculumModuleOutcomeResult>> outcomes, Boolean isModuleGradeShown) {
        for (ModuleDto module : modules) {
            module.setIsPracticeModule(Boolean.valueOf(practiceModules.containsKey(module.getId())));
            module.setIsPracticeModuleGraded(Boolean.valueOf(module.getIsPracticeModule().booleanValue()
                    && practiceModules.get(module.getId()).booleanValue()));
            if (journals.containsKey(module.getId())) {
                module.setJournals(journals.get(module.getId()));
            }
            if (moduleThemes.containsKey(module.getId())) {
                module.setPracticeModuleThemes(moduleThemes.get(module.getId()));
            }
            if (outcomes.containsKey(module.getId())) {
                module.setOutcomes(outcomes.get(module.getId()));
            }
            setModuleColspan(module, isModuleGradeShown);
        }
    }

    private Collection<ModuleTypeDto> moduleTypes(List<ModuleDto> modules) {
        Map<String, ModuleTypeDto> moduleTypes = new LinkedHashMap<>();
        for (ModuleDto module : modules) {
            String moduleCode = module.getType();
            ModuleTypeDto type = moduleTypes.get(moduleCode);
            if (type == null) {
                type = new ModuleTypeDto();
                type.setCode(moduleCode);
                moduleTypes.put(moduleCode, type);
            }
            type.getModules().add(module);
        }

        for (ModuleTypeDto type : moduleTypes.values()) {
            Long colspan = type.getModules().stream().map(ModuleDto::getColspan).reduce(0L, Long::sum);
            type.setColspan(colspan.longValue() > 0 ? colspan : Long.valueOf(1));
        }
        return moduleTypes.values();
    }

    private List<ModuleDto> curriculumModules(Long studentGroupId, Set<Long> moduleIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from"
                + " (select cm.id, cm.name_et, cm.name_en, case when cm.id = group_curriculum.cm_id then cm.module_code"
                + " else '" + CurriculumModuleType.KUTSEMOODUL_V.name() + "' end module_code, cv_id,"
                + " cm.curriculum_id, sg.curriculum_id sg_curriculum_id"
                + " from curriculum_module cm"
                + " join student_group sg on sg.id = :studentGroupId"
                + " left join (select cm2.id cm_id, cv.id cv_id from student_group sg2"
                + " join curriculum_version cv on sg2.curriculum_version_id = cv.id"
                + " join curriculum_version_omodule cvo on cv.id = cvo.curriculum_version_id"
                + " join curriculum_module cm2 on cvo.curriculum_module_id = cm2.id"
                + " where sg2.id = :studentGroupId"
                + " and (coalesce(sg2.speciality_code, 'x') = 'x' or coalesce(sg2.speciality_code, 'x') != 'x'"
                + " and exists(select 1 from curriculum_module_occupation cmo"
                + " left join classifier_connect ccc on cmo.occupation_code = ccc.connect_classifier_code"
                + " where cmo.curriculum_module_id = cm2.id and"
                + " (cmo.occupation_code = sg2.speciality_code or ccc.classifier_code = sg2.speciality_code))))"
                + " group_curriculum on cm_id = cm.id) modules");
        qb.parameter("studentGroupId", studentGroupId);
        qb.sort("case when lower(module_code) = '" + CurriculumModuleType.KUTSEMOODUL_P.name().toLowerCase() + "' then 1"
                + " when lower(module_code) = '" + CurriculumModuleType.KUTSEMOODUL_Y.name().toLowerCase() + "' then 2"
                + " when lower(module_code) = '" + CurriculumModuleType.KUTSEMOODUL_V.name().toLowerCase() + "' then 3 else 4 end,"
                + " case when cv_id is not null then 1 when curriculum_id = sg_curriculum_id then 2 else 3 end,"
                + " name_et, id");

        qb.requiredCriteria("id in (:moduleIds)", "moduleIds", moduleIds);
        List<?> data = qb.select("id, name_et, name_en, module_code", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            ModuleDto module = new ModuleDto();
            module.setId(resultAsLong(r, 0));
            module.setNameEt(resultAsString(r, 1));
            module.setNameEn(resultAsString(r, 2));
            module.setType(resultAsString(r, 3));
            return module;
        }, data);
    }

    private static void setModuleColspan(ModuleDto module, Boolean isModuleGradeShown) {
        int count = 0;
        count += module.getJournals().size();
        count += module.getPracticeModuleThemes().size();
        count += Boolean.TRUE.equals(module.getIsPracticeModuleGraded()) ? 1 : 0;
        count += module.getOutcomes().size();
        count += Boolean.TRUE.equals(isModuleGradeShown) ? 1 : 0;
        module.setColspan(Long.valueOf(count > 0 ? count : 1));
    }

    private Map<Long, List<AutocompleteResult>> studentGroupModuleJournals(StudentGroupTeacherCommand criteria,
            Set<Long> studentIds) {
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
            qb.optionalCriteria("jes.grade_code not in (:positiveGrades)", "positiveGrades",
                    OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
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

    private Map<Long, Boolean> studentGroupModulePracticeModules(StudentGroupTeacherCommand criteria, Set<Long> studentIds) {
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
        List<?> data = qb.select("cvo.curriculum_module_id, case when pjms.curriculum_version_omodule_theme_id is null then true"
                + " else false end as full_module_graded", em).getResultList();
        Map<Long, Boolean> practiceModules = new HashMap<>();
        for (Object row : data) {
            Long moduleId = resultAsLong(row, 0);
            Boolean fullModuleGraded = resultAsBoolean(row, 1);
            if (!practiceModules.containsKey(moduleId)
                    || (Boolean.TRUE.equals(fullModuleGraded) && Boolean.FALSE.equals(practiceModules.get(moduleId)))) {
                practiceModules.put(moduleId, fullModuleGraded);
            }
        }
        return practiceModules;
    }

    private Map<Long, List<AutocompleteResult>> studentGroupModulePracticeModuleThemes(
            StudentGroupTeacherCommand criteria, Set<Long> studentIds, Set<Long> moduleIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj"
                + " join practice_journal_module_subject pjms on pj.id = pjms.practice_journal_id"
                + " join curriculum_version_omodule cvo on pjms.curriculum_version_omodule_id = cvo.id"
                + " join curriculum_version_omodule_theme cvot on pjms.curriculum_version_omodule_theme_id = cvot.id"
                + " join student s on pj.student_id = s.id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("cvo.curriculum_module_id in (:moduleIds)", "moduleIds", moduleIds);
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

    private Map<Long, List<CurriculumModuleOutcomeResult>> studentGroupModuleOutcomes(Set<Long> outcomeIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_module_outcomes cmo");
        qb.requiredCriteria("cmo.id in (:outcomeIds)", "outcomeIds", outcomeIds);

        qb.sort("cmo.order_nr");
        List<?> data = qb.select("distinct cmo.curriculum_module_id, cmo.id, cmo.outcome_et, cmo.outcome_en,"
                + " cmo.order_nr", em).getResultList();

        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new CurriculumModuleOutcomeResult(resultAsLong(r, 1), resultAsString(r, 2),
                        resultAsString(r, 3), resultAsLong(r, 4)), Collectors.toList())));
    }

    private List<ResultColumnDto> resultColumns(StudentGroupTeacherCommand criteria, List<ModuleDto> modules) {
        List<ResultColumnDto> resultColumns = new ArrayList<>();
        List<Long> curriculumFreeChoiceModuleIds = curriculumFreeChoiceModules(criteria.getStudentGroup());

        for (ModuleDto module : modules) {
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
                if (Boolean.TRUE.equals(module.getIsPracticeModuleGraded())) {
                    ResultColumnDto column = new ResultColumnDto();
                    column.setFullPracticeModule(
                            new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn()));
                    resultColumns.add(column);
                }
            }

            for (CurriculumModuleOutcomeResult outcome : module.getOutcomes()) {
                ResultColumnDto column = new ResultColumnDto();
                column.setOutcome(outcome);
                setIsColumnModuleIntended(module, column, curriculumFreeChoiceModuleIds);
                resultColumns.add(column);
            }

            if (Boolean.TRUE.equals(criteria.getModuleGrade())) {
                ResultColumnDto column = new ResultColumnDto();
                column.setModule(new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn()));
                setIsColumnModuleIntended(module, column, curriculumFreeChoiceModuleIds);
                resultColumns.add(column);
            }
        }
        return resultColumns;
    }

    private static void setIsColumnModuleIntended(ModuleDto module, ResultColumnDto column,
            List<Long> curriculumFreeChoiceModuleIds) {
        if (CurriculumModuleType.KUTSEMOODUL_V.name().equals(module.getType())
                && !curriculumFreeChoiceModuleIds.contains(module.getId())) {
            column.setIntendedModule(Boolean.FALSE);
        }
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s"
                + " join person p on s.person_id = p.id"
                + " join student_group sg on s.student_group_id = sg.id");
        qb.requiredCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalCriteria("s.id = :studentId", "studentId", criteria.getStudent());

        qb.sort("p.lastname, p.firstname");
        List<?> data = qb.select("s.id, p.firstname, p.lastname, s.status_code, s.type_code as studentType", em).getResultList();

        List<Long> studentIndividualCurriculums = studentIndividualCurriculums(criteria,
                StreamUtil.toMappedList(r -> resultAsLong(r, 0), data));

        return StreamUtil.toMappedList(r -> {
            StudentDto student = new StudentDto();
            Long studentId = resultAsLong(r, 0);
            student.setId(studentId);
            student.setFullname(PersonUtil.fullnameOptionalGuest(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 4)));
            student.setStatus(resultAsString(r, 3));
            student.setIsIndividualCurriculum(Boolean.valueOf(studentIndividualCurriculums.contains(studentId)));
            return student;
        }, data);
    }

    private List<Long> studentIndividualCurriculums(StudentGroupTeacherCommand criteria, List<Long> studentIds) {
        if (studentIds.isEmpty()) {
            return new ArrayList<>();
        }

        JpaNativeQueryBuilder qb = indokIndividualCurriculums(criteria, studentIds, criteria.getStudyYearObject());
        String indokQuery = qb.querySql("s.id", false);
        Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

        qb = tugiIndividualCurriculums(criteria, studentIds, criteria.getStudyYearObject());
        String tugiQuery = qb.querySql("s2.id", false);
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + indokQuery + " union all " + tugiQuery + ") as ic");
        List<?> data = qb.select("id", em, parameters).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private static JpaNativeQueryBuilder indokIndividualCurriculums(StudentGroupTeacherCommand criteria, List<Long> studentIds,
            StudyYear studyYear) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join directive_student ds on ds.student_id = s.id "
                + "join directive d on d.id = ds.directive_id "
                + "left join (directive_student ds_lop join directive d_lop on d_lop.id = ds_lop.directive_id "
                + "and d_lop.type_code = :lopDirectiveType and d_lop.status_code = :directiveStatus) "
                + "on ds_lop.directive_student_id = ds.id and ds_lop.canceled = false");

        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_INDOK);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus",
                DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.parameter("lopDirectiveType", DirectiveType.KASKKIRI_INDOKLOP.name());
        qb.filter("ds.canceled = false");

        if (studyYear != null) {
            qb.optionalCriteria("ds.start_date <= :studyYearEnd", "studyYearEnd", studyYear.getEndDate(),
                    DateUtils::firstMomentOfDay);
            qb.optionalCriteria("coalesce(ds_lop.start_date, ds.end_date) >= :studyYearStart", "studyYearStart",
                    studyYear.getStartDate(), DateUtils::lastMomentOfDay);
        } else if (criteria.getFrom() != null && criteria.getThru() != null) {
            qb.optionalCriteria("ds.start_date <= :thru", "thru", criteria.getThru(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("coalesce(ds_lop.start_date, ds.end_date) >= :from", "from", criteria.getFrom(),
                    DateUtils::lastMomentOfDay);
        } else {
            qb.optionalCriteria("ds.start_date <= :from and coalesce(ds_lop.start_date, ds.end_date) >= :from", "from",
                    criteria.getFrom());
        }
        return qb;
    }

    private static JpaNativeQueryBuilder tugiIndividualCurriculums(StudentGroupTeacherCommand criteria,
            List<Long> studentIds, StudyYear studyYear) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s2 "
                + "join directive_student ds2 on ds2.student_id = s2.id "
                + "join directive d2 on d2.id = ds2.directive_id "
                + "join application a on a.id = ds2.application_id "
                + "join application_support_service ass on ass.application_id = a.id "
                + "left join (directive_student ds_lop2 join directive d_lop2 on d_lop2.id = ds_lop2.directive_id "
                + "and d_lop2.type_code = :lopDirectiveType2 and d_lop2.status_code = :directiveStatus) "
                + "on ds_lop2.directive_student_id = ds2.id and ds_lop2.canceled = false");

        qb.requiredCriteria("s2.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("d2.type_code = :directiveType2", "directiveType2", DirectiveType.KASKKIRI_TUGI);
        qb.requiredCriteria("d2.status_code = :directiveStatus", "directiveStatus",
                DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.requiredCriteria("ass.support_service_code = :supportServiceCode", "supportServiceCode",
                SupportServiceType.TUGITEENUS_1);
        qb.parameter("lopDirectiveType2", DirectiveType.KASKKIRI_TUGILOPP.name());
        qb.filter("ds2.canceled = false");

        if (studyYear != null) {
            qb.optionalCriteria("ds2.start_date <= :studyYearEnd", "studyYearEnd", studyYear.getEndDate(),
                    DateUtils::firstMomentOfDay);
            qb.optionalCriteria("coalesce(ds_lop2.start_date, ds2.end_date) >= :studyYearStart", "studyYearStart",
                    studyYear.getStartDate(), DateUtils::lastMomentOfDay);
        } else if (criteria.getFrom() != null && criteria.getThru() != null) {
            qb.optionalCriteria("ds2.start_date <= :thru", "thru", criteria.getThru(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("coalesce(ds_lop2.start_date, ds2.end_date) >= :from", "from", criteria.getFrom(),
                    DateUtils::lastMomentOfDay);
        } else {
            qb.optionalCriteria("ds2.start_date <= :from and coalesce(ds_lop2.start_date, ds2.end_date) >= :from", "from",
                    criteria.getFrom());
        }
        return qb;
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
                    Stream<String> journalGrades = student.getResultColumns().stream()
                            .filter(c -> c.getJournalResult() != null)
                            .map(StudentResultColumnDto::getJournalResult)
                            .flatMap(jr -> jr.getResults().stream())
                            .filter(r -> r.getGrade() != null)
                            .map(StudentJournalEntryResultDto::getGrade);
                    Stream<String> outcomeGrades = student.getResultColumns().stream()
                            .filter(c -> c.getOutcomeResult() != null && c.getOutcomeResult().getGrade() != null)
                            .map(r -> r.getOutcomeResult().getGrade());
                    double average = Stream.concat(journalGrades, outcomeGrades)
                            .mapToInt(OccupationalGrade::getGradeMark)
                            .filter(m -> m != 0)
                            .average().orElse(0);
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
            StudentRemarkSearchCommand remarkCriteria = new StudentRemarkSearchCommand();
            remarkCriteria.setStudents(studentIds);
            if (criteria.getStudyYearObject() != null) {
                remarkCriteria.setStudyYearStart(criteria.getStudyYearObject().getStartDate());
                remarkCriteria.setStudyYearEnd(criteria.getStudyYearObject().getEndDate());
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
        qb.optionalCriteria("coalesce(je.entry_date, jes.absence_inserted, jesla.absence_inserted) >= :entryFrom", "entryFrom",
                criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("coalesce(je.entry_date, jes.absence_inserted, jesla.absence_inserted) <= :entryThru", "entryThru",
                criteria.getThru(), DateUtils::lastMomentOfDay);

        if (criteria.getStudyPeriod() != null) {
            qb.filter("coalesce(je.entry_date, jes.absence_inserted, jesla.absence_inserted) >= :studyPeriodStart "
                    + "and coalesce(je.entry_date, jes.absence_inserted, jesla.absence_inserted) <= :studyPeriodEnd");
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

    // outcome and module results are added differently to avoid running query two times
    private void setStudentGroupStudentResultColumns(StudentGroupTeacherCommand criteria, List<StudentDto> students,
            List<ResultColumnDto> resultColumns, Map<Long, List<StudentResultDto>> studentOutcomeResults,
            Map<Long, List<StudentResultDto>> moduleOutcomeResults) {
        if (!students.isEmpty() && !resultColumns.isEmpty()) {
            Set<Long> studentIds = StreamUtil.toMappedSet(StudentDto::getId, students);
            Map<Long, List<StudentResultColumnDto>> resultColumnsByStudent = emptyStudentResultColumns(studentIds,
                    resultColumns);

            setStudentJournals(criteria, students, resultColumns, resultColumnsByStudent);
            setStudentPracticeModuleResults(criteria, studentIds, resultColumns, resultColumnsByStudent);
            setStudentOutcomeResults(studentOutcomeResults, resultColumnsByStudent);
            setStudentModuleResults(moduleOutcomeResults, resultColumnsByStudent);

            for (StudentDto student : students) {
                List<StudentResultColumnDto> studentResultColumns = resultColumnsByStudent.get(student.getId());
                student.setResultColumns(studentResultColumns);
            }
        }
    }

    private static Map<Long, List<StudentResultColumnDto>> emptyStudentResultColumns(Set<Long> studentIds,
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
            } else if (c.getOutcome() != null) {
                StudentModuleResultDto outcomeResult = new StudentModuleResultDto();
                outcomeResult.setId(c.getOutcome().getId());
                column.setOutcomeResult(outcomeResult);
            } else if (c.getModule() != null) {
                StudentModuleResultDto moduleResult = new StudentModuleResultDto();
                moduleResult.setId(c.getModule().getId());
                column.setModuleResult(moduleResult);
            }
            column.setIntendedModule(c.getIntendedModule());
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
        Set<Long> studentIds = StreamUtil.toMappedSet(StudentDto::getId, students);
        Set<Long> journalIds = StreamUtil.nullSafeList(resultColumns).stream().filter(c -> c.getJournal() != null)
                .map(c -> c.getJournal().getId()).collect(Collectors.toSet());
        Map<Long, List<Long>> studentJournals = new HashMap<>();
        Map<Long, List<StudentJournalEntryResultDto>> studentJournalResults = new HashMap<>();

        if (!journalIds.isEmpty()) {
            studentJournals = studentJournals(studentIds, journalIds);
            studentJournalResults = journalResults(criteria, studentIds, journalIds);
        }
        setStudentJournalSpecifics(criteria, students, studentResultColumns, studentJournals,
                studentJournalResults);
    }

    private Map<Long, List<Long>> studentJournals(Set<Long> studentIds, Set<Long> journalIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join journal_student js on js.student_id = s.id " + "join journal j on j.id = js.journal_id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalIds);

        List<?> data = qb.select("s.id, js.journal_id", em).getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toList())));
    }

    private Map<Long, List<StudentJournalEntryResultDto>> journalResults(StudentGroupTeacherCommand criteria,
            Set<Long> studentIds, Set<Long> journalIds) {
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
            qb.optionalCriteria("je.entry_type_code in (:entryTypeCodes)", "entryTypeCodes", criteria.getEntryTypes());

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
                qb.optionalCriteria("jes.grade_code not in (:positiveGrades)", "positiveGrades",
                        OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
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
            List<StudentJournalEntryResultDto> results = journalResults.containsKey(studentId)
                    ? journalResults.get(studentId)
                    : new ArrayList<>();

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

    private void setStudentPracticeModuleResults(StudentGroupTeacherCommand criteria, Set<Long> studentIds,
            List<ResultColumnDto> resultColumns, Map<Long, List<StudentResultColumnDto>> studentResultColumns) {
        Set<Long> practiceModuleIds = StreamUtil.nullSafeList(resultColumns).stream()
                .filter(m -> m.getFullPracticeModule() != null).map(m -> m.getFullPracticeModule().getId())
                .collect(Collectors.toSet());
        Set<Long> practiceModuleThemeIds = StreamUtil.nullSafeList(resultColumns).stream()
                .filter(m -> m.getPracticeModuleTheme() != null).map(m -> m.getPracticeModuleTheme().getId())
                .collect(Collectors.toSet());
        Map<Long, List<StudentResultDto>> studentPracticeModuleResults = new HashMap<>();

        if (!practiceModuleIds.isEmpty() || !practiceModuleThemeIds.isEmpty()) {
            studentPracticeModuleResults = studentPracticeModuleResults(criteria, studentIds, practiceModuleIds,
                    practiceModuleThemeIds);
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
            Set<Long> studentIds, Set<Long> practiceModuleIds, Set<Long> practiceModuleThemeIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj"
                + " join practice_journal_module_subject pjms on pj.id = pjms.practice_journal_id"
                + " join curriculum_version_omodule cvo on pjms.curriculum_version_omodule_id = cvo.id"
                + " left join curriculum_version_omodule_theme cvot on pjms.curriculum_version_omodule_theme_id = cvot.id");

        qb.requiredCriteria("pj.student_id in (:studentIds)", "studentIds", studentIds);

        if (!practiceModuleIds.isEmpty() && !practiceModuleThemeIds.isEmpty()) {
            qb.filter("(cvo.curriculum_module_id in (:practiceModuleIds) or cvot.id in (:practiceModuleThemeIds))");
            qb.parameter("practiceModuleIds", practiceModuleIds);
            qb.parameter("practiceModuleThemeIds", practiceModuleThemeIds);
        } else if (!practiceModuleIds.isEmpty()) {
            qb.requiredCriteria("cvo.curriculum_module_id in (:practiceModuleIds)", "practiceModuleIds",
                    practiceModuleIds);
        } else {
            qb.requiredCriteria("cvot.id in (:practiceModuleThemeIds)", "practiceModuleThemeIds",
                    practiceModuleThemeIds);
        }

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

    private void setStudentOutcomeResults(Map<Long, List<StudentResultDto>> studentOutcomeResults,
            Map<Long, List<StudentResultColumnDto>> studentResultColumns) {
        if (!studentResultColumns.isEmpty()) {
            for (Long studentId : studentResultColumns.keySet()) {
                List<StudentResultColumnDto> outcomes = StreamUtil.toFilteredList(r -> r.getOutcomeResult() != null,
                        studentResultColumns.get(studentId));

                for (StudentResultColumnDto column : outcomes) {
                    StudentModuleResultDto outcomeResult = column.getOutcomeResult();
                    Optional<StudentResultDto> result = StreamUtil.nullSafeList(studentOutcomeResults.get(studentId)).stream()
                            .filter(r -> outcomeResult.getId().equals(r.getOutcomeId())).findFirst();

                    if (result.isPresent()) {
                        outcomeResult.setGrade(result.get().getGrade());
                        outcomeResult.setGradeInserted(result.get().getGradeInserted());
                        outcomeResult.setGradeInsertedBy(result.get().getGradeInsertedBy());
                    }
                }
            }
        }
    }

    private Map<Long, List<StudentResultDto>> studentOutcomeResults(StudentGroupTeacherCommand criteria,
            Set<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_curriculum_module_outcomes_result scmor"
                + " join curriculum_module_outcomes cmo on cmo.id = scmor.curriculum_module_outcomes_id"
                + " join student s on s.id = scmor.student_id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.filter("scmor.grade_code is not null");

        if (criteria.getStudyYearObject() != null) {
            qb.optionalCriteria("scmor.grade_date >= :syFrom", "syFrom", criteria.getStudyYearObject().getStartDate(),
                    DateUtils::firstMomentOfDay);
            qb.optionalCriteria("scmor.grade_date <= :syThru", "syThru", criteria.getStudyYearObject().getEndDate(),
                    DateUtils::lastMomentOfDay);
        }

        qb.optionalCriteria("scmor.grade_date >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("scmor.grade_date <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);

        if (criteria.getStudyPeriod() != null) {
            qb.filter("scmor.grade_date >= :studyPeriodStart and scmor.grade_date <= :studyPeriodEnd");
            qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
            qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
        }

        if (Boolean.TRUE.equals(criteria.getNegativeResults()) && Boolean.FALSE.equals(criteria.getAllModulesAndOutcomes())) {
            qb.optionalCriteria("scmor.grade_code not in (:positiveGrades)", "positiveGrades",
                    OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        }

        qb.sort("scmor.grade_date desc");
        List<?> data = qb.select("scmor.student_id, cmo.curriculum_module_id, scmor.curriculum_module_outcomes_id,"
                + " scmor.grade_code, scmor.grade_date, scmor.grade_inserted_by", em).getResultList();

        Map<Long, List<StudentResultDto>> studentOutcomeResults = new HashMap<>();
        if (!data.isEmpty()) {
            studentOutcomeResults = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> {
                        StudentResultDto result = new StudentResultDto();
                        result.setModuleId(resultAsLong(r, 1));
                        result.setOutcomeId(resultAsLong(r, 2));
                        result.setGrade(resultAsString(r, 3));
                        result.setGradeInserted(resultAsLocalDate(r, 4));
                        result.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 5)));
                        return result;
                    }, Collectors.toList())));
        }
        return studentOutcomeResults;
    }


    private void setStudentModuleResults(Map<Long, List<StudentResultDto>> studentModuleResults,
            Map<Long, List<StudentResultColumnDto>> studentResultColumns) {
        if (!studentResultColumns.isEmpty()) {
            for (Long studentId : studentResultColumns.keySet()) {
                List<StudentResultColumnDto> modules = StreamUtil.toFilteredList(r -> r.getModuleResult() != null,
                        studentResultColumns.get(studentId));

                for (StudentResultColumnDto column : modules) {
                    StudentModuleResultDto moduleResult = column.getModuleResult();
                    List<StudentResultDto> results = StreamUtil.toFilteredList(
                            r -> moduleResult.getId().equals(r.getModuleId()), studentModuleResults.get(studentId));

                    if (!results.isEmpty()) {
                        // if multiple grades, show latest
                        StudentResultDto result = results.get(0);
                        moduleResult.setGrade(result.getGrade());
                        moduleResult.setGradeInserted(result.getGradeInserted());
                    }
                }
            }
        }
    }

    private Map<Long, List<StudentResultDto>> studentModuleResults(StudentGroupTeacherCommand criteria,
            Set<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s"
                + " join student_vocational_result svr on s.id = svr.student_id"
                + " join curriculum_version_omodule cvo on svr.curriculum_version_omodule_id = cvo.id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);

        if (Boolean.FALSE.equals(criteria.getOnlyModuleGrades()) || Boolean.TRUE.equals(criteria.getAllModules())) {
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
        List<?> data = qb.select("s.id as student_id, cvo.curriculum_module_id as module_id,"
                + " svr.grade_code, svr.grade_date", em).getResultList();

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

            if (s.getProgress() != null) {
                Map<String, Object> progress = new HashMap<>();
                progress.put("isCurriculumFulfilled", s.getProgress().getIsCurriculumFulfilled());
                progress.put("isCumLaude", s.getProgress().getIsCumLaude());
                progress.put("weightedAverageGrade", s.getProgress().getWeightedAverageGrade());
                student.put("progress", progress);
            }
            return student;
        }, dto.getStudents());

        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("moduleTypes", dto.getModuleTypes());
        data.put("modules", dto.getModules());
        data.put("resultColumns", resultColumns);
        data.put("students", students);
        data.put("averageGrade", dto.getAverageGrade());
        data.put("studentProgress", dto.getShowStudentProgress());

        String template;
        if (Boolean.TRUE.equals(dto.getShowModuleResultTable())) {
            template = "student.group.teacher.modules.xls";
        } else {
            template = Boolean.TRUE.equals(dto.getShowStudentProgress()) ? "student.group.teacher.progress.xls"
                    : "student.group.teacher.xls";
        }
        return xlsService.generate(template, data);
    }

    private List<ResultReport> negativeResults(StudentGroupTeacherCommand criteria) {
        criteriaChanges(criteria);
        Map<String, Object> queryParameters = new HashMap<>();
        JpaNativeQueryBuilder qb;

        String journalResults = null;
        if (criteria.getEntryTypes() != null) {
            qb = negativeJournalResultsQb(criteria);
            journalResults = qb.querySql("s.id student_id, p.firstname, p.lastname, j.id journal_id, "
                    + "j.name_et journal_name_et, j.name_et journal_name_en, je.entry_type_code, jes.grade_code, jes.grade_inserted, "
                    + "coalesce(jes.grade_inserted_by, jes.changed_by, jes.inserted_by) as grade_inserted_by, "
                    + "false as is_practice_journal", false);
            queryParameters.putAll(qb.queryParameters());
        }

        qb = negativePracticeJournalResultsQb(criteria);
        String practicejournalResults = qb.querySql("s2.id student_id, p2.firstname, p2.lastname, pj.id journal_id, "
                + "cm.name_et || ' - ' || mcl.name_et || ' (' || cv.code || ')' || coalesce(' ' || cvot.name_et, '') journal_name_et, "
                + "cm.name_en || ' - ' || mcl.name_en || ' (' || cv.code || ')' || coalesce(' ' || cvot.name_et, '') journal_name_en, "
                + "null entry_type_code, pj.grade_code, pj.grade_inserted, tp.firstname || ' ' || tp.lastname grade_inserted_by, "
                + "true as is_practice_journal", false);
        queryParameters.putAll(qb.queryParameters());

        String outcomeResults = null;
        if (Boolean.TRUE.equals(criteria.getOutcomeResults())) {
            qb = negativeOutcomeResultsQb(criteria);
            outcomeResults = qb.querySql("s3.id student_id, p3.firstname, p3.lastname, cmo.id outcome_id, cmo.outcome_et, "
                    + "cmo.outcome_en, '" + JournalEntryType.SISSEKANNE_O + "' entry_type_code, scmor.grade_code, "
                    + "scmor.grade_date grade_inserted_by, tp2.firstname || ' ' || tp2.lastname, "
                    + "false as is_practice_journal", false);
            queryParameters.putAll(qb.queryParameters());
        }

        qb = new JpaNativeQueryBuilder("from ("
                + (journalResults != null ? journalResults + " union all " : "")
                + (outcomeResults != null ? outcomeResults + " union all " : "")
                + "select student_id, firstname, lastname, journal_id, "
                + "string_agg(journal_name_et, ', ') journal_name_et, string_agg(journal_name_en, ' ') journal_name_en, "
                + "null entry_type_code, grade_code, grade_inserted, grade_inserted_by, is_practice_journal "
                + "from (" + practicejournalResults + " order by journal_name_et, journal_name_en) as pjr "
                + "group by student_id, firstname, lastname, journal_id, grade_code, grade_inserted, "
                + "grade_inserted_by, is_practice_journal) as results");

        qb.sort("lastname, firstname, journal_name_et, journal_name_en, grade_inserted desc");
        List<?> data = qb.select("student_id, firstname, lastname, journal_id, "
                + "journal_name_et, journal_name_en, entry_type_code, grade_code, grade_inserted, grade_inserted_by, "
                + "is_practice_journal", em, queryParameters).getResultList();

        return StreamUtil.toMappedList(r -> {
            ResultReport result = new ResultReport();
            result.setStudentId(resultAsLong(r, 0));
            result.setFirstname(resultAsString(r, 1));
            result.setLastname(resultAsString(r, 2));
            result.setJournal(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5)));
            result.setEntryType(resultAsString(r, 6));
            result.setGrade(resultAsString(r, 7));
            result.setGradeInserted(resultAsLocalDate(r, 8));
            result.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 9)));
            result.setIsPracticeJournal(resultAsBoolean(r, 10));
            return result;
        }, data);
    }

    private static JpaNativeQueryBuilder negativeJournalResultsQb(StudentGroupTeacherCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on p.id = s.person_id "
                + "join student_group sg on sg.id = s.student_group_id "
                + "join journal_student js on js.student_id = s.id " + "join journal j on j.id = js.journal_id "
                + "join journal_entry_student jes on jes.journal_student_id = js.id "
                + "join journal_entry je on je.id = jes.journal_entry_id");
        qb.requiredCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalCriteria("s.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("je.entry_type_code in (:entryTypeCodes)", "entryTypeCodes", criteria.getEntryTypes());
        qb.optionalCriteria("jes.grade_code not in (:positiveGrades)", "positiveGrades",
                OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
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
        return qb;
    }

    private static JpaNativeQueryBuilder negativePracticeJournalResultsQb(StudentGroupTeacherCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s2 "
                + "join person p2 on p2.id = s2.person_id "
                + "join student_group sg2 on sg2.id = s2.student_group_id "
                + "join practice_journal pj on pj.student_id = s2.id "
                + "join practice_journal_module_subject pjms on pjms.practice_journal_id = pj.id "
                + "join curriculum_version_omodule cvo on cvo.id = pjms.curriculum_version_omodule_id "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "left join curriculum_version_omodule_theme cvot on cvot.id = pjms.curriculum_version_omodule_theme_id "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "join teacher t on t.id = pj.teacher_id "
                + "join person tp on tp.id = t.person_id");

        qb.requiredCriteria("sg2.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalCriteria("s2.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("pj.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("pj.grade_code not in (:positiveGrades)", "positiveGrades",
                OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        qb.optionalCriteria("pj.grade_inserted >= :entryFrom", "entryFrom", criteria.getFrom(),
                DateUtils::firstMomentOfDay);
        qb.optionalCriteria("pj.grade_inserted <= :entryThru", "entryThru", criteria.getThru(),
                DateUtils::lastMomentOfDay);

        if (criteria.getStudyPeriod() != null) {
            qb.filter("pj.grade_inserted >= :studyPeriodStart and pj.grade_inserted <= :studyPeriodEnd");
            qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
            qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
        }
        return qb;
    }

    private static JpaNativeQueryBuilder negativeOutcomeResultsQb(StudentGroupTeacherCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s3 "
                + "join person p3 on p3.id = s3.person_id "
                + "join student_group sg3 on sg3.id = s3.student_group_id "
                + "join student_curriculum_module_outcomes_result scmor on scmor.student_id = s3.id "
                + "join curriculum_module_outcomes cmo on cmo.id = scmor.curriculum_module_outcomes_id "
                + "left join teacher t2 on t2.id = scmor.grade_inserted_teacher_id "
                + "left join person tp2 on tp2.id = t2.person_id");
        qb.requiredCriteria("sg3.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalCriteria("s3.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("scmor.grade_code not in (:positiveGrades)", "positiveGrades",
                OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        if (criteria.getStudyYearObject() != null) {
            qb.optionalCriteria("scmor.grade_date >= :syFrom", "syFrom", criteria.getStudyYearObject().getStartDate(),
                    DateUtils::firstMomentOfDay);
            qb.optionalCriteria("scmor.grade_date <= :syThru", "syThru", criteria.getStudyYearObject().getEndDate(),
                    DateUtils::lastMomentOfDay);
        }

        qb.optionalCriteria("scmor.grade_inserted >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("scmor.grade_inserted <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);

        if (criteria.getStudyPeriod() != null) {
            qb.filter("scmor.grade_date >= :studyPeriodStart and scmor.grade_date <= :studyPeriodEnd");
            qb.parameter("studyPeriodStart", DateUtils.firstMomentOfDay(criteria.getStudyPeriodStart()));
            qb.parameter("studyPeriodEnd", DateUtils.lastMomentOfDay(criteria.getStudyPeriodEnd()));
        }
        return qb;
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

    private Map<Long, List<CurriculumModuleOutcomeResult>> progressReportOutcomes(Set<Long> moduleIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_module_outcomes cmo "
                + "join curriculum_module cm on cm.id = cmo.curriculum_module_id");
        qb.requiredCriteria("cm.id in (:moduleIds)", "moduleIds", moduleIds);
        qb.requiredCriteria("cm.module_code != :examModule", "examModule", CurriculumModuleType.KUTSEMOODUL_L.name());
        qb.sort("cmo.order_nr");

        List<?> data = qb.select("cmo.curriculum_module_id, cmo.id, cmo.outcome_et, cmo.outcome_en,"
                + " cmo.order_nr", em).getResultList();
        if (data.isEmpty()) {
            return Collections.emptyMap();
        }
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                r -> new CurriculumModuleOutcomeResult(resultAsLong(r, 1), resultAsString(r, 2),
                        resultAsString(r, 3), resultAsLong(r, 4)), Collectors.toList())));
    }
}
