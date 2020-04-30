package ee.hitsa.ois.web.commandobject.scholarship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ScholarshipTermForm {

    @Required
    @ClassifierRestriction(MainClassCode.STIPTOETUS)
    private String type;
    @ClassifierRestriction(MainClassCode.EHIS_STIPENDIUM)
    private String scholarshipEhis;
    @Required
    private String nameEt;
    @NotNull
    private Long studyPeriod;
    private LocalDate applicationStart;
    private LocalDate applicationEnd;
    private LocalDate paymentStart;
    private LocalDate paymentEnd;
    private Long places;
    private BigDecimal amountPaid;
    @NotNull
    private List<AutocompleteResult> curriculums;
    private List<String> studyLoads;
    private List<String> studyForms;
    private List<String> courses;
    private BigDecimal averageMark;
    @ClassifierRestriction(MainClassCode.PRIORITEET)
    private String averageMarkPriority;
    private BigDecimal wagMark;
    @ClassifierRestriction(MainClassCode.PRIORITEET)
    private String wagMarkPriority;
    private BigDecimal lastPeriodMark;
    @ClassifierRestriction(MainClassCode.PRIORITEET)
    private String lastPeriodMarkPriority;
    private BigDecimal lastPeriodWagMark;
    @ClassifierRestriction(MainClassCode.PRIORITEET)
    private String lastPeriodWagMarkPriority;
    private BigDecimal curriculumCompletion;
    @ClassifierRestriction(MainClassCode.PRIORITEET)
    private String curriculumCompletionPriority;
    private Long maxAbsences;
    @ClassifierRestriction(MainClassCode.PRIORITEET)
    private String maxAbsencesPriority;
    private LocalDate studyStartPeriodStart;
    private LocalDate studyStartPeriodEnd;
    private Boolean isAcademicLeave;
    private Boolean isStudyBacklog;
    private Boolean isTeacherConfirm;
    private Boolean isFamilyIncomes;
    private Boolean isOpen;
    private Long committee;
    private String addInfo;
    private Boolean isOutcomes;
    private Boolean isPeriodGrade;
    private Boolean isJournalFinalGrade;
    private Boolean isModuleGrade;
    private Boolean isApelGrade;
    private Boolean isJournalGrade;
    private Boolean isNominalEnd;
    private Boolean isNegative;
    private Boolean isBeforeImmat;
    private Boolean isSais;
    private Boolean isLastPeriodOutcomes;
    private Boolean isLastPeriodPeriodGrade;
    private Boolean isLastPeriodJournalFinalGrade;
    private Boolean isLastPeriodModuleGrade;
    private Boolean isLastPeriodApelGrade;
    private Boolean isLastPeriodJournalGrade;
    private LocalDate lastPeriodGradeFrom;
    private LocalDate lastPeriodGradeThru;

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

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public LocalDate getApplicationStart() {
        return applicationStart;
    }

    public void setApplicationStart(LocalDate applicationStart) {
        this.applicationStart = applicationStart;
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

    public List<AutocompleteResult> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(List<AutocompleteResult> curriculums) {
        this.curriculums = curriculums;
    }

    public List<String> getStudyLoads() {
        return studyLoads;
    }

    public void setStudyLoads(List<String> studyLoads) {
        this.studyLoads = studyLoads;
    }

    public List<String> getStudyForms() {
        return studyForms;
    }

    public void setStudyForms(List<String> studyForms) {
        this.studyForms = studyForms;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }

    public String getAverageMarkPriority() {
        return averageMarkPriority;
    }

    public void setAverageMarkPriority(String averageMarkPriority) {
        this.averageMarkPriority = averageMarkPriority;
    }

    public BigDecimal getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(BigDecimal lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public String getLastPeriodMarkPriority() {
        return lastPeriodMarkPriority;
    }

    public void setLastPeriodMarkPriority(String lastPeriodMarkPriority) {
        this.lastPeriodMarkPriority = lastPeriodMarkPriority;
    }

    public BigDecimal getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(BigDecimal curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public String getCurriculumCompletionPriority() {
        return curriculumCompletionPriority;
    }

    public void setCurriculumCompletionPriority(String curriculumCompletionPriority) {
        this.curriculumCompletionPriority = curriculumCompletionPriority;
    }

    public Long getMaxAbsences() {
        return maxAbsences;
    }

    public void setMaxAbsences(Long maxAbsences) {
        this.maxAbsences = maxAbsences;
    }

    public String getMaxAbsencesPriority() {
        return maxAbsencesPriority;
    }

    public void setMaxAbsencesPriority(String maxAbsencesPriority) {
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

    public Long getCommittee() {
        return committee;
    }

    public void setCommittee(Long committee) {
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

    public String getScholarshipEhis() {
        return scholarshipEhis;
    }

    public void setScholarshipEhis(String scholarshipEhis) {
        this.scholarshipEhis = scholarshipEhis;
    }

    public BigDecimal getWagMark() {
        return wagMark;
    }

    public void setWagMark(BigDecimal wagMark) {
        this.wagMark = wagMark;
    }

    public String getWagMarkPriority() {
        return wagMarkPriority;
    }

    public void setWagMarkPriority(String wagMarkPriority) {
        this.wagMarkPriority = wagMarkPriority;
    }

    public BigDecimal getLastPeriodWagMark() {
        return lastPeriodWagMark;
    }

    public void setLastPeriodWagMark(BigDecimal lastPeriodWagMark) {
        this.lastPeriodWagMark = lastPeriodWagMark;
    }

    public String getLastPeriodWagMarkPriority() {
        return lastPeriodWagMarkPriority;
    }

    public void setLastPeriodWagMarkPriority(String lastPeriodWagMarkPriority) {
        this.lastPeriodWagMarkPriority = lastPeriodWagMarkPriority;
    }

    public Boolean getIsNominalEnd() {
        return isNominalEnd;
    }

    public void setIsNominalEnd(Boolean isNominalEnd) {
        this.isNominalEnd = isNominalEnd;
    }

    public Boolean getIsNegative() {
        return isNegative;
    }

    public void setIsNegative(Boolean isNegative) {
        this.isNegative = isNegative;
    }

    public Boolean getIsBeforeImmat() {
        return isBeforeImmat;
    }

    public void setIsBeforeImmat(Boolean isBeforeImmat) {
        this.isBeforeImmat = isBeforeImmat;
    }

    public Boolean getIsSais() {
        return isSais;
    }

    public void setIsSais(Boolean isSais) {
        this.isSais = isSais;
    }

}
