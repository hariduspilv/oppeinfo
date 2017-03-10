package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.util.EntityUtil;

public class StudentApplicationDto {

    private Long id;
    private String type;
    private LocalDate inserted;
    private String status;
    private LocalDate confirmDate;

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

    public LocalDate getInserted() {
        return inserted;
    }

    public void setInserted(LocalDate inserted) {
        this.inserted = inserted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public static StudentApplicationDto of(Application application) {
        return EntityUtil.bindToDto(application, new StudentApplicationDto());
    }
}
