package ee.hitsa.ois.concurrent.request;

import ee.hitsa.ois.concurrent.AsyncRequest;
import ee.hitsa.ois.concurrent.WrapperCallable;
import ee.hitsa.ois.web.dto.sais.SaisApplicationImportResultDto;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class SaisApplicationRequest extends AsyncRequest<SaisApplicationImportResultDto> {

    /** Method reference for getting a wrapper */
    private final Supplier<AtomicReference<SaisApplicationImportResultDto>> wrapper;
    /** Method reference for getting a progress */
    private final Supplier<Float> progress;

    public SaisApplicationRequest(WrapperCallable<SaisApplicationImportResultDto> callable, String key) {
        super(callable, key);
        wrapper = callable::getWrapper;
        progress = callable::getProgress;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public float getProgress() {
        return progress.get();
    }

    @Override
    public SaisApplicationImportResultDto getInterruptedResult() {
        return wrapper.get().get();
    }
}
