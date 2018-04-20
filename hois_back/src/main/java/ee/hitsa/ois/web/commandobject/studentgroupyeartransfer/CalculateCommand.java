package ee.hitsa.ois.web.commandobject.studentgroupyeartransfer;

import java.util.List;
import java.util.Map;

import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.SearchCommand;

public class CalculateCommand extends SearchCommand {

    private Boolean academicLeave;
    private Long academicLeaveDays;
    private Boolean abroadStudies;
    private Long abroadStudiesDays;
    @Required
    private List<Long> studentGroupIds;
    private Map<Long, String> newGroupCodes;
    
    public Boolean getAcademicLeave() {
        return academicLeave;
    }
    public void setAcademicLeave(Boolean academicLeave) {
        this.academicLeave = academicLeave;
    }
    
    public Long getAcademicLeaveDays() {
        return academicLeaveDays;
    }
    public void setAcademicLeaveDays(Long academicLeaveDays) {
        this.academicLeaveDays = academicLeaveDays;
    }
    
    public Boolean getAbroadStudies() {
        return abroadStudies;
    }
    public void setAbroadStudies(Boolean abroadStudies) {
        this.abroadStudies = abroadStudies;
    }
    
    public Long getAbroadStudiesDays() {
        return abroadStudiesDays;
    }
    public void setAbroadStudiesDays(Long abroadStudiesDays) {
        this.abroadStudiesDays = abroadStudiesDays;
    }
    
    public List<Long> getStudentGroupIds() {
        return studentGroupIds;
    }
    public void setStudentGroupIds(List<Long> studentGroupIds) {
        this.studentGroupIds = studentGroupIds;
    }

    public Map<Long, String> getNewGroupCodes() {
        return newGroupCodes;
    }
    public void setNewGroupCodes(Map<Long, String> newGroupCodes) {
        this.newGroupCodes = newGroupCodes;
    }
    
}
