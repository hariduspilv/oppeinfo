package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryStudentForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryStudentLessonAbsenceForm;

public class JournalEntryStudentDto extends JournalEntryStudentForm {

    private String studentName;
    private String studentGroup;
    private List<JournalEntryStudentHistoryDto> journalEntryStudentHistories;

    public static JournalEntryStudentDto of(JournalEntryStudent journalEntryStudent) {
        JournalEntryStudentDto dto = EntityUtil.bindToDto(journalEntryStudent, new JournalEntryStudentDto(),
                "journalEntryStudentHistories");
        dto.setLessonAbsences(StreamUtil.toMap(r -> r.getLessonNr(), r -> JournalEntryStudentLessonAbsenceForm.of(r),
                journalEntryStudent.getJournalEntryStudentLessonAbsences()));
        dto.journalEntryStudentHistories = StreamUtil.toMappedList(JournalEntryStudentHistoryDto::new,
                journalEntryStudent.getJournalEntryStudentHistories());
        return dto;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<JournalEntryStudentHistoryDto> getJournalEntryStudentHistories() {
        return journalEntryStudentHistories;
    }

    public void setJournalEntryStudentHistories(List<JournalEntryStudentHistoryDto> journalEntryStudentHistories) {
        this.journalEntryStudentHistories = journalEntryStudentHistories;
    }
}
