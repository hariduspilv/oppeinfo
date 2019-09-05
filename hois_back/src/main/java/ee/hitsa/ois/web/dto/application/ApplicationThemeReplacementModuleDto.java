package ee.hitsa.ois.web.dto.application;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ApplicationThemeReplacementModuleDto extends AutocompleteResult {

    private String moduleCode;
    private List<ApplicationOccupationModuleThemeDto> oldThemes = new ArrayList<>();
    private List<ApplicationOccupationModuleThemeDto> newThemes = new ArrayList<>();

    public ApplicationThemeReplacementModuleDto(Long id, String nameEt, String nameEn, String moduleCode) {
        super(id, nameEt, nameEn);
        this.moduleCode = moduleCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public List<ApplicationOccupationModuleThemeDto> getOldThemes() {
        return oldThemes;
    }

    public void setOldThemes(List<ApplicationOccupationModuleThemeDto> oldThemes) {
        this.oldThemes = oldThemes;
    }

    public List<ApplicationOccupationModuleThemeDto> getNewThemes() {
        return newThemes;
    }

    public void setNewThemes(List<ApplicationOccupationModuleThemeDto> newThemes) {
        this.newThemes = newThemes;
    }

}
