package ee.hitsa.ois.web.dto.document;

import java.util.List;

public class SupplementDto {
    
    private String fullname;
    private String diplomaNr;
    private String diplomaStatus;
    private String supplementStatus;
    private String supplementStatusEn;
    private List<FormDto> forms;
    private List<FormDto> formsEn;
    private List<FormDto> freeExtraForms;
    
    private Boolean isDiplomaDuplicate;
    private Boolean isSupplementDuplicate;
    private Boolean isSupplementEnDuplicate;
    
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
    
    public String getSupplementStatus() {
        return supplementStatus;
    }
    public void setSupplementStatus(String supplementStatus) {
        this.supplementStatus = supplementStatus;
    }

    public String getSupplementStatusEn() {
        return supplementStatusEn;
    }
    public void setSupplementStatusEn(String supplementStatusEn) {
        this.supplementStatusEn = supplementStatusEn;
    }
    
    public List<FormDto> getForms() {
        return forms;
    }
    public void setForms(List<FormDto> forms) {
        this.forms = forms;
    }
    
    public List<FormDto> getFormsEn() {
        return formsEn;
    }
    public void setFormsEn(List<FormDto> formsEn) {
        this.formsEn = formsEn;
    }
    
    public List<FormDto> getFreeExtraForms() {
        return freeExtraForms;
    }
    public void setFreeExtraForms(List<FormDto> freeExtraForms) {
        this.freeExtraForms = freeExtraForms;
    }
    
    public Boolean getIsDiplomaDuplicate() {
        return isDiplomaDuplicate;
    }
    public void setIsDiplomaDuplicate(Boolean isDiplomaDuplicate) {
        this.isDiplomaDuplicate = isDiplomaDuplicate;
    }
    
    public Boolean getIsSupplementDuplicate() {
        return isSupplementDuplicate;
    }
    public void setIsSupplementDuplicate(Boolean isSupplementDuplicate) {
        this.isSupplementDuplicate = isSupplementDuplicate;
    }
    
    public Boolean getIsSupplementEnDuplicate() {
        return isSupplementEnDuplicate;
    }
    public void setIsSupplementEnDuplicate(Boolean isSupplementEnDuplicate) {
        this.isSupplementEnDuplicate = isSupplementEnDuplicate;
    }
    
}
