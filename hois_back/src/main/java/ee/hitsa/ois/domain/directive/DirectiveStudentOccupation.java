package ee.hitsa.ois.domain.directive;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class DirectiveStudentOccupation extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private DirectiveStudent directiveStudent;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier occupation;
    
    public DirectiveStudent getDirectiveStudent() {
        return directiveStudent;
    }
    public void setDirectiveStudent(DirectiveStudent directiveStudent) {
        this.directiveStudent = directiveStudent;
    }
    
    public Classifier getOccupation() {
        return occupation;
    }
    public void setOccupation(Classifier occupation) {
        this.occupation = occupation;
    }
    
}
