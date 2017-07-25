package ee.hitsa.ois.web.commandobject;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.StateCurriculumModuleDto;

/**
 * Form extends dto as stateCurriculumOccupations should be updated as well
 */
public class StateCurriculumModuleForm extends StateCurriculumModuleDto {
    
    @NotNull
    private Long stateCurriculum;
    
    @NotEmpty
    @ClassifierRestriction(MainClassCode.KUTSE)
    private Set<String> stateCurriculumOccupations = new HashSet<>();
    
    public Long getStateCurriculum() {
        return stateCurriculum;
    }

    public void setStateCurriculum(Long stateCurriculum) {
        this.stateCurriculum = stateCurriculum;
    }

    public Set<String> getStateCurriculumOccupations() {
        return stateCurriculumOccupations;
    }

    public void setStateCurriculumOccupations(Set<String> stateCurriculumOccupations) {
        this.stateCurriculumOccupations = stateCurriculumOccupations;
    }
}
