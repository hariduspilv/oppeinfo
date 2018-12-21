package ee.hitsa.ois.domain.apelapplication;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.subject.Subject;

@Entity
public class ApelApplicationFormalReplacedSubjectOrModule extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplicationRecord apelApplicationRecord;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Subject subject;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private CurriculumVersionOccupationModule curriculumVersionOmodule;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private CurriculumVersionOccupationModuleTheme curriculumVersionOmoduleTheme;

    public ApelApplicationRecord getApelApplicationRecord() {
        return apelApplicationRecord;
    }

    public void setApelApplicationRecord(ApelApplicationRecord apelApplicationRecord) {
        this.apelApplicationRecord = apelApplicationRecord;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public CurriculumVersionOccupationModule getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(CurriculumVersionOccupationModule curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public CurriculumVersionOccupationModuleTheme getCurriculumVersionOmoduleTheme() {
        return curriculumVersionOmoduleTheme;
    }

    public void setCurriculumVersionOmoduleTheme(CurriculumVersionOccupationModuleTheme curriculumVersionOmoduleTheme) {
        this.curriculumVersionOmoduleTheme = curriculumVersionOmoduleTheme;
    }

}
