package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudyPeriodWithYearIdDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;

public class TimetableDto {
    private Long id;
    private List<StudyYearSearchDto> studyYears;
    private List<StudyPeriodWithYearIdDto> studyPeriods;
    private Long currentStudyPeriod;
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TimetableCurriculumDto> curriculums;
    private List<AutocompleteResult> teachers;
    private List<SubjectTeacherPairDto> pairs;
    private String status;
    private List<String> roomCodes;
    private Boolean higher;
    private Boolean editable = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<StudyYearSearchDto> getStudyYears() {
        return studyYears;
    }

    public void setStudyYears(List<StudyYearSearchDto> studyYears) {
        this.studyYears = studyYears;
    }

    public List<StudyPeriodWithYearIdDto> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(List<StudyPeriodWithYearIdDto> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public Long getCurrentStudyPeriod() {
        return currentStudyPeriod;
    }

    public void setCurrentStudyPeriod(Long currentStudyPeriod) {
        this.currentStudyPeriod = currentStudyPeriod;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<TimetableCurriculumDto> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(List<TimetableCurriculumDto> curriculums) {
        this.curriculums = curriculums;
    }

    public List<AutocompleteResult> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<AutocompleteResult> teachers) {
        this.teachers = teachers;
    }

    public List<SubjectTeacherPairDto> getPairs() {
        return pairs;
    }

    public void setPairs(List<SubjectTeacherPairDto> pairs) {
        this.pairs = pairs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getRoomCodes() {
        return roomCodes;
    }

    public void setRoomCodes(List<String> roomCodes) {
        this.roomCodes = roomCodes;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

}
