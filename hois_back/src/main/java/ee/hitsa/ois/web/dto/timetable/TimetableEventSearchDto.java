package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TimetableEventSearchDto implements Translatable {

    private Long id;
    private Long journalId;
    private Long subjectStudyPeriodId;
    private String nameEt;
    private String nameEn;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private List<TimetableEventSearchTeacherDto> teachers;
    private List<TimetableEventSearchRoomDto> rooms;
    private List<TimetableEventSearchGroupDto> studentGroups;
    private List<TimetableEventSearchSubgroupDto> subgroups;
    private List<TimetableEventSearchStudentDto> students;
    private String addInfo;
    private Boolean singleEvent;
    private Boolean publicEvent;
    private Long timetableId;
    private Boolean showStudyMaterials;
    private String capacityType;
    private Boolean isPersonal;
    private AutocompleteResult person;
    private Boolean isJuhanEvent;
    private Boolean isExam;
    private Boolean isOngoing;
    private Boolean includesEventStudents;
    private Long insertedTeacherId;
    private LocalDateTime changed;

    public TimetableEventSearchDto(Long id, Long journalId, Long subjectStudyPeriodId, String nameEt, String nameEn,
            LocalDateTime start, LocalDateTime end, Boolean singleEvent, Long timetableId, String capacityType,
            Long juhanEventId, Boolean isPublicEvent) {
        this.id = id;
        this.journalId = journalId;
        this.subjectStudyPeriodId = subjectStudyPeriodId;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.date = start.toLocalDate();
        this.timeStart = start.toLocalTime();
        this.timeEnd = end.toLocalTime();
        this.singleEvent = singleEvent;
        this.timetableId = timetableId;
        this.capacityType = capacityType;
        this.isJuhanEvent = Boolean.valueOf(juhanEventId != null);
        this.publicEvent = Boolean.TRUE.equals(isPublicEvent);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<TimetableEventSearchSubgroupDto> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(List<TimetableEventSearchSubgroupDto> subgroups) {
        this.subgroups = subgroups;
    }

    public List<TimetableEventSearchStudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<TimetableEventSearchStudentDto> students) {
        this.students = students;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
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

    public Boolean getShowStudyMaterials() {
        return showStudyMaterials;
    }

    public void setShowStudyMaterials(Boolean showStudyMaterials) {
        this.showStudyMaterials = showStudyMaterials;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    public Boolean getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(Boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

    public AutocompleteResult getPerson() {
        return person;
    }

    public void setPerson(AutocompleteResult person) {
        this.person = person;
    }

    public Boolean getIsJuhanEvent() {
        return isJuhanEvent;
    }

    public void setIsJuhanEvent(Boolean isJuhanEvent) {
        this.isJuhanEvent = isJuhanEvent;
    }

    public Boolean getIsExam() {
        return isExam;
    }

    public void setIsExam(Boolean isExam) {
        this.isExam = isExam;
    }

    public Boolean getIsOngoing() {
        return isOngoing;
    }

    public void setIsOngoing(Boolean isOngoing) {
        this.isOngoing = isOngoing;
    }

    public Boolean getIncludesEventStudents() {
        return includesEventStudents;
    }

    public void setIncludesEventStudents(Boolean includesEventStudents) {
        this.includesEventStudents = includesEventStudents;
    }

    public Long getInsertedTeacherId() {
        return insertedTeacherId;
    }

    public void setInsertedTeacherId(Long insertedTeacherId) {
        this.insertedTeacherId = insertedTeacherId;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }
}
