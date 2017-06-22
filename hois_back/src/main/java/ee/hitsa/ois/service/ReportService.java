package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.DateUtils;
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

    public Page<StudentSearchDto> students(Long schoolId, @Valid StudentSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from student s inner join person p on s.person_id = p.id " +
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
        qb.optionalCriteria("s.language_code = :language", "language", criteria.getLanguage());

        // TODO ainepunktid (last value of select)
        return JpaQueryUtil.pagingResult(qb, "s.id, p.firstname, p.lastname, p.idcode, s.study_start, c.orig_study_level_code, " +
                "cv.code, c.name_et, c.name_en, sg.code as student_group_code, s.study_load_code, s.study_form_code, s.status_code, " +
                "s.fin_code, s.fin_specific_code, s.language_code, 50.0"
        , em, pageable).map(r -> new StudentSearchDto(r));
    }

    public byte[] studentsAsExcel(Long schoolId, @Valid StudentSearchCommand criteria) {
        List<StudentSearchDto> students = students(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();
        return xlsService.generate("students.xls", Collections.singletonMap("students", students));
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

    public Page<StudentStatisticsDto> studentStatisticsByPeriod(Long schoolId, StudentStatisticsByPeriodCommand criteria, Pageable pageable) {
        Page<StudentStatisticsDto> result = loadCurriculums(schoolId, criteria.getCurriculum(), pageable);

        // load grouped by value counts of given classifier
        if(!result.getContent().isEmpty()) {
            String groupingField;
            DirectiveType directiveType;
            if(StudentStatus.OPPURSTAATUS_K.name().equals(criteria.getResult())) {
                groupingField = "ds.reason_code";
                directiveType = DirectiveType.KASKKIRI_EKSMAT;
            } else if(StudentStatus.OPPURSTAATUS_A.name().equals(criteria.getResult())) {
                groupingField = "ds.reason_code";
                directiveType = DirectiveType.KASKKIRI_AKAD;
            } else if(StudentStatus.OPPURSTAATUS_L.name().equals(criteria.getResult())) {
                groupingField = "cast(ds.is_cum_laude as varchar)";
                directiveType = DirectiveType.KASKKIRI_LOPET;
            } else {
                throw new AssertionFailedException("Unknown result classifier");
            }

            Map<Long, StudentStatisticsDto> cs = StreamUtil.toMap(StudentStatisticsDto::getId, result.getContent());

            JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from directive_student ds " +
                    "inner join directive d on ds.directive_id = d.id " +
                    "inner join student_history sh on ds.student_history_id = sh.id " +
                    "inner join curriculum_version cv on sh.curriculum_version_id = cv.id")
                    .groupBy(String.format("cv.curriculum_id, %s", groupingField));
            qb.requiredCriteria("cv.curriculum_id in (:curriculum)", "curriculum", cs.keySet());

            qb.requiredCriteria("d.school_id = :schoolId", "schoolId", schoolId);
            qb.requiredCriteria("d.type_code = :directiveType", "directiveType", directiveType);
            qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
            qb.optionalCriteria("d.confirm_date >= :validFrom", "validFrom", criteria.getFrom());
            qb.optionalCriteria("d.confirm_date <= :validThru", "validThru", criteria.getThru());
            // check for directive cancellation for given student
            qb.filter("not exists(select 1 from directive cd inner join directive_student cds on cd.id = cds.directive_id and cd.canceled_directive_id = d.id and cds.student_id=ds.student_id and cd.status_code = :directiveStatus)");

            List<?> data = qb.select(String.format("cv.curriculum_id, %s, count(*)", groupingField), em).getResultList();
            loadStatisticCounts(cs, data, false);
        }

        return result;
    }

    public Page<CurriculumCompletionDto> curriculumCompletion(Long schoolId, CurriculumCompletionCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from student s inner join person p on s.person_id = p.id " +
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

    public Page<TeacherLoadDto> teacherLoad(Long schoolId, TeacherLoadCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(
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

        Page<?> result = JpaQueryUtil.pagingResult(qb, "syc.name_et, syc.name_en, sp.name_et as study_period_name_et, sp.name_en as study_period_name_en, p.firstname, p.lastname, sum(jc.hours), 0, jt.teacher_id, jc.study_period_id", em, pageable);

        // calculate used teacher id and study period id values for returned page
        Map<Long, Map<Long, List<Object>>> subjectRecords;
        if(!result.getContent().isEmpty()) {
            Set<Long> teachers = new HashSet<>();
            Set<Long> studyPeriods = new HashSet<>();
            for(Object record : result.getContent()) {
                teachers.add(resultAsLong(record, 8));
                studyPeriods.add(resultAsLong(record, 9));
            }

            // select subjects by teacher and study period id: starting from SubjectStudyPeriodTeacher table
            qb = new JpaQueryUtil.NativeQueryBuilder("from subject_study_period_teacher sspt " +
                    "inner join subject_study_period ssp on sspt.subject_study_period_id = ssp.id "+
                    "inner join subject s on ssp.subject_id = s.id");

            qb.requiredCriteria("sspt.teacher_id in (:teacher)", "teacher", teachers);
            qb.requiredCriteria("ssp.study_period_id in (:studyPeriod)", "studyPeriod", studyPeriods);

            List<?> subjects = qb.select("s.name_et, s.name_en, s.code, sspt.teacher_id, ssp.study_period_id", em).getResultList();
            subjectRecords = subjects.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 3), Collectors.groupingBy(r -> resultAsLong(r, 4))));
        } else {
            subjectRecords = new HashMap<>();
        }

        return result.map(r -> new TeacherLoadDto(r, subjectRecords.computeIfAbsent(resultAsLong(r, 8), key -> new HashMap<>()).get(resultAsLong(r, 9))));
    }

    private Page<StudentStatisticsDto> loadCurriculums(Long schoolId, List<EntityConnectionCommand> curriculumIds, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from curriculum c").sort("c.name_et");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("c.id in (:curriculum)", "curriculum", StreamUtil.toMappedList(r -> r.getId(), curriculumIds));

        return JpaQueryUtil.pagingResult(qb, "c.id, c.name_et, c.name_en", em, pageable).map(r -> new StudentStatisticsDto(r));
    }

    private void loadStudentStatistics(Map<Long, StudentStatisticsDto> curriculums, String groupingField, StudentStatisticsCommand criteria, boolean filterResult) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from student_history sh inner join curriculum_version cv on sh.curriculum_version_id = cv.id")
                .groupBy(String.format("cv.curriculum_id, %s", groupingField));
        qb.requiredCriteria("cv.curriculum_id in (:curriculum)", "curriculum", curriculums.keySet());
        qb.requiredCriteria("sh.status_code in (:status)", "status", StudentStatus.STUDENT_STATUS_ACTIVE);

        qb.optionalCriteria("(sh.valid_thru is null or sh.valid_thru >= :validFrom)", "validFrom", criteria.getDate(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("sh.valid_from <= :validThru", "validThru", criteria.getDate(), DateUtils::lastMomentOfDay);

        List<?> data = qb.select(String.format("cv.curriculum_id, %s, count(*)", groupingField), em).getResultList();
        loadStatisticCounts(curriculums, data, filterResult);
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
}
