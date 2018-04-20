package ee.hitsa.ois.web.commandobject.studentgroupyeartransfer;

import java.util.List;
import java.util.Map;

import ee.hitsa.ois.validation.Required;

public class TransferCommand {

    @Required
    private List<Long> logIds;
    private Map<Long, String> newGroupCodes;
    private Map<Long, Long> newStudentGroups;
    
    public List<Long> getLogIds() {
        return logIds;
    }
    public void setLogIds(List<Long> logIds) {
        this.logIds = logIds;
    }
    
    public Map<Long, String> getNewGroupCodes() {
        return newGroupCodes;
    }
    public void setNewGroupCodes(Map<Long, String> newGroupCodes) {
        this.newGroupCodes = newGroupCodes;
    }
    
    public Map<Long, Long> getNewStudentGroups() {
        return newStudentGroups;
    }
    public void setNewStudentGroups(Map<Long, Long> newStudentGroups) {
        this.newStudentGroups = newStudentGroups;
    }
    
}
