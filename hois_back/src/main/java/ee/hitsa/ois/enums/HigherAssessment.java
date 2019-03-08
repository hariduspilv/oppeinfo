package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum HigherAssessment {

    KORGHINDAMINE_0(Boolean.TRUE, Boolean.FALSE, Short.valueOf((short) 0)),
    KORGHINDAMINE_1(Boolean.TRUE, Boolean.TRUE, Short.valueOf((short) 1)),
    KORGHINDAMINE_2(Boolean.TRUE, Boolean.TRUE, Short.valueOf((short) 2)),
    KORGHINDAMINE_3(Boolean.TRUE, Boolean.TRUE, Short.valueOf((short) 3)),
    KORGHINDAMINE_4(Boolean.TRUE, Boolean.TRUE, Short.valueOf((short) 4)),
    KORGHINDAMINE_5(Boolean.TRUE, Boolean.TRUE, Short.valueOf((short) 5)),
    KORGHINDAMINE_A(Boolean.FALSE, Boolean.TRUE, null),
    KORGHINDAMINE_M(Boolean.FALSE, Boolean.FALSE, null),
    KORGHINDAMINE_MI(null, Boolean.FALSE, null);

    private final Boolean isDistinctive;
    private final Boolean isPositive;
    // grade as number, used for calculating average grade
    private final Short mark;
    
    public static final List<String> GRADE_POSITIVE = EnumUtil.toNameList(KORGHINDAMINE_5, KORGHINDAMINE_4,
            KORGHINDAMINE_3, KORGHINDAMINE_2, KORGHINDAMINE_1, KORGHINDAMINE_A);
 
    private HigherAssessment(Boolean isDistinctive, Boolean isPositive, Short mark) {
        this.isDistinctive = isDistinctive;
        this.isPositive = isPositive;
        this.mark = mark;
    }

    public Boolean getIsDistinctive() {
        return isDistinctive;
    }

    public Boolean getIsPositive() {
        return isPositive;
    }

    public Short getMark() {
        return mark;
    }

    public static boolean isPositive(String grade) {
        for (HigherAssessment ha : values()) {
            if (Boolean.TRUE.equals(ha.getIsPositive()) && ha.name().equals(grade)) {
                return true;
            }
        }
        return false;
    }
    
    public static Short getGradeMark(String grade) {
        for (HigherAssessment ha : values()) {
            if (ha.name().equals(grade)) {
                return ha.getMark();
            }
        }
        return null;
    }
}
