package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum PollType {
    
    /** Õppeaine või päeviku tagasiside */
    KYSITLUS_O,
    /** Õpetaja või õppejõu tagasiside */
    KYSITLUS_T,
    /** Praktika tagasiside */
    KYSITLUS_P,
    /** Üldine tagasiside */
    KYSITLUS_Y,
    /** Õpilasesinduse valimised */
    KYSITLUS_V;
    
    public static final List<String> KYSITLUS_LIST = EnumUtil.toNameList(KYSITLUS_O, KYSITLUS_T,
            KYSITLUS_P, KYSITLUS_Y, KYSITLUS_V);

}
