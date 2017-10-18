package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StateCurriculumUtil;
import ee.hitsa.ois.validation.StateCurriculumValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.commandobject.UniqueCommand;

@Service
public class StateCurriculumValidationService {
    
    @Autowired
    private StateCurriculumRepository stateCurriculumRepository;
    @Autowired
    private Validator validator;
    
    public static void assertCanChange(HoisUserDetails user, StateCurriculum sc) {
        if(!StateCurriculumUtil.canChange(user, sc)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static void assertCanCreate(HoisUserDetails user) {
        if(!StateCurriculumUtil.canCreate(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public void validateStateCurriculumForm(StateCurriculumForm stateCurriculumForm) {
        ValidationFailedException.throwOnError(validator.validate(stateCurriculumForm, StateCurriculumValidator.Confirmed.class));
    }
    
    public void assertNameIsUnique(StateCurriculum stateCurriculum, StateCurriculumForm stateCurriculumForm) {
        Long id = EntityUtil.getNullableId(stateCurriculum);

        UniqueCommand nameEtUnique = new UniqueCommand();
        nameEtUnique.setId(id);
        nameEtUnique.setParamName("nameEt");
        nameEtUnique.setParamValue(stateCurriculumForm.getNameEt());

        UniqueCommand nameEnUnique = new UniqueCommand();
        nameEnUnique.setId(id);
        nameEnUnique.setParamName("nameEn");
        nameEnUnique.setParamValue(stateCurriculumForm.getNameEn());

        if(!isUnique(nameEtUnique) || !isUnique(nameEnUnique)) {
            throw new ValidationFailedException("stateCurriculum.error.unique.name");
        }
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
            LocalDate now = LocalDate.now();
            filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
            filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }) == 0;
    }

}
