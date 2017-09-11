package ee.hitsa.ois.web.commandobject.ehis;

import java.time.LocalDate;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "changeDateFrom", thru = "changeDateTo")
public class EhisTeacherExportForm {

    private boolean allDates;
    private boolean subjectData;
    private LocalDate changeDateFrom;
    private LocalDate changeDateTo;

    public boolean isAllDates() {
        return allDates;
    }

    public void setAllDates(boolean allDates) {
        this.allDates = allDates;
    }

    public boolean isSubjectData() {
        return subjectData;
    }

    public void setSubjectData(boolean subjectData) {
        this.subjectData = subjectData;
    }

    public LocalDate getChangeDateFrom() {
        return changeDateFrom;
    }

    public void setChangeDateFrom(LocalDate changeDateFrom) {
        this.changeDateFrom = changeDateFrom;
    }

    public LocalDate getChangeDateTo() {
        return changeDateTo;
    }

    public void setChangeDateTo(LocalDate changeDateTo) {
        this.changeDateTo = changeDateTo;
    }
}
