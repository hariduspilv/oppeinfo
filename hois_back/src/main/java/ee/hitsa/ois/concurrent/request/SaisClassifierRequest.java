package ee.hitsa.ois.concurrent.request;

import ee.hitsa.ois.concurrent.AsyncRequest;
import ee.hitsa.ois.concurrent.WrapperCallable;
import ee.hitsa.ois.web.dto.sais.SaisClassifierSearchDto;
import org.springframework.data.domain.Page;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class SaisClassifierRequest extends AsyncRequest<Void> {

    /** Method reference for getting a wrapper */
    private final Supplier<AtomicReference<Void>> wrapper;

    public SaisClassifierRequest(WrapperCallable<Void> callable, String key) {
        super(callable, key);
        wrapper = callable::getWrapper;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public float getProgress() {
        return 0;
    }

    @Override
    public Void getInterruptedResult() {
        return wrapper.get().get();
    }
}
