package ee.hitsa.ois.domain.poll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@Entity
public class Poll extends BaseEntityWithId {
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudyPeriod studyPeriod;
    private String nameEt;
    private String nameEn;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    private LocalDate validThru;
    private LocalDate validFrom;
    private LocalDate reminderDt;
    private LocalDate journalFrom;
    private LocalDate journalThru;
    private String foreword;
    private String afterword;
    private Boolean isTeacherComment;
    private Boolean isTeacherCommentVisible;
    private Boolean isStudentVisible;
    private Boolean isThemePageable;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_id", nullable = false, updatable = false, insertable = false)
    private List<PollStudentGroup> pollStudentGroups = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_id", nullable = false, updatable = false, insertable = false)
    private List<PollTarget> pollTargets = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_id", nullable = false, updatable = false, insertable = false)
    private List<PollTheme> pollThemes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_id", nullable = false, updatable = false, insertable = false)
    private List<Response> responses = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_id", nullable = false, updatable = false, insertable = false)
    private List<PollJournal> pollJournals = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_id", nullable = false, updatable = false, insertable = false)
    private List<PollTeacherOccupation> pollTeacherOccupations = new ArrayList<>();
    
    public Poll(Poll poll, Classifier status, Set<AutocompleteResult> names) {
        if (poll.getNameEt().length() > 251) {
            this.nameEt = poll.nameEt.substring(0, 251) + "copy";
        } else {
            this.nameEt = poll.nameEt + "copy";
        }
        int counter = 1;
        while (names.stream().anyMatch(p -> p.getNameEt().equals(this.nameEt))) {
            if (poll.getNameEt().length() + 4 + String.valueOf(counter).length() > 255) {
                this.nameEt = poll.getNameEt().substring(0, 251 - String.valueOf(counter).length()) + "copy" + counter;
            } else {
                this.nameEt = poll.getNameEt() + "copy" + counter;
            }
            counter++;
        }
        this.nameEn = this.nameEt;
        this.school = poll.getSchool();
        this.type = poll.getType();
        this.status = status;
        this.validThru = poll.getValidThru();
        this.validFrom = poll.getValidFrom();
        this.reminderDt = poll.getReminderDt();
        this.journalFrom = poll.getJournalFrom();
        this.journalThru = poll.getJournalThru();
        this.foreword = poll.getForeword();
        this.afterword = poll.getAfterword();
        this.isTeacherComment = poll.getIsTeacherComment();
        this.isTeacherCommentVisible = poll.getIsTeacherCommentVisible();
        this.isStudentVisible = poll.getIsStudentVisible();
        this.isThemePageable = poll.getIsThemePageable();
        // Set other fields manually
    }
    
    public Poll() {}

    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public Classifier getType() {
        return type;
    }
    public void setType(Classifier type) {
        this.type = type;
    }
    public Classifier getStatus() {
        return status;
    }
    public void setStatus(Classifier status) {
        this.status = status;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getReminderDt() {
        return reminderDt;
    }
    public void setReminderDt(LocalDate reminderDt) {
        this.reminderDt = reminderDt;
    }
    public String getForeword() {
        return foreword;
    }
    public void setForeword(String foreword) {
        this.foreword = foreword;
    }
    public String getAfterword() {
        return afterword;
    }
    public void setAfterword(String afterword) {
        this.afterword = afterword;
    }
    public List<PollStudentGroup> getPollStudentGroups() {
        return pollStudentGroups;
    }
    public void setPollStudentGroups(List<PollStudentGroup> pollStudentGroups) {
        this.pollStudentGroups = pollStudentGroups;
    }
    public List<PollTarget> getPollTargets() {
        return pollTargets;
    }
    public void setPollTargets(List<PollTarget> pollTargets) {
        this.pollTargets = pollTargets;
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
    public List<PollTheme> getPollThemes() {
        return pollThemes;
    }
    public void setPollThemes(List<PollTheme> pollThemes) {
        this.pollThemes = pollThemes;
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

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public List<PollJournal> getPollJournals() {
        return pollJournals;
    }

    public void setPollJournals(List<PollJournal> pollJournals) {
        this.pollJournals = pollJournals;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Boolean getIsStudentVisible() {
        return isStudentVisible;
    }

    public void setIsStudentVisible(Boolean isStudentVisible) {
        this.isStudentVisible = isStudentVisible;
    }

    public Boolean getIsThemePageable() {
        return isThemePageable;
    }

    public void setIsThemePageable(Boolean isThemePageable) {
        this.isThemePageable = isThemePageable;
    }

    public List<PollTeacherOccupation> getPollTeacherOccupations() {
        return pollTeacherOccupations;
    }

    public void setPollTeacherOccupations(List<PollTeacherOccupation> pollTeacherOccupations) {
        this.pollTeacherOccupations = pollTeacherOccupations;
    }
    
}
