package ee.hitsa.ois.web.dto.boardingschool;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class BoardingSchoolManagementDto {

    private Long student;
    private String fullname;
    private AutocompleteResult studentGroup;
    private String idcode;
    private String dormitory;
    private DormitoryDto latestDorm;
    private List<DormitoryDto> previousDorms = new ArrayList<>();

    private String studentStatus;
    private Boolean canEditLatest;
    private Boolean canAddNew;

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public DormitoryDto getLatestDorm() {
        return latestDorm;
    }

    public void setLatestDorm(DormitoryDto latestDorm) {
        this.latestDorm = latestDorm;
    }

    public List<DormitoryDto> getPreviousDorms() {
        return previousDorms;
    }

    public void setPreviousDorms(List<DormitoryDto> previousDorms) {
        this.previousDorms = previousDorms;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Boolean getCanEditLatest() {
        return canEditLatest;
    }

    public void setCanEditLatest(Boolean canEditLatest) {
        this.canEditLatest = canEditLatest;
    }

    public Boolean getCanAddNew() {
        return canAddNew;
    }

    public void setCanAddNew(Boolean canAddNew) {
        this.canAddNew = canAddNew;
    }

}
