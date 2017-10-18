package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.RoomDto;

public class TimetableEventHigherForm extends TimetableEventForm {
    private Long subjectStudyPeriod;
    // if studentgroup is empty then it applies to all groups
    private Long studentGroupId;
    @NotNull
    private LocalDateTime startTime;
    private Long lessonAmount;
    @ClassifierRestriction(MainClassCode.TUNNIPLAAN_SYNDMUS_KORDUS)
    private String repeatCode;
    private RoomDto room;
    private Boolean isSubjectTeacherPair;

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Long getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
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

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public Boolean getIsSubjectTeacherPair() {
        return isSubjectTeacherPair;
    }

    public void setIsSubjectTeacherPair(Boolean isSubjectTeacherPair) {
        this.isSubjectTeacherPair = isSubjectTeacherPair;
    }

}
