package ee.hitsa.ois.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SchoolDepartment extends BaseEntityWithId {

    private String nameEt;
    private String nameEn;
    private String code;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne
    private SchoolDepartment parentSchoolDepartment;
    private LocalDate validFrom;
    private LocalDate validThru;

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public SchoolDepartment getParentSchoolDepartment() {
        return parentSchoolDepartment;
    }

    public void setParentSchoolDepartment(SchoolDepartment parentSchoolDepartment) {
        this.parentSchoolDepartment = parentSchoolDepartment;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
}
