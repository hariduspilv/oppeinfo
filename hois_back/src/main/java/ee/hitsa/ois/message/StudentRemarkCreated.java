package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;

public class StudentRemarkCreated extends StudentMessage {

    private final String studentGroupCode;
    
    public StudentRemarkCreated() {
        studentGroupCode = null;
    }
    
    public StudentRemarkCreated(Student student) {
        super(student);
        
        StudentGroup studentGroup = student.getStudentGroup();
        studentGroupCode = studentGroup != null ? studentGroup.getCode() : "";
    }
    
    public String getRuhmaTahis() {
        return studentGroupCode;
    }
}
