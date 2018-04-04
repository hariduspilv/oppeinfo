package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PracticeJournalSearchDto {

    private Long id;
    private AutocompleteResult student;
    private String studentGroup;
    private AutocompleteResult module;
    private AutocompleteResult theme;
    private LocalDate startDate;
    private LocalDate endDate;
    private String enterpriseName;
    private String enterpriseContactPersonName;
    private String practicePlace;
    private AutocompleteResult teacher;
    private LocalDateTime studentLastEntryDate;
    private Boolean canEdit;
    private Boolean canStudentAddEntries;
    private Boolean canAddEntries;
    private AutocompleteResult subject;

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

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public AutocompleteResult getTheme() {
        return theme;
    }

    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
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

    public String getEnterpriseContactPersonName() {
        return enterpriseContactPersonName;
    }

    public void setEnterpriseContactPersonName(String enterpriseContactPersonName) {
        this.enterpriseContactPersonName = enterpriseContactPersonName;
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

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanStudentAddEntries() {
        return canStudentAddEntries;
    }

    public void setCanStudentAddEntries(Boolean canStudentAddEntries) {
        this.canStudentAddEntries = canStudentAddEntries;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public Boolean getCanAddEntries() {
        return canAddEntries;
    }

    public void setCanAddEntries(Boolean canAddEntries) {
        this.canAddEntries = canAddEntries;
    }
}
