package ee.hitsa.ois.concurrent;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.StampedLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hitsa.ois.enums.FutureStatus;
import ee.hitsa.ois.web.dto.FutureStatusResponse;

public abstract class AsyncRequest<R> extends FutureTask<R> {
    
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    protected final StampedLock sl = new StampedLock();

    /** Unique identifier */
    private final String key;
    private LocalDateTime started;
    private LocalDateTime ended;
    private LocalDateTime lastPollTime;

    public AsyncRequest(Callable<R> callable, String key) {
        super(callable);
        this.key = key;
    }
    
    public void before() { }
    public abstract String getMessage();
    public abstract float getProgress();
    public abstract R getInterruptedResult();

    public String getRequestKey() {
        return key;
    }
    
    protected void fillIfCancelled(FutureStatusResponse response, CancellationException ex) {
        response.setHasError(Boolean.TRUE);
        response.setError(ex.getMessage());
        response.setStatus(FutureStatus.CANCELLED);
        response.setResult(this.getInterruptedResult());
    }
    
    protected void fillIfInterrupted(FutureStatusResponse response, InterruptedException ex) {
        response.setHasError(Boolean.TRUE);
        response.setError(ex.getMessage());
        response.setStatus(FutureStatus.INTERRUPTED);
        response.setResult(this.getInterruptedResult());
    }
    
    protected void fillIfInProgress(FutureStatusResponse response) {
        response.setHasError(Boolean.FALSE);
        response.setStatus(FutureStatus.IN_PROGRESS);
    }
    
    protected void fillIfInQueue(FutureStatusResponse response) {
        response.setHasError(Boolean.FALSE);
        response.setStatus(FutureStatus.IN_QUEUE);
    }
    
    protected void fillIfExecutionException(FutureStatusResponse response, ExecutionException ex) {
        response.setHasError(Boolean.TRUE);
        response.setError(ex.getMessage());
        response.setStatus(FutureStatus.EXCEPTION);
        response.setResult(this.getInterruptedResult());
    }
    
    protected void fillIfDone(FutureStatusResponse response) throws InterruptedException, ExecutionException {
        response.setResult(this.get());
        response.setHasError(Boolean.FALSE);
        response.setStatus(FutureStatus.DONE);
    }
    
    /**
     * TODO: The last request can be not included because its `done` is true once interrupted.
     * 
     * @return
     */
    public FutureStatusResponse generateResponse() {
        FutureStatusResponse response = new FutureStatusResponse();
        response.setStarted(this.getStarted());
        response.setEnded(this.getEnded());
        response.setProgress(Float.valueOf(this.getProgress()));
        response.setMessage(this.getMessage());
        if (this.isDone()) {
            try {
                fillIfDone(response);
            } catch (ExecutionException ex) {
                LOG.error("Error during async request completetion", ex);
                fillIfExecutionException(response, ex);
            } catch (CancellationException ex) {
                fillIfCancelled(response, ex);
            } catch (InterruptedException ex) {
                fillIfInterrupted(response, ex);
            }
        } else {
            if (this.getStarted() == null) {
                fillIfInQueue(response);
            } else {
                fillIfInProgress(response);
            }
        }
        return response;
    }

    @Override
    protected void done() {
        long stamp = sl.writeLock();
        try {
            ended = LocalDateTime.now();
        } finally {
            sl.unlockWrite(stamp);
        }
        super.done();
    }

    @Override
    public void run() {
        long stamp = sl.writeLock();
        try {
            started = LocalDateTime.now();
        } finally {
            sl.unlockWrite(stamp);
        }
        super.run();
    }
    
    public LocalDateTime getStarted() {
        long stamp = sl.tryOptimisticRead();
        LocalDateTime ldt = started;
        if (!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                ldt = started;
            } finally {
                sl.unlock(stamp);
            }
        }
        return ldt;
    }
    
    public LocalDateTime getEnded() {
        long stamp = sl.tryOptimisticRead();
        LocalDateTime ldt = ended;
        if (!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                ldt = ended;
            } finally {
                sl.unlock(stamp);
            }
        }
        return ldt;
    }

    public LocalDateTime getLastPollTime() {
        long stamp = sl.tryOptimisticRead();
        LocalDateTime ldt = lastPollTime;
        if (!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                ldt = lastPollTime;
            } finally {
                sl.unlock(stamp);
            }
        }
        return ldt;
    }

    public void setLastPollTime(LocalDateTime lastPollTime) {
        long stamp = sl.writeLock();
        try {
            this.lastPollTime = lastPollTime;
        } finally {
            sl.unlockWrite(stamp);
        }
    }
}
