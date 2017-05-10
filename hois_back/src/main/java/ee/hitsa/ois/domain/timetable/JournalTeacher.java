package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class JournalTeacher extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Journal journal;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Teacher teacher;
    private Boolean isFiller;
    private Boolean isConfirmer;

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Boolean getIsFiller() {
        return isFiller;
    }

    public void setIsFiller(Boolean isFiller) {
        this.isFiller = isFiller;
    }

    public Boolean getIsConfirmer() {
        return isConfirmer;
    }

    public void setIsConfirmer(Boolean isConfirmer) {
        this.isConfirmer = isConfirmer;
    }
}
