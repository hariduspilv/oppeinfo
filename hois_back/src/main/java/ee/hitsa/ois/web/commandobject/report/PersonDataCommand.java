package ee.hitsa.ois.web.commandobject.report;

public class PersonDataCommand {
    
    private StudentDataString firstname;
    private StudentDataString lastname;
    private StudentDataBoolean fullname;
    private StudentDataString sex;
    private StudentDataString idcode;
    private StudentDataString bankAccount;
    private StudentData birthDay;
    private StudentDataStringList residenceCountry;
    private StudentDataStringList citizenship;
    
    public StudentDataString getFirstname() {
        return firstname;
    }
    public void setFirstname(StudentDataString firstname) {
        this.firstname = firstname;
    }
    public StudentDataString getLastname() {
        return lastname;
    }
    public void setLastname(StudentDataString lastname) {
        this.lastname = lastname;
    }
    public StudentDataBoolean getFullname() {
        return fullname;
    }
    public void setFullname(StudentDataBoolean fullname) {
        this.fullname = fullname;
    }
    public StudentDataString getSex() {
        return sex;
    }
    public void setSex(StudentDataString sex) {
        this.sex = sex;
    }
    public StudentDataString getIdcode() {
        return idcode;
    }
    public void setIdcode(StudentDataString idcode) {
        this.idcode = idcode;
    }
    public StudentDataString getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(StudentDataString bankAccount) {
        this.bankAccount = bankAccount;
    }
    public StudentData getBirthDay() {
        return birthDay;
    }
    public void setBirthDay(StudentData birthDay) {
        this.birthDay = birthDay;
    }
    public StudentDataStringList getResidenceCountry() {
        return residenceCountry;
    }
    public void setResidenceCountry(StudentDataStringList residenceCountry) {
        this.residenceCountry = residenceCountry;
    }
    public StudentDataStringList getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(StudentDataStringList citizenship) {
        this.citizenship = citizenship;
    }
}
