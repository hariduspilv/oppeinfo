package ee.hitsa.ois.web.commandobject.timetable;

import ee.hitsa.ois.domain.timetable.JournalEntryStudentLessonAbsence;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalEntryStudentLessonAbsenceForm {

    private Long id;
    @ClassifierRestriction(MainClassCode.PUUDUMINE)
    private String absence;
    private Long lessonNr;

    public static JournalEntryStudentLessonAbsenceForm of(JournalEntryStudentLessonAbsence lessonAbsence) {
        JournalEntryStudentLessonAbsenceForm form = new JournalEntryStudentLessonAbsenceForm();
        form.setId(lessonAbsence.getId());
        form.setAbsence(EntityUtil.getCode(lessonAbsence.getAbsence()));
        form.setLessonNr(lessonAbsence.getLessonNr());
        return form;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbsence() {
        return absence;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }

    public Long getLessonNr() {
        return lessonNr;
    }

    public void setLessonNr(Long lessonNr) {
        this.lessonNr = lessonNr;
    }

}
