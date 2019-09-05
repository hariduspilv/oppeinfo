package ee.hitsa.ois.web.dto.application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationThemeReplacementDto {

    private List<ApplicationThemeReplacementModuleDto> modules = new ArrayList<>();

    public List<ApplicationThemeReplacementModuleDto> getModules() {
        return modules;
    }

    public void setModules(List<ApplicationThemeReplacementModuleDto> modules) {
        this.modules = modules;
    }

}
