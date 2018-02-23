package ee.hitsa.ois.domain.student;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntity;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;

@Entity
public class StudentVocationalResultOmodule extends BaseEntity {
    
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudentVocationalResult studentVocationalResult;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = true)
    private CurriculumVersionOccupationModule curriculumVersionOmodule;

    public StudentVocationalResult getStudentVocationalResult() {
        return studentVocationalResult;
    }

    public void setStudentVocationalResult(StudentVocationalResult studentVocationalResult) {
        this.studentVocationalResult = studentVocationalResult;
    }

    public CurriculumVersionOccupationModule getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(CurriculumVersionOccupationModule curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

}
