package ee.hitsa.ois.web.commandobject.finalprotocol;

import java.time.LocalDate;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "confirmDateFrom", thru = "confirmDateThru")
@DateRange(from = "insertedFrom", thru = "insertedThru")
public class FinalHigherProtocolSearchCommand {

    private String studentName;
    private Boolean isFinalThesis;
    @ClassifierRestriction(MainClassCode.PROTOKOLL_STAATUS)
    private String status;
    private String protocolNr;
    private Long subject;
    private LocalDate insertedFrom;
    private LocalDate insertedThru;
    private LocalDate confirmDateFrom;
    private LocalDate confirmDateThru;
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public Boolean getIsFinalThesis() {
        return isFinalThesis;
    }
    
    public void setIsFinalThesis(Boolean isFinalThesis) {
        this.isFinalThesis = isFinalThesis;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getProtocolNr() {
        return protocolNr;
    }
    
    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }
    
    public Long getSubject() {
        return subject;
    }
    
    public void setSubject(Long subject) {
        this.subject = subject;
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

    public LocalDate getConfirmDateFrom() {
        return confirmDateFrom;
    }
    
    public void setConfirmDateFrom(LocalDate confirmDateFrom) {
        this.confirmDateFrom = confirmDateFrom;
    }
    
    public LocalDate getConfirmDateThru() {
        return confirmDateThru;
    }
    
    public void setConfirmDateThru(LocalDate confirmDateThru) {
        this.confirmDateThru = confirmDateThru;
    }
    
}
