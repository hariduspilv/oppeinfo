package ee.hitsa.ois.domain.curriculum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.util.Translatable;

@Entity
@Table(name="curriculum_version_hmodule")
public class CurriculumVersionHigherModule extends BaseEntityWithId implements Translatable {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumVersion curriculumVersion;

    @NotBlank
    @Size(max = 255)
    private String nameEt;

    @NotBlank
    @Size(max = 255)
    private String nameEn;

    private Short orderNr;
    private String objectivesEt;
    private String objectivesEn;
    private String outcomesEt;
    private String outcomesEn;
    @Size(max = 255)
    private String typeNameEt;
    @Size(max = 255)
    private String typeNameEn;

    @NotNull
    private BigDecimal totalCredits;
    @NotNull
    private BigDecimal optionalStudyCredits;
    private BigDecimal compulsoryStudyCredits;
    private Short electiveModulesNumber;

    @NotNull
    @Column(name="is_minor_speciality")
    private Boolean minorSpeciality;
    private Boolean isGrade;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumVersionHigherModuleSubject> subjects = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_hmodule_id", nullable = false, updatable = false)
    private Set<CurriculumVersionElectiveModule> electiveModules = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumVersionHigherModuleSpeciality> specialities = new HashSet<>();

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Short getElectiveModulesNumber() {
        return electiveModulesNumber;
    }

    public void setElectiveModulesNumber(Short electiveModulesNumber) {
        this.electiveModulesNumber = electiveModulesNumber;
    }

    public BigDecimal getCompulsoryStudyCredits() {
        return compulsoryStudyCredits;
    }

    public void setCompulsoryStudyCredits(BigDecimal compulsoryStudyCredits) {
        this.compulsoryStudyCredits = compulsoryStudyCredits;
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

    public Short getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Short orderNr) {
        this.orderNr = orderNr;
    }

    public String getObjectivesEt() {
        return objectivesEt;
    }

    public void setObjectivesEt(String objectivesEt) {
        this.objectivesEt = objectivesEt;
    }

    public String getObjectivesEn() {
        return objectivesEn;
    }

    public void setObjectivesEn(String objectivesEn) {
        this.objectivesEn = objectivesEn;
    }

    public String getOutcomesEt() {
        return outcomesEt;
    }

    public void setOutcomesEt(String outcomesEt) {
        this.outcomesEt = outcomesEt;
    }

    public String getOutcomesEn() {
        return outcomesEn;
    }

    public void setOutcomesEn(String outcomesEn) {
        this.outcomesEn = outcomesEn;
    }

    public String getTypeNameEt() {
        return typeNameEt;
    }

    public void setTypeNameEt(String typeNameEt) {
        this.typeNameEt = typeNameEt;
    }

    public String getTypeNameEn() {
        return typeNameEn;
    }

    public void setTypeNameEn(String typeNameEn) {
        this.typeNameEn = typeNameEn;
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(BigDecimal totalCredits) {
        this.totalCredits = totalCredits;
    }

    public BigDecimal getOptionalStudyCredits() {
        return optionalStudyCredits;
    }

    public void setOptionalStudyCredits(BigDecimal optionalStudyCredits) {
        this.optionalStudyCredits = optionalStudyCredits;
    }

    public Boolean getMinorSpeciality() {
        return minorSpeciality;
    }

    public void setMinorSpeciality(Boolean minorSpeciality) {
        this.minorSpeciality = minorSpeciality;
    }

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }

    public Boolean getIsGrade() {
        return isGrade;
    }

    public void setIsGrade(Boolean isGrade) {
        this.isGrade = isGrade;
    }

    public Set<CurriculumVersionHigherModuleSubject> getSubjects() {
        return subjects != null ? subjects : (subjects = new HashSet<>());
    }

    public void setSubjects(Set<CurriculumVersionHigherModuleSubject> subjects) {
        getSubjects().clear();
        getSubjects().addAll(subjects);
    }

    public Set<CurriculumVersionElectiveModule> getElectiveModules() {
        return electiveModules != null ? electiveModules : (electiveModules = new HashSet<>());
    }

    public void setElectiveModules(Set<CurriculumVersionElectiveModule> electiveModules) {
        getElectiveModules().clear();
        getElectiveModules().addAll(electiveModules);
    }

    public Set<CurriculumVersionHigherModuleSpeciality> getSpecialities() {
        return specialities != null ? specialities : (specialities = new HashSet<>());
    }

    public void setSpecialities(Set<CurriculumVersionHigherModuleSpeciality> specialities) {
        getSpecialities().clear();
        getSpecialities().addAll(specialities);
    }
}
