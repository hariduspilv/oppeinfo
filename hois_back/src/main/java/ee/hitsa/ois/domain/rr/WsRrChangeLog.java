package ee.hitsa.ois.domain.rr;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import ee.hitsa.ois.domain.Person;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class WsRrChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Person person;

    @Column(nullable = false, updatable = false)
    private String oldFirstname;
    @Column(updatable = false)
    private String oldLastname;
    @Column(nullable = false, updatable = false)
    private String newFirstname;
    @Column(updatable = false)
    private String newLastname;
    @Column(updatable = false)
    private String oldAddressAdsOid;
    @Column(updatable = false)
    private String newAddressAdsOid;
    @Column(updatable = false)
    private String oldAddress;
    @Column(updatable = false)
    private String newAddress;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime inserted;
    @CreatedBy
    @Column(updatable = false)
    private String insertedBy;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public String getOldFirstname() {
        return oldFirstname;
    }
    public void setOldFirstname(String oldFirstname) {
        this.oldFirstname = oldFirstname;
    }
    public String getOldLastname() {
        return oldLastname;
    }
    public void setOldLastname(String oldLastname) {
        this.oldLastname = oldLastname;
    }
    public String getNewFirstname() {
        return newFirstname;
    }
    public void setNewFirstname(String newFirstname) {
        this.newFirstname = newFirstname;
    }
    public String getNewLastname() {
        return newLastname;
    }
    public void setNewLastname(String newLastname) {
        this.newLastname = newLastname;
    }
    public String getOldAddressAdsOid() {
        return oldAddressAdsOid;
    }
    public void setOldAddressAdsOid(String oldAddressAdsOid) {
        this.oldAddressAdsOid = oldAddressAdsOid;
    }
    public String getNewAddressAdsOid() {
        return newAddressAdsOid;
    }
    public void setNewAddressAdsOid(String newAddressAdsOid) {
        this.newAddressAdsOid = newAddressAdsOid;
    }
    public String getOldAddress() {
        return oldAddress;
    }
    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }
    public String getNewAddress() {
        return newAddress;
    }
    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }
    public LocalDateTime getInserted() {
        return inserted;
    }
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
    public String getInsertedBy() {
        return insertedBy;
    }
    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }
}
