package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class StateCurriculumSearchDto {
    private Long id;
    private String nameEt;
    private String nameEn;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Long credits;
    private String status;
    private String iscedClass;
    private String ekrLevel;
    private Boolean canChange;
    private Boolean isVocational;
    private String stateCurrClass;
    
    public String getEkrLevel() {
        return ekrLevel;
    }

    public void setEkrLevel(String ekrLevel) {
        this.ekrLevel = ekrLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public String getIscedClass() {
        return iscedClass;
    }

    public void setIscedClass(String iscedClass) {
        this.iscedClass = iscedClass;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getCanChange() {
        return canChange;
    }

    public void setCanChange(Boolean canChange) {
        this.canChange = canChange;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public String getStateCurrClass() {
        return stateCurrClass;
    }

    public void setStateCurrClass(String stateCurrClass) {
        this.stateCurrClass = stateCurrClass;
    }
}
