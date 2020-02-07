package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.util.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimetableChanged extends StudentMessage {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final String subjectCode;
    private final String subjectName;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimetableChanged() {
        subjectCode = null;
        subjectName = null;
        start = null;
        end = null;
    }

    public TimetableChanged(Student student, Subject subject, String journalName, LocalDateTime start, LocalDateTime end) {
        super(student);

        subjectCode = subject != null ? subject.getCode() : "";
        subjectName = subject != null ? subject.getNameEt() : journalName;
        this.start = start;
        this.end = end;
    }

    public String getOppeaineKood() {
        return subjectCode;
    }

    public String getOppeaineNimetus() {
        return subjectName;
    }

    public String getSundmuseAeg() {
        if (start == null || end == null) {
            return null;
        }
        return DateUtils.date(start.toLocalDate()) + " " + start.format(TIME_FORMATTER) + " - " + end.format(TIME_FORMATTER);
    }
}
