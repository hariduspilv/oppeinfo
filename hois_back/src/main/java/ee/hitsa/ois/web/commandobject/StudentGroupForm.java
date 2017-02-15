package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;

public class StudentGroupForm extends VersionedCommand {

    @NotEmpty
    @Size(max = 50)
    private String code;
    @NotNull
    @Min(1)
    private Integer course;
    private Long curriculum;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.OPPEVORM)
    private String studyForm;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.OPPEKEEL)
    private String language;
    @ClassifierRestriction(MainClassCode.SPETSKUTSE)
    private String speciality;
    private List<Long> students;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
    }
}
