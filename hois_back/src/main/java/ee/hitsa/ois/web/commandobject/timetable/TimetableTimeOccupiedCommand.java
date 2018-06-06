package ee.hitsa.ois.web.commandobject.timetable;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class TimetableTimeOccupiedCommand {

    // TimetableService.saveVocationalEventRoomsAndTimes
    private Long timetableEventId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Long> rooms;
    private List<Long> teachers;
    
    // TimetableService.saveVocationalEvent
    private Long journal;
    private Long timetable;
    private Long lessonTime;
    private String selectedDay;
    
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
    
    public Long getJournal() {
        return journal;
    }

    public void setJournal(Long journal) {
        this.journal = journal;
    }

    public Long getTimetable() {
        return timetable;
    }

    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }

    public Long getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(Long lessonTime) {
        this.lessonTime = lessonTime;
    }

    public DayOfWeek getSelectedDay() {
        DayOfWeek day;
        switch (selectedDay.toLowerCase()) {
        case "daymon":
            day = DayOfWeek.MONDAY;
            break;
        case "daytue":
            day = DayOfWeek.TUESDAY;
            break;
        case "daywed":
            day = DayOfWeek.WEDNESDAY;
            break;
        case "daythu":
            day = DayOfWeek.THURSDAY;
            break;
        case "dayfri":
            day = DayOfWeek.FRIDAY;
            break;
        case "daysat":
            day = DayOfWeek.SATURDAY;
            break;
        case "daysun":
            day = DayOfWeek.SUNDAY;
            break;
        default:
            day = null;
            break;
        }
        return day;
    }
    
    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }
    
}
