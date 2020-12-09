package ee.hitsa.ois.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class WsAdLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestUrl;
    @Column(updatable = false)
    private String requestParam;
    @Column(updatable = false)
    private String response;
    @Column(nullable = false, updatable = false)
    private Boolean hasErrors = Boolean.FALSE;
    @Column(updatable = false)
    private String logTxt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime inserted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
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

}
