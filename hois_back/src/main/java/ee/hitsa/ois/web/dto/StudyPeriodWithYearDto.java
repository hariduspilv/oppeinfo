package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudyPeriodForm;

public class StudyPeriodWithYearDto extends StudyPeriodForm {
    private Long id;
    private Long studyYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
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
