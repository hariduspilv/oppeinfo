package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.Classifier;

public class ClassifierUtil {

    private static final String estonia = "RIIK_EST";

    public static boolean isEstonia(Classifier riik) {
        return estonia.equals(EntityUtil.getCode(riik));
    }
}
