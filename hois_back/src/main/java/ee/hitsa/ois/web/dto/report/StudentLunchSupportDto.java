package ee.hitsa.ois.web.dto.report;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentLunchSupportDto {
    
    private Long studentId;
    private String fullname;
    private String idcode;
    private AutocompleteResult studentGroup;
    private String status;
    private Boolean isSchoolMeal;
    private LocalDateTime schoolMealChanged;
    private String schoolMealChangedBy;
    private LocalDate akadEnd;
    
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getIdcode() {
        return idcode;
    }
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Boolean getIsSchoolMeal() {
        return isSchoolMeal;
    }
    public void setIsSchoolMeal(Boolean isSchoolMeal) {
        this.isSchoolMeal = isSchoolMeal;
    }
    public LocalDateTime getSchoolMealChanged() {
        return schoolMealChanged;
    }
    public void setSchoolMealChanged(LocalDateTime schoolMealChanged) {
        this.schoolMealChanged = schoolMealChanged;
    }
    public String getSchoolMealChangedBy() {
        return schoolMealChangedBy;
    }
    public void setSchoolMealChangedBy(String schoolMealChangedBy) {
        this.schoolMealChangedBy = schoolMealChangedBy;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public LocalDate getAkadEnd() {
        return akadEnd;
    }
    public void setAkadEnd(LocalDate akadEnd) {
        this.akadEnd = akadEnd;
    }
}
