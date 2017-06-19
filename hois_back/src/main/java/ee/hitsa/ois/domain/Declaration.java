package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.student.Student;

@Entity
public class Declaration extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Classifier status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private StudyPeriod studyPeriod;

    private LocalDate confirmDate;
    private String confirmer;

    @OneToMany(mappedBy = "declaration", fetch = FetchType.LAZY)
    private Set<DeclarationSubject> subjects;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public Set<DeclarationSubject> getSubjects() {
        return subjects != null ? subjects : (subjects = new HashSet<>());
    }

    public void setSubjects(Set<DeclarationSubject> subjects) {
        this.subjects = subjects;
    }
}
