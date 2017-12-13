package ee.hitsa.ois.service.curriculum;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionElectiveModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleThemeCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleYearCapacity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionSpeciality;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.util.EntityUtil;

@Transactional
@Service
public class CurriculumVersionCopyService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;

    public CurriculumVersion copy(CurriculumVersion copied) {
        CurriculumVersion newCurriculumVersion = new CurriculumVersion();
        BeanUtils.copyProperties(copied, newCurriculumVersion, "id", "created", "createdBy", "changed", "changedBy",
                "version", "validFrom", "validThru", "modules", "specialities", "occupationModules", "status", "code");

        newCurriculumVersion
                .setStatus(classifierRepository.findOne(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_S.name()));
        newCurriculumVersion.setCode(copied.getCode() + " COPIED");
        newCurriculumVersion.setValidFrom(LocalDate.now());
        copySpecialities(newCurriculumVersion, copied.getSpecialities());
        copyHigherModules(newCurriculumVersion, copied.getModules());
        copyOccupationModules(newCurriculumVersion, copied.getOccupationModules());
        
        return curriculumVersionRepository.save(newCurriculumVersion);
    }

    private static void copyOccupationModules(CurriculumVersion newCurriculumVersion,
            Set<CurriculumVersionOccupationModule> occupationModules) {
        if(!CollectionUtils.isEmpty(occupationModules)) {
            newCurriculumVersion.setOccupationModules(new HashSet<>());
            
            for(CurriculumVersionOccupationModule copied : occupationModules) {
                CurriculumVersionOccupationModule newModule = new CurriculumVersionOccupationModule();
                BeanUtils.copyProperties(copied, newModule, "id", "created", "createdBy", "changed", "changedBy",
                        "version", "capacities", "themes", "yearCapacities", "curriculumVersion");
                newModule.setCurriculumVersion(newCurriculumVersion);
                
                copyThemes(newModule, copied.getThemes());
                copyCapacities(newModule, copied.getCapacities());
                copyYearCapacities(newModule, copied.getYearCapacities());
                newCurriculumVersion.getOccupationModules().add(newModule);
            }
        }
    }


    private static void copyCapacities(CurriculumVersionOccupationModule newModule,
            Set<CurriculumVersionOccupationModuleCapacity> capacities) {
        if(!CollectionUtils.isEmpty(capacities)) {
            newModule.setCapacities(new HashSet<>());
            
            for(CurriculumVersionOccupationModuleCapacity copied : capacities) {
                CurriculumVersionOccupationModuleCapacity newCapacity 
                = new CurriculumVersionOccupationModuleCapacity();
                newCapacity.setModule(newModule);
                newCapacity.setCapacityType(copied.getCapacityType());
                newCapacity.setHours(copied.getHours());
                newCapacity.setContact(copied.getContact());
                
                newModule.getCapacities().add(newCapacity);
            }
        }
    }
    

    private static void copyYearCapacities(CurriculumVersionOccupationModule newModule,
            Set<CurriculumVersionOccupationModuleYearCapacity> yearCapacities) {

        if(!CollectionUtils.isEmpty(yearCapacities)) {
            newModule.setYearCapacities(new HashSet<>());
            
            for(CurriculumVersionOccupationModuleYearCapacity copied : yearCapacities) {
                CurriculumVersionOccupationModuleYearCapacity newYearCapacity = 
                        new CurriculumVersionOccupationModuleYearCapacity();
                newYearCapacity.setModule(newModule);
                newYearCapacity.setStudyYearNumber(copied.getStudyYearNumber());
                newYearCapacity.setCredits(copied.getCredits());
                
                newModule.getYearCapacities().add(newYearCapacity);
            }
        }
    }

    private static void copyThemes(CurriculumVersionOccupationModule newModule,
            Set<CurriculumVersionOccupationModuleTheme> themes) {
        if(!CollectionUtils.isEmpty(themes)) {
            newModule.setThemes(new HashSet<>());
            
            for(CurriculumVersionOccupationModuleTheme copied : themes) {
                CurriculumVersionOccupationModuleTheme newTheme = new CurriculumVersionOccupationModuleTheme();
                // BeanUtils.copyProperties() did not work for unknown reason
                EntityUtil.bindToEntity(copied, newTheme, "id", "created", "createdBy", "changed", "changedBy",
                        "version", "capacities", "module", "outcomes", "assessment");
                newTheme.setAssessment(copied.getAssessment());
                newTheme.setModule(newModule);
                copyThemeCapacities(newTheme, copied.getCapacities());
                copyThemeOutcomes(newTheme, copied.getOutcomes());
                newModule.getThemes().add(newTheme);
            }
        }
    }

    private static void copyThemeOutcomes(CurriculumVersionOccupationModuleTheme newTheme,
            Set<CurriculumVersionOccupationModuleOutcome> outcomes) {
        if(!CollectionUtils.isEmpty(outcomes)) {
            newTheme.setOutcomes(new HashSet<>());
            for(CurriculumVersionOccupationModuleOutcome copied : outcomes) {
                CurriculumVersionOccupationModuleOutcome newOutcome = new CurriculumVersionOccupationModuleOutcome();
                newOutcome.setOutcome(copied.getOutcome());
                newTheme.getOutcomes().add(newOutcome);
            }
        }
    }

    private static void copyThemeCapacities(CurriculumVersionOccupationModuleTheme newTheme,
            Set<CurriculumVersionOccupationModuleThemeCapacity> capacities) {
        if(!CollectionUtils.isEmpty(capacities)) {
            newTheme.setCapacities(new HashSet<>());
            
            for(CurriculumVersionOccupationModuleThemeCapacity copied : capacities) {
                CurriculumVersionOccupationModuleThemeCapacity newCapacity 
                = new CurriculumVersionOccupationModuleThemeCapacity();
                newCapacity.setTheme(newTheme);
                newCapacity.setCapacityType(copied.getCapacityType());
                newCapacity.setHours(copied.getHours());
                newCapacity.setContact(copied.getContact());
                
                newTheme.getCapacities().add(newCapacity);
            }
        }
    }

    private static void copySpecialities(CurriculumVersion newCurriculumVersion,
            Set<CurriculumVersionSpeciality> specialities) {

        if (!CollectionUtils.isEmpty(specialities)) {
            newCurriculumVersion.setSpecialities(new HashSet<>());

            for (CurriculumVersionSpeciality copied : specialities) {
                CurriculumVersionSpeciality newSpec = new CurriculumVersionSpeciality();
                newSpec.setCurriculumVersion(newCurriculumVersion);
                newSpec.setCurriculumSpeciality(copied.getCurriculumSpeciality());
                newCurriculumVersion.getSpecialities().add(newSpec);
            }
        }
    }

    private static void copyHigherModules(CurriculumVersion newCurriculumVersion, Set<CurriculumVersionHigherModule> modules) {
        if (!CollectionUtils.isEmpty(modules)) {
            newCurriculumVersion.setModules(new HashSet<>());
            for (CurriculumVersionHigherModule copied : modules) {
                CurriculumVersionHigherModule newModule = new CurriculumVersionHigherModule();
                BeanUtils.copyProperties(copied, newModule, "id", "created", "createdBy", "changed", "changedBy",
                        "version", "electiveModules", "subjects", "curriculumVersion", "specialities");

                newModule.setCurriculumVersion(newCurriculumVersion);
                copyElectiveModules(newModule, copied.getElectiveModules());
                copyModuleSubjects(newModule, copied.getSubjects());
                copyModuleSpecialities(newModule, copied.getSpecialities());
                newCurriculumVersion.getModules().add(newModule);
            }
        }
    }

    private static void copyElectiveModules(CurriculumVersionHigherModule newModule,
            Set<CurriculumVersionElectiveModule> copiedElectiveModules) {
        if (!CollectionUtils.isEmpty(copiedElectiveModules)) {
            newModule.setElectiveModules(new HashSet<>());
            for (CurriculumVersionElectiveModule copied : copiedElectiveModules) {
                CurriculumVersionElectiveModule newElective = new CurriculumVersionElectiveModule();
                BeanUtils.copyProperties(copied, newElective, "id", "created", "createdBy", "changed",
                        "changedBy", "version", "subjects");
                newElective.setReferenceNumber(copied.getReferenceNumber());
                newElective.setSubjects(new HashSet<>());
                newModule.getElectiveModules().add(newElective);
            }            
        }
    }

    private static void copyModuleSubjects(CurriculumVersionHigherModule newModule,
            Set<CurriculumVersionHigherModuleSubject> subjects) {
        if (!CollectionUtils.isEmpty(subjects)) {
            newModule.setSubjects(new HashSet<>());
            for (CurriculumVersionHigherModuleSubject copiedSubject : subjects) {
                CurriculumVersionHigherModuleSubject newSubject = new CurriculumVersionHigherModuleSubject();
                BeanUtils.copyProperties(copiedSubject, newSubject, "id", "created", "createdBy", "changed",
                        "changedBy", "electiveModule", "module");
                setElectiveModule(newModule, newSubject, copiedSubject);
                newSubject.setModule(newModule);
                newModule.getSubjects().add(newSubject);
            }
        }
    }
    
    private static void setElectiveModule(CurriculumVersionHigherModule newModule,
            CurriculumVersionHigherModuleSubject newSubject, CurriculumVersionHigherModuleSubject copiedSubject) {
        if(copiedSubject.getElectiveModule() == null) {
            return;
        }
        Optional<CurriculumVersionElectiveModule> o = newModule.getElectiveModules().stream()
                .filter(e -> e.getReferenceNumber()
                        .equals(EntityUtil.getId(copiedSubject.getElectiveModule()))).findFirst();
        if(o.isPresent()) {
            CurriculumVersionElectiveModule elective = o.get();
            elective.getSubjects().add(newSubject);
            newSubject.setElectiveModule(elective);
        }
    }

    private static void copyModuleSpecialities(CurriculumVersionHigherModule newModule,
            Set<CurriculumVersionHigherModuleSpeciality> specialities) {
        if(!CollectionUtils.isEmpty(specialities)) {
            newModule.setSpecialities(new HashSet<>());
            
            for(CurriculumVersionHigherModuleSpeciality copied : specialities) {
                CurriculumVersionHigherModuleSpeciality newSpec = new CurriculumVersionHigherModuleSpeciality();
                newSpec.setModule(newModule);
                newSpec.setSpeciality(findVersionSpeciality(newModule, copied));
                newModule.getSpecialities().add(newSpec);
            }
        }
    }

    private static CurriculumVersionSpeciality findVersionSpeciality(CurriculumVersionHigherModule newModule,
            CurriculumVersionHigherModuleSpeciality copied) {
        Optional<CurriculumVersionSpeciality> spec = newModule.getCurriculumVersion().getSpecialities().stream()
        .filter(vs -> EntityUtil.getId(vs.getCurriculumSpeciality()).equals(EntityUtil.getId(copied.getSpeciality().getCurriculumSpeciality()))).findFirst();
        if(spec.isPresent()) {
            return spec.get();
        }
        return null;
    }
}
