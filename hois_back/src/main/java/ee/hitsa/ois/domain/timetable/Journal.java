package ee.hitsa.ois.domain.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier assessment;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier groupProportion;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    private LocalDate endDate;
    private Long moodleCourseId;
    @Column(name = "is_review_ok")
    private Boolean reviewOk;
    private LocalDate reviewDate;
    private String reviewInfo;
    private String untisCode;
    @Column(name = "is_capacity_diff")
    private Boolean capacityDiff;
    private Boolean addStudents;

    @ManyToOne(fetch = FetchType.LAZY)
    private JournalSub journalSub;

    private Boolean addModuleOutcomes;
    private Boolean isIndividual;
    private Boolean isFree;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private List<JournalTeacher> journalTeachers;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "journal_id", nullable = false, updatable = false)
    private List<JournalRoom> journalRooms;

    // cannot delete journal when there are students
    @OneToMany(mappedBy = "journal", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
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

    public Boolean getReviewOk() {
        return reviewOk;
    }

    public void setReviewOk(Boolean reviewOk) {
        this.reviewOk = reviewOk;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewInfo() {
        return reviewInfo;
    }

    public void setReviewInfo(String reviewInfo) {
        this.reviewInfo = reviewInfo;
    }

    public String getUntisCode() {
        return untisCode;
    }

    public void setUntisCode(String untisCode) {
        this.untisCode = untisCode;
    }

    public Boolean getCapacityDiff() {
        return capacityDiff;
    }

    public void setCapacityDiff(Boolean capacityDiff) {
        this.capacityDiff = capacityDiff;
    }

    public JournalSub getJournalSub() {
        return journalSub;
    }

    public void setJournalSub(JournalSub journalSub) {
        this.journalSub = journalSub;
    }

    public Boolean getAddModuleOutcomes() {
        return addModuleOutcomes;
    }

    public void setAddModuleOutcomes(Boolean addModuleOutcomes) {
        this.addModuleOutcomes = addModuleOutcomes;
    }

    public Boolean getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(Boolean isIndividual) {
        this.isIndividual = isIndividual;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getAddStudents() {
        return addStudents;
    }

    public void setAddStudents(Boolean addStudents) {
        this.addStudents = addStudents;
    }

    public List<JournalTeacher> getJournalTeachers() {
        return journalTeachers != null ?  journalTeachers : (journalTeachers = new ArrayList<>());
    }

    public void setJournalTeachers(List<JournalTeacher> journalTeachers) {
        this.journalTeachers = journalTeachers;
    }

    public List<JournalRoom> getJournalRooms() {
        return journalRooms != null ?  journalRooms : (journalRooms = new ArrayList<>());
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
        return journalCapacities != null ? journalCapacities : (journalCapacities = new ArrayList<>());
    }

    public void setJournalCapacities(List<JournalCapacity> journalCapacities) {
        this.journalCapacities = journalCapacities;
    }

    public List<JournalCapacityType> getJournalCapacityTypes() {
        return journalCapacityTypes != null ? journalCapacityTypes : (journalCapacityTypes = new ArrayList<>());
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
}
