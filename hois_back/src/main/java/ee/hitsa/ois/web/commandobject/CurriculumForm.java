package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.domain.StateCurriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumJointPartner;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;

public class CurriculumForm extends VersionedCommand {

    private Long id;
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;
    private boolean higher;
    private String nameEt;
    private String nameEn;
    private String nameRu;
    private String code;
    private String merCode;
    private LocalDate approval;
    private String approvalDokNr;
    private String outcomesEt;
    private String outcomesEn;
    private String structure;
    private String admissionRequirementsEt;
    private String admissionRequirementsEn;
    private String graduationRequirementsEt;
    private String graduationRequirementsEn;
    private Double credits;
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
    private String objectivesEt;
    private String objectivesEn;
    private String addInfo;
    private LocalDate merRegDate;
    private LocalDate accreditationDate;
    private String accreditationResolution;
    private LocalDate accreditationValidDate;
    private String accreditationNr;
    private boolean occupation;
    private Integer studyPeriod;
    private boolean joint;
    private Integer optionalStudyCredits;
    private LocalDate validFrom;
    private LocalDate validThru;
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier group;
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier ehisStatus;
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier jointMentor;
    private StateCurriculum stateCurriculum;
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier consecution;
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier origStudyLevel;
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier iscedClass;
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier status;
    @JsonDeserialize(using = ClassifierJsonDeserializer.class)
    private Classifier draft;

    private Set<Classifier> studyLanguageClassifiers;
    private Set<Classifier> studyFormClassifiers;
    private Set<SchoolDepartment> schoolDepartments;

    private Set<CurriculumFile> files;
    private Set<CurriculumGrade> grades;
    private Set<CurriculumJointPartner> jointPartners;
    private Set<CurriculumSpeciality> specialities;
    private Set<CurriculumModule> modules;
    private Set<CurriculumOccupation> occupations;
    private Set<CurriculumVersion> versions;

    private String contractEt;
    private String contractEn;
    private String supervisor;
    
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
    public Set<SchoolDepartment> getSchoolDepartments() {
		return schoolDepartments;
	}
	public void setSchoolDepartments(Set<SchoolDepartment> schoolDepartments) {
		this.schoolDepartments = schoolDepartments;
	}
	public Set<Classifier> getStudyLanguageClassifiers() {
		return studyLanguageClassifiers;
	}
	public void setStudyLanguageClassifiers(Set<Classifier> studyLanguageClassifiers) {
		this.studyLanguageClassifiers = studyLanguageClassifiers;
	}
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getInserted() {
        return inserted;
    }
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
    public String getInsertedBy() {
        return insertedBy;
    }
    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }
    public LocalDateTime getChanged() {
        return changed;
    }
    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }
    public String getChangedBy() {
        return changedBy;
    }
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
    public boolean isHigher() {
        return higher;
    }
    public void setHigher(boolean higher) {
        this.higher = higher;
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
    public LocalDate getMerRegDate() {
        return merRegDate;
    }
    public void setMerRegDate(LocalDate merRegDate) {
        this.merRegDate = merRegDate;
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
    public boolean isOccupation() {
        return occupation;
    }
    public void setOccupation(boolean occupation) {
        this.occupation = occupation;
    }
    public Integer getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Integer studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public boolean isJoint() {
        return joint;
    }
    public void setJoint(boolean joint) {
        this.joint = joint;
    }
    public Integer getOptionalStudyCredits() {
        return optionalStudyCredits;
    }
    public void setOptionalStudyCredits(Integer optionalStudyCredits) {
        this.optionalStudyCredits = optionalStudyCredits;
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
    public Classifier getStatus() {
        return status;
    }
    public void setStatus(Classifier status) {
        this.status = status;
    }
    public Classifier getDraft() {
        return draft;
    }
    public void setDraft(Classifier draft) {
        this.draft = draft;
    }
    public Set<CurriculumFile> getFiles() {
        return files;
    }
    public void setFiles(Set<CurriculumFile> files) {
        this.files = files;
    }
    public Set<CurriculumGrade> getGrades() {
        return grades;
    }
    public void setGrades(Set<CurriculumGrade> grades) {
        this.grades = grades;
    }
    public Set<CurriculumJointPartner> getJointPartners() {
        return jointPartners;
    }
    public void setJointPartners(Set<CurriculumJointPartner> jointPartners) {
        this.jointPartners = jointPartners;
    }
    public Set<CurriculumSpeciality> getSpecialities() {
        return specialities;
    }
    public void setSpecialities(Set<CurriculumSpeciality> specialities) {
        this.specialities = specialities;
    }
    public String getContractEt() {
        return contractEt;
    }
    public void setContractEt(String contractEt) {
        this.contractEt = contractEt;
    }
    public String getContractEn() {
        return contractEn;
    }
    public void setContractEn(String contractEn) {
        this.contractEn = contractEn;
    }
    public String getSupervisor() {
        return supervisor;
    }
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
    public Set<Classifier> getStudyFormClassifiers() {
        return studyFormClassifiers;
    }
    public void setStudyFormClassifiers(Set<Classifier> studyFormClassifiers) {
        this.studyFormClassifiers = studyFormClassifiers;
    }
    public Set<CurriculumModule> getModules() {
        return modules;
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

}