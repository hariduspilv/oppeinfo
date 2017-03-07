package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Classifier;

public class ClassifierSelection {

    private final String code;
    private final String nameEt;
    private final String nameEn;
    private final String nameRu;
    private final Boolean valid;
    private final String mainClassCode;
    private final Boolean vocational;
    private final Boolean higher;
    private final String value;

    public ClassifierSelection(String code, String nameEt, String nameEn, String nameRu, Boolean valid, Boolean higher, Boolean vocational, String mainClassCode, String value) {
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.valid = valid;
        this.higher = higher;
        this.vocational = vocational;
        this.mainClassCode = mainClassCode;
        this.value = value;
    }

    public static ClassifierSelection of(Classifier c) {
        return new ClassifierSelection(c.getCode(), c.getNameEt(), c.getNameEn(), c.getNameRu(),
                Boolean.valueOf(c.isValid()), Boolean.valueOf(c.isHigher()), Boolean.valueOf(c.isVocational()),
                c.getMainClassCode(), c.getValue());
    }

    public String getValue() {
        return value;
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

    public String getMainClassCode() {
        return mainClassCode;
    }

    public Boolean getVocational() {
        return vocational;
    }

    public Boolean getHigher() {
        return higher;
    }
}
