package ee.hitsa.ois.domain;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WsPhotoLog extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Directive directive;

    private String request;
    private String response;
    private Boolean isMultipleRequest;
    private String logTxt;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Directive getDirective() {
        return directive;
    }

    public void setDirective(Directive directive) {
        this.directive = directive;
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

    public Boolean getIsMultipleRequest() {
        return isMultipleRequest;
    }

    public void setIsMultipleRequest(Boolean isMultipleRequest) {
        this.isMultipleRequest = isMultipleRequest;
    }

    public String getLogTxt() {
        return logTxt;
    }

    public void setLogTxt(String logTxt) {
        this.logTxt = logTxt;
    }
}
