package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class JournalSearchCommand {

    private Long studyYear;
    private Long studentGroup;
    private Long teacher;
    private List<Long> module;
    private String journalName;

    @ClassifierRestriction(MainClassCode.PAEVIK_STAATUS)
    private String status;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public List<Long> getModule() {
        return module;
    }

    public void setModule(List<Long> module) {
        this.module = module;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
