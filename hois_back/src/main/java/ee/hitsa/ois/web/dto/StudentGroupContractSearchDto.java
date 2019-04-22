package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class StudentGroupContractSearchDto {
    
    private Long id;
    private AutocompleteResult student;
    private String contractNr;
    private String enterpriseContactPersonName;
    private AutocompleteResult teacher;
    private LocalDate confirmDate;
    private String status;
    private String studentGroup;
    private Boolean active;
    private LocalDate startDate;
    private LocalDate endDate;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public AutocompleteResult getStudent() {
        return student;
    }
    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }
    public String getContractNr() {
        return contractNr;
    }
    public void setContractNr(String contractNr) {
        this.contractNr = contractNr;
    }
    public String getEnterpriseContactPersonName() {
        return enterpriseContactPersonName;
    }
    public void setEnterpriseContactPersonName(String enterpriseContactPersonName) {
        this.enterpriseContactPersonName = enterpriseContactPersonName;
    }
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
    public LocalDate getConfirmDate() {
        return confirmDate;
    }
    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
