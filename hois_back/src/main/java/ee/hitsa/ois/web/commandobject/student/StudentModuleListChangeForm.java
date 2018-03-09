package ee.hitsa.ois.web.commandobject.student;

import java.util.List;

import javax.validation.Valid;

import ee.hitsa.ois.validation.Required;


public class StudentModuleListChangeForm {
    
    @Required
    @Valid
    private List<StudentResultModuleChangeForm> modules;

    public List<StudentResultModuleChangeForm> getModules() {
        return modules;
    }

    public void setModules(List<StudentResultModuleChangeForm> modules) {
        this.modules = modules;
    }
    
}
