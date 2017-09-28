package ee.hitsa.ois.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class EnumUtil {

    public static List<String> toNameList(Enum<?>... values) {
        return Arrays.stream(values).map(Enum::name).collect(Collectors.toList()); 
    }

    public static Set<String> toNameSet(Enum<?>... values) {
        return Arrays.stream(values).map(Enum::name).collect(Collectors.toSet());
    }
}
