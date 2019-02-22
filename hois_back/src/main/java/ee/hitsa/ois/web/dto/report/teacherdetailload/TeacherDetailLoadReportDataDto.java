package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.web.commandobject.report.TeacherDetailLoadCommand;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.StudyPeriodDto;

public class TeacherDetailLoadReportDataDto {

    private List<StudyPeriodDto> studyPeriods;
    private List<Short> weekNrs;
    private List<LocalDate> weekBeginningDates;
    private Map<Short, LocalDate> weekBeginningDateMap;
    private Map<Long, List<Short>> weekNrsByMonth;
    private List<Long> months;
    private TeacherDetailLoadCommand criteria;

    public List<StudyPeriodDto> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(List<StudyPeriodDto> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public List<Short> getWeekNrs() {
        return weekNrs;
    }

    public void setWeekNrs(List<Short> weekNrs) {
        this.weekNrs = weekNrs;
    }

    public List<LocalDate> getWeekBeginningDates() {
        return weekBeginningDates;
    }

    public void setWeekBeginningDates(List<LocalDate> weekBeginningDates) {
        this.weekBeginningDates = weekBeginningDates;
    }

    public Map<Short, LocalDate> getWeekBeginningDateMap() {
        return weekBeginningDateMap;
    }

    public void setWeekBeginningDateMap(Map<Short, LocalDate> weekBeginningDateMap) {
        this.weekBeginningDateMap = weekBeginningDateMap;
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
