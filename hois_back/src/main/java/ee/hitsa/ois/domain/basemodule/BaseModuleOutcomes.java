package ee.hitsa.ois.domain.basemodule;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;

@Entity
public class BaseModuleOutcomes extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BaseModule baseModule;
    private String outcomeEt;
    private String outcomeEn;
    private Integer orderNr;
    
    @OneToMany(mappedBy = "baseModuleOutcomes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BaseModuleThemeOutcomes> baseModuleThemeOutcomes = new HashSet<>();
    @OneToMany(mappedBy = "baseModuleOutcomes")
    private Set<CurriculumModuleOutcome> outcomes = new HashSet<>();
    
    public BaseModule getBaseModule() {
        return baseModule;
    }
    public void setBaseModule(BaseModule baseModule) {
        this.baseModule = baseModule;
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
    public Integer getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Integer orderNr) {
        this.orderNr = orderNr;
    }
    public Set<BaseModuleThemeOutcomes> getBaseModuleThemeOutcomes() {
        return baseModuleThemeOutcomes != null ? baseModuleThemeOutcomes : (baseModuleThemeOutcomes = new HashSet<>());
    }
    public void setBaseModuleThemeOutcomes(Set<BaseModuleThemeOutcomes> baseModuleThemeOutcomes) {
        getBaseModuleThemeOutcomes().clear();
        getBaseModuleThemeOutcomes().addAll(baseModuleThemeOutcomes);
    }
    public Set<CurriculumModuleOutcome> getOutcomes() {
        return outcomes != null ? outcomes : (outcomes = new HashSet<>());
    }
    public void setOutcomes(Set<CurriculumModuleOutcome> outcomes) {
        getOutcomes().clear();
        getOutcomes().addAll(outcomes);
    }
}
