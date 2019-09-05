package ee.hitsa.ois.web.dto.poll;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectAnswerDto {
    
    private AutocompleteResult poll;
    private AutocompleteResult name;
    private AutocompleteResult yearCode;
    private Long answers;
    private LocalDate startDate;
    private LocalDate endDate;
    
    public AutocompleteResult getName() {
        return name;
    }
    public void setName(AutocompleteResult name) {
        this.name = name;
    }
    public Long getAnswers() {
        return answers;
    }
    public void setAnswers(Long answers) {
        this.answers = answers;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public AutocompleteResult getPoll() {
        return poll;
    }
    public void setPoll(AutocompleteResult poll) {
        this.poll = poll;
    }
    public AutocompleteResult getYearCode() {
        return yearCode;
    }
    public void setYearCode(AutocompleteResult yearCode) {
        this.yearCode = yearCode;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

}
