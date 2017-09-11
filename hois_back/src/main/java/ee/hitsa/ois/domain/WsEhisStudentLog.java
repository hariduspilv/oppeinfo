package ee.hitsa.ois.domain;

import ee.hitsa.ois.domain.directive.Directive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WsEhisStudentLog extends BaseLog {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Directive directive;
    @Column(nullable = false, updatable = false)
    private Boolean hasXteeErrors = Boolean.FALSE;
    @Column(nullable = false, updatable = false)
    private Boolean hasOtherErrors = Boolean.FALSE;
    @Column(nullable = false, updatable = false)
    private String logTxt;

    public Directive getDirective() {
        return directive;
    }

    public void setDirective(Directive directive) {
        this.directive = directive;
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
