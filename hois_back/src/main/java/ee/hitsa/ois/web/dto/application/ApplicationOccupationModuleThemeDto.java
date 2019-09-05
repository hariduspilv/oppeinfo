package ee.hitsa.ois.web.dto.application;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ApplicationOccupationModuleThemeDto {

    private Long id;
    private Long curriculumVersionOmoduleTheme;
    private AutocompleteResult theme;
    private Boolean isOld;
    private Long curriculumModule;
    private Long journal;
    private Long practiceJournal;

    private String grade;
    private String gradeCode;
    private LocalDate gradeInserted;
    private Boolean covering;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurriculumVersionOmoduleTheme() {
        return curriculumVersionOmoduleTheme;
    }

    public void setCurriculumVersionOmoduleTheme(Long curriculumVersionOmoduleTheme) {
        this.curriculumVersionOmoduleTheme = curriculumVersionOmoduleTheme;
    }

    public AutocompleteResult getTheme() {
        return theme;
    }

    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }

    public Boolean getIsOld() {
        return isOld;
    }

    public void setIsOld(Boolean isOld) {
        this.isOld = isOld;
    }

    public Long getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(Long curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public Long getJournal() {
        return journal;
    }

    public void setJournal(Long journal) {
        this.journal = journal;
    }

    public Long getPracticeJournal() {
        return practiceJournal;
    }

    public void setPracticeJournal(Long practiceJournal) {
        this.practiceJournal = practiceJournal;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public LocalDate getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDate gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

    public Boolean getCovering() {
        return covering;
    }

    public void setCovering(Boolean covering) {
        this.covering = covering;
    }

}
