package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.enums.CertificateStatus;

public class CertificateSearchCommand {
    
    private List<String> type;
    private String headline;
    private String name;
//    @EstonianIdCode
    private String idcode;
    private String certificateNr;
    private LocalDate insertedFrom;
    private LocalDate insertedThru;
    private Long student;
    private CertificateStatus status;
    private List<Long> studentGroups;

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
    public LocalDate getInsertedFrom() {
        return insertedFrom;
    }
    public void setInsertedFrom(LocalDate insertedFrom) {
        this.insertedFrom = insertedFrom;
    }
    public LocalDate getInsertedThru() {
        return insertedThru;
    }
    public void setInsertedThru(LocalDate insertedThru) {
        this.insertedThru = insertedThru;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getStudent() {
        return student;
    }
    public void setStudent(Long student) {
        this.student = student;
    }
    public CertificateStatus getStatus() {
        return status;
    }
    public void setStatus(CertificateStatus status) {
        this.status = status;
    }
    public List<Long> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }
}
