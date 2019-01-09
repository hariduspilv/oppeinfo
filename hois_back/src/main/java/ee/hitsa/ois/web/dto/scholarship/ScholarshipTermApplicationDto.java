package ee.hitsa.ois.web.dto.scholarship;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.StudyPeriodDto;

public class ScholarshipTermApplicationDto extends ScholarshipTermStudentDto {
    private Boolean isAcademicLeave;
    private StudyPeriodDto studyPeriod;
    private List<String> studyForms;
    private List<String> studyLoads;
    private BigDecimal amountPaid;
    private LocalDate studyStartPeriodStart;
    private LocalDate studyStartPeriodEnd;
    private LocalDate paymentStart;
    private LocalDate paymentEnd;

    public static ScholarshipTermApplicationDto of(ScholarshipTerm term) {
        ScholarshipTermApplicationDto dto = new ScholarshipTermApplicationDto();
        EntityUtil.bindToDto(term, dto, "studyPeriod");
        dto.setStudyForms(StreamUtil.toMappedList(t -> EntityUtil.getCode(t.getStudyForm()),
                term.getScholarshipTermStudyForms()));
        
        dto.setStudyLoads(StreamUtil.toMappedList(t -> EntityUtil.getCode(t.getStudyLoad()),
                term.getScholarshipTermStudyLoads()));
        dto.setStudyPeriod(StudyPeriodDto.of(term.getStudyPeriod()));
        dto.setStudyStartPeriodStart(term.getStudyStartPeriodStart());
        dto.setStudyStartPeriodEnd(term.getStudyStartPeriodEnd());
        dto.setPaymentStart(term.getPaymentStart());
        dto.setPaymentEnd(term.getPaymentEnd());
        dto.setAddInfo(term.getAddInfo());
        return dto;
    }

    public Boolean getIsAcademicLeave() {
        return isAcademicLeave;
    }

    public void setIsAcademicLeave(Boolean isAcademicLeave) {
        this.isAcademicLeave = isAcademicLeave;
    }

    public StudyPeriodDto getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriodDto studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public List<String> getStudyForms() {
        return studyForms;
    }

    public void setStudyForms(List<String> studyForms) {
        this.studyForms = studyForms;
    }

    public List<String> getStudyLoads() {
        return studyLoads;
    }

    public void setStudyLoads(List<String> studyLoads) {
        this.studyLoads = studyLoads;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
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

}
