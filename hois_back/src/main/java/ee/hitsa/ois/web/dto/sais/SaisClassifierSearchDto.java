package ee.hitsa.ois.web.dto.sais;

import ee.hitsa.ois.domain.sais.SaisClassifier;
import ee.hitsa.ois.util.EntityUtil;

public class SaisClassifierSearchDto {

    private String code;
    private String value;
    private String nameEt;
    private String nameEn;
    private Long count;

    public static SaisClassifierSearchDto of(SaisClassifier saisClassifier) {
        SaisClassifierSearchDto dto = EntityUtil.bindToDto(saisClassifier, new SaisClassifierSearchDto());
        return dto;
    }

    public SaisClassifierSearchDto(String code, String nameEt, Long count) {
        this.setCode(code);
        this.count = count;
        this.nameEt = nameEt;
    }

    public SaisClassifierSearchDto() {
        // TODO Auto-generated constructor stub
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
