package ee.hitsa.ois.exception;

import java.util.Map;

public class SingleMessageWithParamsException extends RuntimeException {

    private Map<Object, Object> params;

    public SingleMessageWithParamsException() {
        super();
    }

    public SingleMessageWithParamsException(String message) {
        super(message);
    }

    public SingleMessageWithParamsException(String message, Map<Object, Object> params) {
        super(message);
        this.params = params;
    }

    public SingleMessageWithParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SingleMessageWithParamsException(Throwable cause) {
        super(cause);
    }

    public Map<Object, Object> getParams() {
        return params;
    }

    public void setParams(Map<Object, Object> params) {
        this.params = params;
    }

}
