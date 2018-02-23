package ee.hitsa.ois.web.commandobject.student;

import java.util.List;

import javax.validation.Valid;

import ee.hitsa.ois.validation.Required;


public class StudentModuleListChangeForm {
    
    @Required
    @Valid
    private List<StudentVocationalResultModuleChangeForm> vocationalModules;

    public List<StudentVocationalResultModuleChangeForm> getVocationalModules() {
        return vocationalModules;
    }

    public void setVocationalModules(List<StudentVocationalResultModuleChangeForm> vocationalModules) {
        this.vocationalModules = vocationalModules;
    }

}
