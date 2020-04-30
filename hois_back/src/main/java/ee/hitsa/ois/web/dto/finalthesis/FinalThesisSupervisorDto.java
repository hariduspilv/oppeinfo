package ee.hitsa.ois.web.dto.finalthesis;

import ee.hitsa.ois.domain.FinalThesisSupervisor;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.FinalThesisSupervisorForm;

public class FinalThesisSupervisorDto extends FinalThesisSupervisorForm {
    
    public static FinalThesisSupervisorDto of(FinalThesisSupervisor supervisor) {
        if (supervisor == null) {
            return null;
        }
        return EntityUtil.bindToDto(supervisor, new FinalThesisSupervisorDto());
    }

}
