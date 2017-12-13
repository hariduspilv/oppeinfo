package ee.hitsa.ois.web.dto.scholarship;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ScholarshipTermDto {
    private Long id;
    private String type;
    private String nameEt;
    private Long studyPeriod;
    private LocalDate applicationStart;
    private LocalDate applicationEnd;
    private LocalDate paymentStart;
    private LocalDate paymentEnd;
    private Long places;
    private Long amountPaid;
    private List<AutocompleteResult> curriculums;
    private List<String> studyLoads;
    private List<String> studyForms;
    private List<String> courses;
    private Long averageMark;
    private String averageMarkPriority;
    private Long lastPeriodMark;
    private String lastPeriodMarkPriority;
    private Long curriculumCompletion;
    private String curriculumCompletionPriority;
    private Long maxAbsences;
    private String maxAbsencesPriority;
    private LocalDate studyStartPeriodStart;
    private LocalDate studyStartPeriodEnd;
    private Boolean isAcademicLeave;
    private Boolean isStudyBacklog;
    private Boolean isTeacherConfirm;
    private Boolean isFamilyIncomes;
    private Boolean isOpen;

    public static ScholarshipTermDto of(ScholarshipTerm term) {
        ScholarshipTermDto dto = new ScholarshipTermDto();
        EntityUtil.bindToDto(term, dto);
        dto.setCourses(
                StreamUtil.toMappedList(t -> EntityUtil.getCode(t.getCourse()), term.getScholarshipTermCourses()));
        dto.setCurriculums(StreamUtil.toMappedList(t -> AutocompleteResult.of(t.getCurriculum()),
                term.getScholarshipTermCurriculums()));
        dto.setStudyLoads(StreamUtil.toMappedList(t -> EntityUtil.getCode(t.getStudyLoad()),
                term.getScholarshipTermStudyLoads()));
        dto.setStudyForms(StreamUtil.toMappedList(t -> EntityUtil.getCode(t.getStudyForm()),
                term.getScholarshipTermStudyForms()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
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

    public Long getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(Long averageMark) {
        this.averageMark = averageMark;
    }

    public String getAverageMarkPriority() {
        return averageMarkPriority;
    }

    public void setAverageMarkPriority(String averageMarkPriority) {
        this.averageMarkPriority = averageMarkPriority;
    }

    public Long getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(Long lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public String getLastPeriodMarkPriority() {
        return lastPeriodMarkPriority;
    }

    public void setLastPeriodMarkPriority(String lastPeriodMarkPriority) {
        this.lastPeriodMarkPriority = lastPeriodMarkPriority;
    }

    public Long getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(Long curriculumCompletion) {
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

}
