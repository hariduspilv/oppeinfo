package ee.hitsa.ois.service.curriculum;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.basemodule.BaseModule;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleCompetence;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.BaseModuleUtil;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumModuleForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumModuleTypesCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeDto;

@Transactional
@Service
public class CurriculumModuleService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;

    public CurriculumModule create(HoisUserDetails user, CurriculumModuleForm form) {
        CurriculumModule module = new CurriculumModule();
        Curriculum curriculum = em.getReference(Curriculum.class, form.getCurriculum());
        module.setCurriculum(curriculum);
        return update(user, module, form);
    }

    public CurriculumModule update(HoisUserDetails user, CurriculumModule module, CurriculumModuleForm dto) {
        EntityUtil.setUsername(user.getUsername(), em);
        if ((dto.getBaseModule() != null && dto.getBaseModule().getId() != null) && !(module.getBaseModule() != null && module.getBaseModule().getId().equals(dto.getBaseModule().getId()))) {
            BaseModule baseModule = em.getReference(BaseModule.class, dto.getBaseModule().getId());
            module.setBaseModule(baseModule);
        }
        if (module.getBaseModule() == null) {
            EntityUtil.bindToEntity(dto, module, classifierRepository, "baseModule", "occupations", "competences", "outcomes");
            updateOutcomes(module, dto.getOutcomes());
        } else {
            EntityUtil.bindToEntity(dto, module, classifierRepository, "baseModule", "occupations", "competences", "outcomes", "nameEt", "nameEn", "credits", "objectivesEt", "objectivesEn", "assessmentsEt", "assessmentsEn");
            BaseModuleUtil.updateReferences(module.getBaseModule(), Collections.singleton(module), Collections.emptySet(), em);
        }
        updateOccupations(module, dto.getOccupations());
        updateCompetences(module, dto.getCompetences());
        return EntityUtil.save(module, em);
    }

    private void updateOccupations(CurriculumModule module, Set<String> occupations) {
        EntityUtil.bindEntityCollection(module.getOccupations(), o -> EntityUtil.getCode(o.getOccupation()), occupations, occupationCode -> {
            Classifier c = EntityUtil.validateClassifier(em.getReference(Classifier.class, occupationCode),
                    MainClassCode.OSAKUTSE, MainClassCode.KUTSE, MainClassCode.SPETSKUTSE);
            return new CurriculumModuleOccupation(c);
          });
    }

    private void updateCompetences(CurriculumModule module, Set<String> competences) {
        EntityUtil.bindEntityCollection(module.getCompetences(), c -> EntityUtil.getCode(c.getCompetence()), competences, competenceCode -> {
            return new CurriculumModuleCompetence(EntityUtil.validateClassifier(em.getReference(Classifier.class, competenceCode), MainClassCode.KOMPETENTS));
        });
    }

    private void updateOutcomes(CurriculumModule module, List<CurriculumModuleOutcomeDto> outcomes) {
        EntityUtil.bindEntityCollection(module.getOutcomes(), CurriculumModuleOutcome::getId,
            outcomes, CurriculumModuleOutcomeDto::getId,
            this::createOutcome, this::updateOutcome,
            outcome -> {
                JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_module_outcomes cmo").limit(1);
                qb.requiredCriteria("cmo.id = :outcomeId and ("
                        + "exists (select je.id from journal_entry je where je.curriculum_module_outcomes_id = cmo.id) "
                        + "or exists (select a.id from apel_application_informal_subject_or_module_outcomes a where a.curriculum_module_outcomes_id = cmo.id)"
                        + ")"
                        , "outcomeId", outcome.getId());
                if (!qb.select("cmo.id", em).getResultList().isEmpty()) {
                    throw new ValidationFailedException("module.messages.error.outcomeHasConnection");
                }
        });
    }

    private CurriculumModuleOutcome createOutcome(CurriculumModuleOutcomeDto dto) {
        return updateOutcome(dto, new CurriculumModuleOutcome());
    }

    private CurriculumModuleOutcome updateOutcome(CurriculumModuleOutcomeDto dto, CurriculumModuleOutcome outcome) {
        return EntityUtil.bindToEntity(dto, outcome);
    }

    public void delete(HoisUserDetails user, CurriculumModule module) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(module, em);
    }

    public Set<String> getPossibleModuleTypes(CurriculumModuleTypesCommand command) {
        Set<String> possibleTypes = EnumUtil.toNameSet(CurriculumModuleType.values());
        if(command.getCurriculum() != null) {
            Curriculum c = em.getReference(Curriculum.class, command.getCurriculum());

            if(ClassifierUtil.equals(CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_RIIKLIK, c.getDraft())) {
                possibleTypes.remove(CurriculumModuleType.KUTSEMOODUL_P.name());
                possibleTypes.remove(CurriculumModuleType.KUTSEMOODUL_Y.name());
            }
            // only KUTSEMOODUL_V can be added in this case
            if(!CurriculumUtil.basicDataCanBeEdited(c) && command.getModule() == null) {
                possibleTypes = new HashSet<>();
                possibleTypes.add(CurriculumModuleType.KUTSEMOODUL_V.name());
            }
            if(command.getModule() != null) {
                CurriculumModule m = em.getReference(CurriculumModule.class, command.getModule());
                possibleTypes.add(EntityUtil.getCode(m.getModule()));
            }
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from classifier c");
        qb.requiredCriteria("c.code in :possibleTypes", "possibleTypes", possibleTypes);
        List<?> result = qb.select("c.code", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsString(r, 0), result);
    }

    public List<ClassifierSelection> getCompetences(Curriculum curriculum) {
        Set<CurriculumOccupation> occupations = curriculum.getOccupations();
        List<ClassifierSelection> result = new ArrayList<>();
        for(CurriculumOccupation occupation : occupations) {
            result.addAll(StreamUtil.toMappedList(c -> ClassifierSelection.of(c.getClassifier()), 
                    occupation.getOccupation().getChildConnects()));
        }
        return StreamUtil.toFilteredList(c -> MainClassCode.KOMPETENTS.name().equals(c.getMainClassCode()), result);
    }
}
