package ee.hitsa.ois.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class StreamUtil {

    public static <K, T> Map<K, T> toMap(Function<T, K> keyMapper, Collection<T> data) {
        if(data == null) {
            return new HashMap<>();
        }
        return data.stream().collect(Collectors.toMap(keyMapper, it -> it));
    }

    public static <T, R> List<R> toMappedList(Function<T, R> mapper, Collection<T> data) {
        if(data == null) {
            return new ArrayList<>();
        }
        return data.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T, R> Set<R> toMappedSet(Function<T, R> mapper, Collection<T> data) {
        if(data == null) {
            return new HashSet<>();
        }
        return data.stream().map(mapper).collect(Collectors.toSet());
    }
}
