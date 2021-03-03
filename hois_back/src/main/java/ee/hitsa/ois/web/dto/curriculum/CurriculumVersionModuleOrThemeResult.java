package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.GradeDto;

public class CurriculumVersionModuleOrThemeResult {

    private Boolean isModule;
    private Long moduleId;
    private Long themeId;

    private AutocompleteResult module;
    private Long curriculumModuleId;
    private AutocompleteResult theme;
    private BigDecimal credits;
    private Short hours;
    private String assessment;

    private GradeDto grade;
    private LocalDate gradeDate;
    private String teachers;
    private Long journalId;
    private Long practiceJournalId;

    public CurriculumVersionModuleOrThemeResult() { }

    public CurriculumVersionModuleOrThemeResult(Long moduleId, String moduleNameEt, String moduleNameEn,
            BigDecimal credits, String assessment, Long curriculumModuleId, String curriculumVersionCode) {
        this.isModule = Boolean.TRUE;
        this.moduleId = moduleId;
        this.module = moduleResult(moduleNameEt, moduleNameEn, curriculumVersionCode);
        this.credits = credits;
        this.assessment = assessment;
        this.curriculumModuleId = curriculumModuleId;
    }

    public CurriculumVersionModuleOrThemeResult(Long moduleId, String moduleNameEt, String moduleNameEn,
            Long themeId, String themeNameEt, String themeNameEn, BigDecimal credits, Short hours,
            String assessment, String curriculumVersionCode) {
        this.isModule = Boolean.FALSE;
        this.moduleId = moduleId;
        this.themeId = themeId;
        this.module = moduleResult(moduleNameEt, moduleNameEn, curriculumVersionCode);
        this.theme = new AutocompleteResult(themeId, themeNameEt, themeNameEn);
        this.credits = credits;
        this.hours = hours;
        this.assessment = assessment;
    }

    private AutocompleteResult moduleResult(String moduleNameEt, String moduleNameEn, String curriculumVersionCode) {
        curriculumVersionCode = curriculumVersionCode != null ? " (" + curriculumVersionCode + ")" : "";
        return new AutocompleteResult(moduleId, moduleNameEt + curriculumVersionCode,
                (moduleNameEn != null ? moduleNameEn : moduleNameEt) + curriculumVersionCode);
    }

    public Boolean getIsModule() {
        return isModule;
    }

    public void setIsModule(Boolean isModule) {
        this.isModule = isModule;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public Long getCurriculumModuleId() {
        return curriculumModuleId;
    }

    public void setCurriculumModuleId(Long curriculumModuleId) {
        this.curriculumModuleId = curriculumModuleId;
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

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public Long getPracticeJournalId() {
        return practiceJournalId;
    }

    public void setPracticeJournalId(Long practiceJournalId) {
        this.practiceJournalId = practiceJournalId;
    }
}
