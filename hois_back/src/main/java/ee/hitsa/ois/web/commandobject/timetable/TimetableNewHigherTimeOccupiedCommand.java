package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimetableNewHigherTimeOccupiedCommand {

    private Long timetable;
    private Long oldEventId;
    private Long subjectStudyPeriod;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String repeatCode;
    private LocalDate repeatUntil;
    private Long lessonAmount;
    private Long room;
    
    public Long getTimetable() {
        return timetable;
    }

    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }
    
    public Long getOldEventId() {
        return oldEventId;
    }

    public void setOldEventId(Long oldEventId) {
        this.oldEventId = oldEventId;
    }

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    
    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getRepeatCode() {
        return repeatCode;
    }
    
    public void setRepeatCode(String repeatCode) {
        this.repeatCode = repeatCode;
    }

    public LocalDate getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(LocalDate repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public Long getLessonAmount() {
        return lessonAmount;
    }
    
    public void setLessonAmount(Long lessonAmount) {
        this.lessonAmount = lessonAmount;
    }
    
    public Long getRoom() {
        return room;
    }
    
    public void setRoom(Long room) {
        this.room = room;
    }
    
}
