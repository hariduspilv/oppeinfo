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

    private final Long curriculum;
    private final Long schoolDepartment;
    private final String studyForm;
    private final Boolean isVocational;

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

    // FIXME is this used?
    public static CurriculumVersionResult of(CurriculumVersion curriculumVersion) {
        Curriculum curriculum = curriculumVersion.getCurriculum();
        Classifier studyForm = curriculumVersion.getCurriculumStudyForm() != null ? curriculumVersion.getCurriculumStudyForm().getStudyForm() : null;
        String code = curriculumVersion.getCode();
        return new CurriculumVersionResult(curriculumVersion.getId(), CurriculumUtil.versionName(code, curriculum.getNameEt()),
                CurriculumUtil.versionName(code, curriculum.getNameEn()), curriculum.getId(),
                EntityUtil.getId(curriculumVersion.getSchoolDepartment()), EntityUtil.getNullableCode(studyForm),
                Boolean.valueOf(CurriculumUtil.isVocational(curriculum)));
    }
}
