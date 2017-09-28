package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDateTime;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class TimetableEventHigherForm extends TimetableEventForm {
    private Long subjectStudyPeriod;
    // if studentgroup is empty then it applies to all groups
    private Long subjectStudyPeriodStudentGroup;
    private LocalDateTime startTime;
    private Long lessonAmount;
    @ClassifierRestriction(MainClassCode.TUNNIPLAAN_SYNDMUS_KORDUS)
    private String repeatCode;

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Long getSubjectStudyPeriodStudentGroup() {
        return subjectStudyPeriodStudentGroup;
    }

    public void setSubjectStudyPeriodStudentGroup(Long subjectStudyPeriodStudentGroup) {
        this.subjectStudyPeriodStudentGroup = subjectStudyPeriodStudentGroup;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getLessonAmount() {
        return lessonAmount;
    }

    public void setLessonAmount(Long lessonAmount) {
        this.lessonAmount = lessonAmount;
    }

    public String getRepeatCode() {
        return repeatCode;
    }

    public void setRepeatCode(String repeatCode) {
        this.repeatCode = repeatCode;
    }

}
