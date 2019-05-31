package ee.hitsa.ois.web.commandobject.poll;

import java.util.List;

import ee.hitsa.ois.web.dto.poll.QuestionDto;

public class QuestionOrderCommand {
    
    private List<QuestionDto> questions;

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
