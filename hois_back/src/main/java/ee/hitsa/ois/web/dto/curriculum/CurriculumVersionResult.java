package ee.hitsa.ois.web.dto.curriculum;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

/**
 * Variant of AutocompleteResult with additional fields for filtering in frontend
 */
public class CurriculumVersionResult extends AutocompleteResult {

    private final Long curriculum;
    private final Long schoolDepartment;
    private final String studyForm;
    private final Boolean isVocational;
    private List<String> studyLang;

    public CurriculumVersionResult(Long id, String nameEt, String nameEn, Long curriculum, Long schoolDepartment, String studyForm, Boolean isVocational) {
        super(id, nameEt, nameEn);

        this.curriculum = curriculum;
        this.schoolDepartment = schoolDepartment;
        this.studyForm = studyForm;
        this.isVocational = isVocational;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public Long getSchoolDepartment() {
        return schoolDepartment;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public List<String> getStudyLang() {
        return studyLang;
    }

    public void setStudyLang(List<String> studyLang) {
        this.studyLang = studyLang;
    }
}
