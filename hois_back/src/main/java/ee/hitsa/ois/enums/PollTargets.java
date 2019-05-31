    package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum PollTargets {
    /** Õppijad */
    KYSITLUS_SIHT_O,
    /** Õpetajad/õppejõud */
    KYSITLUS_SIHT_T,
    /** Ettevõtte juhendajad */
    KYSITLUS_SIHT_E,
    /** Õppija esindajad */
    KYSITLUS_SIHT_L,
    /** Välised eksperdid */
    KYSITLUS_SIHT_V,
    /** Administratiivsed töötajad */
    KYSITLUS_SIHT_A;
    
    public static final List<String> KYSITLUS_SIHT_LIST = EnumUtil.toNameList(
            KYSITLUS_SIHT_O,
            KYSITLUS_SIHT_T,
            KYSITLUS_SIHT_E,
            KYSITLUS_SIHT_L,
            KYSITLUS_SIHT_V,
            KYSITLUS_SIHT_A);
}
