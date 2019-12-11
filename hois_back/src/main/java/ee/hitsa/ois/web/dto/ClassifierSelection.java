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
    private String extraval1;
    private String extraval2;
    private Collection<String> parents;

    public ClassifierSelection(String code, String nameEt, String nameEn, String nameRu, Boolean valid, 
            Boolean higher, Boolean vocational, String mainClassCode, String value, String value2,
            LocalDate validFrom, LocalDate validThru, String extraval1, String extraval2) {
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
        this.extraval1 = extraval1;
        this.extraval2 = extraval2;
    }

    public static ClassifierSelection of(Classifier c) {
        return new ClassifierSelection(c.getCode(), c.getNameEt(), c.getNameEn(), c.getNameRu(),
                Boolean.valueOf(c.isValid()), Boolean.valueOf(c.isHigher()), Boolean.valueOf(c.isVocational()),
                c.getMainClassCode(), c.getValue(), c.getValue2(), c.getValidFrom(), c.getValidThru(), c.getExtraval1(),
                c.getExtraval2());
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

    public String getExtraval1() {
        return extraval1;
    }

    public void setExtraval1(String extraval1) {
        this.extraval1 = extraval1;
    }

    public String getExtraval2() {
        return extraval2;
    }

    public void setExtraval2(String extraval2) {
        this.extraval2 = extraval2;
    }

}
