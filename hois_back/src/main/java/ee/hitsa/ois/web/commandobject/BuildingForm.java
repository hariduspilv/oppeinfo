package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BuildingForm extends VersionedCommand {

    @NotNull
    @Size(min = 1, max = 20)
    private String code;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @Size(max = 255)
    private String address;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
