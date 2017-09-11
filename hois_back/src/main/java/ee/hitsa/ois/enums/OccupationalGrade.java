package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum OccupationalGrade {

    KUTSEHINDAMINE_5,
    KUTSEHINDAMINE_4,
    KUTSEHINDAMINE_3,
    KUTSEHINDAMINE_2,
    KUTSEHINDAMINE_A,
    KUTSEHINDAMINE_MA,
    KUTSEHINDAMINE_X;


    public static final List<String> OCCUPATIONAL_GRADE_POSITIVE = EnumUtil.toNameList(KUTSEHINDAMINE_5, KUTSEHINDAMINE_4,
            KUTSEHINDAMINE_3, KUTSEHINDAMINE_A);

    public static final List<String> OCCUPATIONAL_VALUE_GRADE_POSITIVE = EnumUtil.toNameList(KUTSEHINDAMINE_5, KUTSEHINDAMINE_4,
            KUTSEHINDAMINE_3);
}
