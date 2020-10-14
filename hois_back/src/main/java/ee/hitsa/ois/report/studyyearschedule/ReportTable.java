package ee.hitsa.ois.report.studyyearschedule;

import java.util.List;

public class ReportTable {

    private String studyYear;
    private List<ReportStudyPeriod> studyPeriods;
    private List<Short> weeks;
    private List<ReportDepartment> departments;
    
    public List<ReportStudyPeriod> getStudyPeriods() {
        return studyPeriods;
    }
    public void setStudyPeriods(List<ReportStudyPeriod> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }
    
    public List<Short> getWeeks() {
        return weeks;
    }
    public void setWeeks(List<Short> weeks) {
        this.weeks = weeks;
    }
    
    public List<ReportDepartment> getDepartments() {
        return departments;
    }
    public void setDepartments(List<ReportDepartment> departments) {
        this.departments = departments;
    }
    public String getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }
    
}
