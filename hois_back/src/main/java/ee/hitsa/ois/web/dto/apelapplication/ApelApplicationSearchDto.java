package ee.hitsa.ois.web.dto.apelapplication;

import java.time.LocalDate;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ApelApplicationSearchDto {

    private Long id;
    private AutocompleteResult student;
    private AutocompleteResult curriculum;
    private String status;
    private LocalDate inserted;
    private LocalDate confirmed;
    
    public static ApelApplicationSearchDto of(Application application) {
        ApelApplicationSearchDto dto = EntityUtil.bindToDto(application, new ApelApplicationSearchDto());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public AutocompleteResult getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getInserted() {
        return inserted;
    }

    public void setInserted(LocalDate inserted) {
        this.inserted = inserted;
    }

    public LocalDate getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(LocalDate confirmed) {
        this.confirmed = confirmed;
    }
    
}
