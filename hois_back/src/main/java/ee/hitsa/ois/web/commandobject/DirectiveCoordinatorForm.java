package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.EstonianIdCode;
import ee.hitsa.ois.validation.NotEmpty;

public class DirectiveCoordinatorForm extends VersionedCommand {

    @NotEmpty
    @Size(max = 100)
    private String name;
    @NotEmpty
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
