package ee.hitsa.ois.domain.timetable;

import ee.hitsa.ois.domain.BaseEntityWithId;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class JournalSub extends BaseEntityWithId {

    private Long subJournals;

    @OneToMany(mappedBy = "journalSub", fetch = FetchType.LAZY)
    private List<Journal> journals;

    public Long getSubJournals() {
        return subJournals;
    }

    public void setSubJournals(Long subJournals) {
        this.subJournals = subJournals;
    }

    public List<Journal> getJournals() {
        return journals != null ? journals : (journals = new ArrayList<>());
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }
}
