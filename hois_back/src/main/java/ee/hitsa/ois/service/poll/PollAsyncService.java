package ee.hitsa.ois.service.poll;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.FutureStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.poll.PollResultStatisticsCommand;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import ee.hitsa.ois.web.dto.poll.PollResultStatisticsDto;

@Transactional
@Service
public class PollAsyncService extends PollService {
    
    private static final int MAX_THREADS = 100;
    
    /**
     * <schoolId <hashKey, object>>
     * ConcurrentHashMap should be thread safe
     * Contains objects from all schools
     */
    private static final Map<Long, Map<String, PollStatisticsRequest>> EXPORT_EXCEL_REQUESTS = new ConcurrentHashMap<>(MAX_THREADS);
    
    private static final long RESULT_EXPIRATION_MINUTES = 30;
    
    private static final long MAX_POLLING_INTERVAL_SECONDS = 30;
    
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    /**
     * Clean up requests
     * @param user user
     * @param key hash
     */
    public void deleteKey(HoisUserDetails user, String key) {
        EXPORT_EXCEL_REQUESTS.get(user.getSchoolId()).remove(key);
        if (EXPORT_EXCEL_REQUESTS.get(user.getSchoolId()).isEmpty()) {
            EXPORT_EXCEL_REQUESTS.remove(user.getSchoolId());
        }
    }
    
    
    public FutureStatusResponse exportExcelStatus(HoisUserDetails user, String key) {
        FutureStatusResponse response = new FutureStatusResponse();
        if (EXPORT_EXCEL_REQUESTS.containsKey(user.getSchoolId()) && EXPORT_EXCEL_REQUESTS.get(user.getSchoolId()).containsKey(key)) {
            PollStatisticsRequest future = EXPORT_EXCEL_REQUESTS.get(user.getSchoolId()).get(key);
            future.setLastPollTime(LocalDateTime.now());
            response.setProgress(Float.valueOf(future.getProgress()));
            response.setMessage(future.getCommand().get().getMessage());
            if (future.isDone()) {
                try {
                    response.setResult(future.get());
                    response.setHasError(Boolean.FALSE);
                    response.setStatus(FutureStatus.DONE);
                } catch (ExecutionException ex) {
                    LOG.info("Error during executing: " + ex.getMessage());
                    response.setHasError(Boolean.TRUE);
                    response.setError(ex.getMessage());
                    response.setStatus(FutureStatus.EXCEPTION);
                } catch (CancellationException | InterruptedException ex) {
                    // TODO: The last request can be not included because its `done` is true once interrupted.
                    response.setHasError(Boolean.TRUE);
                    response.setError(ex.getMessage());
                    response.setCancelledBy(future.getCancelledBy());
                    if (ex instanceof CancellationException) {
                        response.setStatus(FutureStatus.CANCELLED);
                    } else {
                        response.setStatus(FutureStatus.INTERRUPTED);
                    }
                    response.setResult(future.getWrappedResult());
                }
                response.setStarted(future.getStarted());
                response.setEnded(future.getEnded());
            } else {
                response.setHasError(Boolean.FALSE);
                response.setStatus(FutureStatus.IN_PROGRESS);
            }
        } else {
            response.setHasError(Boolean.TRUE);
            response.setStatus(FutureStatus.NOT_FOUND);
        }
        return response;
    }
    
    private static void removeExpiredExportExcelResults() {
        LocalDateTime expirationLimit = LocalDateTime.now().minusMinutes(RESULT_EXPIRATION_MINUTES);
        LocalDateTime pollingExpirationLimit = LocalDateTime.now().minusSeconds(MAX_POLLING_INTERVAL_SECONDS);
        EXPORT_EXCEL_REQUESTS.values().forEach(map -> {
            map.values().removeIf(request -> {
                // Clean up requests when someone closed browser in middle of polling for example
                if (pollingExpirationLimit.isAfter(request.getLastPollTime())) {
                    return true;
                }
                if (request.isDone()) {
                    LocalDateTime ended = request.getEnded(); // synchronized method
                    if (ended != null) {
                        return expirationLimit.isAfter(ended);
                    }
                    return false;
                }
                return false;
            });
        });
        EXPORT_EXCEL_REQUESTS.values().forEach(p -> System.out.println("Request after cleanup per school: " + p.size()));
    }
    
    @Async
    public void exportExcel(HoisUserDetails user, PollResultStatisticsCommand command, String requestHash) {
        Long schoolId = user.getSchoolId();
        PollStatisticsRequest request = new PollStatisticsRequest(new WrapperCallable<PollResultStatisticsDto>() {
            
            @Override
            public PollResultStatisticsDto wrapperCall() {
                PollResultStatisticsDto dto = new PollResultStatisticsDto(command);
                byte[] file = exportStatistics(user, command);
                dto.setFile(file);
                return dto;
            }
            
            @Override
            public PollResultStatisticsCommand getCommand() {
                return command;
            }

            @Override
            public float getProgress() {
                return 0;
            }
            
        }, requestHash, user, command);
        
        if (!EXPORT_EXCEL_REQUESTS.containsKey(schoolId)) {
            EXPORT_EXCEL_REQUESTS.put(schoolId, new ConcurrentHashMap<>());
        }
        System.out.println("Request size before adding: " + EXPORT_EXCEL_REQUESTS.get(schoolId).size());
        EXPORT_EXCEL_REQUESTS.get(schoolId).put(requestHash, request);
        
        request.run();
        
        removeExpiredExportExcelResults(); // Async method runs in different thread so it will not cause problems with response time.
    }
    
    /**
     * Being a Future.
     */
    public static class PollStatisticsRequest extends FutureTask<PollResultStatisticsDto> {
        /** Unique identificator */
        private final String hash;
        private final String user;
        private final Long schoolId;
        private final List<Long> pollIds;
        private final List<Long> questionIds;
        
        private LocalDateTime lastPollTime;
        private LocalDateTime started;
        private LocalDateTime ended;
        
        private String cancelledBy;
        
        /** Method reference for getting a wrapper */
        private final Supplier<AtomicReference<PollResultStatisticsDto>> wrapper;
        /** Method reference for getting a progress */
        private final Supplier<Float> progress;
        
        private final Supplier<PollResultStatisticsCommand> command;
        
        public PollStatisticsRequest(WrapperCallable<PollResultStatisticsDto> callable, String hash,
                HoisUserDetails user, PollResultStatisticsCommand command) {
            super(callable);
            wrapper = callable::getWrapper;
            progress = callable::getProgress;
            this.hash = hash;
            this.user = user.getUsername();
            this.schoolId = user.getSchoolId();
            this.pollIds = command.getPollIds();
            this.questionIds = command.getQuestions();
            this.lastPollTime = LocalDateTime.now();
            this.command = callable::getCommand;
        }
        
        @Override
        protected void done() {
            ended = LocalDateTime.now();
            super.done();
        }

        @Override
        public void run() {
            started = LocalDateTime.now();
            super.run();
        }
        
        /**
         * Sets a name of user who interrupted thread.
         * 
         * @param hoisUser user who interrupted request.
         * @param mayInterruptIfRunning
         * @return
         */
        public synchronized boolean cancel(HoisUserDetails hoisUser, boolean mayInterruptIfRunning) {
            cancelledBy = PersonUtil.stripIdcodeFromFullnameAndIdcode(hoisUser.getUsername());
            return cancel(mayInterruptIfRunning);
        }

        public PollResultStatisticsDto getWrappedResult() {
            return wrapper.get().get();
        }
        
        public float getProgress() {
            return progress.get().floatValue();
        }
        
        public String getRequestHash() {
            return hash;
        }

        public String getUser() {
            return user;
        }

        public Long getSchoolId() {
            return schoolId;
        }

        /**
         * LocalDateTime is immutable. Thread safe
         * 
         * @return time when task started
         */
        public LocalDateTime getStarted() {
            return started;
        }

        /**
         * LocalDateTime is immutable. Thread safe
         * 
         * @return time when task ended
         */
        public LocalDateTime getEnded() {
            return ended;
        }

        public synchronized String getCancelledBy() {
            return cancelledBy;
        }

        public List<Long> getPollIds() {
            return pollIds;
        }

        public List<Long> getQuestionIds() {
            return questionIds;
        }

        public LocalDateTime getLastPollTime() {
            return lastPollTime;
        }
        
        public void setLastPollTime(LocalDateTime lastPollTime) {
            this.lastPollTime = lastPollTime;
        }

        public Supplier<PollResultStatisticsCommand> getCommand() {
            return command;
        }
    }
    
    /**
     * Callable. Has a wrapper to be able to access in case of canceling or interrupting.
     * Supports progress.
     *
     * @param <V>
     */
    public static abstract class WrapperCallable<V> implements Callable<V> {
        
        private final AtomicReference<V> wrapper = new AtomicReference<>();
        
        public abstract V wrapperCall();
        public abstract float getProgress();
        public abstract PollResultStatisticsCommand getCommand();

        @Override
        public V call() throws Exception {
            wrapper.set(wrapperCall());
            return wrapper.get();
        }

        public AtomicReference<V> getWrapper() {
            return wrapper;
        }
        
    }
}
