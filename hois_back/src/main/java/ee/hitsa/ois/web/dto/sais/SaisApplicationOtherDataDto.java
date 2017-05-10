package ee.hitsa.ois.web.dto.sais;

import ee.hitsa.ois.domain.sais.SaisApplicationOtherData;
import ee.hitsa.ois.util.EntityUtil;

public class SaisApplicationOtherDataDto {

    private Long id;
    private String otherDataName;
    private String otherDataValue;

    public static SaisApplicationOtherDataDto of(SaisApplicationOtherData saisApplicationOtherData) {
        SaisApplicationOtherDataDto dto = EntityUtil.bindToDto(saisApplicationOtherData, new SaisApplicationOtherDataDto());
        return dto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtherDataName() {
        return otherDataName;
    }

    public void setOtherDataName(String otherDataName) {
        this.otherDataName = otherDataName;
    }

    public String getOtherDataValue() {
        return otherDataValue;
    }

    public void setOtherDataValue(String otherDataValue) {
        this.otherDataValue = otherDataValue;
    }

}
