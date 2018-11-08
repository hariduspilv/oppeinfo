package ee.hitsa.ois.domain.basemodule;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;

@Entity
public class BaseModuleTheme extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BaseModule baseModule;
    @Column(nullable = false)
    private String nameEt;
    @Column(nullable = false)
    private BigDecimal credits;
    @Column(nullable = false)
    private Short hours;
    private String subthemes;
    private String totalGradeDescription;
    private String passDescription;
    @Column(name = "grade3_description")
    private String grade3Description;
    @Column(name = "grade4_description")
    private String grade4Description;
    @Column(name = "grade5_description")
    private String grade5Description;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier assessment;
    
    @OneToMany(mappedBy = "baseModuleTheme", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BaseModuleThemeCapacity> capacities = new HashSet<>();
    @OneToMany(mappedBy = "baseModuleTheme", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BaseModuleThemeOutcomes> baseModuleThemeOutcomes = new HashSet<>();
    @OneToMany(mappedBy = "baseModuleTheme")
    private Set<CurriculumVersionOccupationModuleTheme> themes = new HashSet<>();
    
    public BaseModule getBaseModule() {
        return baseModule;
    }
    public void setBaseModule(BaseModule baseModule) {
        this.baseModule = baseModule;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public BigDecimal getCredits() {
        return credits;
    }
    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }
    public Short getHours() {
        return hours;
    }
    public void setHours(Short hours) {
        this.hours = hours;
    }
    public String getSubthemes() {
        return subthemes;
    }
    public void setSubthemes(String subthemes) {
        this.subthemes = subthemes;
    }
    public String getTotalGradeDescription() {
        return totalGradeDescription;
    }
    public void setTotalGradeDescription(String totalGradeDescription) {
        this.totalGradeDescription = totalGradeDescription;
    }
    public String getPassDescription() {
        return passDescription;
    }
    public void setPassDescription(String passDescription) {
        this.passDescription = passDescription;
    }
    public String getGrade3Description() {
        return grade3Description;
    }
    public void setGrade3Description(String grade3Description) {
        this.grade3Description = grade3Description;
    }
    public String getGrade4Description() {
        return grade4Description;
    }
    public void setGrade4Description(String grade4Description) {
        this.grade4Description = grade4Description;
    }
    public String getGrade5Description() {
        return grade5Description;
    }
    public void setGrade5Description(String grade5Description) {
        this.grade5Description = grade5Description;
    }
    public Classifier getAssessment() {
        return assessment;
    }
    public void setAssessment(Classifier assessment) {
        this.assessment = assessment;
    }
    
    public Set<BaseModuleThemeCapacity> getCapacities() {
        return capacities != null ? capacities : (capacities = new HashSet<>());
    }
    public void setCapacities(Set<BaseModuleThemeCapacity> capacities) {
        getCapacities().clear();
        getCapacities().addAll(capacities);
    }
    public Set<BaseModuleThemeOutcomes> getBaseModuleThemeOutcomes() {
        return baseModuleThemeOutcomes != null ? baseModuleThemeOutcomes : (baseModuleThemeOutcomes = new HashSet<>());
    }
    public void setBaseModuleThemeOutcomes(Set<BaseModuleThemeOutcomes> baseModuleThemeOutcomes) {
        getBaseModuleThemeOutcomes().clear();
        getBaseModuleThemeOutcomes().addAll(baseModuleThemeOutcomes);
    }
    public Set<CurriculumVersionOccupationModuleTheme> getThemes() {
        return themes != null ? themes : (themes = new HashSet<>());
    }
    public void setThemes(Set<CurriculumVersionOccupationModuleTheme> themes) {
        getThemes().clear();
        getThemes().addAll(themes);
    }
}
