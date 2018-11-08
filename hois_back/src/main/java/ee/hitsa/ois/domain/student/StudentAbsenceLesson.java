package ee.hitsa.ois.domain.student;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class StudentAbsenceLesson extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudentAbsence studentAbsence;
    private LocalDate absence;
    private Long lessonNr;
    
    public StudentAbsence getStudentAbsence() {
        return studentAbsence;
    }
    
    public void setStudentAbsence(StudentAbsence studentAbsence) {
        this.studentAbsence = studentAbsence;
    }
    
    public LocalDate getAbsence() {
        return absence;
    }
    
    public void setAbsence(LocalDate absence) {
        this.absence = absence;
    }
    
    public Long getLessonNr() {
        return lessonNr;
    }
    
    public void setLessonNr(Long lessonNr) {
        this.lessonNr = lessonNr;
    }
    
}
