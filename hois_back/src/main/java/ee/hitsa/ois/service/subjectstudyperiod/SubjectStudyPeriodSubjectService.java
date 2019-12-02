package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

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
        Map<Long, Map<Long, Short>> teacherPlannedLoads = subjectStudyPeriodCapacitiesService
                .subjectStudyPeriodTeacherPlannedLoads(ssps);

        List<SubjectStudyPeriodDto> subjectStudyPeriodDtos = StreamUtil.toMappedList(ssp -> {
            SubjectStudyPeriodDto dto = new SubjectStudyPeriodDto();
            dto.setId(EntityUtil.getId(ssp));
            dto.setSubject(EntityUtil.getId(ssp.getSubject()));
            dto.setStudentGroupObjects(
                    StreamUtil.toMappedList(s -> AutocompleteResult.of(s.getStudentGroup()), ssp.getStudentGroups()));
            dto.setCapacities(StreamUtil.toMappedList(SubjectStudyPeriodCapacityDto::of, ssp.getCapacities()));
            dto.setGroupProportion(EntityUtil.getCode(ssp.getGroupProportion()));
            Map<Long, Short> spPlannedLoads = teacherPlannedLoads.get(EntityUtil.getId(ssp.getStudyPeriod()));
            dto.setTeachers(StreamUtil.toMappedList(
                    t -> SubjectStudyPeriodTeacherDto.of(t,
                            spPlannedLoads != null ? spPlannedLoads.get(EntityUtil.getId(t.getTeacher())) : null),
                    ssp.getTeachers()));
            dto.setCapacityDiff(ssp.getCapacityDiff());
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
        List<Classifier> capacities = subjectStudyPeriodCapacitiesService.capacityClassifiers(schoolId, container);
        List<String> capacityCodes = StreamUtil.toMappedList(c -> EntityUtil.getCode(c), capacities);
        
        List<Map<String, Object>> subjectStudyPeriods = new ArrayList<>();
        Map<String, Short> totals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        
        for (SubjectStudyPeriodDto studyPeriod : container.getSubjectStudyPeriodDtos()) {
            Map<String, Object> period = new HashMap<>();
            period.put("teachers", studyPeriod.getTeachers().stream().map(t -> t.getName()).collect(Collectors.joining(", ")));
            period.put("groups", studyPeriod.getStudentGroupObjects().stream().map(g -> g.getNameEt()).collect(Collectors.joining(", ")));
            period.put("groupProportion", studyPeriod.getGroupProportion());
            
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
        
        
        Map<String, Object> data = new HashMap<>();
        StudyPeriod studyPeriod = em.getReference(StudyPeriod.class, container.getStudyPeriod());
        
        data.put("studyYear", AutocompleteResult.of(studyPeriod.getStudyYear()));
        data.put("studyPeriod", AutocompleteResult.of(studyPeriod));
        data.put("subject", AutocompleteResult.of(em.getReference(Subject.class, container.getSubject())));
        data.put("capacities", capacities);
        data.put("subjectStudyPeriods", subjectStudyPeriods);
        data.put("totals", totals);

        return xlsService.generate("subjectstudyperiodsubject.xls", data);
    }
}
