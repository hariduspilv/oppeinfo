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

import ee.hitsa.ois.domain.school.School;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class WsRrLogSchool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private WsRrLog wsRrLog;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime inserted;
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String insertedBy;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    public WsRrLog getWsRrLog() {
        return wsRrLog;
    }
    public void setWsRrLog(WsRrLog wsRrLog) {
        this.wsRrLog = wsRrLog;
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
