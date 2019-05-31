package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum PollStatus {
    
    /** Koostamisel */
    KYSITLUS_STAATUS_E,
    /** Kinnitatud */
    KYSITLUS_STAATUS_K,
    /** Lõppenud */
    KYSITLUS_STAATUS_L;
    
    public static final List<String> KYSITLUS_STAATUS_LIST = EnumUtil.toNameList(KYSITLUS_STAATUS_E, KYSITLUS_STAATUS_K,
            KYSITLUS_STAATUS_L);
    
}
