package ee.hitsa.ois.web.dto.finalprotocol;

public class FinalProtocolStudentOccupationDto {

    private String certificateNr;
    private String occupationCode;
    private String partOccupationCode;
    private String specialityCode;
    private Long studentOccupationCertificateId;
    private String language;

    public FinalProtocolStudentOccupationDto(String certificateNr, String occupationCode, String partOccupationCode,
            String specialityCode, Long studentOccupationCertificateId, String language) {
        this.certificateNr = certificateNr;
        this.occupationCode = occupationCode;
        this.partOccupationCode = partOccupationCode;
        this.specialityCode = specialityCode;
        this.studentOccupationCertificateId = studentOccupationCertificateId;
        this.language = language;
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

    public String getSpecialityCode() {
        return specialityCode;
    }

    public void setSpecialityCode(String specialityCode) {
        this.specialityCode = specialityCode;
    }

    public Long getStudentOccupationCertificateId() {
        return studentOccupationCertificateId;
    }

    public void setStudentOccupationCertificateId(Long studentOccupationCertificateId) {
        this.studentOccupationCertificateId = studentOccupationCertificateId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
