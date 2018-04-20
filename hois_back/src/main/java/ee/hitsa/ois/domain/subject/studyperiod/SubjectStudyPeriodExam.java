package ee.hitsa.ois.domain.subject.studyperiod;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.timetable.TimetableEvent;

@Entity
public class SubjectStudyPeriodExam extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private SubjectStudyPeriod subjectStudyPeriod;
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false, updatable = false)
    private TimetableEvent timetableEvent;
    private LocalDateTime deadline;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;
    private Long places;
    private String addInfo;
    @OneToMany(mappedBy = "subjectStudyPeriodExam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectStudyPeriodExamStudent> students;

    public SubjectStudyPeriod getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public TimetableEvent getTimetableEvent() {
        return timetableEvent;
    }

    public void setTimetableEvent(TimetableEvent timetableEvent) {
        this.timetableEvent = timetableEvent;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }

    public Long getPlaces() {
        return places;
    }

    public void setPlaces(Long places) {
        this.places = places;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public List<SubjectStudyPeriodExamStudent> getStudents() {
        return students;
    }

    public void setStudents(List<SubjectStudyPeriodExamStudent> students) {
        this.students = students;
    }
}
