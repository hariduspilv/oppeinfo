package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudyYearForm;

import java.util.HashSet;

public class StudyYearDto extends StudyYearForm {
    private Long id;

    public static StudyYearDto of(StudyYear studyYear) {
        StudyYearDto dto = EntityUtil.bindToDto(studyYear, new StudyYearDto());
        dto.setStudyPeriodEvents(new HashSet<>());
        dto.setStudyPeriods(new HashSet<>());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
