package ee.hitsa.ois.domain.student;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class StudentLanguages extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    // EHIS_VOORKEEL
    private Classifier foreignLang;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    // VOORKEEL_TYYP
    private Classifier foreignLangType;
    
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Classifier getForeignLang() {
        return foreignLang;
    }
    public void setForeignLang(Classifier foreignLang) {
        this.foreignLang = foreignLang;
    }
    public Classifier getForeignLangType() {
        return foreignLangType;
    }
    public void setForeignLangType(Classifier foreignLangType) {
        this.foreignLangType = foreignLangType;
    }
}
