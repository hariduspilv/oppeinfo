package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;

public class StateCurriculumUtil {
    
    
    public static boolean canCreate(HoisUserDetails user) {
        return user.isMainAdmin() || user.isExternalExpert();
    }
    
    public static boolean canChange(HoisUserDetails user, StateCurriculum sc) {
        return canChange(user, EntityUtil.getCode(sc.getStatus()));
    }
    
    public static boolean canChange(HoisUserDetails user, String status) {
        return canCreate(user) && 
                (CurriculumStatus.OPPEKAVA_STAATUS_S.name().equals(status) || 
                CurriculumStatus.OPPEKAVA_STAATUS_K.name().equals(status));
    }
}
