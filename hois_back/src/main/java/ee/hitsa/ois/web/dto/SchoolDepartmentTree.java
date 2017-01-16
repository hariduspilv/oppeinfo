package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.List;

public class SchoolDepartmentTree {

    private final Long id;
    private final String code;
    private final String nameEt;
    private final String nameEn;
    private final LocalDate validFrom;
    private final LocalDate validThru;
    private final Long parentSchoolDepartmentId;
    private List<SchoolDepartmentTree> children;

    public SchoolDepartmentTree(Long id, String code, String nameEt, String nameEn, LocalDate validFrom, LocalDate validThru, Long parentSchoolDepartmentId) {
        this.id = id;
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.validFrom = validFrom;
        this.validThru = validThru;
        this.parentSchoolDepartmentId = parentSchoolDepartmentId;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public Long getParentSchoolDepartmentId() {
        return parentSchoolDepartmentId;
    }

    public List<SchoolDepartmentTree> getChildren() {
        return children;
    }

    public void setChildren(List<SchoolDepartmentTree> children) {
        this.children = children;
    }
}
