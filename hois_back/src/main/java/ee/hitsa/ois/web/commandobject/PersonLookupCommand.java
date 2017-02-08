package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.validation.EstonianIdCode;
import ee.hitsa.ois.validation.NotEmpty;

public class PersonLookupCommand {

    @NotEmpty
    @EstonianIdCode
    private String idcode;
    private String role;

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
