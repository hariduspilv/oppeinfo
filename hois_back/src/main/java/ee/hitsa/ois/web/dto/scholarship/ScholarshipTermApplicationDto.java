package ee.hitsa.ois.web.dto.scholarship;

import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.StudyPeriodDto;

public class ScholarshipTermApplicationDto extends ScholarshipTermStudentDto {
    private Boolean isAcademicLeave;
    private StudyPeriodDto studyPeriod;

    public static ScholarshipTermApplicationDto of(ScholarshipTerm term) {
        ScholarshipTermApplicationDto dto = new ScholarshipTermApplicationDto();
        EntityUtil.bindToDto(term, dto, "studyPeriod");
        dto.setNameEt(term.getNameEt());
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

}
