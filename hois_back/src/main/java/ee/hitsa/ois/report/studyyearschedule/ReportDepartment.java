package ee.hitsa.ois.report.studyyearschedule;

import java.util.List;

import ee.hitsa.ois.web.dto.SchoolDepartmentResult;

public class ReportDepartment {

    private SchoolDepartmentResult department;
    private List<ReportStudentGroup> studentGroups;
    
    public SchoolDepartmentResult getDepartment() {
        return department;
    }
    public void setDepartment(SchoolDepartmentResult department) {
        this.department = department;
    }
    
    public List<ReportStudentGroup> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(List<ReportStudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }
    
}
