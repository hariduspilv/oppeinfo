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
public class PollTheme extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Poll poll;
    private String nameEt;
    private String nameEn;
    private Short orderNr;
    private Boolean isRepetitive;
    private Boolean isTeacher;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_theme_id", nullable = false, updatable = false, insertable = false)
    private List<PollThemeQuestion> pollThemeQuestions = new ArrayList<>();
    
    public Poll getPoll() {
        return poll;
    }
    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public Short getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Short orderNr) {
        this.orderNr = orderNr;
    }
    public List<PollThemeQuestion> getPollThemeQuestions() {
        return pollThemeQuestions;
    }
    public void setPollThemeQuestions(List<PollThemeQuestion> pollThemeQuestions) {
        this.pollThemeQuestions = pollThemeQuestions;
    }
    public Boolean getIsRepetitive() {
        return isRepetitive;
    }
    public void setIsRepetitive(Boolean isRepetitive) {
        this.isRepetitive = isRepetitive;
    }
    public Boolean getIsTeacher() {
        return isTeacher;
    }
    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

}
