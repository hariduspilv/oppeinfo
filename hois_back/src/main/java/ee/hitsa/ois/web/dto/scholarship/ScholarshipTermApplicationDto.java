package ee.hitsa.ois.web.dto.scholarship;

import java.math.BigDecimal;
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
    private String matriculationRange;

    public static ScholarshipTermApplicationDto of(ScholarshipTerm term) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        ScholarshipTermApplicationDto dto = new ScholarshipTermApplicationDto();
        EntityUtil.bindToDto(term, dto, "studyPeriod");
        dto.setStudyForms(StreamUtil.toMappedList(t -> EntityUtil.getCode(t.getStudyForm()),
                term.getScholarshipTermStudyForms()));
        if (term.getStudyStartPeriodStart() != null) {
            dto.setMatriculationRange(term.getStudyStartPeriodStart().format(formatter) + "-"
                    + term.getStudyStartPeriodEnd().format(formatter));
        }
        dto.setStudyLoads(StreamUtil.toMappedList(t -> EntityUtil.getCode(t.getStudyLoad()),
                term.getScholarshipTermStudyLoads()));
        dto.setStudyPeriod(StudyPeriodDto.of(term.getStudyPeriod()));
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

    public String getMatriculationRange() {
        return matriculationRange;
    }

    public void setMatriculationRange(String matriculationRange) {
        this.matriculationRange = matriculationRange;
    }

}
