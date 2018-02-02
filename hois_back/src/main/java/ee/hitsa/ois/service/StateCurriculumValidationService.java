package ee.hitsa.ois.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StateCurriculumUtil;
import ee.hitsa.ois.validation.StateCurriculumValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.commandobject.UniqueCommand;

@Transactional
@Service
public class StateCurriculumValidationService {

    private static final Map<String, String> ALLOWED_UNIQUE_PROPERTY_NAMES = new HashMap<>();
    static {
        ALLOWED_UNIQUE_PROPERTY_NAMES.put("nameEt", "name_et");
        ALLOWED_UNIQUE_PROPERTY_NAMES.put("nameEn", "name_en");
    }

    @Autowired
    private EntityManager em;
    @Autowired
    private Validator validator;

    public static void assertCanView(HoisUserDetails user, StateCurriculum sc) {
        if(!StateCurriculumUtil.canView(user, sc)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

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

    public static void assertCanConfirm(HoisUserDetails user, StateCurriculum sc) {
        if(!StateCurriculumUtil.canConfirm(user, sc)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanClose(HoisUserDetails user, StateCurriculum sc) {
        if(!StateCurriculumUtil.canClose(user, sc)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanDelete(HoisUserDetails user, StateCurriculum sc) {
        if(!StateCurriculumUtil.canDelete(user, sc)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public void validateStateCurriculumForm(StateCurriculumForm stateCurriculumForm) {
        ValidationFailedException.throwOnError(validator.validate(stateCurriculumForm, StateCurriculumValidator.Confirmed.class));
    }

    public void assertNameIsUnique(StateCurriculum stateCurriculum, StateCurriculumForm stateCurriculumForm) {
        Long id = EntityUtil.getNullableId(stateCurriculum);

        UniqueCommand nameUnique = new UniqueCommand();
        nameUnique.setId(id);
        nameUnique.setParamName("nameEt");
        nameUnique.setParamValue(stateCurriculumForm.getNameEt());

        if(isUnique(nameUnique)) {
            nameUnique.setParamName("nameEn");
            nameUnique.setParamValue(stateCurriculumForm.getNameEn());

            if(isUnique(nameUnique)) {
                return;
            }
        }
        throw new ValidationFailedException("stateCurriculum.error.unique.name");
    }

    public boolean isUnique(UniqueCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from state_curriculum sc");
        qb.optionalCriteria("sc.id <> :id", "id", command.getId());
        String paramName = ALLOWED_UNIQUE_PROPERTY_NAMES.get(command.getParamName());
        if(paramName != null) {
            // FIXME should compare case insensitive?
            qb.optionalCriteria("sc." + paramName + " = :paramName", "paramName", paramName);
        }
        qb.validNowCriteria("sc.valid_from", "sc.valid_thru");

        return qb.select("sc.id", em).setMaxResults(1).getResultList().isEmpty();
    }
}
