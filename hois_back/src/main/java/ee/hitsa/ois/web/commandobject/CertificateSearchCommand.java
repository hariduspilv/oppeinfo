package ee.hitsa.ois.web.commandobject;

import java.time.LocalDateTime;
import java.util.List;

public class CertificateSearchCommand {
    
    private List<String> type;
    private String headline;
    private String name;
//    @EstonianIdCode
    private String idcode;
    private String certificateNr;
    private LocalDateTime insertedFrom;
    private LocalDateTime insertedThru;

    public List<String> getType() {
        return type;
    }
    public void setType(List<String> type) {
        this.type = type;
    }
    public String getHeadline() {
        return headline;
    }
    public void setHeadline(String headLine) {
        this.headline = headLine;
    }
    public String getIdcode() {
        return idcode;
    }
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    public String getCertificateNr() {
        return certificateNr;
    }
    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }
    public LocalDateTime getInsertedFrom() {
        return insertedFrom;
    }
    public void setInsertedFrom(LocalDateTime insertedFrom) {
        this.insertedFrom = insertedFrom;
    }
    public LocalDateTime getInsertedThru() {
        return insertedThru;
    }
    public void setInsertedThru(LocalDateTime insertedThru) {
        this.insertedThru = insertedThru;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
