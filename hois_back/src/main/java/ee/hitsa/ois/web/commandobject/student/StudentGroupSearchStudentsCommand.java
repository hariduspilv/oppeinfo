package ee.hitsa.ois.web.commandobject.student;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class StudentGroupSearchStudentsCommand {

    private Long id;
    @NotNull
    private EntityConnectionCommand curriculum;
    private Long curriculumVersion;
    @Required
    private String language;
    @Required
    private String studyForm;

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
}
