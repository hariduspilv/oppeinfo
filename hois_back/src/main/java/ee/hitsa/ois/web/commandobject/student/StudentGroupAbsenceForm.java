package ee.hitsa.ois.web.commandobject.student;

public class StudentGroupAbsenceForm {

    private Long studentGroup;
    private String absence;

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }

}
