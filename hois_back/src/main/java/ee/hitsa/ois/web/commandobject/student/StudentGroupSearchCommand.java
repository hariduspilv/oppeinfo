package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

@DateRange
public class StudentGroupSearchCommand {

    private String code;
    private EntityConnectionCommand curriculum;
    private List<Long> curriculums;
    private List<Long> curriculumVersion;
    private List<String> studyForm;
    private EntityConnectionCommand teacher;
    private List<Long> teachers;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean isValid;

    public List<Long> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Long> teachers) {
        this.teachers = teachers;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EntityConnectionCommand getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(EntityConnectionCommand curriculum) {
        this.curriculum = curriculum;
    }

    public void setCurriculums(List<Long> curriculums) {
        this.curriculums = curriculums;
    }

    public void setCurriculumVersion(List<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public List<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public List<Long> getCurriculums() {
        return curriculums;
    }

    public List<String> getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(List<String> studyForm) {
        this.studyForm = studyForm;
    }

    public EntityConnectionCommand getTeacher() {
        return teacher;
    }

    public void setTeacher(EntityConnectionCommand teacher) {
        this.teacher = teacher;
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

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
    
}
