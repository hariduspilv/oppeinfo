package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonTimeSearchDto {

    private Long id;
    private Integer lessonNr;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean dayMon;
    private Boolean dayTue;
    private Boolean dayWed;
    private Boolean dayThu;
    private Boolean dayFri;
    private Boolean daySat;
    private Boolean daySun;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Set<AutocompleteResult> buildings;


    public static LessonTimeSearchDto of(LessonTime lessonTime) {
        LessonTimeSearchDto dto = EntityUtil.bindToDto(lessonTime, new LessonTimeSearchDto());
        dto.setValidFrom(lessonTime.getLessonTimeBuildingGroup().getValidFrom());
        dto.setValidThru(lessonTime.getLessonTimeBuildingGroup().getValidThru());
        dto.setBuildings(lessonTime.getLessonTimeBuildingGroup().getBuildings().stream().map(AutocompleteResult::of).collect(Collectors.toSet()));
        return dto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLessonNr() {
        return lessonNr;
    }

    public void setLessonNr(Integer lessonNr) {
        this.lessonNr = lessonNr;
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

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public Set<AutocompleteResult> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<AutocompleteResult> buildings) {
        this.buildings = buildings;
    }


}
