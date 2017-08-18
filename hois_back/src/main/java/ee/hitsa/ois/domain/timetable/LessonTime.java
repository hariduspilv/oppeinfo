package ee.hitsa.ois.domain.timetable;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.school.School;

@Entity
public class LessonTime extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    private LocalTime startTime;
    private LocalTime endTime;
    private Short lessonNr;
    private Boolean dayMon;
    private Boolean dayTue;
    private Boolean dayWed;
    private Boolean dayThu;
    private Boolean dayFri;
    private Boolean daySat;
    private Boolean daySun;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private LessonTimeBuildingGroup lessonTimeBuildingGroup;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Short getLessonNr() {
        return lessonNr;
    }

    public void setLessonNr(Short lessonNr) {
        this.lessonNr = lessonNr;
    }

    public Boolean getDayMon() {
        return dayMon;
    }

    public void setDayMon(Boolean dayMon) {
        this.dayMon = dayMon;
    }

    public Boolean getDayTue() {
        return dayTue;
    }

    public void setDayTue(Boolean dayTue) {
        this.dayTue = dayTue;
    }

    public Boolean getDayWed() {
        return dayWed;
    }

    public void setDayWed(Boolean dayWed) {
        this.dayWed = dayWed;
    }

    public Boolean getDayThu() {
        return dayThu;
    }

    public void setDayThu(Boolean dayThu) {
        this.dayThu = dayThu;
    }

    public Boolean getDayFri() {
        return dayFri;
    }

    public void setDayFri(Boolean dayFri) {
        this.dayFri = dayFri;
    }

    public Boolean getDaySat() {
        return daySat;
    }

    public void setDaySat(Boolean daySat) {
        this.daySat = daySat;
    }

    public Boolean getDaySun() {
        return daySun;
    }

    public void setDaySun(Boolean daySun) {
        this.daySun = daySun;
    }

    public LessonTimeBuildingGroup getLessonTimeBuildingGroup() {
        return lessonTimeBuildingGroup;
    }

    public void setLessonTimeBuildingGroup(LessonTimeBuildingGroup lessonTimeBuildingGroup) {
        this.lessonTimeBuildingGroup = lessonTimeBuildingGroup;
    }

}
