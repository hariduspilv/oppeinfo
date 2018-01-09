package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.Required;

public class BuildingForm extends VersionedCommand {

    @Required
    @Size(max = 20)
    private String code;
    @Size(max = 255)
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
