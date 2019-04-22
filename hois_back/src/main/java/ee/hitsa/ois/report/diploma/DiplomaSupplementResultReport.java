package ee.hitsa.ois.report.diploma;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DiplomaSupplementResultReport {

    private List<StudyResultItem> studyResults = new ArrayList<>();
    private List<StudyResultItem> finalResults = new ArrayList<>();
    private List<StudyResultItem> finalThesis = new ArrayList<>();
    private List<ApelResultItem> apels = new ArrayList<>();
    private BigDecimal totalCredits = BigDecimal.valueOf(0);

    public List<StudyResultItem> getStudyResults() {
        return studyResults;
    }

    public void setStudyResults(List<StudyResultItem> studyResults) {
        this.studyResults = studyResults;
    }

    public List<StudyResultItem> getFinalResults() {
        return finalResults;
    }

    public void setFinalResults(List<StudyResultItem> finalResults) {
        this.finalResults = finalResults;
    }

    public List<StudyResultItem> getFinalThesis() {
        return finalThesis;
    }

    public void setFinalThesis(List<StudyResultItem> finalThesis) {
        this.finalThesis = finalThesis;
    }

    public List<ApelResultItem> getApels() {
        return apels;
    }

    public void setApels(List<ApelResultItem> apels) {
        this.apels = apels;
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(BigDecimal totalCredits) {
        this.totalCredits = totalCredits;
    }

}
