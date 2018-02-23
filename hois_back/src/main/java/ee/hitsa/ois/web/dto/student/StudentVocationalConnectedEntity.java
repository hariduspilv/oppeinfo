package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

public class StudentVocationalConnectedEntity {
    
    private Long entityId;
    private String type;
    private String nameEt;
    private String nameEn;
    private LocalDate date;
    private String yearCode;
    private String protocolNr;
    
    public StudentVocationalConnectedEntity(Long entityId, String type, String nameEt, String nameEn, LocalDate date,
            String yearCode, String protocolNr) {
        this.entityId = entityId;
        this.type = type;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.date = date;
        this.yearCode = yearCode;
        this.protocolNr = protocolNr;
    }

    public Long getEntityId() {
        return entityId;
    }
    
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getNameEt() {
        return nameEt;
    }
    
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    
    public String getNameEn() {
        return nameEn;
    }
    
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getYearCode() {
        return yearCode;
    }
    
    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public String getProtocolNr() {
        return protocolNr;
    }

    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }
    
}
