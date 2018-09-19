package ee.hitsa.ois.web.dto.scholarship;

import java.time.LocalDate;

public class ScholarshipApplicationStudentDto extends ScholarshipApplicationBaseDto {
    private Long termId;
    private String type;
    private Boolean isTeacherConfirm;
    private LocalDate decisionDate;
    private String rejectComment;
    private Boolean needsConfirm;
    
    public Long getTermId() {
        return termId;
    }
    public void setTermId(Long termId) {
        this.termId = termId;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public Boolean getIsTeacherConfirm() {
        return isTeacherConfirm;
    }
    public void setIsTeacherConfirm(Boolean isTeacherConfirm) {
        this.isTeacherConfirm = isTeacherConfirm;
    }
    
    public LocalDate getDecisionDate() {
        return decisionDate;
    }
    public void setDecisionDate(LocalDate decisionDate) {
        this.decisionDate = decisionDate;
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
    
}
