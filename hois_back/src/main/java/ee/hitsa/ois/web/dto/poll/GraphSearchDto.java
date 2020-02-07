package ee.hitsa.ois.web.dto.poll;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class GraphSearchDto {
    
    private AutocompleteResult theme;
    private QuestionDto question;
    private AutocompleteResult questionAnswer;
    private List<Long> responseIds;
    private Long answerNr;
    private Long answers;
    private Long minNr;
    private Long maxNr;
    private Long responseSubjectId;
    private Boolean isTeacher;
    private Boolean isRepetitive;
    private AutocompleteResult teacher;
    private AutocompleteResult subjectOrJournal;
    private List<String> textAnswers;
    
    public AutocompleteResult getTheme() {
        return theme;
    }
    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }
    public QuestionDto getQuestion() {
        return question;
    }
    public void setQuestion(QuestionDto question) {
        this.question = question;
    }
    public Long getMinNr() {
        return minNr;
    }
    public void setMinNr(Long minNr) {
        this.minNr = minNr;
    }
    public Long getMaxNr() {
        return maxNr;
    }
    public void setMaxNr(Long maxNr) {
        this.maxNr = maxNr;
    }
    public AutocompleteResult getQuestionAnswer() {
        return questionAnswer;
    }
    public void setQuestionAnswer(AutocompleteResult questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
    public Long getAnswerNr() {
        return answerNr;
    }
    public void setAnswerNr(Long answerNr) {
        this.answerNr = answerNr;
    }
    public Long getAnswers() {
        return answers;
    }
    public void setAnswers(Long answers) {
        this.answers = answers;
    }
    public List<Long> getResponseIds() {
        return responseIds;
    }
    public void setResponseIds(List<Long> responseIds) {
        this.responseIds = responseIds;
    }
    public Long getResponseSubjectId() {
        return responseSubjectId;
    }
    public void setResponseSubjectId(Long responseSubjectId) {
        this.responseSubjectId = responseSubjectId;
    }
    public AutocompleteResult getSubjectOrJournal() {
        return subjectOrJournal;
    }
    public void setSubjectOrJournal(AutocompleteResult subjectOrJournal) {
        this.subjectOrJournal = subjectOrJournal;
    }
    public Boolean getIsTeacher() {
        return isTeacher;
    }
    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
    public Boolean getIsRepetitive() {
        return isRepetitive;
    }
    public void setIsRepetitive(Boolean isRepetitive) {
        this.isRepetitive = isRepetitive;
    }
    public List<String> getTextAnswers() {
        return textAnswers;
    }
    public void setTextAnswers(List<String> textAnswers) {
        this.textAnswers = textAnswers;
    }
}
