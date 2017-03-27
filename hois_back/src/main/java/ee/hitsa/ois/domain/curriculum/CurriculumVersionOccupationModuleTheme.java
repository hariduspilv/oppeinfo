package ee.hitsa.ois.domain.curriculum;

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

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
@Table(name="curriculum_version_omodule_theme")
public class CurriculumVersionOccupationModuleTheme  extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false, updatable = false, insertable = false)
    private CurriculumVersionOccupationModule module;

    @Column(nullable = false)
    private String nameEt;

    @Column(nullable = false)
    private Double credits;

    @Column(nullable = false)
    private Integer hours;

    private Double proportion;
    private String subthemes;
    private Integer studyYearNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier assessment;

    private String totalGradeDescription;
    private String passDescription;

    @Column(name = "grade3_description")
    private String grade3Description;

    @Column(name = "grade4_description")
    private String grade4Description;

    @Column(name = "grade5_description")
    private String grade5Description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_omodule_theme_id", nullable = false, updatable = false)
    private Set<CurriculumVersionOccupationModuleOutcome> outcomes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_omodule_theme_id", nullable = false, updatable = false)
    private Set<CurriculumVersionOccupationModuleThemeCapacity> capacities = new HashSet<>();

    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public Double getCredits() {
        return credits;
    }
    public void setCredits(Double credits) {
        this.credits = credits;
    }
    public Integer getHours() {
        return hours;
    }
    public void setHours(Integer hours) {
        this.hours = hours;
    }
    public Double getProportion() {
        return proportion;
    }
    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }
    public String getSubthemes() {
        return subthemes;
    }
    public void setSubthemes(String subthemes) {
        this.subthemes = subthemes;
    }
    public Integer getStudyYearNumber() {
        return studyYearNumber;
    }
    public void setStudyYearNumber(Integer studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
    }
    public Set<CurriculumVersionOccupationModuleThemeCapacity> getCapacities() {
        return capacities;
    }
    public void setCapacities(Set<CurriculumVersionOccupationModuleThemeCapacity> capacities) {
        getCapacities().clear();
        getCapacities().addAll(capacities);
    }
    public Set<CurriculumVersionOccupationModuleOutcome> getOutcomes() {
        return outcomes;
    }
    public void setOutcomes(Set<CurriculumVersionOccupationModuleOutcome> outcomes) {
        getOutcomes().clear();
        getOutcomes().addAll(outcomes);
    }
    public CurriculumVersionOccupationModule getModule() {
        return module;
    }
    public void setModule(CurriculumVersionOccupationModule module) {
        this.module = module;
    }
    public Classifier getAssessment() {
        return assessment;
    }
    public void setAssessment(Classifier assessment) {
        this.assessment = assessment;
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

}
