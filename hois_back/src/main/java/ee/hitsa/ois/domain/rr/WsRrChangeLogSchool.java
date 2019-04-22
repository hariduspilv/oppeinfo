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
import ee.hitsa.ois.domain.student.Student;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class WsRrChangeLogSchool {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private WsRrChangeLog wsRrChangeLog;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;

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
    public WsRrChangeLog getWsRrChangeLog() {
        return wsRrChangeLog;
    }
    public void setWsRrChangeLog(WsRrChangeLog wsRrChangeLog) {
        this.wsRrChangeLog = wsRrChangeLog;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
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
