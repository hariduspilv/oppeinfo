package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.SaisApplication;
import ee.hitsa.ois.util.EntityUtil;

public class SaisApplicationSearchDto {

    private String applicationNr;

    public static SaisApplicationSearchDto of(SaisApplication saisApplication) {
        SaisApplicationSearchDto dto = EntityUtil.bindToDto(saisApplication, new SaisApplicationSearchDto());
        return dto;
    }

    public String getApplicationNr() {
        return applicationNr;
    }

    public void setApplicationNr(String applicationNr) {
        this.applicationNr = applicationNr;
    }

}
