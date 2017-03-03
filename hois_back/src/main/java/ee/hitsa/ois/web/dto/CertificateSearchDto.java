package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

public class CertificateSearchDto {
    
    private Long id;
    private String type;
    private String certificateNr;
    private String headline;
    private String whom;
    private LocalDateTime inserted;
    private StudentSearchDto student;

    public static CertificateSearchDto of(Certificate certificate) {
        CertificateSearchDto dto = EntityUtil.bindToDto(certificate, new CertificateSearchDto(), "student");
        dto.setStudent(StudentSearchDto.of(certificate.getStudent()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCertificateNr() {
        return certificateNr;
    }

    public void setCertificateNr(String certificateNr) {
        this.certificateNr = certificateNr;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public StudentSearchDto getStudent() {
        return student;
    }

    public void setStudent(StudentSearchDto student) {
        this.student = student;
    }
}
