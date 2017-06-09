package ee.hitsa.ois.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class StreamUtil {

    /**
     * Shortcut for collection to Map conversion. Key is determined using mapper, value is item.
     *
     * @param keyMapper
     * @param data can be null
     * @return
     */
    public static <K, T> Map<K, T> toMap(Function<T, K> keyMapper, Collection<T> data) {
        return toMap(keyMapper, it -> it, data);
    }

    /**
     * Shortcut for collection to Map conversion. Key and value both are determined using mappers.
     *
     * @param keyMapper
     * @param valueMapper
     * @param data can be null
     * @return
     */
    public static <K, V, T> Map<K, V> toMap(Function<T, K> keyMapper, Function<T, V> valueMapper, Collection<T> data) {
        if(data == null) {
            return new HashMap<>();
        }
        return data.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * Shortcut for mapping stream. Guaranteed to return ArrayList.
     *
     * @param mapper
     * @param data can be null
     * @return
     */
    public static <T, R> List<R> toMappedList(Function<T, R> mapper, Stream<T> data) {
        if(data == null) {
            return new ArrayList<>();
        }
        return data.map(mapper).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Shortcut for mapping stream. Guaranteed to return ArrayList.
     *
     * @param mapper
     * @param data can be null
     * @return
     */
    public static <T, R> List<R> toMappedList(Function<T, R> mapper, Collection<T> data) {
        if(data == null) {
            return new ArrayList<>();
        }
        return toMappedList(mapper, data.stream());
    }

    /**
     * Shortcut for mapping stream. Guaranteed to return HashSet.
     *
     * @param mapper
     * @param data can be null
     * @return
     */
    public static <T, R> Set<R> toMappedSet(Function<T, R> mapper, Collection<T> data) {
        if(data == null) {
            return new HashSet<>();
        }
        return data.stream().map(mapper).collect(Collectors.toCollection(HashSet::new));
    }

    public static <T> List<T> nullSafeList(List<T> data) {
        return data != null ? data : Collections.emptyList();
    }
}
