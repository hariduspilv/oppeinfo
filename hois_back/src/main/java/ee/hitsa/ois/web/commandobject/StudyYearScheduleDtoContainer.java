package ee.hitsa.ois.web.commandobject;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import ee.hitsa.ois.web.dto.StudyYearScheduleDto;

public class StudyYearScheduleDtoContainer {
    
    private Set<Long> deletedStudyYearSchedules;
    /**Set is done in order to avoid duplicates*/
    @Valid
    private Set<StudyYearScheduleDto> studyYearSchedules;
    
    private Set<Long> studentGroups;
    private Set<Long> studyPeriods;

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
}
