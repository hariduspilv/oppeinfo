package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

public class PracticeJournalSearchDto {

    private Long id;
    private AutocompleteResult student;
    private String studentGroup;
    private AutocompleteResult module;
    private AutocompleteResult theme;
    private LocalDate startDate;
    private LocalDate endDate;
    private AutocompleteResult enterprise;
    private String enterpriseContactPersonName;
    private String practicePlace;
    private AutocompleteResult teacher;
    private LocalDate studentLastEntryDate;

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

    public AutocompleteResult getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(AutocompleteResult enterprise) {
        this.enterprise = enterprise;
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

    public LocalDate getStudentLastEntryDate() {
        return studentLastEntryDate;
    }

    public void setStudentLastEntryDate(LocalDate studentLastEntryDate) {
        this.studentLastEntryDate = studentLastEntryDate;
    }

}
