package ee.hitsa.ois.web.commandobject.timetable;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.service.TimetableService.TimetablePersonHolder;
import ee.hitsa.ois.web.commandobject.SearchCommand;

public class TimetableEventSearchCommand extends SearchCommand {

    private Boolean singleEvent;
    private List<Long> studentGroups;
    private Long studyPeriod;
    private List<Long> teachers;
    private Long room;
    private LocalDate from;
    private LocalDate thru;
    private String otherTeacher;
    private String otherRoom;
    private Long student;
    private String studentName;
    private Boolean higher;
    private Boolean vocational;
    private Long journalId;
    private Long subjectId;
    private Boolean showOnlySubstitutes;
    private Boolean personalEvent;
    private Boolean juhanEvent;
    private Long user;
    private TimetablePersonHolder person; // for personal timetables accessed by encoded url
    private Boolean schoolBoard;
    private Boolean hideExams;

    public Boolean getSingleEvent() {
        return singleEvent;
    }

    public void setSingleEvent(Boolean singleEvent) {
        this.singleEvent = singleEvent;
    }

    public List<Long> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public List<Long> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Long> teachers) {
        this.teachers = teachers;
    }

    public Long getRoom() {
        return room;
    }

    public void setRoom(Long room) {
        this.room = room;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

    public String getOtherTeacher() {
        return otherTeacher;
    }

    public void setOtherTeacher(String otherTeacher) {
        this.otherTeacher = otherTeacher;
    }

    public String getOtherRoom() {
        return otherRoom;
    }

    public void setOtherRoom(String otherRoom) {
        this.otherRoom = otherRoom;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getVocational() {
        return vocational;
    }

    public void setVocational(Boolean vocational) {
        this.vocational = vocational;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Boolean getShowOnlySubstitutes() {
        return showOnlySubstitutes;
    }

    public void setShowOnlySubstitutes(Boolean showOnlySubstitutes) {
        this.showOnlySubstitutes = showOnlySubstitutes;
    }

    public Boolean getPersonalEvent() {
        return personalEvent;
    }

    public void setPersonalEvent(Boolean personalEvent) {
        this.personalEvent = personalEvent;
    }

    public Boolean getJuhanEvent() {
        return juhanEvent;
    }

    public void setJuhanEvent(Boolean juhanEvent) {
        this.juhanEvent = juhanEvent;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public TimetablePersonHolder getPerson() {
        return person;
    }

    public void setPerson(TimetablePersonHolder person) {
        this.person = person;
    }

    public Boolean getSchoolBoard() {
        return schoolBoard;
    }

    public void setSchoolBoard(Boolean schoolBoard) {
        this.schoolBoard = schoolBoard;
    }

    public Boolean getHideExams() {
        return hideExams;
    }

    public void setHideExams(Boolean hideExams) {
        this.hideExams = hideExams;
    }
}
