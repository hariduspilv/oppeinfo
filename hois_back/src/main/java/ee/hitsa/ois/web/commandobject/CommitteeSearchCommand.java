package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

public class CommitteeSearchCommand {

    private String type;
    private String nameEt;
    private String memberName;
    private Long teacher;
    private EntityConnectionCommand person;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean showInvalid = Boolean.FALSE;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    public EntityConnectionCommand getPerson() {
        return person;
    }
    public void setPerson(EntityConnectionCommand person) {
        this.person = person;
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
