package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudyYearForm;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StudyYearDto extends StudyYearForm {
    private Long id;

    private Set<StudyPeriodDto> studyPeriods;

    private Set<StudyPeriodEventDto> studyPeriodEvents;

    public static StudyYearDto of(StudyYear studyYear) {
        StudyYearDto dto = EntityUtil.bindToDto(studyYear, new StudyYearDto(), "studyPeriods");
        dto.setStudyPeriodEvents(new HashSet<>());
        dto.setStudyPeriods(studyYear.getStudyPeriods().stream().map(StudyPeriodDto::of).collect(Collectors.toSet()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<StudyPeriodDto> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(Set<StudyPeriodDto> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public Set<StudyPeriodEventDto> getStudyPeriodEvents() {
        return studyPeriodEvents;
    }

    public void setStudyPeriodEvents(Set<StudyPeriodEventDto> studyPeriodEvents) {
        this.studyPeriodEvents = studyPeriodEvents;
    }
}
