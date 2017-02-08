package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.NotEmpty;

@DateRange
public class SchoolDepartmentForm extends VersionedCommand {

    @NotEmpty
    @Size(max = 255)
    private String nameEt;
    @Size(max = 255)
    private String nameEn;
    @NotEmpty
    @Size(max = 50)
    private String code;
    @NotNull
    private LocalDate validFrom;
    private LocalDate validThru;
    private Long parentSchoolDepartment;

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

    public Long getParentSchoolDepartment() {
        return parentSchoolDepartment;
    }

    public void setParentSchoolDepartment(Long parentSchoolDepartment) {
        this.parentSchoolDepartment = parentSchoolDepartment;
    }
}
