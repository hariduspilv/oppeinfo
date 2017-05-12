package ee.hitsa.ois.domain.sais;

import javax.persistence.Entity;
import javax.persistence.Id;

import ee.hitsa.ois.domain.BaseEntity;

@Entity
public class SaisClassifier extends BaseEntity {
    
    @Id
    private String code;
    private String parentCode;
    private String value;
    private String nameEt;
    private String nameEn;

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
