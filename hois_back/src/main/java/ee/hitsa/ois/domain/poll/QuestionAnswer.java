package ee.hitsa.ois.domain.poll;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class QuestionAnswer extends BaseEntityWithId {
    
    private String nameEt;
    private String nameEn;
    private Short orderNr;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Question question;
    /** Vastuse kaal statistika jaoks */
    private Short answerNr;
    
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
    public Short getAnswerNr() {
        return answerNr;
    }
    public void setAnswerNr(Short answerNr) {
        this.answerNr = answerNr;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    

}
