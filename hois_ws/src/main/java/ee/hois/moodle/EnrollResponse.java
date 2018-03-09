package ee.hois.moodle;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollResponse {
    private List<String> exists;
    private List<String> enrolled;
    private List<String> failed;
    @JsonProperty("missing_user")
    private List<String> missingUser;
    
    public List<String> getExists() {
        return exists;
    }
    public void setExists(List<String> exists) {
        this.exists = exists;
    }
    
    public List<String> getEnrolled() {
        return enrolled;
    }
    public void setEnrolled(List<String> enrolled) {
        this.enrolled = enrolled;
    }
    
    public List<String> getFailed() {
        return failed;
    }
    public void setFailed(List<String> failed) {
        this.failed = failed;
    }
    
    public List<String> getMissingUser() {
        return missingUser;
    }
    public void setMissingUser(List<String> missingUser) {
        this.missingUser = missingUser;
    }
    
}
