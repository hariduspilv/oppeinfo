package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimetableDatesDto {

    private List<LocalDate> dates;

    public TimetableDatesDto(LocalDate from, LocalDate thru) {
        dates = new ArrayList<>();
        while (!from.isAfter(thru)) {
            dates.add(from);
            from = from.plusDays(1);
        }
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

}
