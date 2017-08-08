package ee.hitsa.ois.domain.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.exception.AssertionFailedException;

@Entity
public class CurriculumVersionElectiveModule extends BaseEntityWithId {
    private String nameEt;
    private String nameEn;
    
    @OneToMany(mappedBy = "electiveModule", fetch = FetchType.LAZY)
    private Set<CurriculumVersionHigherModuleSubject> subjects = new HashSet<>();
    
    @Transient
    private Long referenceNumber;
    
    public Long getReferenceNumber() {
        AssertionFailedException.throwIf(referenceNumber == null && getId() == null, "CurriculumVersionElectiveModule should whether be saved before or have a reference number!");
        return referenceNumber != null ? referenceNumber : getId();
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Set<CurriculumVersionHigherModuleSubject> getSubjects() {
        return subjects != null ? subjects : (subjects = new HashSet<>());
    }

    public void setSubjects(Set<CurriculumVersionHigherModuleSubject> subjects) {
        getSubjects().clear();
        getSubjects().addAll(subjects);
    }
}
