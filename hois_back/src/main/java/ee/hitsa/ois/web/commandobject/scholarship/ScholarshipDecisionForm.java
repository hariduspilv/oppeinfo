package ee.hitsa.ois.web.commandobject.scholarship;

import java.time.LocalDate;
import java.util.List;

public class ScholarshipDecisionForm {

    private String protocolNr;
    private LocalDate decided;
    private String addInfo;
    private List<Long> presentCommitteeMembers;
    private List<Long> applicationIds;
    
    public String getProtocolNr() {
        return protocolNr;
    }
    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }
    public LocalDate getDecided() {
        return decided;
    }
    public void setDecided(LocalDate decided) {
        this.decided = decided;
    }
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    public List<Long> getPresentCommitteeMembers() {
        return presentCommitteeMembers;
    }
    public void setPresentCommitteeMembers(List<Long> presentCommitteeMembers) {
        this.presentCommitteeMembers = presentCommitteeMembers;
    }
    public List<Long> getApplicationIds() {
        return applicationIds;
    }
    public void setApplicationIds(List<Long> applicationIds) {
        this.applicationIds = applicationIds;
    }
    
}
