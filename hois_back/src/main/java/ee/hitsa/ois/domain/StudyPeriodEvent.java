package ee.hitsa.ois.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class StudyPeriodEvent extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyYear studyYear;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyPeriod studyPeriod;
    private String descriptionEt;
    private String descriptionEn;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier eventType;
    private LocalDateTime start;
    @Column(name = "\"end\"")
    private LocalDateTime end;

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public String getDescriptionEt() {
        return descriptionEt;
    }

    public void setDescriptionEt(String descriptionEt) {
        this.descriptionEt = descriptionEt;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public Classifier getEventType() {
        return eventType;
    }

    public void setEventType(Classifier eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
