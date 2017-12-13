package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumVersionOccupationModuleResult  extends AutocompleteResult {

    private String assessment;
    
    public CurriculumVersionOccupationModuleResult(Long id, String nameEt, String nameEn, String assessment) {
        super(id, nameEt, nameEn);
        this.assessment = assessment;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
    
}
