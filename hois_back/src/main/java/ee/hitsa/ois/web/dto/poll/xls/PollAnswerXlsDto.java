package ee.hitsa.ois.web.dto.poll.xls;

public class PollAnswerXlsDto {
    /** can be null */
    private Long answerId;
    private Long questionId;
    /** can be null */
    private Short weight;
    /** only used for text answers */
    private String answerTxt;
    
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public Short getWeight() {
        return weight;
    }
    public void setWeight(Short weight) {
        this.weight = weight;
    }
    public String getAnswerTxt() {
        return answerTxt;
    }
    public void setAnswerTxt(String answerTxt) {
        this.answerTxt = answerTxt;
    }
    public Long getAnswerId() {
        return answerId;
    }
    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

}
