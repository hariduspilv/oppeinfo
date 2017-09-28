package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.school.School;

@Entity
public class Committee extends BaseEntityWithId {
    
    private String addInfo;
    private LocalDate validFrom;
    private LocalDate validThru;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private School school;

    @OneToMany(mappedBy = "committee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommitteeMember> members;

    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
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
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    public List<CommitteeMember> getMembers() {
        return members != null ? members : (members = new ArrayList<>());
    }
    public void setMembers(List<CommitteeMember> members) {
        this.members = members;
    }
}
