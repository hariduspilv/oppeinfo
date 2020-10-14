package ee.hitsa.ois.web.commandobject;
import javax.validation.Valid;

import ee.hitsa.ois.web.dto.StudyYearScheduleDto;

public class StudyYearScheduleCommand extends StudyYearScheduleDtoContainer {

    @Valid
    private StudyYearScheduleDto studyYearSchedule;

    public StudyYearScheduleDto getStudyYearSchedule() {
        return studyYearSchedule;
    }

    public void setStudyYearSchedule(StudyYearScheduleDto studyYearSchedule) {
        this.studyYearSchedule = studyYearSchedule;
    }
    
}
