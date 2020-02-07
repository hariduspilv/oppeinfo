package ee.hitsa.ois.web.dto.poll;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class AutocompleteTeacher extends AutocompleteResult {
    
    private List<PollQuestionResultDto> questions = new ArrayList<>();

    public AutocompleteTeacher(Long id, String nameEt, String nameEn) {
        super(id, nameEt, nameEn);
    }

    public AutocompleteTeacher() {}

    public List<PollQuestionResultDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<PollQuestionResultDto> questions) {
        this.questions = questions;
    }
}
