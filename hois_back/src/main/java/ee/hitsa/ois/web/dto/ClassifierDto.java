package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.Translatable;

public class ClassifierDto extends InsertedChangedVersionDto implements Translatable {

    private String code;
    private String nameEt;
    private String nameEn;
    private String nameRu;
    private Boolean valid;
    private String mainClassCode;
    private String value;
    private String value2;
    private String description;
    private LocalDate validFrom;
    private LocalDate validThru;
    private String extraval1;
    private String extraval2;
    private String ehisValue;
    private Boolean vocational;
    private Boolean higher;

    public static ClassifierDto of(Classifier classifier) {
        return EntityUtil.bindToDto(classifier, new ClassifierDto());
    }
    
    public static ClassifierDto ofMin(String code) {
        ClassifierDto dto = new ClassifierDto();
        dto.setCode(code);
        return dto;
    }

    public static ClassifierDto ofEssential(Classifier classifier) {
        return EntityUtil.bindToDto(classifier, new ClassifierDto(),
                "inserted", "insertedBy", "changed", "changedBy");
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Override
    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getMainClassCode() {
        return mainClassCode;
    }

    public void setMainClassCode(String mainClassCode) {
        this.mainClassCode = mainClassCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
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

    public String getEhisValue() {
        return ehisValue;
    }

    public void setEhisValue(String ehisValue) {
        this.ehisValue = ehisValue;
    }

    public Boolean getVocational() {
        return vocational;
    }

    public void setVocational(Boolean vocational) {
        this.vocational = vocational;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

}
