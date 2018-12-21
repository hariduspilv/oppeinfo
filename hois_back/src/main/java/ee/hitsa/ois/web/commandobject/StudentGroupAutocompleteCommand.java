package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class StudentGroupAutocompleteCommand extends SearchCommand {

    private Long curriculumId;
    private Long curriculumVersionId;
    private Set<Long> curriculumVersionIds;
    private Boolean valid;
    private Boolean higher;
    private Long studentGroupTeacherId;
    private Long studyYear;
    
    private Boolean occupied;
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String repeatCode;
    private Long weekAmount;

    
    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
    }

    public Long getCurriculumVersionId() {
        return curriculumVersionId;
    }

    public void setCurriculumVersionId(Long curriculumVersionId) {
        this.curriculumVersionId = curriculumVersionId;
    }

    public Set<Long> getCurriculumVersionIds() {
        return curriculumVersionIds;
    }

    public void setCurriculumVersionIds(Set<Long> curriculumVersionIds) {
        this.curriculumVersionIds = curriculumVersionIds;
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

    public Long getStudentGroupTeacherId() {
        return studentGroupTeacherId;
    }

    public void setStudentGroupTeacherId(Long studentGroupTeacherId) {
        this.studentGroupTeacherId = studentGroupTeacherId;
    }

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
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
    
}
