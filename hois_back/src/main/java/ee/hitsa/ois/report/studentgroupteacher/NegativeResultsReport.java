package ee.hitsa.ois.report.studentgroupteacher;

import java.util.List;

public class NegativeResultsReport {

    public static final String TEMPLATE_NAME = "student.group.teacher.negative.results.xhtml";

    private String studentGroup;
    private List<NegativeResultsStudentReport> students;

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<NegativeResultsStudentReport> getStudents() {
        return students;
    }

    public void setStudents(List<NegativeResultsStudentReport> students) {
        this.students = students;
    }

}
