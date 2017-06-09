package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.EntityUtil;

public class CertificateSearchDto {
    
    private Long id;
    private String type;
    private String certificateNr;
    private String headline;
    private String whom;
    private LocalDateTime inserted;
    private String studentFullname;
    private Long studentId;

    public static CertificateSearchDto of(Certificate certificate) {
        CertificateSearchDto dto = EntityUtil.bindToDto(certificate, new CertificateSearchDto(), "student");
        Student student = certificate.getStudent();
        if(student != null) {
            dto.setStudentFullname(student.getPerson().getFullname());
            dto.setStudentId(student.getId());
        } else {
            dto.setStudentFullname(certificate.getOtherName());
        }
        return dto;
    }
    
   
    public String getStudentFullname() {
        return studentFullname;
    }

    public void setStudentFullname(String studentFullname) {
        this.studentFullname = studentFullname;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
}
