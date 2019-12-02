package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum HigherModuleType {

    KORGMOODUL_L,
    KORGMOODUL_V,
    KORGMOODUL_P,
    KORGMOODUL_F,
    KORGMOODUL_M;

    public static final List<String> FINAL_MODULES = EnumUtil.toNameList(KORGMOODUL_L, KORGMOODUL_F);
}
