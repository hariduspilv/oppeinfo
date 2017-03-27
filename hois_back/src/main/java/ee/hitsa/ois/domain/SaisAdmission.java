package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.validation.NotEmpty;

@Entity
public class SaisAdmission extends BaseEntityWithId {

    @NotEmpty
    @Size(max = 100)
    private String code;

    @NotEmpty
    @Size(max = 1000)
    private String name;

    @NotEmpty
    @Size(max = 50)
    private String saisId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier fin;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier language;
    private Integer places;
    private LocalDate periodStart;

    @Column(nullable = false)
    private LocalDate periodEnd;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier studyForm;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyLoad;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sais_admission_id", nullable = false, updatable = false)
    private Set<SaisApplication> applications = new HashSet<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaisId() {
        return saisId;
    }

    public void setSaisId(String saisId) {
        this.saisId = saisId;
    }

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Classifier getFin() {
        return fin;
    }

    public void setFin(Classifier fin) {
        this.fin = fin;
    }

    public Classifier getLanguage() {
        return language;
    }

    public void setLanguage(Classifier language) {
        this.language = language;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }


    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public Classifier getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }

    public Classifier getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(Classifier studyLevel) {
        this.studyLevel = studyLevel;
    }

    public Classifier getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(Classifier studyLoad) {
        this.studyLoad = studyLoad;
    }

    public Set<SaisApplication> getApplications() {
        return applications;
    }

    public void setApplications(Set<SaisApplication> applications) {
        this.applications = applications;
    }

}
