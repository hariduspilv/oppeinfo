package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.EntityUtil;

public class StudentGroupStudentDto {

    private Long id;
    private String fullname;
    private String idcode;
    private AutocompleteResult<Long> curriculumVersion;
    private String studyForm;
    private String studyLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public AutocompleteResult<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public static StudentGroupStudentDto of(Student student) {
        StudentGroupStudentDto dto = EntityUtil.bindToDto(student, new StudentGroupStudentDto());
        Person p = student.getPerson();
        dto.setFullname(p.getFullname());
        dto.setIdcode(p.getIdcode());
        return dto;
    }
}
