package ee.hitsa.ois.domain.application;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;

@Entity
public class ApplicationSupportService extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier supportService;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Application application;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_support_service_id", nullable = false, updatable = false)
    private Set<ApplicationSupportServiceModule> modules;

    public Classifier getSupportService() {
        return supportService;
    }

    public void setSupportService(Classifier supportService) {
        this.supportService = supportService;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Set<ApplicationSupportServiceModule> getModules() {
        return modules != null ? modules : (modules = new HashSet<>());
    }

    public void setModules(Set<ApplicationSupportServiceModule> modules) {
        this.modules.clear();
        this.modules.addAll(modules);
    }

}
