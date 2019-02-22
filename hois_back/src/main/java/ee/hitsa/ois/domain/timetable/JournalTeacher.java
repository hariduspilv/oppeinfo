package ee.hitsa.ois.domain.timetable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class JournalTeacher extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Journal journal;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Teacher teacher;
    private Boolean isFiller;
    private Boolean isConfirmer;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_teacher_id", nullable = false, updatable = false)
    private List<JournalTeacherCapacity> journalTeacherCapacities;

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

    public List<JournalTeacherCapacity> getJournalTeacherCapacities() {
        return journalTeacherCapacities != null ? journalTeacherCapacities : (journalTeacherCapacities = new ArrayList<>());
    }

    public void setJournalTeacherCapacities(List<JournalTeacherCapacity> journalTeacherCapacities) {
        this.journalTeacherCapacities = journalTeacherCapacities;
    }

}
