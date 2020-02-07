package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.web.commandobject.report.TeacherDetailLoadCommand;
import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;
import ee.hitsa.ois.web.dto.WeekDto;

public class TeacherDetailLoadReportDataDto {

    private List<StudyPeriodWithWeeksDto> studyPeriods;
    private List<Short> weekNrs;
    private Map<Short, LocalDate> weekBeginningDateMap;
    private List<WeekDto> weeks;
    private Map<Long, List<Short>> weekNrsByMonth;
    private List<Long> months;
    private TeacherDetailLoadCommand criteria;

    public List<StudyPeriodWithWeeksDto> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(List<StudyPeriodWithWeeksDto> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public List<Short> getWeekNrs() {
        return weekNrs;
    }

    public void setWeekNrs(List<Short> weekNrs) {
        this.weekNrs = weekNrs;
    }

    public Map<Short, LocalDate> getWeekBeginningDateMap() {
        return weekBeginningDateMap;
    }

    public void setWeekBeginningDateMap(Map<Short, LocalDate> weekBeginningDateMap) {
        this.weekBeginningDateMap = weekBeginningDateMap;
    }

    public List<WeekDto> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<WeekDto> weeks) {
        this.weeks = weeks;
    }

    public Map<Long, List<Short>> getWeekNrsByMonth() {
        return weekNrsByMonth;
    }

    public void setWeekNrsByMonth(Map<Long, List<Short>> weekNrsByMonth) {
        this.weekNrsByMonth = weekNrsByMonth;
    }

    public List<Long> getMonths() {
        return months;
    }

    public void setMonths(List<Long> months) {
        this.months = months;
    }

    public TeacherDetailLoadCommand getCriteria() {
        return criteria;
    }

    public void setCriteria(TeacherDetailLoadCommand criteria) {
        this.criteria = criteria;
    }

}
