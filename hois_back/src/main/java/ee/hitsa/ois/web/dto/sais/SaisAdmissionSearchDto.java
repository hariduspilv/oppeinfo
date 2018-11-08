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
    private Boolean failed;
    private String error;
    private Boolean is_archived;

    public static SaisAdmissionSearchDto of(SaisAdmission saisAdmission) {
        SaisAdmissionSearchDto dto = EntityUtil.bindToDto(saisAdmission, new SaisAdmissionSearchDto());
        return dto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("code: ");
        sb.append(code);
        if (!Boolean.TRUE.equals(failed)) {
            sb.append(";places: ");
            sb.append(places);
            sb.append(";language: ");
            sb.append(language);
            sb.append(";periodStard: ");
            sb.append(periodStart.toString());
            sb.append(";periodEnd: ");
            sb.append(periodEnd.toString());
            sb.append(";studyForm: ");
            sb.append(studyForm);
            sb.append(";is_archived: ");
            sb.append(is_archived);
        } else {
            sb.append(";failed: ");
            sb.append(failed.toString());
            sb.append(";error: ");
            sb.append(error);
        }
        return sb.toString();
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

    public Boolean getFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

	public Boolean getArchived() {
		return is_archived;
	}

	public void setArchived(Boolean is_archived) {
		this.is_archived = is_archived;
	}

}
