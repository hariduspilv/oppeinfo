package ee.hitsa.ois.web.dto.curriculum;

import java.util.Set;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumResult extends AutocompleteResult {

    private final AutocompleteResult curriculum;
    private final String code;
    private Set<Long> specialities;

    public CurriculumResult(Long id, String nameEt, String nameEn, String code) {
        super(id, CurriculumUtil.curriculumName(code, nameEt), CurriculumUtil.curriculumName(code, nameEn));
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
    
}
