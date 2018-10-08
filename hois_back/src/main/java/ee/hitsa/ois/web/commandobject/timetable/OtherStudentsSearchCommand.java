package ee.hitsa.ois.web.commandobject.timetable;

import java.util.Set;

public class OtherStudentsSearchCommand {

    private String studentName;
    private Set<Long> studentId;
    private Long studentGroupId;

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
    public Long getStudentGroupId() {
        return studentGroupId;
    }
    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }
    
}
