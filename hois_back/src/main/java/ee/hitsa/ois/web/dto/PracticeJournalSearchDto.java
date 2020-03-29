package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PracticeJournalSearchDto {

    private Long id;
    private AutocompleteResult student;
    private String studentGroup;
    private LocalDate startDate;
    private LocalDate endDate;
    private String enterpriseName;
    private String enterpriseSupervisors;
    private String practicePlace;
    private AutocompleteResult teacher;
    private LocalDateTime studentLastEntryDate;
    private String status;
    private Boolean canEdit;
    private Boolean canReopen;
    private Boolean canStudentAddEntries;
    private Boolean canAddEntries;
    private String contractStatus;
    private List<PracticeJournalSearchModuleSubjectDto> moduleSubjects;

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

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
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

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseSupervisors() {
        return enterpriseSupervisors;
    }

    public void setEnterpriseSupervisors(String enterpriseSupervisors) {
        this.enterpriseSupervisors = enterpriseSupervisors;
    }

    public String getPracticePlace() {
        return practicePlace;
    }

    public void setPracticePlace(String practicePlace) {
        this.practicePlace = practicePlace;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public LocalDateTime getStudentLastEntryDate() {
        return studentLastEntryDate;
    }

    public void setStudentLastEntryDate(LocalDateTime studentLastEntryDate) {
        this.studentLastEntryDate = studentLastEntryDate;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanReopen() {
        return canReopen;
    }

    public void setCanReopen(Boolean canReopen) {
        this.canReopen = canReopen;
    }

    public Boolean getCanStudentAddEntries() {
        return canStudentAddEntries;
    }

    public void setCanStudentAddEntries(Boolean canStudentAddEntries) {
        this.canStudentAddEntries = canStudentAddEntries;
    }

    public Boolean getCanAddEntries() {
        return canAddEntries;
    }

    public void setCanAddEntries(Boolean canAddEntries) {
        this.canAddEntries = canAddEntries;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public List<PracticeJournalSearchModuleSubjectDto> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(List<PracticeJournalSearchModuleSubjectDto> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }
    
}
