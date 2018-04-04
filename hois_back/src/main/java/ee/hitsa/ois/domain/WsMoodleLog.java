package ee.hitsa.ois.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.school.School;

@Entity
public class WsMoodleLog extends BaseEntityWithId {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private School school;
    private String wsName;
    private String request;
    private String response;
    @Column(nullable = false, updatable = false)
    private Boolean hasErrors = Boolean.FALSE;
    @Column(nullable = false, updatable = false)
    private String logTxt;
    private String ipAddress;
    private Long journalId;
    private Long subjectStudyPeriodId;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier role;
    
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getWsName() {
        return wsName;
    }

    public void setWsName(String wsName) {
        this.wsName = wsName;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public Long getSubjectStudyPeriodId() {
        return subjectStudyPeriodId;
    }

    public void setSubjectStudyPeriodId(Long subjectStudyPeriodId) {
        this.subjectStudyPeriodId = subjectStudyPeriodId;
    }

    public Classifier getRole() {
        return role;
    }

    public void setRole(Classifier role) {
        this.role = role;
    }
    
}
