package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
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
import ee.hitsa.ois.web.commandobject.report.VotaCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;
import ee.hitsa.ois.web.dto.report.CurriculumCompletionDto;
import ee.hitsa.ois.web.dto.report.CurriculumSubjectsDto;
import ee.hitsa.ois.web.dto.report.StudentSearchDto;
import ee.hitsa.ois.web.dto.report.StudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.TeacherLoadDto;
import ee.hitsa.ois.web.dto.report.VotaDto;

@Transactional
@Service
public class ReportService {

    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private SchoolService schoolService;

    /**
     * Students report
     *
     * @param schoolId
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<StudentSearchDto> students(Long schoolId, StudentSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s inner join person p on s.person_id = p.id " +
                "inner join curriculum_version cv on s.curriculum_version_id = cv.id " +
                "inner join curriculum c on cv.curriculum_id = c.id "+
                "left join student_group sg on s.student_group_id = sg.id "+
                "left join student_curriculum_completion scc on scc.student_id = s.id").sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.optionalCriteria("coalesce(p.idcode, p.foreign_idcode) = :idcode", "idcode", criteria.getIdcode());
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

        Page<StudentSearchDto> result = JpaQueryUtil.pagingResult(qb, "s.id, p.firstname, p.lastname, coalesce(p.idcode, p.foreign_idcode) as idcode, s.study_start, c.orig_study_level_code, " +
                "cv.code, c.name_et, c.name_en, c.mer_code, sg.code as student_group_code, s.study_load_code, s.study_form_code, s.status_code, " +
                "s.fin_code, s.fin_specific_code, s.language_code, s.email, scc.credits"
        , em, pageable).map(r -> new StudentSearchDto(r));

        Set<Long> studentIds = result.getContent().stream().map(StudentSearchDto::getId).collect(Collectors.toSet());
        if(!studentIds.isEmpty()) {
            // load occupation certificates for every student
            List<?> data = em.createNativeQuery("select soc.student_id, soc.certificate_nr, soc.occupation_code, soc.part_occupation_code, " +
                    "soc.speciality_code from student_occupation_certificate soc where soc.student_id in (?1)")
                    .setParameter(1, studentIds)
                    .getResultList();
            Map<Long, List<Object>> certificates = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0)));
            for(StudentSearchDto dto : result.getContent()) {
                List<?> studentCertificates = certificates.getOrDefault(dto.getId(), Collections.emptyList());
                dto.setOccupationCertificates(StreamUtil.toMappedList(r -> {
                    return new StudentOccupationCertificateDto(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4));
                }, studentCertificates));
            }
        }
        return result;
    }

    /**
     * Students report in excel format
     *
     * @param schoolId
     * @param criteria
     * @return
     */
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
            } else if(StringUtils.isEmpty(criteria.getResult())) {
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
            } else if(StringUtils.isEmpty(criteria.getResult())) {
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
                "left join student_group sg on s.student_group_id = sg.id "+
                "left join student_curriculum_completion scc on scc.student_id = s.id").sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code in (:status)", "status", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.optionalCriteria("cv.id = :curriculumVersion", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalCriteria("s.study_load_code = :studyLoad", "studyLoad", criteria.getStudyLoad());
        qb.optionalCriteria("s.study_form_code = :studyForm", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("s.student_group_id = :studentGroup", "studentGroup", criteria.getStudentGroup());

        return JpaQueryUtil.pagingResult(qb, "s.id, p.firstname, p.lastname, " +
                "cv.code, c.name_et, c.name_en, sg.code as student_group_code, s.study_load_code, s.study_form_code, s.status_code, " +
                "scc.credits_last_study_period, scc.credits, round(scc.credits * 100 / c.credits), " +
                "(select count(*) from study_period sp join study_year sy on sy.id = sp.study_year_id"+
                " where sy.school_id = s.school_id and sp.end_date < now() and s.study_start < sp.end_date) as period_count, " +
                "(select count(*) from study_year sy"+
                " where sy.school_id = s.school_id and sy.end_date < now() and s.study_start < sy.end_date) as year_count"
        , em, pageable).map(r -> new CurriculumCompletionDto(r));
    }

    public byte[] curriculumCompletionAsExcel(Long schoolId, CurriculumCompletionCommand criteria) {
        List<CurriculumCompletionDto> students = curriculumCompletion(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("students", students);
        return xlsService.generate("curriculumscompletion.xls", data);
    }

    @SuppressWarnings("unused")
    public Page<CurriculumSubjectsDto> curriculumSubjects(Long schoolId, CurriculumSubjectsCommand criteria, Pageable pageable) {
        // TODO not implemented yet
        return null;
    }

    public Page<TeacherLoadDto> teacherLoadVocational(Long schoolId, TeacherLoadCommand criteria, Pageable pageable) {
        return teacherLoad(schoolId, criteria, pageable, false);
    }

    public byte[] teacherLoadVocationalAsExcel(Long schoolId, TeacherLoadCommand criteria) {
        return teacherLoadAsExcel(schoolId, criteria, false);
    }

    public Page<TeacherLoadDto> teacherLoadHigher(Long schoolId, TeacherLoadCommand criteria, Pageable pageable) {
        return teacherLoad(schoolId, criteria, pageable, true);
    }

    public byte[] teacherLoadHigherAsExcel(Long schoolId, TeacherLoadCommand criteria) {
        return teacherLoadAsExcel(schoolId, criteria, true);
    }

    private byte[] teacherLoadAsExcel(Long schoolId, TeacherLoadCommand criteria, boolean higher) {
        SchoolType type = schoolService.schoolType(schoolId);
        List<TeacherLoadDto> rows = teacherLoad(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE), higher).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("rows", rows);
        data.put("isHigherSchool", Boolean.valueOf(type.isHigher()));
        return xlsService.generate("teachersload" + (higher ? "higher" : "vocational") + ".xls", data);
    }

    public Page<VotaDto> vota(Long schoolId, VotaCommand criteria, Pageable pageable) {
        // curriculum versions for given study year/period
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version cv " +
                "join curriculum c on cv.curriculum_id = c.id " +
                "join study_period sp on (cv.valid_from is null or cv.valid_from <= sp.end_date) and (cv.valid_thru is null or cv.valid_thru >= sp.start_date) " +
                "join study_year sy on sp.study_year_id = sy.id " +
                "join classifier syc on sy.year_code = syc.code").sort("sp.start_date", "sp.id", "cv.code");
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("sy.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("sp.id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());

        Set<Long> cvIds = new HashSet<>();
        Set<StudyPeriod> studyPeriods = new HashSet<>();
        Map<Long, Map<Long, VotaDto>> dtosByPeriodAndCurriculum = new HashMap<>();
        Page<VotaDto> result = JpaQueryUtil.pagingResult(qb, "syc.name_et, syc.name_en, sp.id as sp_id, sp.name_et as sp_name_et, sp.name_en as sp_name_en, cv.id, cv.code, c.name_et as c_name_et, c.name_en as c_name_en, sp.start_date, sp.end_date", em, pageable)
                .map(r -> {
                    VotaDto dto = new VotaDto();
                    dto.setStudyYear(new AutocompleteResult(null, resultAsString(r, 0), resultAsString(r, 1)));
                    Long studyPeriodId = resultAsLong(r, 2);
                    dto.setStudyPeriod(new AutocompleteResult(studyPeriodId, resultAsString(r, 3), resultAsString(r, 4)));
                    String cvCode = resultAsString(r, 6);
                    dto.setCurriculum(new AutocompleteResult(null, CurriculumUtil.versionName(cvCode, resultAsString(r, 7)), CurriculumUtil.versionName(cvCode, resultAsString(r, 8))));
                    Long cvId = resultAsLong(r, 5);
                    dtosByPeriodAndCurriculum.computeIfAbsent(studyPeriodId, id -> new HashMap<>()).put(cvId, dto);
                    cvIds.add(cvId);
                    studyPeriods.add(new StudyPeriod(studyPeriodId, resultAsLocalDate(r, 9), resultAsLocalDate(r, 10)));
                    return dto;
                });

        List<VotaDto> dtos = result.getContent();
        if(!dtos.isEmpty()) {
            Map<VotaDto, VotaStatistics> allStats = new IdentityHashMap<>();
            List<StudyPeriod> sortedStudyPeriods = studyPeriods.stream().sorted(Comparator.comparing(StudyPeriod::getStart)).collect(Collectors.toList());
            LocalDateTime start = DateUtils.firstMomentOfDay(studyPeriods.stream().map(StudyPeriod::getStart).min(Comparator.naturalOrder()).get());
            LocalDateTime end = DateUtils.lastMomentOfDay(studyPeriods.stream().map(StudyPeriod::getEnd).max(Comparator.naturalOrder()).get());
            // formal learnings for given curriculum versions and date period
            qb = new JpaNativeQueryBuilder("from apel_application_formal_subject_or_module aafsm " +
                    "join apel_application_record aar on aafsm.apel_application_record_id = aar.id " +
                    "join apel_application aa on aar.apel_application_id = aa.id " +
                    "left join apel_school aps on aafsm.apel_school_id = aps.id " +
                    "left join apel_application_formal_replaced_subject_or_module aafrsm on aar.id = aafrsm.apel_application_record_id and aafrsm.curriculum_version_omodule_id is not null " +
                    "left join curriculum_version_omodule cvo on coalesce(aafsm.curriculum_version_omodule_id, aafrsm.curriculum_version_omodule_id) = cvo.id " +
                    "left join curriculum_version_hmodule cvh on aafsm.curriculum_version_hmodule_id = cvh.id");
            qb.requiredCriteria("aa.inserted >= :start", "start", start);
            qb.requiredCriteria("aa.inserted <= :end", "end", end);
            qb.requiredCriteria("aa.status_code != :draft", "draft", ApelApplicationStatus.VOTA_STAATUS_K);
            qb.requiredCriteria("(cvo.curriculum_version_id in (:cv) or cvh.curriculum_version_id in (:cv))", "cv", cvIds);

            List<?> data = qb.select("aa.id, aa.inserted, case when aafsm.is_my_school then '"+ClassifierUtil.COUNTRY_ESTONIA+"' else aps.country_code end, aafsm.transfer, aa.confirmed, aafsm.credits, coalesce(cvh.curriculum_version_id, cvo.curriculum_version_id), aa.status_code", em).getResultList();
            for(Object r : data) {
                VotaDto dto = findVota(resultAsLocalDate(r, 1), resultAsLong(r, 6), sortedStudyPeriods, dtosByPeriodAndCurriculum);
                if(dto != null) {
                    VotaStatistics stats = allStats.computeIfAbsent(dto, (key) -> new VotaStatistics());
                    stats.addFormal(r);
                }
            }

            // informal learnings for given curriculum versions and date period
            qb = new JpaNativeQueryBuilder("from apel_application_informal_subject_or_module aaism " +
                    "join apel_application_record aar on aaism.apel_application_record_id = aar.id " +
                    "join apel_application aa on aar.apel_application_id = aa.id " +
                    "left join curriculum_version_omodule cvo on aaism.curriculum_version_omodule_id = cvo.id " +
                    "left join curriculum_version_omodule_theme cvot on aaism.curriculum_version_omodule_theme_id = cvot.id " +
                    "left join curriculum_version_hmodule cvh on aaism.curriculum_version_hmodule_id = cvh.id");
            qb.requiredCriteria("aa.inserted >= :start", "start", start);
            qb.requiredCriteria("aa.inserted <= :end", "end", end);
            qb.requiredCriteria("aa.status_code != :draft", "draft", ApelApplicationStatus.VOTA_STAATUS_K);
            qb.requiredCriteria("(cvo.curriculum_version_id in (:cv) or cvh.curriculum_version_id in (:cv))", "cv", cvIds);

            data = qb.select("aa.id, aa.inserted, aaism.transfer, aa.confirmed, coalesce(cvh.total_credits, cvot.credits) as credits, coalesce(cvh.curriculum_version_id, cvo.curriculum_version_id), aa.status_code", em).getResultList();
            for(Object r : data) {
                VotaDto dto = findVota(resultAsLocalDate(r, 1), resultAsLong(r, 5), sortedStudyPeriods, dtosByPeriodAndCurriculum);
                if(dto != null) {
                    VotaStatistics stats = allStats.computeIfAbsent(dto, (key) -> new VotaStatistics());
                    stats.addInformal(r);
                }
            }

            // collect final results into dtos
            for(VotaDto dto : dtos) {
                VotaStatistics stats = allStats.computeIfAbsent(dto, (key) -> new VotaStatistics());
                stats.collect(dto);
            }
        }

        return result;
    }

    private Page<TeacherLoadDto> teacherLoad(Long schoolId, TeacherLoadCommand criteria, Pageable pageable, boolean higher) {
        Page<?> result;
        if(higher) {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_teacher sspt " + 
                    "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id " +
                    "join study_period sp on sp.id = ssp.study_period_id " +
                    "join study_year sy on sy.id = sp.study_year_id " +
                    "join classifier syc on sy.year_code = syc.code " +
                    "join teacher t on sspt.teacher_id = t.id " +
                    "join person p on t.person_id = p.id " +
                    /*"join subject_study_period_plan sspp on ssp.subject_id = sspp.subject_id and sspp.study_period_id = sp.id " +*/
                    "join subject_study_period_capacity ssppc on ssppc.subject_study_period_id = ssp.id").sort(pageable);
            qb.requiredCriteria("sy.school_id = :schoolId", "schoolId", schoolId);
            qb.requiredCriteria("sp.study_year_id = :studyYear", "studyYear", criteria.getStudyYear());
            qb.optionalCriteria("ssp.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
            qb.optionalCriteria("ssp.subject_id = :subject", "subject", criteria.getSubject());
            qb.optionalCriteria("sspt.teacher_id = :teacher", "teacher", criteria.getTeacher());
            qb.groupBy("syc.name_et, syc.name_en, sp.name_et, sp.name_en, p.firstname, p.lastname, sspt.teacher_id, sp.id");
            result = JpaQueryUtil.pagingResult(qb, "syc.name_et, syc.name_en, sp.name_et as study_period_name_et, sp.name_en as study_period_name_en, p.firstname, p.lastname, sum(ssppc.hours), sspt.teacher_id, sp.id", em, pageable);
        } else {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                    "from journal_teacher jt " +
                    "join journal j on j.id = jt.journal_id " +
                    "join study_year sy on j.study_year_id = sy.id " +
                    "join classifier syc on sy.year_code = syc.code " +
                    "join study_period sp on sp.study_year_id = sy.id " +
                    "join teacher t on jt.teacher_id = t.id inner join person p on t.person_id = p.id " +
                    "left join journal_capacity jc on j.id = jc.journal_id and jc.study_period_id = sp.id and (j.is_capacity_diff is null or j.is_capacity_diff = false)" +
                    "left join journal_teacher_capacity jtc on jt.id = jtc.journal_teacher_id and jtc.study_period_id = sp.id and j.is_capacity_diff = true")
                    .sort(pageable);
            qb.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
            qb.requiredCriteria("j.study_year_id = :studyYear", "studyYear", criteria.getStudyYear());
            qb.optionalCriteria("jc.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
            qb.optionalCriteria("jt.teacher_id = :teacher", "teacher", criteria.getTeacher());
            // module filter
            qb.optionalCriteria("j.id in (select jot.journal_id from journal_omodule_theme jot " +
                    "inner join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id " +
                    "inner join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "+
                    "where cvo.curriculum_module_id = :module)", "module", criteria.getModule());
            
            qb.filter("((jc.id is not null and (j.is_capacity_diff is null or j.is_capacity_diff = false)) or (jtc.id is not null and j.is_capacity_diff = true))");
            qb.groupBy("syc.name_et, syc.name_en, sp.name_et, sp.name_en, p.firstname, p.lastname, jt.teacher_id, coalesce(jc.study_period_id, jtc.study_period_id)");
            result = JpaQueryUtil.pagingResult(qb,
                    "syc.name_et, syc.name_en, sp.name_et as study_period_name_et, sp.name_en as study_period_name_en, p.firstname, p.lastname, " +
                    "coalesce(sum(jc.hours), 0) + coalesce(sum(jtc.hours), 0), jt.teacher_id, coalesce(jc.study_period_id, jtc.study_period_id) as study_period_id",
                    em, pageable);
        }

        // calculate used teacher id and study period id values for returned page
        Map<Long, Map<Long, List<Object>>> subjectRecords = new HashMap<>();
        Map<Long, Map<Long, List<Object>>> moduleRecords = new HashMap<>();
        Map<Long, Map<Long, Long>> actualLoadHours = new HashMap<>();
        Map<Long, Map<Long, BigDecimal>> coefficientLoadHours = new HashMap<>();
        if(!result.getContent().isEmpty()) {
            Set<Long> teachers = new HashSet<>();
            Set<Long> studyPeriods = new HashSet<>();
            for(Object record : result.getContent()) {
                teachers.add(resultAsLong(record, 7));
                studyPeriods.add(resultAsLong(record, 8));
            }

            if(higher) {
                // higher: select subjects by teacher and study period id: starting from SubjectStudyPeriodTeacher table
                JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_teacher sspt " +
                        "inner join subject_study_period ssp on sspt.subject_study_period_id = ssp.id "+
                        "inner join subject s on ssp.subject_id = s.id");

                qb.requiredCriteria("sspt.teacher_id in (:teacher)", "teacher", teachers);
                qb.requiredCriteria("ssp.study_period_id in (:studyPeriod)", "studyPeriod", studyPeriods);
                qb.optionalCriteria("ssp.subject_id = :subject", "subject", criteria.getSubject());

                List<?> subjects = qb.select("distinct s.name_et, s.name_en, s.code, sspt.teacher_id, ssp.study_period_id", em).getResultList();
                subjects.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 3), () -> subjectRecords, Collectors.groupingBy(r -> resultAsLong(r, 4))));

                qb = new JpaNativeQueryBuilder("from subject_study_period_teacher sspt"
                        + " join subject_study_period_capacity sspc on sspc.subject_study_period_id = sspt.subject_study_period_id"
                        + " join subject_study_period ssp on ssp.id = sspt.subject_study_period_id"
                        + " join study_period sp on sp.id = ssp.study_period_id"
                        + " join study_year sy on sy.id = sp.study_year_id"
                        + " join school_capacity_type sct on sct.school_id = sy.school_id and sct.capacity_type_code = sspc.capacity_type_code and sct.is_higher = true"
                        + " join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id and sctl.study_year_id = sp.study_year_id");

                qb.requiredCriteria("sspt.teacher_id in (:teacher)", "teacher", teachers);
                qb.requiredCriteria("ssp.study_period_id in (:studyPeriod)", "studyPeriod", studyPeriods);
                qb.optionalCriteria("ssp.subject_id = :subject", "subject", criteria.getSubject());

                qb.groupBy("sspt.teacher_id, ssp.study_period_id, sspc.capacity_type_code, sctl.load_percentage");
                
                String hoursByTypeQuery = qb.querySql("sspt.teacher_id, ssp.study_period_id, sctl.load_percentage * sum(sspc.hours) / 100.0 as hours", false);
                Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

                qb = new JpaNativeQueryBuilder("from (" + hoursByTypeQuery + ") bytype");
                qb.groupBy("teacher_id, study_period_id");
                
                List<?> coefficientLoad = qb.select("teacher_id, study_period_id, sum(hours)", em, parameters).getResultList();
                coefficientLoad.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), () -> coefficientLoadHours, Collectors.toMap(r -> resultAsLong(r, 1), r -> resultAsDecimal(r, 2))));
            } else {
                // vocational: select modules by teacher and study period id
                JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt " +
                        "inner join journal j on jt.journal_id = j.id " +
                        "inner join study_year sy on j.study_year_id = sy.id " +
                        "inner join study_period sp on sp.study_year_id = sy.id " +
                        "inner join journal_omodule_theme jot on j.id = jot.journal_id " +
                        "inner join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id "+
                        "inner join curriculum_version_omodule cvo on lpm.curriculum_version_omodule_id = cvo.id "+
                        "inner join curriculum_module cm on cvo.curriculum_module_id = cm.id " +
                        "inner join classifier m on cm.module_code = m.code " +
                        "inner join curriculum c on cm.curriculum_id = c.id");

                qb.requiredCriteria("jt.teacher_id in (:teacher)", "teacher", teachers);
                qb.requiredCriteria("sp.id in (:studyPeriod)", "studyPeriod", studyPeriods);
                qb.optionalCriteria("cvo.curriculum_module_id = :module", "module", criteria.getModule());

                List<?> modules = qb.select("distinct cm.name_et, cm.name_en, m.name_et as modulename_et, m.name_en as modulename_en, c.code, jt.teacher_id, sp.id", em).getResultList();
                modules.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 5), () -> moduleRecords, Collectors.groupingBy(r -> resultAsLong(r, 6))));

                qb = new JpaNativeQueryBuilder("from journal_teacher jt"
                        + " join journal j on j.id = jt.journal_id"
                        + " left join journal_capacity jc on jc.journal_id = j.id and (j.is_capacity_diff is null or j.is_capacity_diff = false)"
                        + " left join journal_teacher_capacity jtc on jtc.journal_teacher_id = jt.id and j.is_capacity_diff = true"
                        + " join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id or jct.id = jtc.journal_capacity_type_id"
                        + " join study_period sp on sp.id = jc.study_period_id or sp.id = jtc.study_period_id"
                        + " join school_capacity_type sct on sct.school_id = j.school_id and sct.capacity_type_code = jct.capacity_type_code and sct.is_higher = false"
                        + " join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id and sctl.study_year_id = sp.study_year_id");

                qb.requiredCriteria("jt.teacher_id in (:teacher)", "teacher", teachers);
                qb.requiredCriteria("((jc.study_period_id in (:studyPeriod) and (j.is_capacity_diff is null or j.is_capacity_diff = false)) "
                        + "or (jtc.study_period_id in (:studyPeriod) and j.is_capacity_diff = true))", "studyPeriod", studyPeriods);
                qb.optionalCriteria("jt.journal_id in (select jot.journal_id from journal_omodule_theme jot " +
                        "join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id " +
                        "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "+
                        "where cvo.curriculum_module_id = :module)", "module", criteria.getModule());

                qb.groupBy("jt.teacher_id, coalesce(jc.study_period_id, jtc.study_period_id), jct.capacity_type_code, sctl.load_percentage");
                
                String hoursByTypeQuery = qb.querySql("jt.teacher_id, coalesce(jc.study_period_id, jtc.study_period_id) as study_period_id,"
                        + " sctl.load_percentage * (coalesce(sum(jc.hours), 0) + coalesce(sum(jtc.hours), 0)) / 100.0 as hours", false);
                Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

                qb = new JpaNativeQueryBuilder("from (" + hoursByTypeQuery + ") bytype");
                qb.groupBy("teacher_id, study_period_id");
                
                List<?> coefficientLoad = qb.select("teacher_id, study_period_id, sum(hours)", em, parameters).getResultList();
                coefficientLoad.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), () -> coefficientLoadHours, Collectors.toMap(r -> resultAsLong(r, 1), r -> resultAsDecimal(r, 2))));
            }

            // actual load
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_object tto " +
                    "inner join timetable t on tto.timetable_id = t.id " +
                    "inner join timetable_event te on te.timetable_object_id = tto.id " +
                    "inner join timetable_event_time tet on tet.timetable_event_id = te.id " +
                    "inner join timetable_event_teacher tete on tete.timetable_event_time_id = tet.id");

            qb.requiredCriteria("tete.teacher_id in (:teacher)", "teacher", teachers);
            qb.requiredCriteria("t.study_period_id in (:studyPeriod)", "studyPeriod", studyPeriods);
            if (higher) {
                qb.optionalCriteria("tto.subject_study_period_id in (select id from subject_study_period" +
                        " where subject_id = :subject)", "subject", criteria.getSubject());
            } else {
                qb.optionalCriteria("tto.journal_id in (select jot.journal_id from journal_omodule_theme jot " +
                        "join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id " +
                        "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "+
                        "where cvo.curriculum_module_id = :module)", "module", criteria.getModule());
            }

            qb.groupBy("tete.teacher_id, t.study_period_id");
            List<?> actualLoad = qb.select("tete.teacher_id, t.study_period_id, sum(coalesce(te.lessons, 1))", em).getResultList();
            actualLoad.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), () -> actualLoadHours, Collectors.toMap(r -> resultAsLong(r, 1), r -> resultAsLong(r, 2))));
        }

        return result.map(r -> {
            Long teacherId = resultAsLong(r, 7);
            Long studyPeriodId = resultAsLong(r, 8);
            return new TeacherLoadDto(r, subjectRecords.computeIfAbsent(teacherId, key -> new HashMap<>()).get(studyPeriodId), 
                    moduleRecords.computeIfAbsent(teacherId, key -> new HashMap<>()).get(studyPeriodId), 
                    actualLoadHours.computeIfAbsent(teacherId, key -> new HashMap<>()).get(studyPeriodId),
                    coefficientLoadHours.computeIfAbsent(teacherId, key -> new HashMap<>()).get(studyPeriodId));
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

    private static VotaDto findVota(LocalDate inserted, Long cvId, List<StudyPeriod> studyPeriods, Map<Long, Map<Long, VotaDto>> dtosByPeriodAndCurriculum) {
        // study period by inserted date
        // first study period which is not ended
        StudyPeriod sp = studyPeriods.stream().filter(r -> !r.getEnd().isBefore(inserted)).findFirst().orElse(null);
        if(sp == null) {
            return null;
        }
        return dtosByPeriodAndCurriculum.get(sp.getId()).get(cvId);
    }

    private static class StudyPeriod {

        private final Long id;
        private final LocalDate start;
        private final LocalDate end;

        public StudyPeriod(Long id, LocalDate start, LocalDate end) {
            this.id = id;
            this.start = start;
            this.end = end;
        }

        public Long getId() {
            return id;
        }

        public LocalDate getStart() {
            return start;
        }

        public LocalDate getEnd() {
            return end;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof StudyPeriod) && id.equals(((StudyPeriod)o).getId());
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    private static class VotaStatistics {

        private Set<Long> applications = new HashSet<>();
        private BigDecimal totalCredits = BigDecimal.ZERO;
        private BigDecimal acceptedCredits = BigDecimal.ZERO;
        private BigDecimal totalLocalCredits = BigDecimal.ZERO;
        private BigDecimal acceptedLocalCredits = BigDecimal.ZERO;
        private BigDecimal totalAbroadCredits = BigDecimal.ZERO;
        private BigDecimal acceptedAbroadCredits = BigDecimal.ZERO;

        public VotaStatistics() {
        }

        public void collect(VotaDto dto) {
            dto.setApplicationCount(Long.valueOf(applications.size()));
            dto.setTotalCredits(totalCredits);
            dto.setAcceptedCredits(acceptedCredits);
            dto.setTotalLocalCredits(totalLocalCredits);
            dto.setAcceptedLocalCredits(acceptedLocalCredits);
            dto.setTotalAbroadCredits(totalAbroadCredits);
            dto.setAcceptedAbroadCredits(acceptedAbroadCredits);
        }

        public void addInformal(Object r) {
            Long applicationId = resultAsLong(r, 0);
            applications.add(applicationId);
            boolean accepted = isAccepted(resultAsBoolean(r, 2), resultAsString(r, 6));
            BigDecimal credits = resultAsDecimal(r, 4);
            if(credits != null) {
                totalCredits = totalCredits.add(credits);
                if(accepted) {
                    acceptedCredits = acceptedCredits.add(credits);
                }
                totalLocalCredits = totalLocalCredits.add(credits);
                if(accepted) {
                    acceptedLocalCredits = acceptedLocalCredits.add(credits);
                }
            }
        }

        public void addFormal(Object r) {
            Long applicationId = resultAsLong(r, 0);
            applications.add(applicationId);
            String country = resultAsString(r, 2);
            boolean accepted = isAccepted(resultAsBoolean(r, 3), resultAsString(r, 7));
            BigDecimal credits = resultAsDecimal(r, 5);
            totalCredits = totalCredits.add(credits);
            if(accepted) {
                acceptedCredits = acceptedCredits.add(credits);
            }
            if(ClassifierUtil.COUNTRY_ESTONIA.equals(country)) {
                totalLocalCredits = totalLocalCredits.add(credits);
                if(accepted) {
                    acceptedLocalCredits = acceptedLocalCredits.add(credits);
                }
            } else {
                totalAbroadCredits = totalAbroadCredits.add(credits);
                if(accepted) {
                    acceptedAbroadCredits = acceptedAbroadCredits.add(credits);
                }
            }
        }

        private static boolean isAccepted(Boolean transfer, String applicationStatus) {
            return Boolean.TRUE.equals(transfer) && ApelApplicationStatus.VOTA_STAATUS_C.name().equals(applicationStatus);
        }
    }
    
}
