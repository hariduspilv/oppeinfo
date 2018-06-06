package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class FinalThesisSupervisorForm extends InsertedChangedVersionDto {

    private Long id;
    @NotNull
    private Boolean isPrimary;
    @NotNull
    private Boolean isExternal;
    private AutocompleteResult teacher;
    private String firstname;
    private String lastname;
    private String idcode;
    private String occupation;
    private String email;
    private String phone;
    private String bankaccount;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }
    
    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
    
    public Boolean getIsExternal() {
        return isExternal;
    }
    
    public void setIsExternal(Boolean isExternal) {
        this.isExternal = isExternal;
    }
    
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
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
    
    public String getIdcode() {
        return idcode;
    }
    
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    
    public String getOccupation() {
        return occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
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
    
    public String getBankaccount() {
        return bankaccount;
    }
    
    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }
    
}
