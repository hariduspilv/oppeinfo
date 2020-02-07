package ee.hitsa.ois.domain.timetable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

import java.util.List;

@Entity
public class JournalCapacityType extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Journal journal;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Classifier capacityType;

    @OneToMany(mappedBy="journalCapacityType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalTeacherCapacity> journalTeacherCapacities;

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Classifier getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(Classifier capacityType) {
        this.capacityType = capacityType;
    }

    public List<JournalTeacherCapacity> getJournalTeacherCapacities() {
        return journalTeacherCapacities;
    }

    public void setJournalTeacherCapacities(List<JournalTeacherCapacity> journalTeacherCapacities) {
        this.journalTeacherCapacities = journalTeacherCapacities;
    }
}
