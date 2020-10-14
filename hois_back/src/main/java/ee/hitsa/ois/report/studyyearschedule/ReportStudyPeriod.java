package ee.hitsa.ois.report.studyyearschedule;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;
import ee.hitsa.ois.web.dto.report.WeekStartAndEndDto;

public class ReportStudyPeriod {

    private StudyPeriodWithWeeksDto period;
    private List<Short> weeks;
    private List<WeekStartAndEndDto> weekDates = new ArrayList<>();
    private Short startWeek;
    private Short endWeek;

    public StudyPeriodWithWeeksDto getPeriod() {
        return period;
    }

    public void setPeriod(StudyPeriodWithWeeksDto period) {
        this.period = period;
    }

    public List<Short> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Short> weeks) {
        this.weeks = weeks;
    }

    public Short getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Short startWeek) {
        this.startWeek = startWeek;
    }

    public Short getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Short endWeek) {
        this.endWeek = endWeek;
    }

    public List<WeekStartAndEndDto> getWeekDates() {
        return weekDates;
    }

    public void setWeekDates(List<WeekStartAndEndDto> weekDates) {
        this.weekDates = weekDates;
    }
}
