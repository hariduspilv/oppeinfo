package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum GuestStudentRightsFilter {
    ROLE_OIGUS_M_TEEMAOIGUS_LOPTEEMA,
    ROLE_OIGUS_V_TEEMAOIGUS_LOPTEEMA,
    ROLE_OIGUS_V_TEEMAOIGUS_VOTA;
    
    public static List<String> FILTER = EnumUtil.toNameList(GuestStudentRightsFilter.values());
}
