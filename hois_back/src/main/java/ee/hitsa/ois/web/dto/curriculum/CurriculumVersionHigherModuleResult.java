package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionHigherModuleResult extends AutocompleteResult {

    private String type;

    public CurriculumVersionHigherModuleResult(Long id, String nameEt, String nameEn, String type) {
        super(id, nameEt, nameEn);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
