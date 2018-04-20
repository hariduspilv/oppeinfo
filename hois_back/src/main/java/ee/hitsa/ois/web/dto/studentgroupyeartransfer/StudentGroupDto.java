package ee.hitsa.ois.web.dto.studentgroupyeartransfer;

import java.time.LocalDate;

public class StudentGroupDto {

    private Long id;
    private String oldCode;
    private String newCode;
    private Short oldCourse;
    private Short newCourse;
    private LocalDate validThru;
    private String curriculumName;
    private Long relatedStudents;
    private Long logId;
    private Long suitableStudents;
    private Long unsuitableStudents;
    private Boolean transfered;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getOldCode() {
        return oldCode;
    }
    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }
    
    public String getNewCode() {
        return newCode;
    }
    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public Short getOldCourse() {
        return oldCourse;
    }
    public void setOldCourse(Short oldCourse) {
        this.oldCourse = oldCourse;
    }
    
    public Short getNewCourse() {
        return newCourse;
    }
    public void setNewCourse(Short newCourse) {
        this.newCourse = newCourse;
    }
    
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public String getCurriculumName() {
        return curriculumName;
    }
    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }
    
    public Long getRelatedStudents() {
        return relatedStudents;
    }
    public void setRelatedStudents(Long relatedStudents) {
        this.relatedStudents = relatedStudents;
    }
    
    public Long getLogId() {
        return logId;
    }
    public void setLogId(Long logId) {
        this.logId = logId;
    }
    
    public Long getSuitableStudents() {
        return suitableStudents;
    }
    public void setSuitableStudents(Long suitableStudents) {
        this.suitableStudents = suitableStudents;
    }
    
    public Long getUnsuitableStudents() {
        return unsuitableStudents;
    }
    public void setUnsuitableStudents(Long unsuitableStudents) {
        this.unsuitableStudents = unsuitableStudents;
    }
    
    public Boolean getTransfered() {
        return transfered;
    }
    public void setTransfered(Boolean transfered) {
        this.transfered = transfered;
    }

}
