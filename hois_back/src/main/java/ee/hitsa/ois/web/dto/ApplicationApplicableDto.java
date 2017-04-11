package ee.hitsa.ois.web.dto;

public class ApplicationApplicableDto {

    private Boolean isAllowed;
    private String reason;

    public ApplicationApplicableDto() {
        this.isAllowed = Boolean.TRUE;
    }

    public ApplicationApplicableDto(String reason) {
        this.isAllowed = Boolean.FALSE;
        this.reason = reason;
    }
    public Boolean getIsAllowed() {
        return isAllowed;
    }
    public void setIsAllowed(Boolean isAllowed) {
        this.isAllowed = isAllowed;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

}
