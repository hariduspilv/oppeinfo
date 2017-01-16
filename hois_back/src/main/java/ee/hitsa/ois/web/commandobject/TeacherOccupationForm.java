package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeacherOccupationForm extends VersionedCommand {

    @NotNull
    @Size(min = 1, max = 100)
    private String occupationEt;
    @NotNull
    @Size(min = 1, max = 100)
    private String occupationEn;
    private Boolean isValid;

    public String getOccupationEt() {
        return occupationEt;
    }

    public void setOccupationEt(String occupationEt) {
        this.occupationEt = occupationEt;
    }

    public String getOccupationEn() {
        return occupationEn;
    }

    public void setOccupationEn(String occupationEn) {
        this.occupationEn = occupationEn;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
