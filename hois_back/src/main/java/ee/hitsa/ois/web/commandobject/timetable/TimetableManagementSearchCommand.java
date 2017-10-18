package ee.hitsa.ois.web.commandobject.timetable;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class TimetableManagementSearchCommand {

    @NotNull
    private Long studyYear;
    @NotNull
    private Long studyPeriod;
    @ClassifierRestriction(MainClassCode.TUNNIPLAAN_LIIK)
    private String type;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
