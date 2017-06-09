package ee.hitsa.ois.domain.protocol;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class ProtocolStudentHistory extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private ProtocolStudent protocolStudent;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier grade;

    @Size(max = 255)
    private String addInfo;

    public ProtocolStudent getProtocolStudent() {
        return protocolStudent;
    }

    public void setProtocolStudent(ProtocolStudent protocolStudent) {
        this.protocolStudent = protocolStudent;
    }

    public Classifier getGrade() {
        return grade;
    }

    public void setGrade(Classifier grade) {
        this.grade = grade;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

}
