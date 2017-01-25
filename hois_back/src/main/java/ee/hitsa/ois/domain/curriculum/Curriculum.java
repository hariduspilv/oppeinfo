package ee.hitsa.ois.domain.curriculum;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.domain.StateCurriculum;

@Entity
public class Curriculum extends BaseEntityWithId {
    private static final long serialVersionUID = -7063602940937795603L;

    @NotNull
    @Column(name = "is_higher")
    private Boolean higher;

    @NotBlank
    @Size(max = 255)
    private String nameEt;

    @NotBlank
    @Size(max = 255)
    private String nameEn;
    @Size(max = 255)
    private String nameRu;

    @NotBlank
    @Size(max = 25)
    private String code;

    @Size(max = 10)
    private String merCode;
    private LocalDate approval;

    @Size(max = 50)
    private String approvalDokNr;

    @Size(max = 20000)
    private String outcomesEt;

    @Size(max = 20000)
    private String outcomesEn;

    @Size(max = 4000)
    private String structure;

    @Size(max = 20000)
    private String admissionRequirementsEt;

    @Size(max = 20000)
    private String admissionRequirementsEn;

    @Size(max = 20000)
    private String graduationRequirementsEt;

    @Size(max = 20000)
    private String graduationRequirementsEn;

    private Double credits;

    @Size(max = 4000)
    private String draftText;

    @Size(max = 4000)
    private String specialization;

    @Size(max = 20000)
    private String practiceDescription;

    @Size(max = 4000)
    private String finalExamDescription;

    @Size(max = 4000)
    private String optionalStudyDescription;

    @Size(max = 20000)
    private String description;
    private LocalDate ehisChanged;

    @Size(max = 1000)
    private String contactPerson;

    @Size(max = 255)
    private String nameGenitiveEt;

    @Size(max = 255)
    private String nameGenitiveEn;

    @Size(max = 1000)
    private String languageDescription;

    @Size(max = 1000)
    private String otherLanguages;

    @Size(max = 20000)
    private String objectivesEt;

    @Size(max = 20000)
    private String objectivesEn;

    @Size(max = 20000)
    private String addInfo;

    private LocalDate merRegDate;
    private LocalDate accreditationDate;

    @Size(max = 1000)
    private String accreditationResolution;
    private LocalDate accreditationValidDate;

    @Size(max = 1000)
    private String accreditationNr;

    @NotNull
    @Column(name = "is_occupation")
    private Boolean occupation;

    // Study period in months
    @NotNull
    private Integer studyPeriod;

    @NotNull
    @Column(name = "is_joint")
    private Boolean joint;

    @NotNull
    private Integer optionalStudyCredits;

    @NotNull
    private LocalDate validFrom;
    private LocalDate validThru;

    @ManyToOne()
    private Classifier group;

    @ManyToOne()
    private Classifier ehisStatus;

    @ManyToOne()
    private Classifier jointMentor;

    @ManyToOne()
    private StateCurriculum stateCurriculum;

    @ManyToOne(optional = false)
    private Classifier consecution;

    @ManyToOne(optional = false)
    private Classifier origStudyLevel;

    @ManyToOne()
    private Classifier iscedClass;

    @ManyToOne(optional = false)
    private Classifier status;

    @ManyToOne(optional = false)
    private School school;

    @ManyToOne(optional = false)
    private Classifier draft;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumStudyLanguage> studyLanguages = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumDepartment> departments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumFile> files = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumGrade> grades = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumJointPartner> jointPartners = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumSpeciality> specialities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumStudyForm> studyForms = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumModule> modules = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumOccupation> occupations = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false, insertable = true)
    private Set<CurriculumVersion> versions = new HashSet<>();

    public Boolean isHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean isOccupation() {
        return occupation;
    }

    public void setOccupation(Boolean occupation) {
        this.occupation = occupation;
    }

    public Boolean isJoint() {
        return joint;
    }

    public void setJoint(Boolean joint) {
        this.joint = joint;
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

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Classifier getConsecution() {
        return consecution;
    }

    public void setConsecution(Classifier consecution) {
        this.consecution = consecution;
    }

    public Classifier getOrigStudyLevel() {
        return origStudyLevel;
    }

    public void setOrigStudyLevel(Classifier origStudyLevel) {
        this.origStudyLevel = origStudyLevel;
    }

    public Classifier getIscedClass() {
        return iscedClass;
    }

    public void setIscedClass(Classifier iscedClass) {
        this.iscedClass = iscedClass;
    }

    public Integer getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Integer studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Integer getOptionalStudyCredits() {
        return optionalStudyCredits;
    }

    public void setOptionalStudyCredits(Integer optionalStudyCredits) {
        this.optionalStudyCredits = optionalStudyCredits;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Classifier getDraft() {
        return draft;
    }

    public void setDraft(Classifier draft) {
        this.draft = draft;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public LocalDate getApproval() {
        return approval;
    }

    public void setApproval(LocalDate approval) {
        this.approval = approval;
    }

    public String getApprovalDokNr() {
        return approvalDokNr;
    }

    public void setApprovalDokNr(String approvalDokNr) {
        this.approvalDokNr = approvalDokNr;
    }

    public String getOutcomesEt() {
        return outcomesEt;
    }

    public void setOutcomesEt(String outcomesEt) {
        this.outcomesEt = outcomesEt;
    }

    public String getOutcomesEn() {
        return outcomesEn;
    }

    public void setOutcomesEn(String outcomesEn) {
        this.outcomesEn = outcomesEn;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getAdmissionRequirementsEt() {
        return admissionRequirementsEt;
    }

    public void setAdmissionRequirementsEt(String admissionRequirementsEt) {
        this.admissionRequirementsEt = admissionRequirementsEt;
    }

    public String getAdmissionRequirementsEn() {
        return admissionRequirementsEn;
    }

    public void setAdmissionRequirementsEn(String admissionRequirementsEn) {
        this.admissionRequirementsEn = admissionRequirementsEn;
    }

    public String getGraduationRequirementsEt() {
        return graduationRequirementsEt;
    }

    public void setGraduationRequirementsEt(String graduationRequirementsEt) {
        this.graduationRequirementsEt = graduationRequirementsEt;
    }

    public String getGraduationRequirementsEn() {
        return graduationRequirementsEn;
    }

    public void setGraduationRequirementsEn(String graduationRequirementsEn) {
        this.graduationRequirementsEn = graduationRequirementsEn;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public String getDraftText() {
        return draftText;
    }

    public void setDraftText(String draftText) {
        this.draftText = draftText;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPracticeDescription() {
        return practiceDescription;
    }

    public void setPracticeDescription(String practiceDescription) {
        this.practiceDescription = practiceDescription;
    }

    public String getFinalExamDescription() {
        return finalExamDescription;
    }

    public void setFinalExamDescription(String finalExamDescription) {
        this.finalExamDescription = finalExamDescription;
    }

    public String getOptionalStudyDescription() {
        return optionalStudyDescription;
    }

    public void setOptionalStudyDescription(String optionalStudyDescription) {
        this.optionalStudyDescription = optionalStudyDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEhisChanged() {
        return ehisChanged;
    }

    public void setEhisChanged(LocalDate ehisChanged) {
        this.ehisChanged = ehisChanged;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getNameGenitiveEt() {
        return nameGenitiveEt;
    }

    public void setNameGenitiveEt(String nameGenitiveEt) {
        this.nameGenitiveEt = nameGenitiveEt;
    }

    public String getNameGenitiveEn() {
        return nameGenitiveEn;
    }

    public void setNameGenitiveEn(String nameGenitiveEn) {
        this.nameGenitiveEn = nameGenitiveEn;
    }

    public String getLanguageDescription() {
        return languageDescription;
    }

    public void setLanguageDescription(String languageDescription) {
        this.languageDescription = languageDescription;
    }

    public String getOtherLanguages() {
        return otherLanguages;
    }

    public void setOtherLanguages(String otherLanguages) {
        this.otherLanguages = otherLanguages;
    }

    public String getObjectivesEt() {
        return objectivesEt;
    }

    public void setObjectivesEt(String objectivesEt) {
        this.objectivesEt = objectivesEt;
    }

    public String getObjectivesEn() {
        return objectivesEn;
    }

    public void setObjectivesEn(String objectivesEn) {
        this.objectivesEn = objectivesEn;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public LocalDate getAccreditationDate() {
        return accreditationDate;
    }

    public void setAccreditationDate(LocalDate accreditationDate) {
        this.accreditationDate = accreditationDate;
    }

    public String getAccreditationResolution() {
        return accreditationResolution;
    }

    public void setAccreditationResolution(String accreditationResolution) {
        this.accreditationResolution = accreditationResolution;
    }

    public LocalDate getAccreditationValidDate() {
        return accreditationValidDate;
    }

    public void setAccreditationValidDate(LocalDate accreditationValidDate) {
        this.accreditationValidDate = accreditationValidDate;
    }

    public String getAccreditationNr() {
        return accreditationNr;
    }

    public void setAccreditationNr(String accreditationNr) {
        this.accreditationNr = accreditationNr;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public Classifier getGroup() {
        return group;
    }

    public void setGroup(Classifier group) {
        this.group = group;
    }

    public Classifier getEhisStatus() {
        return ehisStatus;
    }

    public void setEhisStatus(Classifier ehisStatus) {
        this.ehisStatus = ehisStatus;
    }

    public Classifier getJointMentor() {
        return jointMentor;
    }

    public void setJointMentor(Classifier jointMentor) {
        this.jointMentor = jointMentor;
    }

    public StateCurriculum getStateCurriculum() {
        return stateCurriculum;
    }

    public void setStateCurriculum(StateCurriculum stateCurriculum) {
        this.stateCurriculum = stateCurriculum;
    }

    public Set<CurriculumStudyLanguage> getStudyLanguages() {
        return studyLanguages;
    }

    public void setStudyLanguages(Set<CurriculumStudyLanguage> studyLanguages) {
        if (studyLanguages != null) {
            if (this.getStudyLanguages() == null) {
                this.studyLanguages = new HashSet<>();
            } else {
                this.getStudyLanguages().clear();
            }
            this.getStudyLanguages().addAll(studyLanguages);
        }
    }

    public Set<CurriculumDepartment> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<CurriculumDepartment> departments) {
        if (departments != null) {
            if (this.getDepartments() == null) {
                this.departments = new HashSet<>();
            } else {
                this.getDepartments().clear();
            }
            this.getDepartments().addAll(departments);
        }
    }

    public Set<CurriculumFile> getFiles() {
        return files;
    }

    public void setFiles(Set<CurriculumFile> files) {
        if (files != null) {
            if (this.getFiles() == null) {
                this.files = new HashSet<>();
            } else {
                this.getFiles().clear();
            }
            this.getFiles().addAll(files);
        }
    }

    public Set<CurriculumGrade> getGrades() {
        return grades;
    }

    public void setGrades(Set<CurriculumGrade> grades) {
        if (grades != null) {
            if (this.getGrades() == null) {
                this.grades = new HashSet<>();
            } else {
                this.getGrades().clear();
            }
            this.getGrades().addAll(grades);
        }
    }

    public Set<CurriculumJointPartner> getJointPartners() {
        return jointPartners;
    }

    public void setJointPartners(Set<CurriculumJointPartner> jointPartners) {
        if (jointPartners != null) {
            if (this.getJointPartners() == null) {
                this.jointPartners = new HashSet<>();
            } else {
                this.getJointPartners().clear();
            }
            this.getJointPartners().addAll(jointPartners);
        }
    }

    public Set<CurriculumSpeciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<CurriculumSpeciality> specialities) {
        if (specialities != null) {
            if (this.getSpecialities() == null) {
                this.specialities = new HashSet<>();
            } else {
                this.getSpecialities().clear();
            }
            this.getSpecialities().addAll(specialities);
        }
    }

    public Set<CurriculumStudyForm> getStudyForms() {
        return studyForms;
    }

    public void setStudyForms(Set<CurriculumStudyForm> studyForms) {
        if (studyForms != null) {
            if (this.getStudyForms() == null) {
                this.studyForms = new HashSet<>();
            } else {
                this.getStudyForms().clear();
            }
            this.getStudyForms().addAll(studyForms);
        }
    }

    public Set<CurriculumModule> getModules() {
        return modules;
    }
    
    public Set<CurriculumVersion> getVersions() {
        return versions;
    }

    public void setVersions(Set<CurriculumVersion> versions) {
        if(versions != null) {
            if(this.getVersions() == null) {
                this.versions = versions;
            } else {
                this.getVersions().clear();
            }
            this.getVersions().addAll(versions);
        }
        this.versions = versions;
    }

    public void setModules(Set<CurriculumModule> modules) {
        this.modules = modules;
    }

    public Set<CurriculumOccupation> getOccupations() {
        return occupations;
    }

    public void setOccupations(Set<CurriculumOccupation> occupations) {
        this.occupations = occupations;
    }

    public LocalDate getMerRegDate() {
        return merRegDate;
    }

    public void setMerRegDate(LocalDate merRegDate) {
        this.merRegDate = merRegDate;
    }

}
