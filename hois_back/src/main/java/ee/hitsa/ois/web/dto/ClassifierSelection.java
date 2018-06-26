package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.Collection;

import ee.hitsa.ois.domain.Classifier;

/**
 * Class used for frontend classifier management.
 */
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
    private final String value2;
    private final LocalDate validFrom;
    private final LocalDate validThru;
    private Collection<String> parents;

    public ClassifierSelection(String code, String nameEt, String nameEn, String nameRu, Boolean valid, 
            Boolean higher, Boolean vocational, String mainClassCode, String value, String value2,
            LocalDate validFrom, LocalDate validThru) {
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.valid = valid;
        this.higher = higher;
        this.vocational = vocational;
        this.mainClassCode = mainClassCode;
        this.value = value;
        this.value2 = value2;
        this.validFrom = validFrom;
        this.validThru = validThru;
    }

    public static ClassifierSelection of(Classifier c) {
        return new ClassifierSelection(c.getCode(), c.getNameEt(), c.getNameEn(), c.getNameRu(),
                Boolean.valueOf(c.isValid()), Boolean.valueOf(c.isHigher()), Boolean.valueOf(c.isVocational()),
                c.getMainClassCode(), c.getValue(), c.getValue2(), c.getValidFrom(), c.getValidThru());
    }

    public String getValue2() {
        return value2;
    }

    public Collection<String> getParents() {
        return parents;
    }

    public void setParents(Collection<String> parents) {
        this.parents = parents;
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

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }
    
}
