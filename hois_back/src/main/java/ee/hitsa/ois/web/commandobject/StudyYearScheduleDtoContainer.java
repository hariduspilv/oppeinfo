package ee.hitsa.ois.web.commandobject;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import ee.hitsa.ois.web.dto.StudyYearScheduleDto;

public class StudyYearScheduleDtoContainer {

    private Set<Long> deletedStudyYearSchedules;
    /** Set is done in order to avoid duplicates */
    @Valid
    private Set<StudyYearScheduleDto> studyYearSchedules;

    private Set<Long> studentGroups;
    private Set<Long> studyPeriods;
    @Size(max = 4000)
    private String addInfo;
    private Boolean showMine;

    public Set<Long> getDeletedStudyYearSchedules() {
        return deletedStudyYearSchedules;
    }

    public void setDeletedStudyYearSchedules(Set<Long> deletedStudyYearSchedules) {
        this.deletedStudyYearSchedules = deletedStudyYearSchedules;
    }

    public Set<StudyYearScheduleDto> getStudyYearSchedules() {
        return studyYearSchedules != null ? studyYearSchedules : (studyYearSchedules = new HashSet<>());
    }

    public void setStudyYearSchedules(Set<StudyYearScheduleDto> studyYearSchedules) {
        this.studyYearSchedules = studyYearSchedules;
    }

    public Set<Long> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Set<Long> getStudyPeriods() {
        return studyPeriods;
    }

    public void setStudyPeriods(Set<Long> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Boolean getShowMine() {
        return showMine;
    }

    public void setShowMine(Boolean showMine) {
        this.showMine = showMine;
    }

}
