package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

/**
 * Variant of AutocompleteResult with additional fields for filtering in frontend
 */
public class StudentGroupResult extends AutocompleteResult {

    private Long curriculum;
    private String merCode;
    private Long curriculumVersion;
    private String studyForm;
    private String language;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean isOccupied;
    
    public StudentGroupResult() {
    	
    }

    public StudentGroupResult(Long id, String code) {
        super(id, code, code);
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public Boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
}
