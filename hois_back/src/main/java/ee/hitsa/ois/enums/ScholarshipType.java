package ee.hitsa.ois.enums;

import ee.hitsa.ois.util.EnumUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ScholarshipType {
    STIPTOETUS_DOKTOR,
    STIPTOETUS_SOIDU,
    STIPTOETUS_ERI,
    STIPTOETUS_POHI,
    STIPTOETUS_MUU,
    STIPTOETUS_TULEMUS,
    STIPTOETUS_ERIALA;

    public static final List<String> GRANTS = EnumUtil.toNameList(STIPTOETUS_POHI, STIPTOETUS_ERI, STIPTOETUS_SOIDU);

    public static final List<String> SCHOLARSHIPS = EnumUtil.toNameList(STIPTOETUS_TULEMUS, STIPTOETUS_ERIALA, STIPTOETUS_MUU);

    public static final List<String> DR_GRANTS = EnumUtil.toNameList(STIPTOETUS_DOKTOR);

    public static final Map<String, List<String>> STIPEND_TYPES = new HashMap<>();
    static {
        STIPEND_TYPES.put("grants", GRANTS);
        STIPEND_TYPES.put("scholarships", SCHOLARSHIPS);
        STIPEND_TYPES.put("drGrants", DR_GRANTS);
    }

}
