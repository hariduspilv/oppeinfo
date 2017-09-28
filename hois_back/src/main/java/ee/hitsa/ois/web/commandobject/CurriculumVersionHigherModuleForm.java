package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.validation.CurriculumValidator.HigherModule;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionHigherModuleDto;

public class CurriculumVersionHigherModuleForm extends CurriculumVersionHigherModuleDto {
    
    @NotEmpty(groups = {HigherModule.class})
    public Set<Long> curriculumVersionSpecialities;

    public Set<Long> getCurriculumVersionSpecialities() {
        return curriculumVersionSpecialities;
    }

    public void setCurriculumVersionSpecialities(Set<Long> curriculumVersionSpecialities) {
        this.curriculumVersionSpecialities = curriculumVersionSpecialities;
    }
}
