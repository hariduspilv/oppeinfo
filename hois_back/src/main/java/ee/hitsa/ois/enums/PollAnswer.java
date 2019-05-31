package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum PollAnswer {
    /** Tekst */
    VASTUS_T,
    /** Märkeruut */
    VASTUS_M,
    /** Raadionupp */
    VASTUS_R,
    /** Valikmenüü */
    VASTUS_V,
    /** Õpilasesinduse valik */
    VASTUS_S;
    
    public static final List<String> VASTUS_LIST = EnumUtil.toNameList(
            VASTUS_T,
            VASTUS_M,
            VASTUS_R,
            VASTUS_V,
            VASTUS_S);

}
