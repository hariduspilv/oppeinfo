package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ResultColumnDto {

    private AutocompleteResult journal;
    private AutocompleteResult practiceModuleTheme;
    private AutocompleteResult fullPracticeModule;
    private AutocompleteResult module;
    private String moduleType;

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

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

}
