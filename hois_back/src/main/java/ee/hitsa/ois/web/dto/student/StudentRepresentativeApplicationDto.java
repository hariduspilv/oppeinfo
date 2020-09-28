package ee.hitsa.ois.web.dto.student;

import static ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus.AVALDUS_ESINDAJA_STAATUS_K;
import static ee.hitsa.ois.enums.StudentRepresentativeApplicationStatus.AVALDUS_ESINDAJA_STAATUS_T;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentativeApplication;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeApplicationForm;

public class StudentRepresentativeApplicationDto extends StudentRepresentativeApplicationForm {

    private Long id;
    private Long studentId;
    private String studentFullname;
    private String fullname;
    private String idcode;
    private LocalDateTime inserted;
    private LocalDateTime confirmDate;
    private LocalDateTime declineDate;
    private String status;
    private String rejectReason;
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

    public LocalDateTime getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDateTime confirmDate) {
        this.confirmDate = confirmDate;
    }

    public LocalDateTime getDeclineDate() {
        return declineDate;
    }

    public void setDeclineDate(LocalDateTime declineDate) {
        this.declineDate = declineDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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
        dto.setStudentFullname(PersonUtil.fullnameTypeSpecific(student.getPerson().getFullname(), EntityUtil.getNullableCode(student.getType())));
        dto.setStudentIdcode(student.getPerson().getIdcode());
        if(AVALDUS_ESINDAJA_STAATUS_K.name().equals(dto.getStatus())) {
            dto.setConfirmDate(application.getChanged());
        } else if(AVALDUS_ESINDAJA_STAATUS_T.name().equals(dto.getStatus())) {
            dto.setDeclineDate(application.getChanged());
        }
        return dto;
    }
}
