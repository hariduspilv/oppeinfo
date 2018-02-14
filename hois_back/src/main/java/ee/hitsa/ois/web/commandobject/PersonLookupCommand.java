package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.validation.EstonianIdCode;

public class PersonLookupCommand {

    @EstonianIdCode
    private String idcode;
    private String foreignIdcode;
    private String role;

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getForeignIdcode() {
        return foreignIdcode;
    }

    public void setForeignIdcode(String foreignIdcode) {
        this.foreignIdcode = foreignIdcode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
