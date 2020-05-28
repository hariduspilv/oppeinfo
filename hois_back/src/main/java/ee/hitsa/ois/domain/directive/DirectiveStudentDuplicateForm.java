package ee.hitsa.ois.domain.directive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Form;

@Entity
public class DirectiveStudentDuplicateForm extends BaseEntityWithId {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DirectiveStudent directiveStudent;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Form form;
    @Column(name = "is_en", nullable = false)
    private Boolean en;

    public DirectiveStudent getDirectiveStudent() {
        return directiveStudent;
    }

    public void setDirectiveStudent(DirectiveStudent directiveStudent) {
        this.directiveStudent = directiveStudent;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Boolean getEn() {
        return en;
    }

    public void setEn(Boolean en) {
        this.en = en;
    }

}
