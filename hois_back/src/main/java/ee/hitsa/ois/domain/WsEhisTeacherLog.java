package ee.hitsa.ois.domain;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WsEhisTeacherLog extends BaseLog {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Teacher teacher;
    @Column(nullable = false, updatable = false)
    private Boolean hasXteeErrors = Boolean.FALSE;
    @Column(nullable = false, updatable = false)
    private Boolean hasOtherErrors = Boolean.FALSE;
    @Column(nullable = false, updatable = false)
    private String logTxt;

    @Override
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
