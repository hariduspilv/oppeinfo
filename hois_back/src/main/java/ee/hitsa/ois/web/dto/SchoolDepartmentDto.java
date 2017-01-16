package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentForm;

public class SchoolDepartmentDto extends SchoolDepartmentForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static SchoolDepartmentDto of(SchoolDepartment schoolDepartment) {
        return EntityUtil.bindToDto(schoolDepartment, new SchoolDepartmentDto());
    }
}
