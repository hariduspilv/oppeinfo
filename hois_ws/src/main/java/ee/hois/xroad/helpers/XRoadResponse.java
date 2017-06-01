package ee.hois.xroad.helpers;

import java.time.LocalDateTime;

public class XRoadResponse {

    private String queryName;
    private String xmlQuery;
    private String xmlResponse;
    private LocalDateTime queryStart;
    private LocalDateTime queryEnd;
    private Boolean xRoadErrors;
    private Boolean processingErrors;
    private String error;
    private Long recordCount;

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getXmlQuery() {
        return xmlQuery;
    }

    public void setXmlQuery(String xmlQuery) {
        this.xmlQuery = xmlQuery;
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    public void setXmlResponse(String xmlResponse) {
        this.xmlResponse = xmlResponse;
    }

    public LocalDateTime getQueryStart() {
        return queryStart;
    }

    public void setQueryStart(LocalDateTime queryStart) {
        this.queryStart = queryStart;
    }

    public LocalDateTime getQueryEnd() {
        return queryEnd;
    }

    public void setQueryEnd(LocalDateTime queryEnd) {
        this.queryEnd = queryEnd;
    }

    public Boolean getxRoadErrors() {
        return xRoadErrors;
    }

    public void setxRoadErrors(Boolean xRoadErrors) {
        this.xRoadErrors = xRoadErrors;
    }

    public Boolean getProcessingErrors() {
        return processingErrors;
    }

    public void setProcessingErrors(Boolean processingErrors) {
        this.processingErrors = processingErrors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

}
