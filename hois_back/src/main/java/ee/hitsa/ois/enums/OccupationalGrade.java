package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum OccupationalGrade {

    KUTSEHINDAMINE_5(5),
    KUTSEHINDAMINE_4(4),
    KUTSEHINDAMINE_3(3),
    KUTSEHINDAMINE_2(2),
    KUTSEHINDAMINE_A(5),
    KUTSEHINDAMINE_MA(0),
    KUTSEHINDAMINE_X(0);
    
    private final int mark;
    
    private OccupationalGrade(int mark) {
        this.mark = mark;
    }

    public static final List<String> OCCUPATIONAL_GRADE_POSITIVE = EnumUtil.toNameList(KUTSEHINDAMINE_5, KUTSEHINDAMINE_4,
            KUTSEHINDAMINE_3, KUTSEHINDAMINE_A);

    public static final List<String> OCCUPATIONAL_VALUE_GRADE_POSITIVE = EnumUtil.toNameList(KUTSEHINDAMINE_5, KUTSEHINDAMINE_4,
            KUTSEHINDAMINE_3);
    
    public static boolean isPositive(String gradeCode) {
        return OCCUPATIONAL_GRADE_POSITIVE.contains(gradeCode);
    }

    public int getMark() {
        return mark;
    }
}
