package ee.hitsa.ois.web.commandobject;

import java.util.List;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class PracticeJournalEntriesTeacherForm {

    private List<PracticeJournalEntryTeacherForm> practiceJournalEntries;
    private String teacherComment;
    private String teacherOpinion;

    @ClassifierRestriction({MainClassCode.KUTSEHINDAMINE, MainClassCode.KORGHINDAMINE})
    private String grade;
    private List<OisFileForm> practiceJournalFiles;

    public List<PracticeJournalEntryTeacherForm> getPracticeJournalEntries() {
        return practiceJournalEntries;
    }

    public void setPracticeJournalEntries(List<PracticeJournalEntryTeacherForm> practiceJournalEntries) {
        this.practiceJournalEntries = practiceJournalEntries;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public String getTeacherOpinion() {
        return teacherOpinion;
    }

    public void setTeacherOpinion(String teacherOpinion) {
        this.teacherOpinion = teacherOpinion;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<OisFileForm> getPracticeJournalFiles() {
        return practiceJournalFiles;
    }

    public void setPracticeJournalFiles(List<OisFileForm> practiceJournalFiles) {
        this.practiceJournalFiles = practiceJournalFiles;
    }

}
