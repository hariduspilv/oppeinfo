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

public class ClassifierUtil {

    public static final String COUNTRY_ESTONIA = "RIIK_EST";

    public static boolean isEstonia(Classifier riik) {
        return COUNTRY_ESTONIA.equals(EntityUtil.getCode(riik));
    }

    public static boolean equals(Enum<?> value, Classifier classifier) {
        return value.name().equals(EntityUtil.getNullableCode(classifier));
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
        private final Map<MainClassCode, Map<String, Classifier>> classifiers = new HashMap<>();

        public ClassifierCache(ClassifierRepository repository) {
            this.repository = repository;
        }

        public Classifier get(String value, MainClassCode mainClassCode, Boolean isCode) {
            // FIXME should fetch all values by mainClassCode with single query?
            Map<String, Classifier> cache = classifiers.computeIfAbsent(mainClassCode, key -> new HashMap<>());
            Classifier c = cache.get(value);
            if(c == null) {
                if(cache.containsKey(value)) {
                    return null;
                }
                c = StringUtils.hasText(value) ? getClassifier(value, mainClassCode, isCode) : null;
                cache.put(value, c);
            }
            return c;
        }

        private Classifier getClassifier(String valueOrCode, MainClassCode mainClassCode, Boolean isCode) {
            if (Boolean.TRUE.equals(isCode)) {
                return repository.getOne(valueOrCode);
            }
            return repository.findByValueAndMainClassCode(valueOrCode, mainClassCode.name());
        }

        public Classifier get(String value, MainClassCode mainClassCode) {
            return get(value, mainClassCode, Boolean.FALSE);
        }

        public Classifier getByCode(String value, MainClassCode mainClassCode) {
            return get(value, mainClassCode, Boolean.TRUE);
        }

        public Classifier getByEhisValue(String ehisValue, MainClassCode mainClassCode) {
            Map<String, Classifier> cache = classifiers.computeIfAbsent(mainClassCode, key -> new HashMap<>());
            Classifier c = cache.get(ehisValue);
            if(c == null) {
                if(cache.containsKey(ehisValue)) {
                    return null;
                }
                if(StringUtils.hasText(ehisValue)){
                    for(Classifier classifier : getClassifiersByMainCode(mainClassCode)) {
                        cache.put(classifier.getEhisValue(), classifier);
                    }
                }
                c = cache.get(ehisValue);
            }
            return c;
        }

        private List<Classifier> getClassifiersByMainCode(MainClassCode mainClassCode) {
            return repository.findAllByMainClassCode(mainClassCode.name());
        }
    }
}
