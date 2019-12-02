package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class UserSchoolRoleRights extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserSchoolRole userSchoolRole;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier object;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier permission;
    
    public UserSchoolRole getUserSchoolRole() {
        return userSchoolRole;
    }

    public void setUserSchoolRole(UserSchoolRole userSchoolRole) {
        this.userSchoolRole = userSchoolRole;
    }

    public Classifier getObject() {
        return object;
    }

    public void setObject(Classifier object) {
        this.object = object;
    }

    public Classifier getPermission() {
        return permission;
    }

    public void setPermission(Classifier permission) {
        this.permission = permission;
    }
}
