package ee.hitsa.ois.web.dto.sais;

import java.time.LocalDate;

import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SaisAdmissionSearchDto {

    private Long id;
    private String code;
    private AutocompleteResult curriculumVersion;
    private Integer places;
    private String language;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String studyForm;

    public static SaisAdmissionSearchDto of(SaisAdmission saisAdmission) {
        SaisAdmissionSearchDto dto = EntityUtil.bindToDto(saisAdmission, new SaisAdmissionSearchDto());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

}
