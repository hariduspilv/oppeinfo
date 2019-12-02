package ee.hitsa.ois.web.commandobject;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

/**
 * schoolId is not necessary as only school admin can create. School is taken from admin user.
 * 
 */
public class UserSchoolRoleForm extends VersionedCommand {

    private EntityConnectionCommand teacherOccupation;
    @Required
    @Size(max=15)
    private String nameEt;
    @Size(max=15)
    private String nameEn;
    private Map<String, List<String>> rights;
    
    private Boolean overwriteRights = Boolean.FALSE;
    private Boolean deleteUserRights = Boolean.FALSE;
    
    public EntityConnectionCommand getTeacherOccupation() {
        return teacherOccupation;
    }

    public void setTeacherOccupation(EntityConnectionCommand teacherOccupation) {
        this.teacherOccupation = teacherOccupation;
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

    public Map<String, List<String>> getRights() {
        return rights;
    }

    public void setRights(Map<String, List<String>> rights) {
        this.rights = rights;
    }

    public Boolean getOverwriteRights() {
        return overwriteRights;
    }

    public void setOverwriteRights(Boolean overwriteRights) {
        this.overwriteRights = overwriteRights;
    }

    public Boolean getDeleteUserRights() {
        return deleteUserRights;
    }

    public void setDeleteUserRights(Boolean deleteUserRights) {
        this.deleteUserRights = deleteUserRights;
    }
}
