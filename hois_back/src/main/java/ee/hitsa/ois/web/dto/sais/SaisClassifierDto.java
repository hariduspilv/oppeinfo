package ee.hitsa.ois.web.dto.sais;

import ee.hitsa.ois.domain.sais.SaisClassifier;
import ee.hitsa.ois.util.EntityUtil;

public class SaisClassifierDto {

    private Long id;
    private String code;
    private String parentCode;
    private String value;
    private String nameEt;
    private String nameEn;
    
    public static SaisClassifierDto of(SaisClassifier saisClassifierDto) {
        SaisClassifierDto dto = EntityUtil.bindToDto(saisClassifierDto, new SaisClassifierDto());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    
    
}
