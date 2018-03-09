package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;

public class TimetableChanged extends StudentMessage {

    private final String subjectCode;
    private final String subjectName;

    public TimetableChanged() {
        subjectCode = null;
        subjectName = null;
    }

    public TimetableChanged(Student student, Subject subject, String journalName) {
        super(student);

        subjectCode = subject != null ? subject.getCode() : "";
        subjectName = subject != null ? subject.getNameEt() : journalName;
    }

    public String getOppeaineKood() {
        return subjectCode;
    }

    public String getOppeaineNimetus() {
        return subjectName;
    }
}
