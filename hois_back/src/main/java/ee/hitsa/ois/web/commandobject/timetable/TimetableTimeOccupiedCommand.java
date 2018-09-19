package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDateTime;
import java.util.List;

public class TimetableTimeOccupiedCommand {

    private Long timetable;
    private Long timetableEventId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Long> rooms;
    private List<Long> teachers;
    private List<Long> studentGroups;
    
    private String repeatCode;
    private Long weekAmount;
    
    private Long exam;
    private Long subjectStudyPeriod;
    
    public Long getTimetable() {
        return timetable;
    }

    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }

    public Long getTimetableEventId() {
        return timetableEventId;
    }
    
    public void setTimetableEventId(Long timetableEventId) {
        this.timetableEventId = timetableEventId;
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
    
    public List<Long> getRooms() {
        return rooms;
    }
    
    public void setRooms(List<Long> rooms) {
        this.rooms = rooms;
    }
    
    public List<Long> getTeachers() {
        return teachers;
    }
    
    public void setTeachers(List<Long> teachers) {
        this.teachers = teachers;
    }

    public List<Long> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public String getRepeatCode() {
        return repeatCode;
    }

    public void setRepeatCode(String repeatCode) {
        this.repeatCode = repeatCode;
    }

    public Long getWeekAmount() {
        return weekAmount;
    }

    public void setWeekAmount(Long weekAmount) {
        this.weekAmount = weekAmount;
    }
    
    public Long getExam() {
        return exam;
    }

    public void setExam(Long exam) {
        this.exam = exam;
    }

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

}
