package ee.hitsa.ois.web.dto.api;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class StudentBaseDto {
    
    private String firstname;
    private String lastname;
    private String idcode;
    private String uqcode;
    private String email;
    private String birthdate;
    private String curriculumMerCode;
    private String curriculumNameEt;
    private String curriculumVersionCode;
    
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
    public String getUqcode() {
        return uqcode;
    }
    public void setUqcode(String uqcode) {
        this.uqcode = uqcode;
    }
    public String getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    public String getCurriculumMerCode() {
        return curriculumMerCode;
    }
    public void setCurriculumMerCode(String curriculumMerCode) {
        this.curriculumMerCode = curriculumMerCode;
    }
    public String getCurriculumNameEt() {
        return curriculumNameEt;
    }
    public void setCurriculumNameEt(String curriculumNameEt) {
        this.curriculumNameEt = curriculumNameEt;
    }
    public String getCurriculumVersionCode() {
        return curriculumVersionCode;
    }
    public void setCurriculumVersionCode(String curriculumVersionCode) {
        this.curriculumVersionCode = curriculumVersionCode;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
