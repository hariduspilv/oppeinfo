package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.Required;

public class EnterpriseForm extends VersionedCommand {
	@Required
    @Size(max = 100)
    private String name;
    @Required
    @Size(max = 20)
    private String regCode;
    @Size(max = 100)
    private String contactPersonName;
    @Size(max = 100)
    private String contactPersonPhone;
    @Size(max = 100)
    private String contactPersonEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }
}
