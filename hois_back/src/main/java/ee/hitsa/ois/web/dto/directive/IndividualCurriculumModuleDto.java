package ee.hitsa.ois.web.dto.directive;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class IndividualCurriculumModuleDto extends AutocompleteResult {

    private List<ExistingIndividualCurriculumModuleDto> existingModules;

    public IndividualCurriculumModuleDto() {
    }

    public IndividualCurriculumModuleDto(Long id, String nameEt, String nameEn,
            List<ExistingIndividualCurriculumModuleDto> existingModules) {
        super(id, nameEt, nameEn);
        this.existingModules = existingModules;
    }

    public List<ExistingIndividualCurriculumModuleDto> getExistingModules() {
        return existingModules;
    }

    public void setExistingModules(List<ExistingIndividualCurriculumModuleDto> existingModules) {
        this.existingModules = existingModules;
    }

}
