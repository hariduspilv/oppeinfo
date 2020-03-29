package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;
import java.util.List;

public class StudentCountCommand {
    
    private LocalDate from;
    private LocalDate thru;
    private List<String> studentTypes;
    private String resultType;
    private Boolean perStatus;
    private Boolean perSex;
    private Long ageFrom;
    private Long ageStep;
    
    public LocalDate getFrom() {
        return from;
    }
    public void setFrom(LocalDate from) {
        this.from = from;
    }
    public LocalDate getThru() {
        return thru;
    }
    public void setThru(LocalDate thru) {
        this.thru = thru;
    }
    public String getResultType() {
        return resultType;
    }
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
    public Boolean getPerStatus() {
        return perStatus;
    }
    public void setPerStatus(Boolean perStatus) {
        this.perStatus = perStatus;
    }
    public Boolean getPerSex() {
        return perSex;
    }
    public void setPerSex(Boolean perSex) {
        this.perSex = perSex;
    }
    public Long getAgeFrom() {
        return ageFrom;
    }
    public void setAgeFrom(Long ageFrom) {
        this.ageFrom = ageFrom;
    }
    public Long getAgeStep() {
        return ageStep;
    }
    public void setAgeStep(Long ageStep) {
        this.ageStep = ageStep;
    }
    public List<String> getStudentTypes() {
        return studentTypes;
    }
    public void setStudentTypes(List<String> studentTypes) {
        this.studentTypes = studentTypes;
    }

}
