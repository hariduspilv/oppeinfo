package ee.hitsa.ois.web.dto.finalthesis;

import ee.hitsa.ois.domain.FinalThesisCercs;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.ClassifierUtil;

public class FinalThesisCercsDto extends FinalThesisCercsForm {

    private String cercsType;
    
    public static FinalThesisCercsDto of(FinalThesisCercs ftc) {
        FinalThesisCercsDto dto = new FinalThesisCercsDto();
        dto.setId(ftc.getId());
        dto.setCercs(ftc.getCercs().getCode());
        dto.setCercsType(ClassifierUtil.parentFor(ftc.getCercs(), MainClassCode.CERCS_TYPE).map(cl -> cl.getCode()).orElse(null));
        return dto;
    }

    public String getCercsType() {
        return cercsType;
    }

    public void setCercsType(String cercsType) {
        this.cercsType = cercsType;
    }
}
