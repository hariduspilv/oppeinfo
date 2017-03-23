package ee.hitsa.ois.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.util.EntityUtil;

@Entity
public class Message extends BaseEntityWithId {

    private String subject;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_code")
    private Classifier sendersRole;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School sendersSchool;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message responseTo;
    
    @OneToMany(mappedBy = "responseTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> responses;
    
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MessageReceiver> receivers;
    
    @Transient
    public Boolean isReadBy(Long personId) {
        boolean read = getReceivers().stream().anyMatch(r -> EntityUtil.getId(r.getPerson()).equals(personId) && r.getRead() != null);
        return Boolean.valueOf(read);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Classifier getSendersRole() {
        return sendersRole;
    }

    public void setSendersRole(Classifier sendersRole) {
        this.sendersRole = sendersRole;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public School getSendersSchool() {
        return sendersSchool;
    }

    public void setSendersSchool(School sendersSchool) {
        this.sendersSchool = sendersSchool;
    }

    public Message getResponseTo() {
        return responseTo;
    }

    public void setResponseTo(Message responseTo) {
        this.responseTo = responseTo;
    }

    public Set<Message> getResponses() {
        return responses != null ? responses : (responses = new HashSet<>());
    }

    public void setResponses(Set<Message> responses) {
        this.responses = responses;
    }

    public Set<MessageReceiver> getReceivers() {
        return receivers != null ? receivers : (receivers = new HashSet<>());
    }

    public void setReceivers(Set<MessageReceiver> receivers) {
        this.receivers = receivers;
    }
}
