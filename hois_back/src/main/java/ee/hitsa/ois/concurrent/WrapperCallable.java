package ee.hitsa.ois.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Callable. Has a wrapper to be able to access in case of canceling or interrupting.
 * Supports progress.
 *
 * @param <V>
 */
public abstract class WrapperCallable<V> implements Callable<V> {
    
    private final AtomicReference<V> wrapper = new AtomicReference<>();
    
    public abstract V wrapperCall();
    public abstract float getProgress();

    @Override
    public V call() throws Exception {
        wrapper.set(wrapperCall());
        return wrapper.get();
    }

    public AtomicReference<V> getWrapper() {
        return wrapper;
    }
    
}
