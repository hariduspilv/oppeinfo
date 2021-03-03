package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacherCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.SubjectService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodTeacherDto;

@Transactional
@Service
public class SubjectStudyPeriodCapacitiesService {

    public static final String SQL_SELECT_TEACHER_CAPACITY =
            "select wr_ssptc.subject_study_period_teacher_id, wr_ssptc.subject_study_period_capacity_id, " +
                    "case when wr_ssptc.has_main then wr_ssptc.hours else wr_ssptc.subhours end as hours " +
                    "from (select ssptc.subject_study_period_teacher_id, ssptc.subject_study_period_capacity_id, " +
                        "bool_or(ssptc.subject_study_period_subgroup_id is null) as has_main, " +
                        "sum(case when ssptc.subject_study_period_subgroup_id is null then ssptc.hours else 0 end) as hours, " +
                        "sum(case when ssptc.subject_study_period_subgroup_id is not null then ssptc.hours else 0 end) as subhours " +
                        "from subject_study_period_teacher_capacity ssptc " +
                        "group by ssptc.subject_study_period_teacher_id, ssptc.subject_study_period_capacity_id) as wr_ssptc";

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private AutocompleteService autocompleteService;

    /**
     * TODO: method and class does not match each other (by name at least by name),
     * but still both of them are used in subject study period controller
     */
    public void setSubjects(SubjectStudyPeriodDtoContainer container) {
        List<Long> subjectIds = StreamUtil.toMappedList(s -> s.getSubject(), container.getSubjectStudyPeriodDtos());
        List<Subject> subjects = subjectService.findAllById(subjectIds);
        List<AutocompleteResult> dtos = StreamUtil.toMappedList(AutocompleteResult::of, subjects);
        container.setSubjects(dtos);
    }

    public void updateSspCapacities(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriod> ssps = new ArrayList<>();

        for (SubjectStudyPeriodDto dto : container.getSubjectStudyPeriodDtos()) {
            SubjectStudyPeriod ssp = em.getReference(SubjectStudyPeriod.class, dto.getId());

            AssertionFailedException.throwIf(!EntityUtil.getId(ssp.getSubject().getSchool()).equals(schoolId),
                    "User and subject have different schools!");

            Map<Long, SubjectStudyPeriodCapacity> savedCapacities = StreamUtil.toMap(c -> EntityUtil.getId(c),
                    ssp.getCapacities());
            List<SubjectStudyPeriodCapacity> capacities = new ArrayList<>();
            for (SubjectStudyPeriodCapacityDto capacityDto : dto.getCapacities()) {
                SubjectStudyPeriodCapacity sspc;
                if (capacityDto.getId() != null) {
                    sspc = savedCapacities.remove(capacityDto.getId());
                    sspc.setHours(capacityDto.getHours());
                } else {
                    sspc = EntityUtil.bindToEntity(capacityDto, new SubjectStudyPeriodCapacity(), classifierRepository);
                    sspc.setSubjectStudyPeriod(ssp);
                }
                capacities.add(sspc);
            }

            // Keep period capacities that have connected teacher capacities
            for (SubjectStudyPeriodCapacity savedCapacity : savedCapacities.values()) {
                if (savedCapacity.getTeacherCapacities().size() > 0) {
                    savedCapacity.setHours(Short.valueOf((short) 0));
                    capacities.add(savedCapacity);
                }
            }

            ssp.setCapacities(capacities);
            ssp.setCapacityDiff(dto.getCapacityDiff());
            if (dto.getCoefficient() != null) {
                ssp.setCoefficient(em.getReference(Classifier.class, dto.getCoefficient()));
            }
            updateTeacherCapacities(ssp, dto);
            ssps.add(ssp);
        }
        subjectStudyPeriodRepository.save(ssps);
    }

    public void updateTeacherCapacities(SubjectStudyPeriod subjectStudyPeriod, SubjectStudyPeriodDto dto) {
        Map<Long, SubjectStudyPeriodTeacher> teachersMap = StreamUtil.toMap(t -> EntityUtil.getId(t.getTeacher()),
                subjectStudyPeriod.getTeachers());
        Map<String, SubjectStudyPeriodCapacity> sspCapacitiesMap = StreamUtil
                .toMap(r -> EntityUtil.getCode(r.getCapacityType()), subjectStudyPeriod.getCapacities());

        for (SubjectStudyPeriodTeacherDto teacherDto : dto.getTeachers()) {
            SubjectStudyPeriodTeacher teacher = teachersMap.get(teacherDto.getTeacherId());

            if (Boolean.TRUE.equals(dto.getCapacityDiff())) {
                List<SubjectStudyPeriodCapacityDto> newCapacities = StreamUtil.toFilteredList(c -> c.getHours() != null,
                        teacherDto.getCapacities());
                addMissingSubjectStudyPeriodCapacities(subjectStudyPeriod, sspCapacitiesMap, newCapacities);

                EntityUtil.bindEntityCollection(teacher.getCapacities(), SubjectStudyPeriodTeacherCapacity::getId,
                        newCapacities, SubjectStudyPeriodCapacityDto::getId, dto3 -> {
                            SubjectStudyPeriodTeacherCapacity newCapacity = new SubjectStudyPeriodTeacherCapacity();
                            newCapacity.setSubjectStudyPeriodTeacher(teacher);
                            newCapacity.setSubjectStudyPeriodCapacity(sspCapacitiesMap.get(dto3.getCapacityType()));
                            newCapacity.setHours(dto3.getHours());
                            newCapacity.setSubgroup(dto3.getSubgroup() != null
                                    ? em.getReference(SubjectStudyPeriodSubgroup.class, dto3.getSubgroup()) : null);
                            return newCapacity;
                        }, (dto2, c) -> {
                            c.setHours(dto2.getHours());
                        });
            } else {
                teacher.getCapacities().clear();
            }
        }
    }

    private void addMissingSubjectStudyPeriodCapacities(SubjectStudyPeriod subjectStudyPeriod,
            Map<String, SubjectStudyPeriodCapacity> sspCapacitiesMap,
            List<SubjectStudyPeriodCapacityDto> teacherCapacities) {
        for (SubjectStudyPeriodCapacityDto capacity : teacherCapacities) {
            if (!sspCapacitiesMap.containsKey(capacity.getCapacityType())) {
                SubjectStudyPeriodCapacity sspCapacity = new SubjectStudyPeriodCapacity();
                sspCapacity.setSubjectStudyPeriod(subjectStudyPeriod);
                sspCapacity.setHours(Short.valueOf((short) 0));
                sspCapacity.setCapacityType(em.getReference(Classifier.class, capacity.getCapacityType()));
                sspCapacitiesMap.put(capacity.getCapacityType(), sspCapacity);
                EntityUtil.save(sspCapacity, em);
            }
        }
    }

    public List<Classifier> capacityClassifiers(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        List<Classifier> allCapacityTypes = classifierService.findAllByMainClassCode(MainClassCode.MAHT);

        SchoolCapacityTypeCommand command = new SchoolCapacityTypeCommand();
        command.setIsHigher(Boolean.TRUE);
        List<Classifier> schoolCapacityTypes = autocompleteService.schoolCapacityTypes(schoolId, command);
        
        Set<String> validCapacities = StreamUtil.toMappedSet(EntityUtil::getCode, schoolCapacityTypes);
        for (SubjectStudyPeriodDto subjectStudyPeriodDto : container.getSubjectStudyPeriodDtos()) {
            for (SubjectStudyPeriodCapacityDto capacityDto : subjectStudyPeriodDto.getCapacities()) {
                validCapacities.add(capacityDto.getCapacityType());
            }
        }
        List<SubjectStudyPeriodPlanDto> subjectStudyPeriodPlans = container.getSubjectStudyPeriodPlans();
        if (subjectStudyPeriodPlans != null) {
            for (SubjectStudyPeriodPlanDto planDto : subjectStudyPeriodPlans) {
                for (SubjectStudyPeriodPlanCapacityDto capacityDto : planDto.getCapacities()) {
                    validCapacities.add(capacityDto.getCapacityType());
                }
            }
        }
        
        List<Classifier> capacities = StreamUtil.toFilteredList(c -> validCapacities.contains(EntityUtil.getCode(c)), allCapacityTypes);
        capacities.sort(Comparator.comparing(Classifier::getCode, String.CASE_INSENSITIVE_ORDER));
        return capacities;
    }
    
    public void setCapacityTypes(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        container.setCapacityTypes(StreamUtil.toMappedList(ClassifierDto::of, capacityClassifiers(schoolId, container)));
    }
    
    public Map<String, Short> emptyOrderedCapacityHours(List<String> capacityCodes) {
        Map<String, Short> capacityHours = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        capacityCodes.forEach(c -> capacityHours.put(c, null));
        return capacityHours;
    }
    
    public Map<String, Short> subjectCapacityHours(Long subjectId, SubjectStudyPeriodDtoContainer container, List<String> capacityCodes) {
        Map<String, Short> subjectCapacityHours = emptyOrderedCapacityHours(capacityCodes);
        capacityCodes.forEach(c -> subjectCapacityHours.put(c, Short.valueOf((short) 0)));
        
        SubjectStudyPeriodPlanDto plan = container.getSubjectStudyPeriodPlans().stream()
                .filter(sp -> sp.getSubject().equals(subjectId)).findFirst().orElse(null);
        if (plan != null) {
            plan.getCapacities().forEach(c -> subjectCapacityHours.put(c.getCapacityType(), c.getHours()));
        }
        return subjectCapacityHours;
    }
    
    public Map<String, Short> subjectPeriodTotals(List<Map<String, Object>> subjects, List<String> capacityCodes) {
        Map<String, Short> totals = emptyOrderedCapacityHours(capacityCodes);
        capacityCodes.forEach(c -> totals.put(c, Short.valueOf((short) 0)));
        
        for (Map<String, Object> subject : subjects) {
            @SuppressWarnings("unchecked")
            Map<String, Short> periodTotals = (Map<String, Short>) subject.get("totals");
            
            for (String capacity : capacityCodes) {
                Short totalHours = totals.get(capacity) != null ? totals.get(capacity) : Short.valueOf((short) 0);
                Short periodHours = periodTotals.get(capacity) != null ? periodTotals.get(capacity) : Short.valueOf((short) 0);
                totals.put(capacity, Short.valueOf((short) (totalHours.shortValue() + periodHours.shortValue())));
            }
        }
        
        return totals;
    }
    
    public Map<String, Object> periodExcel(SubjectStudyPeriodDto periodDto, Map<String, Short> periodTotals, List<String> capacityCodes) {
        Map<String, Object> period = new HashMap<>();

        Map<String, Short> periodCapacityHours = emptyOrderedCapacityHours(capacityCodes);
        Set<String> usedMainCapacity = new HashSet<>();
        periodDto.getCapacities().forEach(c -> {
            if (c.getSubgroup() == null) {
                usedMainCapacity.add(c.getCapacityType());
                periodCapacityHours.put(c.getCapacityType(), c.getHours());
            } else if (!usedMainCapacity.contains(c.getCapacityType())) {
                periodCapacityHours.merge(c.getCapacityType(), c.getHours(),
                        (a, b) -> Short.valueOf((short) (a.shortValue() + b.shortValue())));
            }
        });

        for (String capacity : periodCapacityHours.keySet()) {
            Short totalHours = periodTotals.get(capacity) != null ? periodTotals.get(capacity)
                    : Short.valueOf((short) 0);
            if (!periodTotals.containsKey(capacity)) {
                periodTotals.put(capacity, totalHours);
            } else {
                Short periodHours = periodCapacityHours.get(capacity) != null ? periodCapacityHours.get(capacity)
                        : Short.valueOf((short) 0);
                periodTotals.put(capacity, Short.valueOf((short) (totalHours.shortValue() + periodHours.shortValue())));
            }
        }

        period.put("groupProportion", periodDto.getGroupProportion());
        period.put("subgroups", periodDto.getSubgroups() != null ? periodDto.getSubgroups().size() : 0);
        period.put("coefficient", periodDto.getCoefficient());
        period.put("hours", periodCapacityHours);

        return period;
    }

    public List<SubjectStudyPeriodDto> teacherSubjectStudyPeriodDtos(AutocompleteResult subjectDto,
            SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriodDto> periodDtos = StreamUtil.toFilteredList(
                sp -> sp.getSubject().equals(subjectDto.getId()), container.getSubjectStudyPeriodDtos());
        if (container.getTeacher() != null) {
            List<SubjectStudyPeriodDto> teacherPeriodDtos = new ArrayList<>();
            for (SubjectStudyPeriodDto dto : periodDtos) {
                if (Boolean.TRUE.equals(dto.getCapacityDiff())) {
                    SubjectStudyPeriodTeacherDto teacherDto = StreamUtil.nullSafeList(dto.getTeachers()).stream()
                            .filter(t -> container.getTeacher().equals(t.getTeacherId())).findFirst().get();
                    dto.setCapacities(teacherDto.getCapacities());
                }
                teacherPeriodDtos.add(dto);
            }
            return teacherPeriodDtos;
        }
        return periodDtos;
    }

    public Map<Long, Map<Long, Integer>> subjectStudyPeriodTeacherPlannedLoads(List<SubjectStudyPeriod> subjectStudyPeriods) {
        Map<Long, Map<Long, Integer>> teacherPlannedLoads = new HashMap<>();

        Set<Long> subjectStudyPeriodIds = StreamUtil.toMappedSet(ssp -> EntityUtil.getId(ssp.getStudyPeriod()),
                subjectStudyPeriods);
        Set<Long> teacherIds = StreamUtil.nullSafeList(subjectStudyPeriods).stream()
                .flatMap(ssp -> ssp.getTeachers().stream().map(t -> EntityUtil.getId(t.getTeacher())))
                .collect(Collectors.toSet());

        if (!teacherIds.isEmpty()) {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_teacher sspt"
                    + " join subject_study_period ssp on ssp.id = sspt.subject_study_period_id"
                    + " join teacher t on sspt.teacher_id = t.id"
                    + " join study_period sp on ssp.study_period_id = sp.id");
            qb.requiredCriteria("ssp.study_period_id in (:subjectStudyPeriodIds)", "subjectStudyPeriodIds", subjectStudyPeriodIds);
            qb.requiredCriteria("sspt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);

            qb.groupBy("sspt.teacher_id, ssp.study_period_id, sp.study_year_id, t.is_study_period_schedule_load");
            List<?> data = qb.select("ssp.study_period_id, sspt.teacher_id," +
                    " coalesce((select sum(sspc2.hours)" +
                        " from subject_study_period_teacher sspt2" +
                        " join subject_study_period ssp2 on sspt2.subject_study_period_id = ssp2.id" +
                        " join study_period sp2 on ssp2.study_period_id = sp2.id" +
                        " join subject_study_period_capacity sspc2 on ssp2.id = sspc2.subject_study_period_id" +
                        " where sspt2.teacher_id = sspt.teacher_id" +
                            " and (ssp2.is_capacity_diff is null or ssp2.is_capacity_diff is false)" +
                            " and (case when t.is_study_period_schedule_load = true" +
                                " then ssp.study_period_id = ssp2.study_period_id" +
                                " else sp.study_year_id = sp2.study_year_id end)" +
                        " group by sspt2.teacher_id), 0) +" +
                    " coalesce((select sum(ssptc2.hours)" +
                        " from subject_study_period_teacher sspt2" +
                        " join subject_study_period ssp2 on sspt2.subject_study_period_id = ssp2.id" +
                        " join study_period sp2 on ssp2.study_period_id = sp2.id" +
                        " join subject_study_period_capacity sspc2 on ssp2.id = sspc2.subject_study_period_id" +
                        " join (" + SQL_SELECT_TEACHER_CAPACITY + ") ssptc2" +
                            " on ssptc2.subject_study_period_capacity_id = sspc2.id and" +
                            " ssptc2.subject_study_period_teacher_id = sspt2.id" +
                        " where sspt2.teacher_id = sspt.teacher_id" +
                            " and ssp2.is_capacity_diff = true" +
                            " and (case when t.is_study_period_schedule_load = true" +
                                " then ssp.study_period_id = ssp2.study_period_id" +
                                " else sp.study_year_id = sp2.study_year_id end)" +
                        " group by sspt2.teacher_id), 0) +" +
                    " coalesce((select sum(jc.hours)" +
                        " from journal_teacher jt" +
                        " join journal j on j.id = jt.journal_id" +
                        " join journal_capacity jc on j.id = jc.journal_id" +
                        " where (j.is_capacity_diff is null or j.is_capacity_diff = false)" +
                            " and jt.teacher_id = sspt.teacher_id" +
                            " and (case when t.is_study_period_schedule_load = true" +
                                " then ssp.study_period_id = jc.study_period_id" +
                                " else sp.study_year_id = j.study_year_id end)" +
                        " group by jt.teacher_id), 0) +" +
                    " coalesce((select sum(jtc.hours)" +
                        " from journal_teacher jt2" +
                        " join journal j2 on j2.id = jt2.journal_id" +
                        " join journal_teacher_capacity jtc on jt2.id = jtc.journal_teacher_id" +
                        " where j2.is_capacity_diff = true" +
                            " and sspt.teacher_id = jt2.teacher_id" +
                            " and (case when t.is_study_period_schedule_load = true" +
                                " then ssp.study_period_id = jtc.study_period_id" +
                                " else sp.study_year_id = j2.study_year_id end)" +
                        " group by jt2.teacher_id), 0) as hours", em).getResultList();

            teacherPlannedLoads = StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(
                    r -> resultAsLong(r, 0), Collectors.toMap(r -> resultAsLong(r, 1), r -> resultAsInteger(r, 2))));
        }
        return teacherPlannedLoads;
    }
}
