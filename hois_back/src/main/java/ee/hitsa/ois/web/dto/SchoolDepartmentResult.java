package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class SchoolDepartmentResult extends AutocompleteResult {

    private Long schoolId;
    private String schoolCode;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean valid;

    public SchoolDepartmentResult(Long id, String nameEt, String nameEn, Long schoolId, String schoolCode) {
        super(id, nameEt, nameEn);

        this.schoolId = schoolId;
        this.schoolCode = schoolCode;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
    
}
