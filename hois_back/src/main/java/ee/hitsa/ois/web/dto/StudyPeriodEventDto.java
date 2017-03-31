package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudyPeriodEventForm;

public class StudyPeriodEventDto extends StudyPeriodEventForm {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static StudyPeriodEventDto of(StudyPeriodEvent studyPeriodEvent) {
        StudyPeriodEventDto dto = EntityUtil.bindToDto(studyPeriodEvent, new StudyPeriodEventDto());
        return dto;
    }
}
