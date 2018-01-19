package ee.hitsa.ois.service.curriculum;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupationSpeciality;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumModuleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.ExpiringOccupationStandardDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;

@Transactional
@Service
public class CurriculumOccupationService {

    private static final int OCCUPATION_STANDARD_EXPIRE_IN_MONTHS = 3;

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private CurriculumModuleRepository curriculumModuleRepository;

    /**
     * occupation standards which are already expired or expiring
     *
     * @param schoolId
     * @return
     */
    public List<ExpiringOccupationStandardDto> expiringOccupationStandards(Long schoolId) {
        LocalDate expireDate = LocalDate.now().plusMonths(OCCUPATION_STANDARD_EXPIRE_IN_MONTHS);
        List<?> data = em.createNativeQuery("select os.code as id, os.name_et, os.name_en, os.valid_thru, c.code from classifier os " +
                "inner join curriculum_occupation co on os.code = co.occupation_code " +
                "inner join curriculum c on co.curriculum_id = c.id and c.status_code in (?1)" +
                "where c.school_id = ?2 and os.main_class_code = ?3 and os.valid_thru < ?4 order by c.code")
                .setParameter(1, EnumUtil.toNameList(CurriculumStatus.OPPEKAVA_STAATUS_S, CurriculumStatus.OPPEKAVA_STAATUS_K))
                .setParameter(2, schoolId)
                .setParameter(3, MainClassCode.KUTSE.name())
                .setParameter(4, parameterAsTimestamp(expireDate))
                .getResultList();

        Map<String, ExpiringOccupationStandardDto> standardMap = new HashMap<>();
        for(Object r : data) {
            String code = resultAsString(r, 0);
            standardMap.computeIfAbsent(code, key -> {
                return new ExpiringOccupationStandardDto(resultAsString(r, 1), resultAsString(r, 2), resultAsLocalDate(r, 3), new ArrayList<>());
            }).getCurriculums().add(resultAsString(r, 4));
        }
        List<ExpiringOccupationStandardDto> standards = new ArrayList<>(standardMap.values());
        standards.sort(Comparator.comparing(ExpiringOccupationStandardDto::getValidThru));
        return standards;
    }

    public CurriculumOccupation create(HoisUserDetails user, CurriculumOccupationDto dto) {
        CurriculumOccupation occupation = new CurriculumOccupation();
        occupation.setCurriculum(em.getReference(Curriculum.class, dto.getCurriculum()));
        return update(user, dto, occupation);
    }

    public CurriculumOccupation update(HoisUserDetails user, CurriculumOccupationDto dto, CurriculumOccupation occupation) {
        EntityUtil.setUsername(user.getUsername(), em);
        if(CurriculumUtil.occupationCanBeChanged(occupation.getCurriculum().getDraft())) {
            EntityUtil.bindToEntity(dto, occupation, classifierRepository, "specialities");
            updateSpecialities(occupation, dto.getSpecialities());
            updateCurriculumModules(occupation.getCurriculum());
        } else {
            occupation.setOccupationGrant(dto.getOccupationGrant());
        }
        /*
         * Block below prevents error in the following scenario:
         * precondition: curriculum.occupation = false
         * 1) Set curriculum.occupation to true
         * 2) Add occupation
         * 3) Reload page. Result: occupation is set to false
         */
        Curriculum curriculum = occupation.getCurriculum();
        curriculum.setOccupation(Boolean.valueOf(ClassifierUtil.mainClassCodeEquals(MainClassCode.KUTSE, occupation.getOccupation())));

        EntityUtil.save(curriculum, em);
        return EntityUtil.save(occupation, em);
    }

    private void updateSpecialities(CurriculumOccupation occupation, Set<String> specialities) {
        EntityUtil.bindEntityCollection(occupation.getSpecialities(), s -> EntityUtil.getCode(s.getSpeciality()), specialities, specialityCode -> {
            Classifier c = EntityUtil.validateClassifier(em.getReference(Classifier.class, specialityCode), MainClassCode.SPETSKUTSE);
            return new CurriculumOccupationSpeciality(c);
        });
    }

    public void delete(HoisUserDetails user, CurriculumOccupation curriculumOccupation) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(curriculumOccupation, em);
        updateCurriculumModules(curriculumOccupation.getCurriculum());
    }
    
    /**
     * When occupation is deleted, corresponding occupations, part occupations and specialties 
     * should also be deleted from modules.
     * When occupation is changed, then old occupation, its specialties 
     * and part occupations should be deleted as well from modules
     */
    private void updateCurriculumModules(Curriculum curriculum) {
        Set<String> occupations = StreamUtil.toMappedSet
                (o -> EntityUtil.getCode(o.getOccupation()), curriculum.getOccupations());
        
        for(CurriculumOccupation occupation : curriculum.getOccupations()) {
            occupations.addAll(StreamUtil.toMappedSet(s -> EntityUtil.getCode(s.getSpeciality()), occupation.getSpecialities()));
            occupations.addAll(CurriculumUtil.getPartOccupationsCodes(occupation));
        }
        Set<CurriculumModule> modules = curriculum.getModules();
        for(CurriculumModule module : modules) {
            Set<CurriculumModuleOccupation> deletedOccupations = module.getOccupations()
                    .stream().filter(o -> !occupations.contains
                            (EntityUtil.getCode(o.getOccupation()))).collect(Collectors.toSet());
            module.getOccupations().removeAll(deletedOccupations);
        }
        curriculumModuleRepository.save(modules);
    }
}
