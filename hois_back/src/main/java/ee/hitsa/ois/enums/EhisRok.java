package ee.hitsa.ois.enums;
import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum EhisRok {
    EHIS_ROK_PROK,
    EHIS_ROK_GROK;
    
    public static final Enum<?>[] SECONDARY_ROK_ARRAY = new EhisRok[] {EHIS_ROK_PROK, EHIS_ROK_GROK};
    
    public static final List<String> SECONDARY_ROK_LIST = EnumUtil.toNameList(EHIS_ROK_PROK, EHIS_ROK_GROK);
}
