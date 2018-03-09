package ee.hitsa.ois.domain.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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
    private Long moodleCourseId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private List<JournalTeacher> journalTeachers;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private List<JournalRoom> journalRooms;

    // cannot delete journal when there are students
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private Set<JournalStudent> journalStudents = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private List<JournalOccupationModuleTheme> journalOccupationModuleThemes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private List<JournalCapacity> journalCapacities;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private List<JournalCapacityType> journalCapacityTypes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private Set<JournalEntry> journalEntries = new HashSet<>();

    private Boolean addModuleOutcomes;

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

    public Long getMoodleCourseId() {
        return moodleCourseId;
    }

    public void setMoodleCourseId(Long moodleCourseId) {
        this.moodleCourseId = moodleCourseId;
    }

    public List<JournalTeacher> getJournalTeachers() {
        return journalTeachers;
    }

    public void setJournalTeachers(List<JournalTeacher> journalTeachers) {
        this.journalTeachers = journalTeachers;
    }

    public List<JournalRoom> getJournalRooms() {
        return journalRooms;
    }

    public void setJournalRooms(List<JournalRoom> journalRooms) {
        this.journalRooms = journalRooms;
    }

    public Set<JournalStudent> getJournalStudents() {
        return journalStudents != null ? journalStudents : (journalStudents = new HashSet<>());
    }

    public void setJournalStudents(Set<JournalStudent> journalStudents) {
        this.journalStudents = journalStudents;
    }

    public List<JournalOccupationModuleTheme> getJournalOccupationModuleThemes() {
        return journalOccupationModuleThemes != null ? journalOccupationModuleThemes : (journalOccupationModuleThemes = new ArrayList<>());
    }

    public void setJournalOccupationModuleThemes(List<JournalOccupationModuleTheme> journalOccupationModuleThemes) {
        this.journalOccupationModuleThemes = journalOccupationModuleThemes;
    }

    public List<JournalCapacity> getJournalCapacities() {
        return journalCapacities;
    }

    public void setJournalCapacities(List<JournalCapacity> journalCapacities) {
        this.journalCapacities = journalCapacities;
    }

    public List<JournalCapacityType> getJournalCapacityTypes() {
        return journalCapacityTypes;
    }

    public void setJournalCapacityTypes(List<JournalCapacityType> journalCapacityTypes) {
        this.journalCapacityTypes = journalCapacityTypes;
    }

    public Set<JournalEntry> getJournalEntries() {
        return journalEntries != null ? journalEntries : (journalEntries = new HashSet<>());
    }

    public void setJournalEntries(Set<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public Boolean getAddModuleOutcomes() {
        return addModuleOutcomes;
    }

    public void setAddModuleOutcomes(Boolean addModuleOutcomes) {
        this.addModuleOutcomes = addModuleOutcomes;
    }
    
}
