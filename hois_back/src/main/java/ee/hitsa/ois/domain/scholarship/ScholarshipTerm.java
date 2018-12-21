package ee.hitsa.ois.domain.scholarship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.school.School;

@Entity
public class ScholarshipTerm extends BaseEntityWithId {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "school_id", updatable = false)
    private School school;
    private String nameEt;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;
    private LocalDate applicationStart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "study_period_id")
    private StudyPeriod studyPeriod;
    private LocalDate applicationEnd;
    private LocalDate paymentStart;
    private LocalDate paymentEnd;
    private Long places;
    private BigDecimal amountPaid;
    private BigDecimal averageMark;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier averageMarkPriority;
    private BigDecimal lastPeriodMark;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier lastPeriodMarkPriority;
    private BigDecimal curriculumCompletion;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier curriculumCompletionPriority;
    private Long maxAbsences;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier maxAbsencesPriority;
    private LocalDate studyStartPeriodStart;
    private LocalDate studyStartPeriodEnd;
    @Column(nullable = false)
    private Boolean isAcademicLeave;
    @Column(nullable = false)
    private Boolean isStudyBacklog;
    @Column(nullable = false)
    private Boolean isTeacherConfirm;
    @Column(nullable = false)
    private Boolean isFamilyIncomes;
    @Column(nullable = false)
    private Boolean isOpen;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Committee committee;
    
    private String addInfo;
    private Boolean isOutcomes;
    private Boolean isPeriodGrade;
    private Boolean isJournalFinalGrade;
    private Boolean isModuleGrade;
    private Boolean isApelGrade;
    private Boolean isJournalGrade;
    private Boolean isLastPeriodOutcomes;
    private Boolean isLastPeriodPeriodGrade;
    private Boolean isLastPeriodJournalFinalGrade;
    private Boolean isLastPeriodModuleGrade;
    private Boolean isLastPeriodApelGrade;
    private Boolean isLastPeriodJournalGrade;
    private LocalDate lastPeriodGradeFrom;
    private LocalDate lastPeriodGradeThru;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "scholarshipTerm")
    private List<ScholarshipTermCourse> scholarshipTermCourses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "scholarshipTerm")
    private List<ScholarshipTermCurriculum> scholarshipTermCurriculums = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "scholarshipTerm")
    private List<ScholarshipTermStudyLoad> scholarshipTermStudyLoads = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "scholarshipTerm")
    private List<ScholarshipTermStudyForm> scholarshipTermStudyForms = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "scholarshipTerm")
    private List<ScholarshipApplication> scholarshipApplications = new ArrayList<>();

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

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }

    public LocalDate getApplicationStart() {
        return applicationStart;
    }

    public void setApplicationStart(LocalDate applicationStart) {
        this.applicationStart = applicationStart;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public LocalDate getApplicationEnd() {
        return applicationEnd;
    }

    public void setApplicationEnd(LocalDate applicationEnd) {
        this.applicationEnd = applicationEnd;
    }

    public LocalDate getPaymentStart() {
        return paymentStart;
    }

    public void setPaymentStart(LocalDate paymentStart) {
        this.paymentStart = paymentStart;
    }

    public LocalDate getPaymentEnd() {
        return paymentEnd;
    }

    public void setPaymentEnd(LocalDate paymentEnd) {
        this.paymentEnd = paymentEnd;
    }

    public Long getPlaces() {
        return places;
    }

    public void setPlaces(Long places) {
        this.places = places;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }

    public Classifier getAverageMarkPriority() {
        return averageMarkPriority;
    }

    public void setAverageMarkPriority(Classifier averageMarkPriority) {
        this.averageMarkPriority = averageMarkPriority;
    }

    public BigDecimal getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(BigDecimal lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public BigDecimal getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(BigDecimal curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public Classifier getLastPeriodMarkPriority() {
        return lastPeriodMarkPriority;
    }

    public void setLastPeriodMarkPriority(Classifier lastPeriodMarkPriority) {
        this.lastPeriodMarkPriority = lastPeriodMarkPriority;
    }

    public Classifier getCurriculumCompletionPriority() {
        return curriculumCompletionPriority;
    }

    public void setCurriculumCompletionPriority(Classifier curriculumCompletionPriority) {
        this.curriculumCompletionPriority = curriculumCompletionPriority;
    }

    public Long getMaxAbsences() {
        return maxAbsences;
    }

    public void setMaxAbsences(Long maxAbsences) {
        this.maxAbsences = maxAbsences;
    }

    public Classifier getMaxAbsencesPriority() {
        return maxAbsencesPriority;
    }

    public void setMaxAbsencesPriority(Classifier maxAbsencesPriority) {
        this.maxAbsencesPriority = maxAbsencesPriority;
    }

    public LocalDate getStudyStartPeriodStart() {
        return studyStartPeriodStart;
    }

    public void setStudyStartPeriodStart(LocalDate studyStartPeriodStart) {
        this.studyStartPeriodStart = studyStartPeriodStart;
    }

    public LocalDate getStudyStartPeriodEnd() {
        return studyStartPeriodEnd;
    }

    public void setStudyStartPeriodEnd(LocalDate studyStartPeriodEnd) {
        this.studyStartPeriodEnd = studyStartPeriodEnd;
    }

    public List<ScholarshipTermCourse> getScholarshipTermCourses() {
        return scholarshipTermCourses;
    }

    public void setScholarshipTermCourses(List<ScholarshipTermCourse> scholarshipTermCourses) {
        this.scholarshipTermCourses = scholarshipTermCourses;
    }

    public List<ScholarshipTermCurriculum> getScholarshipTermCurriculums() {
        return scholarshipTermCurriculums;
    }

    public void setScholarshipTermCurriculums(List<ScholarshipTermCurriculum> scholarshipTermCurriculums) {
        this.scholarshipTermCurriculums = scholarshipTermCurriculums;
    }

    public List<ScholarshipTermStudyLoad> getScholarshipTermStudyLoads() {
        return scholarshipTermStudyLoads;
    }

    public List<ScholarshipTermStudyForm> getScholarshipTermStudyForms() {
        return scholarshipTermStudyForms;
    }

    public void setScholarshipTermStudyForms(List<ScholarshipTermStudyForm> scholarshipTermStudyForms) {
        this.scholarshipTermStudyForms = scholarshipTermStudyForms;
    }

    public void setScholarshipTermStudyLoads(List<ScholarshipTermStudyLoad> scholarshipTermStudyLoads) {
        this.scholarshipTermStudyLoads = scholarshipTermStudyLoads;
    }

    public List<ScholarshipApplication> getScholarshipApplications() {
        return scholarshipApplications;
    }

    public void setScholarshipApplications(List<ScholarshipApplication> scholarshipApplications) {
        this.scholarshipApplications = scholarshipApplications;
    }

    public Boolean getIsAcademicLeave() {
        return isAcademicLeave;
    }

    public void setIsAcademicLeave(Boolean isAcademicLeave) {
        this.isAcademicLeave = isAcademicLeave;
    }

    public Boolean getIsStudyBacklog() {
        return isStudyBacklog;
    }

    public void setIsStudyBacklog(Boolean isStudyBacklog) {
        this.isStudyBacklog = isStudyBacklog;
    }

    public Boolean getIsTeacherConfirm() {
        return isTeacherConfirm;
    }

    public void setIsTeacherConfirm(Boolean isTeacherConfirm) {
        this.isTeacherConfirm = isTeacherConfirm;
    }

    public Boolean getIsFamilyIncomes() {
        return isFamilyIncomes;
    }

    public void setIsFamilyIncomes(Boolean isFamilyIncomes) {
        this.isFamilyIncomes = isFamilyIncomes;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Committee getCommittee() {
        return committee;
    }

    public void setCommittee(Committee committee) {
        this.committee = committee;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Boolean getIsOutcomes() {
        return isOutcomes;
    }

    public void setIsOutcomes(Boolean isOutcomes) {
        this.isOutcomes = isOutcomes;
    }

    public Boolean getIsPeriodGrade() {
        return isPeriodGrade;
    }

    public void setIsPeriodGrade(Boolean isPeriodGrade) {
        this.isPeriodGrade = isPeriodGrade;
    }

    public Boolean getIsJournalFinalGrade() {
        return isJournalFinalGrade;
    }

    public void setIsJournalFinalGrade(Boolean isJournalFinalGrade) {
        this.isJournalFinalGrade = isJournalFinalGrade;
    }

    public Boolean getIsModuleGrade() {
        return isModuleGrade;
    }

    public void setIsModuleGrade(Boolean isModuleGrade) {
        this.isModuleGrade = isModuleGrade;
    }

    public Boolean getIsApelGrade() {
        return isApelGrade;
    }

    public void setIsApelGrade(Boolean isApelGrade) {
        this.isApelGrade = isApelGrade;
    }

    public Boolean getIsJournalGrade() {
        return isJournalGrade;
    }

    public void setIsJournalGrade(Boolean isJournalGrade) {
        this.isJournalGrade = isJournalGrade;
    }

    public Boolean getIsLastPeriodOutcomes() {
        return isLastPeriodOutcomes;
    }

    public void setIsLastPeriodOutcomes(Boolean isLastPeriodOutcomes) {
        this.isLastPeriodOutcomes = isLastPeriodOutcomes;
    }

    public Boolean getIsLastPeriodPeriodGrade() {
        return isLastPeriodPeriodGrade;
    }

    public void setIsLastPeriodPeriodGrade(Boolean isLastPeriodPeriodGrade) {
        this.isLastPeriodPeriodGrade = isLastPeriodPeriodGrade;
    }

    public Boolean getIsLastPeriodJournalFinalGrade() {
        return isLastPeriodJournalFinalGrade;
    }

    public void setIsLastPeriodJournalFinalGrade(Boolean isLastPeriodJournalFinalGrade) {
        this.isLastPeriodJournalFinalGrade = isLastPeriodJournalFinalGrade;
    }

    public Boolean getIsLastPeriodModuleGrade() {
        return isLastPeriodModuleGrade;
    }

    public void setIsLastPeriodModuleGrade(Boolean isLastPeriodModuleGrade) {
        this.isLastPeriodModuleGrade = isLastPeriodModuleGrade;
    }

    public Boolean getIsLastPeriodApelGrade() {
        return isLastPeriodApelGrade;
    }

    public void setIsLastPeriodApelGrade(Boolean isLastPeriodApelGrade) {
        this.isLastPeriodApelGrade = isLastPeriodApelGrade;
    }

    public Boolean getIsLastPeriodJournalGrade() {
        return isLastPeriodJournalGrade;
    }

    public void setIsLastPeriodJournalGrade(Boolean isLastPeriodJournalGrade) {
        this.isLastPeriodJournalGrade = isLastPeriodJournalGrade;
    }

    public LocalDate getLastPeriodGradeFrom() {
        return lastPeriodGradeFrom;
    }

    public void setLastPeriodGradeFrom(LocalDate lastPeriodGradeFrom) {
        this.lastPeriodGradeFrom = lastPeriodGradeFrom;
    }

    public LocalDate getLastPeriodGradeThru() {
        return lastPeriodGradeThru;
    }

    public void setLastPeriodGradeThru(LocalDate lastPeriodGradeThru) {
        this.lastPeriodGradeThru = lastPeriodGradeThru;
    }

}
