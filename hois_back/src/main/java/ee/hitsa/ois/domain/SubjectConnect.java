package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SubjectConnect extends BaseEntityWithId {

    @ManyToOne(optional = false)
    private Subject primarySubject;

    @ManyToOne(optional = false)
    private Subject connectSubject;

    @ManyToOne
    private Classifier connection;

    public SubjectConnect() { }

    public SubjectConnect(Subject subject, Subject connect, Classifier connectionType) {
        setPrimarySubject(subject);
        setConnectSubject(connect);
        setConnection(connectionType);
    }

    public Classifier getConnection() {
        return connection;
    }

    public void setConnection(Classifier connection) {
        this.connection = connection;
    }

    public Subject getConnectSubject() {
        return connectSubject;
    }

    public void setConnectSubject(Subject connectSubject) {
        this.connectSubject = connectSubject;
    }

    public Subject getPrimarySubject() {
        return primarySubject;
    }

    public void setPrimarySubject(Subject primarySubject) {
        this.primarySubject = primarySubject;
    }
}
