package ee.hitsa.ois.domain.directive;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.validation.DirectiveValidation.Indok;


@Entity
public class DirectiveStudentModule extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private DirectiveStudent directiveStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersionOccupationModule curriculumVersionOmodule;

    @Required(groups = Indok.class)
    private String addInfo;

    public DirectiveStudent getDirectiveStudent() {
        return directiveStudent;
    }

    public void setDirectiveStudent(DirectiveStudent directiveStudent) {
        this.directiveStudent = directiveStudent;
    }

    public CurriculumVersionOccupationModule getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(CurriculumVersionOccupationModule curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

}
