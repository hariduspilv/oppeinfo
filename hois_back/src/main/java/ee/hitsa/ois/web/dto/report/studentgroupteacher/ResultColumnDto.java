package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeResult;

public class ResultColumnDto {

    private AutocompleteResult journal;
    private AutocompleteResult practiceModuleTheme;
    private AutocompleteResult fullPracticeModule;
    private CurriculumModuleOutcomeResult outcome;
    private AutocompleteResult module;
    private Long moduleId;
    private Boolean intendedModule = Boolean.TRUE;

    public AutocompleteResult getJournal() {
        return journal;
    }

    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
    }

    public AutocompleteResult getPracticeModuleTheme() {
        return practiceModuleTheme;
    }

    public void setPracticeModuleTheme(AutocompleteResult practiceModuleTheme) {
        this.practiceModuleTheme = practiceModuleTheme;
    }

    public AutocompleteResult getFullPracticeModule() {
        return fullPracticeModule;
    }

    public void setFullPracticeModule(AutocompleteResult fullPracticeModule) {
        this.fullPracticeModule = fullPracticeModule;
    }

    public CurriculumModuleOutcomeResult getOutcome() {
        return outcome;
    }

    public void setOutcome(CurriculumModuleOutcomeResult outcome) {
        this.outcome = outcome;
    }

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Boolean getIntendedModule() {
        return intendedModule;
    }

    public void setIntendedModule(Boolean intendedModule) {
        this.intendedModule = intendedModule;
    }
}
