package ee.hitsa.ois.web.commandobject;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class PracticeJournalEntriesSupervisorForm {

    @Valid
    private List<PracticeJournalEntrySupervisorForm> practiceJournalEntries;
    @Valid
    private List<PracticeJournalEvaluationForm> supervisorPracticeEvalCriteria;
    @Valid
    private List<PracticeFileForm> practiceJournalFiles;
    @Size(max=10000)
    private String supervisorOpinion;
    @Size(max=10000)
    private String supervisorComment;

    public List<PracticeJournalEntrySupervisorForm> getPracticeJournalEntries() {
        return practiceJournalEntries != null ? practiceJournalEntries : (practiceJournalEntries = new ArrayList<>());
    }

    public void setPracticeJournalEntries(List<PracticeJournalEntrySupervisorForm> practiceJournalEntries) {
        this.practiceJournalEntries = practiceJournalEntries;
    }

    public List<PracticeFileForm> getPracticeJournalFiles() {
        return practiceJournalFiles;
    }

    public void setPracticeJournalFiles(List<PracticeFileForm> practiceJournalFiles) {
        this.practiceJournalFiles = practiceJournalFiles;
    }

    public String getSupervisorOpinion() {
        return supervisorOpinion;
    }

    public void setSupervisorOpinion(String supervisorOpinion) {
        this.supervisorOpinion = supervisorOpinion;
    }

    public String getSupervisorComment() {
        return supervisorComment;
    }

    public void setSupervisorComment(String supervisorComment) {
        this.supervisorComment = supervisorComment;
    }

    public List<PracticeJournalEvaluationForm> getSupervisorPracticeEvalCriteria() {
        return supervisorPracticeEvalCriteria;
    }

    public void setSupervisorPracticeEvalCriteria(List<PracticeJournalEvaluationForm> supervisorPracticeEvalCriteria) {
        this.supervisorPracticeEvalCriteria = supervisorPracticeEvalCriteria;
    }

}
