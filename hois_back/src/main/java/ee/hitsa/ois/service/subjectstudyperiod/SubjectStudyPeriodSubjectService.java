package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.getOrDefault;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlan;
import ee.hitsa.ois.enums.Coefficient;
import ee.hitsa.ois.service.SubjectStudyPeriodPlanService;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSubgroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.service.XlsService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodTeacherDto;

@Transactional
@Service
public class SubjectStudyPeriodSubjectService {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private SubjectStudyPeriodCapacitiesService subjectStudyPeriodCapacitiesService;
    @Autowired
    private XlsService xlsService;

    public void setSubjectStudyPeriodsToSubjectsContainer(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriod> ssps = subjectStudyPeriodRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("studyPeriod").get("id"), container.getStudyPeriod()));
            filters.add(cb.equal(root.get("studyPeriod").get("studyYear").get("school").get("id"), schoolId));
            filters.add(cb.equal(root.get("subject").get("id"), container.getSubject()));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        Map<Long, Map<Long, Integer>> teacherPlannedLoads = subjectStudyPeriodCapacitiesService
                .subjectStudyPeriodTeacherPlannedLoads(ssps);

        List<SubjectStudyPeriodDto> subjectStudyPeriodDtos = StreamUtil.toMappedList(ssp -> {
            SubjectStudyPeriodDto dto = new SubjectStudyPeriodDto();
            dto.setId(EntityUtil.getId(ssp));
            dto.setSubject(EntityUtil.getId(ssp.getSubject()));
            dto.setStudentGroupObjects(
                    StreamUtil.toMappedList(s -> AutocompleteResult.of(s.getStudentGroup()), ssp.getStudentGroups()));
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, ssp.getCapacities()));
            dto.setGroupProportion(EntityUtil.getCode(ssp.getGroupProportion()));
            Map<Long, Integer> spPlannedLoads = teacherPlannedLoads.get(EntityUtil.getId(ssp.getStudyPeriod()));
            dto.setTeachers(StreamUtil.toMappedList(
                    t -> SubjectStudyPeriodTeacherDto.of(t,
                            spPlannedLoads != null ? spPlannedLoads.get(EntityUtil.getId(t.getTeacher())) : null),
                    ssp.getTeachers()));
            dto.setCapacityDiff(ssp.getCapacityDiff());
            dto.setCoefficient(getOrDefault(EntityUtil.getNullableCode(ssp.getCoefficient()),
                    Coefficient.KOEFITSIENT_K1.name()));
            dto.setSubgroups(StreamUtil.toMappedSet(SubjectStudyPeriodSubgroupDto::of, ssp.getSubgroups()));
            return dto;
        }, ssps);
        container.setSubjectStudyPeriodDtos(subjectStudyPeriodDtos);
    }

    public List<AutocompleteResult> getSubjectsList(Long schoolId, SearchCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject s");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code = :status", "status", SubjectStatus.AINESTAATUS_K);
        if (command != null) {
            qb.optionalContains("s.name_et", "name", command.getName());
            qb.optionalCriteria("not exists " 
                    + "(select * from subject_study_period ssp "
                    + " where ssp.study_period_id = :studyPeriodId and ssp.subject_id = s.id)",
                       "studyPeriodId", command.getId());
        }

        List<?> data = qb.select("s.id, s.code, s.name_et, s.name_en, s.credits", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 1);
            BigDecimal credits = resultAsDecimal(r, 4);
            String nameEt = SubjectUtil.subjectName(code, resultAsString(r, 2), credits);
            String nameEn = SubjectUtil.subjectName(code, resultAsString(r, 3), credits);
            return new AutocompleteResult(resultAsLong(r, 0), nameEt, nameEn);
        }, data);
    }

    public byte[] subjectStudyPeriodSubjectAsExcel(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        setSubjectStudyPeriodsToSubjectsContainer(schoolId, container);
        setSubjectStudyPeriodsPlanToSubjectContainer(container);
        List<Classifier> capacities = subjectStudyPeriodCapacitiesService.capacityClassifiers(schoolId, container);
        List<String> capacityCodes = StreamUtil.toMappedList(c -> EntityUtil.getCode(c), capacities);
        
        List<Map<String, Object>> subjectStudyPeriods = new ArrayList<>();
        Map<String, Short> totals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        
        for (SubjectStudyPeriodDto studyPeriod : container.getSubjectStudyPeriodDtos()) {
            Map<String, Object> period = new HashMap<>();
            period.put("teachers", studyPeriod.getTeachers().stream().map(t -> t.getName()).collect(Collectors.joining(", ")));
            period.put("groups", studyPeriod.getStudentGroupObjects().stream().map(g -> g.getNameEt()).collect(Collectors.joining(", ")));
            period.put("groupProportion", studyPeriod.getGroupProportion());
            period.put("subgroups", studyPeriod.getSubgroups() != null ? studyPeriod.getSubgroups().size() : 0);
            
            Map<String, Short> capacityHours = subjectStudyPeriodCapacitiesService.emptyOrderedCapacityHours(capacityCodes);
            
            for (SubjectStudyPeriodCapacityDto capacityDto : studyPeriod.getCapacities()) {
                capacityHours.put(capacityDto.getCapacityType(), capacityDto.getHours());
            }
            
            studyPeriod.getCapacities().forEach(c -> capacityHours.put(c.getCapacityType(), c.getHours()));
            period.put("hours", capacityHours);
            subjectStudyPeriods.add(period);
            
            for (String capacity : capacityHours.keySet()) {
                Short totalHours = totals.get(capacity) != null ? totals.get(capacity) : Short.valueOf((short) 0);
                Short periodHours = capacityHours.get(capacity) != null ? capacityHours.get(capacity) : Short.valueOf((short) 0);
                totals.put(capacity, Short.valueOf((short) (totalHours.shortValue() + periodHours.shortValue())));
            }
        }

        Map<String, Short> planHours = null;
        if (!container.getSubjectStudyPeriodPlans().isEmpty()) {
            SubjectStudyPeriodPlanDto planDto = container.getSubjectStudyPeriodPlans().get(0);

            planHours = planDto.getCapacities().stream()
                    .filter(pc -> pc.getHours() != null)
                    .collect(Collectors.toMap(SubjectStudyPeriodPlanCapacityDto::getCapacityType,
                            SubjectStudyPeriodPlanCapacityDto::getHours));
        }
        
        
        Map<String, Object> data = new HashMap<>();
        StudyPeriod studyPeriod = em.getReference(StudyPeriod.class, container.getStudyPeriod());
        
        data.put("studyYear", AutocompleteResult.of(studyPeriod.getStudyYear()));
        data.put("studyPeriod", AutocompleteResult.of(studyPeriod));
        data.put("subject", AutocompleteResult.of(em.getReference(Subject.class, container.getSubject())));
        data.put("capacities", capacities);
        data.put("subjectStudyPeriods", subjectStudyPeriods);
        data.put("totals", totals);
        data.put("plan", planHours);

        return xlsService.generate("subjectstudyperiodsubject.xls", data);
    }

    public void setSubjectStudyPeriodsPlanToSubjectContainer(SubjectStudyPeriodDtoContainer container) {
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
}
