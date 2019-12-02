package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.curriculum.Curriculum;

@Entity
public class UserCurriculum extends BaseEntityWithId {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Curriculum curriculum;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
