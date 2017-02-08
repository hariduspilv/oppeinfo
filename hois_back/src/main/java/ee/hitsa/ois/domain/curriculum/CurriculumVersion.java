package ee.hitsa.ois.domain.curriculum;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.SchoolDepartment;

@Entity
public class CurriculumVersion extends BaseEntityWithId {

    private static final long serialVersionUID = -4036460363954584934L;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = true)
    private Curriculum curriculum;

    @NotBlank
    @Size(max=255)
    private String code;

    private Integer admissionYear;

    @Size(max=4000)
    private String targetGroup;

    @Column(name="is_individual")
    private Boolean individual;

    @Size(max=4000)
    private String teachers;

    @Size(max=4000)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolDepartment schoolDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumStudyForm curriculumStudyForm;

    private LocalDate validFrom;
    private LocalDate validThru;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_version_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumVersionHigherModule> modules = new HashSet<>();

//    mappedBy = "curriculumVersion", 
    @OneToMany(mappedBy = "curriculumVersion", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "curriculum_version_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumVersionSpeciality> specialities = new HashSet<>();
    
    @Transient
    private Set<Long> specialitiesReferenceNumbers;

    public Set<Long> getSpecialitiesReferenceNumbers() {
        return specialitiesReferenceNumbers;
    }

    public void setSpecialitiesReferenceNumbers(Set<Long> specialitiesReferenceNumbers) {
        this.specialitiesReferenceNumbers = specialitiesReferenceNumbers;
    }

    public CurriculumStudyForm getCurriculumStudyForm() {
        return curriculumStudyForm;
    }

    public void setCurriculumStudyForm(CurriculumStudyForm curriculumStudyForm) {
        this.curriculumStudyForm = curriculumStudyForm;
    }

    public Set<CurriculumVersionHigherModule> getModules() {
        return modules != null ? modules : (modules = new HashSet<>());
    }

    public void setModules(Set<CurriculumVersionHigherModule> modules) {
        getModules().clear();
        getModules().addAll(modules);
    }

    public Set<CurriculumVersionSpeciality> getSpecialities() {
        return specialities != null ? specialities : (specialities = new HashSet<>());
    }

    public void setSpecialities(Set<CurriculumVersionSpeciality> specialities) {
        getSpecialities().clear();
        getSpecialities().addAll(specialities);
//        getSpecialities().forEach(s -> {
//            s.getCurriculumSpeciality().getCurriculumVersionSpecialities().
//        });
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(Integer admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public Boolean getIndividual() {
        return individual;
    }

    public void setIndividual(Boolean individual) {
        this.individual = individual;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public SchoolDepartment getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(SchoolDepartment schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
}
