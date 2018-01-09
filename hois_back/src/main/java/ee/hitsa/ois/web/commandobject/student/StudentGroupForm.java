package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@DateRange
public class StudentGroupForm extends VersionedCommand {

    @Required
    @Size(max = 50)
    private String code;
    @NotNull
    @Min(1)
    private Short course;
    @NotNull
    private AutocompleteResult curriculum;
    private Long curriculumVersion;
    @Required
    @ClassifierRestriction(MainClassCode.OPPEVORM)
    private String studyForm;
    @Required
    @ClassifierRestriction(MainClassCode.OPPEKEEL)
    private String language;
    private AutocompleteResult teacher;
    @ClassifierRestriction(MainClassCode.SPETSKUTSE)
    private String speciality;
    @Min(1)
    private Integer places;
    private LocalDate validFrom;
    private LocalDate validThru;
    private List<Long> students;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Short getCourse() {
        return course;
    }

    public void setCourse(Short course) {
        this.course = course;
    }

    public AutocompleteResult getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
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

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
    }
}
