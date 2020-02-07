package ee.hitsa.ois.domain.apelapplication;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.validation.Conditional;

@Entity
@Conditional(selected = "apelApplicationRecord.apelApplication.isVocational", values = {"true"}, required = {"curriculumVersionOmodule"})
@Conditional(selected = "apelApplicationRecord.apelApplication.isVocational", values = {"false"}, required = {"subject"})
public class ApelApplicationFormalReplacedSubjectOrModule extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplicationRecord apelApplicationRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersionOccupationModule curriculumVersionOmodule;
    
    @ManyToOne(fetch = FetchType.LAZY)
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
