package ee.hitsa.ois.web.commandobject.curriculum;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class CurriculumAutocompleteCommand extends SearchCommand {

    private Boolean higher;
    private Boolean closed;
    private Long teacher;
    private Integer minSpecialities;
    private String studyLevel;
    private Boolean withMerCode = Boolean.FALSE;
    private Long userId;

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public Integer getMinSpecialities() {
        return minSpecialities;
    }

    public void setMinSpecialities(Integer minSpecialities) {
        this.minSpecialities = minSpecialities;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public Boolean getWithMerCode() {
        return withMerCode;
    }

    public void setWithMerCode(Boolean withMerCode) {
        this.withMerCode = withMerCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
