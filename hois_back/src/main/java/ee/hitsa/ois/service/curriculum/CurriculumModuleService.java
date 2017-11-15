package ee.hitsa.ois.service.curriculum;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleCompetence;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumModuleRepository;
import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumModuleForm;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumModuleTypesCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeDto;

@Transactional
@Service
public class CurriculumModuleService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private CurriculumModuleRepository curriculumModuleRepository;
    @Autowired
    private CurriculumRepository curriculumRepository;
    
    public CurriculumModule create(CurriculumModuleForm form) {
        CurriculumModule module = new CurriculumModule();
        Curriculum curriculum = curriculumRepository.getOne(form.getCurriculum());
        curriculum.getModules().add(module);
        module.setCurriculum(curriculum);
        return update(module, form);
    }

    public CurriculumModule update(CurriculumModule module, CurriculumModuleForm dto) {
        EntityUtil.bindToEntity(dto, module, classifierRepository, "occupations", "competences", "outcomes");
        updateOccupations(module, dto.getOccupations());
        updateCompetences(module, dto.getCompetences());
        updateOutcomes(module, dto.getOutcomes());
        return curriculumModuleRepository.save(module);
    }

    private void updateOccupations(CurriculumModule module, Set<String> occupations) {
        EntityUtil.bindEntityCollection(module.getOccupations(), o -> EntityUtil.getCode(o.getOccupation()), occupations, occupationCode -> {
            Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(occupationCode),
                    MainClassCode.OSAKUTSE, MainClassCode.KUTSE, MainClassCode.SPETSKUTSE);
            return new CurriculumModuleOccupation(c);
          });
    }
    
    private void updateCompetences(CurriculumModule module, Set<String> competences) {
        EntityUtil.bindEntityCollection(module.getCompetences(), c -> EntityUtil.getCode(c.getCompetence()), competences, competenceCode -> {
            return new CurriculumModuleCompetence(EntityUtil.validateClassifier(classifierRepository.getOne(competenceCode), MainClassCode.KOMPETENTS));
        });
    }

    // TODO: rewrite using EntityUtil
    private static void updateOutcomes(CurriculumModule module, Set<CurriculumModuleOutcomeDto> outcomes) {
        Set<CurriculumModuleOutcome> newOutComes = new HashSet<>();
        if(outcomes != null) {
            for(CurriculumModuleOutcomeDto dto : outcomes) {
                CurriculumModuleOutcome outcome = dto.getId() == null ? new CurriculumModuleOutcome() :
                    EntityUtil.find(dto.getId(), module.getOutcomes()).get();

                newOutComes.add(EntityUtil.bindToEntity(dto, outcome));
            }
        }
        module.setOutcomes(newOutComes);
    }

    public void delete(CurriculumModule module) {
        EntityUtil.deleteEntity(module, em);
        module.getCurriculum().getModules().remove(module);        
    }

    public Set<String> getPossibleModuleTypes(CurriculumModuleTypesCommand command) {
        
        Set<String> possibleTypes = Arrays.asList(CurriculumModuleType.values())
                .stream().map(t -> t.name()).collect(Collectors.toSet());
        
        if(command.getCurriculum() != null) {
            Curriculum c = curriculumRepository.getOne(command.getCurriculum());

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
                CurriculumModule m = curriculumModuleRepository.getOne(command.getModule());
                possibleTypes.add(EntityUtil.getCode(m.getModule()));
            }
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from classifier c");
        qb.requiredCriteria("c.code in :possibleTypes", "possibleTypes", possibleTypes);
        List<?> result = qb.select("c.code", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsString(r, 0), result);        
    }
}
