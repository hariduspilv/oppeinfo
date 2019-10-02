package ee.hitsa.ois.concurrent;

import java.time.LocalDateTime;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.PersonUtil;

public class EhisFutureTask<V> extends FutureTask<V> {
    
    /** Unique identificator */
    private final String hash;
    private final String user;
    private final Long schoolId;
    
    private LocalDateTime started;
    private LocalDateTime ended;
    
    private String cancelledBy;
    
    /** Method reference for getting a wrapper */
    private final Supplier<AtomicReference<V>> wrapper;
    /** Method reference for getting a progress */
    private final Supplier<Float> progress;
    
    public EhisFutureTask(WrapperCallable<V> callable, String hash,
            HoisUserDetails user) {
        super(callable);
        wrapper = callable::getWrapper;
        progress = callable::getProgress;
        this.hash = hash;
        this.user = user.getUsername();
        this.schoolId = user.getSchoolId();
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

    public V getWrappedResult() {
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
}
