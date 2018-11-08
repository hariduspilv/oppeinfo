package ee.hitsa.ois.web.dto.timetable;

public class StudentJournalEntryLessonAbsenceDto {

    private Long lessonNr;
    private String absence;

    public StudentJournalEntryLessonAbsenceDto(Long lessonNr, String absence) {
        this.lessonNr = lessonNr;
        this.absence = absence;
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

}
