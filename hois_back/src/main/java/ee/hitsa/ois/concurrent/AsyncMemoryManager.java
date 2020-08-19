package ee.hitsa.ois.concurrent;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class AsyncMemoryManager {

    public static final Integer EHIS_STUDENT = Integer.valueOf(0x00);
    public static final Integer EHIS_TEACHER = Integer.valueOf(0x01);
    public static final Integer POLL = Integer.valueOf(0x10);
    public static final Integer FOTOBOX = Integer.valueOf(0x11);
    public static final Integer OTHER = Integer.valueOf(0xFF);
    
    /**
     *  The first key - type
     *  The second key - school id
     *  The third key - request key
     *  Value - request object
     *  
     *  Values removed when:
     *  - Future is done and its status checked in {@link AsyncManager#getState(HoisUserDetails, String)}
     */
    private static final Map<Integer, Map<Long, Map<String, AsyncRequest<?>>>> REQUESTS = new ConcurrentHashMap<>();
    
    static {
        REQUESTS.put(EHIS_STUDENT, new ConcurrentHashMap<>());
        REQUESTS.put(EHIS_TEACHER, new ConcurrentHashMap<>());
        REQUESTS.put(POLL, new ConcurrentHashMap<>());
        REQUESTS.put(FOTOBOX, new ConcurrentHashMap<>());
        REQUESTS.put(OTHER, new ConcurrentHashMap<>());
    }
    
    private AsyncMemoryManager() { }
    
    public static void add(Integer type, Long schoolId, String key, AsyncRequest<?> request) {
        AssertionFailedException.throwIf(request == null, "Request cannot be null");
        if (!REQUESTS.containsKey(type)) {
            throw new IllegalArgumentException("This type[" + type + "] cannot be applied to memory");
        }
        if (!REQUESTS.get(type).containsKey(schoolId)) {
            REQUESTS.get(type).put(schoolId, new ConcurrentHashMap<>());
        }
        if (REQUESTS.get(type).get(schoolId).containsKey(key)) {
            throw new IllegalArgumentException("This key[" + key + "] has already been added in memory");
        }
        REQUESTS.get(type).get(schoolId).put(key, request);
    }
    
    public static Optional<AsyncRequest<?>> get(Integer type, Long schoolId, String key) {
        if (!REQUESTS.containsKey(type)) {
            return Optional.empty();
        }
        if (!REQUESTS.get(type).containsKey(schoolId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(REQUESTS.get(type).get(schoolId).get(key));
    }
    
    public static boolean remove(Integer type, Long schoolId, String key) {
        if (!REQUESTS.containsKey(type)) {
            return false;
        }
        if (!REQUESTS.get(type).containsKey(schoolId)) {
            return false;
        }
        return REQUESTS.get(type).get(schoolId).remove(key) != null;
    }
    
    public static Optional<AsyncRequest<?>> findAny(Integer type, Long schoolId,
            Function<Map<String, AsyncRequest<?>>, AsyncRequest<?>> fn) {
        if (!REQUESTS.containsKey(type)) {
            return Optional.empty();
        }
        if (!REQUESTS.get(type).containsKey(schoolId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(fn.apply(REQUESTS.get(type).get(schoolId)));
    }
    
    public static void removeExpiredRequests(int minutesAfterFinished) {
        LocalDateTime expirationLimit = LocalDateTime.now().minusMinutes(minutesAfterFinished);
        REQUESTS.values().stream().flatMap(m -> m.values().stream()).map(m -> m.values()).forEach(m -> m.removeIf(request -> {
            LocalDateTime ended = request.getEnded();
            if (ended != null && expirationLimit.isAfter(ended)) {
                return true;
            }
            return false;
        }));
    }
}
