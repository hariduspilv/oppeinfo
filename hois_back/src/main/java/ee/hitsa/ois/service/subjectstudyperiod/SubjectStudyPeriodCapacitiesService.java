package ee.hitsa.ois.service.subjectstudyperiod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.SubjectService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;

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

            List<SubjectStudyPeriodCapacityDto> newCapacities = StreamUtil.toFilteredList(c -> c.getHours() != null, dto.getCapacities());
            EntityUtil.bindEntityCollection(ssp.getCapacities(), SubjectStudyPeriodCapacity::getId, newCapacities,
                    SubjectStudyPeriodCapacityDto::getId, dto3 -> {
                        SubjectStudyPeriodCapacity newCapacity = EntityUtil.bindToEntity(dto3,
                                new SubjectStudyPeriodCapacity(), classifierRepository);
                        newCapacity.setSubjectStudyPeriod(ssp);
                        return newCapacity;
                    }, (dto2, c) -> {
                        c.setHours(dto2.getHours());
                    });
            ssps.add(ssp);
        }
        subjectStudyPeriodRepository.save(ssps);
    }
    
    public List<Classifier> capacityClassifiers() {
        List<Classifier> capacities = classifierService.findAllByMainClassCode(MainClassCode.MAHT);
        capacities.sort(Comparator.comparing(Classifier::getCode, String.CASE_INSENSITIVE_ORDER));
        return capacities;
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
            Short totalHours = periodTotals.get(capacity) != null ? periodTotals.get(capacity) : Short.valueOf((short) 0);
            if (!periodTotals.containsKey(capacity)) {
                periodTotals.put(capacity, totalHours);
            } else {
                Short periodHours = periodCapacityHours.get(capacity) != null ? periodCapacityHours.get(capacity) : Short.valueOf((short) 0);
                periodTotals.put(capacity, Short.valueOf((short) (totalHours.shortValue() + periodHours.shortValue())));
            }
        }
        
        period.put("groupProportion", periodDto.getGroupProportion());
        period.put("hours", periodCapacityHours);
        
        return period;
    }
}
