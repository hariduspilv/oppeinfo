package ee.hitsa.ois.service.curriculum;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleCompetence;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupationSpeciality;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

@Transactional
@Service
public class CurriculumCopyService {

    @Autowired
    private EntityManager em;
    @Autowired
    private CurriculumService curriculumService;

    public Curriculum copyCurriculum(HoisUserDetails user, Curriculum copiedCurriculum) {
        Curriculum newCurriculum = new Curriculum(); 
        BeanUtils.copyProperties
        (copiedCurriculum, newCurriculum, "id", "school", "inserted", "insertedBy", "changed", "changedBy", "version", 
                "ehisStatus", "ehisChanged", "higher", "code", "merCode", "validFrom", "validThru", "stateCurriculum", 
                "studyLanguages", "departments", "files", "grades", "jointPartners", "specialities", 
                "studyForms", "modules", "occupations", "versions");

        String oldCode = copiedCurriculum.getCode() != null ? copiedCurriculum.getCode() : "";
        newCurriculum.setCode(oldCode + " COPY");
        newCurriculum.setSchool(em.getReference(School.class, user.getSchoolId()));
        newCurriculum.setHigher(Boolean.FALSE);
        newCurriculum.setStatus(em.getReference(Classifier.class, CurriculumStatus.OPPEKAVA_STAATUS_S.name()));
        newCurriculum.setDraft(em.getReference(Classifier.class, CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_KOOL.name()));
        newCurriculum.setValidFrom(LocalDate.now());

        curriculumService.updateLanguages(newCurriculum, StreamUtil.toMappedSet(l -> EntityUtil.getCode(l.getStudyLang()), copiedCurriculum.getStudyLanguages()));
        curriculumService.updateStudyForms(newCurriculum, StreamUtil.toMappedSet(sf -> EntityUtil.getCode(sf.getStudyForm()), copiedCurriculum.getStudyForms()));
        curriculumService.updateDepartments(newCurriculum, StreamUtil.toMappedSet(d -> EntityUtil.getId(d.getSchoolDepartment()), copiedCurriculum.getDepartments()));

        copyJointPartners(newCurriculum, copiedCurriculum.getJointPartners());
        copyOccupations(newCurriculum, copiedCurriculum.getOccupations());
        copyModules(newCurriculum, copiedCurriculum.getModules());

        return EntityUtil.save(newCurriculum, em);
    }

    private static void copyJointPartners(Curriculum newCurriculum, Set<CurriculumJointPartner> jointPartners) {
        if(jointPartners != null) {
            for(CurriculumJointPartner copied : jointPartners) {
                CurriculumJointPartner newJointPartner = new CurriculumJointPartner(); 
                BeanUtils.copyProperties(copied, newJointPartner, "id", "changed", "changedBy", "inserted", "insertedBy", "curriculum", "version");
                newJointPartner.setCurriculum(newCurriculum);
                newCurriculum.getJointPartners().add(newJointPartner);
            }
        }
    }

    private static void copyOccupations(Curriculum newCurriculum, Set<CurriculumOccupation> occupations) {
        if(occupations != null) {
            for(CurriculumOccupation copied : occupations) {
                CurriculumOccupation newOccupation = new CurriculumOccupation();
                BeanUtils.copyProperties(copied, newOccupation, "id", "changed", "changedBy", "inserted", "insertedBy", "curriculum", "version", "specialities");
                newOccupation.setCurriculum(newCurriculum);
                copySpecialities(newOccupation, copied.getSpecialities());
                newCurriculum.getOccupations().add(newOccupation);
            }
        }
    }

    private static void copySpecialities(CurriculumOccupation newOccupation,
            Set<CurriculumOccupationSpeciality> specialities) {
        if(specialities != null) {
            for(CurriculumOccupationSpeciality copied : specialities) {
                CurriculumOccupationSpeciality newSpec = new CurriculumOccupationSpeciality();
                newSpec.setSpeciality(copied.getSpeciality());
                newOccupation.getSpecialities().add(newSpec);
            }
        }
    }
    

    private static void copyModules(Curriculum newCurriculum, Set<CurriculumModule> modules) {
        if(modules != null) {
            for(CurriculumModule copied : modules) {
                
                CurriculumModule newModule = new CurriculumModule();
                BeanUtils.copyProperties(copied, newModule, 
                        "id", "changed", "changedBy", "inserted", "insertedBy", "version", 
                        "curriculum", "occupations", "competences", "outcomes", "curriculumVersionOccupationModules");
                newModule.setCurriculum(newCurriculum);
                
                copyOccupations(newModule, copied.getOccupations());
                copyOutcomes(newModule, copied.getOutcomes());
                copyCompetences(newModule, copied.getCompetences());
                
                newCurriculum.getModules().add(newModule);
            }
        }
    }

    private static void copyOccupations(CurriculumModule newModule, Set<CurriculumModuleOccupation> occupations) {
        if(occupations != null) {
            for(CurriculumModuleOccupation copied : occupations) {
                CurriculumModuleOccupation newOccupation = new CurriculumModuleOccupation();
                newOccupation.setOccupation(copied.getOccupation());
                newModule.getOccupations().add(newOccupation);
            }
        }
    }

    private static void copyOutcomes(CurriculumModule newModule, Set<CurriculumModuleOutcome> outcomes) {
        if(outcomes != null) {
            for(CurriculumModuleOutcome copied : outcomes) {
                CurriculumModuleOutcome newOutcome = new CurriculumModuleOutcome();
                newOutcome.setOutcomeEt(copied.getOutcomeEt());
                newOutcome.setOutcomeEn(copied.getOutcomeEn());
                newOutcome.setOrderNr(copied.getOrderNr());
                newModule.getOutcomes().add(newOutcome);
            }
        }
    }

    private static void copyCompetences(CurriculumModule newModule, Set<CurriculumModuleCompetence> competences) {
        if(competences != null) {
            for(CurriculumModuleCompetence copied : competences) {
                CurriculumModuleCompetence newCompetence = new CurriculumModuleCompetence();
                newCompetence.setCompetence(copied.getCompetence());
                newModule.getCompetences().add(newCompetence);
            }
        }
    }
}
