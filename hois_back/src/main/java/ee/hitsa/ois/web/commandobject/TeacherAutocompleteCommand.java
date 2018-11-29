package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TeacherAutocompleteCommand extends SearchCommand {

    private Boolean valid;
    private Boolean higher;
    
    private Boolean occupied;
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String repeatCode;
    private Long weekAmount;
    private Long timetable;
    private Long selectedTeacherId;

    public Long getTimetable() {
        return timetable;
    }

    public void setTimetable(Long timetable) {
        this.timetable = timetable;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }
    
    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Long getWeekAmount() {
        return weekAmount;
    }

    public void setWeekAmount(Long weekAmount) {
        this.weekAmount = weekAmount;
    }

    public Long getSelectedTeacherId() {
        return selectedTeacherId;
    }

    public void setSelectedTeacherId(Long selectedTeacherId) {
        this.selectedTeacherId = selectedTeacherId;
    }

}
