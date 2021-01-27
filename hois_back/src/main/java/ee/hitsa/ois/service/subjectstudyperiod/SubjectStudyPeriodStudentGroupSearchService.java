package ee.hitsa.ois.service.subjectstudyperiod;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.service.SubjectStudyPeriodPlanService;
import ee.hitsa.ois.service.XlsService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodStudentGroupSearchDto;
import ee.hitsa.ois.web.dto.lessonPlan.LessonPlanXlsCapacityDto;
import ee.hitsa.ois.web.dto.subjectstudyperiod.SubjectStudyPeriodExistingPlanDto;
import ee.hitsa.ois.web.dto.subjectstudyperiod.TeacherWithWorkLoadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodCapacitiesService.SQL_SELECT_TEACHER_CAPACITY;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

@Transactional
@Service
public class SubjectStudyPeriodStudentGroupSearchService {
    
    private static final String TIMETABLE_SELECT = " t.id ";
    private static final String TIMETABLE_FROM = " from timetable t "
            + "join timetable_object tobj on tobj.timetable_id = t.id "
            + "join timetable_object_student_group sg on sg.timetable_object_id = tobj.id ";

    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;

    public Page<SubjectStudyPeriodStudentGroupSearchDto> searchByStudentGroup(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_student_group sspsg " +
                "join student_group sg on sspsg.student_group_id = sg.id " +
                "join subject_study_period ssp on sspsg.subject_study_period_id = ssp.id " +
                "join study_period sp on ssp.study_period_id = sp.id " +
                "join study_year sy on sp.study_year_id = sy.id " +
                "join curriculum c on sg.curriculum_id = c.id " +
                "left join curriculum_version cv on sg.curriculum_version_id = cv.id ");
        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("sg.id in :sgIds", "sgIds", criteria.getStudentGroup());
        qb.optionalCriteria("sg.curriculum_id = :cId", "cId", criteria.getCurriculum());
        qb.optionalCriteria("sg.curriculum_version_id = :cvId", "cvId", criteria.getCurriculumVersion());
        if (criteria.getDepartment() != null) {
            qb.requiredCriteria("exists(select 1 from curriculum_department cd " +
                            "where cd.school_department_id in :departmentIds and cd.curriculum_id = sg.curriculum_id)",
                    "departmentIds", Collections.singletonList(criteria.getDepartment()));
        }
        qb.filter("c.is_higher = true");
        qb.validNowCriteria("sg.valid_from", "sg.valid_thru");
        qb.optionalCriteria((criteria.getStudyPeriod() != null ? "sp.id = :timeId" : "sy.id = :timeId"),
                "timeId", criteria.getStudyPeriod() != null ? criteria.getStudyPeriod() : criteria.getStudyYear());

        qb.sort(pageable);
        qb.groupBy("sg.id, c.id, cv.id, sp.id");
        // Object[] {studentGroupId, studyPeriodId}
        Page<Object> pagingResult = JpaQueryUtil.pagingResult(qb, "sg.id as sgid, sp.id as spid", em, pageable);
        List<Long> groupIds = pagingResult.getContent().stream()
                .map(r -> resultAsLong(r, 0))
                .collect(Collectors.toList());

        if (groupIds.isEmpty()) {
            // Empty mapping for return
            return pagingResult.map(r -> new SubjectStudyPeriodStudentGroupSearchDto());
        }

        // fetch everything we need for this request to decrease amount of requests
        em.createQuery("select sg from StudentGroup sg " +
                "join fetch sg.subjectStudyPeriods sspsg " +
                "join fetch sspsg.subjectStudyPeriod ssp " +
                "join fetch ssp.studyPeriod " +
                "join fetch sg.curriculum " +
                "left join fetch sg.curriculumVersion " +
                "where sg.id in :sgIds", StudentGroup.class)
                .setParameter("sgIds", groupIds)
                .getResultList();

        return pagingResult.map(r -> getSearchDto(
                em.getReference(StudentGroup.class, resultAsLong(r, 0)),
                em.getReference(StudyPeriod.class, resultAsLong(r, 1))));
    }

    private SubjectStudyPeriodStudentGroupSearchDto getSearchDto(StudentGroup sg, StudyPeriod studyPeriod) {
        SubjectStudyPeriodStudentGroupSearchDto dto = new SubjectStudyPeriodStudentGroupSearchDto();
        dto.setId(EntityUtil.getId(sg));
        dto.setCode(sg.getCode());
        dto.setCurriculum(curriculum(sg.getCurriculum()));
        dto.setCurriculumVersion(AutocompleteResult.of(sg.getCurriculumVersion()));
        dto.setHours(getHours(sg, studyPeriod.getId()));
        dto.setStudyPeriod(AutocompleteResult.ofWithYear(studyPeriod));
        /*
         * This check prevents the exception in 
         * TimetableEventService.getGeneralTimetableCurriculum
         * When user proceeds to watch timetable 
         */
        if(sg.getCurriculumVersion() != null) {
            dto.setTimetable(getTimetable(dto.getId(), studyPeriod.getId()));
        }
        return dto;
    }

    private static Long getHours(StudentGroup sg, Long studyPeriod) {
        List<SubjectStudyPeriod> ssps = SubjectStudyPeriodUtil.filterSsps
                (StreamUtil.toMappedList(sspSg -> sspSg.getSubjectStudyPeriod(), sg.getSubjectStudyPeriods()), studyPeriod);
        return SubjectStudyPeriodUtil.getHours(ssps);
    }

    private Long getTimetable(Long studentGroup, Long studyPeriod) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(TIMETABLE_FROM);
        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", studyPeriod);
        qb.requiredCriteria("sg.student_group_id = :studentGroup", "studentGroup", studentGroup);
        qb.filter(" t.start_date <= current_date ");
        qb.filter(" t.end_date >= current_date ");
        List<?> result = qb.select(TIMETABLE_SELECT, em).setMaxResults(1).getResultList();
        if(result.isEmpty()) {
            return null;
        }
        return resultAsLong(result.get(0), 0);
    }

    private static AutocompleteResult curriculum(Curriculum curriculum) {
        String nameEt = curriculum.getCode() + " - " + curriculum.getNameEt();
        String nameEn = curriculum.getCode() + " - " + curriculum.getNameEn();
        return new AutocompleteResult(EntityUtil.getId(curriculum), nameEt, nameEn);
    }
    
    public byte[] searchByStudentGroupAsExcel(Long schoolId, SubjectStudyPeriodSearchCommand criteria) {
        List<SubjectStudyPeriodStudentGroupSearchDto> studentGroups = searchByStudentGroup(schoolId, criteria,
                new PageRequest(0, Integer.MAX_VALUE, Direction.ASC, "sg.code")).getContent();

        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("subjectstudyperiod.studyYear", criteria.getStudyYear() != null
                ? AutocompleteResult.of(em.getReference(StudyYear.class, criteria.getStudyYear())) : "-");
        parameters.put("subjectstudyperiod.period", criteria.getStudyPeriod() != null
                ? em.getReference(StudyPeriod.class, criteria.getStudyPeriod()) : "-");

        Map<String, Object> data = new HashMap<>();
        data.put("studentGroups", studentGroups);
        data.put("parameters", parameters);
        return xlsService.generate("searchByStudentGroup.xls", data);
    }

    public byte[] teacherCapacitiesAsExcel(SubjectStudyPeriodSearchCommand criteria, HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from " + 
                "subject_study_period ssp " +
                "join subject s on ssp.subject_id = s.id " +
                "join subject_study_period_teacher sspt on ssp.id = sspt.subject_study_period_id " +
                "join study_period sp on sp.id = ssp.study_period_id " +
                "join study_year sy on sy.id = sp.study_year_id " +
                "join teacher t on sspt.teacher_id = t.id " +
                "join person p on t.person_id = p.id " +
                "left join classifier g_prop on g_prop.code = ssp.group_proportion_code");
        qb.requiredCriteria("sy.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());
        qb.optionalCriteria("sy.id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("ssp.subject_id = :subjectId", "subjectId", criteria.getSubject());
        if (user.isTeacher()) {
            qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        } else {
            qb.optionalCriteria("sspt.teacher_id in (:teacherId)", "teacherId", criteria.getTeacher());
        }
        qb.optionalCriteria("exists(select 1 " +
                        "from subject_study_period_student_group sspsg " +
                        "join student_group sg on sspsg.student_group_id = sg.id " +
                        "join curriculum_department cd on cd.curriculum_id = sg.curriculum_id " +
                        "where cd.school_department_id = :departmentId " +
                        "and sspsg.subject_study_period_id = ssp.id)",
                "departmentId", criteria.getDepartment());
        qb.optionalCriteria("exists(select 1 from subject_study_period_student_group sspsg "
                + "where sspsg.subject_study_period_id = ssp.id "
                + "and sspsg.student_group_id in (:studentGroupIds))", "studentGroupIds", criteria.getStudentGroup());
        qb.sort("sp.start_date, s.code, s.name_et");
        List<?> teachers = qb.select("sspt.id as studyPeriodTeacherId, "
                + "s.code as subjectCode, s.id as subjectId, s.name_et as subjecEt, s.name_en as subjectEn, "
                + "p.firstname || ' ' || p.lastname, g_prop.name_et, "
                + "sp.id as periodId, sp.name_et as periodEt, sp.name_en as periodEn", em).getResultList();
        Set<Long> subjectStudyPeriodTeacherIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), teachers);
        List<ClassifierDto> teacherCapacities = getTeacherCapacities(user.getSchoolId(), subjectStudyPeriodTeacherIds, criteria);
        teacherCapacities.sort(Comparator.comparing(ClassifierDto::getValue, String.CASE_INSENSITIVE_ORDER));
        Map<String, Object> data = new HashMap<>();
        Map<Long, List<LessonPlanXlsCapacityDto>> capacitiesPerTeacherId = teacherCapacities(user, teacherCapacities, criteria);
        Map<Long, List<String>> subjectStudyPeriodStudentGroups = subjectStudyPeriodStudentGroups(subjectStudyPeriodTeacherIds);
        Map<Long, List<String>> subjectStudyPeriodSubGroups = subjectStudyPeriodSubGroups(subjectStudyPeriodTeacherIds);
        List<TeacherWithWorkLoadDto> subjectStudyPeriodTeachers = StreamUtil.toMappedList(r -> {
            TeacherWithWorkLoadDto dto = new TeacherWithWorkLoadDto();
            dto.setSubjectCode(resultAsString(r, 1));
            dto.setSubject(new AutocompleteResult(resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 4)));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 5), resultAsString(r, 5)));
            dto.setProportion(resultAsString(r, 6));
            dto.setStudyPeriod(new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 8), resultAsString(r, 9)));
            List<LessonPlanXlsCapacityDto> capacities = capacitiesPerTeacherId.get(resultAsLong(r, 0));
            if (capacities != null && !capacities.isEmpty()) {
                setCapacitySummaries(dto, capacities);
                capacities.sort(Comparator.comparing(LessonPlanXlsCapacityDto::getCapacityType, String.CASE_INSENSITIVE_ORDER));
                dto.setCapacities(capacities);
            }
            String subGroupString = null;
            String studentGroupString = null;
            List<String> studentgroups = subjectStudyPeriodStudentGroups.get(resultAsLong(r, 0));
            if (studentgroups != null) {
                studentGroupString = studentgroups.stream().collect(Collectors.joining(", "));
            }
            List<String> subgroups = subjectStudyPeriodSubGroups.get(resultAsLong(r, 0));
            if (subgroups != null) {
                subGroupString = subgroups.stream().collect(Collectors.joining(", "));
            }
            dto.setGroups((studentGroupString != null ? studentGroupString : "") + 
                    (subGroupString != null && studentGroupString != null ? " / " : "") + 
                    (subGroupString != null ? subGroupString : ""));
            return dto;
        }, teachers);
        data.put("teachers", subjectStudyPeriodTeachers);
        data.put("capacities", teacherCapacities);
        data.put("studyPeriods", Collections.singletonList(null));
        return xlsService.generate("workloadsummary.xls", data);
    }
    
    private Map<Long, List<String>> subjectStudyPeriodSubGroups(Set<Long> subjectStudyPeriodTeacherIds) {
        if (subjectStudyPeriodTeacherIds == null || subjectStudyPeriodTeacherIds.isEmpty()) {
            return new LinkedHashMap<>();
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from "
                + "subject_study_period_teacher sspt "
                + "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                + "join subject_study_period_subgroup sspsg on sspsg.subject_study_period_id = ssp.id");

        qb.requiredCriteria("sspt.id in (:teacherIds)", "teacherIds", subjectStudyPeriodTeacherIds);
        List<?> data = qb.select(
                "sspt.id as teacherId, sspsg.id, sspsg.code",
                em).getResultList();
        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0),  LinkedHashMap::new, Collectors.mapping(r -> {
                    return resultAsString(r, 2);
                }, Collectors.toList())));
    }

    private Map<Long, List<String>> subjectStudyPeriodStudentGroups(Set<Long> subjectStudyPeriodTeacherIds) {
        if (subjectStudyPeriodTeacherIds == null || subjectStudyPeriodTeacherIds.isEmpty()) {
            return new LinkedHashMap<>();
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from "
                + "subject_study_period_teacher sspt "
                + "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                + "join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id "
                + "join student_group sg on sspsg.student_group_id = sg.id");

        qb.requiredCriteria("sspt.id in (:teacherIds)", "teacherIds", subjectStudyPeriodTeacherIds);
        List<?> data = qb.select(
                "sspt.id as teacherId, sg.id, sg.code",
                em).getResultList();
        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0),  LinkedHashMap::new, Collectors.mapping(r -> {
                    return resultAsString(r, 2);
                }, Collectors.toList())));
    }

    private static void setCapacitySummaries(TeacherWithWorkLoadDto dto, List<LessonPlanXlsCapacityDto> capacities) {
        Long capacitiesSum = Long.valueOf(capacities.stream()
                .filter(p -> p.getHours() != null)
                .mapToInt(p -> p.getHours().intValue()).sum());
        Long contactCapacitiesSum = Long.valueOf(capacities.stream()
                .filter(p -> p.getHours() != null && Boolean.TRUE.equals(p.getIsContact()))
                .mapToInt(p -> p.getHours().intValue()).sum());
        BigDecimal sumWithLoad = capacities.stream().filter(p -> p.getHoursWithLoad() != null).map(p -> p.getHoursWithLoad()).reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setCapacitiesSum(capacitiesSum);
        dto.setContactCapacitiesSum(contactCapacitiesSum);
        dto.setSumWithLoad(sumWithLoad);
    }

    private Map<Long, List<LessonPlanXlsCapacityDto>> teacherCapacities(HoisUserDetails user,
            List<ClassifierDto> teacherCapacities, SubjectStudyPeriodSearchCommand criteria) {
        if (teacherCapacities == null|| teacherCapacities.isEmpty()) return new LinkedHashMap<>();
        
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from subject_study_period ssp "
                + "join study_period sp on sp.id = ssp.study_period_id "
                + "join study_year sy on sp.study_year_id = sy.id "
                + "join subject s on ssp.subject_id = s.id "
                + "join subject_study_period_teacher sspt on ssp.id = sspt.subject_study_period_id "
                + "join classifier c_type on c_type.code in (:capacityTypes) "
                + "left join school_capacity_type sct on sct.capacity_type_code = c_type.code and sct.school_id = :schoolId and sct.is_higher = true "
                + "left join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id and sctl.study_year_id = sp.study_year_id "
                        + "and coalesce(ssp.coefficient_code, 'KOEFITSIENT_K1') = sctl.coefficient_code "
                + "left join (select sspp.ssp_id, ssppc.is_contact, ssppc.capacity_type_code "
                    + "from (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp "
                    + "join subject_study_period_plan_capacity ssppc on ssppc.subject_study_period_plan_id = sspp.plan_id) Contact "
                        + "on Contact.ssp_id = ssp.id "
                        + "and Contact.capacity_type_code = c_type.code "
                + "left join (select ssptc.hours, sspc.capacity_type_code, ssptc.subject_study_period_teacher_id, sspc.subject_study_period_id "
                    + "from subject_study_period_capacity sspc "
                    + "join (" + SQL_SELECT_TEACHER_CAPACITY + ") ssptc on ssptc.subject_study_period_capacity_id = sspc.id) THours "
                        + "on THours.subject_study_period_teacher_id = sspt.id "
                        + "and THours.subject_study_period_id = ssp.id "
                        + "and THours.capacity_type_code = c_type.code "
                        + "and ssp.is_capacity_diff = true "
                + "left join (select sspc.hours, sspc.capacity_type_code, sspc.subject_study_period_id "
                    + "from subject_study_period_capacity sspc) CHours "
                        + "on CHours.subject_study_period_id = ssp.id "
                        + "and CHours.capacity_type_code = c_type.code "
                        + "and ssp.is_capacity_diff is not true");

        qb.requiredCriteria("sy.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());
        qb.optionalCriteria("exists(select 1 " +
                "from subject_study_period_student_group sspsg " +
                "join student_group sg on sspsg.student_group_id = sg.id " +
                "join curriculum_department cd on cd.curriculum_id = sg.curriculum_id " +
                "where cd.school_department_id = :departmentId " +
                "and sspsg.subject_study_period_id = ssp.id)",
                "departmentId", criteria.getDepartment());
        qb.optionalCriteria("ssp.subject_id = :subjectId", "subjectId", criteria.getSubject());
        if (user.isTeacher()) {
            qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        } else {
            qb.optionalCriteria("sspt.teacher_id in (:teacherId)", "teacherId", criteria.getTeacher());
        }
        qb.optionalCriteria("exists(select 1 from subject_study_period_student_group sspsg "
                + "where sspsg.subject_study_period_id = ssp.id "
                + "and sspsg.student_group_id in (:studentGroupIds))", "studentGroupIds", criteria.getStudentGroup());
        qb.optionalCriteria("sy.id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.parameter("capacityTypes", teacherCapacities.stream().map(p -> p.getCode()).collect(Collectors.toList()));
        qb.parameter("schoolId", user.getSchoolId());
        
        qb.groupBy( "sspt.id, Contact.is_contact, c_type.value, sctl.load_percentage");
        List<?> data = qb.select(
                "sspt.id as teacherId, "
                + "sum(coalesce(THours.hours, CHours.hours)) as hours, coalesce(Contact.is_contact, true), c_type.value as capacityType, "
                + "coalesce(sctl.load_percentage, 100) * coalesce(sum(coalesce(THours.hours, CHours.hours)), 0) / 100.0 as hoursWithLoad",
                em).getResultList();
        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0),  LinkedHashMap::new, Collectors.mapping(r -> {
                    LessonPlanXlsCapacityDto dto = new LessonPlanXlsCapacityDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setHours(resultAsLong(r, 1));
                    dto.setIsContact(resultAsBoolean(r, 2));
                    dto.setCapacityType(resultAsString(r, 3));
                    dto.setHoursWithLoad(resultAsDecimal(r, 4));
                    return dto;
                }, Collectors.toList())));
    }

    private List<ClassifierDto> getTeacherCapacities(Long schoolId, Set<Long> subjectStudyPeriodTeacherIds, SubjectStudyPeriodSearchCommand criteria) {
        String capacitiesQuery = "from school_capacity_type sct"
                + " join classifier c on sct.capacity_type_code = c.code "
                + " where sct.school_id = :schoolId and sct.is_usable = true and sct.is_timetable = true and sct.is_higher = true";

        if (!subjectStudyPeriodTeacherIds.isEmpty()) {
            capacitiesQuery += " union all"
                    + " select sspc.capacity_type_code from subject_study_period_capacity sspc"
                    + " join subject_study_period ssp on ssp.id = sspc.subject_study_period_id"
                    + " join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                    + " join study_period sp on sp.id = ssp.study_period_id "
                    + " where"
                    + (criteria.getStudyPeriod() != null ? " sp.id = :studyPeriodId and" : "")
                    + " sspt.id in (:subjectStudyPeriodTeacherIds)";
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(capacitiesQuery);
        if (!subjectStudyPeriodTeacherIds.isEmpty()) {
            if (criteria.getStudyPeriod() != null) {
                qb.parameter("studyPeriodId", criteria.getStudyPeriod());
            }
            qb.parameter("subjectStudyPeriodTeacherIds", subjectStudyPeriodTeacherIds);
        }
        qb.parameter("schoolId", schoolId);
        
        List<?> capacitiesData = qb.select("distinct c.code", em).getResultList();
        Set<String> capacityCodes = StreamUtil.toMappedSet(r -> resultAsString(r, 0), capacitiesData);
        List<Classifier> capacities = new ArrayList<>();
        if (!capacityCodes.isEmpty()) {
            capacities = em.createQuery("select distinct c from Classifier c where c.code in (:codes)", Classifier.class)
                    .setParameter("codes", capacityCodes).getResultList();
        }
        return StreamUtil.toMappedList(ClassifierDto::of, capacities);
    }

    public List<SubjectStudyPeriodExistingPlanDto> getExistingPlans(Long schoolId, StudyPeriod period, StudentGroup group) {
        if (group.getCurriculumVersion() == null) {
            return Collections.emptyList();
        }

        List<?> results = em.createNativeQuery("select sp.id as spid, sg.id as sgid, sp.end_date, " +
                "cl_sy.name_et || ' ' || sp.name_et || ' ' || sg.code || ' (' || coalesce(cv.code, '-') || ' ' || c.name_et || ')' as name_et, " +
                "coalesce(cl_sy.name_en, cl_sy.name_et) || ' ' || coalesce(sp.name_en, sp.name_et) || " +
                "' ' || sg.code || ' (' || coalesce(cv.code, '-') || ' ' || coalesce(c.name_en, c.name_et) || ')' as name_en " +
                "from subject_study_period_student_group sspsg " +
                "join subject_study_period ssp on sspsg.subject_study_period_id = ssp.id " +
                "join study_period sp on ssp.study_period_id = sp.id " +
                "join study_year sy on sp.study_year_id = sy.id " +
                "join classifier cl_sy on sy.year_code = cl_sy.code " +
                "join student_group sg on sspsg.student_group_id = sg.id " +
                "join curriculum c on sg.curriculum_id = c.id " +
                "left join curriculum_version cv on sg.curriculum_version_id = cv.id and c.id = cv.curriculum_id " +
                "where sg.school_id = :schoolId and c.id = :curriculumId and sp.end_date <= :before " +
                "group by cl_sy.code, sp.id, c.id, cv.id, sg.id")
                .setParameter("schoolId", schoolId)
                .setParameter("curriculumId", EntityUtil.getNullableId(group.getCurriculum()))
                .setParameter("before", JpaQueryUtil.parameterAsTimestamp(period.getStartDate()))
                .getResultList();
        return results.stream().map(r -> {
                    SubjectStudyPeriodExistingPlanDto dto = new SubjectStudyPeriodExistingPlanDto();
                    dto.setPeriodId(resultAsLong(r, 0));
                    dto.setGroupId(resultAsLong(r, 1));
                    dto.setEndDate(resultAsLocalDate(r, 2));
                    dto.setNameEt(resultAsString(r, 3));
                    dto.setNameEn(resultAsString(r, 4));
                    return dto;
                }).collect(Collectors.toList());
    }
}
