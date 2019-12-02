package ee.hitsa.ois.web.dto.poll;

public class QuestionResponseDto extends QuestionDto {
    
    private String answerTxt;

    @Override
    public String getAnswerTxt() {
        return answerTxt;
    }

    @Override
    public void setAnswerTxt(String answerTxt) {
        this.answerTxt = answerTxt;
    }
}
