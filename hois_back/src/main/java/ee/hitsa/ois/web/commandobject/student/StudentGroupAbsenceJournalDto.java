package ee.hitsa.ois.web.commandobject.student;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentGroupAbsenceJournalDto extends AutocompleteResult {

    // first absence info if multiple absences
    private Long startLessonNr;
    private Long lessons;

    public StudentGroupAbsenceJournalDto(AutocompleteResult result, Long startLessonNr, Long lessons) {
        super(result.getId(), result.getNameEt(), result.getNameEt());
        this.startLessonNr = startLessonNr;
        this.lessons = lessons;
    }

    public Long getStartLessonNr() {
        return startLessonNr;
    }

    public void setStartLessonNr(Long startLessonNr) {
        this.startLessonNr = startLessonNr;
    }

    public Long getLessons() {
        return lessons;
    }

    public void setLessons(Long lessons) {
        this.lessons = lessons;
    }
}
