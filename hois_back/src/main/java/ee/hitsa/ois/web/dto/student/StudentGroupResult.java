package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

/**
 * Variant of AutocompleteResult with additional fields for filtering in frontend
 */
public class StudentGroupResult extends AutocompleteResult {

    private Long curriculum;
    private Long curriculumVersion;
    private String studyForm;
    private String language;

    public StudentGroupResult(Long id, String code) {
        super(id, code, code);
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
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

    public static StudentGroupResult of(StudentGroup studentGroup) {
        StudentGroupResult dto = new StudentGroupResult(studentGroup.getId(), studentGroup.getCode());
        dto.setCurriculum(EntityUtil.getId(studentGroup.getCurriculum()));
        dto.setCurriculumVersion(EntityUtil.getNullableId(studentGroup.getCurriculumVersion()));
        dto.setStudyForm(EntityUtil.getNullableCode(studentGroup.getStudyForm()));
        dto.setLanguage(EntityUtil.getNullableCode(studentGroup.getLanguage()));
        return dto;
    }
}
