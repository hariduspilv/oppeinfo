package ee.hitsa.ois.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class StreamUtil {

    public static <K, T> Map<K, T> toMap(Function<T, K> keyMapper, Collection<T> data) {
        if(data == null) {
            return new HashMap<>();
        }
        return data.stream().collect(Collectors.toMap(keyMapper, it -> it));
    }
}
