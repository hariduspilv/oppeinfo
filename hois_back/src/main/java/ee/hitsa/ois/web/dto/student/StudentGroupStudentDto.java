package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentGroupStudentDto {

    private Long id;
    private String fullname;
    private String idcode;
    private AutocompleteResult curriculumVersion;

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

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public static StudentGroupStudentDto of(Student student) {
        StudentGroupStudentDto dto = EntityUtil.bindToDto(student, new StudentGroupStudentDto());
        Person p = student.getPerson();
        dto.setFullname(p.getFullname());
        dto.setIdcode(p.getIdcode());
        return dto;
    }
}
