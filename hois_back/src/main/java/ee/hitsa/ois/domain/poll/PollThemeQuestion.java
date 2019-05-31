package ee.hitsa.ois.domain.poll;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class PollThemeQuestion extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PollTheme pollTheme;
    private Short orderNr;
    @ManyToOne(cascade = { CascadeType.PERSIST },optional = false, fetch = FetchType.LAZY)
    private Question question;
    private Boolean isRequired;
    private Boolean isInRow;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_theme_question_id", nullable = false, updatable = false, insertable = false)
    private List<PollThemeQuestionFile> pollThemeQuestionFiles = new ArrayList<>();
    
    public PollTheme getPollTheme() {
        return pollTheme;
    }
    public void setPollTheme(PollTheme pollTheme) {
        this.pollTheme = pollTheme;
    }
    public Short getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Short orderNr) {
        this.orderNr = orderNr;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public Boolean getIsInRow() {
        return isInRow;
    }
    public void setIsInRow(Boolean isInRow) {
        this.isInRow = isInRow;
    }
    public Boolean getIsRequired() {
        return isRequired;
    }
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }
    public List<PollThemeQuestionFile> getPollThemeQuestionFiles() {
        return pollThemeQuestionFiles;
    }
    public void setPollThemeQuestionFiles(List<PollThemeQuestionFile> pollThemeQuestionFiles) {
        this.pollThemeQuestionFiles = pollThemeQuestionFiles;
    }

}
