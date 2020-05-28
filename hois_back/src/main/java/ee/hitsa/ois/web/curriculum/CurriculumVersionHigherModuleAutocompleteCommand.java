package ee.hitsa.ois.web.curriculum;

public class CurriculumVersionHigherModuleAutocompleteCommand {

    private Long curriculumVersion;
    private Boolean isGrade;

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Boolean getIsGrade() {
        return isGrade;
    }

    public void setIsGrade(Boolean isGrade) {
        this.isGrade = isGrade;
    }
}
