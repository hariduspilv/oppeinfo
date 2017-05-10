package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class JournalEntryCapacityType extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private JournalEntry journalEntry;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier capacityType;

    public JournalEntry getJournalEntry() {
        return journalEntry;
    }

    public void setJournalEntry(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
    }

    public Classifier getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(Classifier capacityType) {
        this.capacityType = capacityType;
    }
}
