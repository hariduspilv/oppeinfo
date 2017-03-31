package ee.hitsa.ois.web.dto.student;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.util.EntityUtil;

public class StudentApplicationDto {

    private Long id;
    private String type;
    private LocalDateTime inserted;
    private String status;
    private LocalDateTime confirmDate;
    private LocalDateTime submitted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public LocalDateTime getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDateTime confirmDate) {
        this.confirmDate = confirmDate;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public static StudentApplicationDto of(Application application) {
        StudentApplicationDto dto = EntityUtil.bindToDto(application, new StudentApplicationDto());
        if (EntityUtil.getCode(application.getStatus()).equals(ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name())) {
            dto.setConfirmDate(application.getChanged());
        }
        return dto;
    }
}
