package ee.hitsa.ois.service.poll;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ee.hitsa.ois.concurrent.AsyncRequest;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.poll.PollResultStatisticsCommand;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import ee.hitsa.ois.web.dto.poll.PollResultStatisticsDto;

@Transactional
@Service
public class PollAsyncService extends PollService {
    
    public PollStatisticsRequest createRequest(HoisUserDetails user, PollResultStatisticsCommand command, String key) {
        return new PollStatisticsRequest(new WrapperCallable<PollResultStatisticsDto>() {
            
            @Override
            public PollResultStatisticsDto wrapperCall() throws InterruptedException {
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
            
        }, key, user, command);
    }
    
    /**
     * Being a Future.
     */
    public static class PollStatisticsRequest extends AsyncRequest<PollResultStatisticsDto> {
        private final String user;
        private final Long schoolId;
        private final List<Long> pollIds;
        private final List<Long> questionIds;
        
        private String cancelledBy;
        
        /** Method reference for getting a wrapper */
        private final Supplier<AtomicReference<PollResultStatisticsDto>> wrapper;
        /** Method reference for getting a progress */
        private final Supplier<Float> progress;
        
        private final Supplier<PollResultStatisticsCommand> command;
        
        public PollStatisticsRequest(WrapperCallable<PollResultStatisticsDto> callable, String key,
                HoisUserDetails user, PollResultStatisticsCommand command) {
            super(callable, key);
            wrapper = callable::getWrapper;
            progress = callable::getProgress;
            this.user = user.getUsername();
            this.schoolId = user.getSchoolId();
            this.pollIds = command.getPollIds();
            this.questionIds = command.getQuestions();
            this.command = callable::getCommand;
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
        
        @Override
        protected void fillIfInQueue(FutureStatusResponse response) {
            super.fillIfInQueue(response);
            response.setMessage("concurrent.requestInQueue");
        }
        
        @Override
        public float getProgress() {
            return progress.get().floatValue();
        }

        public String getUser() {
            return user;
        }

        public Long getSchoolId() {
            return schoolId;
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

        public Supplier<PollResultStatisticsCommand> getCommand() {
            return command;
        }

        @Override
        public String getMessage() {
            return command.get().getMessage();
        }

        @Override
        public PollResultStatisticsDto getInterruptedResult() {
            return wrapper.get().get();
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
        
        public abstract V wrapperCall() throws InterruptedException;
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
