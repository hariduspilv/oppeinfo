package ee.hitsa.ois.web.dto.scholarship;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ScholarshipApplicationSearchDto extends ScholarshipApplicationBaseDto {
    private Long place;
    private String type;
    private Long term;
    private String termNameEt;
    private String curriculumCode;
    private String studentGroup;
    private Long studentId;
    private String studentName;
    private String firstName;
    private String lastName;
    private String idcode;
    private String bankAccountOwnerIdcode;
    private BigDecimal credits;
    private Boolean isTeacherConfirm;
    private String rejectComment;
    private Boolean needsConfirm;
    private Long decisionId;
    private LocalDate decisionDecided;
    private Boolean hasDirective;
    private Boolean canViewStudent = Boolean.TRUE;

    private String compensationReason;
    private String compensationFrequency;

    public Long getPlace() {
        return place;
    }

    public void setPlace(Long place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    public String getTermNameEt() {
        return termNameEt;
    }

    public void setTermNameEt(String termNameEt) {
        this.termNameEt = termNameEt;
    }

    public String getCurriculumCode() {
        return curriculumCode;
    }

    public void setCurriculumCode(String curriculumCode) {
        this.curriculumCode = curriculumCode;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getBankAccountOwnerIdcode() {
        return bankAccountOwnerIdcode;
    }

    public void setBankAccountOwnerIdcode(String bankAccountOwnerIdcode) {
        this.bankAccountOwnerIdcode = bankAccountOwnerIdcode;
    }

    public Boolean getIsTeacherConfirm() {
        return isTeacherConfirm;
    }

    public void setIsTeacherConfirm(Boolean isTeacherConfirm) {
        this.isTeacherConfirm = isTeacherConfirm;
    }

    public String getCompensationReason() {
        return compensationReason;
    }

    public void setCompensationReason(String compensationReason) {
        this.compensationReason = compensationReason;
    }

    public String getCompensationFrequency() {
        return compensationFrequency;
    }

    public void setCompensationFrequency(String compensationFrequency) {
        this.compensationFrequency = compensationFrequency;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }
    
    public void setNeedsConfirm(Boolean needsConfirm) {
    	this.needsConfirm = needsConfirm;
    }
    
    public Boolean getNeedsConfirm() {
    	return this.needsConfirm;
    }

    public Long getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(Long decisionId) {
        this.decisionId = decisionId;
    }

    public LocalDate getDecisionDecided() {
        return decisionDecided;
    }

    public void setDecisionDecided(LocalDate decisionDecided) {
        this.decisionDecided = decisionDecided;
    }

    public Boolean getHasDirective() {
        return hasDirective;
    }

    public void setHasDirective(Boolean hasDirective) {
        this.hasDirective = hasDirective;
    }

    public Boolean getCanViewStudent() {
        return canViewStudent;
    }

    public void setCanViewStudent(Boolean canViewStudent) {
        this.canViewStudent = canViewStudent;
    }

}
