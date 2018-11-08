package ee.hitsa.ois.domain.basemodule;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class BaseModuleThemeOutcomes extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BaseModuleTheme baseModuleTheme;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)    
    private BaseModuleOutcomes baseModuleOutcomes;
    
    public BaseModuleTheme getBaseModuleTheme() {
        return baseModuleTheme;
    }
    public void setBaseModuleTheme(BaseModuleTheme baseModuleTheme) {
        this.baseModuleTheme = baseModuleTheme;
    }
    public BaseModuleOutcomes getBaseModuleOutcomes() {
        return baseModuleOutcomes;
    }
    public void setBaseModuleOutcomes(BaseModuleOutcomes baseModuleOutcomes) {
        this.baseModuleOutcomes = baseModuleOutcomes;
    }
}
