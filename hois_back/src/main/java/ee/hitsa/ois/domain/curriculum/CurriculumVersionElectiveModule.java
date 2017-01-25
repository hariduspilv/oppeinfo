package ee.hitsa.ois.domain.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class CurriculumVersionElectiveModule extends BaseEntityWithId {
    @NotBlank
    @Size(max=255)
    private String nameEt;
    @NotBlank
    @Size(max=255)
    private String nameEn;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_elective_module_id")
    private Set<CurriculumVersionHigherModuleSubject> subjects = new HashSet<>();

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
        return subjects;
    }

    public void setSubjects(Set<CurriculumVersionHigherModuleSubject> subjects) {
        this.getSubjects().clear();
        this.getSubjects().addAll(subjects);
    }
}
