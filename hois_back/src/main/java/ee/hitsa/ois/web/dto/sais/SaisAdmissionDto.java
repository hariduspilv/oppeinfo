package ee.hitsa.ois.web.dto.sais;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.sais.SaisAdmission;
import ee.hitsa.ois.enums.StudyLoad;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

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
    private Boolean is_archived;
    private Boolean isFullLoad;
    private Boolean isPartialLoad;
    private Boolean isUndefinedLoad;
    private List<String> loads;

    public static SaisAdmissionDto of(SaisAdmission saisAdmissionDto) {
        SaisAdmissionDto dto = EntityUtil.bindToDto(saisAdmissionDto, new SaisAdmissionDto());
        dto.setLoads(new ArrayList<>());
        if (Boolean.TRUE.equals(dto.getIsFullLoad())) {
            dto.getLoads().add(StudyLoad.OPPEKOORMUS_TAIS.name());
        }
        if (Boolean.TRUE.equals(dto.getIsPartialLoad())) {
            dto.getLoads().add(StudyLoad.OPPEKOORMUS_OSA.name());
        }
        if (Boolean.TRUE.equals(dto.getIsUndefinedLoad())) {
            dto.getLoads().add(StudyLoad.OPPEKOORMUS_MTA.name());
        }
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

	public Boolean getArchived() {
		return is_archived;
	}

	public void setArchived(Boolean is_archived) {
		this.is_archived = is_archived;
	}

    public Boolean getIs_archived() {
        return is_archived;
    }

    public void setIs_archived(Boolean is_archived) {
        this.is_archived = is_archived;
    }

    public Boolean getIsFullLoad() {
        return isFullLoad;
    }

    public void setIsFullLoad(Boolean isFullLoad) {
        this.isFullLoad = isFullLoad;
    }

    public Boolean getIsPartialLoad() {
        return isPartialLoad;
    }

    public void setIsPartialLoad(Boolean isPartialLoad) {
        this.isPartialLoad = isPartialLoad;
    }

    public Boolean getIsUndefinedLoad() {
        return isUndefinedLoad;
    }

    public void setIsUndefinedLoad(Boolean isUndefinedLoad) {
        this.isUndefinedLoad = isUndefinedLoad;
    }

    public List<String> getLoads() {
        return loads;
    }

    public void setLoads(List<String> loads) {
        this.loads = loads;
    }

}
