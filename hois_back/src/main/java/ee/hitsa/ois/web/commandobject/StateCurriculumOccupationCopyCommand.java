package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class StateCurriculumOccupationCopyCommand {
    
    @NotNull
    @ClassifierRestriction({MainClassCode.KUTSE, MainClassCode.OSAKUTSE})
    private String occupation;
    @ClassifierRestriction(MainClassCode.SPETSKUTSE)
    private Set<String> specialities;
    

    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public Set<String> getSpecialities() {
        return specialities;
    }
    public void setSpecialities(Set<String> specialities) {
        this.specialities = specialities;
    }
}
