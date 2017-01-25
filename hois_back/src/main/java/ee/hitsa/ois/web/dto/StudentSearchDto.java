package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Student;
import ee.hitsa.ois.domain.StudentGroup;

public class StudentSearchDto {

    private Long id;
    private String name;
    private String idcode;
    private String curriculum;
    private String group;
    private String studyForm;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
        StudentSearchDto dto = new StudentSearchDto();
        dto.setId(student.getId());
        dto.setName(student.getPerson().getFullname());
        dto.setIdcode(student.getPerson().getIdcode());
        dto.setCurriculum(student.getCurriculumVersion().getCode());
        StudentGroup sg = student.getStudentGroup();
        dto.setGroup(sg != null ? sg.getCode() : null);
        Classifier studyForm = student.getStudyForm();
        dto.setStudyForm(studyForm != null ? studyForm.getCode() : null);
        Classifier status = student.getStatus();
        dto.setStatus(status != null ? status.getCode() : null);
        return dto;
    }
}
