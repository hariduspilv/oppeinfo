package ee.hitsa.ois.service.curriculum;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupationSpeciality;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOccupation;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOutcome;
import ee.hitsa.ois.enums.CurriculumConsecution;
import ee.hitsa.ois.enums.CurriculumDraft;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.StateCurriculumCopyCommand;
import ee.hitsa.ois.web.commandobject.StateCurriculumOccupationCopyCommand;

@Transactional
@Service
public class StateCurriculumCopyService {

    @Autowired
    private EntityManager em;

    private static final long CREDITS_PER_TERM = 30;
    private static final long MONTHS_PER_TERM = 6;

    private static final String FROM_CLASSIFIER_CONNECT = " from classifier_connect "
            + "join classifier c on c.code = classifier_connect.classifier_code ";

    private static final String FILTER_BY_SCHOOL_STUDY_LEVEL = "classifier_code in "
            + "(select study_level_code "
            + "from school_study_level "
            + "where school_id = :school)";
    private static final String FILTER_BY_STATE_CURRICULUM_EKR_LEVEL = "connect_classifier_code in "
            + "("
            + " select cc.connect_classifier_code "
            + "from classifier_connect as cc "
            + " where cc.main_classifier_code = 'EKR' "
            + " and cc.classifier_code in "
            + " (select sco.occupation_code "
            + " from state_curriculum_occupation as sco "
            + " where sco.state_curriculum_id = :stateCurriculum order by sco.id)) ";

    public Curriculum copyStateCurriculum(HoisUserDetails user, StateCurriculumCopyCommand command) {
        Curriculum newCurriculum = new Curriculum(); 
        StateCurriculum copied = em.getReference(StateCurriculum.class, command.getId());

        BeanUtils.copyProperties
        (copied, newCurriculum, "id", "school", "inserted", "insertedBy", "changed", "changedBy", "version", 
                "validFrom", "validThru",  
                "studyLanguages", "specialities", 
                "studyForms", "modules", "occupations", "versions", "nameRu");
        
        newCurriculum.setCode("ADD CODE");
        newCurriculum.setSchool(em.getReference(School.class, user.getSchoolId()));
        newCurriculum.setHigher(Boolean.FALSE);
        newCurriculum.setStatus(em.getReference(Classifier.class, CurriculumStatus.OPPEKAVA_STAATUS_S.name()));
        newCurriculum.setDraft(em.getReference(Classifier.class, CurriculumDraft.OPPEKAVA_LOOMISE_VIIS_RIIKLIK.name()));
        newCurriculum.setConsecution(em.getReference(Classifier.class, CurriculumConsecution.OPPEKAVA_TYPE_E.name()));
        newCurriculum.setValidFrom(LocalDate.now());
        newCurriculum.setStateCurriculum(copied);
        newCurriculum.setOccupation(Boolean.valueOf(isOccupation(command)));
        newCurriculum.setJoint(Boolean.FALSE);
        newCurriculum.setCredits(BigDecimal.valueOf(copied.getCredits().longValue()));
        newCurriculum.setOrigStudyLevel(getStudyLevel(user.getSchoolId(), EntityUtil.getId(copied)));
        newCurriculum.setStudyPeriod(Integer.valueOf(calculateStudyPeriod(copied.getCredits())));

        copyOccupations(newCurriculum, command.getOccupations());
        copyModules(newCurriculum, copied, command.getOccupations());

        return EntityUtil.save(newCurriculum, em);
    }

    private boolean isOccupation(StateCurriculumCopyCommand command) {
        Classifier c = em.getReference(Classifier.class, command.getOccupations().get(0).getOccupation());
        return ClassifierUtil.mainClassCodeEquals(MainClassCode.KUTSE, c);
    }

    public static int calculateStudyPeriod(BigDecimal credits) {
        if(credits.equals(BigDecimal.valueOf(0))) {
            return 0;
        }
        return credits.multiply(BigDecimal.valueOf(MONTHS_PER_TERM))
                .divide(BigDecimal.valueOf(CREDITS_PER_TERM), 0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     * Why List<?> data is used:
     * There may be no query results. 
     * In this case error should be more informative, than just "System error"
     * That is why getResultList() is used instead of getSingleResult() 
     * 
     * Another option would be catching exception and throwing a custom one
     */
    private Classifier getStudyLevel(Long schoolId, Long stateCurriculum) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(FROM_CLASSIFIER_CONNECT).sort("classifier_code");
        qb.filter("classifier_code like 'OPPEASTE%' ");
        qb.filter(" c.valid ");
        qb.requiredCriteria(FILTER_BY_STATE_CURRICULUM_EKR_LEVEL, "stateCurriculum", stateCurriculum);
        qb.requiredCriteria(FILTER_BY_SCHOOL_STUDY_LEVEL, "school", schoolId);
        List<?> data = qb.select(" classifier_code", em).setMaxResults(1).getResultList();
        if(data.isEmpty()) {
            throw new ValidationFailedException("curriculum.error.noSuchStudyLevel");
        }
        String code = resultAsString(data.get(0), 0);
        return em.getReference(Classifier.class, code);
    }

    private void copyOccupations(Curriculum newCurriculum, List<StateCurriculumOccupationCopyCommand> occupations) {
        Set<CurriculumOccupation> newOccupations = new HashSet<>();
        for(StateCurriculumOccupationCopyCommand occupation : occupations) {
            newOccupations.add(copyOccupation(newCurriculum, occupation));
        }
        newCurriculum.setOccupations(newOccupations);
    }

    private CurriculumOccupation copyOccupation(Curriculum newCurriculum, StateCurriculumOccupationCopyCommand occupation) {
        CurriculumOccupation newOccupation  = new CurriculumOccupation();
        newOccupation.setCurriculum(newCurriculum);
        newOccupation.setOccupation(em.getReference(Classifier.class, occupation.getOccupation()));
        newOccupation.setOccupationGrant(Boolean.FALSE);
        copySpecialities(newOccupation, occupation.getSpecialities());
        return newOccupation;
    }

    private void copySpecialities(CurriculumOccupation newOccupation, Set<String> specialities) {
        if(!CollectionUtils.isEmpty(specialities)) {
            newOccupation.setSpecialities(new HashSet<>());
            for(String speciality : specialities) {
                newOccupation.getSpecialities().add(copySpeciality(speciality));
            }
        }
    }

    private CurriculumOccupationSpeciality copySpeciality(String speciality) {
        CurriculumOccupationSpeciality newSpeciality = new CurriculumOccupationSpeciality();
        newSpeciality.setSpeciality(em.getReference(Classifier.class, speciality));
        return newSpeciality;
    }

    private void copyModules(Curriculum newCurriculum, StateCurriculum copied,
            List<StateCurriculumOccupationCopyCommand> occupations) {
        Set<String> codes = getOccupations(occupations);
        if(!copied.getModules().isEmpty()) {
            newCurriculum.setModules(new HashSet<>());
            for(StateCurriculumModule module : copied.getModules()) {            
                if(moduleMustBeCopied(module, codes)) {
                    newCurriculum.getModules().add(copyModule(newCurriculum, module, codes));
                }
            }
        }
    }

    public CurriculumModule copyModule(Curriculum newCurriculum, StateCurriculumModule copied, Set<String> codes) {
        CurriculumModule newModule = new CurriculumModule();
        BeanUtils.copyProperties(copied, newModule, "id", "inserted", "insertedBy", "changed", "changedBy", "version", "outcomes");
        newModule.setCurriculum(newCurriculum);
        newModule.setPractice(Boolean.FALSE);
        copyOutcomes(newModule, copied.getOutcomes());
        copyModuleOccupations(newModule, copied.getModuleOccupations(), codes);
        return newModule;
    }

    private static void copyOutcomes(CurriculumModule newModule, Set<StateCurriculumModuleOutcome> outcomes) {
        if(!outcomes.isEmpty()) {
            // order outcomes initially using record id
            long rowNum = 0;
            for(StateCurriculumModuleOutcome copied : outcomes.stream().sorted(Comparator.comparing(StateCurriculumModuleOutcome::getId)).collect(Collectors.toList())) {
                CurriculumModuleOutcome outcome = new CurriculumModuleOutcome();
                outcome.setOutcomeEt(copied.getOutcomesEt());
                outcome.setOutcomeEn(copied.getOutcomesEn());
                outcome.setOrderNr(Long.valueOf(rowNum++));
                newModule.getOutcomes().add(outcome);
            }
        }
    }

    private static boolean moduleMustBeCopied(StateCurriculumModule module, Set<String> occupations) {
        return module.getModuleOccupations().stream()
                .anyMatch(m -> moduleOccupationAdded(m, occupations));
    }

    /**
     * @param m - state curriculum module occupation
     * @param occupations - set of occupations, partoccupations and specialities 
     *  selected from state curriculum to copy to new curriculum
     * @return true if state curriculum module occupation is defined in this set
     */
    private static boolean moduleOccupationAdded(StateCurriculumModuleOccupation m, Set<String> occupations) {
        return occupations.contains(EntityUtil.getCode(m.getOccupation()));
    }

    private void copyModuleOccupations(CurriculumModule newModule, Set<StateCurriculumModuleOccupation> moduleOccupations,
            Set<String> occupations) {
        newModule.setOccupations(new HashSet<>());
        Set<String> curriculumModuleOccupations = getCopiedModuleOccupations(moduleOccupations, occupations);
        for(String o : curriculumModuleOccupations) {
             newModule.getOccupations().add(copyModuleOccupation(o));
        }
    }

    private static Set<String> getCopiedModuleOccupations(Set<StateCurriculumModuleOccupation> moduleOccupations, Set<String> occupations) {
        return moduleOccupations.stream()
                .filter(m -> moduleOccupationAdded(m, occupations))
                .map(m -> EntityUtil.getCode(m.getOccupation())).collect(Collectors.toSet());
    }

    private CurriculumModuleOccupation copyModuleOccupation(String occupation) {
        CurriculumModuleOccupation cmo = new CurriculumModuleOccupation();
        cmo.setOccupation(em.getReference(Classifier.class, occupation));
        return cmo;
    }

    /**
     * @return occupations, part occupations and specialties selected by user
     */
    private Set<String> getOccupations(List<StateCurriculumOccupationCopyCommand> occupations) {
        Set<String> result = StreamUtil.toMappedSet(o -> o.getOccupation(), occupations);
        result.addAll(getPartOccupations(result));
        result.addAll(getSpecialities(occupations));
        return result;
    }

    /**
     * @return specialties selected by user from all occupations
     */
    private static Set<String> getSpecialities(List<StateCurriculumOccupationCopyCommand> occupations) {
        Set<String> result = new HashSet<>();
        for(StateCurriculumOccupationCopyCommand occupation : occupations) {
            if(occupation.getSpecialities() != null) {
                result.addAll(occupation.getSpecialities());
            }
        }
        return result;
    }

    private Set<String> getPartOccupations(Set<String> result) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(FROM_CLASSIFIER_CONNECT);
        qb.filter(" c.valid ");
        qb.filter("classifier_code like '" + MainClassCode.OSAKUTSE.name() + "%' ");
        qb.sort("classifier_code");
        qb.requiredCriteria("connect_classifier_code in :occupations", "occupations", result);
        List<?> data = qb.select(" classifier_code", em).getResultList();        
        return StreamUtil.toMappedSet(r -> resultAsString(r, 0), data);
    }
}
