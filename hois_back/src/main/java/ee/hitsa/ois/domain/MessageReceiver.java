package ee.hitsa.ois.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MessageReceiver extends BaseEntityWithId {

    private LocalDateTime read;
    
    @ManyToOne(optional = false)
    private Classifier status;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false, insertable = true)    
    private Message message;
    
    @ManyToOne(optional = false)
    private Person person;

    public LocalDateTime getRead() {
        return read;
    }

    public void setRead(LocalDateTime read) {
        this.read = read;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
