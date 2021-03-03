package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleCompetence;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOccupation;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOutcome;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumOccupation;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.EhisRok;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StateCurriculumUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.commandobject.StateCurriculumModuleForm;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.dto.StateCurriculumDto;
import ee.hitsa.ois.web.dto.StateCurriculumModuleCompetenceDto;
import ee.hitsa.ois.web.dto.StateCurriculumModuleDto;
import ee.hitsa.ois.web.dto.StateCurriculumModuleOutcomeDto;
import ee.hitsa.ois.web.dto.StateCurriculumSearchDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;

@Transactional
@Service
public class StateCurriculumService {

    @Autowired
    private StateCurriculumRepository stateCurriculumRepository;
    @Autowired 
    private StateCurriculumValidationService stateCurriculumValidationService;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private SchoolService schoolService;

    private static final String FROM = "from state_curriculum as sc "
            + "inner join classifier status on status.code = sc.status_code";  // only for sorting by classifier's name
    private static final String SELECT = " sc.id, sc.name_et, sc.name_en, sc.valid_from, sc.valid_thru, sc.credits, "
            + "sc.status_code, case when sc.is_vocational = true then "
                + "(select cc.connect_classifier_code "
                + "from classifier_connect as cc "
                + "where cc.main_classifier_code = 'EKR' "
                + "and cc.classifier_code in "
                    + "(select sco.occupation_code "
                    + "from state_curriculum_occupation as sco "
                    + "where sc.id = sco.state_curriculum_id order by sco.id limit 1)) else sc.isced_class_code end as ekr_level, "
            + "status.name_et as statusNameEt, status.name_en as statusNameEn, sc.is_vocational, sc.state_curr_class_code";

    /**
     * With this solution StateCurriculumSpecification will not be required anymore.
     * StateCurriculumSearchDto can also be simplified: iscedClass and large constructor can be removed
     */
    public Page<StateCurriculumSearchDto> search(HoisUserDetails user, StateCurriculumSearchCommand criteria, Pageable pageable) {
        if(user == null || !StateCurriculumUtil.hasPermissionToView(user)) {
            criteria.setStatus(Collections.singletonList(CurriculumStatus.OPPEKAVA_STAATUS_K.name()));
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(FROM).sort(pageable);

        String fieldName = Language.EN.equals(criteria.getLang()) ? "sc.name_en" : "sc.name_et";
        qb.optionalContains(fieldName, "name", criteria.getName());

        qb.optionalCriteria("sc.status_code in (:status)", "status", criteria.getStatus());
        qb.optionalCriteria("sc.isced_class_code in (:iscedRyhm)", "iscedRyhm", criteria.getIscedClass());
        qb.optionalCriteria("sc.is_vocational = :isVocational", "isVocational", criteria.getIsVocational());
        if (user != null && user.isSchoolAdmin()) {
            SchoolType schoolType = schoolService.schoolType(user.getSchoolId());
            if (!schoolType.isBasic()) {
                qb.requiredCriteria("sc.state_curr_class_code != :basicROK", "basicROK", EhisRok.EHIS_ROK_PROK.name());
            }
            if (!schoolType.isSecondary()) {
                qb.requiredCriteria("sc.state_curr_class_code != :highSchoolROK", "highSchoolROK", EhisRok.EHIS_ROK_GROK.name());
            }
            if (!schoolType.isVocational()) {
                qb.filter("sc.is_vocational = false");
            }
        }
        qb.optionalCriteria("(((select cc.connect_classifier_code "
                + "from classifier_connect as cc "
                + "where cc.main_classifier_code = 'EKR' "
                + "and cc.classifier_code in "
                + "(select sco.occupation_code "
                + "from state_curriculum_occupation as sco "
                + "where sc.id = sco.state_curriculum_id "
                + "order by sco.id limit 1) ) in (:ekrLevel) and sc.is_vocational = true) "
                // secondary school isced class code holds EKR level
                + "or (sc.isced_class_code in (:ekrLevel) and sc.is_vocational = false))", "ekrLevel", criteria.getEkrLevel());
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

        qb.optionalCriteria("sc.valid_from >= :validFrom", "validFrom", criteria.getValidFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("sc.valid_thru <= :validThru", "validThru", criteria.getValidThru(), DateUtils::lastMomentOfDay);

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);
        return results.map(r -> resultToSearchDto(user, r));
    }

    private static StateCurriculumSearchDto resultToSearchDto(HoisUserDetails user,  Object r) {
        StateCurriculumSearchDto dto = new StateCurriculumSearchDto();
        dto.setId(resultAsLong(r, 0));
        dto.setNameEt(resultAsString(r, 1));
        dto.setNameEn(resultAsString(r, 2));
        dto.setValidFrom(resultAsLocalDate(r, 3));
        dto.setValidThru(resultAsLocalDate(r, 4));
        dto.setCredits(resultAsLong(r, 5));
        dto.setStatus(resultAsString(r, 6));
        dto.setEkrLevel(resultAsString(r, 7));
        if (user != null) {
            dto.setCanChange(Boolean.valueOf(StateCurriculumUtil.canChange(user, dto.getStatus())));
        }
        dto.setIsVocational(resultAsBoolean(r, 10));
        dto.setStateCurrClass(resultAsString(r, 11));
        return dto;
    }

    public StateCurriculumDto get(HoisUserDetails user, StateCurriculum stateCurriculum) {
        StateCurriculumDto dto = StateCurriculumDto.of(stateCurriculum);
        dto.setCanChange(Boolean.valueOf(StateCurriculumUtil.canChange(user, stateCurriculum)));
        dto.setCanConfirm(Boolean.valueOf(StateCurriculumUtil.canConfirm(user, stateCurriculum)));
        dto.setCanClose(Boolean.valueOf(StateCurriculumUtil.canClose(user, stateCurriculum)));
        dto.setCanDelete(Boolean.valueOf(StateCurriculumUtil.canDelete(user, stateCurriculum)));
        
        dto.setCurricula(StreamUtil.toMappedSet(CurriculumSearchDto::forStateCurriculumForm, 
                stateCurriculum.getCurricula().stream().filter(
                        c -> CurriculumUtil.canView(user, schoolService.getEhisSchool(user.getSchoolId()), c))));
        return dto;
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
            if (Boolean.FALSE.equals(command.getIsVocational())) {
                filters.add(cb.equal(root.get("isVocational"), Boolean.FALSE));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, sort);
    }

    public StateCurriculum create(HoisUserDetails user, StateCurriculumForm stateCurriculumForm) {
        StateCurriculum stateCurriculum = new StateCurriculum();
        stateCurriculum.setStatus(em.getReference(Classifier.class, CurriculumStatus.OPPEKAVA_STAATUS_S.name()));
        return save(user, stateCurriculum, stateCurriculumForm);
    }

    public StateCurriculum save(HoisUserDetails user, StateCurriculum stateCurriculum, StateCurriculumForm stateCurriculumForm) {
        if (Boolean.TRUE.equals(stateCurriculumForm.getIsVocational())) {
            stateCurriculumValidationService.validateStateCurriculumFormOnVocational(stateCurriculumForm);
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(stateCurriculumForm, stateCurriculum, classifierRepository, "occupations", "modules");
        updateStateCurriculumOccupations(stateCurriculum, stateCurriculumForm.getOccupations());
        updateStateCurriculumModules(stateCurriculum, stateCurriculumForm);
        return EntityUtil.save(stateCurriculum, em);
    }

    private void updateModule(StateCurriculumModuleDto dto, StateCurriculumModule module) {
        EntityUtil.bindToEntity(dto, module, classifierRepository, "outcomes", "moduleOccupations");
        updateModuleOutcomes(module, dto.getOutcomes());
        updateModuleOccupations(module, dto.getModuleOccupations());
        updateModuleCompetences(module, dto.getCompetences());
    }

    private void updateModuleCompetences(StateCurriculumModule module,
            Set<StateCurriculumModuleCompetenceDto> competences) {
        EntityUtil.bindEntityCollection(module.getModuleCompetences(), StateCurriculumModuleCompetence::getId, competences, 
                StateCurriculumModuleCompetenceDto::getId, dto -> createCompetence(module, dto), this::updateCompetence);
    }

    private StateCurriculumModuleCompetence createCompetence(StateCurriculumModule module, StateCurriculumModuleCompetenceDto dto) {
        StateCurriculumModuleCompetence competence = new StateCurriculumModuleCompetence();
        competence.setModule(module);
        updateCompetence(dto, competence);
        return competence;
    }

    private void updateCompetence(StateCurriculumModuleCompetenceDto dto, StateCurriculumModuleCompetence competence) {
        EntityUtil.bindToEntity(dto, competence, "competence");
        competence.setCompetence(em.getReference(Classifier.class, dto.getCompetence()));
    }

    private void updateModuleOutcomes(StateCurriculumModule module, Set<StateCurriculumModuleOutcomeDto> outcomes) {
        EntityUtil.bindEntityCollection(module.getOutcomes(), StateCurriculumModuleOutcome::getId, outcomes, 
                StateCurriculumModuleOutcomeDto::getId, dto -> createOutcome(module, dto), this::updateOutcome);
    }

    private StateCurriculumModuleOutcome createOutcome(StateCurriculumModule module, StateCurriculumModuleOutcomeDto dto) {
        StateCurriculumModuleOutcome outcome = new StateCurriculumModuleOutcome();
        outcome.setModule(module);
        updateOutcome(dto, outcome);
        return outcome;
    }

    private void updateOutcome(StateCurriculumModuleOutcomeDto dto, StateCurriculumModuleOutcome grade) {
        EntityUtil.bindToEntity(dto, grade);
    }

    private void updateModuleOccupations(StateCurriculumModule module, Set<String> moduleOccupations) {
        EntityUtil.bindEntityCollection(module.getModuleOccupations(), o -> EntityUtil.getCode(o.getOccupation()), moduleOccupations, occupation -> {
            Classifier c = EntityUtil.validateClassifier(em.getReference(Classifier.class, occupation),
                    MainClassCode.KUTSE, MainClassCode.OSAKUTSE, MainClassCode.SPETSKUTSE);

            return new StateCurriculumModuleOccupation(c);
        });
    }

    private void updateStateCurriculumOccupations(StateCurriculum stateCurriculum, Set<String> occupations) {
        EntityUtil.bindEntityCollection(stateCurriculum.getOccupations(), o -> EntityUtil.getCode(o.getOccupation()), occupations, occupation -> {
            return new StateCurriculumOccupation(EntityUtil.validateClassifier(em.getReference(Classifier.class, occupation), MainClassCode.KUTSE));
        });
    }

    private void updateStateCurriculumModules(StateCurriculum stateCurriculum, StateCurriculumForm form) {
        EntityUtil.bindEntityCollection(stateCurriculum.getModules(), StateCurriculumModule::getId, form.getModules(), 
                StateCurriculumModuleDto::getId, dto -> createModule(dto, stateCurriculum), this::updateModule);
    }

    private StateCurriculumModule createModule(StateCurriculumModuleDto dto, StateCurriculum stateCurriculum) {
        StateCurriculumModule module = new StateCurriculumModule();
        module.setStateCurriculum(stateCurriculum);
        updateModule(dto, module);
        return module;
    }

    public StateCurriculumModule createModule(StateCurriculumModuleForm form) {
        StateCurriculum stateCurriculum = em.getReference(StateCurriculum.class, form.getStateCurriculum());
        updateStateCurriculumOccupations(stateCurriculum, form.getStateCurriculumOccupations());
        StateCurriculumModule module = createModule(form, stateCurriculum);
        EntityUtil.save(stateCurriculum, em);
        return EntityUtil.save(module, em);
    }

    public void deleteModule(HoisUserDetails user, StateCurriculumModule stateCurriculumModule) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(stateCurriculumModule, em);
    }

    public StateCurriculumModule updateModule(HoisUserDetails user, StateCurriculumModule module, StateCurriculumModuleForm form) {
        EntityUtil.setUsername(user.getUsername(), em);
        StateCurriculum stateCurriculum = module.getStateCurriculum();
        updateStateCurriculumOccupations(stateCurriculum, form.getStateCurriculumOccupations());
        updateModule(form, module);
        EntityUtil.save(stateCurriculum, em);
        return EntityUtil.save(module, em);
    }

    public StateCurriculum setStatus(StateCurriculum stateCurriculum, CurriculumStatus status) {
        stateCurriculum.setStatus(em.getReference(Classifier.class, status.name()));
        return EntityUtil.save(stateCurriculum, em);
    }

    public StateCurriculum setStatusAndSave(HoisUserDetails user, StateCurriculum stateCurriculum, StateCurriculumForm form, CurriculumStatus status) {
        stateCurriculum.setStatus(em.getReference(Classifier.class, status.name()));
        return save(user, stateCurriculum, form);
    }

    public void delete(HoisUserDetails user, StateCurriculum stateCurriculum) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(stateCurriculum, em);
    }
}
