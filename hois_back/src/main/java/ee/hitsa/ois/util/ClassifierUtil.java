package ee.hitsa.ois.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.web.dto.ClassifierSelection;

/**
 * Utility functions for working with classifiers
 */
public class ClassifierUtil {

    public static final String COUNTRY_ESTONIA = "RIIK_EST";

    public static boolean isEstonia(Classifier riik) {
        return COUNTRY_ESTONIA.equals(EntityUtil.getCode(riik));
    }

    public static boolean equals(Enum<?> value, Classifier classifier) {
        return value.name().equals(EntityUtil.getNullableCode(classifier));
    }

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

    // sorters for classifiers
    // this needs to be alphabetical not ordinal
    private static final Map<String, Comparator<ClassifierSelection>> CLASSIFIER_SORT = new HashMap<>();
    static {
        CLASSIFIER_SORT.put(MainClassCode.MAHT.name(), (a, b) -> {
            return a.getCode().toUpperCase().compareTo(b.getCode().toUpperCase());
        });
    }

    public static class ClassifierCache {
        private final ClassifierRepository repository;
        private final Map<MainClassCode, List<Classifier>> classifiers = new HashMap<>();
        private final Map<MainClassCode, Map<String, Classifier>> byCode = new HashMap<>();
        private final Map<MainClassCode, Map<String, Classifier>> byEhisValue = new HashMap<>();
        private final Map<MainClassCode, Map<String, Classifier>> byValue = new HashMap<>();

        public ClassifierCache(ClassifierRepository repository) {
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

            List<Classifier> records = repository.findAllByMainClassCode(mainClassCode.name());
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
