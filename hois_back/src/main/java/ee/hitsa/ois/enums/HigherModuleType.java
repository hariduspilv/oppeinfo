package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum HigherModuleType {

    KORGMOODUL_L(3L),   // Final thesis
    KORGMOODUL_V(1L),   // Free choice
    KORGMOODUL_P(null),   // Internship
    KORGMOODUL_F(2L),   // Final exam
    KORGMOODUL_M(null);   // Custom module

    public static final List<String> FINAL_MODULES = EnumUtil.toNameList(KORGMOODUL_L, KORGMOODUL_F);
    public static final List<String> CAN_NOT_MARK_AS_COMPLETE = EnumUtil.toNameList(KORGMOODUL_V, KORGMOODUL_L, KORGMOODUL_F);

    private final Long order;

    private HigherModuleType(Long order) {
        this.order = order;
    }

    public Long getOrder() {
        return order;
    }
}
