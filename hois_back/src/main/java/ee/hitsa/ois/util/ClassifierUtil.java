package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.Classifier;

public class ClassifierUtil {

    public static final String COUNTRY_ESTONIA = "RIIK_EST";

    public static boolean isEstonia(Classifier riik) {
        return COUNTRY_ESTONIA.equals(EntityUtil.getCode(riik));
    }

    public static boolean equals(Enum<?> value, Classifier classifier) {
        return value.name().equals(EntityUtil.getNullableCode(classifier));
    }
}
