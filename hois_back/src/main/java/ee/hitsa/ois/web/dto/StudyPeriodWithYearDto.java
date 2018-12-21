package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.util.EntityUtil;

public class StudyPeriodWithYearDto extends StudyPeriodDto {
    
    private StudyYearSearchDto studyYear;

    public StudyYearSearchDto getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(StudyYearSearchDto studyYear) {
        this.studyYear = studyYear;
    }
    
    public static StudyPeriodWithYearDto of(StudyPeriod studyPeriod) {
        StudyPeriodWithYearDto dto = EntityUtil.bindToDto(studyPeriod, new StudyPeriodWithYearDto());
        dto.setStudyYear(StudyYearSearchDto.of(studyPeriod.getStudyYear()));
        return dto;
    }
}
