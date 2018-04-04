package ee.hitsa.ois.domain.protocol;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;

@Entity
public class ProtocolStudentOccupation extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private ProtocolStudent protocolStudent;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private StudentOccupationCertificate studentOccupationCertificate;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier occupation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier partOccupation;
    
    public ProtocolStudent getProtocolStudent() {
        return protocolStudent;
    }
    
    public void setProtocolStudent(ProtocolStudent protocolStudent) {
        this.protocolStudent = protocolStudent;
    }
    
    public StudentOccupationCertificate getStudentOccupationCertificate() {
        return studentOccupationCertificate;
    }
    
    public void setStudentOccupationCertificate(StudentOccupationCertificate studentOccupationCertificate) {
        this.studentOccupationCertificate = studentOccupationCertificate;
    }
    
    public Classifier getOccupation() {
        return occupation;
    }
    
    public void setOccupation(Classifier occupation) {
        this.occupation = occupation;
    }
    
    public Classifier getPartOccupation() {
        return partOccupation;
    }
    
    public void setPartOccupation(Classifier partOccupation) {
        this.partOccupation = partOccupation;
    }
    
}
