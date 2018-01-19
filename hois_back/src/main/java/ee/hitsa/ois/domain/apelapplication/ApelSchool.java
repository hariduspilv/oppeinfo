package ee.hitsa.ois.domain.apelapplication;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.util.Translatable;

@Entity
public class ApelSchool extends BaseEntityWithId implements Translatable {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier country;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier ehisSchool;

    @Column(nullable = false)
    private String nameEt;

    private String nameEn;

    @OneToMany(mappedBy = "apelSchool", fetch = FetchType.LAZY)
    private List<ApelApplicationFormalSubjectOrModule> formalSubjectOrModules = new ArrayList<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    public Classifier getCountry() {
        return country;
    }

    public void setCountry(Classifier country) {
        this.country = country;
    }

    public Classifier getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(Classifier ehisSchool) {
        this.ehisSchool = ehisSchool;
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public List<ApelApplicationFormalSubjectOrModule> getFormalSubjectOrModules() {
        return formalSubjectOrModules;
    }

    public void setFormalSubjectOrModules(List<ApelApplicationFormalSubjectOrModule> formalSubjectOrModules) {
        this.formalSubjectOrModules = formalSubjectOrModules;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
