package ee.hitsa.ois.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class CollectionUtil {

    public static <T> List<List<T>> partitionIntoEqualSubLists(List<T> collection, int parts) {
        List<List<T>> result = new ArrayList<>();
        if (collection == null) {
            return result;
        }

        int partSize = collection.size() / parts;
        int partsWithOverflow = collection.size() % parts;

        int lastAddedItemIndex = 0;
        for (int i = 0; i < parts; i++) {
            int currentPartSize = i < partsWithOverflow ? partSize + 1 : partSize;
            List<T> sublist = collection.subList(lastAddedItemIndex, lastAddedItemIndex + currentPartSize);
            result.add(sublist);
            lastAddedItemIndex += currentPartSize;
        }
        return result;
    }

    public static <T> List<List<T>> partitionIntoEqualSubLists(Set<T> collection, int parts) {
        return partitionIntoEqualSubLists(collection != null ? new ArrayList<>(collection) : new ArrayList<>(), parts);
    }
}
