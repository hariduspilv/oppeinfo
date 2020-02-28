package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum HigherModuleType {
    
    KORGMOODUL_C,   // Optional subjects
    KORGMOODUL_L,   // Final thesis
    KORGMOODUL_V,   // Unschooling
    KORGMOODUL_P,   // Internship
    KORGMOODUL_F,   // Final exam
    KORGMOODUL_M;   // Custom module

    public static final List<String> FINAL_MODULES = EnumUtil.toNameList(KORGMOODUL_L, KORGMOODUL_F);
}
