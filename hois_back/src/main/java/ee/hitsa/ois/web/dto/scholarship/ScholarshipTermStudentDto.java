package ee.hitsa.ois.web.dto.scholarship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ScholarshipTermStudentDto extends ScholarshipTermSearchDto {
    private Long applicationId;
    private BigDecimal averageMark;
    private BigDecimal lastPeriodMark;
    private BigDecimal curriculumCompletion;
    private Long maxAbsences;
    private String addInfo;
    private Boolean isTeacherConfirm;
    private Boolean isFamilyIncomes;
    private String status;
    private LocalDate decisionDate;
    private String rejectComment;
    private LocalDate studyStartPeriodStart;
    private LocalDate studyStartPeriodEnd;
    private LocalDate paymentStart;
    private LocalDate paymentEnd;
    private BigDecimal amountPaid;
    private List<AutocompleteResult> curriculums;
    private List<String> studyLoads;
    private List<String> studyForms;
    private List<String> courses;
    private Boolean isStudyBacklog;
    private ScholarshipTermComplianceDto termCompliance;

    public ScholarshipTermStudentDto() {

    }

    public ScholarshipTermStudentDto(Long id, String nameEt, String type, LocalDate applicationStart,
            LocalDate applicationEnd, Long places, Boolean isOpen) {
        super(id, nameEt, type, applicationStart, applicationEnd, places, isOpen);
    }

    public static ScholarshipTermStudentDto of(ScholarshipTerm term) {
        ScholarshipTermStudentDto dto = new ScholarshipTermStudentDto();
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

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
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

    public Long getMaxAbsences() {
        return maxAbsences;
    }

    public void setMaxAbsences(Long maxAbsences) {
        this.maxAbsences = maxAbsences;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDate decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
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

    public Boolean getIsStudyBacklog() {
        return isStudyBacklog;
    }

    public void setIsStudyBacklog(Boolean isStudyBacklog) {
        this.isStudyBacklog = isStudyBacklog;
    }

    public ScholarshipTermComplianceDto getTermCompliance() {
        return termCompliance;
    }

    public void setTermCompliance(ScholarshipTermComplianceDto termCompliance) {
        this.termCompliance = termCompliance;
    }

}
