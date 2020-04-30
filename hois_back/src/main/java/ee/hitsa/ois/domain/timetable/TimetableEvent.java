package ee.hitsa.ois.domain.timetable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;

@Entity
public class TimetableEvent extends BaseEntityWithId {

    private LocalDateTime start;
    // FIXME: rename in database
    @Column(name = "\"end\"")
    private LocalDateTime end;
    private Long lessons;
    private Boolean considerBreak;
    private String name;
    private Boolean isImported;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier repeat;
    private Short lessonNr;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier capacityType;
    @ManyToOne(fetch = FetchType.LAZY)
    private SubjectStudyPeriodStudentGroup subjectStudyPeriodStudentGroup;
    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private TimetableObject timetableObject;

    private Boolean isPersonal;
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    private Long juhanEventId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "timetable_event_id", nullable = false, updatable = false)
    private List<TimetableEventTime> timetableEventTimes = new ArrayList<>();

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

    public Long getLessons() {
        return lessons;
    }

    public void setLessons(Long lessons) {
        this.lessons = lessons;
    }

    public Boolean getConsiderBreak() {
        return considerBreak;
    }

    public void setConsiderBreak(Boolean considerBreak) {
        this.considerBreak = considerBreak;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Classifier getRepeatCode() {
        return repeat;
    }

    public void setRepeatCode(Classifier repeat) {
        this.repeat = repeat;
    }

    public Short getLessonNr() {
        return lessonNr;
    }

    public void setLessonNr(Short lessonNr) {
        this.lessonNr = lessonNr;
    }

    public Classifier getRepeat() {
        return repeat;
    }

    public void setRepeat(Classifier repeat) {
        this.repeat = repeat;
    }

    public Classifier getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(Classifier capacityType) {
        this.capacityType = capacityType;
    }

    public SubjectStudyPeriodStudentGroup getSubjectStudyPeriodStudentGroup() {
        return subjectStudyPeriodStudentGroup;
    }

    public void setSubjectStudyPeriodStudentGroup(SubjectStudyPeriodStudentGroup subjectStudyPeriodStudentGroup) {
        this.subjectStudyPeriodStudentGroup = subjectStudyPeriodStudentGroup;
    }

    public TimetableObject getTimetableObject() {
        return timetableObject;
    }

    public void setTimetableObject(TimetableObject timetableObject) {
        this.timetableObject = timetableObject;
    }

    public Boolean getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(Boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getJuhanEventId() {
        return juhanEventId;
    }

    public void setJuhanEventId(Long juhanEventId) {
        this.juhanEventId = juhanEventId;
    }

    public List<TimetableEventTime> getTimetableEventTimes() {
        return timetableEventTimes;
    }

    public void setTimetableEventTimes(List<TimetableEventTime> timetableEventTimes) {
        this.timetableEventTimes = timetableEventTimes;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

	public Boolean getIsImported() {
		return isImported;
	}

	public void setIsImported(Boolean isImported) {
		this.isImported = isImported;
	}

}
