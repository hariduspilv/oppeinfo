package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.schoolcapacity.SchoolCapacityType;
import ee.hitsa.ois.domain.schoolcapacity.SchoolCapacityTypeLoad;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeForm;
import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeForms;
import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeLoadForm;
import ee.hitsa.ois.web.commandobject.schoolcapacity.SchoolCapacityTypeLoadForms;
import ee.hitsa.ois.web.dto.schoolcapacity.SchoolCapacityTypeDto;

@Service
@Transactional
public class SchoolCapacityTypeService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierService classifierService;

    public List<SchoolCapacityTypeDto> get(HoisUserDetails user, Boolean isHigher) {
        List<Classifier> capacityClassifiers = classifierService.findAllByMainClassCode(MainClassCode.MAHT).stream()
                .sorted(Comparator.comparing(EntityUtil::getCode, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        Map<String, SchoolCapacityType> typeMap = getSchoolCapacitiesByTypeCode(user, isHigher);
        return StreamUtil.toMappedList(capacityClassifier -> {
            String typeCode = EntityUtil.getCode(capacityClassifier);
            SchoolCapacityType capacityType = typeMap.get(typeCode);
            SchoolCapacityTypeDto dto = new SchoolCapacityTypeDto();
            dto.setTypeCode(typeCode);
            if (capacityType == null) {
                dto.setIsUsable(Boolean.FALSE);
                dto.setIsTimetable(Boolean.FALSE);
                dto.setLoads(new ArrayList<>());
            } else {
                dto.setId(EntityUtil.getId(capacityType));
                dto.setIsUsable(capacityType.getIsUsable());
                dto.setIsTimetable(capacityType.getIsTimetable());
                dto.setLoads(StreamUtil.toMappedList(load -> {
                    SchoolCapacityTypeLoadForm loadDto = new SchoolCapacityTypeLoadForm();
                    loadDto.setStudyYearId(EntityUtil.getId(load.getStudyYear()));
                    loadDto.setLoadPercentage(load.getLoadPercentage());
                    return loadDto;
                }, capacityType.getTypeLoads()));
            }
            return dto;
        }, capacityClassifiers);
    }

    public void save(HoisUserDetails user, SchoolCapacityTypeForms form) {
        School school = em.getReference(School.class, user.getSchoolId());
        Map<String, SchoolCapacityType> typeMap = getSchoolCapacitiesByTypeCode(user, form.getIsHigher());
        for (SchoolCapacityTypeForm capacity : form.getCapacities()) {
            SchoolCapacityType capacityType = typeMap.get(capacity.getTypeCode());
            if (capacityType == null) {
                capacityType = new SchoolCapacityType();
                capacityType.setSchool(school);
                capacityType.setCapacityType(em.getReference(Classifier.class, capacity.getTypeCode()));
                capacityType.setIsHigher(form.getIsHigher());
            }
            capacityType.setIsUsable(capacity.getIsUsable());
            capacityType.setIsTimetable(capacity.getIsTimetable());
            EntityUtil.save(capacityType, em);
        }
    }

    public void saveLoads(HoisUserDetails user, SchoolCapacityTypeLoadForms form) {
        SchoolCapacityType capacityType = capacityType(user, form.getId());
        Map<Long, SchoolCapacityTypeLoad> loadByStudyYear = StreamUtil.toMap(load -> EntityUtil.getId(load.getStudyYear()), 
                capacityType.getTypeLoads());
        for (SchoolCapacityTypeLoadForm loadForm : form.getLoads()) {
            SchoolCapacityTypeLoad load = loadByStudyYear.get(loadForm.getStudyYearId());
            Integer percentage = loadForm.getLoadPercentage();
            if (percentage != null) {
                if (load == null) {
                    load = new SchoolCapacityTypeLoad();
                    load.setSchoolCapacityType(capacityType);
                    load.setStudyYear(studyYear(user, loadForm.getStudyYearId()));
                }
                load.setLoadPercentage(percentage);
                EntityUtil.save(load, em);
            } else {
                if (load != null) {
                    EntityUtil.deleteEntity(load, em);
                }
            }
        }
    }

    private SchoolCapacityType capacityType(HoisUserDetails user, Long id) {
        SchoolCapacityType capacityType = em.getReference(SchoolCapacityType.class, id);
        UserUtil.assertSameSchool(user, capacityType.getSchool());
        return capacityType;
    }

    private StudyYear studyYear(HoisUserDetails user, Long id) {
        StudyYear studyYear = em.getReference(StudyYear.class, id);
        UserUtil.assertSameSchool(user, studyYear.getSchool());
        return studyYear;
    }

    private Map<String, SchoolCapacityType> getSchoolCapacitiesByTypeCode(HoisUserDetails user, Boolean isHigher) {
        List<SchoolCapacityType> schoolCapacities = em.createQuery("select sct from SchoolCapacityType sct"
                + " where sct.school.id = ?1 and sct.isHigher = ?2", 
                SchoolCapacityType.class)
                .setParameter(1, user.getSchoolId())
                .setParameter(2, isHigher)
                .getResultList();
        return StreamUtil.toMap(sct -> EntityUtil.getCode(sct.getCapacityType()), schoolCapacities);
    }

}
