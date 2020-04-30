package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.StreamUtil;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableChanged extends StudentMessage {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final String subjectCode;
    private final String subjectName;
    private final List<TimetableEventTime> changedEvents;

    public TimetableChanged() {
        subjectCode = null;
        subjectName = null;
        changedEvents = null;
    }

    public TimetableChanged(Student student, Subject subject, String journalName,
            List<TimetableEventTime> changedEvents) {
        super(student);

        subjectCode = subject != null ? subject.getCode() : "";
        subjectName = subject != null ? subject.getNameEt() : journalName;
        changedEvents.sort(Comparator.comparing(TimetableEventTime::getStart));
        this.changedEvents = changedEvents;
    }

    public String getOppeaineKood() {
        return subjectCode;
    }

    public String getOppeaineNimetus() {
        return subjectName;
    }

    public String getSundmuseAeg() {
        return StreamUtil.nullSafeList(changedEvents).stream().map(this::eventTime).collect(Collectors.joining(", "));
    }

    private String eventTime(TimetableEventTime event) {
        String date = DateUtils.date(event.getStart().toLocalDate());
        return date + " " + event.getStart().format(TIME_FORMATTER) + " - " + event.getEnd().format(TIME_FORMATTER);
    }
}
