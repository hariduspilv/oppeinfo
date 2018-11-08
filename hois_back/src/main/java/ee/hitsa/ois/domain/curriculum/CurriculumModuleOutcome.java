package ee.hitsa.ois.domain.curriculum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModuleOutcomes;
import ee.hitsa.ois.domain.basemodule.BaseModuleOutcomes;

@Entity
@Table(name = "curriculum_module_outcomes")
public class CurriculumModuleOutcome extends BaseEntityWithId {

    private String outcomeEt;
    private String outcomeEn;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    private CurriculumModule curriculumModule;
    
    private Long orderNr;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private BaseModuleOutcomes baseModuleOutcomes;

    @OneToMany(mappedBy="curriculumModuleOutcomes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationInformalSubjectOrModuleOutcomes> outcomes = new ArrayList<>();
    
    @OneToMany(mappedBy="outcome", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumVersionOccupationModuleOutcome> themeOutcomes = new HashSet<>(); 

    public Long getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
    }

    public String getOutcomeEt() {
        return outcomeEt;
    }

    public void setOutcomeEt(String outcomeEt) {
        this.outcomeEt = outcomeEt;
    }

    public String getOutcomeEn() {
        return outcomeEn;
    }

    public void setOutcomeEn(String outcomeEn) {
        this.outcomeEn = outcomeEn;
    }
    
    public CurriculumModule getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(CurriculumModule curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public List<ApelApplicationInformalSubjectOrModuleOutcomes> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<ApelApplicationInformalSubjectOrModuleOutcomes> outcomes) {
        this.outcomes = outcomes;
    }

    public BaseModuleOutcomes getBaseModuleOutcomes() {
        return baseModuleOutcomes;
    }

    public void setBaseModuleOutcomes(BaseModuleOutcomes baseModuleOutcomes) {
        this.baseModuleOutcomes = baseModuleOutcomes;
    }

    public Set<CurriculumVersionOccupationModuleOutcome> getThemeOutcomes() {
        return themeOutcomes;
    }

    public void setThemeOutcomes(Set<CurriculumVersionOccupationModuleOutcome> themeOutcomes) {
        this.themeOutcomes = themeOutcomes;
    }
    
}
