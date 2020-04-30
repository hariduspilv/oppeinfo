package ee.hitsa.ois.web.commandobject.basemodule;

import java.time.LocalDate;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class BaseModuleSearchCommand {

    private String name;
    private String addNameEt;
    private EntityConnectionCommand curriculum;
    private EntityConnectionCommand curriculumVersion;
    private LocalDate validFrom;
    private LocalDate validThru;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddNameEt() {
        return addNameEt;
    }

    public void setAddNameEt(String addNameEt) {
        this.addNameEt = addNameEt;
    }

    public EntityConnectionCommand getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(EntityConnectionCommand curriculum) {
        this.curriculum = curriculum;
    }

    public EntityConnectionCommand getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(EntityConnectionCommand curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
}
