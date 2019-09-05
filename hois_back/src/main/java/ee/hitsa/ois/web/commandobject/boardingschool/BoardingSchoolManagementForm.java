package ee.hitsa.ois.web.commandobject.boardingschool;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardingSchoolManagementForm {

    private Long student;
    private String fullname;
    private String idcode;
    private DormitoryForm latestDorm;
    private List<DormitoryForm> previousDorms = new ArrayList<>();
    private String dormitory;
    private Boolean isLatestDorm;

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

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public DormitoryForm getLatestDorm() {
        return latestDorm;
    }

    public void setLatestDorm(DormitoryForm latestDorm) {
        this.latestDorm = latestDorm;
    }

    public List<DormitoryForm> getPreviousDorms() {
        return previousDorms;
    }

    public void setPreviousDorms(List<DormitoryForm> previousDorms) {
        this.previousDorms = previousDorms;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public Boolean getIsLatestDorm() {
        return isLatestDorm;
    }

    public void setIsLatestDorm(Boolean isLatestDorm) {
        this.isLatestDorm = isLatestDorm;
    }

}
