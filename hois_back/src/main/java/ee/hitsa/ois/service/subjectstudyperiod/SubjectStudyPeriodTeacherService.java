package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.getOrDefault;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.enums.Coefficient;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.LessonPlanService;
import ee.hitsa.ois.service.SubjectStudyPeriodPlanService;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSubgroupDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanByTeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;
import ee.hitsa.ois.repository.SubjectStudyPeriodPlanRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.service.XlsService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodTeacherDto;

@Transactional
@Service
public class SubjectStudyPeriodTeacherService {

    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private SubjectStudyPeriodPlanRepository subjectStudyPeriodPlanRepository;
    @Autowired
    private SubjectStudyPeriodCapacitiesService subjectStudyPeriodCapacitiesService;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private LessonPlanService lessonPlanService;
    @Autowired
    private ClassifierService classifierService;

    public void setSubjectStudyPeriodsToTeachersContainer(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriod> ssps = subjectStudyPeriodRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("studyPeriod").get("id"), container.getStudyPeriod()));
            filters.add(cb.equal(root.get("studyPeriod").get("studyYear").get("school").get("id"), schoolId));

            Subquery<Long> teacherSubquery = query.subquery(Long.class);
            Root<SubjectStudyPeriodTeacher> targetRoot = teacherSubquery.from(SubjectStudyPeriodTeacher.class);
            teacherSubquery = teacherSubquery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                    .where(cb.equal(targetRoot.get("teacher").get("id"), container.getTeacher()));
            filters.add(root.get("id").in(teacherSubquery));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        List<SubjectStudyPeriodDto> subjectStudyPeriodDtos = StreamUtil.toMappedList(ssp -> {
            SubjectStudyPeriodDto dto = new SubjectStudyPeriodDto();
            dto.setId(EntityUtil.getId(ssp));
            dto.setSubject(EntityUtil.getId(ssp.getSubject()));
            dto.setStudentGroupObjects(
                    StreamUtil.toMappedList(s -> AutocompleteResult.of(s.getStudentGroup()), ssp.getStudentGroups()));
            dto.setTeachers(ssp.getTeachers().stream()
                    .map(t -> SubjectStudyPeriodTeacherDto.of(t, false))
                    .collect(Collectors.toList()));
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, ssp.getCapacities()));
            dto.setGroupProportion(EntityUtil.getCode(ssp.getGroupProportion()));
            dto.setCapacityDiff(ssp.getCapacityDiff());
            dto.setCoefficient(getOrDefault(EntityUtil.getNullableCode(ssp.getCoefficient()),
                    Coefficient.KOEFITSIENT_K1.name()));
            dto.setSubgroups(StreamUtil.toMappedSet(SubjectStudyPeriodSubgroupDto::of, ssp.getSubgroups()));
            return dto;
        }, ssps);
        container.setSubjectStudyPeriodDtos(subjectStudyPeriodDtos);
    }

    public void setSubjectStudyPeriodPlansToTeachersContainer(SubjectStudyPeriodDtoContainer container) {
        if (container.getSubjectStudyPeriodDtos().isEmpty()) {
            container.setSubjectStudyPeriodPlans(Collections.emptyList());
            return;
        }
        List<?> result = em.createNativeQuery("select sspp.plan_id, ssp.id" +
                " from subject_study_period ssp" +
                " join (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP +
                ") sspp on ssp.id = sspp.ssp_id and sspp.priority != 999" +
                " where ssp.id in :sspIds" +
                " order by ssp.id")
                .setParameter("sspIds", StreamUtil.toMappedList(SubjectStudyPeriodDto::getId,
                        container.getSubjectStudyPeriodDtos()))
                .getResultList();
        Map<Long, Long> planIds = result.stream()
                .collect(Collectors.toMap(r -> resultAsLong(r, 1), r -> resultAsLong(r, 0),
                        (o, n) -> o, LinkedHashMap::new));

        if (!planIds.isEmpty()) {
            em.createQuery("select sspp from SubjectStudyPeriodPlan sspp" +
                    " left join fetch sspp.capacities" +
                    " where sspp.id in :planIds", SubjectStudyPeriodPlan.class)
                    .setParameter("planIds", planIds.values())
                    .getResultList();
        }

        container.setSubjectStudyPeriodPlans(planIds.entrySet().stream().map(planId -> {
            SubjectStudyPeriodPlan plan = em.getReference(SubjectStudyPeriodPlan.class, planId.getValue());
            SubjectStudyPeriodPlanDto dto = new SubjectStudyPeriodPlanDto();
            dto.setId(EntityUtil.getId(plan));
            dto.setSubject(EntityUtil.getId(plan.getSubject()));
            dto.setCapacities(StreamUtil.toMappedSet(SubjectStudyPeriodPlanCapacityDto::of, plan.getCapacities()));

            dto.setSspId(planId.getKey());
            return dto;
        }).collect(Collectors.toList()));
    }

    public List<AutocompleteResult> getTeachersList(Long schoolId, Long studyPeriodId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t join person p on p.id = t.person_id");

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("t.is_higher = true");
        qb.filter("t.is_active = true");

        qb.requiredCriteria("not exists " 
                        + "(select * from subject_study_period_teacher sspt "
                        + "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                        + "where ssp.study_period_id = :studyPeriodId and sspt.teacher_id = t.id)",
                          "studyPeriodId", studyPeriodId);

        List<?> data = qb.select("t.id, p.firstname, p.lastname", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }
    
    public byte[] subjectStudyPeriodTeacherAsExcel(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        setSubjectStudyPeriodsToTeachersContainer(schoolId, container);
        setSubjectStudyPeriodPlansToTeachersContainer(container);
        subjectStudyPeriodCapacitiesService.setSubjects(container);
        
        List<Classifier> capacities = subjectStudyPeriodCapacitiesService.capacityClassifiers(schoolId, container);
        List<String> capacityCodes = StreamUtil.toMappedList(c -> EntityUtil.getCode(c), capacities);
        
        List<Map<String, Object>> subjects = new ArrayList<>();
        for (AutocompleteResult s : container.getSubjects()) {
            subjects.add(excelSubject(s, container, capacityCodes));
        }

        Map<String, Object> data = new HashMap<>();
        Teacher teacher = em.getReference(Teacher.class, container.getTeacher());
        StudyPeriod studyPeriod = em.getReference(StudyPeriod.class, container.getStudyPeriod());
        
        data.put("studyYear", AutocompleteResult.of(studyPeriod.getStudyYear()));
        data.put("studyPeriod", AutocompleteResult.of(studyPeriod));
        data.put("teacher", AutocompleteResult.of(teacher));
        data.put("teacherLoad", teacher.getScheduleLoad());
        data.put("teacherLoadPeriod", teacher.getIsStudyPeriodScheduleLoad());
        data.put("capacities", capacities);
        data.put("subjects", subjects);
        data.put("totals", subjectStudyPeriodCapacitiesService.subjectPeriodTotals(subjects, capacityCodes));

        List<LessonPlanByTeacherDto.LessonPlanByTeacherSubjectDto> lessonPlanSubjects = lessonPlanService
                .getTeacherSubjects(teacher.getId(), EntityUtil.getId(studyPeriod.getStudyYear())).values().stream()
                .sorted(Comparator.comparing(LessonPlanByTeacherDto.LessonPlanByTeacherSubjectDto::getNameEt, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        data.put("contactTotals", LessonPlanService.getSubjectContactTotals(lessonPlanSubjects)
                .getOrDefault(studyPeriod.getId(), Collections.emptyMap()));

        List<?> capacitiesByYear = queryTeacherVocationalCapacitiesByYear(teacher, studyPeriod.getStudyYear());
        Map<String, Integer> periodCapacities = capacitiesByYear.stream()
                .filter(r -> studyPeriod.getId().equals(resultAsLong(r, 2)))
                .collect(Collectors
                        .toMap(r -> resultAsString(r, 1), r -> resultAsInteger(r, 0), Integer::sum));
        data.put("totalsVocationalPeriod", periodCapacities);
        data.put("totalVocationalPeriod", periodCapacities.values().stream().reduce(0, Integer::sum));
        Map<String, Integer> yearCapacities = capacitiesByYear.stream()
                .collect(Collectors
                        .toMap(r -> resultAsString(r, 1), r -> resultAsInteger(r, 0), Integer::sum));
        data.put("totalsVocationalYear", yearCapacities);
        data.put("totalVocationalYear", yearCapacities.values().stream().reduce(0, Integer::sum));

        return xlsService.generate("subjectstudyperiodteacher.xls", data);
    }
    
    private Map<String, Object> excelSubject(AutocompleteResult subjectDto, SubjectStudyPeriodDtoContainer container,
            List<String> capacityCodes) {
        Map<String, Object> subject = new HashMap<>();
        Map<String, Short> subjectCapacityHours = subjectStudyPeriodCapacitiesService.subjectCapacityHours(subjectDto.getId(), container, capacityCodes);
        
        List<Map<String, Object>> periods = new ArrayList<>();
        Map<String, Short> periodTotals = subjectStudyPeriodCapacitiesService.emptyOrderedCapacityHours(capacityCodes);
        
        List<SubjectStudyPeriodDto> periodDtos = subjectStudyPeriodCapacitiesService.teacherSubjectStudyPeriodDtos(subjectDto,
                container);
        for (SubjectStudyPeriodDto periodDto : periodDtos) {
            Map<String, Object> period = subjectStudyPeriodCapacitiesService.periodExcel(periodDto, periodTotals, capacityCodes);
            if (periodDto.getStudentGroupObjects() != null) {
                String nameEt = periodDto.getStudentGroupObjects().stream().map(sg -> sg.getNameEt()).collect(Collectors.joining(", "));
                String nameEn = periodDto.getStudentGroupObjects().stream().map(sg -> sg.getNameEn()).collect(Collectors.joining(", "));
                period.put("studentGroups", new AutocompleteResult(null, nameEt, nameEn));
            }
            periods.add(period);
        }
        
        subject.put("subject", subjectDto);
        subject.put("hours", subjectCapacityHours);
        subject.put("subjectPeriods", periods);
        subject.put("totals", periodTotals);
        return subject;
    }

    public void setTeacherContainerData(SubjectStudyPeriodDtoContainer container) {
        ValidationFailedException.throwIf(container.getTeacher() == null, "Teacher cannot be null");
        Teacher teacher = em.getReference(Teacher.class, container.getTeacher());
        container.setIsStudyPeriodScheduleLoad(teacher.getIsStudyPeriodScheduleLoad());
        container.setStudyLoad(teacher.getScheduleLoad());

        if (!Boolean.TRUE.equals(teacher.getIsVocational())) {
            return;
        }

        StudyPeriod period = em.getReference(StudyPeriod.class, container.getStudyPeriod());
        // journal capacity
        List<?> capacityResult = queryTeacherVocationalCapacitiesByYear(teacher, period.getStudyYear());

        container.setTeacherPeriodVocationalCapacities(capacityResult.stream()
                .filter(r -> period.getId().equals(resultAsLong(r, 2)))
                .collect(Collectors
                        .toMap(r -> resultAsString(r, 1), r -> resultAsInteger(r, 0), Integer::sum, LinkedHashMap::new)));
        container.setTeacherYearVocationalCapacities(capacityResult.stream()
                .collect(Collectors
                        .toMap(r -> resultAsString(r, 1), r -> resultAsInteger(r, 0), Integer::sum)));

        Set<String> validCapacities = container.getTeacherYearVocationalCapacities().keySet();
        List<Classifier> allCapacityTypes = classifierService.findAllByMainClassCode(MainClassCode.MAHT);
        container.setVocationalCapacityTypes(allCapacityTypes.stream()
                .filter(cl -> validCapacities.contains(EntityUtil.getCode(cl)))
                .sorted(Comparator.comparing(Classifier::getCode, String.CASE_INSENSITIVE_ORDER))
                .map(ClassifierDto::ofEssential)
                .collect(Collectors.toList()));
    }

    private List<?> queryTeacherVocationalCapacitiesByYear(Teacher teacher, StudyYear year) {
        // journal capacity
        return (List<?>) em.createNativeQuery("select coalesce(sum(jc.hours), 0) as num, jct.capacity_type_code cap, jc.study_period_id as per " +
                "from journal_teacher jt " +
                "join journal j on j.id = jt.journal_id " +
                "join journal_capacity jc on j.id = jc.journal_id " +
                "join journal_capacity_type jct on jc.journal_capacity_type_id = jct.id " +
                "join classifier c on jct.capacity_type_code = c.code " +
                "join school_capacity_type sct on sct.school_id = :schoolId and c.code = sct.capacity_type_code and sct.is_higher = false " +
                "where (j.is_capacity_diff is null or j.is_capacity_diff = false) " +
                "and j.study_year_id = :studyYear and jt.teacher_id = :teacherId " +
                "group by cap, per " +
                "union all " +
                "select coalesce(sum(jtc.hours), 0) as num, jct.capacity_type_code as cap, jtc.study_period_id as per " +
                "from journal_teacher jt2 " +
                "join journal j2 on j2.id = jt2.journal_id " +
                "join journal_teacher_capacity jtc on jt2.id = jtc.journal_teacher_id " +
                "join journal_capacity_type jct on jtc.journal_capacity_type_id = jct.id " +
                "join classifier c on jct.capacity_type_code = c.code " +
                "join school_capacity_type sct on sct.school_id = :schoolId and c.code = sct.capacity_type_code and sct.is_higher = false " +
                "where j2.is_capacity_diff = true and j2.study_year_id = :studyYear and jt2.teacher_id = :teacherId  " +
                "group by cap, per ")
                .setParameter("teacherId", EntityUtil.getId(teacher))
                .setParameter("schoolId", EntityUtil.getId(teacher.getSchool()))
                .setParameter("studyYear", EntityUtil.getId(year))
                .getResultList();
    }
}
