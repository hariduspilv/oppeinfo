package ee.hitsa.ois.web.dto.document;

import java.time.LocalDate;
import java.util.List;

public class DiplomaSearchDto {

    private Long studentId;
    private String fullname;
    private String firstname;
    private String lastname;
    private String idcode;
    private String merCode;
    private String curriculum;
    private LocalDate studyEnd;
    private String type;
    private Long diplomaId;
    private String diplomaStatus;
    private String diplomaNr;
    private String diplomaCancelledNr;
    private List<Document> diplomaNrs;
    private Long supplementId;
    private Long supplementIdEn;
    private String supplementStatus;
    private String supplementNr;
    private String supplementCancelledNr;
    private List<Document> supplementNrs;
    private String supplementStatusEn;
    private String supplementNrEn;
    private String supplementCancelledNrEn;
    private List<Document> supplementNrsEn;
    
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
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
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }
    public LocalDate getStudyEnd() {
        return studyEnd;
    }
    public void setStudyEnd(LocalDate studyEnd) {
        this.studyEnd = studyEnd;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getDiplomaId() {
        return diplomaId;
    }
    public void setDiplomaId(Long diplomaId) {
        this.diplomaId = diplomaId;
    }
    public String getDiplomaStatus() {
        return diplomaStatus;
    }
    public void setDiplomaStatus(String diplomaStatus) {
        this.diplomaStatus = diplomaStatus;
    }
    public String getDiplomaNr() {
        return diplomaNr;
    }
    public void setDiplomaNr(String diplomaNr) {
        this.diplomaNr = diplomaNr;
    }
    public List<Document> getDiplomaNrs() {
        return diplomaNrs;
    }
    public void setDiplomaNrs(List<Document> diplomaNrs) {
        this.diplomaNrs = diplomaNrs;
    }
    public Long getSupplementId() {
        return supplementId;
    }
    public void setSupplementId(Long supplementId) {
        this.supplementId = supplementId;
    }
    public Long getSupplementIdEn() {
        return supplementIdEn;
    }
    public void setSupplementIdEn(Long supplementIdEn) {
        this.supplementIdEn = supplementIdEn;
    }
    public String getSupplementStatus() {
        return supplementStatus;
    }
    public void setSupplementStatus(String supplementStatus) {
        this.supplementStatus = supplementStatus;
    }
    public List<Document> getSupplementNrs() {
        return supplementNrs;
    }
    public void setSupplementNrs(List<Document> supplementNrs) {
        this.supplementNrs = supplementNrs;
    }
    public String getSupplementStatusEn() {
        return supplementStatusEn;
    }
    public void setSupplementStatusEn(String supplementStatusEn) {
        this.supplementStatusEn = supplementStatusEn;
    }
    public List<Document> getSupplementNrsEn() {
        return supplementNrsEn;
    }
    public void setSupplementNrsEn(List<Document> supplementNrsEn) {
        this.supplementNrsEn = supplementNrsEn;
    }
    public String getSupplementNr() {
        return supplementNr;
    }
    public void setSupplementNr(String supplementNr) {
        this.supplementNr = supplementNr;
    }
    public String getSupplementNrEn() {
        return supplementNrEn;
    }
    public void setSupplementNrEn(String supplementNrEn) {
        this.supplementNrEn = supplementNrEn;
    }
    public String getDiplomaCancelledNr() {
        return diplomaCancelledNr;
    }
    public void setDiplomaCancelledNr(String diplomaCancelledNr) {
        this.diplomaCancelledNr = diplomaCancelledNr;
    }
    public String getSupplementCancelledNr() {
        return supplementCancelledNr;
    }
    public void setSupplementCancelledNr(String supplementCancelledNr) {
        this.supplementCancelledNr = supplementCancelledNr;
    }
    public String getSupplementCancelledNrEn() {
        return supplementCancelledNrEn;
    }
    public void setSupplementCancelledNrEn(String supplementCancelledNrEn) {
        this.supplementCancelledNrEn = supplementCancelledNrEn;
    }

    public static class Document {
        
        private Boolean duplicate;
        private Boolean defected;
        private String fullCode;

        public Boolean getDuplicate() {
            return duplicate;
        }

        public void setDuplicate(Boolean duplicate) {
            this.duplicate = duplicate;
        }

        public Boolean getDefected() {
            return defected;
        }

        public void setDefected(Boolean defected) {
            this.defected = defected;
        }

        public String getFullCode() {
            return fullCode;
        }

        public void setFullCode(String fullCode) {
            this.fullCode = fullCode;
        }
    }
}
