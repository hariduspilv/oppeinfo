package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import ee.hitsa.ois.domain.SaisAdmission;
import ee.hitsa.ois.util.EntityUtil;

public class SaisAdmissionDto {

    private Long id;
    private String code;
    private AutocompleteResult curriculumVersion;
    private Integer places;
    private String language;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String studyForm;
    private String studyLevel;
    private String studyLoad;
    private String fin;

    public static SaisAdmissionDto of(SaisAdmission saisAdmissionDto) {
        SaisAdmissionDto dto = EntityUtil.bindToDto(saisAdmissionDto, new SaisAdmissionDto());
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

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public String getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(String studyLoad) {
        this.studyLoad = studyLoad;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
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
