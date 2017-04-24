package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.util.EntityUtil;

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

    public StateCurriculumSearchDto() {}
    
    public StateCurriculumSearchDto(Long id, String nameEt, String nameEn, LocalDate validFrom,
            LocalDate validThru, Long credits, String status, String iscedClass) {
        this.id = id;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.validFrom = validFrom;
        this.validThru = validThru;
        this.credits = credits;
        this.status = status;
        this.iscedClass = iscedClass;
    }
    
    public static StateCurriculumSearchDto of (StateCurriculum stateCurriculum) {
        StateCurriculumSearchDto dto = new StateCurriculumSearchDto();
        EntityUtil.bindToDto(stateCurriculum, dto, "status", "iscedClass");
        dto.setStatus(EntityUtil.getNullableCode(stateCurriculum.getStatus()));
        return dto;
    }
    
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
}
