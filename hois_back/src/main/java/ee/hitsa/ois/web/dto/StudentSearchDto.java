package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.Student;
import ee.hitsa.ois.domain.StudentGroup;
import ee.hitsa.ois.util.EntityUtil;

public class StudentSearchDto {

    private Long id;
    private String fullname;
    private String idcode;
    private AutocompleteResult<Long> curriculum;
    private String studentGroup;
    private String studyForm;
    private String status;

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

    public AutocompleteResult<Long>  getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult<Long> curriculum) {
        this.curriculum = curriculum;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static StudentSearchDto of(Student student) {
        StudentSearchDto dto = EntityUtil.bindToDto(student, new StudentSearchDto());
        Person p = student.getPerson();
        dto.setFullname(p.getFullname());
        dto.setIdcode(p.getIdcode());
        dto.setCurriculum(AutocompleteResult.of(student.getCurriculumVersion()));
        StudentGroup sg = student.getStudentGroup();
        dto.setStudentGroup(sg != null ? sg.getCode() : null);
        return dto;
    }
}
