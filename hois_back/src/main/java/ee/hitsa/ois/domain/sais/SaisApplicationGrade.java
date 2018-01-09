package ee.hitsa.ois.domain.sais;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.validation.Required;

@Entity
public class SaisApplicationGrade extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    private SaisApplication saisApplication;

    @Size(max = 255)
    @Required
    private String subjectName;

    @Size(max = 100)
    private String subjectType;

    @Size(max = 100)
    @Required
    private String grade;

    public SaisApplication getSaisApplication() {
        return saisApplication;
    }

    public void setSaisApplication(SaisApplication saisApplication) {
        this.saisApplication = saisApplication;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
