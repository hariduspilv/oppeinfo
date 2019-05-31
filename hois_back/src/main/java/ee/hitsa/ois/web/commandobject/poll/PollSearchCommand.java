package ee.hitsa.ois.web.commandobject.poll;

import java.time.LocalDate;

public class PollSearchCommand {
    
    private String pollName;
    private LocalDate validFrom;
    private LocalDate validThru;
    private String typeCode;
    private String statusCode;
    private String targetCode;
    
    public String getPollName() {
        return pollName;
    }
    public void setPollName(String pollName) {
        this.pollName = pollName;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    public String getTargetCode() {
        return targetCode;
    }
    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }
}