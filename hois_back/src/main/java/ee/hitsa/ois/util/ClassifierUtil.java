package ee.hitsa.ois.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.CapacityType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.web.dto.ClassifierSelection;

public class ClassifierUtil {

    public static final String COUNTRY_ESTONIA = "RIIK_EST";

    public static boolean isEstonia(Classifier riik) {
        return COUNTRY_ESTONIA.equals(EntityUtil.getCode(riik));
    }

    public static boolean equals(Enum<?> value, Classifier classifier) {
        return value.name().equals(EntityUtil.getNullableCode(classifier));
    }

    public static void sort(String mainClassCode, List<ClassifierSelection> data) {
        Comparator<ClassifierSelection> c = CLASSIFIER_SORT.get(mainClassCode);
        if(c != null) {
            data.sort(c);
        }
    }

    // sorters for classifiers
    private static final Map<String, Comparator<ClassifierSelection>> CLASSIFIER_SORT = new HashMap<>();
    static {
        CLASSIFIER_SORT.put(MainClassCode.MAHT.name(), (a, b) -> {
            return CapacityType.valueOf(a.getCode()).ordinal() - CapacityType.valueOf(b.getCode()).ordinal();
        });
    }
}
