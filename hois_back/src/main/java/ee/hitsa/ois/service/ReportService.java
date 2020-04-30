package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.xml.ws.Holder;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.report.SchoolQuery;
import ee.hitsa.ois.domain.report.SchoolQueryCriteria;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.ExmatriculationReason;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.ReportStudentData;
import ee.hitsa.ois.enums.Sex;
import ee.hitsa.ois.enums.StudentCountType;
import ee.hitsa.ois.enums.StudentMovementType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.report.CurriculumCompletionCommand;
import ee.hitsa.ois.web.commandobject.report.CurriculumSubjectsCommand;
import ee.hitsa.ois.web.commandobject.report.ForeignStudentStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.GuestStudentStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.IndividualCurriculumStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.Order;
import ee.hitsa.ois.web.commandobject.report.QuerySaveCommand;
import ee.hitsa.ois.web.commandobject.report.ScholarshipStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.SchoolQueryCommand;
import ee.hitsa.ois.web.commandobject.report.StudentCountCommand;
import ee.hitsa.ois.web.commandobject.report.StudentDataCommand;
import ee.hitsa.ois.web.commandobject.report.StudentMovementCommand;
import ee.hitsa.ois.web.commandobject.report.StudentSearchCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsByPeriodCommand;
import ee.hitsa.ois.web.commandobject.report.StudentStatisticsCommand;
import ee.hitsa.ois.web.commandobject.report.SubjectStudyPeriodDataCommand;
import ee.hitsa.ois.web.commandobject.report.TeacherLoadCommand;
import ee.hitsa.ois.web.commandobject.report.VotaCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;
import ee.hitsa.ois.web.dto.report.ClassifierResult;
import ee.hitsa.ois.web.dto.report.CurriculumCompletionDto;
import ee.hitsa.ois.web.dto.report.CurriculumSubjectsDto;
import ee.hitsa.ois.web.dto.report.ForeignStudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.GuestStudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.IndividualCurriculumSatisticsDto;
import ee.hitsa.ois.web.dto.report.ReportStudentDataDto;
import ee.hitsa.ois.web.dto.report.ScholarshipReportDto;
import ee.hitsa.ois.web.dto.report.SchoolQueryDto;
import ee.hitsa.ois.web.dto.report.StudentCountDto;
import ee.hitsa.ois.web.dto.report.StudentMovementDto;
import ee.hitsa.ois.web.dto.report.StudentRepresentativeDto;
import ee.hitsa.ois.web.dto.report.StudentSearchDto;
import ee.hitsa.ois.web.dto.report.StudentStatisticsDto;
import ee.hitsa.ois.web.dto.report.SubjectStudyPeriodDataDto;
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
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private ClassifierService classifierService;

    private static final String DATA_TRANSFER_ROWS = "DATA_TRANSFER_PROCESS";

    /**
     * Students report
     *
     * @param schoolId
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<StudentSearchDto> students(HoisUserDetails user, StudentSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s inner join person p on s.person_id = p.id " +
                "left join curriculum_version cv on s.curriculum_version_id = cv.id " +
                "left join curriculum c on cv.curriculum_id = c.id " +
                "left join student_group sg on s.student_group_id = sg.id " +
                "left join student_curriculum_completion scc on scc.student_id = s.id").sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("c.id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.optionalContains("s.study_company", "studyCompany", criteria.getStudyCompany());
        qb.optionalContains("s.previous_school_name", "previousSchoolName", criteria.getPreviousSchoolName());
        qb.optionalCriteria("coalesce(p.idcode, p.foreign_idcode) = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("p.birthdate >= :birthdateFrom", "birthdateFrom", criteria.getBirthdateFrom());
        qb.optionalCriteria("p.birthdate <= :birthdateThru", "birthdateThru", criteria.getBirthdateThru());
        qb.optionalCriteria("s.study_start >= :studyStartFrom", "studyStartFrom", criteria.getStudyStartFrom());
        qb.optionalCriteria("s.study_start <= :studyStartThru", "studyStartThru", criteria.getStudyStartThru());
        qb.optionalCriteria("s.nominal_study_end >= :nominalStudyEndFrom", "nominalStudyEndFrom", criteria.getNominalStudyEndFrom());
        qb.optionalCriteria("s.nominal_study_end <= :nominalStudyEndThru", "nominalStudyEndThru", criteria.getNominalStudyEndThru());
        qb.optionalCriteria("c.orig_study_level_code = :studyLevel", "studyLevel", criteria.getStudyLevel());
        qb.optionalCriteria("cv.id in (:curriculumVersions)", "curriculumVersions", criteria.getCurriculumVersions());
        qb.optionalCriteria("s.student_group_id in (:studentGroups)", "studentGroups", criteria.getStudentGroups());
        qb.optionalCriteria("s.study_load_code = :studyLoad", "studyLoad", criteria.getStudyLoad());
        qb.optionalCriteria("s.study_form_code = :studyForm", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("s.previous_study_level_code = :previousStudyLevel", "previousStudyLevel", criteria.getPreviousStudyLevel());
        qb.optionalCriteria("s.status_code = :status", "status", criteria.getStatus());
        qb.optionalCriteria("s.fin_code = :fin", "fin", criteria.getFin());
        qb.optionalCriteria("s.fin_specific_code = :fin", "fin", criteria.getFinSpecific());
        qb.optionalCriteria("s.dormitory_code = :dormitory", "dormitory", criteria.getDormitory());
        qb.optionalCriteria("s.language_code = :language", "language", criteria.getLanguage());

        Page<StudentSearchDto> result = JpaQueryUtil.pagingResult(qb, "s.id, p.firstname, p.lastname, coalesce(p.idcode, p.foreign_idcode) as idcode, " +
                "s.study_start, c.orig_study_level_code, cv.code, c.name_et, c.name_en, c.mer_code, sg.code as student_group_code, " +
                "s.study_load_code, s.study_form_code, s.status_code, s.fin_code, s.fin_specific_code, s.language_code, s.email, scc.credits, " +
                "s.dormitory_code, s.type_code as studentType, s.previous_study_level_code, s.previous_school_name, s.study_company, s.nominal_study_end"
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
            if (Boolean.TRUE.equals(criteria.getDisplayRepresentative())) {
                // Load representatives for every student
                attachStudentRepresentatives(studentIds, result);
            }
        }
        return result;
    }
    
    private void attachStudentRepresentatives(Set<Long> studentIds, Page<StudentSearchDto> result) {
        List<?> data = em.createNativeQuery("select distinct sr.student_id, p.firstname, p.lastname, p.phone, p.email " +
                "from student_representative sr " +
                "join person p on sr.person_id = p.id " +
                "where sr.student_id in (?1) " + 
                "and sr.is_student_visible = true")
                .setParameter(1, studentIds)
                .getResultList();
        Map<Long, List<Object>> representatives = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0)));
        for(StudentSearchDto dto : result.getContent()) {
            List<?> studentRepresentatives = representatives.getOrDefault(dto.getId(), Collections.emptyList());
            dto.setStudentRepresentatives(StreamUtil.toMappedList(r -> {
                return new StudentRepresentativeDto(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4));
            }, studentRepresentatives));
        }
    }

    /**
     * Students report in excel format
     *
     * @param schoolId
     * @param criteria
     * @return
     */
    public byte[] studentsAsExcel(HoisUserDetails user, StudentSearchCommand criteria) {
        List<StudentSearchDto> students = students(user, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();
        students.stream().forEach(p -> p.setStudentRepresentativesString(p.getStudentRepresentatives().stream()
                        .map(s ->(optionalAdd(s.getFirstname())
                                + optionalAdd(s.getLastname())
                                + optionalAdd(s.getPhone())
                                + optionalAdd(s.getEmail())).trim()).collect(Collectors.toList())));
        students.stream().forEach(p -> p.setOccupationCertificatesString(p.getOccupationCertificates().stream()
                .map(s -> s.getCertificateNr() + (s.getOccupation() != null ? ' ' + s.getOccupation().getNameEt() : "")).collect(Collectors.toList())));
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("today", LocalDate.now());
        data.put("students", students);
        return xlsService.generate("students.xls", data);
    }
    
    private static String optionalAdd(String field) {
        if (field != null)  return " " + field;
        return "";
    }
    
    public Page<StudentCountDto> studentCount(HoisUserDetails user, StudentCountCommand criteria, Pageable pageable) {
        StudentCountType resultType = StudentCountType.valueOf(criteria.getResultType());
        List<String> values = new ArrayList<>();
        if (resultType.equals(StudentCountType.COUNT_STAT_AGE)) {
            int step = criteria.getAgeStep().intValue();
            // additional check so that loop wouldnt take too long or stay stuck
            if (step < 1 || criteria.getAgeFrom().intValue() < 1) return null;
            for (int from = criteria.getAgeFrom().intValue(); from < 100; from = from + step) {
                String label = step != 1 ? from + ".." + (from + step - 1) : String.valueOf(from);
                values.add("(" + from + ", " + (from + step - 1) + ", '" + label +  "')");
            }
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on s.person_id = p.id "
                + (StudentCountType.COUNT_STAT_AGE.equals(resultType) ? " join (values " + String.join(", ", values) + ") "
                        + "as v(ageFrom, ageThru, label) on extract(year from age(:paramDate, p.birthdate)) between v.ageFrom and v.ageThru " : "")
                + "left join (select " + (criteria.getThru() == null ? "distinct on(sh.student_id) " : "distinct on(sh.student_id, sh.status_code) ") 
                    + "sh.student_id, sh.valid_from, sh.valid_thru, sh.status_code, sh.curriculum_version_id, sh.student_group_id "
                    + "from student_history sh "
                    + "where sh.status_code in (:statusCodes) "
                    // data transer generated student history should be ignored
                    + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
                    + (criteria.getThru() != null ? 
                            "and cast(sh.valid_from as date) <= :studyEnd and (cast(sh.valid_thru as date) >= :studyStart or sh.valid_thru is null)"
                            : "and cast(sh.valid_from as date) <= :studyStart and (cast(sh.valid_thru as date) > :studyStart or sh.valid_thru is null)")
                    + " order by sh.student_id" + (criteria.getThru() != null ? ", sh.status_code" : "") + " , sh.valid_from"
                    + ") SH1 on SH1.student_id = s.id "
                + "left join curriculum_version cv on coalesce(SH1.curriculum_version_id, s.curriculum_version_id) = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join classifier_connect cc on cc.classifier_code = c.isced_class_code and cc.main_classifier_code = '" + MainClassCode.ISCED_SUUN.name() + "' "
                    + "and :resultType = '" + StudentCountType.COUNT_STAT_ISCED_VALD.name() +  "' "
                + "left join classifier_connect cc_1 on cc_1.classifier_code = cc.connect_classifier_code "
                    + "and :resultType = '" + StudentCountType.COUNT_STAT_ISCED_VALD.name() +  "' "
                + "left join classifier c_connected on c_connected.code = cc_1.connect_classifier_code "
                    + "and :resultType = '" + StudentCountType.COUNT_STAT_ISCED_VALD.name() +  "' "
                + "left join (select coalesce(SD1.id, SD2.id) as id, coalesce(SD1.name_et, SD2.name_et) as name_et, coalesce(coalesce(SD1.name_en, SD1.name_et), coalesce(SD2.name_en, SD2.name_et)) as name_en, cv.id as version_id from "
                    + "curriculum_version cv "
                    + "left join school_department SD1 on SD1.id = cv.school_department_id "
                    + "left join (select distinct on(cd.curriculum_id) sd.id, sd.name_et, sd.name_en, cd.curriculum_id from "
                        + "school_department sd "
                        + "join curriculum_department cd on cd.school_department_id = sd.id "
                        + "order by cd.curriculum_id, sd.name_et) SD2 on SD2.curriculum_id = cv.curriculum_id"
                        + ") SD1 on SD1.version_id = cv.id and :resultType = '" + StudentCountType.COUNT_STAT_STRUCTURAL_UNIT.name() + "' "
                + "left join student_group sg on coalesce(SH1.student_group_id, s.student_group_id) = sg.id "
                + "left join classifier oslc on oslc.code = c.orig_study_level_code").sort(pageable);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter(":resultType = :resultType");
        qb.parameter("resultType", criteria.getResultType());
        // type codes should not change
        qb.requiredCriteria("s.type_code in (:studentTypes)", "studentTypes", criteria.getStudentTypes());
        if (criteria.getThru() == null) {
            qb.filter("((SH1.student_id is null and (s.study_start <= :studyStart or s.study_start is null) and (s.study_end > :studyStart or s.study_end is null) and s.status_code in :statusCodes) "
                    + "or (cast(SH1.valid_from as date) <= :studyStart and (cast(SH1.valid_thru as date) > :studyStart or SH1.valid_thru is null) and SH1.status_code in :statusCodes))");
        } else {
            qb.requiredCriteria("((SH1.student_id is null and (s.study_start <= :studyEnd or s.study_start is null) and (s.study_end >= :studyStart or s.study_end is null) and s.status_code in :statusCodes) "
                    + "or (cast(SH1.valid_from as date) <= :studyEnd and (cast(SH1.valid_thru as date) >= :studyStart or SH1.valid_thru is null) and SH1.status_code in :statusCodes))", "studyEnd", criteria.getThru());
        }
        qb.parameter("studyStart", criteria.getFrom());
        qb.parameter("statusCodes", StudentStatus.STUDENT_STATUS_ACTIVE);
        if (StudentCountType.COUNT_STAT_AGE.equals(resultType)) {
            LocalDate timestamp = criteria.getThru() != null ? criteria.getThru() : LocalDate.now();
            qb.parameter("paramDate", timestamp);
            qb.requiredCriteria("extract(year from age(:paramDate, p.birthdate)) >= :minAge", "minAge", criteria.getAgeFrom());
        }
        List<String> groupByList = new ArrayList<>();
        List<String> select = new ArrayList<>();
        select.add("count(s.id)");
        extendSelectPerCriteria(criteria, select);
        extendSelectAndGroupByPerResultType(resultType, groupByList, select, pageable, qb);
        qb.groupBy(String.join(", ", groupByList));
        Page<StudentCountDto> result = JpaQueryUtil.pagingResult(qb, String.join(", ", select), em, pageable).map(r -> {
            StudentCountDto dto = new StudentCountDto();
            if (resultType.equals(StudentCountType.COUNT_STAT_OVERALL)) {
                mapOverallCountResults(r, criteria, dto);
                return dto;
            }
            mapOtherCountResults(r, criteria, dto);
            return dto;
        });
        List<StudentCountDto> modifiedResult = new ArrayList<>();
        modifiedResult.addAll(result.getContent());
        if (result.getContent().size() != pageable.getPageSize() && !resultType.equals(StudentCountType.COUNT_STAT_OVERALL)) {
            addSum(qb, modifiedResult, criteria);
        }
        return new PageImpl<>(modifiedResult, pageable, result.getTotalElements() + 1);
    }
    
    /**
     * Student count report in excel format
     *
     * @param user
     * @param criteria
     * @return
     */
    public byte[] studentsCountAsExcel(HoisUserDetails user, StudentCountCommand criteria) {
        List<StudentCountDto> students = studentCount(user, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();
        Map<String, Object> data = new HashMap<>();
        String name = "report.studentCount." + criteria.getResultType();
        data.put("name", name);
        data.put("criteria", criteria);
        data.put("students", students);
        if (Boolean.TRUE.equals(criteria.getPerStatus()) && Boolean.TRUE.equals(criteria.getPerSex())) {
            return xlsService.generate("studentscountboth.xls", data);
        } else if (Boolean.TRUE.equals(criteria.getPerStatus())) {
            return xlsService.generate("studentscountstatus.xls", data);
        } else if (Boolean.TRUE.equals(criteria.getPerSex())) {
            return xlsService.generate("studentscountsex.xls", data);
        }
        return xlsService.generate("studentscountnone.xls", data);
    }
    
    public Page<StudentMovementDto> studentMovement(HoisUserDetails user, StudentMovementCommand criteria, Pageable pageable) {
        StudentMovementType queryType = StudentMovementType.valueOf(criteria.getQueryType());
        List<String> groupByList = new ArrayList<>();
        List<String> select = new ArrayList<>();
        extendSelectPerCriteria(select, criteria);
        extendSelectAndGroupByPerResultType(queryType, groupByList, select);
        String sort = sortPerResultType(queryType, pageable);
        JpaNativeQueryBuilder qb = createQuery(select, user.getSchoolId(), groupByList, pageable, criteria, sort);
        List<String> subSelect = new ArrayList<>();
        extendSelectPerAlias(subSelect);
        extendSelectPerResultTypeAlias(subSelect, queryType);
        Page<StudentMovementDto> result = JpaQueryUtil.pagingResult(qb, String.join(", ", subSelect)
        , em, pageable).map(r -> {
            StudentMovementDto dto = new StudentMovementDto();
            if (queryType.equals(StudentMovementType.MOVEMENT_STAT_SUM)) {
                mapOverallCountResults(r, dto);
                return dto;
            }
            mapOtherCountResults(r, criteria, dto);
            return dto;
        });
        List<StudentMovementDto> modifiedResult = new ArrayList<>();
        modifiedResult.addAll(result.getContent());
        if (result.getContent().size() != pageable.getPageSize() && !queryType.equals(StudentMovementType.MOVEMENT_STAT_SUM)) {
            select = new ArrayList<>();
            extendSelectPerCriteria(select, criteria);
            qb = createQuery(select, user.getSchoolId(), new ArrayList<>(), pageable, criteria, null);
            addSum(qb, modifiedResult);
        }
        return new PageImpl<>(modifiedResult, pageable, result.getTotalElements() + 1);
    }
    
    private static void extendSelectPerResultTypeAlias(List<String> select, StudentMovementType queryType) {
        switch (queryType) {
        case MOVEMENT_STAT_STUDY_LEVEL:
            select.add("data.orig_study_level_code, data.name_et, data.name_en, null as mer_code");
            break;
        case MOVEMENT_STAT_STUDENT_GROUP:
            select.add("data.id, data.codeEt, data.codeEn, null as mer_code");
            break;
        case MOVEMENT_STAT_CURRICULUM_GROUP:
            select.add("data.isced_class_code, data.iscedEt, data.iscedEn, null as mer_code");
            break;
        case MOVEMENT_STAT_STRUCTURAL_UNIT:
            select.add("data.id, data.name_et, data.name_en, null as mer_code");
            break;
        case MOVEMENT_STAT_CURRICULA:
            select.add("data.id, data.curriculaEt, data.curriculaEn, data.mer_code as mer_code");
            break;
        case MOVEMENT_STAT_COURSE:
            select.add("null, data.courseEt, data.courseEn, null as mer_code");
            break;
        default:
            break;
        }
    }

    private static JpaNativeQueryBuilder createQuery(List<String> select, Long schoolId, List<String> groupByList,
            Pageable pageable, StudentMovementCommand criteria, String sort) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from (select " + String.join(", ", select) 
        + " from student s "
        + "join person p on s.person_id = p.id "
        // studying from
        + "left join (select distinct on (ss.id) ss.id, "
            + "coalesce(sh.curriculum_version_id, ss.curriculum_version_id) as curriculum_version_id, "
            + "coalesce(sh.student_group_id, ss.student_group_id) as student_group_id from student ss " 
            + "left join student_history sh on sh.student_id = ss.id "
                + "and sh.status_code in :statusCodes "
                + "and cast(sh.valid_from as date) < :studystart and (cast(sh.valid_thru as date) >= :studystart or sh.valid_thru is null) "
                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
            // data transer generated student history should be ignored
            + "where ("
                + "(sh.id is null and (ss.study_start < :studystart or ss.study_start is null) and (ss.study_end >= :studystart or ss.study_end is null) and ss.status_code in :statusCodes) "
                + "or sh.id is not null) "
            + "order by ss.id, sh.valid_thru NULLS FIRST, sh.valid_from DESC) S1 on S1.id = s.id "
         // studying thru
        + "left join (select distinct on (ss.id) ss.id, "
            + "coalesce(sh.curriculum_version_id, ss.curriculum_version_id) as curriculum_version_id, "
            + "coalesce(sh.student_group_id, ss.student_group_id) as student_group_id from student ss "
            + "left join student_history sh on sh.student_id = ss.id "
                + "and sh.status_code in :statusCodes "
                + "and cast(sh.valid_from as date) <= :studyEnd and (cast(sh.valid_thru as date) > :studyEnd or sh.valid_thru is null) "
                + "and sh.inserted_by != 'DATA_TRANSFER_PROCESS' and upper(sh.inserted_by) not like upper('%andmete%kanne%') "
            // data transer generated student history should be ignored
            + "where ("
                + "(sh.id is null and (ss.study_start <= :studyEnd or ss.study_start is null) and (ss.study_end > :studyEnd or ss.study_end is null) and ss.status_code in :statusCodes) "
                + "or sh.id is not null) "
            + "order by ss.id, sh.valid_thru NULLS FIRST, sh.valid_from DESC) S2 on S2.id = s.id "
        // can be 2 of these
        + ((StudentMovementType.MOVEMENT_STAT_CURRICULA.name().equals(criteria.getQueryType()) || StudentMovementType.MOVEMENT_STAT_CURRICULUM_GROUP.name().equals(criteria.getQueryType())
                || StudentMovementType.MOVEMENT_STAT_STRUCTURAL_UNIT.name().equals(criteria.getQueryType()) || StudentMovementType.MOVEMENT_STAT_STUDY_LEVEL.name().equals(criteria.getQueryType())) ?
            "left join curriculum_version cv on (cv.id = S1.curriculum_version_id or cv.id = S2.curriculum_version_id or cv.id = s.curriculum_version_id) "
            + "left join curriculum c on cv.curriculum_id = c.id "
            + "left join (select coalesce(SD1.id, SD2.id) as id, coalesce(SD1.name_et, SD2.name_et) as name_et, coalesce(coalesce(SD1.name_en, SD1.name_et), coalesce(SD2.name_en, SD2.name_et)) as name_en, cv.id as version_id from "
                + "curriculum_version cv "
                + "left join school_department SD1 on SD1.id = cv.school_department_id "
                + "left join (select distinct on(cd.curriculum_id) sd.id, sd.name_et, sd.name_en, cd.curriculum_id from "
                    + "school_department sd "
                    + "join curriculum_department cd on cd.school_department_id = sd.id "
                    + "order by cd.curriculum_id, sd.name_et) SD2 on SD2.curriculum_id = cv.curriculum_id"
                    + ") SD1 on SD1.version_id = cv.id and :queryType = '" + StudentMovementType.MOVEMENT_STAT_STRUCTURAL_UNIT.name() + "' "
            + "left join classifier oslc on oslc.code = c.orig_study_level_code "
            + "left join classifier isced on isced.code = c.isced_class_code " : "")
        // can be 2 of these
        + ((StudentMovementType.MOVEMENT_STAT_STUDENT_GROUP.name().equals(criteria.getQueryType()) || StudentMovementType.MOVEMENT_STAT_COURSE.name().equals(criteria.getQueryType())) ? 
                "left join student_group sg on (sg.id = S1.student_group_id or sg.id = S2.student_group_id or sg.id = s.student_group_id)" : "")
        + "where s.school_id = :schoolId "
        + "and s.type_code != '" + StudentType.OPPUR_K.name() + "'"
        + (!groupByList.isEmpty() ? " group by " + String.join(", ", groupByList) : "")
        + (sort != null ? " order by " + sort : "")+ ") as data").sort(pageable);
        qb.filter(":queryType = :queryType");
        qb.filter(":studystart = :studystart");
        qb.filter(":studyEnd = :studyEnd");
        qb.filter(":ownWishReasons = :ownWishReasons");
        qb.filter(":studyStartDirectives = :studyStartDirectives");
        qb.filter(":statusCodes = :statusCodes");
        qb.filter(":endingDirectives = :endingDirectives");
        qb.filter(":schoolId = :schoolId");
        qb.parameter("queryType", criteria.getQueryType());
        qb.parameter("schoolId", schoolId);
        qb.parameter("studystart", criteria.getFrom());
        qb.parameter("studyEnd", criteria.getThru());
        qb.parameter("statusCodes", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.parameter("studyStartDirectives", EnumUtil.toNameList(DirectiveType.KASKKIRI_IMMAT, 
                DirectiveType.KASKKIRI_IMMATV,
                DirectiveType.KASKKIRI_ENNIST));
        qb.parameter("ownWishReasons", EnumUtil.toNameList(ExmatriculationReason.EKSMAT_POHJUS_D,
                ExmatriculationReason.EKSMAT_POHJUS_G_KUTSE,
                ExmatriculationReason.EKSMAT_POHJUS_J_KUTSE,
                ExmatriculationReason.EKSMAT_POHJUS_X,
                ExmatriculationReason.EKSMAT_POHJUS_Z,
                ExmatriculationReason.EKSMAT_POHJUS_L,
                ExmatriculationReason.EKSMAT_POHJUS_O,
                ExmatriculationReason.EKSMAT_POHJUS_R,
                ExmatriculationReason.EKSMAT_POHJUS_S,
                ExmatriculationReason.EKSMAT_POHJUS_T,
                ExmatriculationReason.EKSMAT_POHJUS_V,
                ExmatriculationReason.EKSMAT_POHJUS_W));
        qb.parameter("endingDirectives", EnumUtil.toNameList(DirectiveType.KASKKIRI_EKSMAT, 
                                                            DirectiveType.KASKKIRI_LOPET));
        qb.filter("(studyingBeforeFrom != 0 or startedStudyingDuringPeriod != 0 or exmatOwnWish != 0 or exmatElse != 0 "
                + "or finishedStudying != 0 or studyingAfterThru != 0)");
        return qb;
    }

    private static void extendSelectPerAlias(List<String> select) {
        select.add("studyingBeforeFrom");
        select.add("startedStudyingDuringPeriod");
        select.add("exmatOwnWish");
        select.add("exmatElse");
        select.add("finishedStudying");
        select.add("studyingAfterThru");
        select.add("case when (studyingBeforeFrom + startedStudyingDuringPeriod) > 0 then "
                + "round((exmatElse + exmatOwnWish) * 100.00 / (studyingBeforeFrom + startedStudyingDuringPeriod), 2) "
                + "else 0.00 end as exmatPercentage");
        select.add("case when (studyingBeforeFrom + startedStudyingDuringPeriod) > 0 then "
                + "round(finishedStudying * 100.00 / (studyingBeforeFrom + startedStudyingDuringPeriod), 2) "
                + "else 0.00 end as completionPercentage");
    }

    private static String sortPerResultType(StudentMovementType queryType, Pageable pageable) {
        if (pageable.getSort() == null) {
            switch (queryType) {
            case MOVEMENT_STAT_STUDY_LEVEL:
                return "oslc.name_et";
            case MOVEMENT_STAT_STUDENT_GROUP:
                return "sg.code";
            case MOVEMENT_STAT_CURRICULUM_GROUP:
                return "isced.name_et";
            case MOVEMENT_STAT_STRUCTURAL_UNIT:
                return "sd1.name_et, sd1.name_en";
            case MOVEMENT_STAT_CURRICULA:
                return "c.name_et, c.name_en";
            case MOVEMENT_STAT_COURSE:
                return "sg.course";
            default:
                break;
            }
        }
        return null;
    }

    public byte[] studentsMovementAsExcel(HoisUserDetails user, StudentMovementCommand criteria) {
        List<StudentMovementDto> students = studentMovement(user, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();
        Map<String, Object> data = new HashMap<>();
        String name = "report.studentMovement." + criteria.getQueryType();
        data.put("name", name);
        data.put("criteria", criteria);
        data.put("students", students);
        return xlsService.generate("studentsmovement.xls", data);
    }

    private static void mapOtherCountResults(Object r, StudentMovementCommand criteria, StudentMovementDto dto) {
        mapOverallCountResults(r, dto);
        dto.setObject(new AutocompleteResult(null, resultAsString(r, 9), resultAsString(r, 10)));
        dto.setHtmCode(resultAsString(r, 11));
        if (dto.getObject().getNameEt() == null) {
            if (StudentMovementType.MOVEMENT_STAT_STUDENT_GROUP.name().equals(criteria.getQueryType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma rühmata", "Without group"));
            } else if (StudentMovementType.MOVEMENT_STAT_CURRICULUM_GROUP.name().equals(criteria.getQueryType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma rühmata", "Without group"));
            } else if (StudentMovementType.MOVEMENT_STAT_COURSE.name().equals(criteria.getQueryType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma kursuseta", "Without course"));
            } else if (StudentMovementType.MOVEMENT_STAT_STRUCTURAL_UNIT.name().equals(criteria.getQueryType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma struktuuriüksuseta", "Without structural unit"));
            } else if (StudentMovementType.MOVEMENT_STAT_STUDY_LEVEL.name().equals(criteria.getQueryType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma õppetasemeta", "Without study level"));
            } else if (StudentMovementType.MOVEMENT_STAT_CURRICULA.name().equals(criteria.getQueryType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma õppekavata", "Without curricula"));
            }
        }
    }

    private void addSum(JpaNativeQueryBuilder qb, List<StudentMovementDto> modifiedResult) {
        qb.groupBy(null);
        qb.sort("1");
        ArrayList<String> select = new ArrayList<>();
        extendSelectPerAlias(select);
        Page<StudentMovementDto> sum = JpaQueryUtil.pagingResult(qb, String.join(", ", select), em, new PageRequest(0, Integer.MAX_VALUE)).map(r -> {
                    StudentMovementDto dto = new StudentMovementDto();
                    mapOverallCountResults(r, dto);
                    return dto;
                });
        if (sum.getContent().size() != 0) {
            StudentMovementDto dto = sum.getContent().get(0);
            dto.setObject(new AutocompleteResult(null, "Kokku", "Sum"));
            modifiedResult.add(dto);
        }
    }

    private static void mapOverallCountResults(Object r, StudentMovementDto dto) {
        dto.setStudyingFrom(resultAsLong(r, 0));
        dto.setStartedStudying(resultAsLong(r, 1));
        dto.setExmatOwnWish(resultAsLong(r, 2));
        dto.setExmatElse(resultAsLong(r, 3));
        dto.setCompleted(resultAsLong(r, 4));
        dto.setStudyingThru(resultAsLong(r, 5));
        dto.setExmatPercentage(JpaQueryUtil.resultAsDecimal(r,6));
        dto.setCompletionPercentage(JpaQueryUtil.resultAsDecimal(r,7));
    }

    private static void extendSelectAndGroupByPerResultType(StudentMovementType queryType, List<String> groupByList, List<String> select) {
        switch (queryType) {
        case MOVEMENT_STAT_STUDY_LEVEL:
            groupByList.add("c.orig_study_level_code, oslc.name_et, oslc.name_en");
            select.add("c.orig_study_level_code, oslc.name_et, oslc.name_en, null as mer_code");
            break;
        case MOVEMENT_STAT_STUDENT_GROUP:
            groupByList.add("sg.id, sg.code");
            select.add("sg.id, sg.code as codeEt, sg.code as codeEn, null as mer_code");
            break;
        case MOVEMENT_STAT_CURRICULUM_GROUP:
            groupByList.add("c.isced_class_code, isced.name_et, isced.name_en");
            select.add("c.isced_class_code, isced.name_et as iscedEt, isced.name_en as iscedEn, null as mer_code");
            break;
        case MOVEMENT_STAT_STRUCTURAL_UNIT:
            groupByList.add("SD1.id, SD1.name_et, SD1.name_en");
            select.add("SD1.id, SD1.name_et, SD1.name_en, null as mer_code");
            break;
        case MOVEMENT_STAT_CURRICULA:
            groupByList.add("c.id, c.name_et, c.name_en");
            select.add("c.id, c.name_et || ' (' || c.code || ')' as curriculaEt, c.name_en || ' (' || c.code || ')' as curriculaEn, c.mer_code as mer_code");
            break;
        case MOVEMENT_STAT_COURSE:
            groupByList.add("sg.course");
            select.add("null, sg.course\\:\\:character varying as courseEt, sg.course\\:\\:character varying as courseEn, null as mer_code");
            break;
        default:
            break;
        }
    }
    
    private static void extendSelectPerCriteria(List<String> select, StudentMovementCommand command) {
            select.add("sum(case when "
                    + "S1.id is not null "
                    + ((StudentMovementType.MOVEMENT_STAT_CURRICULA.name().equals(command.getQueryType()) || StudentMovementType.MOVEMENT_STAT_CURRICULUM_GROUP.name().equals(command.getQueryType())
                            || StudentMovementType.MOVEMENT_STAT_STRUCTURAL_UNIT.name().equals(command.getQueryType()) || StudentMovementType.MOVEMENT_STAT_STUDY_LEVEL.name().equals(command.getQueryType())) ? 
                            "and S1.curriculum_version_id = cv.id " : "")
                    + ((StudentMovementType.MOVEMENT_STAT_STUDENT_GROUP.name().equals(command.getQueryType()) || StudentMovementType.MOVEMENT_STAT_COURSE.name().equals(command.getQueryType())) ? 
                            "and (S1.student_group_id = sg.id or (S1.student_group_id is null and sg.id is null))" : "")
                    + "then 1 else 0 end) as studyingBeforeFrom");
            // study start directives are KASKKIRI_IMMAT, KASKKIRI_IMMATV and KASKKIRI_ENNIST
            select.add("sum(case when "
                    + "exists(select 1 from directive d join directive_student ds on d.id = ds.directive_id "
                    + "where ds.student_id = s.id "
                    + "and d.type_code in :studyStartDirectives  "
                    + "and d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                    + "and ds.canceled != true "
                    + "and d.confirm_date between :studystart and :studyEnd ) "
                    + "then 1 else 0 end) as startedStudyingDuringPeriod");
            select.add("sum(case when "
                    + "exists(select 1 from directive d join directive_student ds on d.id = ds.directive_id "
                    + "where ds.student_id = s.id "
                    + "and d.type_code = '" + DirectiveType.KASKKIRI_EKSMAT.name() + "' "
                    + "and d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                    + "and ds.reason_code in :ownWishReasons "
                    + "and ds.canceled != true "
                    + "and d.confirm_date between :studystart and :studyEnd ) "
                    + "then 1 else 0 end) as exmatOwnWish");
            select.add("sum(case when "
                    + "exists(select 1 from directive d join directive_student ds on d.id = ds.directive_id "
                    + "where ds.student_id = s.id "
                    + "and d.type_code = '" + DirectiveType.KASKKIRI_EKSMAT.name() + "' "
                    + "and d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                    + "and ds.reason_code not in (:ownWishReasons) "
                    + "and ds.canceled != true "
                    + "and d.confirm_date between :studystart and :studyEnd ) "
                    + "then 1 else 0 end) as exmatElse");
            select.add("sum(case when "
                    + "exists(select 1 from directive d join directive_student ds on d.id = ds.directive_id "
                    + "where ds.student_id = s.id "
                    + "and d.type_code = '" + DirectiveType.KASKKIRI_LOPET.name() + "' "
                    + "and d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                    + "and ds.canceled != true "
                    + "and d.confirm_date between :studystart and :studyEnd ) "
                    + "then 1 else 0 end) as finishedStudying");
            select.add("sum(case when "
                    + "S2.id is not null "
                    + ((StudentMovementType.MOVEMENT_STAT_CURRICULA.name().equals(command.getQueryType()) || StudentMovementType.MOVEMENT_STAT_CURRICULUM_GROUP.name().equals(command.getQueryType())
                            || StudentMovementType.MOVEMENT_STAT_STRUCTURAL_UNIT.name().equals(command.getQueryType()) || StudentMovementType.MOVEMENT_STAT_STUDY_LEVEL.name().equals(command.getQueryType())) ? 
                            "and S2.curriculum_version_id = cv.id " : "")
                    + ((StudentMovementType.MOVEMENT_STAT_STUDENT_GROUP.name().equals(command.getQueryType()) || StudentMovementType.MOVEMENT_STAT_COURSE.name().equals(command.getQueryType())) ? 
                            "and (S2.student_group_id = sg.id or (S2.student_group_id is null and sg.id is null))" : "")
                    + "then 1 else 0 end) as studyingAfterThru");
    }

    private void addSum(JpaNativeQueryBuilder qb, List<StudentCountDto> modifiedResult, StudentCountCommand criteria) {
        qb.groupBy(null);
        qb.sort("1");
        ArrayList<String> select = new ArrayList<>();
        select.add("count(s.id)");
        extendSelectPerCriteria(criteria, select);
        Page<StudentCountDto> sum = JpaQueryUtil.pagingResult(qb, String.join(", ", select), em, new PageRequest(0, Integer.MAX_VALUE)).map(r -> {
                    StudentCountDto dto = new StudentCountDto();
                    mapOverallCountResults(r, criteria, dto);
                    return dto;
                });
        if (sum.getContent().size() != 0) {
            StudentCountDto dto = sum.getContent().get(0);
            dto.setObject(new AutocompleteResult(null, "Kokku", "Sum"));
            modifiedResult.add(dto);
        }
    }

    private static void extendSelectAndGroupByPerResultType(StudentCountType resultType, List<String> groupByList,
            List<String> select, Pageable pageable, JpaNativeQueryBuilder qb) {
        switch (resultType) {
        case COUNT_STAT_STUDY_LEVEL:
            groupByList.add("c.orig_study_level_code, oslc.name_et, oslc.name_en");
            select.add("c.orig_study_level_code, oslc.name_et, oslc.name_en");
            break;
        case COUNT_STAT_STUDENT_GROUP:
            groupByList.add("sg.id, sg.code");
            select.add("sg.id, sg.code as codeEt, sg.code as codeEn");
            if (pageable.getSort() == null) {
                qb.sort("sg.code");
            }
            break;
        case COUNT_STAT_STRUCTURAL_UNIT:
            groupByList.add("SD1.id, SD1.name_et, SD1.name_en");
            select.add("SD1.id, SD1.name_et, SD1.name_en");
            if (pageable.getSort() == null) {
                qb.sort("sd1.name_et, sd1.name_en");
            }
            break;
        case COUNT_STAT_AGE:
            groupByList.add("v.label, v.agefrom");
            select.add("null, v.label as ageEt, v.label as ageEn");
            if (pageable.getSort() == null) {
                qb.sort("cast(v.agefrom as int)");
            }
            break;
        case COUNT_STAT_CURRICULA:
            groupByList.add("c.id, c.name_et, c.name_en");
            select.add("c.mer_code, c.name_et || ' (' || c.code || ')' as curriculaEt, c.name_en || ' (' || c.code || ')' as curriculaEn");
            if (pageable.getSort() == null) {
                qb.sort("c.name_et, c.name_en");
            }
            break;
        case COUNT_STAT_COURSE:
            groupByList.add("sg.course");
            select.add("null, sg.course\\:\\:character varying as courseEt, sg.course\\:\\:character varying as courseEn");
            if (pageable.getSort() == null) {
                qb.sort("sg.course");
            }
            break;
        case COUNT_STAT_ISCED_VALD:
            groupByList.add("c_connected.code, c_connected.name_et, c_connected.name_et");
            select.add("null, c_connected.name_et as iscedValdEt, c_connected.name_et as iscedValdEn");
            if (pageable.getSort() == null) {
                qb.sort("c_connected.name_et, c_connected.name_et");
            }
            break;
        default:
            break;
        }
    }
    
    private static String extendCase(String status, StudentCountCommand criteria) {
        String fullStatement = "";
        String studentStatement;
        String studentHistoryStatement;
        String checkStatement;
        if (criteria.getThru() != null) {
            studentStatement = "(SH1.student_id is null and s.status_code = '" + status + "')";
            studentHistoryStatement = "(SH1.status_code = '" + status + "' "
                    + "and cast(SH1.valid_from as date) <= :studyEnd "
                    + "and (cast(SH1.valid_thru as date) >= :studyStart or SH1.valid_thru is null))";
            checkStatement = "true";
        } else {
            studentStatement = "(s.status_code = '" + status + "')";
            studentHistoryStatement = "(SH1.status_code = '" + status + "' "
                    + "and cast(SH1.valid_from as date) <= :studyStart "
                    + "and (cast(SH1.valid_thru as date) > :studyStart or SH1.valid_thru is null))";
            // if statuses are different for student and student history and both are in date range, select the students status over history
            // there shouldn't be a student represented in both statuses at the same time
            checkStatement = "case when s.status_code != SH1.status_code "
                    + "and s.status_code in :statusCodes "
                    + "and cast(SH1.valid_from as date) <= :studyStart "
                    + "and SH1.status_code in :statusCodes "
                    + "and (cast(SH1.valid_thru as date) > :studyStart or cast(SH1.valid_thru as date) is null) "
                    + "then SH1.status_code = '" + status + "' else true end";
        }
        fullStatement = "(" +studentStatement + " or " + studentHistoryStatement + ") and " + checkStatement;
        return fullStatement;
    }

    private static void extendSelectPerCriteria(StudentCountCommand criteria, List<String> select) {
        if (Boolean.TRUE.equals(criteria.getPerSex()) && Boolean.TRUE.equals(criteria.getPerStatus())) {
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_N.name() + "' "
                                + "and " + extendCase(StudentStatus.OPPURSTAATUS_O.name(), criteria) + " then 1 else 0 end) as studyingwomen");
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_M.name() + "' "
                                + "and " + extendCase(StudentStatus.OPPURSTAATUS_O.name(), criteria) + " then 1 else 0 end) as studyingmen");
            select.add("sum(case when " + extendCase(StudentStatus.OPPURSTAATUS_O.name(), criteria) + " then 1 else 0 end) as studying");
            
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_N.name() + "' "
                                + "and " + extendCase(StudentStatus.OPPURSTAATUS_A.name(), criteria) + " then 1 else 0 end) as academicwomen");
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_M.name() + "' "
                                + "and " + extendCase(StudentStatus.OPPURSTAATUS_A.name(), criteria) + " then 1 else 0 end) as academicgmen");
            select.add("sum(case when " + extendCase(StudentStatus.OPPURSTAATUS_A.name(), criteria) + " then 1 else 0 end) as academic");
            
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_N.name() + "' "
                                + "and " + extendCase(StudentStatus.OPPURSTAATUS_V.name(), criteria) + " then 1 else 0 end) as foreignwomen");
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_M.name() + "' "
                                + "and " + extendCase(StudentStatus.OPPURSTAATUS_V.name(), criteria) + " then 1 else 0 end) as foreignmen");
            select.add("sum(case when " + extendCase(StudentStatus.OPPURSTAATUS_V.name(), criteria) + " then 1 else 0 end) as foreign");
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_M.name() + "' then 1 else 0 end) as men");
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_N.name() + "' then 1 else 0 end) as women");
        } else if (Boolean.TRUE.equals(criteria.getPerSex())) {
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_N.name() + "' then 1 else 0 end) as women");
            select.add("sum(case when p.sex_code = '" + Sex.SUGU_M.name() + "' then 1 else 0 end) as men");
        } else if (Boolean.TRUE.equals(criteria.getPerStatus())) {
            select.add("sum(case when " + extendCase(StudentStatus.OPPURSTAATUS_O.name(), criteria) + " then 1 else 0 end) as studying");
            select.add("sum(case when " + extendCase(StudentStatus.OPPURSTAATUS_A.name(), criteria) + " then 1 else 0 end) as academic");
            select.add("sum(case when " + extendCase(StudentStatus.OPPURSTAATUS_V.name(), criteria) + " then 1 else 0 end) as foreignStudy");
        }
    }

    private static void mapOtherCountResults(Object r, StudentCountCommand criteria, StudentCountDto dto) {
        mapOverallCountResults(r, criteria, dto);
        if (Boolean.TRUE.equals(criteria.getPerStatus()) && Boolean.TRUE.equals(criteria.getPerSex())) {
            dto.setObject(new AutocompleteResult(null, resultAsString(r, 13), resultAsString(r, 14)));
            if (StudentCountType.COUNT_STAT_CURRICULA.name().equals(criteria.getResultType())) {
                dto.setHtmCode(resultAsString(r, 12));
            }
        } else if (Boolean.TRUE.equals(criteria.getPerSex())) {
            dto.setObject(new AutocompleteResult(null, resultAsString(r, 4), resultAsString(r, 5)));
            if (StudentCountType.COUNT_STAT_CURRICULA.name().equals(criteria.getResultType())) {
                dto.setHtmCode(resultAsString(r, 3));
            }
        } else if (Boolean.TRUE.equals(criteria.getPerStatus())) {
            dto.setObject(new AutocompleteResult(null, resultAsString(r, 5), resultAsString(r, 6)));
            if (StudentCountType.COUNT_STAT_CURRICULA.name().equals(criteria.getResultType())) {
                dto.setHtmCode(resultAsString(r, 4));
            }
        } else {
            dto.setObject(new AutocompleteResult(null, resultAsString(r, 2), resultAsString(r, 3)));
            if (StudentCountType.COUNT_STAT_CURRICULA.name().equals(criteria.getResultType())) {
                dto.setHtmCode(resultAsString(r, 1));
            }
        }
        if (dto.getObject().getNameEt() == null) {
            if (StudentCountType.COUNT_STAT_STUDENT_GROUP.name().equals(criteria.getResultType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma rühmata", "Without group"));
            } else if (StudentCountType.COUNT_STAT_COURSE.name().equals(criteria.getResultType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma kursuseta", "Without course"));
            } else if (StudentCountType.COUNT_STAT_STRUCTURAL_UNIT.name().equals(criteria.getResultType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma struktuuriüksuseta", "Without structural unit"));
            } else if (StudentCountType.COUNT_STAT_STUDY_LEVEL.name().equals(criteria.getResultType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma õppetasemeta", "Without study level"));
            } else if (StudentCountType.COUNT_STAT_CURRICULA.name().equals(criteria.getResultType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma õppekavata", "Without curricula"));
            } else if (StudentCountType.COUNT_STAT_ISCED_VALD.name().equals(criteria.getResultType())) {
                dto.setObject(new AutocompleteResult(null, "Ilma valdkonnata", "Without field"));
            }
        }
    }

    private static void mapOverallCountResults(Object r, StudentCountCommand criteria, StudentCountDto dto) {
        if (Boolean.TRUE.equals(criteria.getPerStatus()) && Boolean.TRUE.equals(criteria.getPerSex())) {
            dto.setStudyingFemale(resultAsLong(r, 1));
            dto.setStudyingMale(resultAsLong(r, 2));
            dto.setStudying(resultAsLong(r, 3));
            
            dto.setAcademicFemale(resultAsLong(r, 4));
            dto.setAcademicMale(resultAsLong(r, 5));
            dto.setAcademic(resultAsLong(r, 6));
            
            dto.setForeignFemale(resultAsLong(r, 7));
            dto.setForeignMale(resultAsLong(r, 8));
            dto.setForeign(resultAsLong(r, 9));
            dto.setMale(resultAsLong(r, 10));
            dto.setFemale(resultAsLong(r, 11));
        } else if (Boolean.TRUE.equals(criteria.getPerSex())) {
            dto.setFemale(resultAsLong(r, 1));
            dto.setMale(resultAsLong(r, 2));
        } else if (Boolean.TRUE.equals(criteria.getPerStatus())) {
            dto.setStudying(resultAsLong(r, 1));
            dto.setAcademic(resultAsLong(r, 2));
            dto.setForeign(resultAsLong(r, 3));
        }
        dto.setSum(resultAsLong(r, 0));
    }

    public Page<StudentStatisticsDto> studentStatistics(HoisUserDetails user, StudentStatisticsCommand criteria,
            Pageable pageable) {
        Page<StudentStatisticsDto> result = loadCurriculums(user, criteria.getCurriculum(), pageable);

        // load grouped by value counts of given classifier
        if(!result.getContent().isEmpty()) {
            String groupingField = studentStatisticsGroupingField(criteria);
            String subquerySelect = studentStatisticsSubquerySelect(criteria);

            Map<Long, StudentStatisticsDto> curriculums = StreamUtil.toMap(StudentStatisticsDto::getId,
                    result.getContent());

            String grouping = "x.curriculum_id";
            if(groupingField != null) {
                grouping = grouping + ", " + groupingField;
            }
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from (select " + subquerySelect + " from student ss "
                    + "left join student_history sh on ss.id=sh.student_id and sh.inserted_by != :dataTransfer "
                    + "inner join curriculum_version cv on 1=1 and cv.curriculum_id in (:curriculum) "
                    + "inner join (select count(case when inserted_by != :dataTransfer then 1 else null end) as history_count, "
                    + "student_id from student_history group by student_id) x on x.student_id=ss.id "
                    + "where ss.school_id = :school and x.history_count > 0 "
                    + "and (sh.curriculum_version_id = cv.id and (sh.valid_thru is null or sh.valid_thru >= :validFrom) and sh.valid_from <= :validThru) "
                    + "or x.history_count = 0 and (ss.curriculum_version_id = cv.id and ss.status_code in (:status))) x")
                    .groupBy(grouping);
            qb.requiredCriteria("x.status_code in (:status)", "status", StudentStatus.STUDENT_STATUS_ACTIVE);
            qb.parameter("school", user.getSchoolId());
            if (user.isLeadingTeacher()) {
                qb.requiredCriteria("x.curriculum_id in (:userCurriculumIds)", "userCurriculumIds",
                        user.getCurriculumIds());
            }

            qb.parameter("curriculum", curriculums.keySet());
            qb.parameter("dataTransfer", DATA_TRANSFER_ROWS);

            if (criteria.getDate() != null) {
                qb.parameter("validFrom", DateUtils.firstMomentOfDay(criteria.getDate()));
                qb.parameter("validThru", DateUtils.lastMomentOfDay(criteria.getDate()));
            }

            List<?> data = qb.select(grouping + ", count(*)", em).getResultList();
            if(groupingField == null) {
                loadCounts(curriculums, data);
            } else {
                loadStatisticCounts(curriculums, data);
            }
        }
        return result;
    }

    private static String studentStatisticsGroupingField(StudentStatisticsCommand criteria) {
        String groupingField;
        if(MainClassCode.FINALLIKAS.name().equals(criteria.getResult())) {
            groupingField = "x.fin_specific_code";
        } else if(MainClassCode.OPPEVORM.name().equals(criteria.getResult())) {
            groupingField = "x.study_form_code";
        } else if(MainClassCode.OPPURSTAATUS.name().equals(criteria.getResult())) {
            groupingField = "x.status_code";
        } else if(StringUtils.isEmpty(criteria.getResult())) {
            groupingField = null;
        } else {
            throw new AssertionFailedException("Unknown result classifier");
        }
        return groupingField;
    }

    private static String studentStatisticsSubquerySelect(StudentStatisticsCommand criteria) {
        String select = "distinct cv.curriculum_id";
        if (MainClassCode.FINALLIKAS.name().equals(criteria.getResult())) {
            select += ", first_value(coalesce(sh.fin_specific_code,ss.fin_specific_code)) "
                    + "over (partition by ss.id order by sh.valid_thru nulls first, sh.valid_from desc) as fin_specific_code";
        } else if (MainClassCode.OPPEVORM.name().equals(criteria.getResult())) {
            select += ", first_value(coalesce(sh.study_form_code,ss.study_form_code)) "
                    + "over (partition by ss.id order by sh.valid_thru nulls first, sh.valid_from desc) as study_form_code";
        }
        select += ", first_value(coalesce(sh.status_code,ss.status_code)) over "
                + "(partition by ss.id order by sh.valid_thru nulls first, sh.valid_from desc) as status_code, ss.id";
        return select;
    }

    public byte[] studentStatisticsAsExcel(HoisUserDetails user, StudentStatisticsCommand criteria) {
        List<StudentStatisticsDto> students = studentStatistics(user, criteria,
                new PageRequest(0, Integer.MAX_VALUE, new Sort("c.name_et"))).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("students", students);
        return xlsService.generate("studentstatistics.xls", data);
    }

    public Page<StudentStatisticsDto> studentStatisticsByPeriod(HoisUserDetails user,
            StudentStatisticsByPeriodCommand criteria, Pageable pageable) {
        Page<StudentStatisticsDto> result = loadCurriculums(user, criteria.getCurriculum(), pageable);

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

            String from = "from directive_student ds "
                    + "inner join directive d on ds.directive_id = d.id "
                    + "inner join student ss on ds.student_id = ss.id ";
            if (StudentStatus.OPPURSTAATUS_L.name().equals(criteria.getResult())) {
                from += "inner join curriculum_version cv on ss.curriculum_version_id = cv.id";
            } else {
                from += "left join student_history sh on ds.student_history_id=sh.id " +
                        "inner join curriculum_version cv on coalesce(sh.curriculum_version_id,ss.curriculum_version_id) = cv.id";
            }

            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from).groupBy(grouping);

            qb.requiredCriteria("cv.curriculum_id in (:curriculum)", "curriculum", cs.keySet());

            qb.requiredCriteria("d.school_id = :schoolId", "schoolId", user.getSchoolId());
            if (user.isLeadingTeacher()) {
                qb.requiredCriteria("cv.curriculum_id in (:userCurriculumIds)", "userCurriculumIds",
                        user.getCurriculumIds());
            }

            qb.requiredCriteria("d.type_code in(:directiveType)", "directiveType", directiveTypes);
            qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus",
                    DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
            qb.optionalCriteria("d.confirm_date >= :validFrom", "validFrom", criteria.getFrom(),
                    DateUtils::firstMomentOfDay);
            qb.optionalCriteria("d.confirm_date <= :validThru", "validThru", criteria.getThru(),
                    DateUtils::lastMomentOfDay);
            // check for directive cancellation for given student
            qb.filter("ds.canceled = false");

            List<?> data = qb.select(grouping + ", count(*)", em).getResultList();
            if(groupingField == null) {
                loadCounts(cs, data);
            } else {
                loadStatisticCounts(cs, data);
            }
        }   

        return result;
    }

    public byte[] studentStatisticsByPeriodAsExcel(HoisUserDetails user, StudentStatisticsByPeriodCommand criteria) {
        List<StudentStatisticsDto> students = studentStatisticsByPeriod(user, criteria,
                new PageRequest(0, Integer.MAX_VALUE, new Sort("c.name_et"))).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("students", students);
        return xlsService.generate("studentstatisticsbyperiod.xls", data);
    }

    public Page<CurriculumCompletionDto> curriculumCompletion(HoisUserDetails user,
            CurriculumCompletionCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s inner join person p on s.person_id = p.id " +
                "inner join curriculum_version cv on s.curriculum_version_id = cv.id " +
                "inner join curriculum c on cv.curriculum_id = c.id "+
                "left join student_group sg on s.student_group_id = sg.id "+
                "left join student_curriculum_completion scc on scc.student_id = s.id").sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("c.id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        }

        qb.requiredCriteria("s.status_code in (:status)", "status", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.optionalCriteria("cv.id = :curriculumVersion", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalCriteria("s.study_load_code = :studyLoad", "studyLoad", criteria.getStudyLoad());
        qb.optionalCriteria("s.study_form_code = :studyForm", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("s.student_group_id = :studentGroup", "studentGroup", criteria.getStudentGroup());

        Page<CurriculumCompletionDto> page = JpaQueryUtil.pagingResult(qb, "s.id, p.firstname, p.lastname, " +
                "cv.code, c.name_et, c.name_en, sg.code as student_group_code, s.study_load_code, s.study_form_code, s.status_code, " +
                "scc.credits, round((c.credits + scc.study_backlog) * 100 / c.credits), " +
                "(select count(*) from study_period sp join study_year sy on sy.id = sp.study_year_id"+
                " where sy.school_id = s.school_id and sp.end_date < now() and s.study_start < sp.end_date) as period_count, " +
                "(select count(*) from study_year sy"+
                " where sy.school_id = s.school_id and sy.end_date < now() and s.study_start < sy.end_date) as year_count, " +
                "s.type_code as studentType"
        , em, pageable).map(r -> new CurriculumCompletionDto(r));
        if (!page.getContent().isEmpty()) {
            setLastStudyPeriodCredits(user.getSchoolId(), page.getContent());
        }
        return page;
    }

    private void setLastStudyPeriodCredits(Long schoolId, List<CurriculumCompletionDto> curriculumCompletionDtos) {
        Long lastStudyPeridodId = studyYearService.getPreviousStudyPeriod(schoolId);
        if (lastStudyPeridodId != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, lastStudyPeridodId);
            Map<Long, CurriculumCompletionDto> dtosByStudent = StreamUtil.toMap(c -> c.getId(),
                    curriculumCompletionDtos);

            List<?> data = em.createNativeQuery(
                    "select s.id, (select spCredits.sum from (" +
                        "select * from (select sum(credits) from (select distinct on (svr.curriculum_version_omodule_id) first_value(svr.credits) " +
                        "over (partition by svr.curriculum_version_omodule_id order by coalesce(svr.changed, svr.inserted) desc) credits " +
                        "from student_vocational_result svr " +
                        "where svr.student_id = s.id and c.is_higher = false and svr.grade_code in (:vocationalPositiveGrades) " +
                        "and svr.grade_date >= :studyPeriodStart and svr.grade_date <= :studyPeriodEnd " +
                        ") as distinct_module_credits) credits_sum where sum is not null " +
                        "union all " +
                        "select sum(shr.credits) from student_higher_result shr " +
                        "where shr.student_id = s.id and c.is_higher = true and shr.grade_code in (:higherPositiveGrades) " +
                        "and shr.is_active = true and shr.grade_date >= :studyPeriodStart and shr.grade_date <= :studyPeriodEnd " +
                        "group by shr.student_id) spCredits) " +
                    "from student s " +
                    "join curriculum_version cv on cv.id = s.curriculum_version_id " +
                    "join curriculum c on c.id = cv.curriculum_id " +
                    "where s.id in (:studentIds)")
                    .setParameter("studentIds", dtosByStudent.keySet())
                    .setParameter("studyPeriodStart", JpaQueryUtil.parameterAsTimestamp(sp.getStartDate()))
                    .setParameter("studyPeriodEnd", JpaQueryUtil.parameterAsTimestamp(sp.getEndDate()))
                    .setParameter("vocationalPositiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE)
                    .setParameter("higherPositiveGrades", HigherAssessment.GRADE_POSITIVE)
                    .getResultList();

            Map<Long, BigDecimal> lastSpCreditsByStudent = StreamUtil.nullSafeList(data).stream()
                    .filter(r -> resultAsDecimal(r, 1) != null)
                    .collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> resultAsDecimal(r, 1)));
            for (Long studentId : lastSpCreditsByStudent.keySet()) {
                dtosByStudent.get(studentId).setCreditsLastStudyPeriod(lastSpCreditsByStudent.get(studentId));
            }
        }
    }

    public byte[] curriculumCompletionAsExcel(HoisUserDetails user, CurriculumCompletionCommand criteria) {
        List<CurriculumCompletionDto> students = curriculumCompletion(user, criteria,
                new PageRequest(0, Integer.MAX_VALUE)).getContent();
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

    public Page<VotaDto> vota(HoisUserDetails user, VotaCommand criteria, Pageable pageable) {
        // curriculum versions for given study year/period
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version cv " +
                "join curriculum c on cv.curriculum_id = c.id " +
                "join study_period sp on (cv.valid_from is null or cv.valid_from <= sp.end_date) and (cv.valid_thru is null or cv.valid_thru >= sp.start_date) " +
                "join study_year sy on sp.study_year_id = sy.id " +
                "join classifier syc on sy.year_code = syc.code").sort("sp.start_date", "sp.id", "cv.code");
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("sy.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("c.id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        }

        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("sp.id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());

        Set<Long> cvIds = new HashSet<>();
        Set<StudyPeriodHolder> studyPeriods = new HashSet<>();
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
                    studyPeriods.add(new StudyPeriodHolder(studyPeriodId, resultAsLocalDate(r, 9), resultAsLocalDate(r, 10)));
                    return dto;
                });

        List<VotaDto> dtos = result.getContent();
        if(!dtos.isEmpty()) {
            Map<VotaDto, VotaStatistics> allStats = new IdentityHashMap<>();
            List<StudyPeriodHolder> sortedStudyPeriods = studyPeriods.stream().sorted(Comparator.comparing(StudyPeriodHolder::getStart)).collect(Collectors.toList());
            LocalDateTime start = DateUtils.firstMomentOfDay(studyPeriods.stream().map(StudyPeriodHolder::getStart).min(Comparator.naturalOrder()).get());
            LocalDateTime end = DateUtils.lastMomentOfDay(studyPeriods.stream().map(StudyPeriodHolder::getEnd).max(Comparator.naturalOrder()).get());
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
                    "join subject_study_period_capacity ssppc on ssppc.subject_study_period_id = ssp.id " +
                    "left join subject_study_period_teacher_capacity " +
                        "ssptc on ssptc.subject_study_period_capacity_id = ssppc.id and ssptc.subject_study_period_teacher_id = sspt.id").sort(pageable);
            qb.requiredCriteria("sy.school_id = :schoolId", "schoolId", schoolId);
            qb.requiredCriteria("sp.study_year_id = :studyYear", "studyYear", criteria.getStudyYear());
            qb.optionalCriteria("ssp.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
            qb.optionalCriteria("ssp.subject_id = :subject", "subject", criteria.getSubject());
            qb.optionalCriteria("sspt.teacher_id = :teacher", "teacher", criteria.getTeacher());
            qb.groupBy("syc.name_et, syc.name_en, sp.name_et, sp.name_en, p.firstname, p.lastname, sspt.teacher_id, sp.id");
            result = JpaQueryUtil.pagingResult(qb, 
                    "syc.name_et, syc.name_en, sp.name_et as study_period_name_et, sp.name_en as study_period_name_en, p.firstname, p.lastname, " +
                    "coalesce(sum(case when ssp.is_capacity_diff is null or ssp.is_capacity_diff is false then ssppc.hours end), 0) " +
                    "+ coalesce(sum(case when ssp.is_capacity_diff then ssptc.hours end), 0), sspt.teacher_id, sp.id", em, pageable);
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
                        + " left join subject_study_period_teacher_capacity ssptc on ssptc.subject_study_period_capacity_id = sspc.id and ssptc.subject_study_period_teacher_id = sspt.id"
                        + " join subject_study_period ssp on ssp.id = sspt.subject_study_period_id"
                        + " join study_period sp on sp.id = ssp.study_period_id"
                        + " join study_year sy on sy.id = sp.study_year_id"
                        + " join school_capacity_type sct on sct.school_id = sy.school_id and sct.capacity_type_code = sspc.capacity_type_code and sct.is_higher = true"
                        + " join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id and sctl.study_year_id = sp.study_year_id");

                qb.requiredCriteria("sspt.teacher_id in (:teacher)", "teacher", teachers);
                qb.requiredCriteria("ssp.study_period_id in (:studyPeriod)", "studyPeriod", studyPeriods);
                qb.optionalCriteria("ssp.subject_id = :subject", "subject", criteria.getSubject());

                qb.groupBy("sspt.teacher_id, ssp.study_period_id, sspc.capacity_type_code, sctl.load_percentage");
                
                String hoursByTypeQuery = qb.querySql("sspt.teacher_id, ssp.study_period_id, "
                        + "sctl.load_percentage * (coalesce(sum(case when ssp.is_capacity_diff is null or ssp.is_capacity_diff is false then sspc.hours end), 0) "
                        + "+ coalesce(sum(case when ssp.is_capacity_diff then ssptc.hours end), 0)) / 100.0 as hours", false);
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

    private Page<StudentStatisticsDto> loadCurriculums(HoisUserDetails user,
            List<EntityConnectionCommand> curriculumIds, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum c").sort(pageable);

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("c.id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria("c.id in (:curriculum)", "curriculum", StreamUtil.toMappedList(r -> r.getId(), curriculumIds));

        return JpaQueryUtil.pagingResult(qb, "c.id, c.name_et, c.name_en, c.mer_code", em, pageable).map(r -> new StudentStatisticsDto(r));
    }

    private static void loadStatisticCounts(Map<Long, StudentStatisticsDto> curriculums, List<?> data) {
        for (Object r : data) {
            Long curriculumId = resultAsLong(r, 0);
            String group = resultAsString(r, 1);
            if (group != null) {
                StudentStatisticsDto dto = curriculums.get(curriculumId);
                dto.getResult().put(group, resultAsLong(r, 2));
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

    private static VotaDto findVota(LocalDate inserted, Long cvId, List<StudyPeriodHolder> studyPeriods, Map<Long, Map<Long, VotaDto>> dtosByPeriodAndCurriculum) {
        // study period by inserted date
        // first study period which is not ended
        StudyPeriodHolder sp = studyPeriods.stream().filter(r -> !r.getEnd().isBefore(inserted)).findFirst().orElse(null);
        if(sp == null) {
            return null;
        }
        return dtosByPeriodAndCurriculum.get(sp.getId()).get(cvId);
    }

    public byte[] scholarshipStatisticsAsExcel(HoisUserDetails user, ScholarshipStatisticsCommand criteria) {
        StringBuilder from = new StringBuilder("from directive d ");
        from.append("join directive_student ds on ds.directive_id = d.id ");
        from.append("join classifier c on c.code = coalesce(d.scholarship_type_code, d.scholarship_ehis_code) ");
        from.append("join student s on s.id = ds.student_id ");
        from.append("left join student_group sg on sg.id = s.student_group_id ");
        from.append("join person p on p.id = s.person_id ");
        from.append("left join scholarship_application sa on sa.id = ds.scholarship_application_id ");
        from.append("left join scholarship_term st on st.id = sa.scholarship_term_id ");
        StringBuilder select = new StringBuilder("sg.code, p.idcode, p.firstname || ' ' || p.lastname as fullname, ");
        select.append("d.confirm_date, d.directive_nr, coalesce(ds.amount_paid, st.amount_paid) as amount_paid, c.name_et, c.name_en, ");
        select.append("sa.bank_account_owner_idcode, sa.bank_account_owner_name, coalesce(ds.bank_account, sa.bank_account) as bank_account, ");
        select.append("coalesce(ds.start_date, sa.scholarship_from, st.payment_start) as date_from, ");
        select.append("coalesce(ds.end_date, sa.scholarship_thru, st.payment_end) as date_thru, ");
        select.append("sa.id as sApplication ");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString());

        qb.requiredCriteria("d.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("sg.curriculum_id in (:userCurriculumIds)", "userCurriculumIds",
                    user.getCurriculumIds());
        }

        qb.requiredCriteria("d.type_code = :type", "type", DirectiveType.KASKKIRI_STIPTOET);
        qb.requiredCriteria("d.status_code = :status", "status", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.filter("(d.confirm_date >= :from and d.confirm_date <= :thru)");
        qb.parameter("from", criteria.getFrom());
        qb.parameter("thru", criteria.getThru());
        qb.optionalCriteria("(d.scholarship_type_code in (:types) or d.scholarship_ehis_code in (:types))", "types", criteria.getTypes());
        List<?> results = qb.select(select.toString(), em).getResultList();
        Map<String, Object> context = new HashMap<>();
        context.put("scholarships", StreamUtil.toMappedList(row -> {
            boolean hasScholarshipApplication = resultAsLong(row, 13) != null;
            ScholarshipReportDto dto = new ScholarshipReportDto();
            dto.setGroup(resultAsString(row, 0));
            dto.setStudent(new ScholarshipReportDto.Person(resultAsString(row, 1), resultAsString(row, 2)));
            dto.setDecisionDate(resultAsLocalDate(row, 3));
            dto.setProtocolNr(resultAsString(row, 4));
            dto.setAmountPaid(resultAsDecimal(row, 5));
            if (hasScholarshipApplication) {
                dto.setType(new AutocompleteResult(null, resultAsString(row, 6), resultAsString(row, 7)));
            } else {
                dto.setType(new AutocompleteResult(null, "Muu - " + resultAsString(row, 6), "Muu - " + resultAsString(row, 7)));
            }
            String receiverIdCode = resultAsString(row, 8);
            String receiverName = resultAsString(row, 9);
            if (receiverIdCode != null || receiverName != null) {
                dto.setReceiver(new ScholarshipReportDto.Person(receiverIdCode, receiverName));
            }
            dto.setBankAccount(resultAsString(row, 10));
            dto.setFrom(resultAsLocalDate(row, 11));
            dto.setThru(resultAsLocalDate(row, 12));
            return dto;
        }, results));
        return xlsService.generate("scholarships.xlsx", context);
    }

    private static class StudyPeriodHolder {

        private final Long id;
        private final LocalDate start;
        private final LocalDate end;

        public StudyPeriodHolder(Long id, LocalDate start, LocalDate end) {
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
            return (o instanceof StudyPeriodHolder) && id.equals(((StudyPeriodHolder)o).getId());
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

    public Page<IndividualCurriculumSatisticsDto> individualCurriculumStatistics(HoisUserDetails user,
            IndividualCurriculumStatisticsCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = indokIndividualCurriculums(user, criteria);
        String indokQuery = qb.querySql("s.id student_id, p.firstname, p.lastname, sg.id group_id, sg.code group_code, "
                + "cm.id curriculum_id, cm.name_et cm_name_et, cm.name_en cm_name_en, dsm.add_info, "
                + "ds.start_date, coalesce(ds_lop.start_date, ds.end_date) end_date, s.type_code as studentType", false);
        Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

        qb = tugiIndividualCurriculums(user, criteria); 
        String tugiQuery = qb.querySql("s2.id student_id, p2.firstname, p2.lastname, sg2.id group_id, "
                + "sg2.code group_code, cm2.id curriculum_id, cm2.name_et cm_name_et, cm2.name_en cm_name_en, "
                + "assm.add_info, ds2.start_date, coalesce(ds_lop2.start_date, ds2.end_date), s2.type_code as studentType", false);
       parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + indokQuery + " union all " + tugiQuery + ") as individual_curriculums")
                .sort(pageable);
        
        return JpaQueryUtil.pagingResult(qb, "student_id, firstname, lastname, group_id, group_code, "
                + "curriculum_id, cm_name_et, cm_name_en, add_info, start_date, end_date, studentType", parameters, em, pageable)
                .map(r -> new IndividualCurriculumSatisticsDto(r));
    }

    private static JpaNativeQueryBuilder indokIndividualCurriculums(HoisUserDetails user,
            IndividualCurriculumStatisticsCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s " +
                "join curriculum_version cv on cv.id = s.curriculum_version_id " +
                "join person p on p.id = s.person_id " +
                "left join student_group sg on sg.id = s.student_group_id "+
                "join directive_student ds on ds.student_id = s.id "+
                "join directive d on d.id = ds.directive_id " +
                "join directive_student_module dsm on dsm.directive_student_id = ds.id " +
                "join curriculum_version_omodule cvo on cvo.id = dsm.curriculum_version_omodule_id " +
                "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                "left join (directive_student ds_lop join directive d_lop on d_lop.id = ds_lop.directive_id and d_lop.type_code = :lopDirectiveType " +
                "and d_lop.status_code = :directiveStatus) on ds_lop.directive_student_id = ds.id and ds_lop.canceled = false");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("cv.curriculum_id in (:userCurriculumIds)", "userCurriculumIds",
                    user.getCurriculumIds());
        }

        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_INDOK);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus",
                DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.optionalCriteria("s.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalContains(Arrays.asList("cm.name_et", "cm.name_en"), "moduleName", criteria.getModuleName());
        qb.optionalCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                criteria.getCurriculumVersion());
        qb.optionalCriteria("ds.start_date >= :from", "from", criteria.getFrom());
        qb.optionalCriteria("coalesce(ds_lop.start_date, ds.end_date) <= :thru", "thru", criteria.getThru());
        qb.filter("ds.canceled = false");

        if (user.isTeacher()) {
            qb.requiredCriteria("sg.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }
        qb.parameter("lopDirectiveType",  DirectiveType.KASKKIRI_INDOKLOP.name());

        return qb;
    }

    private static JpaNativeQueryBuilder tugiIndividualCurriculums(HoisUserDetails user,
            IndividualCurriculumStatisticsCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s2 " +
                "join curriculum_version cv2 on cv2.id = s2.curriculum_version_id " +
                "join person p2 on p2.id = s2.person_id " +
                "left join student_group sg2 on sg2.id = s2.student_group_id " +
                "join directive_student ds2 on ds2.student_id = s2.id " +
                "join directive d2 on d2.id = ds2.directive_id " +
                "join application a on a.id = ds2.application_id " +
                "join application_support_service ass on ass.application_id = a.id " +
                "join application_support_service_module assm on assm.application_support_service_id = ass.id " +
                "join curriculum_version_omodule cvo2 on cvo2.id = assm.curriculum_version_omodule_id " +
                "join curriculum_module cm2 on cm2.id = cvo2.curriculum_module_id " +
                "left join (directive_student ds_lop2 join directive d_lop2 on d_lop2.id = ds_lop2.directive_id and d_lop2.type_code = :lopDirectiveType2 " +
                "and d_lop2.status_code = :directiveStatus) on ds_lop2.directive_student_id = ds2.id and ds_lop2.canceled = false");

        qb.requiredCriteria("s2.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("cv2.curriculum_id in (:userCurriculumIds)", "userCurriculumIds",
                    user.getCurriculumIds());
        }

        qb.requiredCriteria("d2.type_code = :directiveType2", "directiveType2", DirectiveType.KASKKIRI_TUGI);
        qb.requiredCriteria("d2.status_code = :directiveStatus", "directiveStatus",
                DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.requiredCriteria("ass.support_service_code = :supportServiceCode", "supportServiceCode",
                SupportServiceType.TUGITEENUS_1);
        qb.optionalCriteria("s2.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("sg2.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalContains(Arrays.asList("cm2.name_et", "cm2.name_en"), "moduleName", criteria.getModuleName());
        qb.optionalCriteria("cvo2.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                criteria.getCurriculumVersion());
        qb.optionalCriteria("ds2.start_date >= :from", "from", criteria.getFrom());
        qb.optionalCriteria("coalesce(ds_lop2.start_date, ds2.end_date) <= :thru", "thru", criteria.getThru());
        qb.filter("ds2.canceled = false");

        if (user.isTeacher()) {
            qb.requiredCriteria("sg2.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }
        qb.parameter("lopDirectiveType2",  DirectiveType.KASKKIRI_TUGILOPP.name());

        return qb;
    }

    public byte[] individualCurriculumStatisticsAsExcel(HoisUserDetails user, IndividualCurriculumStatisticsCommand criteria) {
        List<IndividualCurriculumSatisticsDto> rows = individualCurriculumStatistics(user, criteria,
                new PageRequest(0, Integer.MAX_VALUE)).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("rows", rows);
        return xlsService.generate("individualcurriculumstatistics.xls", data);
    }

    public Page<GuestStudentStatisticsDto> guestStudentStatistics(HoisUserDetails user, GuestStudentStatisticsCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join directive_student ds on ds.student_id = s.id "
                + "join directive d on ds.directive_id = d.id "
                + "join person p on s.person_id = p.id "
                + "left join apel_school aps on ds.apel_school_id = aps.id "
                + "left join classifier ehis_cl on ehis_cl.code = ds.ehis_school_code "
                + "left join classifier aps_cl on aps_cl.code = aps.country_code "
                + "left join classifier ds_cl on ds_cl.code = ds.abroad_programme_code "
                + "left join student_group sg on s.student_group_id = sg.id "
                + "left join curriculum_version cv on s.curriculum_version_id = cv.id "
                + "left join curriculum c on c.id = cv.curriculum_id "
                + "left join classifier_connect cc on cc.classifier_code = c.orig_study_level_code "
                + "left join curriculum_department cd on cd.curriculum_id = c.id "
                + "left join school_department sd on cd.school_department_id = sd.id "
                + "left join study_year sy on (s.study_start >= sy.start_date and s.study_end <= sy.end_date and sy.school_id = s.school_id) "
                + "left join student_curriculum_completion scc on scc.student_id = s.id").sort(pageable)
                .groupBy("s.id, p.firstname, p.lastname, "
                + "cv.code, c.name_et, c.name_en, sg.code, s.study_start, s.study_end, "
                + "aps.id, aps.name_et, aps.name_en, aps.country_code, "
                + "ds.abroad_programme_code, scc.credits, aps_cl.name_et, ds_cl.name_et");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.type_code = :studentType", "studentType", StudentType.OPPUR_K.name());
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_KYLALIS.name());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getStudent());
        qb.optionalContains(Arrays.asList("aps.name_et", "ds.abroad_school", "ehis_cl.name_et"), "prevSchool", criteria.getHomeSchool());
        qb.optionalCriteria("c.id = :curriculumId", "curriculumId", criteria.getCurriculum());
        qb.optionalCriteria("cv.id = :curriculumVersion", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalCriteria("sy.id = :studyYear", "studyYear", criteria.getStudyYear());
        qb.optionalCriteria("s.study_start >= :startFrom", "startFrom", criteria.getStartFrom());
        qb.optionalCriteria("s.study_start <= :startThru", "startThru", criteria.getStartThru());
        qb.optionalCriteria("s.study_end >= :endFrom", "endFrom", criteria.getEndFrom());
        qb.optionalCriteria("s.study_end <= :endThru", "endThru", criteria.getEndThru());
        qb.optionalCriteria("sd.id = :departmentId", "departmentId", criteria.getDepartment());
        qb.optionalCriteria("cc.connect_classifier_code = :educationLevel", "educationLevel", criteria.getEducationLevel());
        qb.optionalCriteria("aps.country_code = :homeCountry", "homeCountry", criteria.getHomeCountry());
        qb.optionalCriteria("ds.abroad_programme_code = :programmeCode", "programmeCode", criteria.getProgramme());

        return JpaQueryUtil.pagingResult(qb, "s.id, p.firstname, p.lastname, "
                + "cv.code, c.name_et, c.name_en, sg.code as student_group_code, s.study_start, s.study_end, "
                + "aps.id as homeSchoolId, aps.name_et as homeSchoolEt, aps.name_en as homeSchoolEn, aps.country_code as homeCountry, "
                + "ds.abroad_programme_code, scc.credits as totalEap, aps_cl.name_et as homeCountryName, ds_cl.name_et as abroadEt"
        , em, pageable).map(r -> new GuestStudentStatisticsDto(r));
    }

    public byte[] guestStudentStatisticsAsExcel(HoisUserDetails user, GuestStudentStatisticsCommand criteria) {
        List<GuestStudentStatisticsDto> students = guestStudentStatistics(user, criteria,
                new PageRequest(0, Integer.MAX_VALUE, new Sort("p.lastname, p.firstname"))).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("students", students);
        return xlsService.generate("gueststudentstatistics.xls", data);
    }

    public Page<ForeignStudentStatisticsDto> foreignStudentStatistics(HoisUserDetails user,
            ForeignStudentStatisticsCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join directive_student ds on ds.student_id = s.id "
                + "join directive d on ds.directive_id = d.id "
                + "join person p on s.person_id = p.id "
                + "left join study_period startPeriod on startPeriod.id = ds.study_period_start_id "
                + "left join study_period endPeriod on endPeriod.id = ds.study_period_end_id "
                + "left join (select ds1.start_date, ds1.student_id, ds1.directive_student_id "
                    + "from directive_student ds1 "
                    + "join directive d1 on ds1.directive_id = d1.id "
                    + "where d1.type_code = 'KASKKIRI_VALISKATK' and d1.status_code = :directiveStatus) as VALISKATK "
                    + "on VALISKATK.directive_student_id = ds.id "
                + "left join classifier ehisSchool on ds.ehis_school_code = ehisSchool.code "
                + "left join apel_school aps on ds.apel_school_id = aps.id "
                + "left join student_group sg on s.student_group_id = sg.id "
                + "left join curriculum_version cv on s.curriculum_version_id = cv.id "
                + "left join curriculum c on c.id = cv.curriculum_id "
                + "left join classifier cl_programme on cl_programme.code = ds.abroad_programme_code "
                + "left join classifier cl_country on (cl_country.code = aps.country_code or cl_country.code = ds.country_code) "
                + "left join classifier_connect cc on cc.classifier_code = c.orig_study_level_code "
                    + "and cc.main_classifier_code = 'HARIDUSTASE'"
                + "left join school_department sd on ((cv.school_department_id is not null and sd.id = cv.school_department_id) "
                    + "or (cv.school_department_id is null and sd.id in "
                    + "(select cd.school_department_id from curriculum_department cd where cd.curriculum_id = c.id)))")
//                + "left join (select aa.id, aafsm.credits, coalesce(nominal_cl.value, '0') as nominal, aafsm.grade_date, aa.confirmed, aa.student_id, aa.is_ehis_sent "
//                          + "from apel_application aa "
//                          + "join apel_application_record aar on aar.apel_application_id = aa.id "
//                          + "join apel_application_formal_subject_or_module aafsm on aafsm.apel_application_record_id = aar.id "
//                          + "join apel_school aschool on aschool.id = aafsm.apel_school_id "
//                          + "left join classifier nominal_cl on nominal_cl.code = aa.nominal_type_code "
//                          + "where aa.status_code = 'VOTA_STAATUS_C' and aafsm.transfer = true and aschool.country_code != 'RIIK_EST') aa on "
//                          + "aa.confirmed > coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, endPeriod.end_date, ds.end_date) "
//                          + "and aa.grade_date between coalesce(startPeriod.start_date, ds.start_date) "
//                              + "and coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, endPeriod.end_date, ds.end_date) "
//                          + "and aa.student_id = ds.student_id ")
                .sort(pageable)
                .groupBy("s.id, p.id, c.id, cv.id, sg.id, cc.connect_classifier_code, "
                        + "endPeriod.id, startPeriod.id, ds.id, aps.id, ds.id, ehisSchool.code, cl_country.code, "
                        + "ds.abroad_programme_code, cl_programme.code, VALISKATK.start_date");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_VALIS.name());
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getStudent());
        qb.optionalCriteria("c.id = :curriculumId", "curriculumId", criteria.getCurriculum());
        qb.optionalCriteria("cv.id = :curriculumVersion", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalCriteria("coalesce(ds.start_date, startPeriod.start_date) >= :startFrom", "startFrom", criteria.getStartFrom());
        qb.optionalCriteria("coalesce(ds.start_date, startPeriod.start_date) <= :startThru", "startThru", criteria.getStartThru());
        qb.optionalCriteria(
                "coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, ds.end_date, endPeriod.end_date) >= :endFrom",
                "endFrom", criteria.getEndFrom());
        qb.optionalCriteria(
                "coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, ds.end_date, endPeriod.end_date) <= :endThru",
                "endThru", criteria.getEndThru());

        StudyYear studyYear = EntityUtil.getOptionalOne(StudyYear.class, criteria.getStudyYear(), em);
        if (studyYear != null) {
            qb.optionalCriteria("coalesce(ds.start_date, startPeriod.start_date) >= :syStart", "syStart", studyYear.getStartDate());
            qb.optionalCriteria(
                    "coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, ds.end_date, endPeriod.end_date) <= :syEnd",
                    "syEnd", studyYear.getEndDate());
        }

        qb.optionalCriteria("((cv.school_department_id is not null and cv.school_department_id = :departmentId) or "
                + "(cv.school_department_id is null and :departmentId in "
                + "(select cd.school_department_id from curriculum_department cd where cd.curriculum_id = c.id)))",
                "departmentId", criteria.getDepartment());
        qb.optionalCriteria("cc.connect_classifier_code = :educationLevel", "educationLevel", criteria.getEducationLevel());
        qb.optionalContains(Arrays.asList("ds.abroad_school", "aps.name_et", "aps.name_en", "ehisSchool.name_et", "ehisSchool.name_en"), 
                "foreignSchool", criteria.getForeignSchool());
        qb.optionalCriteria("cl_country.code = :foreignCountry", "foreignCountry", criteria.getForeignCountry());
        qb.optionalCriteria("ds.abroad_programme_code = :programmeCode", "programmeCode", criteria.getProgramme());
        qb.optionalCriteria("coalesce(ds.start_date, startPeriod.start_date) >= :durationStart", "durationStart", criteria.getDurationStart());
        qb.optionalCriteria(
                "coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, ds.end_date, endPeriod.end_date) <= :durationEnd",
                "durationEnd", criteria.getDurationEnd());
        if (Boolean.TRUE.equals(criteria.getIsExtended())) {
            qb.having("exists(select 1 "
                    + "from apel_application aa "
                    + "join apel_application_record aar on aar.apel_application_id = aa.id "
                    + "join apel_application_formal_subject_or_module aafsm on aafsm.apel_application_record_id = aar.id "
                    + "join apel_school aschool on aschool.id = aafsm.apel_school_id "
                    + "join classifier nominal_cl on nominal_cl.code = aa.nominal_type_code "
                    + "where nominal_cl.code != 'NOM_PIKEND_0' and aa.status_code = 'VOTA_STAATUS_C' and aafsm.transfer = true and aschool.country_code != 'RIIK_EST' "
                    + "and aa.confirmed > coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, endPeriod.end_date, ds.end_date) "
                    + "and aafsm.grade_date between coalesce(startPeriod.start_date, ds.start_date) and coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, endPeriod.end_date, ds.end_date) "
                    + "and aa.student_id = ds.student_id)");
        }
        qb.filter("(ds.canceled is null or ds.canceled = false)");

        return JpaQueryUtil.pagingResult(qb, "s.id, p.firstname, p.lastname, cv.code, c.name_et, "
                + "c.name_en, sg.code as student_group_code, cc.connect_classifier_code education_level, "
                + "string_agg(sd.name_et, ', ' order by sd.name_et) sd_name_et, "
                + "string_agg(coalesce(sd.name_en, sd.name_et), ', ' order by coalesce(sd.name_en, sd.name_et)) sd_name_en, "
                + "coalesce (ds.start_date, startPeriod.start_date) as leaving_date, "
                + "coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, ds.end_date, endPeriod.end_date) as return_date, "
                + "coalesce(aps.name_et, ds.abroad_school, ehisSchool.name_et) as foreign_school_et, "
                + "coalesce(aps.name_en, ds.abroad_school, ehisSchool.name_en) as foreign_school_en, "
                + "cl_country.code as homeCountry, ds.abroad_programme_code, ds.application_id, "
                // Total wanted EAP (points)
                + "(select coalesce(sum(cvot.credits), sum(su.credits)) as credits "
                    + "from application_planned_subject apsubj "
                    + "left join application_planned_subject_equivalent apse on apse.application_planned_subject_id = apsubj.id "
                    + "left join subject su on su.id = apse.subject_id "
                    + "left join curriculum_version_omodule cvo on apse.curriculum_version_omodule_id = cvo.id "
                    + "left join curriculum_version_omodule_theme cvot on "
                        + "((apse.curriculum_version_omodule_theme_id = cvot.id and cvo.id = cvot.curriculum_version_omodule_id) "
                        + "or (apse.curriculum_version_omodule_theme_id is null and cvo.id = cvot.curriculum_version_omodule_id)) "
                        + "where apsubj.application_id = ds.application_id) as total_wanted_eap, "
                // Has duplicates/ TODO
//                + "sum(aa.credits) as credits, "
//                + "case "
//                    + "when coalesce(max(case when aa.is_ehis_sent then aa.nominal else '0' end), '0') != '0' "
//                    + "then max(case when aa.is_ehis_sent then aa.nominal else '0' end) "
//                    + "else max(aa.nominal) "
//                + "end as max_nominal"
                // Temporary
                + "(select sum(aafsm.credits) "
                + "from apel_application aa "
                + "join apel_application_record aar on aar.apel_application_id = aa.id "
                + "join apel_application_formal_subject_or_module aafsm on aafsm.apel_application_record_id = aar.id "
                + "join apel_school aschool on aschool.id = aafsm.apel_school_id "
                + "left join classifier nominal_cl on nominal_cl.code = aa.nominal_type_code "
                + "where aa.status_code = 'VOTA_STAATUS_C' and aafsm.transfer = true and aschool.country_code != 'RIIK_EST' "
                + "and aa.confirmed > coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, endPeriod.end_date, ds.end_date) "
                + "and aafsm.grade_date between coalesce(startPeriod.start_date, ds.start_date) and coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, endPeriod.end_date, ds.end_date) "
                + "and aa.student_id = ds.student_id) as credits, "
                + "(select case "
                + "when coalesce(max(case when aa.is_ehis_sent then nominal_cl.value else '0' end), '0') != '0' then max(case when aa.is_ehis_sent then nominal_cl.value else '0' end) "
                + "else max(nominal_cl.value) "
                + "end as max_nominal "
                + "from apel_application aa "
                + "join apel_application_record aar on aar.apel_application_id = aa.id "
                + "join apel_application_formal_subject_or_module aafsm on aafsm.apel_application_record_id = aar.id "
                + "join apel_school aschool on aschool.id = aafsm.apel_school_id "
                + "left join classifier nominal_cl on nominal_cl.code = aa.nominal_type_code "
                + "where aa.status_code = 'VOTA_STAATUS_C' and aafsm.transfer = true and aschool.country_code != 'RIIK_EST' "
                + "and aa.confirmed > coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, endPeriod.end_date, ds.end_date) "
                + "and aafsm.grade_date between coalesce(startPeriod.start_date, ds.start_date) and coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, endPeriod.end_date, ds.end_date) "
                + "and aa.student_id = ds.student_id) as max_nominal"
        , em, pageable).map(r -> new ForeignStudentStatisticsDto(r));
    }

    public byte[] foreignStudentStatisticsAsExcel(HoisUserDetails user, ForeignStudentStatisticsCommand criteria) {
        List<ForeignStudentStatisticsDto> students = foreignStudentStatistics(user, criteria,
                new PageRequest(0, Integer.MAX_VALUE, new Sort("p.lastname, p.firstname"))).getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("students", students);
        return xlsService.generate("foreignstudentstatistics.xls", data);
    }

    @SuppressWarnings("boxing")
    public Page<ReportStudentDataDto> studentData(HoisUserDetails user, StudentDataCommand criteria, Pageable pageable) {
        String SEARCH_FROM = "from student s "
                + "join person p on s.person_id = p.id "
                + "left join student_group sg on s.student_group_id = sg.id "
                + "left join curriculum_version cv on s.curriculum_version_id = cv.id "
                + "left join curriculum c on cv.curriculum_id = c.id "
                + "left join school_department sd1 on (cv.school_department_id is not null and sd1.id = cv.school_department_id) "
                + "left join student_curriculum_completion scc on scc.student_id = s.id "
                + "left join (select cd.curriculum_id, string_agg(sd.id\\:\\:text, ', ') as ids, string_agg(sd.name_et, ', ') as nameEt "
                    + "from curriculum_department cd "
                    + "join school_department sd on cd.school_department_id = sd.id "
                    + "group by cd.curriculum_id) "
                    + "sd2 on (cv.school_department_id is null and sd2.curriculum_id = c.id) ";
        
        if (Boolean.TRUE.equals(criteria.getDirectiveTypesShow()) || (criteria.getDirectiveTypes() != null && !criteria.getDirectiveTypes().isEmpty()) || 
            Boolean.TRUE.equals(criteria.getDirectiveReasonsShow()) || (criteria.getDirectiveReasons() != null && !criteria.getDirectiveReasons().isEmpty()) || 
            Boolean.TRUE.equals(criteria.getDirectiveConfirmDateShow()) || criteria.getDirectiveConfirmDateFrom() != null || criteria.getDirectiveConfirmDateThru() != null) {
            SEARCH_FROM += "left join (select ds.id, ds.student_id, d.type_code, ds.reason_code, d.confirm_date "
                    + "from directive d "
                    + "join directive_student ds on ds.directive_id = d.id "
                    + "where d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                    + "and ds.canceled != true) D1 on D1.student_id = s.id ";
        }
        
        if (Boolean.TRUE.equals(criteria.getEapSumShow()) || criteria.getEapSum() != null) SEARCH_FROM += creditSubQuery(criteria);
        
        if (Boolean.TRUE.equals(criteria.getWeightedAverageShow()) || criteria.getWeightedAverage() != null) SEARCH_FROM += weightedAverageSubQuery(criteria);
        
        if (Boolean.TRUE.equals(criteria.getAverageSumShow()) || criteria.getAverageSum() != null) SEARCH_FROM += averageSubQuery(null, null, "averageSum");
        
        if (Boolean.TRUE.equals(criteria.getAverageShow()) || criteria.getAverage() != null) SEARCH_FROM += averageSubQuery(criteria);
        
        if (Boolean.TRUE.equals(criteria.getDebtSumShow()) || criteria.getDebtSum() != null) SEARCH_FROM += debtSubQuery(null, null, "debtSum");
        
        if (Boolean.TRUE.equals(criteria.getDebtShow()) || criteria.getDebt() != null) SEARCH_FROM += debtSubQuery(criteria);
        
        if (Boolean.TRUE.equals(criteria.getDebtPointsSumShow()) || criteria.getDebtPointsSum() != null) SEARCH_FROM += debtPointsSubQuery(null, null, "debtPointsSum");
        
        if (Boolean.TRUE.equals(criteria.getDebtPointsShow()) || criteria.getDebtPoints() != null) SEARCH_FROM += debtPointsSubQuery(criteria);
        
        if (Boolean.TRUE.equals(criteria.getDeclaredEapShow()) || criteria.getDeclaredEap() != null) SEARCH_FROM += declaredEAP(criteria);

        if (Boolean.TRUE.equals(criteria.getDeclaredSubjectShow()) || (criteria.getDeclaredSubject() != null && !criteria.getDeclaredSubject().isEmpty())) SEARCH_FROM += declaredSubject(criteria);

        if (Boolean.TRUE.equals(criteria.getActiveResultShow()) || (criteria.getActiveResult() != null && !criteria.getActiveResult().isEmpty())) SEARCH_FROM += activeResult(criteria);
        
        if (Boolean.TRUE.equals(criteria.getDeclarationConfirmationShow()) || criteria.getDeclarationConfirmationFrom() != null || criteria.getDeclarationConfirmationThru() != null) SEARCH_FROM += declarationConfirmation(user);
        
        String sortFields = (criteria.getOrderField1() != null && !StringUtils.isEmpty(criteria.getOrderField1()) ? (criteria.getOrderField1() + (Boolean.TRUE.equals(criteria.getOrderField1Desc()) ? " desc, " : ", " )) : "") +
                            (criteria.getOrderField2() != null && !StringUtils.isEmpty(criteria.getOrderField2()) ? (criteria.getOrderField2() + (Boolean.TRUE.equals(criteria.getOrderField2Desc()) ? " desc, " : ", " )) : "") +
                            (criteria.getOrderField3() != null && !StringUtils.isEmpty(criteria.getOrderField3()) ? (criteria.getOrderField3() + (Boolean.TRUE.equals(criteria.getOrderField3Desc()) ? " desc" : "" )) : "");
        
        String[] sortList = sortFields.equals("") ? null : sortFields.split(", ");
    
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(((sortList != null && sortList.length != 0) ? new Sort(sortList) : pageable.getSort()));
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        
        setHeaderDataCriteria(qb, criteria);
        
        setPersonDataCriteria(qb, criteria);
        
        setStudyDataCriteria(qb, criteria);
        
        setContactDataCriteria(qb, criteria);
        
        setStatisticsDataCriteria(qb, criteria);
        
        setAddInfoDataCriteria(qb, criteria);
        
        String PERSON_DATA_SELECT = "s.id, p.firstname, p.lastname, p.sex_code as sex, coalesce(p.idcode, p.foreign_idcode) as idCode, "
                + "p.bankaccount, p.birthdate, p.residence_country_code as residence_country, p.citizenship_code as citizenship, ";
        
        String STUDY_DATA_SELECT = "case when s.type_code = '" + StudentType.OPPUR_K.name() + "' then true else false end as guest_student, "
                                 + "case when s.status_code = '" + StudentStatus.OPPURSTAATUS_V.name() + "' then true else false end as foreign_student, "
                                 + (Boolean.TRUE.equals(criteria.getCumLaudeShow()) ? "exists(select 1 from directive d "
                                         + "join directive_student ds on ds.directive_id = d.id "
                                         + "where d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                                         + "and d.type_code = '" + DirectiveType.KASKKIRI_LOPET.name() + "' "
                                         + "and ds.canceled != true "
                                         + "and ds.is_cum_laude = true "
                                         + "and ds.student_id = s.id) as cum_laude, " : "null as cum_laude, ")
                                 + "s.study_start as immat_date, "
                                 + "s.study_end as finished_date, "
                                 + (Boolean.TRUE.equals(criteria.getDirectiveTypesShow()) ? "D1.type_code as directive_types, " : "null as directive_types, ")
                                 + (Boolean.TRUE.equals(criteria.getDirectiveConfirmDateShow()) ? "D1.confirm_date as directive_confirm_date, " : "null as directive_confirm_date, ")
                                 + (Boolean.TRUE.equals(criteria.getDirectiveReasonsShow()) ? "D1.reason_code as directive_reasons, " : "null as directive_reasons, ")
                                 + "sg.id as studentGroupId, sg.code as student_groups, s.status_code as student_statuses, s.study_form_code as study_form, s.study_load_code as study_load, coalesce(sd1.name_et, sd2.nameEt) as school_department, "
                                 + "c.id as curriculumId, c.code || ' ' || c.name_et as curriculum_et, c.code || ' ' || c.name_en as curriculum_en, "
                                 + "c.mer_code as ehis_code, c.orig_study_level_code as study_level, sg.speciality_code as speciality, sg.course as study_year_nr, "
                                 + "s.fin_specific_code as fin, s.language_code as language, "
                                 + "case when round((c.credits + scc.study_backlog) * 100 / coalesce(case when c.credits is not null and c.credits != 0 then c.credits else null end, 1)) is null then 0 else round((c.credits + scc.study_backlog) * 100 / coalesce(case when c.credits is not null and c.credits != 0 then c.credits else null end, 1)) end as curriculum_percentage, ";
        
        String CONTACT_DATA_SELECT = "p.address, p.phone, s.email as official_email, p.email as personal_email, ";
        
        String STATISTICS_DATA_SELECT = "scc.credits as eap"
                + ", scc.average_mark as weighted_average_sum"
                + (Boolean.TRUE.equals(criteria.getEapSumShow()) ? ", case when credits.sum is null then '0' else credits.sum end as eap_sum" : ", null as eap_sum")
                + (Boolean.TRUE.equals(criteria.getWeightedAverageShow()) ? ", case when weightedAverage.weightedAverage is null then '0' else weightedAverage.weightedAverage end as weighted_average" : ", null as weighted_average")
                + (Boolean.TRUE.equals(criteria.getAverageSumShow()) ? ", case when averageSum.averageSum is null then '0' else averageSum.averageSum end as average_sum" : ", null as average_sum")
                + (Boolean.TRUE.equals(criteria.getAverageShow()) ? ", case when average.average is null then '0' else average.average end as average" : ", null as average")
                + (Boolean.TRUE.equals(criteria.getDebtSumShow()) ? ", case when debtSum.debtSum is null then '0' else debtSum.debtSum end as debt_sum" : ", null as debt_sum")
                + (Boolean.TRUE.equals(criteria.getDebtShow()) ? ", case when debt.debt is null then '0' else debt.debt end as debt" :", null as debt")
                + (Boolean.TRUE.equals(criteria.getDebtPointsSumShow()) ? ", case when debtPointsSum.debtPointsSum is null then '0' else debtPointsSum.debtPointsSum end as debt_points_sum" : ", null as debt_points_sum")
                + (Boolean.TRUE.equals(criteria.getDebtPointsShow()) ? ", case when debtPoints.debtPoints is null then '0' else debtPoints.debtPoints end as debt_points" : ", null as debt_points")
                + (Boolean.TRUE.equals(criteria.getDeclaredEapShow()) ? ", case when declaredEAP.declaredEAP is null then '0' else declaredEAP.declaredEAP end as declared_eap" : ", null as declared_eap");
        
        String ADD_INFO_SELECT = 
               (Boolean.TRUE.equals(criteria.getActiveResultShow()) ? 
                    ", activeResult.grade_code as grade_code, activeResult.id as subjectId, activeResult.code || ' - ' || activeResult.name_et as active_result_subject_et, activeResult.code || ' - ' || activeResult.name_en as active_result_subject_en" 
                    : ", null as grade_code, null as subjectId, null as active_result_subject_et, null as active_result_subject_en")
                + (Boolean.TRUE.equals(criteria.getDeclaredSubjectShow()) ? 
                    ", declaredSubject.id as declaredSubjectId, declaredSubject.code || ' - ' || declaredSubject.name_et as declared_subject_et, declaredSubject.code || ' - ' || declaredSubject.name_en as declared_subject_en" 
                    : ", null as declaredSubjectId, null as declared_subject_et, null as declared_subject_en")
                + (Boolean.TRUE.equals(criteria.getDeclarationConfirmationShow()) ? ", declarationConfirmation.confirm_date as declaration_confirmation" : ", null as declaration_confirmation")
                + ", s.previous_school_name, extract(year from s.previous_school_end_date) as completed_school_year";
        Holder<Integer> i = new Holder<>(Integer.valueOf(0));
        return JpaQueryUtil.pagingResult(qb, 
                PERSON_DATA_SELECT + STUDY_DATA_SELECT + CONTACT_DATA_SELECT + STATISTICS_DATA_SELECT + ADD_INFO_SELECT, em, pageable).map(r -> {
                    i.value ++;
                    return new ReportStudentDataDto(r, criteria, i.value);
                });
    }

    private static void setAddInfoDataCriteria(JpaNativeQueryBuilder qb, StudentDataCommand criteria) {
        qb.optionalCriteria("declarationConfirmation.confirm_date >= :confirmationFrom", "confirmationFrom", criteria.getDeclarationConfirmationFrom());
        qb.optionalCriteria("declarationConfirmation.confirm_date <= :confirmationThru", "confirmationThru", criteria.getDeclarationConfirmationThru());
        qb.optionalContains("s.previous_school_name", "previousSchoolName", criteria.getPreviousSchoolName());
        if (criteria.getCompletedSchoolYearSign() != null) {
            qb.optionalCriteria("extract(year from s.previous_school_end_date) " + criteria.getCompletedSchoolYearSign() + " :completedSchoolYear", "completedSchoolYear", criteria.getCompletedSchoolYear());
        }
    }

    private String declarationConfirmation(HoisUserDetails user) {
        Long currentStudyPeriod = studyYearService.getCurrentStudyPeriod(user.getSchoolId());
        return "join (select d.confirm_date, d.student_id "
                + "from declaration d "
                + "where d.study_period_id = " + currentStudyPeriod + " "
                + "and d.status_code = '" + DeclarationStatus.OPINGUKAVA_STAATUS_K.name() + "') as declarationConfirmation on declarationConfirmation.student_id = s.id ";
    }

    private static String declaredSubject(StudentDataCommand criteria) {
        return "join (select distinct on (s.id, d.student_id) d.student_id, s.id, s.name_et, s.name_en, s.code from declaration d "
                + "join declaration_subject ds on ds.declaration_id = d.id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject s on s.id = ssp.subject_id "
                + "where d.status_code = '" + DeclarationStatus.OPINGUKAVA_STAATUS_K.name() + "' "
                + (criteria.getDeclaredSubject() != null  ? "and s.id in (" + String.join(", ", criteria.getDeclaredSubject().stream().map(String::valueOf).collect(Collectors.toList())) + ") " : "")
                + (Boolean.TRUE.equals(criteria.getDeclaredSubjectRepetitive()) ? 
                        "and exists( "
                        + "select * from declaration_subject ds2 "
                        + "join declaration d2 on d2.id = ds2.declaration_id "
                        + "join subject_study_period ssp2 on ssp2.id = ds2.subject_study_period_id "
                        + "and ds2.declaration_id <> ds.declaration_id "
                        + "and d2.student_id = d.student_id "
                        + "and ssp2.subject_id = ssp.subject_id) ": "")
                + ") as declaredSubject on declaredSubject.student_id = s.id ";
    }

    private static String activeResult(StudentDataCommand criteria) {
        return "left join (select shr.student_id, shr.grade_code, s.id, s.name_et, s.name_en, s.code from student_higher_result shr "
                + "join subject s on shr.subject_id = s.id "
                + "where shr.is_active = true "
                + (criteria.getActiveResult() != null  ? "and shr.subject_id in (" + String.join(", ", criteria.getActiveResult().stream().map(String::valueOf).collect(Collectors.toList())) + ") " : "")
                + (Boolean.TRUE.equals(criteria.getActiveResultPositive()) ? "and shr.grade_code in (:higherPositiveGrades) " : "")
                + (Boolean.FALSE.equals(criteria.getActiveResultPositive()) ? "and shr.grade_code not in (:higherPositiveGrades) " : "")
                + ") as activeResult on activeResult.student_id = s.id " 
                + ((Boolean.TRUE.equals(criteria.getDeclaredSubjectShow()) 
                        || (criteria.getDeclaredSubject() != null && !criteria.getDeclaredSubject().isEmpty())) ? "and activeResult.id = declaredSubject.id " : "");
    }

    /**
     * Only in higher
     * @param criteria
     * @return
     */
    private String declaredEAP(StudentDataCommand criteria) {
        Long studyPeriodId = criteria.getDeclaredEapPeriod();
        LocalDate startDate = criteria.getDeclaredEapFrom();
        LocalDate endDate = criteria.getDeclaredEapThru();
        if (studyPeriodId != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
            startDate = sp.getStartDate();
            endDate = sp.getEndDate();
        }
        return "left join(select sum(s.credits) as declaredEap, d.student_id from " +
                    "declaration_subject ds " +
                    "join declaration d on d.id = ds.declaration_id and d.status_code = '" + DeclarationStatus.OPINGUKAVA_STAATUS_K.name() + "' " +
                    "left join subject_study_period ssp on ssp.id = ds.subject_study_period_id " +
                    "left join subject s on ssp.subject_id = s.id " +
                    (startDate != null ? "and d.confirm_date >= :declaredFrom " : "") +
                    (endDate != null ? "and d.confirm_date <= :declaredThru " : "") +
                    "group by d.student_id) declaredEap on declaredEap.student_id = s.id ";
    }

    private String debtPointsSubQuery(StudentDataCommand criteria) {
        Long studyPeriodId = criteria.getDebtPointsPeriod();
        LocalDate startDate = criteria.getDebtPointsFrom();
        LocalDate endDate = criteria.getDebtPointsThru();
        if (studyPeriodId != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
            startDate = sp.getStartDate();
            endDate = sp.getEndDate();
        }
        return debtPointsSubQuery(startDate, endDate, "debtPoints");
    }

    private static String debtPointsSubQuery(LocalDate startDate, LocalDate endDate, String variable) {
        return "left join (select sum(" + variable + ") as " + variable + ", student_id from (select " + variable + ", student_id from " +
                "(select sum(s.credits) as " + variable + ", d.student_id from " +
                    "declaration_subject ds " +
                    "join declaration d on d.id = ds.declaration_id and d.status_code = '" + DeclarationStatus.OPINGUKAVA_STAATUS_K.name() + "' " +
                    "left join subject_study_period ssp on ssp.id = ds.subject_study_period_id " +
                    "left join subject s on ssp.subject_id = s.id " +
                    "left join student_higher_result shr on shr.subject_id = ssp.subject_id " +
                    "where shr.grade_code not in (:higherPositiveGrades) " +
                    "and shr.is_active = true " +
                    (startDate != null ? "and shr.grade_date >= :debtPointsFrom " : "") +
                    (endDate != null ? "and shr.grade_date <= :debtPointsThru " : "") +
                    "group by d.student_id) as " + variable + " " +
                "union all " +
                "select sum(credits) as " + variable + ", student_id from (select distinct on (svr.curriculum_version_omodule_id) svr.credits, svr.student_id " +
                    "from student_vocational_result svr " +
                    "where svr.grade_code not in (:vocationalPositiveGrades) " +
                    (startDate != null ? "and svr.grade_date >= :debtPointsFrom " : "") +
                    (endDate != null ? "and svr.grade_date <= :debtPointsThru " : "") +
                    "order by svr.curriculum_version_omodule_id, coalesce(svr.changed, svr.inserted) desc) as " + variable +
                    " group by student_id ) as " + variable + " group by student_id " +
            ") as " + variable + " on " + variable + ".student_id = s.id ";
    }

    private String debtSubQuery(StudentDataCommand criteria) {
        Long studyPeriodId = criteria.getDebtPeriod();
        LocalDate startDate = criteria.getDebtFrom();
        LocalDate endDate = criteria.getDebtThru();
        if (studyPeriodId != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
            startDate = sp.getStartDate();
            endDate = sp.getEndDate();
        }
        return debtSubQuery(startDate, endDate, "debt");
    }

    private static String debtSubQuery(LocalDate startDate, LocalDate endDate, String variable) {
        return "left join (select " + variable + "." + variable + ", student_id from " +
                    "(select count(distinct ds.id) as " + variable + ", d.student_id from " +
                        "declaration_subject ds " +
                        "join declaration d on d.id = ds.declaration_id and d.status_code = '" + DeclarationStatus.OPINGUKAVA_STAATUS_K.name() + "' " +
                        "left join subject_study_period ssp on ssp.id = ds.subject_study_period_id " +
                        "left join student_higher_result shr on shr.subject_id = ssp.subject_id " +
                        "where shr.grade_code not in (:higherPositiveGrades) " +
                        "and shr.is_active = true " +
                        (startDate != null ? "and shr.grade_date >= :debtFrom " : "") +
                        (endDate != null ? "and shr.grade_date <= :debtThru " : "") +
                        "group by d.student_id) as " + variable + " " +
                        "union all " +
                    "select count(distinct je.curriculum_module_outcomes_id) as " + variable + ", js.student_id from " +
                        "journal_entry je join journal_entry_student jes on jes.journal_entry_id = je.id " +
                        "join journal_student js on  jes.journal_student_id = js.id " +
                        "where jes.grade_code not in (:vocationalPositiveGrades) " +
                        (startDate != null ? "and jes.grade_inserted >= :debtFrom " : "") +
                        (endDate != null ? "and jes.grade_inserted <= :debtThru " : "") +
                        "group by js.student_id " +
                        "union all " +
                    "select count(distinct id), student_id from (select distinct on (svr.curriculum_version_omodule_id) svr.id, svr.student_id " +
                        "from student_vocational_result svr " +
                        "where svr.grade_code not in (:vocationalPositiveGrades) " +
                        (startDate != null ? "and svr.grade_date >= :debtFrom " : "") +
                        (endDate != null ? "and svr.grade_date <= :debtThru " : "") +
                        "order by svr.curriculum_version_omodule_id, coalesce(svr.changed, svr.inserted) desc) as " + variable +
                        " group by student_id " +
                ") as " + variable + " on " + variable + ".student_id = s.id ";
    }

    private String averageSubQuery(StudentDataCommand criteria) {
        Long studyPeriodId = criteria.getAveragePeriod();
        LocalDate startDate = criteria.getAverageFrom();
        LocalDate endDate = criteria.getAverageThru();
        if (studyPeriodId != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
            startDate = sp.getStartDate();
            endDate = sp.getEndDate();
        }
        return averageSubQuery(startDate, endDate, "average");
    }
    
    private static String averageSubQuery(LocalDate startDate, LocalDate endDate, String variable) {
        return "left join (select " + variable + ", student_id from (" +
                "select * from (select floor((sum(grade_mark) * 100) / count(distinct id)) / 100 as " + variable + ", student_id " +
                "from (select distinct on (svr.curriculum_version_omodule_id) svr.id, svr.grade_mark, svr.student_id " +
                "from student_vocational_result svr " +
                "where svr.grade_code in (:vocationalPositiveGrades) " +
                (startDate != null ? "and svr.grade_date >= :averageFrom " : "") +
                (endDate != null ? "and svr.grade_date <= :averageThru " : "") +
                "and svr.grade_mark is not null and svr.grade_mark != 0 " +
                "and svr.credits is not null and credits != 0 " +
                "order by svr.curriculum_version_omodule_id, coalesce(svr.changed, svr.inserted) desc) " +
                "as distinct_module_credits group by student_id) credits_sum where credits_sum." + variable + " is not null " +
                "union all " +
                "select floor((sum(shr.grade_mark) * 100) / count(distinct shr.id)) / 100 as " + variable + ", shr.student_id " +
                "from student_higher_result shr " +
                "where shr.grade_code in (:higherPositiveGrades) " +
                "and shr.is_active = true " +
                (startDate != null ? "and shr.grade_date >= :averageFrom " : "") +
                (endDate != null ? "and shr.grade_date <= :averageThru " : "") +
                "and shr.grade_mark is not null and shr.grade_mark != 0 " +
                "and shr.credits is not null and shr.credits != 0 " +
                "group by shr.student_id) spCredits ) as " + variable + " on " + variable + ".student_id = s.id ";
    }

    private String creditSubQuery(StudentDataCommand criteria) {
        Long studyPeriodId = criteria.getEapSumPeriod();
        LocalDate startDate = criteria.getEapSumFrom();
        LocalDate endDate = criteria.getEapSumThru();
        if (studyPeriodId != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
            startDate = sp.getStartDate();
            endDate = sp.getEndDate();
        }
        return "left join (select spCredits.sum, spCredits.student_id from (" +
        "select * from (select sum(credits), student_id from (select distinct on (svr.curriculum_version_omodule_id) svr.credits, svr.student_id " +
        "from student_vocational_result svr " +
        "where svr.grade_code in (:vocationalPositiveGrades) " +
        (startDate != null ? "and svr.grade_date >= :creditsFrom " : "") +
        (endDate != null ? "and svr.grade_date <= :creditsThru " : "") +
        (Boolean.TRUE.equals(criteria.getEapSumApel()) ? "" : "and svr.apel_application_record_id is null ") +
        "order by svr.curriculum_version_omodule_id, coalesce(svr.changed, svr.inserted) desc) " +
        "as distinct_module_credits group by student_id) credits_sum where sum is not null " +
        "union all " +
        "select sum(shr.credits), shr.student_id from student_higher_result shr " +
        "where shr.grade_code in (:higherPositiveGrades) " +
        "and shr.is_active = true " +
        (startDate != null ? "and shr.grade_date >= :creditsFrom " : "") +
        (endDate != null ? "and shr.grade_date <= :creditsThru " : "") +
        (Boolean.TRUE.equals(criteria.getEapSumApel()) ? "" : "and shr.apel_application_record_id is null ") +
        "group by shr.student_id) spCredits) as credits on credits.student_id = s.id ";
    }

    private String weightedAverageSubQuery(StudentDataCommand criteria) {
        Long studyPeriodId = criteria.getWeightedAveragePeriod();
        LocalDate startDate = criteria.getWeightedAverageFrom();
        LocalDate endDate = criteria.getWeightedAverageThru();
        if (studyPeriodId != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
            startDate = sp.getStartDate();
            endDate = sp.getEndDate();
        }
        
        return "left join (select weightedAverage, student_id from (" +
                "select * from (select floor(sum(gradeMark)*100/sum(credits))/100 as weightedAverage, student_id " +
                "from (select distinct on (svr.curriculum_version_omodule_id) svr.credits * svr.grade_mark as gradeMark, svr.credits, svr.student_id " +
                "from student_vocational_result svr " +
                "where svr.grade_code in (:vocationalPositiveGrades) " +
                (startDate != null ? "and svr.grade_date >= :weightedAverageFrom " : "") +
                (endDate != null ? "and svr.grade_date <= :weightedAverageThru " : "") +
                "and svr.grade_mark is not null and svr.grade_mark != 0 " +
                "and svr.credits is not null and credits != 0 " +
                "order by svr.curriculum_version_omodule_id, coalesce(svr.changed, svr.inserted) desc) as distinct_module_credits group by student_id) credits_sum where credits_sum.weightedAverage is not null " +
                "union all " +
                "select floor(sum(shr.credits * shr.grade_mark)*100/sum(shr.credits))/100 as weightedAverage, shr.student_id " +
                "from student_higher_result shr " +
                "where shr.grade_code in (:higherPositiveGrades) " +
                "and shr.is_active = true " +
                (startDate != null ? "and shr.grade_date >= :weightedAverageFrom " : "") +
                (endDate != null ? "and shr.grade_date <= :weightedAverageThru " : "") +
                "and shr.grade_mark is not null and shr.grade_mark != 0 " +
                "and shr.credits is not null and shr.credits != 0 " +
                "group by shr.student_id) spCredits ) as weightedAverage on weightedAverage.student_id = s.id ";
    }
    
    private void setStatisticsDataCriteria(JpaNativeQueryBuilder qb, StudentDataCommand criteria) {
        if (criteria.getEapSign() != null) {
            qb.optionalCriteria("scc.credits " + criteria.getEapSign() + " :eap", "eap", criteria.getEap());
        }
        
        if (Boolean.TRUE.equals(criteria.getEapSumShow()) || criteria.getEapSum() != null) {
            if (criteria.getEapSumSign() != null) {
                qb.optionalCriteria("credits.sum " + criteria.getEapSumSign() + " :eapSum", "eapSum", criteria.getEapSum());
            }
            Long studyPeriodId = criteria.getEapSumPeriod();
            LocalDate startDate = criteria.getEapSumFrom();
            LocalDate endDate = criteria.getEapSumThru();
            if (studyPeriodId != null) {
                StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
                startDate = sp.getStartDate();
                endDate = sp.getEndDate();
            }
            if (startDate != null) qb.parameter("creditsFrom", JpaQueryUtil.parameterAsTimestamp(startDate));
            if (endDate != null) qb.parameter("creditsThru", JpaQueryUtil.parameterAsTimestamp(endDate));
        }
        if (criteria.getWeightedAverageSumSign() != null) {
            qb.optionalCriteria("scc.average_mark " + criteria.getWeightedAverageSumSign() + " :weightedAverageSum", "weightedAverageSum", criteria.getWeightedAverageSum());
        }
        if (Boolean.TRUE.equals(criteria.getWeightedAverageShow()) || criteria.getWeightedAverage() != null) {
            if (criteria.getWeightedAverageSign() != null) {
                qb.optionalCriteria("weightedAverage.weightedAverage " + criteria.getWeightedAverageSign() + " :weightedAverage", "weightedAverage", criteria.getWeightedAverage());
            }
            Long studyPeriodId = criteria.getWeightedAveragePeriod();
            LocalDate startDate = criteria.getWeightedAverageFrom();
            LocalDate endDate = criteria.getWeightedAverageThru();
            if (studyPeriodId != null) {
                StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
                startDate = sp.getStartDate();
                endDate = sp.getEndDate();
            }
            if (startDate != null) qb.parameter("weightedAverageFrom", JpaQueryUtil.parameterAsTimestamp(startDate));
            if (endDate != null) qb.parameter("weightedAverageThru", JpaQueryUtil.parameterAsTimestamp(endDate));
        }
        if (criteria.getAverageSumSign() != null) {
            qb.optionalCriteria("averageSum.averageSum " + criteria.getAverageSumSign() + " :averageSum", "averageSum", criteria.getAverageSum());
        }
        if (Boolean.TRUE.equals(criteria.getAverageShow()) || criteria.getAverage() != null) {
            if (criteria.getAverageSign() != null) {
                qb.optionalCriteria("average.average " + criteria.getAverageSign() + " :average", "average", criteria.getAverage());
            }
            Long studyPeriodId = criteria.getAveragePeriod();
            LocalDate startDate = criteria.getAverageFrom();
            LocalDate endDate = criteria.getAverageThru();
            if (studyPeriodId != null) {
                StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
                startDate = sp.getStartDate();
                endDate = sp.getEndDate();
            }
            if (startDate != null) qb.parameter("averageFrom", JpaQueryUtil.parameterAsTimestamp(startDate));
            if (endDate != null) qb.parameter("averageThru", JpaQueryUtil.parameterAsTimestamp(endDate));
        }
        if (criteria.getDebtSumSign() != null) {
            qb.optionalCriteria("debtSum.debtSum " + criteria.getDebtSumSign() + " :debtSum", "debtSum", criteria.getDebtSum());
        }
        if (Boolean.TRUE.equals(criteria.getDebtShow()) || criteria.getDebt() != null) {
            if (criteria.getDebtSign() != null) {
                qb.optionalCriteria("debt.debt " + criteria.getDebtSign() + " :debt", "debt", criteria.getDebt());
            }
            Long studyPeriodId = criteria.getDebtPeriod();
            LocalDate startDate = criteria.getDebtFrom();
            LocalDate endDate = criteria.getDebtThru();
            if (studyPeriodId != null) {
                StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
                startDate = sp.getStartDate();
                endDate = sp.getEndDate();
            }
            if (startDate != null) qb.parameter("debtFrom", JpaQueryUtil.parameterAsTimestamp(startDate));
            if (endDate != null) qb.parameter("debtThru", JpaQueryUtil.parameterAsTimestamp(endDate));
        }
        if (criteria.getDebtPointsSumSign() != null) {
            qb.optionalCriteria("debtPointsSum.debtPointsSum " + criteria.getDebtPointsSumSign() + " :debtPointsSum", "debtPointsSum", criteria.getDebtPointsSum());
        }
        if (Boolean.TRUE.equals(criteria.getDebtPointsShow()) || criteria.getDebtPoints() != null) {
            if (criteria.getDebtPointsSign() != null) {
                qb.optionalCriteria("debtPoints.debtPoints " + criteria.getDebtPointsSign() + " :debtPoints", "debtPoints", criteria.getDebtPoints());
            }
            Long studyPeriodId = criteria.getDebtPointsPeriod();
            LocalDate startDate = criteria.getDebtPointsFrom();
            LocalDate endDate = criteria.getDebtPointsThru();
            if (studyPeriodId != null) {
                StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
                startDate = sp.getStartDate();
                endDate = sp.getEndDate();
            }
            if (startDate != null) qb.parameter("debtPointsFrom", JpaQueryUtil.parameterAsTimestamp(startDate));
            if (endDate != null) qb.parameter("debtPointsThru", JpaQueryUtil.parameterAsTimestamp(endDate));
        }
        if (Boolean.TRUE.equals(criteria.getDeclaredEapShow()) || criteria.getDeclaredEap() != null) {
            if (criteria.getDeclaredEapSign() != null) {
                qb.optionalCriteria("declaredEap.declaredEap " + criteria.getDeclaredEapSign() + " :declaredEap", "declaredEap", criteria.getDeclaredEap());
            }
            Long studyPeriodId = criteria.getDeclaredEapPeriod();
            LocalDate startDate = criteria.getDeclaredEapFrom();
            LocalDate endDate = criteria.getDeclaredEapThru();
            if (studyPeriodId != null) {
                StudyPeriod sp = em.getReference(StudyPeriod.class, studyPeriodId);
                startDate = sp.getStartDate();
                endDate = sp.getEndDate();
            }
            if (startDate != null) qb.parameter("declaredFrom", JpaQueryUtil.parameterAsTimestamp(startDate));
            if (endDate != null) qb.parameter("declaredThru", JpaQueryUtil.parameterAsTimestamp(endDate));
        }
        qb.filter(":vocationalPositiveGrades = :vocationalPositiveGrades");
        qb.filter(":higherPositiveGrades = :higherPositiveGrades");
        qb.parameter("vocationalPositiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        qb.parameter("higherPositiveGrades", HigherAssessment.GRADE_POSITIVE);
    }

    private static void setHeaderDataCriteria(JpaNativeQueryBuilder qb, StudentDataCommand criteria) {
        if (ReportStudentData.STUDENT_DATA_ACTIVE.name().equals(criteria.getResultType())) {
            qb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses", StudentStatus.STUDENT_STATUS_ACTIVE);
        } else if (ReportStudentData.STUDENT_DATA_STUDENTGROUP.name().equals(criteria.getResultType())) {
            qb.filter("s.student_group_id is not null");
        } else if (ReportStudentData.STUDENT_DATA_STUDYING.name().equals(criteria.getResultType())) {
            qb.requiredCriteria("s.status_code = :studyingStatus", "studyingStatus", StudentStatus.OPPURSTAATUS_O.name());
        } else if (ReportStudentData.STUDENT_DATA_GRADUATES.name().equals(criteria.getResultType())) {
            qb.requiredCriteria("s.status_code = :finishedStatus", "finishedStatus", StudentStatus.OPPURSTAATUS_L.name());
        }
        if (Boolean.TRUE.equals(criteria.getWithoutGuestStudents())) {
            qb.requiredCriteria("s.type_code != :guestStudentType", "guestStudentType", StudentType.OPPUR_K.name());
        }
    }

    private static void setContactDataCriteria(JpaNativeQueryBuilder qb, StudentDataCommand criteria) {
        qb.optionalContains("p.address", "address", criteria.getAddress());
        qb.optionalContains("p.phone", "phone", criteria.getPhone());
        qb.optionalContains("p.email", "personalEmail", criteria.getPersonalEmail());
        qb.optionalContains("s.email", "officialEmail", criteria.getOfficialEmail());
    }

    private static void setStudyDataCriteria(JpaNativeQueryBuilder qb, StudentDataCommand criteria) {
        Boolean isGuestStudent = criteria.getGuestStudent();
        if (Boolean.TRUE.equals(isGuestStudent)) {
            qb.requiredCriteria("s.type_code = :guestStudentType", "guestStudentType", StudentType.OPPUR_K.name());
        } else if (Boolean.FALSE.equals(isGuestStudent)) {
            qb.requiredCriteria("s.type_code != :guestStudentType", "guestStudentType", StudentType.OPPUR_K.name());
        }
        
        Boolean isForeignStudent = criteria.getForeignStudent();
        if (Boolean.TRUE.equals(isForeignStudent)) {
            qb.requiredCriteria("s.status_code = :foreignStudentStatus", "foreignStudentStatus", StudentStatus.OPPURSTAATUS_V.name());
        } else if (Boolean.FALSE.equals(isForeignStudent)) {
            qb.requiredCriteria("s.status_code != :foreignStudentStatus", "foreignStudentStatus", StudentStatus.OPPURSTAATUS_V.name());
        }
        
        Boolean isCumLaude = criteria.getCumLaude();
        if (Boolean.TRUE.equals(isCumLaude)) {
            qb.filter("exists(select 1 from directive d "
                    + "join directive_student ds on ds.directive_id = d.id "
                    + "where d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                    + "and d.type_code = '" + DirectiveType.KASKKIRI_LOPET.name() + "' "
                    + "and ds.canceled != true "
                    + "and ds.is_cum_laude = true "
                    + "and ds.student_id = s.id)");
        } else if (Boolean.FALSE.equals(isCumLaude)) {
            qb.filter("not exists(select 1 from directive d "
                    + "join directive_student ds on ds.directive_id = d.id "
                    + "where d.status_code = '" + DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name() + "' "
                    + "and d.type_code = '" + DirectiveType.KASKKIRI_LOPET.name() + "' "
                    + "and ds.canceled != true "
                    + "and ds.is_cum_laude = true "
                    + "and ds.student_id = s.id)");
        }
        
        qb.optionalCriteria("s.study_start >= :immatFrom", "immatFrom", criteria.getImmatDateFrom());
        qb.optionalCriteria("s.study_start <= :immatThru", "immatThru", criteria.getImmatDateThru());
        qb.optionalCriteria("(s.study_end >= :finishedFrom or s.study_end is null)", "finishedFrom", criteria.getFinishedDateFrom());
        qb.optionalCriteria("s.study_end <= :finishedThru", "finishedThru", criteria.getFinishedDateThru());
        qb.optionalCriteria("D1.type_code in (:directiveTypes)", "directiveTypes", criteria.getDirectiveTypes());
        qb.optionalCriteria("D1.reason_code in (:directiveReasons)", "directiveReasons", criteria.getDirectiveReasons());
        qb.optionalCriteria("D1.confirm_date >= :confirmFrom", "confirmFrom", criteria.getDirectiveConfirmDateFrom());
        qb.optionalCriteria("D1.confirm_date <= :confirmThru", "confirmThru", criteria.getDirectiveConfirmDateThru());
        qb.optionalCriteria("sg.id in (:sgIds)", "sgIds", criteria.getStudentGroups());
        qb.optionalCriteria("s.status_code in (:studentStatuses)", "studentStatuses", criteria.getStudentStatuses());
        qb.optionalCriteria("s.study_form_code in (:studyForms)", "studyForms", criteria.getStudyForm());
        qb.optionalCriteria("s.study_load_code in (:studyLoads)", "studyLoads", criteria.getStudyLoad());
        // school department uses array in array
        List<Long> schoolDepartments = criteria.getSchoolDepartment();
        if (schoolDepartments != null && schoolDepartments.size() != 0) {
            String arrayInArray = "";
            for (int i = 0; i < schoolDepartments.size(); i++) {
                Long id = schoolDepartments.get(i);
                if (i == schoolDepartments.size() - 1) {
                    arrayInArray += "'" + id + "' in (sd2.ids)";
                } else {
                    arrayInArray += "'" + id + "' in (sd2.ids) or ";
                }
            }
            qb.optionalCriteria("(" + arrayInArray + " or sd1.id in (:schoolDepartments))", "schoolDepartments", schoolDepartments);
        }
        
        qb.optionalCriteria("c.id in (:curriculumIds)", "curriculumIds", criteria.getCurriculum());
        qb.optionalContains("c.mer_code", "ehisCode", criteria.getEhisCode());
        qb.optionalCriteria("c.orig_study_level_code in (:studyLevels)", "studyLevels", criteria.getStudyLevel());
        qb.optionalCriteria("sg.speciality_code in (:specialityCodes)", "specialityCodes", criteria.getSpeciality());
        qb.optionalCriteria("sg.course = :studyYearNumber", "studyYearNumber", criteria.getStudyYearNumber());
        qb.optionalCriteria("s.fin_specific_code in (:finCodes)", "finCodes", criteria.getFin());
        qb.optionalCriteria("s.language_code in (:studyLanguages)", "studyLanguages", criteria.getLanguage());
        qb.optionalCriteria("round((c.credits + scc.study_backlog) * 100 / coalesce(case when c.credits is not null and c.credits != 0 then c.credits else null end, 1)) > :curriculumPercentageFrom", "curriculumPercentageFrom", criteria.getCurriculumPercentageFrom());
        qb.optionalCriteria("round((c.credits + scc.study_backlog) * 100 / coalesce(case when c.credits is not null and c.credits != 0 then c.credits else null end, 1)) < :curriculumPercentageThru", "curriculumPercentageThru", criteria.getCurriculumPercentageThru());
    }

    private static void setPersonDataCriteria(JpaNativeQueryBuilder qb, StudentDataCommand criteria) {
        qb.optionalContains("p.firstname", "firstName", criteria.getFirstname());
        qb.optionalContains("p.lastname", "lastName", criteria.getLastname());
        qb.optionalCriteria("p.sex_code = :sexCode", "sexCode", criteria.getSex());
        qb.optionalContains("coalesce(p.idcode, p.foreign_idcode)", "idCode", criteria.getIdcode());
        qb.optionalContains("p.bankaccount", "bankAccount", criteria.getBankaccount());
        qb.optionalCriteria("p.birthdate >= :birthDayFrom", "birthDayFrom", criteria.getBirthdateFrom());
        qb.optionalCriteria("p.birthdate <= :birthDayThru", "birthDayThru", criteria.getBirthdateThru());
        qb.optionalCriteria("p.residence_country_code in :residenceCountryCodes", "residenceCountryCodes", criteria.getResidenceCountry());
        qb.optionalCriteria("p.citizenship_code in :citizenshipCodes", "citizenshipCodes", criteria.getCitizenship());
    }

    public byte[] studentsDataAsExcel(HoisUserDetails user, StudentDataCommand criteria, Pageable pageable) {
        List<ReportStudentDataDto> students = studentData(user, criteria, new PageRequest(0, Integer.MAX_VALUE, pageable.getSort())).getContent();
        Map<String, Object> data = new HashMap<>();
        List<String> header = convertToHeader(criteria, "studentData");
        List<List<Object>> studentData = getData(header, students);
        data.put("data", studentData);
        data.put("header", header);
        data.put("criteria", criteria);
        return xlsService.generate("studentsdata.xls", data);
    }

    private List<List<Object>> getData(List<String> header, List<?> students) {
        ClassifierUtil.ClassifierCache classifierCache = new ClassifierUtil.ClassifierCache(classifierService);
        List<String> classifierFields = Arrays.asList("sex", "residenceCountry", "citizenship", "directiveTypes", "fin", 
                "speciality", "studentStatuses", "studyForm", "studyLoad", "studyLevel", "language", "activeResult", "studyYear");
        List<String> booleanTranslatable = Arrays.asList("guestStudent", "foreignStudent", "cumLaude");
        List<List<Object>> result = new ArrayList<>();
        for (Object student : students) {
            List<Object> studentData = new ArrayList<>();
            for (String fieldNameTranslation : header) {
                Method classMethod;
                try {
                    String[] splittedTranslation = fieldNameTranslation.split("\\.");
                    String fieldName = splittedTranslation[splittedTranslation.length - 1];
                    classMethod = student.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                    Object value = classMethod.invoke(student);
                    if (value instanceof String) {
                        if ("directiveReasons".equals(fieldName)) {
                            if (((String)value).startsWith("AKADPUHKUS_POHJUS")) {
                                value = classifierCache.getByCode((String)value, MainClassCode.valueOf("AKADPUHKUS_POHJUS"));
                            } else if (((String)value).startsWith("EKSMAT_POHJUS")) {
                                value = classifierCache.getByCode((String)value, MainClassCode.valueOf("EKSMAT_POHJUS"));
                            } else if (((String)value).startsWith("KASKKIRI_STIPTOETL_POHJUS")) {
                                value = classifierCache.getByCode((String)value, MainClassCode.valueOf("KASKKIRI_STIPTOETL_POHJUS"));
                            }
                        } else if (classifierFields.contains(fieldName)) {
                            value = classifierCache.getByCode((String)value, MainClassCode.valueOf(((String)value).split("\\_")[0]));
                        }
                    } else if (value instanceof Boolean && booleanTranslatable.contains(fieldName)) {
                        if (Boolean.TRUE.equals(value)) {
                            value = TranslateUtil.translate("yes", Language.ET);
                        } else {
                            value = TranslateUtil.translate("no", Language.ET);
                        }
                    }
                    studentData.add(value);
                } catch (@SuppressWarnings("unused") Exception e) {
                    continue;
                }
            }
            result.add(studentData);
        }
        return result;
    }

    private static List<String> convertToHeader(Object criteria, String variable) {
        List<String> header = new ArrayList<>();
        List<Method> methods = Arrays.asList(criteria.getClass().getDeclaredMethods());
        List<Method> sortedMethods = methods.stream().filter(p -> p.getName().endsWith("Show") && p.getName().startsWith("get")).collect(Collectors.toList());
        // Sort methos as they are in front-end table
        // @Order should be used on getter fields of criteria since reflection takes methods in random order
        sortedMethods.sort(new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                Order or1 = o1.getAnnotation(Order.class);
                Order or2 = o2.getAnnotation(Order.class);
                // nulls last
                if (or1 != null && or2 != null) {
                    return or1.value() - or2.value();
                } else
                if (or1 != null && or2 == null) {
                    return -1;
                } else
                if (or1 == null && or2 != null) {
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
        // Get names of fields that should be shown
        for (Method method : sortedMethods) {
            try {
                Method classMethod = criteria.getClass().getMethod(method.getName());
                Boolean value = (Boolean) classMethod.invoke(criteria);
                if (Boolean.TRUE.equals(value)) {
                    String translation = method.getName().replace("get", "").replace("Show", "");
                    header.add("report." + variable + "." + translation.substring(0, 1).toLowerCase() + translation.substring(1));
                    if ("ActiveResult".equals(translation)) {
                        header.add("report." + variable + "." + translation.substring(0, 1).toLowerCase() + translation.substring(1) + "Subject");
                    }
                }
            } catch (@SuppressWarnings("unused") Exception e) {
                continue;
            }
        }
        return header;
    }
    
    private void validateQuery(HoisUserDetails user, QuerySaveCommand criteria) {
        List<SchoolQuery> schoolQueries = em.createQuery("select sq from SchoolQuery sq where sq.school.id = :schoolId and lower(sq.nameEt) = :schoolQueryName", SchoolQuery.class)
            .setParameter("schoolId", user.getSchoolId())
            .setParameter("schoolQueryName", criteria.getQueryNameEt().toLowerCase())
            .getResultList();
            if (!schoolQueries.isEmpty()) {
                throw new HoisException("report.studentData.error.repetitiveName");
            }
    }

    public void saveQuery(HoisUserDetails user, StudentDataCommand criteria) {
        validateQuery(user, criteria);
        
        SchoolQuery schoolQuery = new SchoolQuery();
        schoolQuery.setNameEt(criteria.getQueryNameEt());
        schoolQuery.setIsWithoutGuests(criteria.getWithoutGuestStudents());
        schoolQuery.setQuerySubType(criteria.getResultType());
        schoolQuery.setOrderby1(criteria.getOrderField1());
        schoolQuery.setOrderby2(criteria.getOrderField2());
        schoolQuery.setOrderby3(criteria.getOrderField3());
        schoolQuery.setIsOrderby1Desc(criteria.getOrderField1Desc());
        schoolQuery.setIsOrderby2Desc(criteria.getOrderField2Desc());
        schoolQuery.setIsOrderby3Desc(criteria.getOrderField3Desc());
        schoolQuery.setSchool(em.getReference(School.class, user.getSchoolId()));
        schoolQuery.setIsStudentQuery(Boolean.TRUE);
        schoolQuery = EntityUtil.save(schoolQuery, em);
        // Get names of fields that should be shown
        saveQueryCriteria(schoolQuery, criteria);
    }
    
    public void saveQuery(HoisUserDetails user, SubjectStudyPeriodDataCommand criteria) {
        validateQuery(user, criteria);
        
        SchoolQuery schoolQuery = new SchoolQuery();
        schoolQuery.setNameEt(criteria.getQueryNameEt());
        schoolQuery.setSchool(em.getReference(School.class, user.getSchoolId()));
        schoolQuery.setIsStudentQuery(Boolean.FALSE);
        schoolQuery = EntityUtil.save(schoolQuery, em);
        // Get names of fields that should be shown
        saveQueryCriteria(schoolQuery, criteria);
    }
    
    /**
     * Uses reflection to save all fields with 'Show' ending as main code
     * Main code, 'Show' and 'Sign' fields are saved in one object, otherwise creates new object
     * @param schoolQuery
     * @param criteria
     * @param objectClass 
     */
    private void saveQueryCriteria(SchoolQuery schoolQuery, Object criteria) {
        List<Method> methods = Arrays.asList(criteria.getClass().getDeclaredMethods());
        List<Method> mainMethods = methods.stream().filter(p -> p.getName().endsWith("Show") && p.getName().startsWith("get")).collect(Collectors.toList());
        List<SchoolQueryCriteria> schoolQueryCriterias = new ArrayList<>();
        for (Method mainMethodShow : mainMethods) {
            try {
                String mainMethod = mainMethodShow.getName().replace("Show", "");
                // fields that are saved in the same object
                List<String> methodNames = Arrays.asList(mainMethod, mainMethod + "Show", mainMethod + "Sign");
                List<Method> existingMethods = new ArrayList<>();
                for (String method : methodNames) {
                    try {
                        Method classMethod = criteria.getClass().getMethod(method);
                        Object value = classMethod.invoke(criteria);
                        if (value != null) {
                            if (!mainMethod.endsWith("Show") || (mainMethod.endsWith("Show") && Boolean.TRUE.equals(value))) {
                                existingMethods.add(classMethod);
                            }
                        }
                    } catch (@SuppressWarnings("unused") NoSuchMethodException nsme) {
                        continue;
                    }
                }
                SchoolQueryCriteria schoolQueryCriteria = new SchoolQueryCriteria();
                schoolQueryCriteria.setSchoolQuery(schoolQuery);
                schoolQueryCriteria.setCriteriaCode(mainMethod.replace("get", ""));
                for (Method classMethod : existingMethods) {
                    Object value = classMethod.invoke(criteria);
                    if (classMethod.getName().endsWith("Show")) {
                        schoolQueryCriteria.setShowInResults((Boolean) value);
                    }  else if (classMethod.getName().endsWith("Sign")) {
                        schoolQueryCriteria.setCriteriaCondition(value.toString());
                    } else if (classMethod.getName().equals(mainMethod)) {
                        schoolQueryCriteria.setCriteriaVal1(value.toString());
                    }
                }
                if (existingMethods.size() != 0) schoolQueryCriterias.add(schoolQueryCriteria);
                // fields that are saved as separate objects
                methodNames = Arrays.asList(mainMethod + "From", mainMethod + "Thru", mainMethod + "Period", mainMethod + "Apel", mainMethod + "Repetitive", mainMethod + "Positive");
                existingMethods = new ArrayList<>();
                for (String method : methodNames) {
                    try {
                        Method classMethod = criteria.getClass().getMethod(method);
                        Object value = classMethod.invoke(criteria);
                        if (value != null) {
                            existingMethods.add(classMethod);
                        }
                    } catch (@SuppressWarnings("unused") NoSuchMethodException nsme) {
                        continue;
                    }
                }
                for (Method classMethod : existingMethods) {
                    schoolQueryCriteria = new SchoolQueryCriteria();
                    schoolQueryCriteria.setSchoolQuery(schoolQuery);
                    schoolQueryCriteria.setCriteriaCode(classMethod.getName().replace("get", ""));
                    Object value = classMethod.invoke(criteria);
                    schoolQueryCriteria.setCriteriaVal1(value.toString());
                    schoolQueryCriterias.add(schoolQueryCriteria);
                }
            } catch (@SuppressWarnings("unused") SecurityException | IllegalAccessException | InvocationTargetException e) {
                continue;
            }
        }
        schoolQueryCriterias.forEach(p -> EntityUtil.save(p, em));
    }

    public List<AutocompleteResult> savedStudentQueries(HoisUserDetails user, SchoolQueryCommand command) {
        List<?> data = em.createNativeQuery("select sq.id, sq.name_et from school_query sq where sq.school_id = :schoolId and sq.is_student_query = :isStudentQuery")
                .setParameter("schoolId", user.getSchoolId())
                .setParameter("isStudentQuery", command.getIsStudentQuery())
                .getResultList();
        return data.stream().map(p -> new AutocompleteResult(resultAsLong(p, 0), resultAsString(p, 1), resultAsString(p, 1))).collect(Collectors.toList());
    }

    public void deleteQuery(HoisUserDetails user, SchoolQuery schoolQuery) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(schoolQuery, em);
    }

    @SuppressWarnings("boxing")
    public Page<SubjectStudyPeriodDataDto> subjectStudyPeriodData(HoisUserDetails user, SubjectStudyPeriodDataCommand criteria, Pageable pageable) {
        
        String SEARCH_FROM = "from subject s "
                + "left join subject_study_period ssp on ssp.subject_id = s.id "
                + "left join study_period sp on sp.id = ssp.study_period_id "
                + "left join study_year sy on sp.study_year_id = sy.id "
                + (Boolean.TRUE.equals(criteria.getOptionalSubjectShow()) || criteria.getOptionalSubject() != null || 
                    Boolean.TRUE.equals(criteria.getCurriculumShow()) || criteria.getCurriculum() != null ?
                    "left join curriculum_version_hmodule_subject cvhs on cvhs.subject_id = s.id " : "")
                + (Boolean.TRUE.equals(criteria.getCurriculumShow()) || criteria.getCurriculum() != null ? 
                    "left join curriculum_version_hmodule cvh on cvhs.curriculum_version_hmodule_id = cvh.id "
                    + "left join curriculum_version cv on cvh.curriculum_version_id = cv.id "
                    + "left join curriculum c on cv.curriculum_id = c.id " : "")
                + "left join (select string_agg(p.firstname || ' ' || p.lastname, ', ' order by p.firstname || ' ' || p.lastname) as name, array_agg(t.id\\:\\:character varying) as teacherIds, sspt.subject_study_period_id "
                    + "from subject_study_period_teacher sspt "
                    + "join teacher t on sspt.teacher_id = t.id "
                    + "join person p on t.person_id = p.id "
                    + "group by sspt.subject_study_period_id"
                    + ") teachers on teachers.subject_study_period_id = ssp.id "
                + "left join (select string_agg(sg.code, ', ' order by sg.code) as studentGroupCodes, array_agg(sg.id\\:\\:character varying) as studentGroupIds, array_agg(distinct(sg.course)\\:\\:character varying) as studentGroupCourses, sspsg.subject_study_period_id "
                    + "from subject_study_period_student_group sspsg "
                    + "join student_group sg on sspsg.student_group_id = sg.id "
                    + "group by sspsg.subject_study_period_id"
                    + ") groups on groups.subject_study_period_id = ssp.id "
                + "left join (select string_agg(p.protocol_nr\\:\\:character varying, ', ' order by p.protocol_nr) as protocolNumbers, ph.subject_study_period_id "
                    + "from protocol_hdata ph "
                    + "join protocol p on p.id = ph.protocol_id "
                    + "group by ph.subject_study_period_id "
                    + ") protocols on protocols.subject_study_period_id = ssp.id";
    
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains("s.code", "subjectCode", criteria.getSubjectCode());
        qb.optionalCriteria("s.id = :subjectEt", "subjectEt", criteria.getSubjectEt());
        qb.optionalCriteria("s.id = :subjectEn", "subjectEn", criteria.getSubjectEn());
        qb.optionalCriteria(":teacherId\\:\\:character varying = any(teachers.teacherIds)", "teacherId", criteria.getTeacher());
        qb.optionalCriteria("c.id in (:curriculumIds)", "curriculumIds", criteria.getCurriculum());
        if (criteria.getStudyYearSign() != null) {
            qb.optionalCriteria("sy.year_code " + criteria.getStudyYearSign() + " :yearCode", "yearCode", criteria.getStudyYear());
        }
        qb.optionalCriteria("sp.type_code in (:periodCodes)", "periodCodes", criteria.getStudyPeriod());
        if (criteria.getCourse() != null) {
            String result = "(";
            for (int i = 0; i < criteria.getCourse().size(); i++) {
                String course = criteria.getCourse().get(i);
                if (i == criteria.getCourse().size() - 1) {
                    result += course + "\\:\\:character varying = any(groups.studentGroupCourses)";
                } else {
                    result += course + "\\:\\:character varying = any(groups.studentGroupCourses) or ";
                }
            }
            result += ") ";
            qb.filter(result);
        }
        qb.optionalCriteria(":studentGroupId\\:\\:character varying = any(groups.studentGroupIds)", "studentGroupId", criteria.getStudentGroup());
        if (criteria.getEapSign() != null) {
            qb.optionalCriteria("s.credits " + criteria.getEapSign() + " :eap", "eap", criteria.getEap());
        }
        qb.optionalCriteria("cvhs.is_optional = :optionalSubject", "optionalSubject", criteria.getOptionalSubject());
        qb.optionalContains("ssp.moodle_course_id\\:\\:character varying", "moodleId", criteria.getMoodleId());
        if (Boolean.TRUE.equals(criteria.getProtocol())) {
            qb.filter("protocols.protocolNumbers is not null");
        } else if (Boolean.FALSE.equals(criteria.getProtocol())) {
            qb.filter("protocols.protocolNumbers is null");
        }
        
        String SELECT = "s.code, s.id, s.name_et, s.name_en, s.credits, teachers.name, "
                + (Boolean.TRUE.equals(criteria.getCurriculumShow()) ? "c.id as curriculumId, c.code || ' ' || c.name_et as curriculumEt, c.code || ' ' || c.name_en as curriculumEn, " :
                    "null as curriculumId, null as curriculumEt, null as curriculumEn, ")
                + "sy.year_code, sp.name_et as studyPeriodEt, sp.name_en as studyPeriodEn, array_to_string(groups.studentGroupCourses, ', '), "
                + (Boolean.TRUE.equals(criteria.getOptionalSubjectShow()) ? " cvhs.is_optional as isOptional, " : "null as isOptional, ")
                + "groups.studentGroupCodes, ssp.moodle_course_id, protocols.protocolNumbers";
        String GROUP_BY = "s.code, s.id, s.name_et, s.name_en, s.credits, teachers.name, "
                + (Boolean.TRUE.equals(criteria.getCurriculumShow()) ? "c.id, c.code || ' ' || c.name_et, c.code || ' ' || c.name_en, " : "")
                + "sy.year_code, sp.name_et, sp.name_en, groups.studentGroupCourses, "
                + (Boolean.TRUE.equals(criteria.getOptionalSubjectShow()) ? "cvhs.is_optional, " : "")
                + "groups.studentGroupCodes, ssp.moodle_course_id, protocols.protocolNumbers, ssp.id";
        qb.groupBy(GROUP_BY);
        Holder<Integer> i = new Holder<>(Integer.valueOf(0));
        return JpaQueryUtil.pagingResult(qb, SELECT, em, pageable).map(r -> {
            i.value ++;
            return new SubjectStudyPeriodDataDto(r, i.value);
        });
    }

    public Object getQuery(SchoolQuery schoolQuery, Class<? extends SchoolQueryDto>  commandClass) {
        ClassifierUtil.ClassifierCache classifierCache = new ClassifierUtil.ClassifierCache(classifierService);
        SchoolQueryDto command;
        try {
            command = commandClass.newInstance();
        } catch (@SuppressWarnings("unused") InstantiationException | IllegalAccessException e1) {
            return null;
        }
        command.setResultType(schoolQuery.getQuerySubType());
        command.setWithoutGuestStudents(schoolQuery.getIsWithoutGuests());
        command.setOrderField1(schoolQuery.getOrderby1());
        command.setOrderField2(schoolQuery.getOrderby2());
        command.setOrderField3(schoolQuery.getOrderby3());
        command.setOrderField1Desc(schoolQuery.getIsOrderby1Desc());
        command.setOrderField2Desc(schoolQuery.getIsOrderby2Desc());
        command.setOrderField3Desc(schoolQuery.getIsOrderby3Desc());
        List<SchoolQueryCriteria> criterias = schoolQuery.getSchoolQueryCriteria();
        for (SchoolQueryCriteria criteria : criterias) {
            String criteriaCode = criteria.getCriteriaCode();
            if (Boolean.TRUE.equals(criteria.getShowInResults())) {
                String method = "set" + criteriaCode + "Show";
                try {
                    Method classMethod = command.getClass().getMethod(method, Boolean.class);
                    classMethod.invoke(command, criteria.getShowInResults());
                } catch (@SuppressWarnings("unused") Exception e) {
                    continue;
                }
            }
            if (criteria.getCriteriaCondition() != null) {
                String method = "set" + criteriaCode + "Sign";
                try {
                    Method classMethod = command.getClass().getMethod(method, String.class);
                    classMethod.invoke(command, criteria.getCriteriaCondition());
                } catch (@SuppressWarnings("unused") Exception e) {
                    continue;
                }
            }
            if (criteria.getCriteriaVal1() != null) {
                List<Method> methods = Arrays.asList(commandClass.getMethods());
                String methodName = "set" + criteriaCode;
                Optional<Method> method = methods.stream().filter(p -> methodName.equals(p.getName())).findFirst();
                if (method.isPresent()) {
                    Method classMethod = method.get();
                    Class<?> usedClass = classMethod.getParameterTypes()[0];
                    try {
                        if (usedClass.equals(Boolean.class)) {
                            classMethod.invoke(command, Boolean.valueOf(criteria.getCriteriaVal1()));
                        } else if (usedClass.equals(String.class)) {
                            classMethod.invoke(command, criteria.getCriteriaVal1());
                        } else if (usedClass.equals(Long.class)) {
                            classMethod.invoke(command, Long.valueOf(criteria.getCriteriaVal1()));
                        } else if (usedClass.equals(BigDecimal.class)) {
                            classMethod.invoke(command, new BigDecimal(criteria.getCriteriaVal1()));
                        } else if (usedClass.equals(LocalDate.class)) {
                            classMethod.invoke(command, LocalDate.parse(criteria.getCriteriaVal1()));
                        } else if (usedClass.equals(AutocompleteResult.class)) {
                            // Autocomplete single fields
                            AutocompleteResult autocompleteResult = null;
                            if ("SubjectEt".equals(criteriaCode) || "SubjectEn".equals(criteriaCode)) {
                                autocompleteResult = AutocompleteResult.of(em.getReference(Subject.class, Long.valueOf(criteria.getCriteriaVal1())));
                            } else if ("StudentGroup".equals(criteriaCode)) {
                                autocompleteResult = AutocompleteResult.of(em.getReference(StudentGroup.class, Long.valueOf(criteria.getCriteriaVal1())));
                            } else if ("Teacher".equals(criteriaCode)) {
                                autocompleteResult = AutocompleteResult.of(em.getReference(Teacher.class, Long.valueOf(criteria.getCriteriaVal1())));
                            }
                            classMethod.invoke(command, autocompleteResult);
                        } else if (usedClass.equals(List.class)) {
                            ParameterizedType type = (ParameterizedType)classMethod.getGenericParameterTypes()[0];
                            Type classType = type.getActualTypeArguments()[0];
                            String listVal = criteria.getCriteriaVal1();
                            List<String> stringValues = Arrays.asList(listVal.substring(1, listVal.length() - 1).split(", "));
                            if (classType.equals(ClassifierResult.class)) {
                                String classifierName = stringValues.get(0);
                                List<Classifier> classifiers = stringValues.stream().map(p -> classifierCache.getByCode(p, MainClassCode.valueOf(classifierName.split("\\_")[0]))).collect(Collectors.toList());
                                List<ClassifierResult> classifierResults = classifiers.stream().map(p -> ClassifierResult.of(p)).collect(Collectors.toList());
                                classMethod.invoke(command, classifierResults);
                            } else if (classType.equals(Long.class)) {
                                List<Long> longValues = stringValues.stream().map(Long::valueOf).collect(Collectors.toList());
                                classMethod.invoke(command, longValues);
                            } else if (classType.equals(String.class)) {
                                classMethod.invoke(command, stringValues);
                            } else if (classType.equals(AutocompleteResult.class)) {
                                // Autocomplete list fields
                                List<Long> longValues = stringValues.stream().map(Long::valueOf).collect(Collectors.toList());
                                List<AutocompleteResult> autocompleteValues = null;
                                if ("StudentGroups".equals(criteriaCode)) {
                                    autocompleteValues = longValues.stream().map(p -> AutocompleteResult.of(em.getReference(StudentGroup.class, p))).collect(Collectors.toList());
                                } else if ("SchoolDepartment".equals(criteriaCode)) {
                                    autocompleteValues = longValues.stream().map(p -> AutocompleteResult.of(em.getReference(SchoolDepartment.class, p))).collect(Collectors.toList());
                                } else if ("Curriculum".equals(criteriaCode)) {
                                    autocompleteValues = longValues.stream().map(p -> AutocompleteResult.of(em.getReference(Curriculum.class, p))).collect(Collectors.toList());
                                } else if ("ActiveResult".equals(criteriaCode) || "DeclaredSubject".equals(criteriaCode)) {
                                    autocompleteValues = longValues.stream().map(p -> AutocompleteResult.of(em.getReference(Subject.class, p))).collect(Collectors.toList());
                                }
                                classMethod.invoke(command, autocompleteValues);
                            }
                        }
                    } catch (@SuppressWarnings("unused") IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        continue;
                    }
                }
            }
            
        }
        return command;
    }

    public byte[] subjectStudyPeriodDataAsExcel(HoisUserDetails user, SubjectStudyPeriodDataCommand criteria,
            Pageable pageable) {
        List<SubjectStudyPeriodDataDto> students = subjectStudyPeriodData(user, criteria, new PageRequest(0, Integer.MAX_VALUE, pageable.getSort())).getContent();
        Map<String, Object> data = new HashMap<>();
        List<String> header = convertToHeader(criteria, "subjectStudyPeriodData");
        List<List<Object>> studentData = getData(header, students);
        data.put("data", studentData);
        data.put("header", header);
        data.put("criteria", criteria);
        return xlsService.generate("subjectstudyperioddata.xls", data);
    }
}
