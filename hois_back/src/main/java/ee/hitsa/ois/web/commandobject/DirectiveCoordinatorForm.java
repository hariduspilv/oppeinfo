package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.EstonianIdCode;

public class DirectiveCoordinatorForm extends VersionedCommand {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @NotNull
    @EstonianIdCode
    private String idcode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
}
