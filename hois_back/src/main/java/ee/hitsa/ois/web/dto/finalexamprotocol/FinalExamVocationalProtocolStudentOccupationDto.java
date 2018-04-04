package ee.hitsa.ois.web.dto.finalexamprotocol;

public class FinalExamVocationalProtocolStudentOccupationDto {

    private String certificateNr;
    private String occupationCode;
    private String partOccupationCode;
    private Long studentOccupationCertificateId;
    
    public FinalExamVocationalProtocolStudentOccupationDto(String certificateNr, String occupationCode,
            String partOccupationCode, Long studentOccupationCertificateId) {
        this.certificateNr = certificateNr;
        this.occupationCode = occupationCode;
        this.partOccupationCode = partOccupationCode;
        this.studentOccupationCertificateId = studentOccupationCertificateId;
    }

    public String getCertificateNr() {
        return certificateNr;
    }
    
    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }
    
    public String getOccupationCode() {
        return occupationCode;
    }
    
    public void setOccupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
    }
    
    public String getPartOccupationCode() {
        return partOccupationCode;
    }
    
    public void setPartOccupationCode(String partOccupationCode) {
        this.partOccupationCode = partOccupationCode;
    }
    
    public Long getStudentOccupationCertificateId() {
        return studentOccupationCertificateId;
    }
    
    public void setStudentOccupationCertificateId(Long studentOccupationCertificateId) {
        this.studentOccupationCertificateId = studentOccupationCertificateId;
    }
    
}
