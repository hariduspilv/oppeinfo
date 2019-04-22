package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
        periodDto.getCapacities().forEach(c -> periodCapacityHours.put(c.getCapacityType(), c.getHours()));

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

    public Map<Long, Map<Long, Short>> subjectStudyPeriodTeacherPlannedLoads(List<SubjectStudyPeriod> subjectStudyPeriods) {
        Map<Long, Map<Long, Short>> teacherPlannedLoads = new HashMap<>();

        Set<Long> subjectStudyPeriodIds = StreamUtil.toMappedSet(ssp -> EntityUtil.getId(ssp.getStudyPeriod()),
                subjectStudyPeriods);
        Set<Long> teacherIds = StreamUtil.nullSafeList(subjectStudyPeriods).stream()
                .flatMap(ssp -> ssp.getTeachers().stream().map(t -> EntityUtil.getId(t.getTeacher())))
                .collect(Collectors.toSet());

        if (!teacherIds.isEmpty()) {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_teacher sspt"
                    + " join subject_study_period ssp on ssp.id = sspt.subject_study_period_id"
                    + " join subject_study_period_capacity ssppc on ssppc.subject_study_period_id = ssp.id"
                    + " left join subject_study_period_teacher_capacity ssptc on ssptc.subject_study_period_capacity_id = ssppc.id"
                    + " and ssptc.subject_study_period_teacher_id = sspt.id");
            qb.requiredCriteria("ssp.study_period_id in (:subjectStudyPeriodIds)", "subjectStudyPeriodIds", subjectStudyPeriodIds);
            qb.requiredCriteria("sspt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);

            qb.groupBy("sspt.teacher_id, ssp.study_period_id");
            List<?> data = qb.select("ssp.study_period_id, sspt.teacher_id,"
                    + " coalesce(sum(case when ssp.is_capacity_diff is null or ssp.is_capacity_diff is false then ssppc.hours end), 0) +"
                    + " coalesce(sum(case when ssp.is_capacity_diff then ssptc.hours end), 0)", em).getResultList();

            teacherPlannedLoads = StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(
                    r -> resultAsLong(r, 0), Collectors.toMap(r -> resultAsLong(r, 1), r -> resultAsShort(r, 2))));
        }
        return teacherPlannedLoads;
    }
}
