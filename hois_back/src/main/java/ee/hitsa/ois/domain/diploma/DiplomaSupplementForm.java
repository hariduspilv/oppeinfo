package ee.hitsa.ois.domain.diploma;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Hibernate;

import ee.hitsa.ois.domain.BaseEntity;
import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.util.EntityUtil;

@Entity
public class DiplomaSupplementForm extends BaseEntity {

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Form form;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private DiplomaSupplement diplomaSupplement;
    
    public Form getForm() {
        return form;
    }
    public void setForm(Form form) {
        this.form = form;
    }
    
    public DiplomaSupplement getDiplomaSupplement() {
        return diplomaSupplement;
    }
    public void setDiplomaSupplement(DiplomaSupplement diplomaSupplement) {
        this.diplomaSupplement = diplomaSupplement;
    }
    
    @Override
    public int hashCode() {
        Long id = EntityUtil.getNullableId(form);
        return id == null ? 31 : id.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || form == null || !Hibernate.getClass(this).equals(Hibernate.getClass(obj))) {
            return false;
        }

        return obj instanceof DiplomaSupplementForm && form.equals(((DiplomaSupplementForm) obj).getForm());
    }
    
}
