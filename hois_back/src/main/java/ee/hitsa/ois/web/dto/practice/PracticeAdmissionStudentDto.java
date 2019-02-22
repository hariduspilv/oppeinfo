package ee.hitsa.ois.web.dto.practice;

import java.time.LocalDate;

public class PracticeAdmissionStudentDto {

    private Long id;
    private String enterpriseName;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Long places;
    private Long submittedApplications;
    private String addInfo;
    private LocalDate submitDate;
    private String status;
    private String rejectReason;
    private Long contractId;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEnterpriseName() {
        return enterpriseName;
    }
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
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
    public Long getPlaces() {
        return places;
    }
    public void setPlaces(Long places) {
        this.places = places;
    }
    public Long getSubmittedApplications() {
        return submittedApplications;
    }
    public void setSubmittedApplications(Long submittedApplications) {
        this.submittedApplications = submittedApplications;
    }
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    public LocalDate getSubmitDate() {
        return submitDate;
    }
    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRejectReason() {
        return rejectReason;
    }
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
    public Long getContractId() {
        return contractId;
    }
    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
    
}
