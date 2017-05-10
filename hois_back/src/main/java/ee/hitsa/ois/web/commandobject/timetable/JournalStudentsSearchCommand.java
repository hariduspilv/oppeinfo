package ee.hitsa.ois.web.commandobject.timetable;

public class JournalStudentsSearchCommand {

    private String studentName;
    private Boolean restrictStudentGroup;

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public Boolean getRestrictStudentGroup() {
        return restrictStudentGroup;
    }
    public void setRestrictStudentGroup(Boolean restrictStudentGroup) {
        this.restrictStudentGroup = restrictStudentGroup;
    }


}
