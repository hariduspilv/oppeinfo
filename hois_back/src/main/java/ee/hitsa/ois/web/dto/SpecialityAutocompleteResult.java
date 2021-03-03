package ee.hitsa.ois.web.dto;

import java.util.Set;

public class SpecialityAutocompleteResult extends AutocompleteResult {

    private String code;
    private Long curriculum;
    private Set<Long> curriculumVersions;
    
    public SpecialityAutocompleteResult() {
        super();
    }
    
    public SpecialityAutocompleteResult(Long id, String code, String nameEt, String nameEn) {
        super(id, nameEt, nameEn);
        this.code = code;
    }

    public SpecialityAutocompleteResult(Long id, String code, String nameEt, String nameEn, Long curriculum, Set<Long> curriculumVersions) {
        this(id, code, nameEt, nameEn);
        this.curriculum = curriculum;
        this.curriculumVersions = curriculumVersions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public Set<Long> getCurriculumVersions() {
        return curriculumVersions;
    }

    public void setCurriculumVersions(Set<Long> curriculumVersions) {
        this.curriculumVersions = curriculumVersions;
    }
}
