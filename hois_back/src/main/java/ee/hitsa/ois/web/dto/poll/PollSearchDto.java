package ee.hitsa.ois.web.dto.poll;

import java.time.LocalDate;
import java.util.List;

public class PollSearchDto {
    
    private Long id;
    private String name;
    private String typeCode;
    private List<String> targetCodes;
    private LocalDate validFrom;
    private LocalDate validThru;
    private String status;
    private String insertedBy;
    private String changedBy;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<String> getTargetCodes() {
        return targetCodes;
    }
    public void setTargetCodes(List<String> targetCodes) {
        this.targetCodes = targetCodes;
    }
    public String getInsertedBy() {
        return insertedBy;
    }
    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }
    public String getChangedBy() {
        return changedBy;
    }
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

}
