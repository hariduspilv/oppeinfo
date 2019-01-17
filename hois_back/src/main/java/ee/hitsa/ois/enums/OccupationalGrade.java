package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum OccupationalGrade {

    KUTSEHINDAMINE_5(5),
    KUTSEHINDAMINE_4(4),
    KUTSEHINDAMINE_3(3),
    KUTSEHINDAMINE_2(2),
    KUTSEHINDAMINE_1(1),
    KUTSEHINDAMINE_A(0),
    KUTSEHINDAMINE_MA(0),
    KUTSEHINDAMINE_X(0);

    private final int mark;

    private OccupationalGrade(int mark) {
        this.mark = mark;
    }

    public static final List<String> OCCUPATIONAL_GRADE_POSITIVE = EnumUtil.toNameList(KUTSEHINDAMINE_5, KUTSEHINDAMINE_4,
            KUTSEHINDAMINE_3, KUTSEHINDAMINE_A);

    public static final List<String> OCCUPATIONAL_GRADE_DISTINCTIVE = EnumUtil.toNameList(KUTSEHINDAMINE_5, KUTSEHINDAMINE_4,
            KUTSEHINDAMINE_3, KUTSEHINDAMINE_2, KUTSEHINDAMINE_1);

    public static boolean isPositive(String gradeCode) {
        return OCCUPATIONAL_GRADE_POSITIVE.contains(gradeCode);
    }

    public static boolean isDistinctive(String gradeCode) {
        return OCCUPATIONAL_GRADE_DISTINCTIVE.contains(gradeCode);
    }

    public int getMark() {
        return mark;
    }

    public static int getGradeMark(String gradeCode) {
        for (OccupationalGrade grade : values()) {
            if (grade.name().equals(gradeCode)) {
                return grade.getMark();
            }
        }
        return 0;
    }
}
