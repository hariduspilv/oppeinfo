package ee.hitsa.ois.web.commandobject.poll;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;

public class PollForm {
    
    private Long id;
    private String afterword;
    private String foreword;
    private LocalDate validFrom;
    private LocalDate validThru;
    private LocalDate reminderDt;
    private LocalDate journalFrom;
    private LocalDate journalThru;
    private String nameEt;
    private List<AutocompleteResult> studentGroups;
    private List<ClassifierSelection> targetCodes;
    private Boolean isTeacherComment;
    private Boolean isTeacherCommentVisible;
    private String typeCode;
    private String status;
    private long themes;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public List<AutocompleteResult> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(List<AutocompleteResult> studentGroups) {
        this.studentGroups = studentGroups;
    }
    public List<ClassifierSelection> getTargetCodes() {
        return targetCodes;
    }
    public void setTargetCodes(List<ClassifierSelection> targetCodes) {
        this.targetCodes = targetCodes;
    }
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public long getThemes() {
        return themes;
    }
    public void setThemes(long themes) {
        this.themes = themes;
    }
    public Boolean getIsTeacherComment() {
        return isTeacherComment;
    }
    public void setIsTeacherComment(Boolean isTeacherComment) {
        this.isTeacherComment = isTeacherComment;
    }
    public Boolean getIsTeacherCommentVisible() {
        return isTeacherCommentVisible;
    }
    public void setIsTeacherCommentVisible(Boolean isTeacherCommentVisible) {
        this.isTeacherCommentVisible = isTeacherCommentVisible;
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

}
