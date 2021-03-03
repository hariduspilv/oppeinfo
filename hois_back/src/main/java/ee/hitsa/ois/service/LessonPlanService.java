package ee.hitsa.ois.service;

import static ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodCapacitiesService.SQL_SELECT_TEACHER_CAPACITY;
import static ee.hitsa.ois.util.JpaQueryUtil.getOrDefault;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.timetable.JournalSub;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.StudyPeriodEventType;
import ee.hitsa.ois.util.JournalUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.StudyPeriodEventDto;
import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanCurriculumDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalCapacity;
import ee.hitsa.ois.domain.timetable.JournalCapacityType;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.JournalRoom;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.domain.timetable.LessonPlan;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.domain.timetable.TimetableObjectStudentGroup;
import ee.hitsa.ois.enums.GroupProportion;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.EntityRemoveException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.LessonPlanModuleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.LessonPlanUtil;
import ee.hitsa.ois.util.LessonPlanUtil.LessonPlanCapacityMapper;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UntisCodeUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.StudentGroupAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumVersionAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanCreateForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm.LessonPlanModuleForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm.LessonPlanModuleJournalForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanForm.LessonPlanModuleJournalTeacherForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm.LessonPlanGroupForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm.LessonPlanJournalTeacherForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanSearchTeacherCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeResult;
import ee.hitsa.ois.web.dto.lessonPlan.LessonPlanXlsCapacityDto;
import ee.hitsa.ois.web.dto.lessonPlan.LessonPlanXlsTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanByTeacherDto.LessonPlanByTeacherSubjectDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanByTeacherDto.LessonPlanByTeacherSubjectStudentGroupDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanCreatedJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanModuleDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanModuleJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanModuleJournalTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.LessonPlanTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanSearchTeacherDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanTeacherLoadDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanXlsDto.LessonPlanXlsJournalDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanXlsDto.LessonPlanXlsModuleDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanXlsDto.LessonPlanXlsStudyPeriodDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanXlsDto.LessonPlanXlsTotalsDto;

@Transactional
@Service
public class LessonPlanService {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private LessonPlanModuleRepository lessonPlanModuleRepository;
    @Autowired
    private XlsService xlsService;

    private static final String TEACHER_SSP_LOAD_FROM = "from subject_study_period_teacher sspt "
            + "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                + "and (ssp.is_capacity_diff is null or ssp.is_capacity_diff = false) "
            + "join study_period sp on sp.id = ssp.study_period_id "
            + "join subject_study_period_capacity sspc on sspc.subject_study_period_id = ssp.id "
            + "left join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP
                + ") sspp on ssp.id = sspp.ssp_id and sspp.priority != 999 "
            + "left join subject_study_period_plan_capacity ssppc on sspp.plan_id = ssppc.subject_study_period_plan_id "
                + "and sspc.capacity_type_code = ssppc.capacity_type_code";

    private static final String TEACHER_SPECIFIC_SSP_LOAD_FROM = "from (" + SQL_SELECT_TEACHER_CAPACITY + ") ssptc "
            + "join subject_study_period_teacher sspt on sspt.id = ssptc.subject_study_period_teacher_id "
            + "join subject_study_period_capacity sspc on sspc.id = ssptc.subject_study_period_capacity_id "
            + "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id and ssp.is_capacity_diff = true "
            + "join study_period sp on sp.id = ssp.study_period_id "
            + "left join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP
            + ") sspp on ssp.id = sspp.ssp_id and sspp.priority != 999 "
            + "left join subject_study_period_plan_capacity ssppc on sspp.plan_id = ssppc.subject_study_period_plan_id "
                + "and sspc.capacity_type_code = ssppc.capacity_type_code";

    public LessonPlanDto get(LessonPlan lessonPlan) {
        LessonPlanDto dto = LessonPlanDto.of(lessonPlan, scheduleLegends(lessonPlan));
        setLessonPlanModuleExtra(lessonPlan, dto);
        setLessonPlanCapacities(lessonPlan, dto);
        setStudyYearVacations(lessonPlan, dto);

        Set<Long> journalTeachers = lessonPlanJournalTeachers(dto.getId());
        dto.setTeachers(getLessonPlanTeachers(EntityUtil.getId(lessonPlan.getStudyYear()), journalTeachers));
        return dto;
    }

    private void setLessonPlanModuleExtra(LessonPlan lessonPlan, LessonPlanDto dto) {
        Map<Long, ? extends LessonPlanModuleForm> modulesById = StreamUtil.toMap(
                LessonPlanModuleForm::getOccupationModuleId, r -> r, dto.getModules());
        Set<Long> plannedModules = plannedModules(lessonPlan.getId(), modulesById.keySet());
        Map<Long, Integer> previousHoursById = modulePreviousHours(lessonPlan.getStudyYear(), modulesById.keySet());

        for (Long occupationModuleId : modulesById.keySet()) {
            LessonPlanModuleDto module = (LessonPlanModuleDto) modulesById.get(occupationModuleId);
            module.setIsPlanned(Boolean.valueOf(plannedModules.contains(occupationModuleId)));
            Integer previousHours = previousHoursById.get(occupationModuleId);
            module.setPreviousHours(previousHours != null ? previousHours : Integer.valueOf(0));
        }
    }

    private Set<Long> plannedModules(Long lessonPlanId, Set<Long> moduleIds) {
        // student group current year number matches atleast one module theme study year number
        List<?> data = em.createNativeQuery("select cvo.id from curriculum_version_omodule cvo "
                + "join curriculum_version_omodule_theme cvot on cvot.curriculum_version_omodule_id = cvo.id "
                + "where cvo.id in (:moduleIds) and cvot.study_year_number = "
                    + "(select extract(year from lp_sy.start_date) - extract(year from sg_sy.start_date) + 1 from lesson_plan lp "
                    + "join study_year lp_sy on lp_sy.id = lp.study_year_id "
                    + "join student_group sg on sg.id = lp.student_group_id "
                    + "join study_year sg_sy on sg_sy.id = get_study_year(sg.valid_from, sg.school_id) "
                    + "where lp.id = :lessonPlanId)")
                .setParameter("moduleIds", moduleIds)
                .setParameter("lessonPlanId", lessonPlanId)
                .getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }

    private Map<Long, Integer> modulePreviousHours(StudyYear studyYear, Set<Long> moduleIds) {
        List<?> data = em.createNativeQuery("select cvo.id, sum(jc.hours) from curriculum_version_omodule cvo "
                + "join lesson_plan_module lpm on lpm.curriculum_version_omodule_id = cvo.id "
                + "join (select distinct ot.lesson_plan_module_id, journal_id from journal_omodule_theme ot) jot on jot.lesson_plan_module_id = lpm.id "
                + "join journal_capacity jc on jc.journal_id = jot.journal_id "
                + "join study_period sp on sp.id = jc.study_period_id "
                + "join study_year sy on sy.id = sp.study_year_id "
                + "where cvo.id in (:moduleIds) and sy.end_date <= :lessonPlanSyStart "
                + "group by cvo.id")
                .setParameter("moduleIds", moduleIds)
                .setParameter("lessonPlanSyStart", JpaQueryUtil.parameterAsTimestamp(studyYear.getStartDate()))
                .getResultList();
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsInteger(r, 1), data);
    }

    private Set<Long> lessonPlanJournalTeachers(Long lessonPlanId) {
        List<?> teachers = em.createNativeQuery("select t.id from lesson_plan lp "
                    + "join lesson_plan_module lpm on lp.id = lpm.lesson_plan_id "
                    + "join curriculum_version_omodule cvo on lpm.curriculum_version_omodule_id = cvo.id "
                    + "join curriculum_version_omodule_theme cvot on cvo.id = cvot.curriculum_version_omodule_id "
                    + "join journal_omodule_theme jot on cvot.id = jot.curriculum_version_omodule_theme_id "
                    + "join journal_teacher jt on jot.journal_id = jt.journal_id "
                    + "join teacher t on jt.teacher_id = t.id " 
                    + "where lp.id = ?1")
                .setParameter(1, lessonPlanId)
                .getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), teachers);
    }

    public List<LessonPlanTeacherDto> getLessonPlanTeachers(Long studyYearId, Set<Long> teacherIds) {
        List<LessonPlanTeacherDto> teacherDtos = new ArrayList<>();

        if (!teacherIds.isEmpty()) {
            List<?> teacherScheduleLoads = em.createNativeQuery(
                    "select t.id, t.schedule_load, t.is_study_period_schedule_load from teacher t " +
                    "join journal_teacher jt on t.id = jt.teacher_id " +
                    "join journal j on jt.journal_id = j.id " +
                    "where t.id in (:teacherIds) and j.study_year_id = :studyYearId " +
                    "group by t.id")
                .setParameter("teacherIds", teacherIds)
                .setParameter("studyYearId", studyYearId)
                    .getResultList();

            Map<Long, List<LessonPlanTeacherLoadDto>> studyLoadsByTeacher = teacherStudyLoads(teacherIds, studyYearId);
            // in hybrid schools teachers can give both vocational and higher lessons
            Map<Long, List<LessonPlanTeacherLoadDto>> subjectStudyLoadsByTeacher = subjectStudyLoads(teacherIds, studyYearId);

            StudyYear studyYear = em.getReference(StudyYear.class, studyYearId);
            LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(studyYear);

            teacherDtos = StreamUtil.toMappedList(r -> {
                LessonPlanTeacherDto teacherDto = new LessonPlanTeacherDto();
                Long teacherId = resultAsLong(r, 0);
                teacherDto.setId(teacherId);
                teacherDto.setScheduleLoad(resultAsShort(r, 1));
                teacherDto.setIsStudyPeriodScheduleLoad(resultAsBoolean(r, 2));

                // vocational load
                List<LessonPlanTeacherLoadDto> teacherStudyLoads = studyLoadsByTeacher.get(teacherId);
                Map<String, List<Long>> teacherStudyLoadByCapacity = capacityMapper.mapTeacherLoadsByCapacities(teacherStudyLoads);
                Map<String, List<Long>> teacherContactStudyLoadByCapacity = capacityMapper.mapTeacherLoadsByCapacities(
                        StreamUtil.toFilteredList(l -> Boolean.TRUE.equals(l.getIsContact()), teacherStudyLoads));

                List<Long> teacherStudyLoad = new ArrayList<>();
                Map<String, Long> plannedLessonsByCapacity = new HashMap<>();
                if (teacherStudyLoadByCapacity != null) {
                    for (String capacity : teacherStudyLoadByCapacity.keySet()) {
                        plannedLessonsByCapacity.put(capacity, sumWeekLoads(teacherStudyLoadByCapacity.get(capacity)));

                        // week as well as planned lessons totals are calculated by only contact hours
                        List<Long> weekLoads = teacherContactStudyLoadByCapacity.get(capacity);
                        if (weekLoads == null) {
                            continue;
                        }
                        if (teacherStudyLoad.isEmpty()) {
                            teacherStudyLoad.addAll(weekLoads);
                        } else {
                            for (int i = 0; i < weekLoads.size(); i++) {
                                long sum = Long.sum(teacherStudyLoad.get(i) != null ? teacherStudyLoad.get(i).longValue() : 0,
                                        weekLoads.get(i) != null ? weekLoads.get(i).longValue() : 0);
                                teacherStudyLoad.set(i, sum > 0 ? Long.valueOf(sum) : null);
                            }
                        }
                    }
                }

                // higher load
                List<LessonPlanTeacherLoadDto> teacherSubjectStudyLoads = subjectStudyLoadsByTeacher.get(teacherId);
                Long subjectPlannedLessons = Long.valueOf(StreamUtil.nullSafeList(teacherSubjectStudyLoads).stream()
                        .filter(l -> Boolean.TRUE.equals(l.getIsContact()))
                        .mapToLong(LessonPlanTeacherLoadDto::getSum).sum());

                if (teacherSubjectStudyLoads != null) {
                    for (LessonPlanTeacherLoadDto dto : teacherSubjectStudyLoads) {
                        String capacity = dto.getCapacity();
                        plannedLessonsByCapacity.put(capacity, plannedLessonsByCapacity.containsKey(capacity)
                                ? Long.sum(plannedLessonsByCapacity.get(capacity), dto.getSum()) : dto.getSum());
                    }
                }

                Long plannedLessons = Long.sum(sumWeekLoads(teacherStudyLoad), subjectPlannedLessons);
                teacherDto.setStudyLoadByWeekAndCapacity(teacherStudyLoadByCapacity);
                teacherDto.setStudyLoadByWeek(teacherStudyLoad);
                teacherDto.setPlannedLessonsByCapacity(plannedLessonsByCapacity);
                teacherDto.setPlannedLessons(plannedLessons);
                teacherDto.setSubjectStudyLoads(teacherSubjectStudyLoads);
                return teacherDto;
            }, teacherScheduleLoads);
        }
        return teacherDtos;
    }

    private static Long sumWeekLoads(List<Long> loads) {
        return Long.valueOf(StreamUtil.nullSafeList(loads).stream().filter(Objects::nonNull).mapToLong(l -> l).sum());
    }

    private Map<Long, List<LessonPlanTeacherLoadDto>> teacherStudyLoads(Set<Long> teacherIds, Long studyYearId) {
        List<?> teacherStudyLoads = em.createNativeQuery(
                "select teacher_id, week_nr, study_period_id, capacity_type_code, is_contact, sum(hours) from (" +
                "select jt.teacher_id, week_nr, jc.study_period_id, jct.capacity_type_code, sct.is_contact, sum(jc.hours) as hours " +
                    "from journal_teacher jt " +
                    "join journal j on jt.journal_id = j.id " +
                    "join journal_capacity jc on jc.journal_id = j.id and (j.is_capacity_diff is null or j.is_capacity_diff = false) " +
                    "join journal_capacity_type jct on jc.journal_capacity_type_id = jct.id " +
                    "join school_capacity_type sct on sct.capacity_type_code = jct.capacity_type_code " +
                        "and sct.school_id = j.school_id and sct.is_higher = false " +
                "where jt.teacher_id in (:teacherIds) and j.study_year_id = :studyYearId " +
                    "group by teacher_id, week_nr, study_period_id, jct.capacity_type_code, sct.is_contact " +
                "union all " +
                "select jt2.teacher_id, jtc.week_nr, jtc.study_period_id, jct2.capacity_type_code, sct2.is_contact, sum(jtc.hours) as hours " +
                    "from journal_teacher jt2 " +
                    "join journal j2 on jt2.journal_id = j2.id " +
                    "join journal_teacher_capacity jtc on jt2.id = jtc.journal_teacher_id and j2.is_capacity_diff = true " +
                    "join journal_capacity_type jct2 on jtc.journal_capacity_type_id = jct2.id " +
                    "join school_capacity_type sct2 on sct2.capacity_type_code = jct2.capacity_type_code " +
                        "and sct2.school_id = j2.school_id and sct2.is_higher = false " +
                "where jt2.teacher_id in (:teacherIds) and j2.study_year_id = :studyYearId " +
                    "group by teacher_id, week_nr, study_period_id, jct2.capacity_type_code, sct2.is_contact) as hours " +
                "group by teacher_id, week_nr, study_period_id, capacity_type_code, is_contact")
            .setParameter("teacherIds", teacherIds)
            .setParameter("studyYearId", studyYearId)
            .getResultList();

        return StreamUtil.nullSafeList(teacherStudyLoads).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new LessonPlanTeacherLoadDto(resultAsShort(r, 1), resultAsLong(r, 2),
                        resultAsString(r, 3), resultAsBoolean(r, 4), resultAsLong(r, 5)),
                        Collectors.toList())));
    }

    private Map<Long, List<LessonPlanTeacherLoadDto>> subjectStudyLoads(Set<Long> teacherIds, Long studyYearId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(TEACHER_SSP_LOAD_FROM);
        qb.requiredCriteria("sspt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);
        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", studyYearId);

        String sspLoad = qb.querySql("sspt.teacher_id, ssp.study_period_id, sspc.capacity_type_code, "
                + "sspc.hours, coalesce(ssppc.is_contact, false) is_contact", false);

        qb = new JpaNativeQueryBuilder(TEACHER_SPECIFIC_SSP_LOAD_FROM);
        qb.requiredCriteria("sspt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);
        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", studyYearId);

        String specificSspLoad = qb.querySql("sspt.teacher_id, ssp.study_period_id, sspc.capacity_type_code, "
                + "ssptc.hours, coalesce(ssppc.is_contact, false) as contact", false);
        Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + sspLoad + " union all " + specificSspLoad + ") as loads");
        String groupBy = "teacher_id, study_period_id, capacity_type_code, is_contact";
        qb.groupBy(groupBy);

        List<?> data = qb.select(groupBy + ", sum(hours)", em, parameters).getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new LessonPlanTeacherLoadDto(null, resultAsLong(r, 1),
                                resultAsString(r, 2), resultAsBoolean(r, 3), resultAsLong(r, 4)),
                        Collectors.toList())));
    }

    private void setLessonPlanCapacities(LessonPlan lessonPlan, LessonPlanDto dto) {
        List<ClassifierDto> schoolCapacities = lessonPlanSchoolCapacityTypeDtos(
                EntityUtil.getId(lessonPlan.getSchool()), Boolean.FALSE);

        if (dto.getModules() != null) {
            List<LessonPlanModuleJournalForm> journals = dto.getModules().stream().filter(m -> m.getJournals() != null)
                    .map(LessonPlanModuleForm::getJournals).flatMap(Collection::stream).collect(Collectors.toList());

            Set<String> journalCapacities = journals.stream().filter(j -> j.getHours() != null).map(j -> j.getHours().keySet())
                    .flatMap(Collection::stream).collect(Collectors.toSet());
            addMissingJournalCapacities(schoolCapacities, journalCapacities);
        }

        schoolCapacities.sort(Comparator.comparing(ClassifierDto::getNameEt, String.CASE_INSENSITIVE_ORDER));
        dto.setLessonPlanCapacities(schoolCapacities);
        dto.setVocationalContactCapacities(vocationalContactCapacities(EntityUtil.getId(lessonPlan.getSchool()), schoolCapacities));
    }
    
    private void setTeacherLessonPlanCapacities(LessonPlanByTeacherDto dto, Long schoolId,
            List<Journal> journals, List<LessonPlanByTeacherSubjectDto> subjects) {
        List<ClassifierDto> schoolCapacities = lessonPlanSchoolCapacityTypeDtos(schoolId, null);
        
        if (journals != null) {
            Set<String> journalCapacities = new HashSet<>();
            journalCapacities.addAll(journals.stream().map(j -> j.getJournalCapacityTypes())
                    .flatMap(j -> j.stream()).map(jct -> jct.getCapacityType().getCode()).collect(Collectors.toSet()));
            addMissingJournalCapacities(schoolCapacities, journalCapacities);
        }
        
        if (subjects != null) {
            Set<String> subjectCapacities = new HashSet<>();
            subjectCapacities.addAll(subjects.stream().map(s -> s.getCapacityTotals()).flatMap(s -> s.values().stream())
                    .flatMap(s -> s.keySet().stream()).collect(Collectors.toSet()));
            addMissingJournalCapacities(schoolCapacities, subjectCapacities);
        }

        schoolCapacities.sort(Comparator.comparing(ClassifierDto::getNameEt, String.CASE_INSENSITIVE_ORDER));
        dto.setLessonPlanCapacities(schoolCapacities);
        dto.setVocationalContactCapacities(vocationalContactCapacities(schoolId, schoolCapacities));
    }
    
    private List<ClassifierDto> lessonPlanSchoolCapacityTypeDtos(Long schoolId, Boolean isHigher) {
        SchoolCapacityTypeCommand command = new SchoolCapacityTypeCommand();
        command.setIsHigher(isHigher);
        return autocompleteService.schoolCapacityTypeDtos(schoolId, command);
    }
    
    private void addMissingJournalCapacities(List<ClassifierDto> schoolCapacities, Set<String> journalCapacities) {
        Set<String> schoolCapacityCodes = StreamUtil.toMappedSet(ClassifierDto::getCode, schoolCapacities);
        List<String> missingCapacityCodes = StreamUtil.toFilteredList(jc -> !schoolCapacityCodes.contains(jc),
                journalCapacities);
        if (!missingCapacityCodes.isEmpty()) {
            List<Classifier> missingCapacities = em.createQuery("select c from Classifier c " +
                    "where c.code in (:capacityCodes)", Classifier.class)
                    .setParameter("capacityCodes", missingCapacityCodes)
                    .getResultList();
            schoolCapacities.addAll(StreamUtil.toMappedList(ClassifierDto::of, missingCapacities));
        }
    }

    private List<String> vocationalContactCapacities(Long schoolId, List<ClassifierDto> schoolCapacities) {
        Set<String> schoolCapacityCodes = StreamUtil.toMappedSet(ClassifierDto::getCode, schoolCapacities);
        if (schoolCapacityCodes.isEmpty()) {
            return new ArrayList<>();
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from school_capacity_type sct "
                + "join classifier c on sct.capacity_type_code = c.code");
        qb.requiredCriteria("sct.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("c.code in :codes", "codes", schoolCapacityCodes);
        qb.requiredCriteria("sct.is_contact = :isContact", "isContact", Boolean.TRUE);
        List<?> data = qb.select("c.code", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsString(r, 0), data);
    }

    private void setStudyYearVacations(LessonPlan lessonPlan, LessonPlanDto dto) {
        List<StudyPeriodEvent> vacations = em.createQuery("select spe from StudyPeriodEvent spe " +
                "where spe.studyYear.id = :studyYearId and spe.eventType.code = :eventTypeCode", StudyPeriodEvent.class)
                .setParameter("studyYearId", EntityUtil.getId(lessonPlan.getStudyYear()))
                .setParameter("eventTypeCode", StudyPeriodEventType.SYNDMUS_VAHA.name())
                .getResultList();
        dto.setVacations(StreamUtil.toMappedList(StudyPeriodEventDto::of, vacations));
    }

    public LessonPlan create(HoisUserDetails user, LessonPlanCreateForm form) {
        StudentGroup studentGroup = em.getReference(StudentGroup.class, form.getStudentGroup());
        UserUtil.assertSameSchool(user, studentGroup.getSchool());
        StudyYear studyYear = em.getReference(StudyYear.class, form.getStudyYear());
        UserUtil.assertSameSchool(user, studyYear.getSchool());

        LessonPlan lessonPlan = new LessonPlan();
        lessonPlan.setSchool(em.getReference(School.class, user.getSchoolId()));
        lessonPlan.setStudentGroup(studentGroup);
        lessonPlan.setStudyYear(studyYear);
        lessonPlan.setIsUsable(Boolean.FALSE);
        lessonPlan.setShowWeeks(Boolean.FALSE);
        CurriculumVersion curriculumVersion = studentGroup.getCurriculumVersion();
        lessonPlan.setCurriculumVersion(curriculumVersion);

        EntityUtil.save(lessonPlan, em);
        if (form.getPreviousLessonplan() != null) {
            copyPreviousLessonPlan(form, lessonPlan);
        }
        return lessonPlan;
    }

    private void copyPreviousLessonPlan(LessonPlanCreateForm form, LessonPlan lessonPlan) {
        LessonPlan previousLessonPlan = em.getReference(LessonPlan.class, form.getPreviousLessonplan());
        Map<Long, CurriculumVersionOccupationModule> moduleEquivalents = occupationalModuleEquivalents(lessonPlan,
                previousLessonPlan);

        for (LessonPlanModule lpm : previousLessonPlan.getLessonPlanModules()) {
            LessonPlanModule lessonPlanModuleCopy = new LessonPlanModule();
            lessonPlanModuleCopy.setLessonPlan(lessonPlan);
            
            CurriculumVersionOccupationModule module = moduleEquivalents.get(EntityUtil.getId(lpm.getCurriculumVersionOccupationModule()));
            if (module == null) {
                continue;
            }
            lessonPlanModuleCopy.setCurriculumVersionOccupationModule(module);
            
            if (lpm.getTeacher() != null && Boolean.TRUE.equals(lpm.getTeacher().getIsActive())) {
                lessonPlanModuleCopy.setTeacher(lpm.getTeacher());
            }
            lessonPlan.getLessonPlanModules().add(lessonPlanModuleCopy);
            EntityUtil.save(lessonPlanModuleCopy, em);
            
            Map<Long, CurriculumVersionOccupationModuleTheme> themeEquivalents = occupationalModuleThemeEquivalents(lpm, module);
            Set<Journal> journals = lessonPlanModuleJournals(lpm);
            Map<Long, JournalSub> journalSubEquivalents = new HashMap<>();

            for (Journal j : journals) {
                List<CurriculumVersionOccupationModuleTheme> occupationModuleThemes = StreamUtil.toMappedList(
                        JournalOccupationModuleTheme::getCurriculumVersionOccupationModuleTheme, j.getJournalOccupationModuleThemes());
                List<CurriculumVersionOccupationModuleTheme> journalThemeEquivalents = journalCopyOcupationModuleThemes(
                        lessonPlanModuleCopy, occupationModuleThemes, themeEquivalents);
                
                if (!journalThemeEquivalents.isEmpty()) {
                    JournalSub journalSubCopy = copyPreviousLessonPlanJournalJournalSub(j, journalSubEquivalents);
                    copyPreviousLessonPlanJournal(form, j, journalThemeEquivalents, lessonPlan, lessonPlanModuleCopy,
                            journalSubCopy);
                }
            }
        }
    }

    private Map<Long, CurriculumVersionOccupationModule> occupationalModuleEquivalents(LessonPlan lessonPlan,
            LessonPlan previousLessonPlan) {
        CurriculumVersion lpCurriculumVersion = lessonPlan.getCurriculumVersion();
        CurriculumVersion previousLpCurriculumVersion = previousLessonPlan.getCurriculumVersion();

        List<?> data;
        if (!EntityUtil.getId(lpCurriculumVersion.getCurriculum()).equals(EntityUtil.getId(previousLpCurriculumVersion.getCurriculum()))) {
            data = em.createNativeQuery("select distinct on (cvo_id) cvo_id, cvo2_id "
                    + "from (select first_value(cvo.id) over(partition by cvo2.id order by "
                        + "case when (replace(replace(cm2.name_et, ' ', ''), '-', '') "
                        + "ilike replace(replace(cm.name_et, ' ', ''), '-', '')) then 0 else 1 end) cvo_id, cvo2.id cvo2_id "
                    + "from curriculum_version_omodule cvo "
                    + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                    + "join curriculum_version_omodule cvo2 on cvo2.curriculum_version_id = ?2 "
                    + "join curriculum_module cm2 on cm2.id = cvo2.curriculum_module_id "
                        + "and (replace(replace(cm2.name_et, ' ', ''), '-', '') ilike '%' || replace(replace(cm.name_et, ' ', ''), '-', '') || '%' "
                        + "or replace(replace(cm.name_et, ' ', ''), '-', '') ilike '%' || replace(replace(cm2.name_et, ' ', ''), '-', '') || '%') "
                    + "where cvo.curriculum_version_id = ?1 order by cvo.id) x")
                .setParameter(1, EntityUtil.getId(previousLessonPlan.getCurriculumVersion()))
                .setParameter(2, EntityUtil.getId(lessonPlan.getCurriculumVersion()))
                .getResultList();
        } else if (EntityUtil.getId(lpCurriculumVersion).equals(EntityUtil.getId(previousLessonPlan))) {
            data = em.createNativeQuery("select cvo.id cvo_id, cvo.id cvo2_id from curriculum_version_omodule cvo "
                    + "where cvo.curriculum_version_id = ?1")
                .setParameter(1, EntityUtil.getId(previousLessonPlan.getCurriculumVersion()))
                .getResultList();
        } else {
            data = em.createNativeQuery("select cvo.id cvo_id, cvo2.id cvo2_id from curriculum_version_omodule cvo "
                    + "join curriculum_version_omodule cvo2 on cvo.curriculum_module_id = cvo2.curriculum_module_id "
                    + "where cvo.curriculum_version_id = ?1 and cvo2.curriculum_version_id = ?2")
                .setParameter(1, EntityUtil.getId(previousLessonPlan.getCurriculumVersion()))
                .setParameter(2, EntityUtil.getId(lessonPlan.getCurriculumVersion()))
                .getResultList();
        }

        Set<Long> equivalentModuleIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 1), data);
        if (!equivalentModuleIds.isEmpty()) {
            List<CurriculumVersionOccupationModule> equivalentModules = em.createQuery("select cvo "
                    + "from CurriculumVersionOccupationModule cvo "
                    + "where cvo.id in (?1)", CurriculumVersionOccupationModule.class)
                .setParameter(1, equivalentModuleIds)
                .getResultList();
            Map<Long, CurriculumVersionOccupationModule> equivalentModuleMap = equivalentModules.stream()
                    .collect(Collectors.toMap(CurriculumVersionOccupationModule::getId, r -> r, (o, n) -> o));
            return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> equivalentModuleMap.get(resultAsLong(r, 1)), data);
        }
        return new HashMap<>();
    }

    private Map<Long, CurriculumVersionOccupationModuleTheme> occupationalModuleThemeEquivalents(LessonPlanModule lessonPlanModule,
            CurriculumVersionOccupationModule copiedLessonPlanModuleOccupationModule) {
        List<?> data = em.createNativeQuery("select distinct on (theme_id) theme_id, equivalent_theme_id "
            + "from (select first_value(cvot.id) over (partition by cvot2.id order by "
                + "case when (replace(replace(cvot2.name_et, ' ', ''), '-', '') "
                + "ilike replace(replace(cvot.name_et, ' ', ''), '-', '')) then 0 else 1 end) theme_id, cvot2.id as equivalent_theme_id "
            + "from curriculum_version_omodule_theme cvot "
            + "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "
            + "join curriculum_version_omodule cvo2 on cvo2.id = ?1 "
            + "join curriculum_version_omodule_theme cvot2 on cvo2.id = cvot2.curriculum_version_omodule_id "
                + "and (replace(replace(cvot2.name_et, ' ', ''), '-', '') ilike '%' || replace(replace(cvot.name_et, ' ', ''), '-', '') || '%' "
                + "or replace(replace(cvot.name_et, ' ', ''), '-', '') ilike '%' || replace(replace(cvot2.name_et, ' ', ''), '-', '') || '%') "
            + "where cvot.curriculum_version_omodule_id = ?2 order by cvot.id) x")
                .setParameter(1, EntityUtil.getId(copiedLessonPlanModuleOccupationModule))
                .setParameter(2, EntityUtil.getId(lessonPlanModule.getCurriculumVersionOccupationModule()))
                .getResultList();
        
        List<Long> equivalentThemeIds = StreamUtil.toMappedList(r -> resultAsLong(r, 1), data);
        List<CurriculumVersionOccupationModuleTheme> equivalentThemes = new ArrayList<>();
        if (!equivalentThemeIds.isEmpty()) {
            equivalentThemes = em.createQuery("select cvomt from CurriculumVersionOccupationModuleTheme cvomt "
                    + "where cvomt.id in (?1)", CurriculumVersionOccupationModuleTheme.class)
                    .setParameter(1, equivalentThemeIds).getResultList();
        }
        Map<Long, CurriculumVersionOccupationModuleTheme> equivalentThemesMap = StreamUtil.toMap(t -> t.getId(), t -> t, equivalentThemes);
        
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> equivalentThemesMap.get(resultAsLong(r, 1)), data);
    }
    
    private List<CurriculumVersionOccupationModuleTheme> journalCopyOcupationModuleThemes(LessonPlanModule lessonPlanModuleCopy,
            List<CurriculumVersionOccupationModuleTheme> occupationModuleThemes,
            Map<Long, CurriculumVersionOccupationModuleTheme> journalThemeEquivalents) {
        List<CurriculumVersionOccupationModuleTheme> themes = new ArrayList<>();
        //if there are any equivalents then those are added to journal
        for (CurriculumVersionOccupationModuleTheme cvomt : occupationModuleThemes) {
            Long cvomtId = EntityUtil.getId(cvomt);
            if (journalThemeEquivalents.containsKey(cvomtId)) {
                themes.add(journalThemeEquivalents.get(cvomtId));
            }
        }
        
        //if there are no equivalents then all of curriculum version modules themes are added
        if (themes.isEmpty()) {
            themes = em.createQuery(
                    "select cvomt from CurriculumVersionOccupationModuleTheme cvomt where cvomt.module.id = ?1",
                        CurriculumVersionOccupationModuleTheme.class)
                    .setParameter(1, EntityUtil.getId(lessonPlanModuleCopy.getCurriculumVersionOccupationModule()))
                    .getResultList();
        }
        
        return themes;
    }
    
    private Set<Journal> lessonPlanModuleJournals(LessonPlanModule lessonPlanModule) {
        return new HashSet<>(em.createQuery("select jomt.journal from JournalOccupationModuleTheme jomt "
                + "where jomt.lessonPlanModule.id = ?1", Journal.class)
                .setParameter(1, EntityUtil.getId(lessonPlanModule))
                .getResultList());
    }

    private void copyPreviousLessonPlanJournal(LessonPlanCreateForm form, Journal journal,
            List<CurriculumVersionOccupationModuleTheme> journalThemeEquivalents, LessonPlan lessonPlan,
            LessonPlanModule lessonPlanModuleCopy, JournalSub journalSubCopy) {
        Journal journalCopy = new Journal();
        journalCopy.setSchool(lessonPlan.getSchool());
        journalCopy.setAssessment(journal.getAssessment());
        journalCopy.setNameEt(journal.getNameEt());
        journalCopy.setStudyYear(lessonPlan.getStudyYear());
        journalCopy.setGroupProportion(journal.getGroupProportion());
        journalCopy.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
        journalCopy.setAddModuleOutcomes(journal.getAddModuleOutcomes());
        journalCopy.setAddStudents(journal.getAddStudents());
        journalCopy.setIsIndividual(journal.getIsIndividual());
        journalCopy.setIsFree(journal.getIsFree());
        journalCopy.setUntisCode(journal.getUntisCode());
        journalCopy.setJournalSub(journalSubCopy);

        copyPreviousLessonPlanJournalCapacities(journal, journalCopy);
        copyPreviousLessonPlanJournalTeachers(journal, journalCopy);
        copyPreviousLessonPlanJournalRooms(journal, journalCopy);
        copyPreviousLessonPlanJournalModuleThemes(journalCopy, journalThemeEquivalents, lessonPlanModuleCopy);

        if (Boolean.TRUE.equals(form.getCopyLessons())) {
            copyPreviousLessonPlanJournalLessons(journal, journalCopy, lessonPlan);
        }
        EntityUtil.save(journalCopy, em);
    }

    private static void copyPreviousLessonPlanJournalRooms(Journal journal, Journal journalCopy) {
        for (JournalRoom jr : journal.getJournalRooms()) {
            JournalRoom journalRoomCopy = new JournalRoom();
            journalRoomCopy.setJournal(journalCopy);
            journalRoomCopy.setRoom(jr.getRoom());
            journalCopy.getJournalRooms().add(journalRoomCopy);
        }
    }

    private static void copyPreviousLessonPlanJournalTeachers(Journal journal, Journal journalCopy) {
        for (JournalTeacher jt : journal.getJournalTeachers()) {
            if (Boolean.TRUE.equals(jt.getTeacher().getIsActive())) {
                JournalTeacher journalTeacherCopy = new JournalTeacher();
                journalTeacherCopy.setJournal(journalCopy);
                journalTeacherCopy.setTeacher(jt.getTeacher());
                journalTeacherCopy.setIsConfirmer(jt.getIsConfirmer());
                journalTeacherCopy.setIsFiller(jt.getIsFiller());
                journalCopy.getJournalTeachers().add(journalTeacherCopy);
            }
        }
    }

    private static void copyPreviousLessonPlanJournalCapacities(Journal journal, Journal journalCopy) {
        for (JournalCapacityType jct : journal.getJournalCapacityTypes()) {
            JournalCapacityType journalCapacityTypeCopy = new JournalCapacityType();
            journalCapacityTypeCopy.setJournal(journalCopy);
            journalCapacityTypeCopy.setCapacityType(jct.getCapacityType());
            journalCopy.getJournalCapacityTypes().add(journalCapacityTypeCopy);
        }
    }

    private static void copyPreviousLessonPlanJournalLessons(Journal journal, Journal journalCopy, LessonPlan lessonPlan) {
        List<Short> lessonPlanWeekNrs = lessonPlan.getStudyYear().getStudyPeriods().stream()
                .flatMap(r -> r.getWeekNrs().stream()).collect(Collectors.toList());
        Map<StudyPeriod, List<Short>> weekNrsByStudyPeriod = lessonPlan.getStudyYear().getStudyPeriods()
                .stream().collect(Collectors.toMap(r -> r, r -> r.getWeekNrs()));
        Map<Classifier, JournalCapacityType> journalCopyCapacityTypes = StreamUtil.toMap(jct -> jct.getCapacityType(),
                journalCopy.getJournalCapacityTypes());

        for (JournalCapacity jc : journal.getJournalCapacities()) {
            if (lessonPlanWeekNrs.contains(jc.getWeekNr())) {
                StudyPeriod studyPeriod = weekNrsByStudyPeriod.entrySet().stream()
                        .filter(sp -> sp.getValue().stream().anyMatch(nr -> nr.equals(jc.getWeekNr())))
                        .map(sp -> sp.getKey()).findFirst().orElse(null);

                JournalCapacity journalCapacityCopy = new JournalCapacity();
                journalCapacityCopy.setJournal(journalCopy);
                journalCapacityCopy.setStudyPeriod(studyPeriod);
                journalCapacityCopy.setJournalCapacityType(journalCopyCapacityTypes
                        .get(jc.getJournalCapacityType().getCapacityType()));
                journalCapacityCopy.setWeekNr(jc.getWeekNr());
                journalCapacityCopy.setHours(jc.getHours());
                journalCopy.getJournalCapacities().add(journalCapacityCopy);
            }
        }
    }

    private static void copyPreviousLessonPlanJournalModuleThemes(Journal journalCopy,
            List<CurriculumVersionOccupationModuleTheme> journalThemeEquivalents,
            LessonPlanModule lessonPlanModuleCopy) {
        List<Long> addedThemes = new ArrayList<>();
        for (CurriculumVersionOccupationModuleTheme cvomt : journalThemeEquivalents) {
            // added themes are kept track of because there can be multiples of equivalent themes
            Long themeId = EntityUtil.getId(cvomt);
            if (!addedThemes.contains(themeId)) {
                JournalOccupationModuleTheme journalOModuleThemeCopy = new JournalOccupationModuleTheme();
                journalOModuleThemeCopy.setJournal(journalCopy);
                journalOModuleThemeCopy.setLessonPlanModule(lessonPlanModuleCopy);
                journalOModuleThemeCopy.setCurriculumVersionOccupationModuleTheme(cvomt);
                journalCopy.getJournalOccupationModuleThemes().add(journalOModuleThemeCopy);
                addedThemes.add(themeId); 
            }
        }
    }

    private JournalSub copyPreviousLessonPlanJournalJournalSub(Journal journal, Map<Long, JournalSub> journalSubEquivalents) {
        JournalSub journalSubCopy = null;

        Long journalSubId = EntityUtil.getNullableId(journal.getJournalSub());
        if (journalSubId != null) {
            journalSubCopy = journalSubEquivalents.get(journalSubId);
            if (journalSubCopy == null) {
                journalSubCopy = new JournalSub();
                journalSubCopy.setSubJournals(journal.getJournalSub().getSubJournals());
                EntityUtil.save(journalSubCopy, em);
                journalSubEquivalents.put(journalSubId, journalSubCopy);
            }
        }
        return journalSubCopy;
    }

    public LessonPlan save(LessonPlan lessonPlan, LessonPlanForm form) {
        EntityUtil.bindToEntity(form, lessonPlan, "coefficient");
        lessonPlan.setCoefficient(EntityUtil.getOptionalOne(form.getCoefficient(), em));

        Map<Long, LessonPlanModule> modules = StreamUtil.toMap(LessonPlanModule::getId,
                lessonPlan.getLessonPlanModules());
        List<? extends LessonPlanModuleForm> formModules = form.getModules();
        if (formModules != null) {
            LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(lessonPlan.getStudyYear());

            for (LessonPlanModuleForm formModule : formModules) {
                LessonPlanModule lpm = null;
                if (formModule.getId() == null) {
                    // if teacher responsible for teacher is added to a lesson
                    // plan module that has never had any journals, the module
                    // needs to be created
                    if (formModule.getTeacher() != null) {
                        lpm = new LessonPlanModule();
                        lpm.setLessonPlan(lessonPlan);
                        lpm.setCurriculumVersionOccupationModule(em.getReference(CurriculumVersionOccupationModule.class,
                                formModule.getOccupationModuleId()));
                        lessonPlan.getLessonPlanModules().add(lpm);
                    } else {
                        continue;
                    }
                } else {
                    lpm = modules.remove(formModule.getId());
                    if (lpm == null) {
                        throw new AssertionFailedException("Unknown lessonplan module");
                    }
                }
                EntityUtil.bindToEntity(formModule, lpm);
                lpm.setTeacher(EntityUtil.getOptionalOne(Teacher.class, formModule.getTeacher(), em));
                // store journal capacities
                List<? extends LessonPlanModuleJournalForm> formJournals = formModule.getJournals();
                if (formJournals != null) {
                    for (LessonPlanModuleJournalForm formJournal : formJournals) {
                        Journal journal = em.getReference(Journal.class, formJournal.getId());
                        // TODO better checks - is journal related to this
                        // module
                        assertSameSchool(journal, lessonPlan.getSchool());
                        capacityMapper.mapJournalInput(journal, formJournal.getHours());

                        saveJournalTeacherCapacities(journal, formJournal);
                        EntityUtil.save(journal, em);
                    }
                }
            }
        }
        AssertionFailedException.throwIf(!modules.isEmpty(), "Unhandled lessonplan module");
        return EntityUtil.save(lessonPlan, em);
    }

    public void saveJournalTeacherCapacities(Journal journal, LessonPlanModuleJournalForm form) {
        journal.setCapacityDiff(form.getCapacityDiff());

        if (Boolean.TRUE.equals(form.getCapacityDiff())) {
            LessonPlanCapacityMapper capacityMapper = LessonPlanUtil.capacityMapper(journal.getStudyYear());
            for (LessonPlanModuleJournalTeacherForm teacherForm : form.getTeachers()) {
                JournalTeacher journalTeacher = em.getReference(JournalTeacher.class, teacherForm.getId());
                capacityMapper.mapTeacherInput(journal, journalTeacher, teacherForm.getHours());
            }
        } else {
            // Remove previously saved teacher capacities
            List<JournalTeacher> teachers = journal.getJournalTeachers();
            for (JournalTeacher journalTeacher : teachers) {
                journalTeacher.getJournalTeacherCapacities().clear();
            }
        }
    }

    public boolean lessonPlanHasJournalsWithStudents(LessonPlan lessonPlan) {
        List<?> data = em.createNativeQuery("select j.id from journal j " +
                "join journal_student js on js.journal_id = j.id " +
                "join journal_omodule_theme jot on jot.journal_id = j.id " +
                "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
                "where lpm.lesson_plan_id = :lessonPlanId")
                .setParameter("lessonPlanId", lessonPlan.getId())
                .setMaxResults(1)
                .getResultList();
        return !data.isEmpty();
    }

    public void delete(HoisUserDetails user, LessonPlan lessonPlan) {
        if (lessonPlanHasJournalsWithEntries(lessonPlan)) {
            throw new ValidationFailedException("lessonplan.error.connectedJournalsHaveEntries");
        }
        Set<Journal> lessonPlanJournals = new HashSet<>(em.createQuery("select j from Journal j "
                + "join j.journalOccupationModuleThemes jot "
                + "where jot.lessonPlanModule.lessonPlan.id = :lessonPlanId", Journal.class)
                .setParameter("lessonPlanId", lessonPlan.getId())
                .getResultList());

        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(lessonPlan, em);

        // remove journals that don't have connected themes after deleting lesson plan
        for (Journal journal : lessonPlanJournals) {
            if (journal.getJournalOccupationModuleThemes().size() == 0) {
                deleteJournal(user, journal);
            }
        }
    }

    private boolean lessonPlanHasJournalsWithEntries(LessonPlan lessonPlan) {
        List<?> data = em.createNativeQuery("select j.id from journal j " +
                "join journal_entry je on je.journal_id = j.id " +
                "join journal_omodule_theme jot on jot.journal_id = j.id " +
                "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
                "where lpm.lesson_plan_id = :lessonPlanId")
                .setParameter("lessonPlanId", lessonPlan.getId())
                .getResultList();
        return !data.isEmpty();
    }

    public Page<LessonPlanSearchDto> search(HoisUserDetails user, LessonPlanSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from lesson_plan lp inner join student_group sg on lp.student_group_id = sg.id " +
                "inner join curriculum_version cv on lp.curriculum_version_id = cv.id").sort(pageable);

        qb.requiredCriteria("lp.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("cv.curriculum_id in (:userCurriculumIds)", "userCurriculumIds",
                    user.getCurriculumIds());
        }

        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("cv.school_department_id = :schoolDepartmentId", "schoolDepartmentId", criteria.getSchoolDepartment());
        qb.optionalCriteria("lp.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", criteria.getCurriculumVersion());
        qb.optionalCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroup());
        
        if (criteria.getTeacher() != null) {
            qb.requiredCriteria("lp.id in (select lp.id from lesson_plan lp "
                    + "join lesson_plan_module lpm on lp.id = lpm.lesson_plan_id "
                    + "join journal_omodule_theme jot on lpm.id = jot.lesson_plan_module_id "
                    + "join journal j on jot.journal_id = j.id "
                    + "join journal_teacher jt on j.id = jt.journal_id "
                    + "where jt.teacher_id in (:teacherIds))", "teacherIds", criteria.getTeacher());
        }
        
        String select = "lp.id, sg.code as student_group_code, cv.code, (select coalesce(sum(jc.hours * (1 / cast(c.value as numeric))), 0) "
                + "from journal_capacity jc "
                + "join journal j on jc.journal_id = j.id "
                + "join classifier c on j.group_proportion_code = c.code "
                + "where jc.journal_id in "
                + "(select j.id from journal j join journal_omodule_theme jot on j.id = jot.journal_id "
                + "join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id "
                + "join lesson_plan lp2 on lpm.lesson_plan_id = lp2.id "
                + "where lp.id = lp2.id)) as hours";

        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new LessonPlanSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsDecimal(r, 3));
        });
    }

    public Page<LessonPlanSearchTeacherDto> search(HoisUserDetails user, LessonPlanSearchTeacherCommand criteria, Pageable pageable) {
        String from = " from teacher t " +
                // journal capacity
                "  left join (  " +
                "    select  " +
                "      sum(jc.hours) as num,  " +
                "      sum(case when sct.is_contact then jc.hours end) as contact,  " +
                "      jt.teacher_id  " +
                "    from  " +
                "      journal_teacher jt  " +
                "    join journal j on  " +
                "      j.id = jt.journal_id  " +
                "    join journal_capacity jc on  " +
                "      j.id = jc.journal_id  " +
                "    join journal_capacity_type jct on jc.journal_capacity_type_id = jct.id  " +
                "    join classifier c on jct.capacity_type_code = c.code  " +
                "    join school_capacity_type sct on sct.school_id = :schoolId and c.code = sct.capacity_type_code and sct.is_higher = false " +
                "    where  " +
                "      (j.is_capacity_diff is null  " +
                "      or j.is_capacity_diff = false)  " +
                "      and j.study_year_id = :studyYear  " +
                (user.isTeacher() ?
                    " and j.id in (select jot.journal_id"
                    + " from journal_omodule_theme jot"
                    + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                    + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                    + " where lp.is_usable = true) "
                    : ""
                ) +
                "    group by jt.teacher_id) jc_hours on t.id = jc_hours.teacher_id  " +
                // journal teacher capacity
                "  left join (  " +
                "    select  " +
                "      sum(jtc.hours) as num,  " +
                "      sum(case when sct.is_contact then jtc.hours end) as contact,  " +
                "      jt2.teacher_id  " +
                "    from  " +
                "      journal_teacher jt2  " +
                "    join journal j2 on  " +
                "      j2.id = jt2.journal_id  " +
                "    join journal_teacher_capacity jtc on  " +
                "      jt2.id = jtc.journal_teacher_id  " +
                "    join journal_capacity_type jct on jtc.journal_capacity_type_id = jct.id  " +
                "    join classifier c on jct.capacity_type_code = c.code  " +
                "    join school_capacity_type sct on sct.school_id = :schoolId and c.code = sct.capacity_type_code and sct.is_higher = false " +
                "    where  " +
                "      j2.is_capacity_diff = true  " +
                "      and j2.study_year_id = :studyYear  " +
                (user.isTeacher() ?
                    " and j2.id in (select jot.journal_id"
                    + " from journal_omodule_theme jot"
                    + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                    + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                    + " where lp.is_usable = true) "
                    : ""
                ) +
                "    group by jt2.teacher_id) jtc_hours on t.id = jtc_hours.teacher_id  " +
                // subject capacity
                "  left join (  " +
                "    select  " +
                "      sum(sspc.hours) as num,  " +
                "      sum(case when ssppc.is_contact then sspc.hours end) as contact,  " +
                "      sspt.teacher_id  " +
                "    from  " +
                "      subject_study_period_teacher sspt  " +
                "    join subject_study_period ssp on  " +
                "      ssp.id = sspt.subject_study_period_id  " +
                "    join subject_study_period_capacity sspc on  " +
                "      sspc.subject_study_period_id = ssp.id  " +
                "    join study_period sp on  " +
                "      sp.id = ssp.study_period_id " +
                "   left join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp" +
                    " on ssp.id = sspp.ssp_id and sspp.priority != 999" +
                "   left join subject_study_period_plan_capacity ssppc" +
                    " on sspp.plan_id = ssppc.subject_study_period_plan_id and sspc.capacity_type_code = ssppc.capacity_type_code" +
                "    where  " +
                "      (ssp.is_capacity_diff is null  " +
                "      or ssp.is_capacity_diff = false)  " +
                "      and sp.study_year_id = :studyYear  " +
                "    group by sspt.teacher_id) ssp_hours on t.id = ssp_hours.teacher_id  " +
                // subject teacher capacity
                "  left join (  " +
                "    select  " +
                "      sum(ssptc.hours) as num,  " +
                "      sum(case when ssppc.is_contact then ssptc.hours end) as contact,  " +
                "      sspt2.teacher_id  " +
                "    from  " +
                "      subject_study_period_teacher sspt2  " +
                "    join (" + SQL_SELECT_TEACHER_CAPACITY + ") ssptc on  " +
                "      ssptc.subject_study_period_teacher_id = sspt2.id  " +
                "    join subject_study_period ssp2 on  " +
                "      ssp2.id = sspt2.subject_study_period_id  " +
                "    join study_period sp2 on  " +
                "      sp2.id = ssp2.study_period_id  " +
                "    join subject_study_period_capacity sspc on  " +
                "      ssptc.subject_study_period_capacity_id = sspc.id  " +
                "   left join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp" +
                " on ssp2.id = sspp.ssp_id and sspp.priority != 999" +
                "   left join subject_study_period_plan_capacity ssppc" +
                " on sspp.plan_id = ssppc.subject_study_period_plan_id and sspc.capacity_type_code = ssppc.capacity_type_code" +
                "    where  " +
                "      ssp2.is_capacity_diff = true  " +
                "      and sp2.study_year_id = :studyYear  " +
                "    group by sspt2.teacher_id) sspt_hours on t.id = sspt_hours.teacher_id  ";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        if (user.isTeacher()) {
            qb.requiredCriteria("t.id = :teacherId", "teacherId", user.getTeacherId());
        } else {
            qb.optionalCriteria("t.id in (:teacherId)", "teacherId", criteria.getTeacher());
        }
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.parameter("studyYear", criteria.getStudyYear());

        String hoursQuery = qb.querySql(
                " t.id, t.person_id, " +
                "coalesce(jc_hours.num, 0) as jc_hours, " +
                "coalesce(jc_hours.contact, 0) as jc_hours_contact, " +
                "coalesce(jtc_hours.num, 0) as jtc_hours, " +
                "coalesce(jtc_hours.contact, 0) as jtc_hours_contact, " +
                "coalesce(ssp_hours.num, 0) as ssp_hours, " +
                "coalesce(ssp_hours.contact, 0) as ssp_hours_contact, " +
                "coalesce(sspt_hours.num, 0) as sspt_hours, " +
                "coalesce(sspt_hours.contact, 0) as sspt_hours_contact ", false);
        Map<String, Object> queryParameters = new HashMap<>(qb.queryParameters());
        
        qb = new JpaNativeQueryBuilder("from (" + hoursQuery + ") hours"
                + " join person p on hours.person_id = p.id ").sort(pageable);
        qb.filter("(hours.jc_hours + hours.jtc_hours + hours.ssp_hours + hours.sspt_hours) > 0");

        return JpaQueryUtil.pagingResult(qb, "hours.id, p.firstname, p.lastname," +
                        " hours.jc_hours + hours.jtc_hours + hours.ssp_hours + hours.sspt_hours as total_hours," +
                        " hours.jc_hours_contact + hours.jtc_hours_contact + hours.ssp_hours_contact + hours.sspt_hours_contact as total_contact_hours",
                queryParameters, em, pageable).map(r -> new LessonPlanSearchTeacherDto(
                        resultAsLong(r, 0), PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)),
                        resultAsLong(r, 3), resultAsLong(r, 4), criteria.getStudyYear()));
    }

    public LessonPlanByTeacherDto getByTeacher(HoisUserDetails user, Teacher teacher, StudyYear studyYear) {
        Long studyYearId = EntityUtil.getId(studyYear);
        Long teacherId = EntityUtil.getId(teacher);

        List<Journal> journals = getTeacherJournals(user, teacherId, studyYearId);

        List<LessonPlanByTeacherSubjectDto> subjects = getTeacherSubjects(teacherId, studyYearId).values().stream()
                .sorted(Comparator.comparing(LessonPlanByTeacherSubjectDto::getNameEt, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        LessonPlanByTeacherDto dto = new LessonPlanByTeacherDto(studyYear, journals, subjects,
                getSubjectTotals(subjects), getSubjectContactTotals(subjects), teacher);
        setTeacherLessonPlanCapacities(dto, EntityUtil.getId(teacher.getSchool()), journals, subjects);

        return dto;
    }

    private List<Journal> getTeacherJournals(HoisUserDetails user, Long teacherId, Long studyYearId) {
        return journalRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("studyYear").get("id"), studyYearId));

            Subquery<Long> journalTeachersQuery = query.subquery(Long.class);
            Root<Journal> journalRoot = journalTeachersQuery.from(Journal.class);
            Join<Object, Object> journalTeachersJoin = journalRoot.join("journalTeachers");
            journalTeachersQuery.select(journalRoot.get("id")).where(
                cb.and(
                    cb.equal(journalRoot.get("id"), root.get("id")),
                    cb.equal(journalTeachersJoin.get("teacher").get("id"), teacherId))
                );
            filters.add(cb.exists(journalTeachersQuery));

            if (user.isTeacher()) {
                Subquery<Long> usableLessonPlanQuery = query.subquery(Long.class);
                journalRoot = usableLessonPlanQuery.from(Journal.class);
                Join<Object, Object> lessonPlanJoin = journalRoot.join("journalOccupationModuleThemes")
                        .join("lessonPlanModule").join("lessonPlan");
                usableLessonPlanQuery.select(journalRoot.get("id")).where(
                    cb.and(
                        cb.equal(journalRoot.get("id"), root.get("id")),
                        cb.equal(lessonPlanJoin.get("isUsable"), Boolean.TRUE))
                    );
                filters.add(cb.exists(usableLessonPlanQuery));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
    }

    public Map<Long, LessonPlanByTeacherSubjectDto> getTeacherSubjects(Long teacherId, Long studyYearId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp "+
                "inner join study_period sp on ssp.study_period_id = sp.id " +
                "inner join subject_study_period_teacher sspt on ssp.id = sspt.subject_study_period_id " +
                "inner join subject s on ssp.subject_id = s.id " +
                "left join (select sspsg.subject_study_period_id, sspsg.student_group_id, sg.code from subject_study_period_student_group sspsg " +
                    "join student_group sg on sspsg.student_group_id = sg.id) sg on ssp.id = sg.subject_study_period_id");

        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", studyYearId);
        qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", teacherId);
        
        qb.sort("s.name_et");

        List<?> teacherSubjects = qb.select("distinct s.id, s.name_et, s.name_en, sg.student_group_id, sg.code, ssp.id as ssp_id," +
                " (select count(*) from subject_study_period_subgroup subgroup where subgroup.subject_study_period_id = ssp.id) as subgroups", em).getResultList();

        Query capacityQuery = em.createNativeQuery("select ssp.study_period_id, sspc.capacity_type_code, sspc.hours, coalesce(ssppc.is_contact, false) as contact"
                + " " + TEACHER_SSP_LOAD_FROM
                + " where sspc.subject_study_period_id = ?1 and sspt.teacher_id = ?2"
                + " union all"
                + " select ssp.study_period_id, sspc.capacity_type_code, ssptc.hours, coalesce(ssppc.is_contact, false) as contact"
                + " " + TEACHER_SPECIFIC_SSP_LOAD_FROM
                + " where sspc.subject_study_period_id = ?1 and sspt.teacher_id = ?2");

        Map<Long, LessonPlanByTeacherSubjectDto> subjects = new HashMap<>();
        Map<Long, Long> subjectStudyPeriodToSubject = new HashMap<>();
        Map<Long, Integer> subjectStudyPeriodToSubgroups = new HashMap<>();
        Map<Long, List<String>> studentGroups = new HashMap<>();
        Map<Long, Map<Long, Map<String, Long>>> studentGroupHours = new HashMap<>();
        Map<Long, Map<Long, Map<String, Long>>> studentGroupContactHours = new HashMap<>();
        for(Object r : teacherSubjects) {
            Long subjectId = resultAsLong(r, 0);
            LessonPlanByTeacherSubjectDto subject = subjects.computeIfAbsent(subjectId,
                    k -> new LessonPlanByTeacherSubjectDto(subjectId, resultAsString(r, 1), resultAsString(r, 2)));
            String studentGroupCode = resultAsString(r, 4);
            Long subjectStudyPeriodId = resultAsLong(r, 5);
            if (studentGroupCode != null) {
                subjectStudyPeriodToSubgroups.put(subjectStudyPeriodId, getOrDefault(resultAsInteger(r, 6), 0));
                subjectStudyPeriodToSubject.put(subjectStudyPeriodId, subjectId);
                studentGroups.computeIfAbsent(subjectStudyPeriodId, k -> new ArrayList<>()).add(studentGroupCode);
            }
            Map<Long, Map<String, Long>> periodCapacityHours = new HashMap<>();
            Map<Long, Map<String, Long>> periodContactCapacityHours = new HashMap<>();
            List<?> capacities = capacityQuery
                    .setParameter(1, subjectStudyPeriodId)
                    .setParameter(2, teacherId)
                    .getResultList();
            for (Object cr : capacities) {
                Long studyPeriodId = resultAsLong(cr, 0);
                String capacityTypeCode = resultAsString(cr, 1);
                Long hours = resultAsLong(cr, 2);
                periodCapacityHours.computeIfAbsent(studyPeriodId, k -> new HashMap<>())
                    .put(capacityTypeCode, hours);
                if (Boolean.TRUE.equals(resultAsBoolean(cr, 3))) {
                    periodContactCapacityHours.computeIfAbsent(studyPeriodId, k -> new HashMap<>())
                            .put(capacityTypeCode, hours);
                }
            }
            if (studentGroupCode != null) {
                studentGroupHours.put(subjectStudyPeriodId, periodCapacityHours);
                studentGroupContactHours.put(subjectStudyPeriodId, periodContactCapacityHours);
            } else {
                subject.setHours(periodCapacityHours);
                subject.setContactHours(periodContactCapacityHours);
            }
        }
        for (Entry<Long, Long> entry : subjectStudyPeriodToSubject.entrySet()) {
            subjects.get(entry.getValue()).getStudentGroups().add(new LessonPlanByTeacherSubjectStudentGroupDto(
                    studentGroups.get(entry.getKey()),
                    studentGroupHours.get(entry.getKey()),
                    studentGroupContactHours.get(entry.getKey()),
                    subjectStudyPeriodToSubgroups.get(entry.getKey())));
        }
        for (LessonPlanByTeacherSubjectDto subject : subjects.values()) {
            Map<Long, Map<String, Long>> totals = subject.getCapacityTotals();
            Map<Long, Map<String, Long>> contactTotals = subject.getContactCapacityTotals();
            addSubjectHours(subject.getHours(), totals);
            addSubjectHours(subject.getContactHours(), contactTotals);
            for (LessonPlanByTeacherSubjectStudentGroupDto studentGroup : subject.getStudentGroups()) {
                addSubjectHours(studentGroup.getHours(), totals);
                addSubjectHours(studentGroup.getContactHours(), contactTotals);
            }
        }
        return subjects;
    }

    private static void addSubjectHours(Map<Long, Map<String, Long>> hours, Map<Long, Map<String, Long>> totals) {
        if (hours == null) {
            return;
        }
        for (Entry<Long, Map<String, Long>> periodEntry : hours.entrySet()) {
            Map<String, Long> periodTotals = totals.computeIfAbsent(periodEntry.getKey(), k -> new HashMap<>());
            for (Entry<String, Long> entry : periodEntry.getValue().entrySet()) {
                periodTotals.put(entry.getKey(), Long.valueOf(periodTotals.computeIfAbsent(entry.getKey(), k -> Long.valueOf(0))
                        .longValue() + entry.getValue().longValue()));
            }
        }
    }
    
    public static Map<Long, Map<String, Long>> getSubjectTotals(List<LessonPlanByTeacherSubjectDto> subjects) {
        Map<Long, Map<String, Long>> result = new HashMap<>();
        for (LessonPlanByTeacherSubjectDto subject : subjects) {
            addSubjectHours(subject.getCapacityTotals(), result);
        }
        return result;
    }

    public static Map<Long, Map<String, Long>> getSubjectContactTotals(List<LessonPlanByTeacherSubjectDto> subjects) {
        Map<Long, Map<String, Long>> result = new HashMap<>();
        for (LessonPlanByTeacherSubjectDto subject : subjects) {
            addSubjectHours(subject.getContactCapacityTotals(), result);
        }
        return result;
    }

    public Map<String, ?> searchFormData(HoisUserDetails user) {
        Map<String, Object> data = new HashMap<>();
        Long schoolId = user.getSchoolId();
        data.put("studyYears", autocompleteService.studyYears(schoolId));
        StudentGroupAutocompleteCommand studentGroupLookup = new StudentGroupAutocompleteCommand();
        studentGroupLookup.setHigher(Boolean.FALSE);
        data.put("studentGroups", autocompleteService.studentGroups(schoolId, studentGroupLookup, false));
        data.put("studentGroupMapping", studentgroupsWithLessonPlans(schoolId));
        data.put("curriculumLessonPlans", curriculumLessonPlans(schoolId));
        CurriculumVersionAutocompleteCommand curriculumVersionLookup = new CurriculumVersionAutocompleteCommand();
        curriculumVersionLookup.setHigher(Boolean.FALSE);
        curriculumVersionLookup.setValid(Boolean.TRUE);
        if (user.isLeadingTeacher()) {
            curriculumVersionLookup.setUserId(user.getUserId());
        }
        data.put("curriculumVersions", autocompleteService.curriculumVersions(schoolId, curriculumVersionLookup));
        return data;
    }

    /**
     * New record for insertion with default values filled
     *
     * @param user
     * @param lessonPlanId
     * @param occupationModuleId
     * @param lessonPlanModuleId
     * @return
     */
    public LessonPlanJournalDto newJournal(HoisUserDetails user, Long lessonPlanId,
            Long occupationModuleId, Long lessonPlanModuleId) {
        LessonPlanModule lessonPlanModule = EntityUtil.getOptionalOne(LessonPlanModule.class, lessonPlanModuleId, em);
        CurriculumVersionOccupationModule occupationModule;
        LessonPlan lessonPlan;
        if (lessonPlanModule == null) {
            occupationModule = em.getReference(CurriculumVersionOccupationModule.class, occupationModuleId);
            lessonPlan = em.getReference(LessonPlan.class, lessonPlanId);
        } else {
            occupationModule = lessonPlanModule.getCurriculumVersionOccupationModule();
            lessonPlan = lessonPlanModule.getLessonPlan();
        }
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, lessonPlan.getStudentGroup());

        Journal journal = new Journal();
        // default values filled
        Set<CurriculumVersionOccupationModuleTheme> themes = occupationModule.getThemes();
        if(themes.size() == 1) {
            CurriculumVersionOccupationModuleTheme theme = themes.iterator().next();
            journal.setAssessment(theme.getAssessment());
            journal.setNameEt(theme.getNameEt());
        }

        LessonPlanJournalDto dto = lessonPlanModule == null ?
                LessonPlanJournalDto.of(journal, lessonPlan, occupationModule) :
                LessonPlanJournalDto.of(journal, lessonPlanModule);
        dto.setGroupProportion(GroupProportion.PAEVIK_GRUPI_JAOTUS_1.name());  // by default 1/1
        
        Map<Long, CurriculumVersionOccupationModuleThemeResult> dtoThemes = StreamUtil.toMap(t -> t.getId(), t -> t,
                dto.getThemes());
        autocompleteService.setThemesInOtherJournals(dtoThemes, dto.getStudentGroupId(), dto.getId(),
                dto.getJournalSubId());
        return dto;
    }

    public LessonPlanJournalDto getJournal(Journal journal, Long lessonPlanModuleId) {
        LessonPlanModule lessonPlanModule = em.getReference(LessonPlanModule.class, lessonPlanModuleId);
        LessonPlanJournalDto dto = LessonPlanJournalDto.of(journal, lessonPlanModule);
        
        Map<Long, CurriculumVersionOccupationModuleThemeResult> themes = StreamUtil.toMap(t -> t.getId(), t -> t,
                dto.getThemes());
        autocompleteService.setThemesInOtherJournals(themes,
                EntityUtil.getId(lessonPlanModule.getLessonPlan().getStudentGroup()),
                EntityUtil.getId(journal), EntityUtil.getNullableId(journal.getJournalSub()));
        return dto;
    }

    public LessonPlanCreatedJournalDto createJournal(HoisUserDetails user, LessonPlanJournalForm form) {
        LessonPlan lessonPlan = em.getReference(LessonPlan.class, form.getLessonPlan());
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, lessonPlan.getStudentGroup());
        LessonPlanModule lessonPlanModule;
        if (form.getLessonPlanModuleId() == null) {
            lessonPlanModule = new LessonPlanModule();
            lessonPlanModule.setLessonPlan(lessonPlan);
            lessonPlanModule.setCurriculumVersionOccupationModule(
                    em.getReference(CurriculumVersionOccupationModule.class, form.getOccupationModuleId()));
            lessonPlan.getLessonPlanModules().add(lessonPlanModule);
        } else {
            lessonPlanModule = em.getReference(LessonPlanModule.class, form.getLessonPlanModuleId());
        }

        Journal journal = new Journal();
        if (Boolean.TRUE.equals(form.getDivideIntoGroups())) {
            JournalSub journalSub = createJournalSub(form);
            String journalName = form.getNameEt();
            String groupEt = TranslateUtil.translate("lessonplan.group", Language.ET);
            for (long i = journalSub.getSubJournals().longValue(); i > 0; i--) {
                form.setNameEt(journalName + " " + groupEt + " " + i);
                journal = createJournal(user, form, lessonPlan, lessonPlanModule, journalSub);
            }
        } else {
            journal = createJournal(user, form, lessonPlan, lessonPlanModule, null);
        }
        return new LessonPlanCreatedJournalDto(EntityUtil.getId(journal), EntityUtil.getId(lessonPlanModule));
    }

    private JournalSub createJournalSub(LessonPlanJournalForm form) {
        JournalSub journalSub = new JournalSub();
        Classifier cl = em.getReference(Classifier.class, form.getGroupProportion());
        journalSub.setSubJournals(Long.valueOf(cl.getValue()));
        return EntityUtil.save(journalSub, em);
    }

    private Journal createJournal(HoisUserDetails user, LessonPlanJournalForm form, LessonPlan lessonPlan,
            LessonPlanModule lessonPlanModule, JournalSub journalSub) {
        Journal journal = new Journal();
        journal.setStudyYear(lessonPlan.getStudyYear());
        journal.setSchool(lessonPlan.getSchool());
        journal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
        if (journalSub != null) {
            journal.setJournalSub(journalSub);
            journalSub.getJournals().add(journal);
        }
        return saveJournal(journal, form, user, lessonPlanModule, true);
    }

    private void saveJournalSpecifics(Journal journal, LessonPlanJournalForm form) {
        EntityUtil.bindToEntity(form, journal, classifierRepository, "journalCapacityTypes", "journalTeachers",
                "journalOccupationModuleThemes", "groups", "journalRooms");

        List<JournalCapacityType> capacityTypes = journal.getJournalCapacityTypes();
        if (capacityTypes != null) {
            // try to delete capacity types first to catch foreign reference errors
            List<String> formJournalCapacityTypes = form.getJournalCapacityTypes();
            capacityTypes.removeIf(type -> !formJournalCapacityTypes.contains(EntityUtil.getCode(type.getCapacityType())));
            try {
                em.flush();
            } catch (PersistenceException e) {
                Throwable cause = e.getCause();
                if (cause instanceof ConstraintViolationException) {
                    throw new EntityRemoveException("lessonplan.journal.capacityTypeReferenced", cause);
                }
                throw e;
            }
        } else {
            journal.setJournalCapacityTypes(capacityTypes = new ArrayList<>());
        }

        EntityUtil.bindEntityCollection(capacityTypes, c -> EntityUtil.getCode(c.getCapacityType()), form.getJournalCapacityTypes(), ct -> {
            JournalCapacityType jct = new JournalCapacityType();
            jct.setJournal(journal);
            jct.setCapacityType(EntityUtil.validateClassifier(em.getReference(Classifier.class, ct), MainClassCode.MAHT));
            return jct;
        });

        List<JournalRoom> journalRooms = journal.getJournalRooms();
        if (journalRooms == null) {
            journal.setJournalRooms(journalRooms = new ArrayList<>());
        }
        EntityUtil.bindEntityCollection(journalRooms, r -> EntityUtil.getId(r.getRoom()), form.getJournalRooms(), jr -> jr.getId(), jrf -> {
            JournalRoom jr = new JournalRoom();
            jr.setJournal(journal);
            jr.setRoom(em.getReference(Room.class, jrf.getId()));
            return jr;
        });

        List<JournalTeacher> teachers = journal.getJournalTeachers();
        if (teachers == null) {
            journal.setJournalTeachers(teachers = new ArrayList<>());
        }
        EntityUtil.bindEntityCollection(teachers, EntityUtil::getId, form.getJournalTeachers(), jt -> jt.getId(), jtf -> {
            JournalTeacher jt = EntityUtil.bindToEntity(jtf, new JournalTeacher());
            jt.setJournal(journal);
            jt.setTeacher(EntityUtil.getOptionalOne(Teacher.class, jtf.getTeacher(), em));
            assertSameSchool(journal, jt.getTeacher().getSchool());
            return jt;
        }, (jtf, jt) -> {
            EntityUtil.bindToEntity(jtf, jt);
        });
    }

    private Journal saveJournal(Journal journal, LessonPlanJournalForm form, HoisUserDetails user,
            LessonPlanModule lessonPlanModule, boolean saveJournalSpecifics) {
        assertSameSchool(journal, lessonPlanModule.getLessonPlan().getSchool());
        EntityUtil.setUsername(user.getUsername(), em);

        List<JournalOccupationModuleTheme> oldThemes = journal.getJournalOccupationModuleThemes();
        List<JournalOccupationModuleThemeHolder> fromForm = StreamUtil.toMappedList(id -> new JournalOccupationModuleThemeHolder(journal, lessonPlanModule, id), form.getJournalOccupationModuleThemes());

        if(form.getGroups() != null && !form.getGroups().isEmpty()) {
            Set<Long> groupIds = StreamUtil.toMappedSet(LessonPlanGroupForm::getStudentGroup, form.getGroups());
            Map<Long, Long> lessonPlanIds = findLessonPlanByStudyYearAndStudentGroup(journal.getStudyYear(), groupIds);
            createMissingPlansAndAdd(groupIds, lessonPlanIds, EntityUtil.getId(journal.getStudyYear()), user);

            List<LessonPlanModule> lessonPlanModules = lessonPlanModuleRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                filters.add(root.get("lessonPlan").get("id").in(lessonPlanIds.values()));
                filters.add(root.get("curriculumVersionOccupationModule").get("id").in(StreamUtil.toMappedList(LessonPlanGroupForm::getCurriculumVersionOccupationModule, form.getGroups())));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            });
            for(LessonPlanGroupForm lpg : form.getGroups()) {
                Long lessonPlanId = lessonPlanIds.get(lpg.getStudentGroup());
                LessonPlan lp;
                if(lessonPlanId == null) {
                    LessonPlanCreateForm lpCreateForm = new LessonPlanCreateForm();
                    lpCreateForm.setStudentGroup(lpg.getStudentGroup());
                    lpCreateForm.setStudyYear(EntityUtil.getId(journal.getStudyYear()));
                    lp = create(user, lpCreateForm);
                } else {
                    lp = em.getReference(LessonPlan.class, lessonPlanId);
                }
                LessonPlanModule lpm = getLessonPlanModule(lessonPlanModules, EntityUtil.getId(lp), lpg.getCurriculumVersionOccupationModule());
                if(lpm == null) {
                    lpm = new LessonPlanModule();
                    lpm.setLessonPlan(lp);
                    CurriculumVersionOccupationModule module = em.getReference(CurriculumVersionOccupationModule.class, lpg.getCurriculumVersionOccupationModule());
                    lpm.setCurriculumVersionOccupationModule(module);
                    lp.getLessonPlanModules().add(lpm);
                    EntityUtil.save(lpm, em);
                }
                final LessonPlanModule lessonPlanModuleForSave = lpm;
                fromForm.addAll(StreamUtil.toMappedList(cvomt -> new JournalOccupationModuleThemeHolder(journal, lessonPlanModuleForSave, cvomt), lpg.getCurriculumVersionOccupationModuleThemes()));
            }
        }

        setJournalOccupationModuleThemes(journal, oldThemes, fromForm);


        List<CurriculumVersionOccupationModuleTheme> cvomThemes = JournalUtil.journalCurriculumModuleThemes(journal);
        boolean themeWithNoAssessment = cvomThemes.stream().anyMatch(t -> t.getAssessment() == null);
        boolean outcomeBasedAssessment = JournalUtil.allThemesAssessedByOutcomes(cvomThemes);
        if (!themeWithNoAssessment && !outcomeBasedAssessment && form.getAssessment() == null) {
            throw new ValidationFailedException("lessonplan.journal.assessmentRequired");
        }

        setTimetableObjectStudentGroups(journal, form, lessonPlanModule);

        if (saveJournalSpecifics) {
            saveJournalSpecifics(journal, form);

            if (journal.getId() == null) {
                em.persist(journal);
            }
            setUniqueUntisCode(journal, form);
        }
        return EntityUtil.save(journal, em);
    }

    public Journal saveJournal(Journal journal, LessonPlanJournalForm form, HoisUserDetails user) {
        LessonPlanModule lessonPlanModule = em.getReference(LessonPlanModule.class, form.getLessonPlanModuleId());

        if (journal.getJournalSub() != null) {
            List<Journal> journalSubJournals = journal.getJournalSub().getJournals();
            for (Journal subJournal : journalSubJournals) {
                if (subJournal.getId().equals(journal.getId())) {
                    continue;
                }
                saveJournal(subJournal, form, user, lessonPlanModule, false);
            }
        } else if (Boolean.TRUE.equals(form.getDivideIntoGroups())) {
            JournalSub journalSub = createJournalSub(form);
            String journalName = form.getNameEt();
            String groupEt = TranslateUtil.translate("lessonplan.group", Language.ET);

            // hack: remove journal_teacher_id from form to add teachers for new journals
            List<LessonPlanJournalTeacherForm> copiedTeachers = form.getJournalTeachers();
            List<LessonPlanJournalTeacherForm> newJournalTeachers = new ArrayList<>();
            for (LessonPlanJournalTeacherForm teacherForm : form.getJournalTeachers()) {
                LessonPlanJournalTeacherForm newJournalTeacher = new LessonPlanJournalTeacherForm();
                newJournalTeacher.setTeacher(teacherForm.getTeacher());
                newJournalTeacher.setIsFiller(teacherForm.getIsFiller());
                newJournalTeacher.setIsConfirmer(teacherForm.getIsConfirmer());
                newJournalTeachers.add(newJournalTeacher);
            }
            form.setJournalTeachers(newJournalTeachers);

            for (long i = journalSub.getSubJournals().longValue(); i > 1; i--) {
                form.setNameEt(journalName + " " + groupEt + " " + i);
                createJournal(user, form, lessonPlanModule.getLessonPlan(), lessonPlanModule, journalSub);
            }

            form.setNameEt(journalName + " " + groupEt + " " + 1);
            form.setJournalTeachers(copiedTeachers);
            journal.setJournalSub(journalSub);
        }
        return saveJournal(journal, form, user, lessonPlanModule, true);
    }
    
    private void setJournalOccupationModuleThemes(Journal journal, List<JournalOccupationModuleTheme> oldThemes, List<JournalOccupationModuleThemeHolder> fromForm) {
        if (!fromForm.isEmpty()) {
            List<JournalOccupationModuleTheme> savedFormThemes = new ArrayList<>();
            
            for (JournalOccupationModuleThemeHolder jmth : fromForm) {
                JournalOccupationModuleTheme oldTheme = oldThemes.stream()
                        .filter(o -> EntityUtil.getId(o.getCurriculumVersionOccupationModuleTheme()).equals(jmth.getCvomt()) 
                                && o.getLessonPlanModule().equals(jmth.getLessonPlanModule()))
                        .findFirst().orElse(null);
                if (oldTheme != null) {
                    savedFormThemes.add(oldTheme);
                } else {
                    JournalOccupationModuleTheme jmt = new JournalOccupationModuleTheme();
                    jmt.setJournal(jmth.getJournal());
                    jmt.setLessonPlanModule(jmth.getLessonPlanModule());
                    jmt.setCurriculumVersionOccupationModuleTheme(em.getReference(CurriculumVersionOccupationModuleTheme.class, jmth.getCvomt()));
                    journal.getJournalOccupationModuleThemes().add(jmt);
                    savedFormThemes.add(jmt);
                }
            }
            oldThemes.removeIf(o -> !savedFormThemes.contains(o));
        }
    }
    
    private void setTimetableObjectStudentGroups(Journal journal, LessonPlanJournalForm form, LessonPlanModule lessonPlanModule) {
        // Remove previously connected groups from timetable objects
        Set<Long> connectedGroups = StreamUtil.toMappedSet(LessonPlanGroupForm::getStudentGroup, form.getGroups());
        connectedGroups.add(EntityUtil.getId(lessonPlanModule.getLessonPlan().getStudentGroup()));
        List<TimetableObjectStudentGroup> leftOverTimetableGroups = em.createQuery(
                "select tosg from TimetableObjectStudentGroup tosg join tosg.timetableObject to where to.journal.id = ?1 and tosg.studentGroup.id not in ?2",
                TimetableObjectStudentGroup.class)
                .setParameter(1, EntityUtil.getId(journal))
                .setParameter(2, connectedGroups)
                .getResultList();
        
        for (TimetableObjectStudentGroup group : leftOverTimetableGroups) {
            em.remove(group);
            em.flush();
        }
        
        // Add student groups to existing timetable objects
        List<TimetableObject> timetableObjects = em.createQuery("select to from TimetableObject to where to.journal.id = ?1", TimetableObject.class)
                .setParameter(1, EntityUtil.getId(journal))
                .getResultList();
        for (TimetableObject object : timetableObjects) {
            List<Long> groups = StreamUtil.toMappedList(tosg -> EntityUtil.getId(tosg.getStudentGroup()), object.getTimetableObjectStudentGroups());
            for (Long connectedGroup : connectedGroups) {
                if (!groups.contains(connectedGroup)) {
                    TimetableObjectStudentGroup tosg = new TimetableObjectStudentGroup();
                    tosg.setTimetableObject(object);
                    tosg.setStudentGroup(em.getReference(StudentGroup.class, connectedGroup));
                    object.getTimetableObjectStudentGroups().add(tosg);
                }
            }
        }
    }

    private void setUniqueUntisCode(Journal journal, LessonPlanJournalForm form) {
        if (StringUtils.isEmpty(form.getUntisCode())) {
            journal.setUntisCode(UntisCodeUtil.generateJournalCode(journal, form, em));
        } else {
            List<Journal> journalsWithSameCode = UntisCodeUtil.journalsWithUntisCode(journal, form.getUntisCode(), em);
            if (journalsWithSameCode.size() > 1) {
                journal.setUntisCode(UntisCodeUtil.generateJournalCode(journal, form, em));
            }
        }
    }

    private void createMissingPlansAndAdd(Collection<Long> newGroupIds, Map<Long, Long> lessonPlanIds, Long studyYearId, HoisUserDetails user) {
        newGroupIds.removeAll(lessonPlanIds.keySet());
        // add lesson plans for student groups not already present
        for(Long missingGroupId : newGroupIds) {
            LessonPlanCreateForm lpCreateForm = new LessonPlanCreateForm();
            lpCreateForm.setStudentGroup(missingGroupId);
            lpCreateForm.setStudyYear(studyYearId);
            LessonPlan lp = create(user, lpCreateForm);
            lessonPlanIds.put(missingGroupId, EntityUtil.getId(lp));
        }
    }

    private static LessonPlanModule getLessonPlanModule(List<LessonPlanModule> lessonPlanModules, Long lessonPlan, Long cvom) {
        return lessonPlanModules.stream().filter(p ->
            EntityUtil.getId(p.getLessonPlan()).equals(lessonPlan) &&
            EntityUtil.getId(p.getCurriculumVersionOccupationModule()).equals(cvom)).findFirst().orElse(null);
    }

    public boolean journalHasStudents(Journal journal) {
        List<?> data = em.createNativeQuery("select j.id from journal j " +
                "join journal_student js on js.journal_id = j.id " +
                "where j.id = :journalId")
                .setParameter("journalId", journal.getId())
                .getResultList();
        return !data.isEmpty();
    }

    public void deleteJournal(HoisUserDetails user, Journal journal) {
        if (!journal.getJournalEntries().isEmpty()) {
            throw new ValidationFailedException("lessonplan.journal.hasEntries");
        }
        EntityUtil.setUsername(user.getUsername(), em);

        //  remove timetable objects and groups that do not have any connecting events
        Query objectsQuery = em.createNativeQuery("select tto.id from timetable_object tto where tto.journal_id=?1 " + 
                "and tto.id not in (select tto.id from timetable_object tto join timetable_event te on te.timetable_object_id=tto.id " +
                "where tto.journal_id=?1)");
        objectsQuery.setParameter(1, EntityUtil.getId(journal));
        List<?> objectsData = objectsQuery.getResultList();
        
        if (!objectsData.isEmpty()) {
            List<Long> objects = StreamUtil.toMappedList(r -> resultAsLong(r, 0), objectsData);
            List<TimetableObjectStudentGroup> groups = em
                    .createQuery("select tosg from TimetableObjectStudentGroup tosg where tosg.timetableObject.id in (?1)", TimetableObjectStudentGroup.class)
                    .setParameter(1, objects).getResultList();
            
            for (TimetableObjectStudentGroup group: groups) {
                EntityUtil.deleteEntity(group, em);
            }
            for (Long objectId : objects) {
                EntityUtil.deleteEntity(em.getReference(TimetableObject.class, objectId), em);
            }
        }

        EntityUtil.deleteEntity(journal, em);

        if (journal.getJournalSub() != null) {
            journal.getJournalSub().getJournals().remove(journal);
            journalSubChangesFromJournalDeletion(journal.getJournalSub());
        }
    }

    private void journalSubChangesFromJournalDeletion(JournalSub journalSub) {
        List<Journal> journalSubJournals = journalSub.getJournals();

        Classifier groupProportion = em.find(Classifier.class, MainClassCode.PAEVIK_GRUPI_JAOTUS.name()
                + "_" + journalSubJournals.size());
        if (groupProportion == null) {
            throw new ValidationFailedException("lessonplan.journal.groupProportionClassifierMissing");
        }

        if (journalSubJournals.size() > 1) {
            for (Journal subJournal : journalSubJournals) {
                subJournal.setGroupProportion(groupProportion);
                EntityUtil.save(subJournal, em);
            }
            journalSub.setSubJournals(Long.valueOf(journalSubJournals.size()));
            EntityUtil.save(journalSub, em);
        } else {
            Journal leftOverJournal = journalSubJournals.get(0);
            leftOverJournal.setGroupProportion(groupProportion);
            leftOverJournal.setJournalSub(null);
            EntityUtil.save(leftOverJournal, em);
            EntityUtil.deleteEntity(journalSub, em);
        }
    }

    private Map<Long, Long> findLessonPlanByStudyYearAndStudentGroup(StudyYear studyYear, Collection<Long> studentGroup) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_plan lp");

        qb.requiredCriteria("lp.study_year_id = :studyYear", "studyYear", EntityUtil.getId(studyYear));
        qb.requiredCriteria("lp.student_group_id in (:studentGroup)", "studentGroup", studentGroup);

        List<?> result = qb.select("lp.student_group_id, lp.id", em).getResultList();
        return StreamUtil.toMap(r -> resultAsLong(r, 0),  r -> resultAsLong(r, 1), result);
    }

    private Map<Long, List<Long>> studentgroupsWithLessonPlans(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from study_year sy inner join lesson_plan lp on sy.id = lp.study_year_id inner join student_group sg on lp.student_group_id = sg.id");

        qb.requiredCriteria("sg.school_id = :schoolId and sy.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select("sy.id, sg.id as sg_id", em).getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toList())));
    }

    private List<LessonPlanCurriculumDto> curriculumLessonPlans(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_plan lp "
                        + "join curriculum_version cv on lp.curriculum_version_id = cv.id "
                        + "join curriculum c on cv.curriculum_id = c.id "
                        + "join student_group sg on lp.student_group_id = sg.id "
                        + "join study_year sy on lp.study_year_id = sy.id "
                        + "join classifier cl on sy.year_code = cl.code");

        qb.requiredCriteria("lp.school_id = :schoolId", "schoolId", schoolId);

        List<?> data = qb.select("c.id as curriculum_id, c.mer_code, sy.id as study_year_id, sy.end_date, "
                        + "lp.id as lesson_plan_id, cl.name_et || ' ' || sg.code as name", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            LessonPlanCurriculumDto dto = new LessonPlanCurriculumDto();
            dto.setCurriculum(resultAsLong(r, 0));
            dto.setMerCode(resultAsString(r, 1));
            dto.setStudyYear(resultAsLong(r, 2));
            dto.setStudyYearEndDate(resultAsLocalDate(r, 3));
            dto.setLessonplan(resultAsLong(r, 4));
            dto.setName(resultAsString(r, 5));
            return dto;
        }, data);
    }

    private Map<Long, Long> scheduleLegends(LessonPlan lessonPlan) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_plan lp inner join study_year sy on lp.study_year_id = sy.id"
                + " inner join study_period sp on sy.id = sp.study_year_id"
                + " inner join study_year_schedule sys on sp.id = sys.study_period_id");

        qb.requiredCriteria("lp.id = :lessonPlanId", "lessonPlanId", lessonPlan.getId());
        qb.requiredCriteria("sys.school_id = :schoolId", "schoolId", EntityUtil.getId(lessonPlan.getSchool()));
        qb.requiredCriteria("sys.student_group_id = :studentGroupId", "studentGroupId", EntityUtil.getId(lessonPlan.getStudentGroup()));

        List<?> data = qb.select("sys.week_nr, sys.study_year_schedule_legend_id", em).getResultList();
        return data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> resultAsLong(r, 1), (o, n) -> o));
    }

    private static void assertSameSchool(Journal journal, School school) {
        if(school != null && !EntityUtil.getId(journal.getSchool()).equals(EntityUtil.getId(school))) {
            throw new AssertionFailedException("School mismatch");
        }
    }
    
    public byte[] lessonplanAsExcel(LessonPlan lessonPlan) {
        LessonPlanDto dto = get(lessonPlan);
        List<LessonPlanXlsStudyPeriodDto> studyPeriods = lessonplanExcelStudyPeriods(dto.getStudyPeriods());
        List<LessonPlanXlsModuleDto> modules = lessonplanExcelModules(dto);
        LessonPlanXlsTotalsDto totals = lessonplanExcelTotals(modules, dto.getWeekNrs(),
                dto.getVocationalContactCapacities(), true);

        Map<String, Object> data = new HashMap<>();
        data.put("capacities", dto.getLessonPlanCapacities());

        data.put("studyYearCode", dto.getStudyYear().getYear());
        data.put("studentGroupCode", dto.getStudentGroupCode());
        data.put("courseNr", dto.getCourseNr());
        data.put("curriculumCode", dto.getCurriculumCode());
        data.put("curriculumVersion", dto.getCurriculumVersion());
        data.put("studyPeriodYears", Integer.valueOf(dto.getStudyPeriod().intValue() / 12));
        data.put("studyPeriodMonths", Integer.valueOf(dto.getStudyPeriod().intValue() % 12));

        data.put("studyPeriods", studyPeriods);
        data.put("weekNrs", dto.getWeekNrs());
        data.put("modules", modules);
        data.put("totals", totals);
        
        return xlsService.generate("lessonplan.xls", data);
    }
    
    private static List<LessonPlanXlsStudyPeriodDto> lessonplanExcelStudyPeriods(List<StudyPeriodWithWeeksDto> inputPeriods) {
        List<LessonPlanXlsStudyPeriodDto> studyPeriods = new ArrayList<>();
        for (StudyPeriodWithWeeksDto sp : inputPeriods) {
            LessonPlanXlsStudyPeriodDto studyPeriod = new LessonPlanXlsStudyPeriodDto();
            studyPeriod.setId(sp.getId());
            studyPeriod.setNameEt(sp.getNameEt());
            studyPeriod.setNameEn(sp.getNameEn());
             
            List<Short> colspanColumns = sp.getWeekNrs().size() > 0
                    ? sp.getWeekNrs().subList(0, sp.getWeekNrs().size() - 1)
                    : sp.getWeekNrs();
            studyPeriod.setColspanColumns(colspanColumns);
            studyPeriods.add(studyPeriod);
        }
        return studyPeriods;
    }
    
    private List<LessonPlanXlsModuleDto> lessonplanExcelModules(LessonPlanDto dto) {
        List<LessonPlanXlsModuleDto> modules = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<LessonPlanModuleDto> dtoModules = (List<LessonPlanModuleDto>) dto.getModules();
        for (LessonPlanModuleDto m : dtoModules) {
            LessonPlanXlsModuleDto module = new LessonPlanXlsModuleDto();
            module.setNameEt(m.getNameEt());
            module.setNameEn(m.getNameEn());
            
            AutocompleteResult teacher = m.getTeacher() != null
                    ? AutocompleteResult.of(em.getReference(Teacher.class, m.getTeacher().getId()))
                    : null;
            module.setTeacher(teacher != null ? teacher.getNameEn() : null);
        
            List<LessonPlanXlsJournalDto> journals =  lessonplanExcelJournals(m, dto.getWeekNrs());
            module.setJournals(journals);
            setLessonplanExcelModuleTotals(module, journals, dto.getWeekNrs(), true);
            modules.add(module);
        }
        return modules;
    }
    
    private static List<LessonPlanXlsJournalDto> lessonplanExcelJournals(LessonPlanModuleDto module, List<Short> weekNrs) {
        List<LessonPlanXlsJournalDto> journals = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<LessonPlanModuleJournalDto> dtoJournals = (List<LessonPlanModuleJournalDto>) module.getJournals();
        for (LessonPlanModuleJournalDto j : dtoJournals) {
            LessonPlanXlsJournalDto journal = new LessonPlanXlsJournalDto();
            journal.setNameEt(j.getNameEt());
            journal.setTeachers(StreamUtil.toMappedList(
                    t -> ((LessonPlanModuleJournalTeacherDto) t).getTeacher().getNameEt(), j.getTeachers()));
            journal.setGroupProportion(j.getGroupProportion());
            journal.setHours(sortCapacities(j.getHours()));
            
            List<Short> totalHours = grandTotals(j.getHours(), weekNrs);
            journal.setTotalHours(totalHours);
            journals.add(journal);
        }
        return journals;
    }
    
    private void setLessonplanExcelModuleTotals(LessonPlanXlsModuleDto module, List<LessonPlanXlsJournalDto> journals,
            List<Short> weekNrs, boolean useGroupProportion) {
        Map<String, List<Double>> hours = new HashMap<>();
        journals.forEach(journal -> {
            double groupProportion = useGroupProportion ? 1 / Double
                    .valueOf(em.getReference(Classifier.class, journal.getGroupProportion()).getValue()).doubleValue()
                    : 1;
            for (String capacity : journal.getHours().keySet()) {
                if (!hours.containsKey(capacity)) {
                    List<Double> weekHours = new ArrayList<>();
                    for (Short hour : journal.getHours().get(capacity)) {
                        weekHours.add(hour != null ? Double.valueOf(hour.shortValue() * groupProportion) : Double.valueOf(0));
                    }
                    hours.put(capacity, weekHours);
                } else {
                    List<Double> capacityHours = hours.get(capacity);
                    List<Short> journalCapacityHours = journal.getHours().get(capacity);
                    for (int i = 0; i < capacityHours.size(); i++) {
                        double weekHours = capacityHours.get(i) != null ? capacityHours.get(i).doubleValue() : 0;
                        double journalWeekHours = journalCapacityHours.get(i) != null ? journalCapacityHours.get(i).doubleValue() * groupProportion : 0;
                        capacityHours.set(i, Double.valueOf(weekHours + journalWeekHours));
                    }
                }
            }
        });
        module.setHours(sortTotalHourCapacities(hours));
        module.setTotalHours(grandProportionTotals(module.getHours(), weekNrs));
    }
    
    private LessonPlanXlsTotalsDto lessonplanExcelTotals(List<LessonPlanXlsModuleDto> modules, List<Short> weekNrs,
            List<String> contactTypes, boolean useGroupProportion) {
        LessonPlanXlsTotalsDto totals = new LessonPlanXlsTotalsDto();
        
        Map<String, List<Double>> hours = new HashMap<>();
        modules.forEach(module -> module.getJournals().forEach(journal -> {
            double groupProportion = useGroupProportion ? 1 / Double
                    .valueOf(em.getReference(Classifier.class, journal.getGroupProportion()).getValue()).doubleValue()
                    : 1;
            for (String capacity : journal.getHours().keySet()) {
                if (!hours.containsKey(capacity)) {
                    List<Double> weekHours = new ArrayList<>();
                    for (Short hour : journal.getHours().get(capacity)) {
                        weekHours.add(hour != null ? Double.valueOf(hour.shortValue() * groupProportion) : Double.valueOf(0));
                    }
                    hours.put(capacity, weekHours);
                } else {
                    List<Double> capacityHours = hours.get(capacity);
                    List<Short> journalCapacityHours = journal.getHours().get(capacity);
                    for (int i = 0; i < capacityHours.size(); i++) {
                        double weekHours = capacityHours.get(i) != null ? capacityHours.get(i).doubleValue() : 0;
                        double journalWeekHours = journalCapacityHours.get(i) != null ? journalCapacityHours.get(i).doubleValue() * groupProportion : 0;
                        capacityHours.set(i, Double.valueOf(weekHours + journalWeekHours));
                    }
                }
            }
        }));

        totals.setHours(sortTotalHourCapacities(hours));
        totals.setTotalHours(grandProportionTotals(totals.getHours(), weekNrs));
        Map<String, List<Double>> contactHours = hours.entrySet().stream()
                .filter(map -> contactTypes.contains(map.getKey()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        totals.setTotalContactHours(grandProportionTotals(contactHours, weekNrs));
        return totals;
    }
    
    private static List<Short> grandTotals(Map<String, List<Short>> capacityHours, List<Short> weekNrs) {
        List<Short> totalHours = new ArrayList<>();
        for (int i = 0; i < weekNrs.size(); i++) {
            totalHours.add(Short.valueOf((short) 0));
        }
        
        for (String capacity : capacityHours.keySet()) {
            List<Short> hours = capacityHours.get(capacity);
            for (int i = 0; i < hours.size() ; i++) {
                Short weekHours = hours.get(i) != null ? hours.get(i) : Short.valueOf((short) 0);
                totalHours.set(i, Short.valueOf((short) (totalHours.get(i).shortValue() + weekHours.shortValue())));
            }
        }
        return totalHours;
    }
    
    private static List<Double> grandProportionTotals(Map<String, List<Double>> capacityHours, List<Short> weekNrs) {
        List<Double> totalHours = new ArrayList<>();
        for (int i = 0; i < weekNrs.size(); i++) {
            totalHours.add(Double.valueOf(0));
        }
        
        for (String capacity : capacityHours.keySet()) {
            List<Double> hours = capacityHours.get(capacity);
            for (int i = 0; i < hours.size() ; i++) {
                double weekHours = hours.get(i) != null ? hours.get(i).doubleValue() : 0;
                totalHours.set(i, Double.valueOf(totalHours.get(i).doubleValue() + weekHours));
            }
        }
        return totalHours;
    }
    
    private static Map<String, List<Short>> sortCapacities(Map<String, List<Short>> hours) {
        Map<String, List<Short>> sorted = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (String capacity : hours.keySet()) {
            List<Short> capacityHours = new ArrayList<>();
            for (Short hour : hours.get(capacity) ) {
                capacityHours.add(hour != null ? Short.valueOf(hour.shortValue()) : null);
            }
            sorted.put(capacity, capacityHours);
        }
        return sorted;
    }
    
    private static Map<String, List<Double>> sortTotalHourCapacities(Map<String, List<Double>> hours) {
        Map<String, List<Double>> sorted = new LinkedHashMap<>();
        List<String> sortedCapacities = StreamUtil.nullSafeSet(hours.keySet()).stream()
                .sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.toList());
        for (String capacity : sortedCapacities) {
            sorted.put(capacity, hours.get(capacity));
        }
        return sorted;
    }

    public byte[] lessonplanByTeacherAsExcel(HoisUserDetails user, Teacher teacher, StudyYear studyYear) {
        LessonPlanByTeacherDto dto = getByTeacher(user, teacher, studyYear);
        List<LessonPlanXlsStudyPeriodDto> studyPeriods = lessonplanExcelStudyPeriods(dto.getStudyPeriods());
        List<LessonPlanXlsJournalDto> journals = lessonplanExcelJournals(dto);

        LessonPlanXlsModuleDto totalModule = new LessonPlanXlsModuleDto();
        totalModule.setJournals(journals);
        LessonPlanXlsTotalsDto totals = lessonplanExcelTotals(Collections.singletonList(totalModule), dto.getWeekNrs(),
                dto.getVocationalContactCapacities(), false);

        Map<String, Object> data = new HashMap<>();
        data.put("studyYearCode", dto.getStudyYearCode());
        data.put("teacherName", dto.getTeacherName());
        data.put("capacities", dto.getLessonPlanCapacities());
        data.put("studyPeriods", studyPeriods);
        data.put("weekNrs", dto.getWeekNrs());
        data.put("journals", journals);
        data.put("totals", totals);
        data.put("subjects", dto.getSubjects());
        data.put("subjectTotals", dto.getSubjectTotals());
        data.put("subjectContactTotals", dto.getSubjectContactTotals());
        
        return xlsService.generate("lessonplanbyteacher.xls", data);
    }
    
    public byte[] summaryLessonPlan(LessonPlanSearchCommand criteria, HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from lesson_plan lp "
                + "join student_group sg on lp.student_group_id = sg.id "
                + "join curriculum_version cv on lp.curriculum_version_id = cv.id "
                + "join curriculum c on cv.curriculum_id = c.id "
                + "left join school_department sd on cv.school_department_id = sd.id "
                + "join lesson_plan_module lpm on lp.id = lpm.lesson_plan_id "
                + "join curriculum_version_omodule cvo on cvo.id = lpm.curriculum_version_omodule_id "
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id "
                + "join (select distinct j.id, jot.lesson_plan_module_id, j.group_proportion_code, j.name_et "
                    + "from journal_omodule_theme jot "
                    + "join journal j on jot.journal_id = j.id) j on j.lesson_plan_module_id = lpm.id "
                + "join classifier g_prop on g_prop.code = j.group_proportion_code "
                + "join journal_teacher jt on j.id = jt.journal_id "
                + "join teacher t on jt.teacher_id = t.id "
                + "join person p on t.person_id = p.id");

        if (user.isLeadingTeacher() && !Boolean.TRUE.equals(criteria.getByTeacher())) {
            qb.requiredCriteria("c.id in (:userCurriculumIds)", "userCurriculumIds",
                    user.getCurriculumIds());
        }
        qb.requiredCriteria("lp.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroup());
        qb.optionalCriteria("lp.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", criteria.getCurriculumVersion());
        qb.optionalCriteria("cv.school_department_id = :schoolDepartmentId", "schoolDepartmentId", criteria.getSchoolDepartment());
        if (user.isTeacher()) {
            qb.requiredCriteria("jt.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        } else {
            qb.optionalCriteria("jt.teacher_id in (:teacherIds)", "teacherIds", criteria.getTeacher());
        }
        qb.sort("sg.code, cm.name_et, j.name_et");
        List<?> journals = qb.select(
                "lp.id as planId, j.id as journalId, j.name_et as journalEt, "
                + "sd.id as departmentId, sd.name_et as departmentEt, sd.name_en as departmentEn, "
                // HTM code    group proportion
                + "c.mer_code, g_prop.name_et as proportionEt, jt.id as teacherId, p.firstname || ' ' || p.lastname, "
                + "sg.id, sg.code as groupCode, cm.id as moduleId, cm.name_et as moduleEt, cm.name_en as moduleEn, "
                + "c.id as curriculumId, c.name_et as curriculumEt, c.name_en as curriculumEn"
                , em).getResultList();
        Set<Long> lessonPlanIds = journals.stream().map(p -> resultAsLong(p, 0)).collect(Collectors.toSet());
        List<ClassifierDto> schoolCapacities = lessonPlanSchoolCapacityTypeDtos(user.getSchoolId(), Boolean.FALSE);
        // add missing journal capacities if school capacities where changed but lesson plan still contains them
        Set<String> additionalJournalCapacities = getJournalCapacitiesFromLessonPlan(lessonPlanIds, criteria, user);
        addMissingJournalCapacities(schoolCapacities, additionalJournalCapacities);
        schoolCapacities.sort(Comparator.comparing(ClassifierDto::getNameEt, String.CASE_INSENSITIVE_ORDER));
        List<String> defaultSort = schoolCapacities.stream().map(ClassifierDto::getValue).collect(Collectors.toList());
        // map of journal teacher id, student group id and capacity dto
        Map<Long, Map<Long, List<LessonPlanXlsCapacityDto>>> capacitiesPerJournalTeacherId = teacherCapacities(criteria, schoolCapacities, lessonPlanIds, user);
        ClassifierDto contactLesson = new ClassifierDto();
        contactLesson.setValue("kt");
        schoolCapacities.add(contactLesson);
        List<LessonPlanXlsTeacherDto> teachers = StreamUtil.toMappedList(r -> {
            LessonPlanXlsTeacherDto dto = new LessonPlanXlsTeacherDto();
            dto.setLessonPlanId(resultAsLong(r, 0));
            dto.setJournal(new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 2)));
            dto.setDepartment(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5)));
            dto.setMerCode(resultAsString(r, 6));
            dto.setProportion(resultAsString(r, 7));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 8), resultAsString(r, 9), resultAsString(r, 9)));
            Map<Long, List<LessonPlanXlsCapacityDto>> capacitiesPerStudentGroup = capacitiesPerJournalTeacherId.get(resultAsLong(r, 8));
            if (capacitiesPerStudentGroup != null) {
                List<LessonPlanXlsCapacityDto> capacities = capacitiesPerStudentGroup.get(resultAsLong(r, 10));
                if (capacities != null) {
                    setCapacitySummaries(dto, capacities);
                    capacities.sort(Comparator.comparing(LessonPlanXlsCapacityDto::getStudyPeriodStart)
                            .thenComparing(Comparator.comparingInt(item -> defaultSort.indexOf(item.getCapacityType()))));
                    Map<Long, List<LessonPlanXlsCapacityDto>> capacietiesPerStudyPeriod = capacities.stream().collect(Collectors.groupingBy(o -> o.getStudyPeriod().getId(), LinkedHashMap::new, 
                            Collectors.mapping(s -> s, Collectors.toList())));
                    Iterator<Map.Entry<Long, List<LessonPlanXlsCapacityDto>>> iterator = capacietiesPerStudyPeriod.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<Long, List<LessonPlanXlsCapacityDto>> pair = iterator.next();
                        LessonPlanXlsCapacityDto copyable = pair.getValue().get(0);
                        LessonPlanXlsCapacityDto newCapacity = createNewCapacity(copyable, pair.getValue());
                        pair.getValue().add(newCapacity);
                    }
                    capacities = capacietiesPerStudyPeriod.entrySet().stream().flatMap(p -> p.getValue().stream()).collect(Collectors.toList());
                    dto.setCapacities(capacities);
                }
            }
            dto.setStudentGroup(new AutocompleteResult(resultAsLong(r, 10), resultAsString(r, 11), resultAsString(r, 11)));
            dto.setModule(new AutocompleteResult(resultAsLong(r, 12), resultAsString(r, 13), resultAsString(r, 14)));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 15), resultAsString(r, 16), resultAsString(r, 17)));
            return dto;
        }, journals);
        Map<String, Object> data = new HashMap<>();
        StudyYear studyYear = em.getReference(StudyYear.class, criteria.getStudyYear());
        data.put("teachers", teachers);
        data.put("capacities", schoolCapacities);
        data.put("studyPeriods", studyYear.getStudyPeriods().stream()
                .sorted(Comparator.comparing(StudyPeriod::getStartDate))
                .map(p -> new AutocompleteResult(p.getId(), p.getNameEt(), p.getNameEn())).collect(Collectors.toList()));
        return xlsService.generate("lessonplansummary.xls", data);
    }
    
    private static void setCapacitySummaries(LessonPlanXlsTeacherDto dto, List<LessonPlanXlsCapacityDto> capacities) {
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

    public static LessonPlanXlsCapacityDto createNewCapacity(LessonPlanXlsCapacityDto copyable,
            List<LessonPlanXlsCapacityDto> list) {
        LessonPlanXlsCapacityDto newCapacity = new LessonPlanXlsCapacityDto();
        newCapacity.setCapacityType("kt");
        newCapacity.setStudyPeriod(copyable.getStudyPeriod());
        newCapacity.setStudyPeriodStart(copyable.getStudyPeriodStart());
        newCapacity.setHours(Long.valueOf(list.stream()
                .filter(p -> p.getHours() != null && Boolean.TRUE.equals(p.getIsContact()))
                .mapToInt(p -> p.getHours().intValue()).sum()));
        return newCapacity;
    }

    private Set<String> getJournalCapacitiesFromLessonPlan(Set<Long> lessonPlanIds, LessonPlanSearchCommand criteria, HoisUserDetails user) {
        if (lessonPlanIds == null || lessonPlanIds.isEmpty()) {
            return new HashSet<>();
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from lesson_plan lp "
                + "join lesson_plan_module lpm on lp.id = lpm.lesson_plan_id "
                + "join journal_omodule_theme jot on lpm.id = jot.lesson_plan_module_id "
                + "join journal j on jot.journal_id = j.id "
                + "join journal_capacity_type jct on j.id = jct.journal_id");
        qb.requiredCriteria("lp.id in (:lessonPlanIds)", "lessonPlanIds", lessonPlanIds);

        // capacity check using `exists` instead of `join` to improve performance
        // capacity_diff = null/false
        qb.filter("(j.is_capacity_diff is not true and exists(" +
                    "select 1 from journal_capacity jc2 " +
                    "where j.id = jc2.journal_id and jct.id = jc2.journal_capacity_type_id) " +
                // capacity_diff = true
                "or j.is_capacity_diff = true and exists(" +
                    "select 1 from journal_teacher jt " +
                    "join journal_teacher_capacity jtc on jt.id = jtc.journal_teacher_id " +
                    "where j.id = jt.journal_id and jct.id = jtc.journal_capacity_type_id)" +
                ")");

        if (user.isTeacher()) {
            qb.requiredCriteria("exists(select 1 from journal_teacher jt " +
                    "where j.id = jt.journal_id and jt.teacher_id = :teacherId)",
                    "teacherId", user.getTeacherId());
        } else {
            qb.optionalCriteria("exists(select 1 from journal_teacher jt " +
                    "where j.id = jt.journal_id and jt.teacher_id in :teacherIds)",
                    "teacherIds", criteria.getTeacher());
        }
        
        List<?> additionalCapacities = qb.select("distinct jct.capacity_type_code", em).getResultList();
        return additionalCapacities.stream().map(p -> resultAsString(p, 0)).collect(Collectors.toSet());
    }

    private Map<Long, Map<Long, List<LessonPlanXlsCapacityDto>>> teacherCapacities(LessonPlanSearchCommand criteria, 
            List<ClassifierDto> schoolCapacities, Set<Long> lessonPlanIds, HoisUserDetails user) {
        if (lessonPlanIds == null || lessonPlanIds.isEmpty() || schoolCapacities == null || schoolCapacities.isEmpty()) {
            return new LinkedHashMap<>();
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from study_year sy "
                + "join lesson_plan lp on sy.id = lp.study_year_id "
                + "join lesson_plan_module lpm on lp.id = lpm.lesson_plan_id "
                + "join (select distinct j.id, jot.lesson_plan_module_id, j.group_proportion_code, j.name_et, j.is_capacity_diff, j.is_free "
                    + "from journal_omodule_theme jot "
                    + "join journal j on jot.journal_id = j.id) j on j.lesson_plan_module_id = lpm.id "
                + "join journal_teacher jt on j.id = jt.journal_id "
                + " left join classifier lp_coefficient on lp_coefficient.code = (select min(lp2.coefficient_code) from lesson_plan lp2"
                    + " join lesson_plan_module lpm2 on lpm2.lesson_plan_id = lp2.id"
                    + " join journal_omodule_theme jot2 on jot2.lesson_plan_module_id = lpm2.id"
                    + " where jot2.journal_id = jt.journal_id)"
                + "join study_period sp on sp.study_year_id = sy.id "
                + "join classifier c_type on c_type.code in (:capacityTypes) "
                + "left join (select jtc.hours, jct.capacity_type_code, jtc.journal_teacher_id, jtc.study_period_id "
                    + "from journal_teacher_capacity jtc "
                    + "join journal_capacity_type jct on jct.id = jtc.journal_capacity_type_id"
                    + ") teacherCap on j.is_capacity_diff = true and teacherCap.journal_teacher_id = jt.id and sp.id = teacherCap.study_period_id and c_type.code = teacherCap.capacity_type_code "
                + "left join (select jc.hours, jc.journal_id, jc.study_period_id, jct.capacity_type_code "
                    + "from journal_capacity jc "
                    + "join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id"
                    + ") journalCap on j.is_capacity_diff is not true and journalCap.journal_id = j.id and sp.id = journalCap.study_period_id and c_type.code = journalCap.capacity_type_code "
                + "left join school_capacity_type sct on sct.capacity_type_code = c_type.code and sct.school_id = lp.school_id and sct.is_higher is not true "
                + "left join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id"
                    + " and sctl.study_year_id = sy.id and coalesce(lp_coefficient.code, 'KOEFITSIENT_K1') = sctl.coefficient_code ");

        qb.filter("lp.id in (" + lessonPlanIds.stream().map(p -> String.valueOf(p)).collect(Collectors.joining(", ")) + ")");
        if (user.isTeacher()) {
            qb.requiredCriteria("jt.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        } else {
            qb.optionalCriteria("jt.teacher_id in (:teacherIds)", "teacherIds", criteria.getTeacher());
        }

        qb.requiredCriteria("sy.id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.parameter("capacityTypes", schoolCapacities.stream().map(p -> p.getCode()).collect(Collectors.toList()));
        qb.groupBy( "journalTeacherId, periodId, periodEt, periodStart, "
                + "sct.is_contact, capacityType, groupId, sctl.load_percentage, j.is_free");
        List<?> data = qb.select(
                "jt.id as journalTeacherId, sp.id as periodId, sp.name_et as periodEt, sp.start_date as periodStart, "
                + "sum(coalesce(teacherCap.hours, journalCap.hours)) as hours, sct.is_contact, c_type.value as capacityType, lp.student_group_id as groupId, "
                + "coalesce(case when j.is_free = true then 0 end, sctl.load_percentage, 100) * coalesce(sum(coalesce(teacherCap.hours, journalCap.hours)), 0) / 100.0 as hoursWithLoad",
                em).getResultList();
        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.groupingBy(r -> resultAsLong(r, 7), LinkedHashMap::new, Collectors.mapping(r -> {
                    LessonPlanXlsCapacityDto dto = new LessonPlanXlsCapacityDto();
                    dto.setStudyPeriod(new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 2)));
                    dto.setStudyPeriodStart(resultAsLocalDate(r, 3));
                    dto.setHours(resultAsLong(r, 4));
                    dto.setIsContact(resultAsBoolean(r, 5));
                    dto.setCapacityType(resultAsString(r, 6));
                    dto.setHoursWithLoad(resultAsDecimal(r, 8));
                    return dto;
                }, Collectors.toList()))));
    }

    private static List<LessonPlanXlsJournalDto> lessonplanExcelJournals(LessonPlanByTeacherDto dto) {
        List<LessonPlanXlsJournalDto> journals = new ArrayList<>();
        for (LessonPlanModuleJournalDto j : dto.getJournals()) {
            LessonPlanXlsJournalDto journal = new LessonPlanXlsJournalDto();
            journal.setNameEt(j.getNameEt());
            journal.setTeachers(StreamUtil.toMappedList(
                    t -> ((LessonPlanModuleJournalTeacherDto) t).getTeacher().getNameEt(), j.getTeachers()));
            journal.setStudentGroups(j.getStudentGroups().stream().collect(Collectors.joining(" ")));
            journal.setGroupProportion(j.getGroupProportion());
            journal.setHours(sortCapacities(j.getHours()));

            List<Short> totalHours = grandTotals(j.getHours(), dto.getWeekNrs());
            journal.setTotalHours(totalHours);
            journals.add(journal);
        }
        return journals;
    }

    private static class JournalOccupationModuleThemeHolder {
        private Journal journal;
        private LessonPlanModule lessonPlanModule;
        private Long cvomt;

        public JournalOccupationModuleThemeHolder(Journal journal, LessonPlanModule lpm, Long cvomt) {
            this.journal = journal;
            this.lessonPlanModule = lpm;
            this.cvomt = cvomt;
        }

        public Journal getJournal() {
            return journal;
        }

        public LessonPlanModule getLessonPlanModule() {
            return lessonPlanModule;
        }

        public Long getCvomt() {
            return cvomt;
        }
    }
}
