package ee.hitsa.ois.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.school.School;

@Entity
public class WsEhisCurriculumLog extends BaseLog {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Curriculum curriculum;
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

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
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
