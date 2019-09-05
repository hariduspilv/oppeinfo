package ee.hitsa.ois.web.dto.poll;

import java.time.LocalDate;

public class PollPracticeJournalDto {
    
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String practiceDuration;
    private String enterpriseName;
    private String contractStudentName;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public String getPracticeDuration() {
        return practiceDuration;
    }
    public void setPracticeDuration(String practiceDuration) {
        this.practiceDuration = practiceDuration;
    }
    public String getEnterpriseName() {
        return enterpriseName;
    }
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    public String getContractStudentName() {
        return contractStudentName;
    }
    public void setContractStudentName(String contractStudentName) {
        this.contractStudentName = contractStudentName;
    }

}
