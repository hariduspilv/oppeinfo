package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.Required;

@DateRange(from = "startDate", thru = "endDate")
public class TimetableEditForm {

    private Long studyYear;
    @NotNull
    private Long studyPeriod;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Required
    @ClassifierRestriction(MainClassCode.TUNNIPLAAN_LIIK)
    private String code;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
