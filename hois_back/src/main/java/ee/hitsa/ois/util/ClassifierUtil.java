package ee.hitsa.ois.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.web.dto.ClassifierSelection;

/**
 * Utility functions for working with classifiers
 */
public abstract class ClassifierUtil {

    public static final String COUNTRY_ESTONIA = "RIIK_EST";

    /**
     * Does given classifier represent country with value Estonia?
     * @param classifier can be null
     * @return
     */
    public static boolean isEstonia(Classifier classifier) {
        return COUNTRY_ESTONIA.equals(EntityUtil.getNullableCode(classifier));
    }

    /**
     * Is enum value equal to classifier?
     * @param value
     * @param classifier can be null
     * @return
     * @throws NullPointerException if value is null
     */
    public static boolean equals(Enum<?> value, Classifier classifier) {
        return value.name().equals(EntityUtil.getNullableCode(classifier));
    }

    /**
     * Is mainClassCode equal to classifier mainClassCode?
     * @param mainClassCode
     * @param classifier can be null
     * @return
     * @throws NullPointerException if mainClassCode is null
     */
    public static boolean mainClassCodeEquals(MainClassCode mainClassCode, Classifier classifier) {
        return classifier != null && mainClassCode.name().equals(classifier.getMainClassCode());
    }

    /**
     * Is classifer equal to one of enum values?
     * @param classifier
     * @param values
     * @return
     * @throws IllegalArgumentException if values are missing
     */
    public static boolean oneOf(Classifier classifier, Enum<?>... values) {
        if(values.length == 0) {
            throw new IllegalArgumentException("At least one value is required");
        }
        for(Enum<?> value : values) {
            if(equals(value, classifier)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find parent classifier for given mainClassCode
     *
     * @param child
     * @param mainClassCode
     * @return empty Optional if parent was not found
     */
    public static Optional<Classifier> parentFor(Classifier child, MainClassCode mainClassCode) {
        return parentLinkFor(child, mainClassCode).map(r -> r.getConnectClassifier());
    }

    /**
     * Find link to parent classifier for given mainClassCode
     * @param child
     * @param mainClassCode
     * @return empty Optional if parent link was not found
     */
    public static Optional<ClassifierConnect> parentLinkFor(Classifier child, MainClassCode mainClassCode) {
        if(child == null) {
            return Optional.empty();
        }
        // find matching parent link
        return child.getClassifierConnects().stream()
                .filter(r -> mainClassCode.name().equals(r.getMainClassifierCode()))
                .findAny();
    }

    public static List<ClassifierSelection> sort(List<String> mainClassCodes, List<ClassifierSelection> data) {
        if(mainClassCodes.size() == 1) {
            String mainClassCode = mainClassCodes.get(0);
            Comparator<ClassifierSelection> c = CLASSIFIER_SORT.get(mainClassCode);
            if(c != null) {
                data.sort(c);
            }
        }
        return data;
    }

    /**
     * Merge functions for Collectors.toMap. Chooses first valid classifier.
     *
     * @param o
     * @param n
     * @return
     */
    public static Classifier validOne(Classifier o, Classifier n) {
        return o.isValid() ? o : n;
    }

    // sorters for classifiers
    private static final Map<String, Comparator<ClassifierSelection>> CLASSIFIER_SORT = new HashMap<>();
    static {
        CLASSIFIER_SORT.put(MainClassCode.MAHT.name(), (a, b) -> {
            return String.CASE_INSENSITIVE_ORDER.compare(a.getCode(), b.getCode());
        });
        CLASSIFIER_SORT.put(MainClassCode.OIGUS.name(), (a, b) -> {
            return Permission.valueOf(a.getCode()).ordinal() - Permission.valueOf(b.getCode()).ordinal();
        });
    }

    public static String getNullableNameEt(Classifier classifier) {
        return classifier != null ? classifier.getNameEt() : null;
    }

    public static class ClassifierCache {
        private final ClassifierService repository;
        private final Map<MainClassCode, List<Classifier>> classifiers = new HashMap<>();
        private final Map<MainClassCode, Map<String, Classifier>> byCode = new HashMap<>();
        private final Map<MainClassCode, Map<String, Classifier>> byEhisValue = new HashMap<>();
        private final Map<MainClassCode, Map<String, Classifier>> byValue = new HashMap<>();

        public ClassifierCache(ClassifierService repository) {
            this.repository = repository;
        }

        public List<Classifier> getAll(MainClassCode mainClassCode) {
            loadClassifiers(mainClassCode);
            return classifiers.get(mainClassCode);
        }

        public Classifier getByValue(String value, MainClassCode mainClassCode) {
            return get(value, mainClassCode, byValue);
        }

        public Classifier getByCode(String code, MainClassCode mainClassCode) {
            return get(code, mainClassCode, byCode);
        }

        public Classifier getByEhisValue(String ehisValue, MainClassCode mainClassCode) {
            return get(ehisValue, mainClassCode, byEhisValue);
        }

        private Classifier get(String cacheKey, MainClassCode mainClassCode, Map<MainClassCode, Map<String, Classifier>> cacheMap) {
            loadClassifiers(mainClassCode);
            Map<String, Classifier> cache = cacheMap.get(mainClassCode);
            return cache.get(cacheKey);
        }

        private void loadClassifiers(MainClassCode mainClassCode) {
            if(classifiers.containsKey(mainClassCode)) {
                return;
            }

            List<Classifier> records = repository.findAllByMainClassCode(mainClassCode);
            // FIXME sorting?
            classifiers.put(mainClassCode, records);

            Map<String, Classifier> code = new HashMap<>();
            Map<String, Classifier> ehisValue = new HashMap<>();
            Map<String, Classifier> value = new HashMap<>();

            for(Classifier c : records) {
                code.put(c.getCode(), c);
                if(StringUtils.hasText(c.getEhisValue())) {
                    ehisValue.put(c.getEhisValue(), c);
                }
                value.put(c.getValue(), c);
            }

            byCode.put(mainClassCode, code);
            byEhisValue.put(mainClassCode, ehisValue);
            byValue.put(mainClassCode, value);
        }
    }
}
