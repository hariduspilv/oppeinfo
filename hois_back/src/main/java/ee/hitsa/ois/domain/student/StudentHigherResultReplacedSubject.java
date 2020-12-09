package ee.hitsa.ois.domain.student;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.subject.Subject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StudentHigherResultReplacedSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudentHigherResult studentHigherResult;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Subject subject;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersionHigherModule curriculumVersionHmodule;

    private Boolean isOptional;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentHigherResult getStudentHigherResult() {
        return studentHigherResult;
    }

    public void setStudentHigherResult(StudentHigherResult studentHigherResult) {
        this.studentHigherResult = studentHigherResult;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public CurriculumVersionHigherModule getCurriculumVersionHmodule() {
        return curriculumVersionHmodule;
    }

    public void setCurriculumVersionHmodule(CurriculumVersionHigherModule curriculumVersionHmodule) {
        this.curriculumVersionHmodule = curriculumVersionHmodule;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }
}
