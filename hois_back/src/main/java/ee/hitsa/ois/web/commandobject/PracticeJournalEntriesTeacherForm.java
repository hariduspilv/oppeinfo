package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import ee.hitsa.ois.web.dto.GradeDto;

public class PracticeJournalEntriesTeacherForm {

    @Valid
    private List<PracticeJournalEntryTeacherForm> practiceJournalEntries;
    @Size(max=10000)
    private String teacherComment;
    @Size(max=10000)
    private String teacherOpinion;

    private GradeDto grade;
    private List<PracticeFileForm> practiceJournalFiles;
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

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public List<PracticeFileForm> getPracticeJournalFiles() {
        return practiceJournalFiles;
    }

    public void setPracticeJournalFiles(List<PracticeFileForm> practiceJournalFiles) {
        this.practiceJournalFiles = practiceJournalFiles;
    }

}
