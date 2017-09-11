package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;

public class TimetableEventDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer lessonNr;
    private Long room;
    private Long journal;
    private Long studentGroup;
    private String capacityType;

    public TimetableEventDto(Long id, LocalDateTime start, LocalDateTime end, Integer lessonNr, String capacityType,
            Long room, Long journal, Long studentGroup) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.lessonNr = lessonNr;
        this.capacityType = capacityType;
        this.room = room;
        this.journal = journal;
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

    public Long getRoom() {
        return room;
    }

    public void setRoom(Long room) {
        this.room = room;
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

}
