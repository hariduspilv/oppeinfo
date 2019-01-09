package ee.hitsa.ois.web.dto;

import java.util.Set;

public class SpecialityAutocompleteResult extends AutocompleteResult {

    private Long curriculum;
    private Set<Long> curriculumVersions;
    
    public SpecialityAutocompleteResult() {
        super();
    }
    
    public SpecialityAutocompleteResult(Long id, String nameEt, String nameEn) {
        super(id, nameEt, nameEn);
    }

    public SpecialityAutocompleteResult(Long id, String nameEt, String nameEn, Long curriculum, Set<Long> curriculumVersions) {
        super(id, nameEt, nameEn);
        this.curriculum = curriculum;
        this.curriculumVersions = curriculumVersions;
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
