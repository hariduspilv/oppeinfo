package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@DateRange(from = "startDate", thru = "endDate")
public class StudyYearForm extends VersionedCommand {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @ClassifierRestriction(MainClassCode.OPPEAASTA)
    private String year;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
