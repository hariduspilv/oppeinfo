package ee.hitsa.ois.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    public static String exceptionToStackTraceString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     * Get root cause of exception.
     *
     * @param throwable
     * @return
     * @throws NullPointerException if throwable is null
     */
    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if(cause == null) {
            return throwable;
        }
        return getRootCause(cause);
    }
}
