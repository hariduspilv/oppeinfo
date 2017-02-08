package ee.hitsa.ois.web.dto.curriculum;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumSearchDto {

    private Long id;
    private String nameEt;
    private String nameEn;
    private Integer credits;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean higher;
    private String status;
    private String origStudyLevel;
    private AutocompleteResult<Long> school;

    public CurriculumSearchDto() {
    }

    public CurriculumSearchDto(Long id, String nameEt, String nameEn, Double credits, LocalDate validFrom,
            LocalDate validThru, Boolean higher, String status, String origStudyLevel,
            Long schoolId, String schoolNameEt, String schoolNameEn) {
        this.id = id;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        // FIXME why integer?
        this.credits = credits != null ? Integer.valueOf(credits.intValue()) : null;
        this.validFrom = validFrom;
        this.validThru = validThru;
        this.higher = higher;
        this.status = status;
        this.origStudyLevel = origStudyLevel;
        this.school = new AutocompleteResult<>(schoolId, schoolNameEt, schoolNameEn);
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

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
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

    public AutocompleteResult<Long> getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult<Long> school) {
        this.school = school;
    }
}
