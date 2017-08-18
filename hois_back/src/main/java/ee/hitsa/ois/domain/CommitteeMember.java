package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class CommitteeMember extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Committee committee;

    private Boolean isExternal;
    private Boolean isChairman;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;
    private String memberName;
    
    @Transient
    public String getMemberFullname() {
        return this.getTeacher() != null ? this.getTeacher().getPerson().getFullname() : memberName;
    }
    
    public Committee getCommittee() {
        return committee;
    }
    public void setCommittee(Committee committee) {
        this.committee = committee;
    }
    public Boolean getIsExternal() {
        return isExternal;
    }
    public void setIsExternal(Boolean isExternal) {
        this.isExternal = isExternal;
    }
    public Boolean getIsChairman() {
        return isChairman;
    }
    public void setIsChairman(Boolean isChairman) {
        this.isChairman = isChairman;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
