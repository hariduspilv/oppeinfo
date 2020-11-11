package ee.hitsa.ois.web.commandobject;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class PracticeJournalEntriesStudentForm {

    @Size(max=10000)
    private String practiceReport;
    @Valid
    private List<PracticeFileForm> practiceJournalStudentFiles;
    @Valid
    private List<PracticeJournalEntryStudentForm> practiceJournalEntries;
    @Valid
    private List<PracticeJournalEvaluationForm> studentPracticeEvalCriteria;

    public String getPracticeReport() {
        return practiceReport;
    }

    public void setPracticeReport(String practiceReport) {
        this.practiceReport = practiceReport;
    }

    public List<PracticeJournalEntryStudentForm> getPracticeJournalEntries() {
        return practiceJournalEntries;
    }

    public void setPracticeJournalEntries(List<PracticeJournalEntryStudentForm> practiceJournalEntries) {
        this.practiceJournalEntries = practiceJournalEntries;
    }

    public List<PracticeJournalEvaluationForm> getStudentPracticeEvalCriteria() {
        return studentPracticeEvalCriteria != null ? studentPracticeEvalCriteria : (studentPracticeEvalCriteria = new ArrayList<>());
    }

    public void setStudentPracticeEvalCriteria(List<PracticeJournalEvaluationForm> studentPracticeEvalCriteria) {
        this.studentPracticeEvalCriteria = studentPracticeEvalCriteria;
    }

    public List<PracticeFileForm> getPracticeJournalStudentFiles() {
        return practiceJournalStudentFiles;
    }

    public void setPracticeJournalStudentFiles(List<PracticeFileForm> practiceJournalStudentFiles) {
        this.practiceJournalStudentFiles = practiceJournalStudentFiles;
    }

}
