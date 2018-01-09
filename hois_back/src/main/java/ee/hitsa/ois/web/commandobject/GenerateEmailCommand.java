package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.Required;

public class GenerateEmailCommand {

    @Size(max = 255)
    private String firstname;
    @Required
    @Size(max = 255)
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
