package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.report.CurriculumCompletionCommand;
import ee.hitsa.ois.web.commandobject.report.CurriculumSubjectsCommand;
import ee.hitsa.ois.web.commandobject.report.StudentSearchCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsByPeriodCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.TeacherLoadCommand;
import ee.hitsa.ois.web.dto.report.CurriculumCompletionDto;
import ee.hitsa.ois.web.dto.report.CurriculumSubjectsDto;
import ee.hitsa.ois.web.dto.report.StudentSearchDto;
import ee.hitsa.ois.web.dto.report.StudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.TeacherLoadDto;

@Transactional
@Service
public class ReportService {

    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;

    public Page<StudentSearchDto> students(Long schoolId, StudentSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s inner join person p on s.person_id = p.id " +
                "inner join curriculum_version cv on s.curriculum_version_id = cv.id " +
                "inner join curriculum c on cv.curriculum_id = c.id "+
                "left join student_group sg on s.student_group_id = sg.id").sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("p.birthdate >= :birthdateFrom", "birthdateFrom", criteria.getBirthdateFrom());
        qb.optionalCriteria("p.birthdate <= :birthdateThru", "birthdateThru", criteria.getBirthdateThru());
        qb.optionalCriteria("s.study_start >= :studyStartFrom", "studyStartFrom", criteria.getStudyStartFrom());
        qb.optionalCriteria("s.study_start <= :studyStartThru", "studyStartThru", criteria.getStudyStartThru());
        qb.optionalCriteria("c.orig_study_level_code = :studyLevel", "studyLevel", criteria.getStudyLevel());
        qb.optionalCriteria("cv.id = :curriculumVersion", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalCriteria("s.student_group_id = :studentGroup", "studentGroup", criteria.getStudentGroup());
        qb.optionalCriteria("s.study_load_code = :studyLoad", "studyLoad", criteria.getStudyLoad());
        qb.optionalCriteria("s.study_form_code = :studyForm", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("s.status_code = :status", "status", criteria.getStatus());
        qb.optionalCriteria("s.fin_code = :fin", "fin", criteria.getFin());
        qb.optionalCriteria("s.fin_specific_code = :fin", "fin", criteria.getFinSpecific());
        qb.optionalCriteria("s.language_code = :language", "language", criteria.getLanguage());

        // TODO ainepunktid (last value of select)
        return JpaQueryUtil.pagingResult(qb, "s.id, p.firstname, p.lastname, p.idcode, s.study_start, c.orig_study_level_code, " +
                "cv.code, c.name_et, c.name_en, sg.code as student_group_code, s.study_load_code, s.study_form_code, s.status_code, " +
                "s.fin_code, s.fin_specific_code, s.language_code, 50.0"
        , em, pageable).map(r -> new StudentSearchDto(r));
    }

    public byte[] studentsAsExcel(Long schoolId, StudentSearchCommand criteria) {
        List<StudentSearchDto> students = students(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("students", students);
        return xlsService.generate("students.xls", data);
    }

    public Page<StudentStatisticsDto> studentStatistics(Long schoolId, StudentStatisticsCommand criteria, Pageable pageable) {
        Page<StudentStatisticsDto> result = loadCurriculums(schoolId, criteria.getCurriculum(), pageable);

        // load grouped by value counts of given classifier
        if(!result.getContent().isEmpty()) {
            String groupingField;
            if(MainClassCode.FINALLIKAS.name().equals(criteria.getResult())) {
                groupingField = "sh.fin_code";
            } else if(MainClassCode.OPPEVORM.name().equals(criteria.getResult())) {
                groupingField = "sh.study_form_code";
            } else if(MainClassCode.OPPURSTAATUS.name().equals(criteria.getResult())) {
                groupingField = "sh.status_code";
            } else if(criteria.getResult() == null) {
                groupingField = null;
            } else {
                throw new AssertionFailedException("Unknown result classifier");
            }

            Map<Long, StudentStatisticsDto> cs = StreamUtil.toMap(StudentStatisticsDto::getId, result.getContent());

            loadStudentStatistics(cs, groupingField, criteria, false);
            if(MainClassCode.FINALLIKAS.name().equals(criteria.getResult())) {
                // Fin source is grouped twice: fin source and fin source explanation
                loadStudentStatistics(cs, "sh.fin_specific_code", criteria, true);
            }
        }
        return result;
    }

    public byte[] studentStatisticsAsExcel(Long schoolId, StudentStatisticsCommand criteria) {
        List<StudentStatisticsDto> students = studentStatistics(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("students", students);
        return xlsService.generate("studentstatistics.xls", data);
    }

    public Page<StudentStatisticsDto> studentStatisticsByPeriod(Long schoolId, StudentStatisticsByPeriodCommand criteria, Pageable pageable) {
        Page<StudentStatisticsDto> result = loadCurriculums(schoolId, criteria.getCurriculum(), pageable);

        // load grouped by value counts of given classifier
        if(!result.getContent().isEmpty()) {
            String groupingField;
            List<String> directiveTypes;
            if(StudentStatus.OPPURSTAATUS_K.name().equals(criteria.getResult())) {
                groupingField = "ds.reason_code";
                directiveTypes = EnumUtil.toNameList(DirectiveType.KASKKIRI_EKSMAT);
            } else if(StudentStatus.OPPURSTAATUS_A.name().equals(criteria.getResult())) {
                groupingField = "ds.reason_code";
                directiveTypes = EnumUtil.toNameList(DirectiveType.KASKKIRI_AKAD);
            } else if(StudentStatus.OPPURSTAATUS_L.name().equals(criteria.getResult())) {
                groupingField = "cast(ds.is_cum_laude as varchar)";
                directiveTypes = EnumUtil.toNameList(DirectiveType.KASKKIRI_LOPET);
            } else if(criteria.getResult() == null) {
                groupingField = null;
                directiveTypes = EnumUtil.toNameList(DirectiveType.KASKKIRI_EKSMAT, DirectiveType.KASKKIRI_AKAD, DirectiveType.KASKKIRI_LOPET);
            } else {
                throw new AssertionFailedException("Unknown result classifier");
            }

            Map<Long, StudentStatisticsDto> cs = StreamUtil.toMap(StudentStatisticsDto::getId, result.getContent());

            String grouping = "cv.curriculum_id";
            if(groupingField != null) {
                grouping = grouping + ", " + groupingField;
            }
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds " +
                    "inner join directive d on ds.directive_id = d.id " +
                    "inner join student_history sh on ds.student_history_id = sh.id " +
                    "inner join curriculum_version cv on sh.curriculum_version_id = cv.id").groupBy(grouping);

            qb.requiredCriteria("cv.curriculum_id in (:curriculum)", "curriculum", cs.keySet());

            qb.requiredCriteria("d.school_id = :schoolId", "schoolId", schoolId);
            qb.requiredCriteria("d.type_code in(:directiveType)", "directiveType", directiveTypes);
            qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
            qb.optionalCriteria("d.confirm_date >= :validFrom", "validFrom", criteria.getFrom());
            qb.optionalCriteria("d.confirm_date <= :validThru", "validThru", criteria.getThru());
            // check for directive cancellation for given student
            qb.filter("ds.canceled = false");

            List<?> data = qb.select(grouping + ", count(*)", em).getResultList();
            if(groupingField == null) {
                loadCounts(cs, data);
            } else {
                loadStatisticCounts(cs, data, false);
            }
        }

        return result;
    }

    public byte[] studentStatisticsByPeriodAsExcel(Long schoolId, StudentStatisticsByPeriodCommand criteria) {
        List<StudentStatisticsDto> students = studentStatisticsByPeriod(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("students", students);
        return xlsService.generate("studentstatisticsbyperiod.xls", data);
    }

    public Page<CurriculumCompletionDto> curriculumCompletion(Long schoolId, CurriculumCompletionCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s inner join person p on s.person_id = p.id " +
                "inner join curriculum_version cv on s.curriculum_version_id = cv.id " +
                "inner join curriculum c on cv.curriculum_id = c.id "+
                "left join student_group sg on s.student_group_id = sg.id").sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code in (:status)", "status", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.optionalCriteria("cv.id = :curriculumVersion", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalCriteria("s.study_load_code = :studyLoad", "studyLoad", criteria.getStudyLoad());
        qb.optionalCriteria("s.study_form_code = :studyForm", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("s.student_group_id = :studentGroup", "studentGroup", criteria.getStudentGroup());

        return JpaQueryUtil.pagingResult(qb, "s.id, p.firstname, p.lastname, " +
                "cv.code, c.name_et, c.name_en, sg.code as student_group_code, s.study_load_code, s.study_form_code, s.status_code"
        , em, pageable).map(r -> new CurriculumCompletionDto(r));
    }

    @SuppressWarnings("unused")
    public Page<CurriculumSubjectsDto> curriculumSubjects(Long schoolId, CurriculumSubjectsCommand criteria, Pageable pageable) {
        return null;
    }

    public Page<TeacherLoadDto> teacherLoadVocational(Long schoolId, TeacherLoadCommand criteria, Pageable pageable) {
        return teacherLoad(schoolId, criteria, pageable, false);
    }

    public Page<TeacherLoadDto> teacherLoadHigher(Long schoolId, TeacherLoadCommand criteria, Pageable pageable) {
        return teacherLoad(schoolId, criteria, pageable, true);
    }

    private Page<TeacherLoadDto> teacherLoad(Long schoolId, TeacherLoadCommand criteria, Pageable pageable, boolean higher) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from journal_teacher jt inner join journal j on j.id = jt.journal_id " +
                "inner join study_year sy on j.study_year_id = sy.id " +
                "inner join classifier syc on sy.year_code = syc.code " +
                "inner join study_period sp on sp.study_year_id = sy.id " +
                "inner join teacher t on jt.teacher_id = t.id inner join person p on t.person_id = p.id " +
                "inner join journal_capacity jc on j.id = jc.journal_id and jc.study_period_id = sp.id").sort(pageable);

        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("j.study_year_id = :studyYear", "studyYear", criteria.getStudyYear());
        qb.optionalCriteria("jc.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.optionalCriteria("jt.teacher_id = :teacher", "teacher", criteria.getTeacher());

        qb.groupBy("syc.name_et, syc.name_en, sp.name_et, sp.name_en, p.firstname, p.lastname, jt.teacher_id, jc.study_period_id");

        Page<?> result = JpaQueryUtil.pagingResult(qb, "syc.name_et, syc.name_en, sp.name_et as study_period_name_et, sp.name_en as study_period_name_en, p.firstname, p.lastname, sum(jc.hours), jt.teacher_id, jc.study_period_id", em, pageable);

        // calculate used teacher id and study period id values for returned page
        Map<Long, Map<Long, List<Object>>> subjectRecords = new HashMap<>();
        Map<Long, Map<Long, List<Object>>> moduleRecords = new HashMap<>();
        Map<Long, Map<Long, Long>> actualLoadHours = new HashMap<>();
        if(!result.getContent().isEmpty()) {
            Set<Long> teachers = new HashSet<>();
            Set<Long> studyPeriods = new HashSet<>();
            for(Object record : result.getContent()) {
                teachers.add(resultAsLong(record, 7));
                studyPeriods.add(resultAsLong(record, 8));
            }

            if(higher) {
                // higher: select subjects by teacher and study period id: starting from SubjectStudyPeriodTeacher table
                qb = new JpaNativeQueryBuilder("from subject_study_period_teacher sspt " +
                        "inner join subject_study_period ssp on sspt.subject_study_period_id = ssp.id "+
                        "inner join subject s on ssp.subject_id = s.id");

                qb.requiredCriteria("sspt.teacher_id in (:teacher)", "teacher", teachers);
                qb.requiredCriteria("ssp.study_period_id in (:studyPeriod)", "studyPeriod", studyPeriods);

                List<?> subjects = qb.select("s.name_et, s.name_en, s.code, sspt.teacher_id, ssp.study_period_id", em).getResultList();
                subjects.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 3), () -> subjectRecords, Collectors.groupingBy(r -> resultAsLong(r, 4))));
            } else {
                // vocational: select modules by teacher and study period id
                qb = new JpaNativeQueryBuilder("from journal_teacher jt " +
                        "inner join journal j on jt.journal_id = j.id " +
                        "inner join study_year sy on j.study_year_id = sy.id " +
                        "inner join study_period sp on sp.study_year_id = sy.id " +
                        "inner join journal_omodule_theme jot on j.id = jot.journal_id " +
                        "inner join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id "+
                        "inner join curriculum_version_omodule_theme cvot on lpm.curriculum_version_omodule_id = cvot.id " +
                        "inner join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "+
                        "inner join curriculum_module cm on cvo.curriculum_module_id = cm.id " +
                        "inner join classifier m on cm.module_code = m.code " +
                        "inner join curriculum c on cm.curriculum_id = c.id");

                qb.requiredCriteria("jt.teacher_id in (:teacher)", "teacher", teachers);
                qb.requiredCriteria("sp.id in (:studyPeriod)", "studyPeriod", studyPeriods);

                List<?> modules = qb.select("cm.name_et, cm.name_en, m.name_et as modulename_et, m.name_en as modulename_en, c.code, jt.teacher_id, sp.id", em).getResultList();
                modules.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 5), () -> moduleRecords, Collectors.groupingBy(r -> resultAsLong(r, 6))));
            }

            // actual load
            qb = new JpaNativeQueryBuilder("from timetable_object tto " +
                    "inner join timetable t on tto.timetable_id = t.id " +
                    "inner join timetable_event te on te.timetable_object_id = tto.id " +
                    "inner join timetable_event_time tet on tet.timetable_event_id = te.id " +
                    "inner join timetable_event_teacher tete on tete.timetable_event_time_id = tet.id");

            qb.requiredCriteria("tete.teacher_id in (:teacher)", "teacher", teachers);
            qb.requiredCriteria("t.study_period_id in (:studyPeriod)", "studyPeriod", studyPeriods);

            qb.groupBy("tete.teacher_id, t.study_period_id");
            List<?> actualLoad = qb.select("tete.teacher_id, t.study_period_id, sum(te.lessons)", em).getResultList();
            actualLoad.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), () -> actualLoadHours, Collectors.toMap(r -> resultAsLong(r, 1), r -> resultAsLong(r, 2))));
        }

        return result.map(r -> {
            Long teacherId = resultAsLong(r, 7);
            Long studyPeriodId = resultAsLong(r, 8);
            return new TeacherLoadDto(r, subjectRecords.computeIfAbsent(teacherId, key -> new HashMap<>()).get(studyPeriodId), moduleRecords.computeIfAbsent(teacherId, key -> new HashMap<>()).get(studyPeriodId), actualLoadHours.computeIfAbsent(teacherId, key -> new HashMap<>()).get(studyPeriodId));
        });
    }

    private Page<StudentStatisticsDto> loadCurriculums(Long schoolId, List<EntityConnectionCommand> curriculumIds, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum c").sort("c.name_et");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("c.id in (:curriculum)", "curriculum", StreamUtil.toMappedList(r -> r.getId(), curriculumIds));

        return JpaQueryUtil.pagingResult(qb, "c.id, c.name_et, c.name_en", em, pageable).map(r -> new StudentStatisticsDto(r));
    }

    private void loadStudentStatistics(Map<Long, StudentStatisticsDto> curriculums, String groupingField, StudentStatisticsCommand criteria, boolean filterResult) {
        String grouping = "cv.curriculum_id";
        if(groupingField != null) {
            grouping = grouping + ", " + groupingField;
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_history sh inner join curriculum_version cv on sh.curriculum_version_id = cv.id")
                .groupBy(grouping);
        qb.requiredCriteria("cv.curriculum_id in (:curriculum)", "curriculum", curriculums.keySet());
        qb.requiredCriteria("sh.status_code in (:status)", "status", StudentStatus.STUDENT_STATUS_ACTIVE);

        qb.optionalCriteria("(sh.valid_thru is null or sh.valid_thru >= :validFrom)", "validFrom", criteria.getDate(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("sh.valid_from <= :validThru", "validThru", criteria.getDate(), DateUtils::lastMomentOfDay);

        List<?> data = qb.select(grouping + ", count(*)", em).getResultList();
        if(groupingField == null) {
            loadCounts(curriculums, data);
        } else {
            loadStatisticCounts(curriculums, data, filterResult);
        }
    }

    private static void loadStatisticCounts(Map<Long, StudentStatisticsDto> curriculums, List<?> data, boolean filterResult) {
        for(Object r : data) {
            Long curriculumId = resultAsLong(r, 0);
            String group = resultAsString(r, 1);
            if(group != null) {
                StudentStatisticsDto dto = curriculums.get(curriculumId);
                dto.getResult().put(group, resultAsLong(r, 2));
                if(filterResult) {
                    dto.getResultFilter().add(group);
                }
            }
        }
    }

    private static void loadCounts(Map<Long, StudentStatisticsDto> curriculums, List<?> data) {
        for(Object r : data) {
            Long curriculumId = resultAsLong(r, 0);
            StudentStatisticsDto dto = curriculums.get(curriculumId);
            dto.getResult().put("count", resultAsLong(r, 1));
        }
    }
}
