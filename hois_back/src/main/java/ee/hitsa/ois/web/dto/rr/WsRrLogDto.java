package ee.hitsa.ois.web.dto.rr;

import java.time.LocalDateTime;

public class WsRrLogDto {

    private Long id;
    private String wsName;
    private String logTxt;
    private Boolean error;
    private LocalDateTime inserted;
    private String insertedBy;
    private String request;
    private String response;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWsName() {
        return wsName;
    }
    public void setWsName(String wsName) {
        this.wsName = wsName;
    }
    public String getLogTxt() {
        return logTxt;
    }
    public void setLogTxt(String logTxt) {
        this.logTxt = logTxt;
    }
    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }
    public LocalDateTime getInserted() {
        return inserted;
    }
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
    public String getInsertedBy() {
        return insertedBy;
    }
    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
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
}
