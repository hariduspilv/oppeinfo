package ee.hitsa.ois.domain.subject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class SubjectConnect extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Subject primarySubject;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Subject connectSubject;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier connection;

    public SubjectConnect() {
    }

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
