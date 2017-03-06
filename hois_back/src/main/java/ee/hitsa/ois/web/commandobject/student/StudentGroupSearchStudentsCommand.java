package ee.hitsa.ois.web.commandobject.student;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class StudentGroupSearchStudentsCommand {

    private Long id;
    @NotNull
    private EntityConnectionCommand curriculum;
    @NotEmpty
    private String language;
    @NotEmpty
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
