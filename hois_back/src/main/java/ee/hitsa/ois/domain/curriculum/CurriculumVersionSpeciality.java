package ee.hitsa.ois.domain.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class CurriculumVersionSpeciality extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumSpeciality curriculumSpeciality;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumVersion curriculumVersion;
    
    @OneToMany(mappedBy = "speciality", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumVersionHigherModuleSpeciality> moduleSpecialities;
    
    @OneToMany(mappedBy = "curriculumVersionSpeciality", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumVersionNominalCapacity> nominalCapacities;
    
    public Set<CurriculumVersionHigherModuleSpeciality> getModuleSpecialities() {
        return moduleSpecialities != null ? moduleSpecialities : (moduleSpecialities = new HashSet<>());
    }

    public void setModuleSpecialities(Set<CurriculumVersionHigherModuleSpeciality> moduleSpecialities) {
        getModuleSpecialities().clear();
        getModuleSpecialities().addAll(moduleSpecialities);
    }

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public CurriculumSpeciality getCurriculumSpeciality() {
        return curriculumSpeciality;
    }
    public void setCurriculumSpeciality(CurriculumSpeciality curriculumSpeciality) {
        this.curriculumSpeciality = curriculumSpeciality;
    }

    public Set<CurriculumVersionNominalCapacity> getNominalCapacities() {
        return nominalCapacities;
    }

    public void setNominalCapacities(Set<CurriculumVersionNominalCapacity> nominalCapacities) {
        this.nominalCapacities = nominalCapacities;
    }
}
