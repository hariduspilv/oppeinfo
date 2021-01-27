package ee.AD.demo;

import java.util.List;

public class TimetableEventDto {

    private String nameEt;
    private String eventStart;
    private String eventEnd;
    private Long journalId;
    private Long subjectStudyPeriodId;
    private List<TimetableEventTeacherDto> teachers;
    private List<TimetableEventRoomDto> rooms;
    
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public Long getJournalId() {
        return journalId;
    }
    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }
    public Long getSubjectStudyPeriodId() {
        return subjectStudyPeriodId;
    }
    public void setSubjectStudyPeriodId(Long subjectStudyPeriodId) {
        this.subjectStudyPeriodId = subjectStudyPeriodId;
    }
    public List<TimetableEventTeacherDto> getTeachers() {
        return teachers;
    }
    public void setTeachers(List<TimetableEventTeacherDto> teachers) {
        this.teachers = teachers;
    }
    public List<TimetableEventRoomDto> getRooms() {
        return rooms;
    }
    public void setRooms(List<TimetableEventRoomDto> rooms) {
        this.rooms = rooms;
    }
    public String getEventStart() {
        return eventStart;
    }
    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }
    public String getEventEnd() {
        return eventEnd;
    }
    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }
}
