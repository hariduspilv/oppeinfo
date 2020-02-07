package ee.hitsa.ois.web.commandobject.poll;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.LiteralResult;

public class PollForm {
    
    private Long id;
    private String afterword;
    private String foreword;
    private String insertedBy;
    private String changedBy;
    private LocalDate validFrom;
    private LocalDate validThru;
    private LocalDate reminderDt;
    private LocalDate journalFrom;
    private LocalDate journalThru;
    private Long studyPeriod;
    private String nameEt;
    private List<AutocompleteResult> studentGroups;
    private List<ClassifierSelection> targetCodes;
    private List<Long> teacherOccupations;
    private List<LiteralResult> journals;
    private List<LiteralResult> subjectStudyPeriods;
    private Boolean isTeacherComment;
    private Boolean isTeacherCommentVisible;
    private Boolean isThemePageable;
    private Boolean isStudentVisible;
    private Boolean responded;
    private Boolean themeEmpty;
    private String type;
    private String status;
    private String pollUrl;
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
    public List<Long> getTeacherOccupations() {
        return teacherOccupations;
    }
    public void setTeacherOccupations(List<Long> teacherOccupations) {
        this.teacherOccupations = teacherOccupations;
    }
    public List<LiteralResult> getJournals() {
        return journals;
    }
    public void setJournals(List<LiteralResult> journals) {
        this.journals = journals;
    }
    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public Boolean getIsStudentVisible() {
        return isStudentVisible;
    }
    public void setIsStudentVisible(Boolean isStudentVisible) {
        this.isStudentVisible = isStudentVisible;
    }
    public String getInsertedBy() {
        return insertedBy;
    }
    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }
    public String getChangedBy() {
        return changedBy;
    }
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
    public Boolean getResponded() {
        return responded;
    }
    public void setResponded(Boolean responded) {
        this.responded = responded;
    }
    public String getPollUrl() {
        return pollUrl;
    }
    public void setPollUrl(String pollUrl) {
        this.pollUrl = pollUrl;
    }
    public Boolean getIsThemePageable() {
        return isThemePageable;
    }
    public void setIsThemePageable(Boolean isThemePageable) {
        this.isThemePageable = isThemePageable;
    }
    public Boolean getThemeEmpty() {
        return themeEmpty;
    }
    public void setThemeEmpty(Boolean themeEmpty) {
        this.themeEmpty = themeEmpty;
    }
    public List<LiteralResult> getSubjectStudyPeriods() {
        return subjectStudyPeriods;
    }
    public void setSubjectStudyPeriods(List<LiteralResult> subjectStudyPeriods) {
        this.subjectStudyPeriods = subjectStudyPeriods;
    }

}
