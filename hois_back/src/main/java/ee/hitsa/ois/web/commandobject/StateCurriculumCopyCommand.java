package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class StateCurriculumCopyCommand {
    @NotNull
    public Long id;
    @NotEmpty
    public List<StateCurriculumOccupationCopyCommand> occupations;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<StateCurriculumOccupationCopyCommand> getOccupations() {
        return occupations;
    }
    public void setOccupations(List<StateCurriculumOccupationCopyCommand> occupations) {
        this.occupations = occupations;
    }
     
    
}
