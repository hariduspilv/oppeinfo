package ee.hitsa.ois.report.studyyearschedule;

import java.util.List;

import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;

public class ReportStudyPeriod {

    private StudyPeriodWithWeeksDto period;
    private List<Short> weeks;
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
}
