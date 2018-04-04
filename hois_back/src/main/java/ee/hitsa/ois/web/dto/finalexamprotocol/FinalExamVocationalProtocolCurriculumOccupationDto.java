package ee.hitsa.ois.web.dto.finalexamprotocol;

public class FinalExamVocationalProtocolCurriculumOccupationDto {
    
    private Long id;
    private String occupation;
    private String partOccupation;
    private String certificateNr;
    
    public FinalExamVocationalProtocolCurriculumOccupationDto(Long id, String occupation, String partOccupation,
            String certificateNr) {
        this.id = id;
        this.occupation = occupation;
        this.partOccupation = partOccupation;
        this.certificateNr = certificateNr;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPartOccupation() {
        return partOccupation;
    }

    public void setPartOccupation(String partOccupation) {
        this.partOccupation = partOccupation;
    }

    public String getCertificateNr() {
        return certificateNr;
    }

    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }
    
}
