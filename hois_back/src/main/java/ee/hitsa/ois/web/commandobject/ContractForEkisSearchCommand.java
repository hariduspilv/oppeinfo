package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.web.dto.student.StudentGroupResult;

public class ContractForEkisSearchCommand {
    private Long studentGroup;
    private String studentName;


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
}
