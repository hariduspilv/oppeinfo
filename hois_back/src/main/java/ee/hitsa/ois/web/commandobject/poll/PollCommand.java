package ee.hitsa.ois.web.commandobject.poll;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollCommand {
    
    private String afterword;
    private String foreword;
    private LocalDate validFrom;
    private LocalDate validThru;
    private LocalDate journalFrom;
    private LocalDate journalThru;
    private LocalDate reminderDt;
    private String nameEt;
    private List<Long> studentGroups;
    private List<String> targetCodes;
    private List<AutocompleteResult> journals;
    private List<AutocompleteResult> subjectStudyPeriods;
    private Long studyPeriod;
    private Boolean isTeacherComment;
    private Boolean isTeacherCommentVisible;
    private Boolean isStudentVisible;
    private List<Long> teacherOccupations;
    private String type;
    private String status;
    
    public String getAfterword() {
        return afterword;
    }
    public void setAfterword(String afterword) {
        this.afterword = afterword;
    }
    public String getForeword() {
        return foreword;
    }
    public void setForeword(String foreword) {
        this.foreword = foreword;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
    public LocalDate getReminderDt() {
        return reminderDt;
    }
    public void setReminderDt(LocalDate reminderDt) {
        this.reminderDt = reminderDt;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public List<Long> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }
    public List<String> getTargetCodes() {
        return targetCodes;
    }
    public void setTargetCodes(List<String> targetCodes) {
        this.targetCodes = targetCodes;
    }
    public Boolean getIsTeacherCommentVisible() {
        return isTeacherCommentVisible;
    }
    public void setIsTeacherCommentVisible(Boolean isTeacherCommentVisible) {
        this.isTeacherCommentVisible = isTeacherCommentVisible;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Boolean getIsTeacherComment() {
        return isTeacherComment;
    }
    public void setIsTeacherComment(Boolean isTeacherComment) {
        this.isTeacherComment = isTeacherComment;
    }
    public LocalDate getJournalFrom() {
        return journalFrom;
    }
    public void setJournalFrom(LocalDate journalFrom) {
        this.journalFrom = journalFrom;
    }
    public LocalDate getJournalThru() {
        return journalThru;
    }
    public void setJournalThru(LocalDate journalThru) {
        this.journalThru = journalThru;
    }
    public List<AutocompleteResult> getJournals() {
        return journals;
    }
    public void setJournals(List<AutocompleteResult> journals) {
        this.journals = journals;
    }
    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Boolean getIsStudentVisible() {
        return isStudentVisible;
    }
    public void setIsStudentVisible(Boolean isStudentVisible) {
        this.isStudentVisible = isStudentVisible;
    }
    public List<Long> getTeacherOccupations() {
        return teacherOccupations;
    }
    public void setTeacherOccupations(List<Long> teacherOccupations) {
        this.teacherOccupations = teacherOccupations;
    }
    public List<AutocompleteResult> getSubjectStudyPeriods() {
        return subjectStudyPeriods;
    }
    public void setSubjectStudyPeriods(List<AutocompleteResult> subjectStudyPeriods) {
        this.subjectStudyPeriods = subjectStudyPeriods;
    }
}
