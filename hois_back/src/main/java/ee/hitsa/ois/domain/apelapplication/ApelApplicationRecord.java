package ee.hitsa.ois.domain.apelapplication;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class ApelApplicationRecord extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplication apelApplication;
    
    private Boolean isFormalLearning;
    
    @OneToMany(mappedBy="apelApplicationRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationInformalExperience> informalExperiences = new ArrayList<>();
    
    @OneToMany(mappedBy="apelApplicationRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationInformalSubjectOrModule> informalSubjectsOrModules = new ArrayList<>();

    @OneToMany(mappedBy="apelApplicationRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationFormalSubjectOrModule> formalSubjectsOrModules = new ArrayList<>();

    @OneToMany(mappedBy="apelApplicationRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationFormalReplacedSubjectOrModule> formalReplacedSubjectsOrModules = new ArrayList<>();

    public ApelApplication getApelApplication() {
        return apelApplication;
    }

    public void setApelApplication(ApelApplication apelApplication) {
        this.apelApplication = apelApplication;
    }

    public Boolean getIsFormalLearning() {
        return isFormalLearning;
    }

    public void setIsFormalLearning(Boolean isFormalLearning) {
        this.isFormalLearning = isFormalLearning;
    }

    public List<ApelApplicationInformalExperience> getInformalExperiences() {
        return informalExperiences;
    }

    public void setInformalExperiences(List<ApelApplicationInformalExperience> informalExperiences) {
        this.informalExperiences = informalExperiences;
    }

    public List<ApelApplicationInformalSubjectOrModule> getInformalSubjectsOrModules() {
        return informalSubjectsOrModules;
    }

    public void setInformalSubjectsOrModules(List<ApelApplicationInformalSubjectOrModule> informalSubjectsOrModules) {
        this.informalSubjectsOrModules = informalSubjectsOrModules;
    }

    public List<ApelApplicationFormalSubjectOrModule> getFormalSubjectsOrModules() {
        return formalSubjectsOrModules;
    }

    public void setFormalSubjectsOrModules(List<ApelApplicationFormalSubjectOrModule> formalSubjectsOrModules) {
        this.formalSubjectsOrModules = formalSubjectsOrModules;
    }

    public List<ApelApplicationFormalReplacedSubjectOrModule> getFormalReplacedSubjectsOrModules() {
        return formalReplacedSubjectsOrModules;
    }

    public void setFormalReplacedSubjectsOrModules(List<ApelApplicationFormalReplacedSubjectOrModule> formalReplacedSubjectsOrModules) {
        this.formalReplacedSubjectsOrModules = formalReplacedSubjectsOrModules;
    }
}
