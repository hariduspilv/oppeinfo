package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

public class RtipLogDto {

    private final Long id;
    private final String wsName;
    private String request;
    private String response;
    private final LocalDateTime inserted;
    private final String insertedBy;
    private final Boolean error;
    private final String logTxt;

    public RtipLogDto(Long id, String wsName, LocalDateTime inserted, String insertedBy, Boolean error, String logTxt) {
        this.id = id;
        this.wsName = wsName;
        this.inserted = inserted;
        this.insertedBy = insertedBy;
        this.error = error;
        this.logTxt = logTxt;
    }

    public Long getId() {
        return id;
    }

    public String getWsName() {
        return wsName;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public Boolean getError() {
        return error;
    }

    public String getLogTxt() {
        return logTxt;
    }
}
