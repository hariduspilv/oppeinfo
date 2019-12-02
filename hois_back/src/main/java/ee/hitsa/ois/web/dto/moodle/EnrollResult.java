package ee.hitsa.ois.web.dto.moodle;

import java.util.ArrayList;
import java.util.List;

public class EnrollResult {

    private Integer enrolled;
    private List<String> failed;
    private List<String> missingUser;
    private List<String> missingIdcode = new ArrayList<>();
    
    public Integer getEnrolled() {
        return enrolled;
    }
    public void setEnrolled(Integer enrolled) {
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
    
    public List<String> getMissingIdcode() {
        return missingIdcode;
    }
    public void setMissingIdcode(List<String> missingIdcode) {
        this.missingIdcode = missingIdcode;
    }
    
}
