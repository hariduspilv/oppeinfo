package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.EstonianIdCode;
import ee.hitsa.ois.validation.Required;

public class PersonForm extends VersionedCommand {

    @EstonianIdCode
    private String idcode;
    @Required
    @Size(max = 100)
    private String firstname;
    @Required
    @Size(max = 100)
    private String lastname;
    private String foreignIdcode;
    private String uniqueCode;
    @ClassifierRestriction(MainClassCode.RIIK)
    private String citizenship;
    private LocalDate birthdate;
    @Required
    @ClassifierRestriction(MainClassCode.SUGU)
    private String sex;
    @Size(max = 100)
    private String email;
    @Size(max = 100)
    private String phone;

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getForeignIdcode() {
        return foreignIdcode;
    }

    public void setForeignIdcode(String foreignIdcode) {
        this.foreignIdcode = foreignIdcode;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
}
