package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOccupation;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOutcome;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumOccupation;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.StateCurriculumModuleDto;
import ee.hitsa.ois.web.dto.StateCurriculumSearchDto;

@Transactional
@Service
public class StateCurriculumService {

	@Autowired
	private StateCurriculumRepository stateCurriculumRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    
    private static final String FROM = "from state_curriculum as sc "
            + "inner join classifier status on status.code = sc.status_code";  // only for sorting by classifier's name
    private static final String SELECT = " sc.id, sc.name_et, sc.name_en, sc.valid_from, sc.valid_thru, sc.credits, "
            + "sc.status_code, "
                + "(select cc.connect_classifier_code "
                + "from classifier_connect as cc "
                + "where cc.main_classifier_code = 'EKR' "
                + "and cc.classifier_code in "
                    + "(select sco.occupation_code "
                    + "from state_curriculum_occupation as sco "
                    + "where sc.id = sco.state_curriculum_id order by sco.id limit 1) ) as ekr_level, "
            + "status.name_et as statusNameEt, status.name_en as statusNameEn";
    /**
     * With this solution StateCurriculumSpecification will not be required anymore.
     * StateCurriculumSearchDto can also be simplified: iscedClass and large constructor can be removed
     */
    public Page<StateCurriculumSearchDto> search(StateCurriculumSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM).sort(pageable);

        String fieldName = Language.EN.equals(criteria.getLang()) ? "sc.name_en" : "sc.name_et";
        qb.optionalContains(fieldName, "name", criteria.getName());

        qb.optionalCriteria("sc.status_code in (:status)", "status", criteria.getStatus());
        qb.optionalCriteria("sc.isced_class_code in (:iscedRyhm)", "iscedRyhm", criteria.getIscedClass());
        
        qb.optionalCriteria("(select cc.connect_classifier_code "
                + "from classifier_connect as cc "
                + "where cc.main_classifier_code = 'EKR' "
                + "and cc.classifier_code in "
                + "(select sco.occupation_code "
                + "from state_curriculum_occupation as sco "
                + "where sc.id = sco.state_curriculum_id "
                + "order by sco.id limit 1) ) in (:ekrLevel)", "ekrLevel", criteria.getEkrLevel());
        /*
         * To avoid joins and subqueries, following property of classifiers can be used (look at numbers):
         * ISCED_RYHM_0511 is bound with ISCED_SUUN_051 and it is bound with ISCED_VALD_05
         */
        qb.optionalCriteria("(select cc.connect_classifier_code "
                + "from classifier_connect as cc "
                + "where cc.classifier_code = sc.isced_class_code "
                + "and cc.main_classifier_code = 'ISCED_SUUN') "
                + "in (:iscedSuun)", "iscedSuun", criteria.getIscedSuun());
        
        qb.optionalCriteria("(select cc2.connect_classifier_code "
                + "from classifier_connect as cc "
                + "inner join classifier_connect as cc2 "
                + "on cc2.classifier_code = cc.connect_classifier_code "
                + "where cc.classifier_code = sc.isced_class_code "
                + "and cc2.main_classifier_code = 'ISCED_VALD' ) "
                + " = :iscedVald", "iscedVald", criteria.getIscedVald());

        qb.optionalCriteria("sc.inserted >= :insertedFrom", "insertedFrom", criteria.getValidFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("sc.inserted <= :insertedThru", "insertedThru", criteria.getValidThru(), DateUtils::lastMomentOfDay);

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);
        return results.map(r -> {
            StateCurriculumSearchDto dto = new StateCurriculumSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setNameEn(resultAsString(r, 2));
            dto.setValidFrom(resultAsLocalDate(r, 3));
            dto.setValidThru(resultAsLocalDate(r, 4));
            dto.setCredits(resultAsLong(r, 5));
            dto.setStatus(resultAsString(r, 6));
            dto.setEkrLevel(resultAsString(r, 7));
            return dto;
        });
    }

    public boolean isUnique(UniqueCommand command) {
        // TODO use existsBy
        return stateCurriculumRepository.count((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            if(command.getId() != null) {
                filters.add(cb.notEqual(root.get("id"), command.getId()));
            }
            final Set<String> ALLOWED_PROPERTIES = new HashSet<>(Arrays.asList("nameEt", "nameEn"));
            if(ALLOWED_PROPERTIES.contains(command.getParamName())) {
                filters.add(cb.equal(root.get(command.getParamName()), command.getParamValue()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }) == 0;
    }

	public void delete(StateCurriculum curriculum) {
		EntityUtil.deleteEntity(stateCurriculumRepository, curriculum);
	}

    public List<StateCurriculum> searchAll(StateCurriculumSearchCommand command, Sort sort) {
        return stateCurriculumRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            if(Boolean.TRUE.equals(command.getValid())) {
                LocalDate now = LocalDate.now();
                filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
                filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            }

            if(Boolean.FALSE.equals(command.getExpired())) {
                LocalDate now = LocalDate.now();
                filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            }

            if (!CollectionUtils.isEmpty(command.getStatus())) {
                filters.add(root.get("status").get("code").in(command.getStatus()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, sort);
    }

    public StateCurriculum create(StateCurriculumForm stateCurriculumForm) {
        return save(new StateCurriculum(), stateCurriculumForm);
    }

    public StateCurriculum save(StateCurriculum stateCurriculum, StateCurriculumForm stateCurriculumForm) {
        EntityUtil.bindToEntity(stateCurriculumForm, stateCurriculum, classifierRepository, "occupations", "modules");
        updateOccupations(stateCurriculum, stateCurriculumForm.getOccupations());
        updateModules(stateCurriculum, stateCurriculumForm.getModules());
        return stateCurriculumRepository.save(stateCurriculum);
    }

    private void updateOccupations(StateCurriculum stateCurriculum, Set<String> occupations) {
        EntityUtil.bindEntityCollection(stateCurriculum.getOccupations(), o -> EntityUtil.getCode(o.getOccupation()), occupations, occupation -> {
            return new StateCurriculumOccupation(EntityUtil.validateClassifier(classifierRepository.getOne(occupation), MainClassCode.KUTSE));
        });
    }

    private void updateModules(StateCurriculum stateCurriculum, Set<StateCurriculumModuleDto> moduleDtos) {
        EntityUtil.bindEntityCollection(stateCurriculum.getModules(), StateCurriculumModule::getId, moduleDtos, StateCurriculumModuleDto::getId, dto -> {
            StateCurriculumModule module = new StateCurriculumModule();
            updateModule(dto, module);
            return module;
        }, this::updateModule);
    }

    private void updateModule(StateCurriculumModuleDto dto, StateCurriculumModule module) {
        module = EntityUtil.bindToEntity(dto, module, classifierRepository, "outcome", "moduleOccupations");
        StateCurriculumModuleOutcome outcome = module.getOutcome();
        outcome.setOutcomesEt(dto.getOutcomesEt());
        outcome.setOutcomesEn(dto.getOutcomesEn());
        outcome.setModule(module);
        updateModuleOccupations(module, dto.getModuleOccupations());
    }

    private void updateModuleOccupations(StateCurriculumModule module, Set<String> moduleOccupations) {
        EntityUtil.bindEntityCollection(module.getModuleOccupations(), o -> EntityUtil.getCode(o.getOccupation()), moduleOccupations, occupation -> {
            Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(occupation),
                    MainClassCode.KUTSE, MainClassCode.OSAKUTSE, MainClassCode.SPETSKUTSE);

            return new StateCurriculumModuleOccupation(c);
        });
    }

    public StateCurriculum updateStateCurriculumModules(StateCurriculum stateCurriculum, StateCurriculumForm form) {
        updateOccupations(stateCurriculum, form.getOccupations());
        updateModules(stateCurriculum, form.getModules());
        return stateCurriculumRepository.save(stateCurriculum);
    }
}
