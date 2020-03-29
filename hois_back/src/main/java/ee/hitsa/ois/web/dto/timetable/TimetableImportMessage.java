package ee.hitsa.ois.web.dto.timetable;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class TimetableImportMessage extends NameAndCode {

    public enum ErrorLevel {
        NONE, WARNING, ERROR
    }

    private String uniid = UUID.randomUUID().toString();
    private Map<String, Object> params;
    private Boolean global = Boolean.TRUE;
    private ErrorLevel error = ErrorLevel.NONE;

    public TimetableImportMessage(String code) {
        super(code);
    }

    public TimetableImportMessage(String code, Map<String, Object> params) {
        this(code);
        this.params = params != null ? params : Collections.emptyMap();
    }

    public TimetableImportMessage(String code, Map<String, Object> params, ErrorLevel error) {
        this(code, params);
        this.error = error != null ? error : ErrorLevel.NONE;
    }

    public TimetableImportMessage(String code, Map<String, Object> params, ErrorLevel error, Boolean global) {
        this(code, params, error);
        this.global = global != null ? global : Boolean.TRUE;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public ErrorLevel getError() {
        return error;
    }

    public void setError(ErrorLevel error) {
        this.error = error;
    }

    public String getUniid() {
        return uniid;
    }

    public void setUniid(String uniid) {
        this.uniid = uniid;
    }
}
