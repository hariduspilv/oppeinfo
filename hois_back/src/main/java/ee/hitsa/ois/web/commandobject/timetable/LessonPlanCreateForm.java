package ee.hitsa.ois.web.commandobject.timetable;

import javax.validation.constraints.NotNull;

public class LessonPlanCreateForm {

    @NotNull
    private Long studyYear;
    @NotNull
    private Long studentGroup;
    private Long previousLessonplan;
    private Boolean copyLessons = Boolean.FALSE;

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

    public Long getPreviousLessonplan() {
        return previousLessonplan;
    }

    public void setPreviousLessonplan(Long previousLessonplan) {
        this.previousLessonplan = previousLessonplan;
    }

    public Boolean getCopyLessons() {
        return copyLessons;
    }

    public void setCopyLessons(Boolean copyLessons) {
        this.copyLessons = copyLessons;
    }
    
}
