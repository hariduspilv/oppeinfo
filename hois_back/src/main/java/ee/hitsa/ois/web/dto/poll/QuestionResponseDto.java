package ee.hitsa.ois.web.dto.poll;

public class QuestionResponseDto extends QuestionDto {
    
    private String answerTxt;

    public String getAnswerTxt() {
        return answerTxt;
    }

    public void setAnswerTxt(String answerTxt) {
        this.answerTxt = answerTxt;
    }
}
