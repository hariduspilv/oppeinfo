package ee.hitsa.ois.report.studyyearschedule;

import java.util.List;

import ee.hitsa.ois.web.dto.StudyPeriodDto;

public class ReportStudyPeriod {

    private StudyPeriodDto period;
    private List<Short> weeks;
    
    public StudyPeriodDto getPeriod() {
        return period;
    }
    public void setPeriod(StudyPeriodDto period) {
        this.period = period;
    }
    
    public List<Short> getWeeks() {
        return weeks;
    }
    public void setWeeks(List<Short> weeks) {
        this.weeks = weeks;
    }
    
}
