package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class JournalStudentApelResultDto {

    private AutocompleteResult name;
    private Boolean isModule;
    private LocalDate confirmed;
    private String grade;
    private Boolean isFormalLearning;

    public AutocompleteResult getName() {
        return name;
    }

    public void setName(AutocompleteResult name) {
        this.name = name;
    }

    public Boolean getIsModule() {
        return isModule;
    }

    public void setIsModule(Boolean isModule) {
        this.isModule = isModule;
    }

    public LocalDate getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(LocalDate confirmed) {
        this.confirmed = confirmed;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Boolean getIsFormalLearning() {
        return isFormalLearning;
    }

    public void setIsFormalLearning(Boolean isFormalLearning) {
        this.isFormalLearning = isFormalLearning;
    }

}
