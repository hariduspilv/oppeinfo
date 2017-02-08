package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Classifier;

public class ClassifierSelection {

    private final String code;
    private final String nameEt;
    private final String nameEn;
    private final String nameRu;
    private final Boolean valid;

    public ClassifierSelection(String code, String nameEt, String nameEn, String nameRu, Boolean valid) {
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.valid = valid;
    }
    
    public static ClassifierSelection of(Classifier c) {
        return new ClassifierSelection(c.getCode(), c.getNameEt(), c.getNameEn(), c.getNameRu(), Boolean.valueOf(c.isValid()));
    }

    public String getCode() {
        return code;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public Boolean getValid() {
        return valid;
    }
}
