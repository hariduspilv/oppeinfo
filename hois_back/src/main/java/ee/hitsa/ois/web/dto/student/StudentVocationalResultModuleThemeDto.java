package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentVocationalResultModuleThemeDto {

    private Long curriculumVersionModuleId;
    private AutocompleteResult module;
    private AutocompleteResult theme;
    private BigDecimal credits;
    private String grade;
    private LocalDate date;
    private List<AutocompleteResult> teachers = new ArrayList<>();
    private String studyYear;
    private LocalDate studyYearStartDate;
    private Boolean isApelTransfer;
    private Boolean isFormalLearning;
    private Boolean isCurrentCurriculumVersionResult; 
    private List<Long> replacedModules;

    public Long getCurriculumVersionModuleId() {
        return curriculumVersionModuleId;
    }

    public void setCurriculumVersionModuleId(Long curriculumVersionModuleId) {
        this.curriculumVersionModuleId = curriculumVersionModuleId;
    }

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public AutocompleteResult getTheme() {
        return theme;
    }

    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<AutocompleteResult> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<AutocompleteResult> teachers) {
        this.teachers = teachers;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }

    public LocalDate getStudyYearStartDate() {
        return studyYearStartDate;
    }

    public void setStudyYearStartDate(LocalDate studyYearStartDate) {
        this.studyYearStartDate = studyYearStartDate;
    }

    public Boolean getIsApelTransfer() {
        return isApelTransfer;
    }

    public void setIsApelTransfer(Boolean isApelTransfer) {
        this.isApelTransfer = isApelTransfer;
    }

    public Boolean getIsFormalLearning() {
        return isFormalLearning;
    }

    public void setIsFormalLearning(Boolean isFormalLearning) {
        this.isFormalLearning = isFormalLearning;
    }

    public Boolean getIsCurrentCurriculumVersionResult() {
        return isCurrentCurriculumVersionResult;
    }

    public void setIsCurrentCurriculumVersionResult(Boolean isCurrentCurriculumVersionResult) {
        this.isCurrentCurriculumVersionResult = isCurrentCurriculumVersionResult;
    }

    public List<Long> getReplacedModules() {
        return replacedModules;
    }

    public void setReplacedModules(List<Long> replacedModules) {
        this.replacedModules = replacedModules;
    }
}
