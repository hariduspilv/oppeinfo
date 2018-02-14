package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TimetableEventSearchDto {

    private Long id;
    private String nameEt;
    private String nameEn;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private List<TimetableEventSearchTeacherDto> teachers;
    private List<TimetableEventSearchRoomDto> rooms;
    private List<TimetableEventSearchGroupDto> studentGroups;
    private Boolean considerBreak;
    private Boolean singleEvent;
    private Boolean publicEvent;
    private Long timetableId;

    public TimetableEventSearchDto(Long id, String nameEt, String nameEn, LocalDate date, LocalTime timeStart,
            LocalTime timeEnd, Boolean considerBreak, Boolean singleEvent, Long timetableId) {
        this.id = id;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.considerBreak = considerBreak;
        this.singleEvent = singleEvent;
        this.publicEvent = Boolean.TRUE;
        this.timetableId = timetableId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }
    
    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public List<TimetableEventSearchTeacherDto> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TimetableEventSearchTeacherDto> teachers) {
        this.teachers = teachers;
    }

    public List<TimetableEventSearchRoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<TimetableEventSearchRoomDto> rooms) {
        this.rooms = rooms;
    }

    public List<TimetableEventSearchGroupDto> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<TimetableEventSearchGroupDto> studentGroups) {
        this.studentGroups = studentGroups;
    }
    
    public Boolean getConsiderBreak() {
        return considerBreak;
    }

    public void setConsiderBreak(Boolean considerBreak) {
        this.considerBreak = considerBreak;
    }

    public Boolean getSingleEvent() {
        return singleEvent;
    }

    public void setSingleEvent(Boolean singleEvent) {
        this.singleEvent = singleEvent;
    }

    public Boolean getPublicEvent() {
        return publicEvent;
    }

    public void setPublicEvent(Boolean publicEvent) {
        this.publicEvent = publicEvent;
    }

    public Long getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(Long timetableId) {
        this.timetableId = timetableId;
    }
    
}
