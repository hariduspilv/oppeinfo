package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

/**
 * Variant of AutocompleteResult with additional fields for filtering in frontend
 */
public class CurriculumVersionResult extends AutocompleteResult {

    private final String studyForm;

    public CurriculumVersionResult(Long id, String nameEt, String nameEn, String studyForm) {
        super(id, nameEt, nameEn);

        this.studyForm = studyForm;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public static CurriculumVersionResult of(CurriculumVersion curriculumVersion) {
        Curriculum curriculum = curriculumVersion.getCurriculum();
        Classifier studyForm = curriculumVersion.getCurriculumStudyForm() != null ? curriculumVersion.getCurriculumStudyForm().getStudyForm() : null;
        return new CurriculumVersionResult(curriculumVersion.getId(), CurriculumUtil.versionName(curriculumVersion.getCode(),
                curriculum.getNameEt()), CurriculumUtil.versionName(curriculumVersion.getCode(), curriculum.getNameEn()),
                EntityUtil.getNullableCode(studyForm));
    }
}
