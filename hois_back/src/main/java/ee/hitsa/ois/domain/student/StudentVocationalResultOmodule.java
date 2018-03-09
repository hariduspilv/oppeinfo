package ee.hitsa.ois.domain.student;

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
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;

@Entity
public class StudentVocationalResultOmodule extends BaseEntity {
    
    @Id
    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "studentVocationalResult"))
    @GeneratedValue(generator = "generator")
    @Column(unique = true, nullable = false)
    private Long studentVocationalResultId;
    
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
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
