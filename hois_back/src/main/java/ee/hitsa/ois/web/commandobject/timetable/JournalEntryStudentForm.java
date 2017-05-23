package ee.hitsa.ois.web.commandobject.timetable;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalEntryStudentForm {

    private Long id;
    private Long journalStudent;
    @ClassifierRestriction(MainClassCode.PUUDUMINE)
    private String absence;
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMINE)
    private String grade;
    private String addInfo;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getJournalStudent() {
        return journalStudent;
    }
    public void setJournalStudent(Long journalStudent) {
        this.journalStudent = journalStudent;
    }
    public String getAbsence() {
        return absence;
    }
    public void setAbsence(String absence) {
        this.absence = absence;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }


}
