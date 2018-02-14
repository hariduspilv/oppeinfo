package ee.hitsa.ois.web.dto;

import java.util.List;

public class TeacherSearchDto {

    private Long id;
    private AutocompleteResult school;
    private String name;
    private String idcode;
    private String email;
    private String phone;
    private Boolean isActive;
    private AutocompleteResult teacherOccupation;
    private List<AutocompleteResult> schoolDepartments;
    private Boolean canEdit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public AutocompleteResult getTeacherOccupation() {
        return teacherOccupation;
    }

    public void setTeacherOccupation(AutocompleteResult teacherOccupation) {
        this.teacherOccupation = teacherOccupation;
    }

    public List<AutocompleteResult> getSchoolDepartments() {
        return schoolDepartments;
    }

    public void setSchoolDepartments(List<AutocompleteResult> schoolDepartments) {
        this.schoolDepartments = schoolDepartments;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
}
