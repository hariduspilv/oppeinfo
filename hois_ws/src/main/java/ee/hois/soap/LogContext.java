package ee.hois.soap;

import java.time.LocalDateTime;

public class LogContext {

    private final String id;
    private final String queryName;
    private String outgoingXml;
    private String incomingXml;
    private final LocalDateTime queryStart;
    private LocalDateTime queryEnd;
    private Throwable error;
    private Long recordCount;

    public LogContext(String id, String queryName) {
        this.id = id;
        this.queryName = queryName;
        this.queryStart = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getQueryName() {
        return queryName;
    }

    public String getOutgoingXml() {
        return outgoingXml;
    }

    public void setOutgoingXml(String outgoingXml) {
        this.outgoingXml = outgoingXml;
    }

    public String getIncomingXml() {
        return incomingXml;
    }

    public void setIncomingXml(String incomingXml) {
        this.incomingXml = incomingXml;
    }

    public LocalDateTime getQueryStart() {
        return queryStart;
    }

    public LocalDateTime getQueryEnd() {
        return queryEnd;
    }

    public void setQueryEnd(LocalDateTime queryEnd) {
        this.queryEnd = queryEnd;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }
}
