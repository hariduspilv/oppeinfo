package ee.hitsa.ois.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PracticeJournalFile extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private OisFile oisFile;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private PracticeJournal practiceJournal;
    
    private Boolean isStudent;

    public OisFile getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFile oisFile) {
        this.oisFile = oisFile;
    }

    public PracticeJournal getPracticeJournal() {
        return practiceJournal;
    }

    public void setPracticeJournal(PracticeJournal practiceJournal) {
        this.practiceJournal = practiceJournal;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

}
