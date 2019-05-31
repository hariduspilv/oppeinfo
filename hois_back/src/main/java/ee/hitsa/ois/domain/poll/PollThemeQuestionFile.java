package ee.hitsa.ois.domain.poll;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.OisFile;

@Entity
public class PollThemeQuestionFile extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PollThemeQuestion pollThemeQuestion;
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private OisFile oisFile;
    
    public PollThemeQuestion getPollThemeQuestion() {
        return pollThemeQuestion;
    }
    public void setPollThemeQuestion(PollThemeQuestion pollThemeQuestion) {
        this.pollThemeQuestion = pollThemeQuestion;
    }
    public OisFile getOisFile() {
        return oisFile;
    }
    public void setOisFile(OisFile oisFile) {
        this.oisFile = oisFile;
    }

}
