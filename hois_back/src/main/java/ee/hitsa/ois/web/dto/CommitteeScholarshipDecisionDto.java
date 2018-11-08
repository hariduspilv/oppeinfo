package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class CommitteeScholarshipDecisionDto {

    private Long id;
    private String protocolNr;
    private LocalDate decided;
    private String scholarshipType;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProtocolNr() {
        return protocolNr;
    }
    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }
    
    public LocalDate getDecided() {
        return decided;
    }
    public void setDecided(LocalDate decided) {
        this.decided = decided;
    }
    
    public String getScholarshipType() {
        return scholarshipType;
    }
    public void setScholarshipType(String scholarshipType) {
        this.scholarshipType = scholarshipType;
    }
    
}
