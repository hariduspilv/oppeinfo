package ee.hois.soap.dds.service;

public class GetStatusInfoResponse {

    private String status;
    private String statusCode;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    
}
