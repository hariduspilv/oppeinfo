package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionHigherModuleResult extends AutocompleteResult {

    private String type;
    private Long curriculumVersionId;
    private Long curriculumId;

    public CurriculumVersionHigherModuleResult(Long id, String nameEt, String nameEn, String type) {
        super(id, nameEt, nameEn);
        this.type = type;
    }

    public CurriculumVersionHigherModuleResult(Long id, String nameEt, String nameEn, String type,
            Long curriculumVersionId, Long curriculumId) {
        super(id, nameEt, nameEn);
        this.type = type;
        this.curriculumVersionId = curriculumVersionId;
        this.curriculumId = curriculumId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCurriculumVersionId() {
        return curriculumVersionId;
    }

    public void setCurriculumVersionId(Long curriculumVersionId) {
        this.curriculumVersionId = curriculumVersionId;
    }

    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
    }
}
