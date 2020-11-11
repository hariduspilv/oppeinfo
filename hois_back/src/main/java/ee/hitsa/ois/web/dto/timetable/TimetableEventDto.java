package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.RoomAutocompleteResult;

public class TimetableEventDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<RoomAutocompleteResult> rooms = new ArrayList<>();
    private List<Long> teachers = new ArrayList<>();
    private List<String> teacherNames = new ArrayList<>();
    private List<Long> students = new ArrayList<>();
    private List<String> studentNames = new ArrayList<>();
    private List<Long> subgroups = new ArrayList<>();
    private List<String> subgroupCodes = new ArrayList<>();
    private Long studentGroup;
    private String capacityType;
    private AutocompleteResult subject; // journal in vocational event

    // vocational
    private Integer lessonNr;
    private Long journal;

    // higher
    private Long subjectStudyPeriod;
    private String subjectCode;
    private List<Long> objectStudentGroups = new ArrayList<>();
    private String repeatCode;

    public TimetableEventDto(Long id, LocalDateTime start, LocalDateTime end, String capacityType,
            String subjectNameEt, String subjectNameEn, Long studentGroup) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.capacityType = capacityType;
        this.subject = new AutocompleteResult(null, subjectNameEt, subjectNameEn);
        this.studentGroup = studentGroup;
    }

    public TimetableEventDto(Long id, LocalDateTime start, LocalDateTime end, Integer lessonNr, String capacityType,
            Long journal, String journalNameEt, Long studentGroup) {
        this(id, start, end, capacityType, journalNameEt, null, studentGroup);
        this.lessonNr = lessonNr;
        this.journal = journal;
        this.studentGroup = studentGroup;
    }

    public TimetableEventDto(Long id, LocalDateTime start, LocalDateTime end, String capacityType,
            String subjectCode, String subjectNameEt,  String subjectNameEn, Long subjectStudyPeriod,
            Long studentGroup, String repeatCode) {
        this(id, start, end, capacityType, subjectNameEt, subjectNameEn, studentGroup);
        this.subjectCode = subjectCode;
        this.subjectStudyPeriod = subjectStudyPeriod;
        this.studentGroup = studentGroup;
        this.repeatCode = repeatCode;
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

    public List<RoomAutocompleteResult> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomAutocompleteResult> rooms) {
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
    
    public List<String> getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(List<String> teacherNames) {
        this.teacherNames = teacherNames;
    }

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }

    public List<Long> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(List<Long> subgroups) {
        this.subgroups = subgroups;
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

    public List<String> getSubgroupCodes() {
        return subgroupCodes;
    }

    public void setSubgroupCodes(List<String> subgroupCodes) {
        this.subgroupCodes = subgroupCodes;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }


    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public List<Long> getObjectStudentGroups() {
        return objectStudentGroups;
    }

    public void setObjectStudentGroups(List<Long> objectStudentGroups) {
        this.objectStudentGroups = objectStudentGroups;
    }

    public String getRepeatCode() {
        return repeatCode;
    }

    public void setRepeatCode(String repeatCode) {
        this.repeatCode = repeatCode;
    }

}
