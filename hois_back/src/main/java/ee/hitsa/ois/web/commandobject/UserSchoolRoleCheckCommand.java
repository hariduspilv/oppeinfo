package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.validation.Required;

public class UserSchoolRoleCheckCommand {

    private Long roleId;
    private Long schoolId;
    private Long teacherOccupationId;
    @Required
    private String nameEt;
    private String nameEn;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getTeacherOccupationId() {
        return teacherOccupationId;
    }

    public void setTeacherOccupationId(Long teacherOccupationId) {
        this.teacherOccupationId = teacherOccupationId;
    }

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
}
