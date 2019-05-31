package ee.hitsa.ois.domain.application;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;

@Entity
public class ApplicationSupportServiceModule extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_support_service_id", nullable = false, updatable = false, insertable = false)
    private ApplicationSupportService supportService;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false, updatable = false)
    private CurriculumVersionOccupationModule module;
    
    private String addInfo;

    public ApplicationSupportService getSupportService() {
        return supportService;
    }

    public void setSupportService(ApplicationSupportService supportService) {
        this.supportService = supportService;
    }

    public CurriculumVersionOccupationModule getModule() {
        return module;
    }

    public void setModule(CurriculumVersionOccupationModule module) {
        this.module = module;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
}
