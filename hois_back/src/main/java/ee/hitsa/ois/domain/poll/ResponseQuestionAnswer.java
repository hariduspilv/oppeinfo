package ee.hitsa.ois.domain.poll;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class ResponseQuestionAnswer extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Response response;
    /** Vastus */
    private String answerTxt;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Question question;
    /** Vastuse kaal */
    private Short answerNr;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private QuestionAnswer questionAnswer;
    
    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }
    public String getAnswerTxt() {
        return answerTxt;
    }
    public void setAnswerTxt(String answerTxt) {
        this.answerTxt = answerTxt;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public Short getAnswerNr() {
        return answerNr;
    }
    public void setAnswerNr(Short answerNr) {
        this.answerNr = answerNr;
    }
    public QuestionAnswer getQuestionAnswer() {
        return questionAnswer;
    }
    public void setQuestionAnswer(QuestionAnswer questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
