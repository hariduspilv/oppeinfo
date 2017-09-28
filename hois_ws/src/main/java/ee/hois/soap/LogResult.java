package ee.hois.soap;

public class LogResult<T> {

    private final LogContext log;
    private final T result;

    public LogResult(LogContext log, T result) {
        this.log = log;
        this.result = result;
    }

    public LogContext getLog() {
        return log;
    }

    public T getResult() {
        return result;
    }

    public boolean hasError() {
        return log != null && log.getError() != null;
    }
}
