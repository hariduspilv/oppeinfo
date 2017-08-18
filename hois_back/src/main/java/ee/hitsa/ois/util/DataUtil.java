package ee.hitsa.ois.util;

import java.util.HashMap;
import java.util.Map;

public class DataUtil {

    public static Map<Object, Object> asMap(Object...objects) {
        Map<Object, Object> result = new HashMap<>();
        for (int i = 0; i < objects.length; i += 2) {
            result.put(objects[i], objects[i+1]);
        }
        return result;
    }

}
