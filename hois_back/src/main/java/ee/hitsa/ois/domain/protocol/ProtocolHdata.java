package ee.hitsa.ois.domain.protocol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import ee.hitsa.ois.domain.BaseEntity;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;

@Entity
public class ProtocolHdata extends BaseEntity {
    
    @Id
    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "protocol"))
    @GeneratedValue(generator = "generator")
    @Column(unique = true, nullable = false)
    private Long protocolId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Protocol protocol;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private SubjectStudyPeriod subjectStudyPeriod;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Curriculum curriculum;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Subject finalSubject;

    public Protocol getProtocol() {
        return protocol;
    }
    
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
    
    public SubjectStudyPeriod getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    
    public void setSubjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
    
    public Classifier getType() {
        return type;
    }
    
    public void setType(Classifier type) {
        this.type = type;
    }
    
    public Curriculum getCurriculum() {
        return curriculum;
    }
    
    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public Subject getFinalSubject() {
        return finalSubject;
    }

    public void setFinalSubject(Subject finalSubject) {
        this.finalSubject = finalSubject;
    }
    
}
