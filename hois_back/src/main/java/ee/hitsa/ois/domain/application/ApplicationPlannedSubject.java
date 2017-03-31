package ee.hitsa.ois.domain.application;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class ApplicationPlannedSubject extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Application application;

    @Size(max = 1000)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_planned_subject_id", nullable = false, updatable = false)
    private Set<ApplicationPlannedSubjectEquivalent> equivalents = new HashSet<>();

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ApplicationPlannedSubjectEquivalent> getEquivalents() {
        return equivalents;
    }

    public void setEquivalents(Set<ApplicationPlannedSubjectEquivalent> equivalents) {
        this.equivalents = equivalents;
    }

}
