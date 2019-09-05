package ee.hitsa.ois.web.dto.poll;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ResponseDto {
    
    private Long id;
    private String status;
    private AutocompleteResult name;
    private LocalDate validFrom;
    private LocalDate validThru;
    private String type;
    private AutocompleteResult student;
    private Boolean isThemePageable;
    
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
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
    public AutocompleteResult getName() {
        return name;
    }
    public void setName(AutocompleteResult name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Boolean getIsThemePageable() {
        return isThemePageable;
    }
    public void setIsThemePageable(Boolean isThemePageable) {
        this.isThemePageable = isThemePageable;
    }
}
