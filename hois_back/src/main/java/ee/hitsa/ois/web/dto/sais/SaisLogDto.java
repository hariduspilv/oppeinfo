package ee.hitsa.ois.web.dto.sais;

import java.time.LocalDateTime;

public class SaisLogDto {

    private final Long id;
    private final String wsName;
    private String request;
    private String response;
    private final LocalDateTime inserted;
    private final String insertedBy;
    private final Boolean error;
    private String logTxt;

    public SaisLogDto(Long id, String wsName, LocalDateTime inserted, String insertedBy, Boolean error) {
        this.id = id;
        this.wsName = wsName;
        this.inserted = inserted;
        this.insertedBy = insertedBy;
        this.error = error;
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

    public void setLogTxt(String logTxt) {
        this.logTxt = logTxt;
    }

    public String getLogTxt() {
        return logTxt;
    }
}
