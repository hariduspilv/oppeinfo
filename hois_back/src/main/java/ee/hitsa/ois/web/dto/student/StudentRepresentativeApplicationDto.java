package ee.hitsa.ois.web.dto.student;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.Student;
import ee.hitsa.ois.domain.StudentRepresentativeApplication;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudentRepresentativeApplicationForm;

public class StudentRepresentativeApplicationDto extends StudentRepresentativeApplicationForm {

    private Long id;
    private Long studentId;
    private String studentFullname;
    private String fullname;
    private String idcode;
    private LocalDateTime inserted;
    private String status;
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentFullname() {
        return studentFullname;
    }

    public void setStudentFullname(String studentFullname) {
        this.studentFullname = studentFullname;
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

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public static StudentRepresentativeApplicationDto of(StudentRepresentativeApplication application) {
        StudentRepresentativeApplicationDto dto = EntityUtil.bindToDto(application.getPerson(), new StudentRepresentativeApplicationDto(), "id", "inserted", "version");
        EntityUtil.bindToDto(application, dto);
        Student student = application.getStudent();
        dto.setStudentId(student.getId());
        dto.setStudentFullname(student.getPerson().getFullname());
        dto.setStudentIdcode(student.getPerson().getIdcode());
        return dto;
    }
}
