package ee.hitsa.ois.web.commandobject;

public class SpecialitiesAutocompleteCommand extends SearchCommand {

    private Long curriculum;
    private Long curriculumVersion;
    private Boolean filter;
    private Boolean higher;
    private Boolean vocational;
    
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

    public Boolean getFilter() {
        return filter;
    }

    public void setFilter(Boolean filter) {
        this.filter = filter;
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
