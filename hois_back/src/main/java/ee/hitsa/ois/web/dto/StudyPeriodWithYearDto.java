package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.util.EntityUtil;

public class StudyPeriodWithYearDto extends StudyPeriodDto {

    private Long studyYear;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public static StudyPeriodWithYearDto of(StudyPeriod studyPeriod) {
        return EntityUtil.bindToDto(studyPeriod, new StudyPeriodWithYearDto());
    }
}
