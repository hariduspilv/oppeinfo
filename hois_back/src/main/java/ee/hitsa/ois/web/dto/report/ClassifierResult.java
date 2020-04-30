package ee.hitsa.ois.web.dto.report;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ClassifierResult extends AutocompleteResult {
    
    public ClassifierResult(Long id, String nameEt, String nameEn, String code) {
        super(id, nameEt, nameEn);
        this.code = code;
    }
    
    public static ClassifierResult of(Classifier classifier) {
        return new ClassifierResult(null, classifier.getNameEt(), classifier.getNameEn(), classifier.getCode());
    }
    
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
