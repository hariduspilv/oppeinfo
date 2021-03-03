package ee.hitsa.ois.web.dto.curriculum;

import java.util.Set;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumResult extends AutocompleteResult {

    private final AutocompleteResult curriculum;
    private final String code;
    private Set<Long> specialities;
    private Set<Long> occupationSpecialities;
    private String origStudyLevel;
    private Boolean higher;
    private Boolean vocational;

    public CurriculumResult(Long id, String nameEt, String nameEn, String code) {
        super(id, CurriculumUtil.curriculumName(code, nameEt), CurriculumUtil.curriculumName(code, nameEn));
        this.curriculum = new AutocompleteResult(id, nameEt, nameEn);
        this.code = code;
    }

    public CurriculumResult(Long id, String nameEt, String nameEn, String code, String merCode) {
        super(id, CurriculumUtil.curriculumNameWithMerCode(code, nameEt, merCode),
                CurriculumUtil.curriculumNameWithMerCode(code, nameEn, merCode));
        this.curriculum = new AutocompleteResult(id, nameEt, nameEn);
        this.code = code;
    }

    public AutocompleteResult getCurriculum() {
        return curriculum;
    }

    public String getCode() {
        return code;
    }

    public Set<Long> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Long> specialities) {
        this.specialities = specialities;
    }

    public String getOrigStudyLevel() {
        return origStudyLevel;
    }

    public void setOrigStudyLevel(String origStudyLevel) {
        this.origStudyLevel = origStudyLevel;
    }

    public Set<Long> getOccupationSpecialities() {
        return occupationSpecialities;
    }

    public void setOccupationSpecialities(Set<Long> occupationSpecialities) {
        this.occupationSpecialities = occupationSpecialities;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getVocational() {
        return vocational;
    }

    public void setVocational(Boolean vocational) {
        this.vocational = vocational;
    }
}
