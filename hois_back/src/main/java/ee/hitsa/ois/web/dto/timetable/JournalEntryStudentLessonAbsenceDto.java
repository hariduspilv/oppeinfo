package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.timetable.JournalEntryStudentLessonAbsence;
import ee.hitsa.ois.util.EntityUtil;

public class JournalEntryStudentLessonAbsenceDto {
    
    private Long lessonNr;
    private String absence;
    private LocalDateTime absenceInserted;
    
    public JournalEntryStudentLessonAbsenceDto() {
    }
    
    public JournalEntryStudentLessonAbsenceDto(JournalEntryStudentLessonAbsenceDto lessonAbsence) {
        this.lessonNr = lessonAbsence.getLessonNr();
        this.absence = lessonAbsence.getAbsence();
        this.absenceInserted = lessonAbsence.getAbsenceInserted();
    }
    
    public JournalEntryStudentLessonAbsenceDto(JournalEntryStudentLessonAbsence lessonAbsence) {
        lessonNr = lessonAbsence.getLessonNr();
        absence = EntityUtil.getCode(lessonAbsence.getAbsence());
        absenceInserted = lessonAbsence.getAbsenceInserted();
    }

    public Long getLessonNr() {
        return lessonNr;
    }

    public void setLessonNr(Long lessonNr) {
        this.lessonNr = lessonNr;
    }

    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }

    public LocalDateTime getAbsenceInserted() {
        return absenceInserted;
    }

    public void setAbsenceInserted(LocalDateTime absenceInserted) {
        this.absenceInserted = absenceInserted;
    }

}
