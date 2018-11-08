package ee.hitsa.ois.report.studyyearschedule;

import java.util.List;

public class ReportTable {

    private List<ReportStudyPeriod> studyPeriods;
    private List<Integer> weeks;
    private List<ReportDepartment> departments;
    
    public List<ReportStudyPeriod> getStudyPeriods() {
        return studyPeriods;
    }
    public void setStudyPeriods(List<ReportStudyPeriod> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }
    
    public List<Integer> getWeeks() {
        return weeks;
    }
    public void setWeeks(List<Integer> weeks) {
        this.weeks = weeks;
    }
    
    public List<ReportDepartment> getDepartments() {
        return departments;
    }
    public void setDepartments(List<ReportDepartment> departments) {
        this.departments = departments;
    }
    
}
