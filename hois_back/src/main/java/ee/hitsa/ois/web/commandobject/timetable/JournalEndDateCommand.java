package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;

public class JournalEndDateCommand {

    private LocalDate endDate;

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
