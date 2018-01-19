package ee.hitsa.ois.web.dto.scholarship;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.util.EntityUtil;

public class ScholarshipStudentRejectionDto {
    private Long id;
    private String fullName;
    private String idCode;
    private String rejectComment;
    
    public static ScholarshipStudentRejectionDto of(ScholarshipApplication application) {
        ScholarshipStudentRejectionDto dto = new ScholarshipStudentRejectionDto();
        dto.setId(EntityUtil.getId(application));
        dto.setFullName(application.getStudent().getPerson().getFullname());
        dto.setIdCode(application.getStudent().getPerson().getIdcode());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

}
