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
public class QuestionAnswer extends BaseEntityWithId {
    
    private String nameEt;
    private String nameEn;
    private Short orderNr;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Question question;
    /** Answer weight */
    private Short answerNr;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_answer_id", nullable = false, updatable = false, insertable = false)
    private List<ResponseQuestionAnswer> responseQuestionAnswers = new ArrayList<>();
    
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
    public List<ResponseQuestionAnswer> getResponseQuestionAnswers() {
        return responseQuestionAnswers;
    }
    public void setResponseQuestionAnswers(List<ResponseQuestionAnswer> responseQuestionAnswers) {
        this.responseQuestionAnswers = responseQuestionAnswers;
    }
    

}
