package ee.hitsa.ois.domain.rr;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class WsRrLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String wsName;
    @Column(nullable = false, updatable = false)
    private String idcode;
    @Column(nullable = false, updatable = false)
    private String request;
    @Column(updatable = false)
    private String response;
    @Column(nullable = false, updatable = false)
    private Boolean hasErrors;
    @Column(updatable = false)
    private String logTxt;
    
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
    public String getWsName() {
        return wsName;
    }
    public void setWsName(String wsName) {
        this.wsName = wsName;
    }
    public String getIdcode() {
        return idcode;
    }
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    public String getRequest() {
        return request;
    }
    public void setRequest(String request) {
        this.request = request;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }
    public Boolean getHasErrors() {
        return hasErrors;
    }
    public void setHasErrors(Boolean hasErrors) {
        this.hasErrors = hasErrors;
    }
    public String getLogTxt() {
        return logTxt;
    }
    public void setLogTxt(String logTxt) {
        this.logTxt = logTxt;
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
