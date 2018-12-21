package ee.hitsa.ois.web.dto.scholarship;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.util.EntityUtil;

public class ScholarshipStudentRejectionDto {
    private Long id;
    private String fullName;
    private String idCode;
    private String studentGroup;
    private String rejectComment;
    
    public static ScholarshipStudentRejectionDto of(ScholarshipApplication application) {
        ScholarshipStudentRejectionDto dto = new ScholarshipStudentRejectionDto();
        dto.setId(EntityUtil.getId(application));
        Student student = application.getStudent();
        dto.setFullName(student.getPerson().getFullname());
        dto.setIdCode(student.getPerson().getIdcode());
        StudentGroup studentGroup = student.getStudentGroup() != null ? student.getStudentGroup() : null;
        dto.setStudentGroup(studentGroup != null ? studentGroup.getCode() : null);
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

}
