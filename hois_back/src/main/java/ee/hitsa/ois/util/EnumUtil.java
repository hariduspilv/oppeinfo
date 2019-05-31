package ee.hitsa.ois.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Enums;

import ee.hitsa.ois.domain.Classifier;

public abstract class EnumUtil {

    /**
     * Returns list of enum names. Guaranteed to return ArrayList.
     * @param values
     * @return
     */
    public static List<String> toNameList(Enum<?>... values) {
        return Arrays.stream(values).map(Enum::name).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns set of enum names. Guaranteed to return HashSet.
     * @param values
     * @return
     */
    public static Set<String> toNameSet(Enum<?>... values) {
        return Arrays.stream(values).map(Enum::name).collect(Collectors.toCollection(HashSet::new));
    }
    
    /**
     * Returns an enum or null in case if no value has been found.
     * @param enumClass
     * @param name
     * @return Enum
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumClass, String name) {
        return Enums.getIfPresent(enumClass, name).orNull();
    }

    public static <T extends Enum<T>> T valueOf(Class<T> enumClass, Classifier classifier) {
        if (classifier == null) {
            return null;
        }
        return Enums.getIfPresent(enumClass, EntityUtil.getCode(classifier)).orNull();
    }
}
