package ee.hitsa.ois.concurrent;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.FutureTask;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.FutureStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.FutureStatusResponse;

@Transactional
@Service
public class AsyncManager {
    
    private static final int EXPIRATION_MINUTES = 60;
    
    /**
     * UUIDv4
     * The number of random version 4 UUIDs which need to be generated in order
     * to have a 50% probability of at least one collision is 2.71 quintillion.
     * 
     * @return UUID value
     */
    public String generateKey(HoisUserDetails user) {
        return UUID.randomUUID().toString() + "-" + user.getUsername().hashCode();
    }
    
    public <R> void createRequest(HoisUserDetails user, Integer type, String key, AsyncRequest<R> request) {
        AsyncMemoryManager.add(type, user.getSchoolId(), key, request);
    }

    @Async
    public <R> FutureTask<R> processRequest(AsyncRequest<R> request) {
        request.run();
        return request;
    }
    
    public FutureStatusResponse getState(HoisUserDetails user, Integer type, String key, boolean removeIfDone) {
        FutureStatusResponse response;
        Optional<AsyncRequest<?>> optRequest = AsyncMemoryManager.get(type, user.getSchoolId(), key);
        if (optRequest.isPresent()) {
            AsyncRequest<?> future = optRequest.get();
            future.setLastPollTime(LocalDateTime.now());
            response = future.generateResponse();
            if (removeIfDone && future.isDone()) {
                AsyncMemoryManager.remove(type, user.getSchoolId(), key);
            }
        } else {
            response = new FutureStatusResponse();
            response.setHasError(Boolean.TRUE);
            response.setStatus(FutureStatus.NOT_FOUND);
        }
        return response;
    }
    
    /**
     * 
     */
    @Scheduled(cron = "0 */15 * * * *")
    public void removeExpiredRequests() {
        AsyncMemoryManager.removeExpiredRequests(EXPIRATION_MINUTES);
    }
    
}
