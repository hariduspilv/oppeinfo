package ee.hitsa.ois.web.dto.scholarship;

import ee.hitsa.ois.validation.DateRange;

import java.time.LocalDate;

@DateRange(from = "applicationStart", thru = "applicationEnd")
public class ScholarshipApplicationSearchDto {

    private Long id;
    private Long studentId;
    private String studentName;
    private String studentGroup;
    private Long termId;
    private String termType;
    private String termNameEt;
    private LocalDate applicationStart;
    private LocalDate applicationEnd;
    private String applicationStatus;
    private LocalDate inserted;
    private Boolean canEdit;

    public ScholarshipApplicationSearchDto(Long id, Long studentId, String studentName, String studentGroup, Long termId,
            String termType, String termNameEt, LocalDate applicationStart, LocalDate applicationEnd,
            String applicationStatus, LocalDate inserted) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentGroup = studentGroup;
        this.termId = termId;
        this.termType = termType;
        this.termNameEt = termNameEt;
        this.applicationStart = applicationStart;
        this.applicationEnd = applicationEnd;
        this.applicationStatus = applicationStatus;
        this.inserted = inserted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getTermNameEt() {
        return termNameEt;
    }

    public void setTermNameEt(String termNameEt) {
        this.termNameEt = termNameEt;
    }

    public LocalDate getApplicationStart() {
        return applicationStart;
    }

    public void setApplicationStart(LocalDate applicationStart) {
        this.applicationStart = applicationStart;
    }

    public LocalDate getApplicationEnd() {
        return applicationEnd;
    }

    public void setApplicationEnd(LocalDate applicationEnd) {
        this.applicationEnd = applicationEnd;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LocalDate getInserted() {
        return inserted;
    }

    public void setInserted(LocalDate inserted) {
        this.inserted = inserted;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
}
