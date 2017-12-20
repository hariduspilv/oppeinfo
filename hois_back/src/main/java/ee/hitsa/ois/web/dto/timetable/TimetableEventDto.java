package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TimetableEventDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<AutocompleteResult> rooms = new ArrayList<>();
    private List<Long> teachers = new ArrayList<>();
    private Long studentGroup;
    private String capacityType;

    // vocational
    private Integer lessonNr;
    private Long journal;

    // higher
    private Long subjectStudyPeriod;
    private String subjectCode;
    private String subjectName;

    // TODO: make different classes with different cosntructors
    public TimetableEventDto(Long id, LocalDateTime start, LocalDateTime end, Integer lessonNr, String capacityType,
            Long journal, Long studentGroup, String subjectName) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.lessonNr = lessonNr;
        this.capacityType = capacityType;
        this.journal = journal;
        this.studentGroup = studentGroup;
        this.subjectName = subjectName;
    }

    public TimetableEventDto(Long id, LocalDateTime start, LocalDateTime end, String capacityType, String subjectCode,
            Long studentGroup, String subjectName, Long subjectStudyPeriod) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.capacityType = capacityType;
        this.subjectCode = subjectCode;
        this.studentGroup = studentGroup;
        this.subjectName = subjectName;
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public TimetableEventDto(Long id, LocalDateTime start, LocalDateTime end, String capacityType, String subjectCode,
            String subjectName, Long subjectStudyPeriod) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.capacityType = capacityType;
        this.subjectCode = subjectCode;
        this.subjectStudyPeriod = subjectStudyPeriod;
        this.subjectName = subjectName;
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

    public List<AutocompleteResult> getRooms() {
        return rooms;
    }

    public void setRooms(List<AutocompleteResult> rooms) {
        if(rooms != null) {
            this.rooms = rooms;
        }
    }

    public List<Long> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Long> teachers) {
        if(teachers != null) {
            this.teachers = teachers;
        }
    }

    public Long getJournal() {
        return journal;
    }

    public void setJournal(Long journal) {
        this.journal = journal;
    }

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}