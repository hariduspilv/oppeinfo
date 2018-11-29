package ee.hitsa.ois.web.dto.scholarship;

import java.util.List;

import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipDecisionForm;

public class ScholarshipDecisionDto extends ScholarshipDecisionForm {

    private Long id;
    private Long committeeId;
    private String committeeName;
    private List<ScholarshipApplicationSearchDto> applications;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCommitteeId() {
        return committeeId;
    }
    public void setCommitteeId(Long committeeId) {
        this.committeeId = committeeId;
    }
    public String getCommitteeName() {
        return committeeName;
    }
    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }
    public List<ScholarshipApplicationSearchDto> getApplications() {
        return applications;
    }
    public void setApplications(List<ScholarshipApplicationSearchDto> applications) {
        this.applications = applications;
    }
    
}
