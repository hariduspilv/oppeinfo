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
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;

@Entity
public class Journal extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    private String nameEt;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyYear studyYear;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier assessment;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier groupProportion;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private Set<JournalTeacher> journalTeachers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private Set<JournalStudent> journalStudents = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private Set<JournalOccupationModuleTheme> journalOccupationModuleThemes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private Set<JournalCapacity> journalCapacities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private Set<JournalCapacityType> journalCapacityTypes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private Set<JournalEntry> journalEntries = new HashSet<>();


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

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public Classifier getAssessment() {
        return assessment;
    }

    public void setAssessment(Classifier assessment) {
        this.assessment = assessment;
    }

    public Classifier getGroupProportion() {
        return groupProportion;
    }

    public void setGroupProportion(Classifier groupProportion) {
        this.groupProportion = groupProportion;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<JournalTeacher> getJournalTeachers() {
        return journalTeachers;
    }

    public void setJournalTeachers(Set<JournalTeacher> journalTeachers) {
        this.journalTeachers = journalTeachers;
    }

    public Set<JournalStudent> getJournalStudents() {
        return journalStudents;
    }

    public void setJournalStudents(Set<JournalStudent> journalStudents) {
        this.journalStudents = journalStudents;
    }

    public Set<JournalOccupationModuleTheme> getJournalOccupationModuleThemes() {
        return journalOccupationModuleThemes;
    }

    public void setJournalOccupationModuleThemes(Set<JournalOccupationModuleTheme> journalOccupationModuleThemes) {
        this.journalOccupationModuleThemes = journalOccupationModuleThemes;
    }

    public Set<JournalCapacity> getJournalCapacities() {
        return journalCapacities;
    }

    public void setJournalCapacities(Set<JournalCapacity> journalCapacities) {
        this.journalCapacities = journalCapacities;
    }

    public Set<JournalCapacityType> getJournalCapacityTypes() {
        return journalCapacityTypes;
    }

    public void setJournalCapacityTypes(Set<JournalCapacityType> journalCapacityTypes) {
        this.journalCapacityTypes = journalCapacityTypes;
    }

    public Set<JournalEntry> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(Set<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }
}
