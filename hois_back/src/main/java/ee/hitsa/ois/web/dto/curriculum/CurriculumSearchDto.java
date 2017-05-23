package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumSearchDto {

    private Long id;
    private String nameEt;
    private String nameEn;
    private BigDecimal credits;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean higher;
    private String status;
    private String origStudyLevel;
    private AutocompleteResult school;
    private List<Long> departments;

    public CurriculumSearchDto() {
    }

    public CurriculumSearchDto(Long id, String nameEt, String nameEn, BigDecimal credits, LocalDate validFrom,
            LocalDate validThru, Boolean higher, String status, String origStudyLevel,
            Long schoolId, String schoolNameEt, String schoolNameEn) {
        this.id = id;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.credits = credits;
        this.validFrom = validFrom;
        this.validThru = validThru;
        this.higher = higher;
        this.status = status;
        this.origStudyLevel = origStudyLevel;
        this.school = new AutocompleteResult(schoolId, schoolNameEt, schoolNameEn);
    }

    public List<Long> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Long> departments) {
        this.departments = departments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
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

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrigStudyLevel() {
        return origStudyLevel;
    }

    public void setOrigStudyLevel(String origStudyLevel) {
        this.origStudyLevel = origStudyLevel;
    }

    public AutocompleteResult getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult school) {
        this.school = school;
    }
}
