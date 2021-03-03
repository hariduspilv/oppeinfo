package ee.hitsa.ois.report.studentgroupteacher;

import ee.hitsa.ois.web.dto.AutocompleteResult;

import java.util.ArrayList;
import java.util.List;

public class StudentGroupTeacherReportCardTableRow {

    private AutocompleteResult module;
    private AutocompleteResult journal;

    private List<String> otherResults;
    private List<String> evaluationResults;
    private List<String> periodResults;
    private String outcomeResult;
    private String finalResult;
    private String moduleResult;

    private Boolean isPracticeResult;
    private Boolean isPracticeModuleResult;

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public AutocompleteResult getJournal() {
        return journal;
    }

    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
    }

    public List<String> getOtherResults() {
        return otherResults != null ? otherResults : (otherResults = new ArrayList<>());
    }

    public void setOtherResults(List<String> otherResults) {
        this.otherResults = otherResults;
    }

    public List<String> getEvaluationResults() {
        return evaluationResults != null ? evaluationResults : (evaluationResults = new ArrayList<>());
    }

    public void setEvaluationResults(List<String> evaluationResults) {
        this.evaluationResults = evaluationResults;
    }

    public List<String> getPeriodResults() {
        return periodResults != null ? periodResults : (periodResults = new ArrayList<>());
    }

    public void setPeriodResults(List<String> periodResults) {
        this.periodResults = periodResults;
    }

    public String getOutcomeResult() {
        return outcomeResult;
    }

    public void setOutcomeResult(String outcomeResult) {
        this.outcomeResult = outcomeResult;
    }

    public String getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    public String getModuleResult() {
        return moduleResult;
    }

    public void setModuleResult(String moduleResult) {
        this.moduleResult = moduleResult;
    }

    public Boolean getIsPracticeResult() {
        return isPracticeResult;
    }

    public void setIsPracticeResult(Boolean isPracticeResult) {
        this.isPracticeResult = isPracticeResult;
    }

    public Boolean getIsPracticeModuleResult() {
        return isPracticeModuleResult;
    }

    public void setIsPracticeModuleResult(Boolean isPracticeModuleResult) {
        this.isPracticeModuleResult = isPracticeModuleResult;
    }
}
