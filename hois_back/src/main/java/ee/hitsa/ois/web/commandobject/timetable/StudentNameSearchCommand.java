package ee.hitsa.ois.web.commandobject.timetable;

import java.util.Set;

public class StudentNameSearchCommand {

    private String studentName;
    private Set<Long> studentId;

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public Set<Long> getStudentId() {
        return studentId;
    }
    public void setStudentId(Set<Long> studentId) {
        this.studentId = studentId;
    }
}
