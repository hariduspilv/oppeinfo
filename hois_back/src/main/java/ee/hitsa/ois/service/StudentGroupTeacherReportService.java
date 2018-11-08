package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.report.StudentGroupTeacherCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ModuleDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ModuleTypeDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ResultColumnDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentGroupTeacherDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentJournalEntryDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentJournalResultDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentModuleResultDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentResultColumnDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentResultDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentLessonAbsenceDto;

@Transactional
@Service
public class StudentGroupTeacherReportService {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;

    public StudentGroupTeacherDto studentGroupTeacher(StudentGroupTeacherCommand criteria) {
        Collection<ModuleTypeDto> moduleTypes = studentGroupModules(criteria);
        List<ModuleDto> modules = new ArrayList<>();
        for (ModuleTypeDto type : moduleTypes) {
            modules.addAll(type.getModules());
        }
        
        List<StudentDto> students = studentGroupStudents(criteria);
        List<ResultColumnDto> resultColumns = new ArrayList<>();
        
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
                ResultColumnDto column = new ResultColumnDto();
                column.setFullPracticeModule(new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn()));
                resultColumns.add(column);
            }
            
            if (Boolean.TRUE.equals(criteria.getModuleGrade())) {
                ResultColumnDto column = new ResultColumnDto();
                column.setModule(new AutocompleteResult(module.getId(), module.getNameEt(), module.getNameEn()));
                resultColumns.add(column);
            }
        }
        
        setStudentGroupStudentResults(criteria, students, resultColumns);
        
        StudentGroupTeacherDto dto = new StudentGroupTeacherDto();
        dto.getModuleTypes().addAll(moduleTypes);
        dto.setStudents(students);
        dto.setShowModuleGrade(criteria.getModuleGrade());
        dto.setModules(modules);
        dto.setResultColumns(resultColumns);
        return dto;
    }
    
    private Collection<ModuleTypeDto> studentGroupModules(StudentGroupTeacherCommand criteria) {
        Map<Long, List<AutocompleteResult>> journalsByModules = studentGroupModuleJournals(criteria);
        Set<Long> journalModuleIds = journalsByModules.keySet();
        Set<Long> practiceModuleIds = studentGroupModulePracticeModules(criteria);
        Set<Long> moduleIds = new HashSet<>();
        moduleIds.addAll(journalModuleIds);
        moduleIds.addAll(practiceModuleIds);
        
        Map<String, ModuleTypeDto> moduleTypes = new LinkedHashMap<>();
        if (moduleIds.isEmpty()) {
            return moduleTypes.values();
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version_omodule cvo" + 
                " join curriculum_module cm on cvo.curriculum_module_id = cm.id");
        qb.requiredCriteria("cvo.id in (:moduleIds)", "moduleIds", moduleIds);
        
        qb.sort("module_order, name_et");
        List<?> rows = qb.select("distinct cvo.id, cm.name_et, cm.name_en,"
                + "case when cvo.curriculum_version_id = " + criteria.getCurriculumVersion() + " then cm.module_code "
                + "else upper('kutsemoodul_v') end as module_code,"
                + "cm.is_practice,"
                + "case when cvo.curriculum_version_id = " + criteria.getCurriculumVersion() + " then " 
                    + "case when lower(module_code) = 'kutsemoodul_p' then 1 "
                    + "when lower(module_code) = 'kutsemoodul_y' then 2 "
                    + "when lower(module_code) = 'kutsemoodul_v' then 3 "
                    + "else 4 end "
               + "else 3 end as module_order", em).getResultList();
        
        Map<Long, List<AutocompleteResult>> moduleThemesByModules = new HashMap<>();
        if (!practiceModuleIds.isEmpty()) {
            moduleThemesByModules = studentGroupModulePracticeModuleThemes(criteria, practiceModuleIds);
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
        count += module.getJournals().size();
        count += module.getPracticeModuleThemes().size();
        count += Boolean.TRUE.equals(module.getIsPracticeModule()) ? 1 : 0;
        count += Boolean.TRUE.equals(criteria.getModuleGrade()) ? 1 : 0;
        module.setColspan(Long.valueOf(count > 0 ? count : 1));
    }
    
    private Map<Long, List<AutocompleteResult>> studentGroupModuleJournals(StudentGroupTeacherCommand criteria) {
        String journalFrom = "from journal j" + 
                " join journal_omodule_theme jot on jot.journal_id = j.id" + 
                " join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id" + 
                " join journal_student js on j.id = js.journal_id" + 
                " join student s on js.student_id = s.id" + 
                " join student_group sg on s.student_group_id = sg.id" +
                " join lesson_plan_module lpm on jot.lesson_plan_module_id=lpm.id" +
                " join lesson_plan lp on lpm.lesson_plan_id=lp.id";
        if (Boolean.TRUE.equals(criteria.getJournalsWithEntries())) {
            journalFrom += " join journal_entry je on j.id = je.journal_id" +
                    " join journal_entry_student jes on je.id = jes.journal_entry_id";
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(journalFrom);
        qb.requiredCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.filter("(lp.student_group_id=sg.id  or j.id not in "
                + "(select jot2.journal_id from journal_omodule_theme jot2 "
                + "join lesson_plan_module lpm2 on jot2.lesson_plan_module_id = lpm2.id "
                + "join lesson_plan lp2 on lpm2.lesson_plan_id = lp2.id "
                + "where jot2.journal_id = j.ID and lp2.student_group_id = sg.id))");
        qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        
        if (Boolean.TRUE.equals(criteria.getJournalsWithEntries())) {
            qb.optionalCriteria("coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);
            
            if (criteria.getStudyPeriod() != null) {
                qb.filter("coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) >= '"
                        + criteria.getStudyPeriodStart() + "' and coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) <= '"
                        + criteria.getStudyPeriodEnd() + "'");
            }
        }
        
        qb.sort("name_et");
        List<?> data = qb.select("distinct cvot.curriculum_version_omodule_id, j.id, j.name_et", em).getResultList();
        
        if(data.isEmpty()) {
            return Collections.emptyMap();
        }
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                r -> new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 2)), Collectors.toList())));
    }
    
    private Set<Long> studentGroupModulePracticeModules(StudentGroupTeacherCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj" + 
                " join student s on pj.student_id = s.id" + 
                " join student_group sg on s.student_group_id = sg.id");
        qb.requiredCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalCriteria("pj.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.filter("pj.curriculum_version_omodule_id is not null and pj.grade_code is not null");
        
        if (Boolean.TRUE.equals(criteria.getJournalsWithEntries())) {
            qb.optionalCriteria("pj.grade_inserted >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("pj.grade_inserted <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);
            
            if (criteria.getStudyPeriod() != null) {
                qb.filter("pj.grade_inserted >= '" + criteria.getStudyPeriodStart() + "' and pj.grade_inserted <= '"
                        + criteria.getStudyPeriodEnd() + "'");
            }
        }
        List<?> data = qb.select("pj.curriculum_version_omodule_id", em).getResultList();
        
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }
    
    private Map<Long, List<AutocompleteResult>> studentGroupModulePracticeModuleThemes(StudentGroupTeacherCommand criteria, Set<Long> moduleIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(" from practice_journal pj " + 
                "join curriculum_version_omodule_theme cvot on pj.curriculum_version_omodule_theme_id = cvot.id " + 
                "join student s on pj.student_id = s.id " + 
                "join student_group sg on s.student_group_id = sg.id");
        qb.requiredCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.requiredCriteria("pj.curriculum_version_omodule_id in (:moduleIds)", "moduleIds", moduleIds);
        qb.filter("pj.grade_code is not null");
        
        qb.sort("name_et");
        List<?> data = qb.select("distinct pj.curriculum_version_omodule_id, pj.curriculum_version_omodule_theme_id, cvot.name_et", em).getResultList();
        
        if(data.isEmpty()) {
            return Collections.emptyMap();
        }
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                r -> new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 2)), Collectors.toList())));
    }
    
    private List<StudentDto> studentGroupStudents(StudentGroupTeacherCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s " +
                " join person p on s.person_id = p.id" +
                " join student_group sg on s.student_group_id = sg.id");
        qb.requiredCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        
        qb.sort("p.lastname, p.firstname");
        List<?> data = qb.select("s.id, p.firstname, p.lastname", em).getResultList();
        List<StudentDto> students = StreamUtil.toMappedList(r -> {
            StudentDto student = new StudentDto();
            student.setId(resultAsLong(r, 0));
            student.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            return student;
        }, data);
        
        return students;
    }
    
    private void setStudentGroupStudentResults(StudentGroupTeacherCommand criteria,
            List<StudentDto> students, List<ResultColumnDto> resultColumns) {
        if (!students.isEmpty() && !resultColumns.isEmpty()) {
            Map<Long, List<StudentResultColumnDto>> resultColumnsByStudent = studentGroupStudentResultColumns(
                    criteria, StreamUtil.toMappedList(s -> s.getId(), students), resultColumns);
            
            for (StudentDto student : students) {
                List<StudentResultColumnDto> studentResultColumns = resultColumnsByStudent.get(student.getId());
                student.setResultColumns(studentResultColumns);
                student.setHasAddInfo(Boolean.valueOf(studentHasAddInfo(studentResultColumns)));
                for (Absence absence : Absence.values()) {
                    student.getAbsences().put(absence.name(), studentAbsencesCount(studentResultColumns, absence));
                }
            }
        }
    }
    
    private static boolean studentHasAddInfo(List<StudentResultColumnDto> studentResultColumns) {
        List<StudentJournalResultDto> journalResults = StreamUtil.toMappedList(r -> r.getJournalResult(),
                StreamUtil.toFilteredList(r -> r.getJournalResult() != null, studentResultColumns));
        for (StudentJournalResultDto result : journalResults) {
            for (StudentJournalEntryDto entry : result.getEntries()) {
                if (entry.getAddInfo() != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static Short studentAbsencesCount(List<StudentResultColumnDto> studentResultColumns, Absence type) {
        short count = 0;
        List<Long> countedJournals = new ArrayList<>();
        List<StudentJournalResultDto> journalColumns = StreamUtil.toMappedList(c -> c.getJournalResult(),
                StreamUtil.toFilteredList(c -> c.getJournalResult() != null, studentResultColumns));
        for (StudentJournalResultDto column : journalColumns) {
            if (!countedJournals.contains(column.getId())) {
                count += journalAbsencesCount(column.getEntries(), type);
                countedJournals.add(column.getId());
            }
        }
        return Short.valueOf(count);
    }
    
    private Map<Long, List<StudentResultColumnDto>> studentGroupStudentResultColumns(StudentGroupTeacherCommand criteria, 
            List<Long> studentIds, List<ResultColumnDto> resultColumns) {
        Map<Long, List<StudentResultColumnDto>> studentResultColumns = emptyStudentResultColumns(studentIds, resultColumns);
        setStudentJournals(criteria, studentIds, resultColumns, studentResultColumns);
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
            return column;
        }, resultColumns);
        
        for (Long studentId : studentIds) {
            studentEmptyResultColumns.put(studentId, cloneEmptyResultColumns(emptyResultColumns));
        }
        return studentEmptyResultColumns;
    }
    
    private void setStudentJournals(StudentGroupTeacherCommand criteria, List<Long> studentIds,
            List<ResultColumnDto> resultColumns, Map<Long, List<StudentResultColumnDto>> studentResultColumns) {
        List<Long> journalIds = resultColumns.stream().filter(c -> c.getJournal() != null)
                .map(c -> c.getJournal().getId()).collect(Collectors.toList());
        Map<Long, List<StudentJournalEntryDto>> studentJournalEntries = new HashMap<>();
        if (!journalIds.isEmpty()) {
            studentJournalEntries = studentJournalEntries(criteria, studentIds, journalIds);
        }
        if (!studentJournalEntries.isEmpty()) {
            setStudentJournalEntries(criteria, studentResultColumns, studentJournalEntries);
        }
    }
    
    private static List<StudentResultColumnDto> cloneEmptyResultColumns(
            List<StudentResultColumnDto> emptyResultColumns) {
        List<StudentResultColumnDto> clonedList = new ArrayList<>();
        for (StudentResultColumnDto column : emptyResultColumns) {
            clonedList.add(new StudentResultColumnDto(column));
        }
        return clonedList;
    }
    
    private Map<Long, List<StudentJournalEntryDto>> studentJournalEntries(
            StudentGroupTeacherCommand criteria, List<Long> studentIds, List<Long> journalIds) {
        Map<Long, List<StudentJournalEntryDto>> studentGrades = journalEntries(criteria, studentIds, journalIds);
        Map<Long, List<StudentJournalEntryDto>> studentAbsences = journalEntryAbsences(criteria, studentIds, journalIds);
        
        Map<Long, List<StudentJournalEntryDto>> studentJournalEntries = new HashMap<>(studentGrades);
        for (Long studentId : studentAbsences.keySet()) {
            if (!studentJournalEntries.containsKey(studentId)) {
                studentJournalEntries.put(studentId, new ArrayList<>());
            }
            addAbsencesToStudentEntries(studentId, studentJournalEntries, studentAbsences);
            Collections.sort(studentJournalEntries.get(studentId),
                    StreamUtil.comparingWithNullsLast(StudentJournalEntryDto::getOrderDate));
        }
        return studentJournalEntries;
    }
    
    private Map<Long, List<StudentJournalEntryDto>> journalEntries(StudentGroupTeacherCommand criteria,
            List<Long> studentIds, List<Long> journalIds) {
        Map<Long, List<StudentJournalEntryDto>> studentGrades = new HashMap<>();

        if (criteria.getEntryTypes() != null) {
            JpaNativeQueryBuilder qb = studentJournalEntriesQuery(criteria, studentIds, journalIds);
            qb.requiredCriteria("je.entry_type_code in (:entryTypeCodes)", "entryTypeCodes", criteria.getEntryTypes());

            List<?> grades = qb.select(
                    "distinct s.id as student_id, jes.id as student_entry_id, j.id as journal_id, j.name_et, "
                            + "je.entry_type_code, je.entry_date, jes.grade_code, jes.grade_inserted, "
                            + "coalesce(jes.grade_inserted_by, jes.changed_by, jes.inserted_by) as grade_inserted_by, jes.add_info",
                    em).getResultList();
            
            if (!grades.isEmpty()) {
                studentGrades = grades.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    StudentJournalEntryDto journalEntry = new StudentJournalEntryDto();
                    journalEntry.setId(resultAsLong(r, 1));
                    journalEntry.setJournal(new AutocompleteResult(resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 3)));
                    journalEntry.setEntryType(resultAsString(r, 4));
                    journalEntry.setEntryDate(resultAsLocalDate(r, 5));
                    journalEntry.setGrade(resultAsString(r, 6));
                    journalEntry.setGradeInserted(resultAsLocalDate(r, 7));
                    journalEntry.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 8)));
                    journalEntry.setAddInfo(resultAsString(r, 9));
                    journalEntry.setOrderDate(resultAsLocalDate(r, 7));
                    return journalEntry;
                }, Collectors.toList())));
            }
        }
        return studentGrades;
    }
    
    private Map<Long, List<StudentJournalEntryDto>> journalEntryAbsences(StudentGroupTeacherCommand criteria,
            List<Long> studentIds, List<Long> journalIds) {
        Map<Long, List<StudentJournalEntryDto>> studentAbsences = new HashMap<>();

        JpaNativeQueryBuilder qb = studentJournalEntriesQuery(criteria, studentIds, journalIds);
        qb.filter("(jes.absence_code is not null or jesla.absence_code is not null)");

        List<?> absences = qb.select(
                "distinct s.id as student_id, jes.id as student_entry_id, j.id as journal_id, j.name_et, je.entry_type_code, je.entry_date, "
                + "coalesce(jesla.absence_code, jes.absence_code) as absence_code, "
                + "coalesce(jesla.absence_inserted, jes.absence_inserted) as absence_inserted, "
                + "jesla.lesson_nr + coalesce(je.start_lesson_nr - 1, 0), je.lessons, jes.add_info",
                em).getResultList();
        
        if (!absences.isEmpty()) {
            studentAbsences = absences.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                StudentJournalEntryDto journalEntry = new StudentJournalEntryDto();
                journalEntry.setId(resultAsLong(r, 1));
                journalEntry.setJournal(new AutocompleteResult(resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 3)));
                journalEntry.setEntryType(resultAsString(r, 4));
                journalEntry.setEntryDate(resultAsLocalDate(r, 5));
                journalEntry.setAbsence(resultAsString(r, 6));
                journalEntry.setAbsenceInserted(resultAsLocalDate(r, 7));
                journalEntry.setOrderDate(resultAsLocalDate(r, 7));
                journalEntry.setLessonNr(resultAsLong(r, 8));
                journalEntry.setLessons(resultAsLong(r, 9));
                journalEntry.setAddInfo(resultAsString(r, 10));
                return journalEntry;
            }, Collectors.toList())));
        }
        return studentAbsences;
    }
    
    private static JpaNativeQueryBuilder studentJournalEntriesQuery(StudentGroupTeacherCommand criteria, List<Long> studentIds, List<Long> journalIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s " +
                "join journal_student js on js.student_id = s.id " +
                "join journal_entry_student jes on js.id = jes.journal_student_id " +
                "left join journal_entry_student_lesson_absence jesla on jes.id = jesla.journal_entry_student_id " +
                "join journal_entry je on je.id = jes.journal_entry_id " +
                "join journal j on js.journal_id = j.id " +
                "join journal_omodule_theme jot on jot.journal_id = j.id " +
                "join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalIds);
        
        qb.optionalCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) >= :entryFrom",
                "entryFrom", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) <= :entryThru",
                "entryThru", criteria.getThru(), DateUtils::lastMomentOfDay);
        
        if (criteria.getStudyPeriod() != null) {
            qb.filter("coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) >= '"
                    + criteria.getStudyPeriodStart()
                    + "' and coalesce(je.entry_date, jes.grade_inserted, jes.absence_inserted) <= '"
                    + criteria.getStudyPeriodEnd() + "'");
        }
        
        return qb;
    }
    
    private static void addAbsencesToStudentEntries(Long studentId, Map<Long, List<StudentJournalEntryDto>> studentJournalEntries,
            Map<Long, List<StudentJournalEntryDto>> studentAbsences) {
        Map<Long, StudentJournalEntryDto> entries = StreamUtil.toMap(e -> e.getId(), e -> e,
                studentJournalEntries.get(studentId));

        for (StudentJournalEntryDto absence : studentAbsences.get(studentId)) {
            if (!entries.containsKey(absence.getId())) {
                if (absence.getLessonNr() == null) {
                    studentJournalEntries.get(studentId).add(absence);
                    entries.put(absence.getId(), absence);
                } else {
                    StudentJournalEntryDto entry = new StudentJournalEntryDto();
                    entry.setId(absence.getId());
                    entry.setJournal(absence.getJournal());
                    entry.setEntryType(absence.getEntryType());
                    entry.setEntryDate(absence.getEntryDate());
                    entry.setOrderDate( absence.getOrderDate());
                    entry.setAddInfo(absence.getAddInfo());
                    addLessonAbsenceToStudentEntry(entry, absence);
                    studentJournalEntries.get(studentId).add(entry);
                    entries.put(absence.getId(), entry);
                }
            } else {
                StudentJournalEntryDto entry = entries.get(absence.getId());
                if (absence.getLessonNr() == null) {
                    entry.setAbsence(absence.getAbsence());
                    entry.setAbsenceInserted(absence.getAbsenceInserted());
                    entry.setLessons(absence.getLessons());
                } else {
                    addLessonAbsenceToStudentEntry(entry, absence);
                }
            }
        }
    }
    
    private static void addLessonAbsenceToStudentEntry(StudentJournalEntryDto entry, StudentJournalEntryDto absence) {
        JournalEntryStudentLessonAbsenceDto lessonAbsence = new JournalEntryStudentLessonAbsenceDto();
        lessonAbsence.setAbsence(absence.getAbsence());
        lessonAbsence.setAbsenceInserted(LocalDateTime.of(absence.getAbsenceInserted(), LocalTime.MIN));
        lessonAbsence.setLessonNr(absence.getLessonNr());
        entry.getLessonAbsences().add(lessonAbsence);
    }
    
    private static void setStudentJournalEntries(StudentGroupTeacherCommand criteria,
            Map<Long, List<StudentResultColumnDto>> resultColumns,
            Map<Long, List<StudentJournalEntryDto>> journalEntries) {
        for (Long studentId : resultColumns.keySet()) {
            List<StudentJournalEntryDto> entries = journalEntries.get(studentId);
            if (entries != null) {
                List<StudentResultColumnDto> journalColumns = StreamUtil.toFilteredList(r -> r.getJournalResult() != null,
                        resultColumns.get(studentId));
                for (StudentResultColumnDto journalColumn : journalColumns) {
                    StudentJournalResultDto journal = journalColumn.getJournalResult();
                    journal.getEntries().addAll(
                            StreamUtil.toFilteredList(e -> journal.getId().equals(e.getJournal().getId()), entries));

                    if (Boolean.TRUE.equals(criteria.getAbsencesPerJournals())) {
                        for (Absence absence : Absence.values()) {
                            journal.getAbsences().put(absence.name(),
                                    Short.valueOf(journalAbsencesCount(journal.getEntries(), absence)));
                        }
                    }
                }
            }
        }
    }
    
    private static short journalAbsencesCount(List<StudentJournalEntryDto> journalEntries, Absence type) {
        short count = 0;
        for (StudentJournalEntryDto entry : journalEntries) {
            count = addAbsenceOfTypeToCount(type, count, entry.getAbsence(), entry.getLessons());
            for (JournalEntryStudentLessonAbsenceDto lessonAbsence : entry.getLessonAbsences()) {
                count = addAbsenceOfTypeToCount(type, count, lessonAbsence.getAbsence(), null);
            }
        }
        return count;
    }

    private static short addAbsenceOfTypeToCount(Absence type, short count, String absence, Long lessons) {
        if (type.name().equals(absence)) {
            if (lessons != null) {
                count += lessons.shortValue();
            } else {
                count++;
            }
        }
        return count;
    }
    
    private void setStudentPracticeModuleResults(StudentGroupTeacherCommand criteria, List<Long> studentIds,
            List<ResultColumnDto> resultColumns,
            Map<Long, List<StudentResultColumnDto>> studentResultColumns) {
        List<Long> practiceModuleIds = StreamUtil.toMappedList(m -> m.getFullPracticeModule().getId(),
                StreamUtil.toFilteredList(m -> m.getFullPracticeModule() != null, resultColumns));
        Map<Long, List<StudentResultDto>> studentPracticeModuleResults = new HashMap<>();
        
        if (!practiceModuleIds.isEmpty()) {
            studentPracticeModuleResults = studentPracticeModuleResults(criteria, studentIds, practiceModuleIds);
        }
        if (!studentResultColumns.isEmpty()) {
            for (Long studentId : studentResultColumns.keySet()) {
                List<StudentResultColumnDto> practiceThemeColumns = StreamUtil
                        .toFilteredList(r -> r.getPracticeModuleThemeResult() != null, studentResultColumns.get(studentId));
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
                    List<StudentResultDto> results = StreamUtil.toFilteredList(
                            r -> fullPracticeModuleResult.getId().equals(r.getModuleId()) && r.getModuleThemeId() == null,
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj");
        qb.requiredCriteria("pj.student_id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("pj.curriculum_version_omodule_id in (:practiceModuleIds)", "practiceModuleIds", practiceModuleIds);
        
        qb.optionalCriteria("pj.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("pj.grade_inserted >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("pj.grade_inserted <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);
        
        if (criteria.getStudyPeriod() != null) {
            qb.filter("pj.grade_inserted >= '" + criteria.getStudyPeriodStart() + "' and pj.grade_inserted <= '"
                    + criteria.getStudyPeriodEnd() + "'");
        }
        
        qb.filter("pj.grade_code is not null");
        qb.sort("pj.grade_inserted desc nulls last");
        List<?> data = qb.select(
                "pj.student_id, pj.curriculum_version_omodule_id, pj.curriculum_version_omodule_theme_id, pj.grade_code, pj.grade_inserted",
                em).getResultList();
        
        Map<Long, List<StudentResultDto>> studentPracticeModuleResults = new HashMap<>();
        if (!data.isEmpty()) {
            studentPracticeModuleResults = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
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
        Map<Long, List<StudentResultDto>> studentModuleResults = new HashMap<>();
        
        if (!moduleIds.isEmpty()) {
            studentModuleResults = studentModuleResults(criteria, studentIds, moduleIds);
        }
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
    
    private Map<Long, List<StudentResultDto>> studentModuleResults(
            StudentGroupTeacherCommand criteria, List<Long> studentIds, List<Long> moduleIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s " +
                "join student_vocational_result svr on s.id = svr.student_id");
        qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        qb.requiredCriteria("svr.curriculum_version_omodule_id in (:moduleIds)", "moduleIds", moduleIds);
        
        qb.optionalCriteria("svr.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("svr.grade_date >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("svr.grade_date <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);
        
        if (criteria.getStudyPeriod() != null) {
            qb.filter("svr.grade_date >= '" + criteria.getStudyPeriodStart() + "' and svr.grade_date <= '"
                    + criteria.getStudyPeriodEnd() + "'");
        }
        
        qb.sort("svr.grade_date desc nulls last");
        List<?> data = qb.select("s.id as student_id, svr.curriculum_version_omodule_id as module_id, svr.grade_code, svr.grade_date", em).getResultList();
        
        Map<Long, List<StudentResultDto>> studentModuleResults = new HashMap<>();
        if (!data.isEmpty()) {
            studentModuleResults = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                StudentResultDto result = new StudentResultDto();
                result.setModuleId(resultAsLong(r, 1));
                result.setGrade(resultAsString(r, 2));
                result.setGradeInserted(resultAsLocalDate(r, 3));
                return result;
            }, Collectors.toList())));
        }
        return studentModuleResults;
    }
    
    public byte[] studentGroupTeacherAsExcel(StudentGroupTeacherCommand criteria, ClassifierCache classifierCache) {
        StudentGroupTeacherDto dto = studentGroupTeacher(criteria);
        
        List<String> resultColumns = StreamUtil.toMappedList(rc -> ReportUtil.resultColumnAsString(rc, Boolean.TRUE, Language.ET),
                dto.getResultColumns());
        
        List< Map<String, Object>> students = StreamUtil.toMappedList(s -> {
            Map<String, Object> student = new HashMap<>();
            student.put("fullname", s.getFullname());
            student.put("resultColumns",
                    StreamUtil.toMappedList(rc -> ReportUtil.studentResultColumnAsString(criteria.getAbsencesPerJournals(), rc, classifierCache),
                            s.getResultColumns()));
            student.put("absenceH", s.getAbsences().get(Absence.PUUDUMINE_H.name()));
            student.put("absenceP", s.getAbsences().get(Absence.PUUDUMINE_P.name()));
            student.put("absenceV", s.getAbsences().get(Absence.PUUDUMINE_V.name()));
            student.put("absencePR", s.getAbsences().get(Absence.PUUDUMINE_PR.name()));
            return student;
        }, dto.getStudents());
        
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("moduleTypes", dto.getModuleTypes());
        data.put("modules", dto.getModules());
        data.put("resultColumns", resultColumns);
        data.put("students", students);
        
        return xlsService.generate("studentgroupteacher.xls", data);
    }
}
