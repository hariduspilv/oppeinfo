package ee.hitsa.ois.web.dto.document;

public class SupplementStudentDto {
    
    private Long directiveStudentId;
    private String fullname;
    private String diplomaNr;
    private String diplomaStatus;
    private String supplementStatus;
    private String supplementStatusEn;
    
    public Long getDirectiveStudentId() {
        return directiveStudentId;
    }
    public void setDirectiveStudentId(Long directiveStudentId) {
        this.directiveStudentId = directiveStudentId;
    }
    
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

}
