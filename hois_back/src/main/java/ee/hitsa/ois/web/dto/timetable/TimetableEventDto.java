package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;
import java.util.List;

import ee.hitsa.ois.web.dto.RoomDto;

public class TimetableEventDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer lessonNr;
    private List<RoomDto> rooms;
    private Long journal;
    private Long studentGroup;
    private String capacityType;
    private String subjectCode;

    public TimetableEventDto(Long id, LocalDateTime start, LocalDateTime end, Integer lessonNr, String capacityType,
            Long journal, Long studentGroup) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.lessonNr = lessonNr;
        this.capacityType = capacityType;
        this.journal = journal;
        this.studentGroup = studentGroup;
    }

    public TimetableEventDto(Long id, LocalDateTime start, LocalDateTime end, Integer lessonNr, String capacityType,
            String subjectCode, Long studentGroup) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.lessonNr = lessonNr;
        this.capacityType = capacityType;
        this.subjectCode = subjectCode;
        this.studentGroup = studentGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Integer getLessonNr() {
        return lessonNr;
    }

    public void setLessonNr(Integer lessonNr) {
        this.lessonNr = lessonNr;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public Long getJournal() {
        return journal;
    }

    public void setJournal(Long journal) {
        this.journal = journal;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

}
