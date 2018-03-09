package ee.hitsa.ois.domain.timetable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;

@Entity
public class JournalEntry extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Journal journal;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier entryType;
    private String nameEt;
    private LocalDate entryDate;
    private Long startLessonNr;
    private Long lessons;
    private String content;
    private String homework;
    private LocalDate homeworkDuedate;
    private Long moodleGradeItemId;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private CurriculumModuleOutcome curriculumModuleOutcomes;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    @JoinColumn(name = "journal_entry_id", nullable = false, updatable = true)
    private Set<JournalEntryCapacityType> journalEntryCapacityTypes = new HashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    @JoinColumn(name = "journal_entry_id", nullable = false, updatable = true)
    private Set<JournalEntryStudent> journalEntryStudents = new HashSet<>();

    public Journal getJournal() {
        return journal;
    }
    public void setJournal(Journal journal) {
        this.journal = journal;
    }
    public Classifier getEntryType() {
        return entryType;
    }
    public void setEntryType(Classifier entryType) {
        this.entryType = entryType;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public LocalDate getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }
    public Long getStartLessonNr() {
        return startLessonNr;
    }
    public void setStartLessonNr(Long startLessonNr) {
        this.startLessonNr = startLessonNr;
    }
    public Long getLessons() {
        return lessons;
    }
    public void setLessons(Long lessons) {
        this.lessons = lessons;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getHomework() {
        return homework;
    }
    public void setHomework(String homework) {
        this.homework = homework;
    }
    public LocalDate getHomeworkDuedate() {
        return homeworkDuedate;
    }
    public void setHomeworkDuedate(LocalDate homeworkDuedate) {
        this.homeworkDuedate = homeworkDuedate;
    }
    public Long getMoodleGradeItemId() {
        return moodleGradeItemId;
    }
    public void setMoodleGradeItemId(Long moodleGradeItemId) {
        this.moodleGradeItemId = moodleGradeItemId;
    }
    public Set<JournalEntryCapacityType> getJournalEntryCapacityTypes() {
        return journalEntryCapacityTypes;
    }
    public void setJournalEntryCapacityTypes(Set<JournalEntryCapacityType> journalEntryCapacityTypes) {
        this.journalEntryCapacityTypes = journalEntryCapacityTypes;
    }
    public Set<JournalEntryStudent> getJournalEntryStudents() {
        return journalEntryStudents;
    }
    public void setJournalEntryStudents(Set<JournalEntryStudent> journalEntryStudents) {
        this.journalEntryStudents = journalEntryStudents;
    }
    public CurriculumModuleOutcome getCurriculumModuleOutcomes() {
        return curriculumModuleOutcomes;
    }
    public void setCurriculumModuleOutcomes(CurriculumModuleOutcome curriculumModuleOutcomes) {
        this.curriculumModuleOutcomes = curriculumModuleOutcomes;
    }

}
