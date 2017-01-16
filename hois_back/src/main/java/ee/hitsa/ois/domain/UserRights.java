package ee.hitsa.ois.domain;

import javax.persistence.*;

@Entity
public class UserRights extends BaseEntityWithId {

    @ManyToOne
    private User user;

    @ManyToOne
    private Classifier permission;

    @ManyToOne
    private Classifier object;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Classifier getPermission() {
        return permission;
    }

    public void setPermission(Classifier permission) {
        this.permission = permission;
    }

    public Classifier getObject() {
        return object;
    }

    public void setObject(Classifier object) {
        this.object = object;
    }
}
