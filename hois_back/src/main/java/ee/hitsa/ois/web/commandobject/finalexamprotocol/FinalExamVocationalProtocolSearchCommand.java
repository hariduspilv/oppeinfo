package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class FinalExamVocationalProtocolSearchCommand {

    private Long studyYear;
    private Long teacher;
    private Long curriculumVersion;
    private List<Long> module;
    @ClassifierRestriction(MainClassCode.PROTOKOLL_STAATUS)
    private String status;
    private String protocolNr;
    private LocalDate confirmDateFrom;
    private LocalDate confirmDateThru;
    private LocalDate insertedFrom;
    private LocalDate insertedThru;
    
    public Long getStudyYear() {
        return studyYear;
    }
    
    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }
    
    public Long getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    
    public Long getCurriculumVersion() {
        return curriculumVersion;
    }
    
    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    
    public List<Long> getModule() {
        return module;
    }
    
    public void setModule(List<Long> module) {
        this.module = module;
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
    
}
