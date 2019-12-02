package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

import ee.hitsa.ois.enums.FutureStatus;

public class FutureStatusResponse {

    private FutureStatus status;
    private Float progress;
    private Object result;
    private Boolean hasError;
    private String error;
    private String message;
    private String cancelledBy; 
    private LocalDateTime started;
    private LocalDateTime ended;
    
    public FutureStatus getStatus() {
        return status;
    }

    public void setStatus(FutureStatus status) {
        this.status = status;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getEnded() {
        return ended;
    }

    public void setEnded(LocalDateTime ended) {
        this.ended = ended;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
