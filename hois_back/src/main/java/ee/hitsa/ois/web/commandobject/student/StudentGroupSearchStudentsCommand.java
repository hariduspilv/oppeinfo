package ee.hitsa.ois.web.commandobject.student;

import ee.hitsa.ois.validation.Conditional;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

@Conditional(selected = "isGuest", values = {"false", "null"}, required = {"curriculum", "language", "studyForm"})
public class StudentGroupSearchStudentsCommand {

    private Long id;
    private EntityConnectionCommand curriculum;
    private Long curriculumVersion;
    private String language;
    private String studyForm;
    private Boolean isGuest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityConnectionCommand getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(EntityConnectionCommand curriculum) {
        this.curriculum = curriculum;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public Boolean getIsGuest() {
        return isGuest;
    }

    public void setIsGuest(Boolean isGuest) {
        this.isGuest = isGuest;
    }
}
