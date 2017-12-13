package ee.hitsa.ois.domain.curriculum;

import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.validation.CurriculumValidator.Confirmed;
import ee.hitsa.ois.validation.CurriculumValidator.ConfirmedHigher;
import ee.hitsa.ois.validation.CurriculumValidator.ConfirmedVocational;
import ee.hitsa.ois.validation.CurriculumValidator.Joint;

@Entity
public class Curriculum extends BaseEntityWithId implements Translatable {
    private static final long serialVersionUID = -7063602940937795603L;

    @Column(name = "is_higher")
    private Boolean higher;
    private String nameEt;
    private String nameEn;
    private String nameRu;
    private String code;
    private String merCode; // XXX for EHIS this should be number (see EhisStudentService)
    private LocalDate approval;
    private String approvalDokNr;
    @NotNull(groups = {Confirmed.class})
    private String outcomesEt;
    @NotNull(groups = {ConfirmedHigher.class})
    private String outcomesEn;
    private String structure;
    @NotNull(groups = {Confirmed.class})
    private String admissionRequirementsEt;
    @NotNull(groups = {ConfirmedHigher.class})
    private String admissionRequirementsEn;
    @NotNull(groups = {Confirmed.class})
    private String graduationRequirementsEt;
    @NotNull(groups = {ConfirmedHigher.class})
    private String graduationRequirementsEn;
    private BigDecimal credits;
    private String draftText;
    private String specialization;
    private String practiceDescription;
    private String finalExamDescription;
    private String optionalStudyDescription;
    private String description;
    private LocalDate ehisChanged;
    private String contactPerson;
    private String nameGenitiveEt;
    private String nameGenitiveEn;
    private String languageDescription;
    private String otherLanguages;
    @NotNull(groups = {ConfirmedHigher.class})
    private String objectivesEt;
    @NotNull(groups = {ConfirmedHigher.class})
    private String objectivesEn;
    private String addInfo;
    private LocalDate merRegDate;
    private LocalDate accreditationDate;
    private String accreditationResolution;
    private LocalDate accreditationValidDate;
    private String accreditationNr;

    @Column(name = "is_occupation")
    private Boolean occupation;
    /**
     * Study period in months
     */
    private Integer studyPeriod;
    @Column(name = "is_joint")
    private Boolean joint;
    private BigDecimal optionalStudyCredits;
    @NotNull(groups = {Confirmed.class})
    private LocalDate validFrom;
    private LocalDate validThru;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier group;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier ehisStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier jointMentor;

    @ManyToOne(fetch = FetchType.LAZY)
    private StateCurriculum stateCurriculum;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier consecution;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier origStudyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier iscedClass;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier draft;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false)
    private Set<CurriculumStudyLanguage> studyLanguages = new HashSet<>();

    @NotEmpty(groups = {Confirmed.class})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false)
    private Set<CurriculumDepartment> departments = new HashSet<>();

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumFile> files = new HashSet<>();

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumGrade> grades = new HashSet<>();

    @NotEmpty(groups = {Joint.class})
    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumJointPartner> jointPartners = new HashSet<>();

    @NotEmpty(groups = {ConfirmedHigher.class})
    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumSpeciality> specialities = new HashSet<>();
    
    @NotEmpty(groups = {ConfirmedVocational.class})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curriculum_id", nullable = false, updatable = false)
    private Set<CurriculumStudyForm> studyForms = new HashSet<>();

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumModule> modules = new HashSet<>();

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumOccupation> occupations = new HashSet<>();

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurriculumVersion> versions = new HashSet<>();

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getOccupation() {
        return occupation;
    }

    public void setOccupation(Boolean occupation) {
        this.occupation = occupation;
    }

    public Boolean getJoint() {
        return joint;
    }

    public void setJoint(Boolean joint) {
        this.joint = joint;
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Override
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

    public BigDecimal getOptionalStudyCredits() {
        return optionalStudyCredits;
    }

    public void setOptionalStudyCredits(BigDecimal optionalStudyCredits) {
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

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
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
        return studyLanguages != null ? studyLanguages : (studyLanguages = new HashSet<>());
    }

    public void setStudyLanguages(Set<CurriculumStudyLanguage> studyLanguages) {
        getStudyLanguages().clear();
        getStudyLanguages().addAll(studyLanguages);
    }

    public Set<CurriculumDepartment> getDepartments() {
        return departments != null ? departments : (departments = new HashSet<>());
    }

    public void setDepartments(Set<CurriculumDepartment> departments) {
        getDepartments().clear();
        getDepartments().addAll(departments); 
    }

    public Set<CurriculumFile> getFiles() {
        return files != null ? files : (files = new HashSet<>());
    }

    public void setFiles(Set<CurriculumFile> files) {
        getFiles().clear();
        getFiles().addAll(files);
    }

    public Set<CurriculumGrade> getGrades() {
        return grades != null ? grades : (grades = new HashSet<>());
    }

    public void setGrades(Set<CurriculumGrade> grades) {
        getGrades().clear();
        getGrades().addAll(grades);
    }

    public Set<CurriculumJointPartner> getJointPartners() {
        return jointPartners != null ? jointPartners : (jointPartners = new HashSet<>());
    }

    public void setJointPartners(Set<CurriculumJointPartner> jointPartners) {
        getJointPartners().clear();
        getJointPartners().addAll(jointPartners);
    }

    public Set<CurriculumSpeciality> getSpecialities() {
        return specialities != null ? specialities : (specialities = new HashSet<>());
    }

    public void setSpecialities(Set<CurriculumSpeciality> specialities) {
        getSpecialities().clear();
        getSpecialities().addAll(specialities);
    }

    public Set<CurriculumStudyForm> getStudyForms() {
        return studyForms != null ? studyForms : (studyForms = new HashSet<>());
    }

    public void setStudyForms(Set<CurriculumStudyForm> studyForms) {
            getStudyForms().clear();
            getStudyForms().addAll(studyForms);
    }

    public Set<CurriculumModule> getModules() {
        return modules != null ? modules : (modules = new HashSet<>());
    }

    public Set<CurriculumVersion> getVersions() {
        return versions != null ? versions : (versions = new HashSet<>());
    }

    public void setVersions(Set<CurriculumVersion> versions) {
        getVersions().clear();
        getVersions().addAll(versions);
    }

    public void setModules(Set<CurriculumModule> modules) {
        getModules().clear();
        getModules().addAll(modules);
    }

    public Set<CurriculumOccupation> getOccupations() {
        return occupations != null ? occupations : (occupations = new HashSet<>());
    }

    public void setOccupations(Set<CurriculumOccupation> occupations) {
        getOccupations().clear();
        getOccupations().addAll(occupations);
    }

    public LocalDate getMerRegDate() {
        return merRegDate;
    }

    public void setMerRegDate(LocalDate merRegDate) {
        this.merRegDate = merRegDate;
    }
}
