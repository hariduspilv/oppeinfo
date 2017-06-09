package ee.hitsa.ois.domain;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class WsEhisTeacherLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Teacher teacher;
    @Column(nullable = false, updatable = false)
    private String wsName;
    @Column(nullable = false, updatable = false)
    private String request;
    @Column(nullable = false, updatable = false)
    private String response;
    @Column(nullable = false, updatable = false)
    private Boolean hasXteeErrors = Boolean.FALSE;
    @Column(nullable = false, updatable = false)
    private Boolean hasOtherErrors = Boolean.FALSE;
    @Column(nullable = false, updatable = false)
    private String logTxt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime inserted = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getWsName() {
        return wsName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    public Boolean getHasXteeErrors() {
        return hasXteeErrors;
    }

    public void setHasXteeErrors(Boolean hasXteeErrors) {
        this.hasXteeErrors = hasXteeErrors;
    }

    public Boolean getHasOtherErrors() {
        return hasOtherErrors;
    }

    public void setHasOtherErrors(Boolean hasOtherErrors) {
        this.hasOtherErrors = hasOtherErrors;
    }

    public String getLogTxt() {
        return logTxt;
    }

    public void setLogTxt(String logTxt) {
        this.logTxt = logTxt;
    }
}
