package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

public class CommitteeSearchCommand {

    private String memberName;
    private Long teacher;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean showInvalid = Boolean.FALSE;

    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
    public Boolean getShowInvalid() {
        return showInvalid;
    }
    public void setShowInvalid(Boolean showInvalid) {
        this.showInvalid = showInvalid;
    }
    
    
}
