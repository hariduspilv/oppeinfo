package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.Student;

public abstract class StudentMessage {

    private final String studentFullname;
    private final String studentIdcode;

    public StudentMessage() {
        this.studentFullname = null;
        this.studentIdcode = null;
    }

    public StudentMessage(Student student) {
        this.studentFullname = student.getPerson().getFullname();
        this.studentIdcode = student.getPerson().getIdcode();
    }

    public String getStudentFullname() {
        return studentFullname;
    }

    public String getStudentIdcode() {
        return studentIdcode;
    }

    public String getOppuriNimi() {
        return studentFullname;
    }

    public String getOppuriIsikukood() {
        return studentIdcode;
    }
}
