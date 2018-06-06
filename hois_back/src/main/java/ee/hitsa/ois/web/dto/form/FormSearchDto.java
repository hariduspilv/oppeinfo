package ee.hitsa.ois.web.dto.form;

public class FormSearchDto {

    private String type;
    private String code;
    private Long from;
    private Long thru;
    private Long count;
    private String status;
    private String defectReason;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    
    public Long getFrom() {
        return from;
    }
    public void setFrom(Long from) {
        this.from = from;
    }
    
    public Long getThru() {
        return thru;
    }
    public void setThru(Long thru) {
        this.thru = thru;
    }
    
    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDefectReason() {
        return defectReason;
    }
    public void setDefectReason(String defectReason) {
        this.defectReason = defectReason;
    }
    
}
