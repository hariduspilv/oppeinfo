package ee.hitsa.ois.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
}
