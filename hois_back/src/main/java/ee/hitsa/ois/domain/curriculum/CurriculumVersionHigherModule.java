package ee.hitsa.ois.domain.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;
import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
@Table(name="curriculum_version_hmodule")
public class CurriculumVersionHigherModule extends BaseEntityWithId {
    
    @NotBlank
    @Size(max = 255)
    private String nameEt;
    
    @NotBlank
    @Size(max = 255)
    private String nameEn;
    
    private String objectivesEt;
    private String objectivesEn;
    private String outcomesEt;
    private String outcomesEn;
    @Size(max = 255)
    private String typeNameEt;
    @Size(max = 255)
    private String typeNameEn;

    @NotNull
    private Integer totalCredits;
    @NotNull
    private Integer optionalStudyCredits;
    
    @NotNull
    @Column(name="is_minor_speciality")
    private Boolean minorSpeciality;
    
    @ManyToOne(optional = false)
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier type;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_hmodule_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumVersionHigherModuleSubject> subjects = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_hmodule_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumVersionElectiveModule> electiveModules = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_hmodule_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumVersionHigherModuleSpeciality> specialities = new HashSet<>();

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

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public Integer getOptionalStudyCredits() {
        return optionalStudyCredits;
    }

    public void setOptionalStudyCredits(Integer optionalStudyCredits) {
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

    public Set<CurriculumVersionHigherModuleSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<CurriculumVersionHigherModuleSubject> subjects) {
        this.getSubjects().clear();
        this.getSubjects().addAll(subjects);
    }

    public Set<CurriculumVersionElectiveModule> getElectiveModules() {
        return electiveModules;
    }

    public void setElectiveModules(Set<CurriculumVersionElectiveModule> electiveModules) {
        this.getElectiveModules().clear();
        this.getElectiveModules().addAll(electiveModules);
    }

    public Set<CurriculumVersionHigherModuleSpeciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<CurriculumVersionHigherModuleSpeciality> specialities) {
        this.getSpecialities().clear();
        this.getSpecialities().addAll(specialities);
    }
}
