package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.NotEmpty;

@Entity
public class Classifier extends BaseEntity {
    private static final long serialVersionUID = 3111361264166192650L;

    @Id
    @Size(max = 200)
    @NotEmpty
    // https://hibernate.atlassian.net/browse/HHH-3718
    private String code;

    @NotEmpty
    private String value;
    private String value2;

    @NotEmpty
    private String nameEt;
    private String nameEn;
    private String nameRu;
    private String mainClassCode;

    //lazy load causes - org.springframework.http.converter.HttpMessageNotWritableException: Could not write content: failed to lazily initialize a collection of role: ee.hitsa.ois.domain.Classifier.children, could not initialize proxy - no Session (through reference chain: ee.hitsa.ois.domain.Classifier["children"]);
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "mainClassCode", nullable = false, updatable = false, insertable = false)
    private Set<Classifier> children;

    private String description;
    private LocalDate validFrom;
    private LocalDate validThru;
    private String extraval1;
    private String extraval2;
    private String ehisValue;
    @Column(name="is_vocational")
    private boolean vocational;
    @Column(name="is_higher")
    private boolean higher;
    private boolean valid;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "classifier_code", nullable = false, updatable = false, insertable = false)
    private Set<ClassifierConnect> classifierConnects;

    @Override
    public int hashCode() {
        return code == null ? 31 : code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || code == null || !Hibernate.getClass(this).equals(Hibernate.getClass(obj))) {
            return false;
        }

        return code.equals(EntityUtil.getCode((Classifier) obj));
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Set<Classifier> getChildren() {
        return children != null ? children : (children = new HashSet<>());
    }

    public String getCode() {
        return code;
    }

    public String getMainClassCode() {
        return mainClassCode;
    }

    public void setMainClassCode(String mainClassCode) {
        this.mainClassCode = mainClassCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public String getExtraval1() {
        return extraval1;
    }

    public void setExtraval1(String extraval1) {
        this.extraval1 = extraval1;
    }

    public String getExtraval2() {
        return extraval2;
    }

    public void setExtraval2(String extraval2) {
        this.extraval2 = extraval2;
    }

    public String getEhisValue() {
        return ehisValue;
    }

    public void setEhisValue(String ehisValue) {
        this.ehisValue = ehisValue;
    }

    public boolean isVocational() {
        return vocational;
    }

    public void setVocational(boolean vocational) {
        this.vocational = vocational;
    }

    public boolean isHigher() {
        return higher;
    }

    public void setHigher(boolean higher) {
        this.higher = higher;
    }

    public Set<ClassifierConnect> getClassifierConnects() {
        return classifierConnects != null ? classifierConnects : (classifierConnects = new HashSet<>());
    }
}
