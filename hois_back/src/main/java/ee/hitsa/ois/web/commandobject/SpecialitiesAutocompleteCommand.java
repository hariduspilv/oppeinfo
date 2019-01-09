package ee.hitsa.ois.web.commandobject;

public class SpecialitiesAutocompleteCommand extends SearchCommand {

    private Long curriculum;
    private Long curriculumVersion;
    
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
}
