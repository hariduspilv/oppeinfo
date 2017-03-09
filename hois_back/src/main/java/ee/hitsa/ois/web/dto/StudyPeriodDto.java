package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudyPeriodForm;

public class StudyPeriodDto extends StudyPeriodForm {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static StudyPeriodDto of(StudyPeriod studyPeriod) {
        return EntityUtil.bindToDto(studyPeriod, new StudyPeriodDto());
    }
}
