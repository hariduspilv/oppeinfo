package ee.hitsa.ois.enums;

import ee.hitsa.ois.util.EnumUtil;

import java.util.List;

public enum EducationLevel {

    HARIDUSTASE_B,
    HARIDUSTASE_C,
    HARIDUSTASE_D,
    HARIDUSTASE_G,
    HARIDUSTASE_I,
    HARIDUSTASE_K,
    HARIDUSTASE_M,
    HARIDUSTASE_P,
    HARIDUSTASE_PG,
    HARIDUSTASE_R,
    HARIDUSTASE_X;

    public static final List<String> BASIC = EnumUtil.toNameList(HARIDUSTASE_PG);
    public static final List<String> SECONDARY = EnumUtil.toNameList(HARIDUSTASE_G);
    public static final List<String> VOCATIONAL = EnumUtil.toNameList(HARIDUSTASE_C, HARIDUSTASE_K, HARIDUSTASE_P,
            HARIDUSTASE_X);
    public static final List<String> HIGHER = EnumUtil.toNameList(HARIDUSTASE_B, HARIDUSTASE_D, HARIDUSTASE_I,
            HARIDUSTASE_M, HARIDUSTASE_R);
    public static final List<String> DOCTOR = EnumUtil.toNameList(HARIDUSTASE_D);

}
