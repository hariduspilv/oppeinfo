package ee.hitsa.ois.web.commandobject.apelapplication;

import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationFormalReplacedSubjectOrModuleForm extends InsertedChangedVersionDto {

    private Long id;
    private Long subject;
    private Long curriculumVersionOmodule;
    private Long curriculumVersionOmoduleTheme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(Long curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public Long getCurriculumVersionOmoduleTheme() {
        return curriculumVersionOmoduleTheme;
    }

    public void setCurriculumVersionOmoduleTheme(Long curriculumVersionOmoduleTheme) {
        this.curriculumVersionOmoduleTheme = curriculumVersionOmoduleTheme;
    }

}
