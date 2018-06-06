package ee.hitsa.ois.web.dto.document;

import java.util.List;

public class SupplementDto {
    
    private String fullname;
    private String diplomaNr;
    private String diplomaStatus;
    private List<FormDto> freeForms;
    private List<FormDto> freeExtraForms;
    
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    public String getDiplomaNr() {
        return diplomaNr;
    }
    public void setDiplomaNr(String diplomaNr) {
        this.diplomaNr = diplomaNr;
    }
    
    public String getDiplomaStatus() {
        return diplomaStatus;
    }
    public void setDiplomaStatus(String diplomaStatus) {
        this.diplomaStatus = diplomaStatus;
    }
    
    public List<FormDto> getFreeForms() {
        return freeForms;
    }
    public void setFreeForms(List<FormDto> freeForms) {
        this.freeForms = freeForms;
    }
    
    public List<FormDto> getFreeExtraForms() {
        return freeExtraForms;
    }
    public void setFreeExtraForms(List<FormDto> freeExtraForms) {
        this.freeExtraForms = freeExtraForms;
    }
    
}
