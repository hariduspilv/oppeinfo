package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentForm;

public class SchoolDepartmentDto extends SchoolDepartmentForm {

    private Long id;
    private List<SchoolDepartmentDto> children;

    public SchoolDepartmentDto() {
    }

    public SchoolDepartmentDto(Long id, Long version, String code, String nameEt, String nameEn, LocalDate validFrom, LocalDate validThru, Long parentSchoolDepartmentId) {
        this.id = id;
        setVersion(version);
        setCode(code);
        setNameEt(nameEt);
        setNameEn(nameEn);
        setValidFrom(validFrom);
        setValidThru(validThru);
        setParentSchoolDepartmentId(parentSchoolDepartmentId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SchoolDepartmentDto> getChildren() {
        return children;
    }

    public void setChildren(List<SchoolDepartmentDto> children) {
        this.children = children;
    }

    public static SchoolDepartmentDto of(SchoolDepartment schoolDepartment) {
        return EntityUtil.bindToDto(schoolDepartment, new SchoolDepartmentDto());
    }
}
