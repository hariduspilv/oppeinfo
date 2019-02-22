package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.StudyPeriod;

@Entity
public class JournalTeacherCapacity extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private JournalTeacher journalTeacher;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private JournalCapacityType journalCapacityType;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyPeriod studyPeriod;
    private Short weekNr;
    private Short hours;

    public JournalTeacher getJournalTeacher() {
        return journalTeacher;
    }

    public void setJournalTeacher(JournalTeacher journalTeacher) {
        this.journalTeacher = journalTeacher;
    }

    public JournalCapacityType getJournalCapacityType() {
        return journalCapacityType;
    }

    public void setJournalCapacityType(JournalCapacityType journalCapacityType) {
        this.journalCapacityType = journalCapacityType;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Short getWeekNr() {
        return weekNr;
    }

    public void setWeekNr(Short weekNr) {
        this.weekNr = weekNr;
    }

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }

}
