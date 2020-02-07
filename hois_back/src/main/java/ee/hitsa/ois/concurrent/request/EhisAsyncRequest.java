package ee.hitsa.ois.concurrent.request;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import ee.hitsa.ois.concurrent.AsyncRequest;
import ee.hitsa.ois.concurrent.WrapperCallable;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.FutureStatusResponse;

public class EhisAsyncRequest<V> extends AsyncRequest<V> {
    
    private final String user;
    private final Long schoolId;
    
    private String cancelledBy;
    
    /** Method reference for getting a wrapper */
    private final Supplier<AtomicReference<V>> wrapper;
    /** Method reference for getting a progress */
    private final Supplier<Float> progress;
    
    public EhisAsyncRequest(WrapperCallable<V> callable, String key, HoisUserDetails user) {
        super(callable, key);
        wrapper = callable::getWrapper;
        progress = callable::getProgress;
        this.user = user.getUsername();
        this.schoolId = user.getSchoolId();
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
    protected void fillIfInProgress(FutureStatusResponse response) {
        super.fillIfInProgress(response);
        response.setMessage("ehis.messages.requestInProgress");
    }
    
    @Override
    protected void fillIfCancelled(FutureStatusResponse response, CancellationException ex) {
        super.fillIfCancelled(response, ex);
        response.setCancelledBy(this.cancelledBy);
    }
    
    @Override
    protected void fillIfInterrupted(FutureStatusResponse response, InterruptedException ex) {
        super.fillIfInterrupted(response, ex);
        response.setCancelledBy(this.cancelledBy);
    }

    public String getUser() {
        return user;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public V getInterruptedResult() {
        return wrapper.get().get();
    }

    @Override
    public float getProgress() {
        return progress.get().floatValue();
    }
}
