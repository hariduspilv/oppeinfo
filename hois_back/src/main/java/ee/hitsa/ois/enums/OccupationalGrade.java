package ee.hitsa.ois.enums;

import java.util.Arrays;
import java.util.List;

public enum OccupationalGrade {

    KUTSEHINDAMINE_5,
    KUTSEHINDAMINE_4,
    KUTSEHINDAMINE_3,
    KUTSEHINDAMINE_2,
    KUTSEHINDAMINE_A,
    KUTSEHINDAMINE_MA,
    KUTSEHINDAMINE_X;


    public static final List<String> OCCUPATIONAL_GRADE_POSITIVE = Arrays.asList(KUTSEHINDAMINE_5.name(), KUTSEHINDAMINE_4.name(),
            KUTSEHINDAMINE_4.name(), KUTSEHINDAMINE_A.name());

}
