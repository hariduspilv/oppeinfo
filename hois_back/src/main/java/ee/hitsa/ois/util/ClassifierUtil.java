package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.Classifier;

public class ClassifierUtil {

    private static final String ESTONIA = "RIIK_EST";

    public static boolean isEstonia(Classifier riik) {
        return ESTONIA.equals(EntityUtil.getCode(riik));
    }

    public static boolean equals(Enum<?> value, Classifier classifier) {
        return value.name().equals(EntityUtil.getNullableCode(classifier));
    }
}
