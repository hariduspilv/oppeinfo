package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.Set;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.dto.curriculum.CurriculumFileDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumJointPartnerDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionDto;

/*
 * TODO: apply Classifier validation by mainClassCode
 * TODO: apply @Valid validation to child classes
 */
@DateRange
public class CurriculumForm extends VersionedCommand {

    private Boolean higher;
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
    private Boolean occupation;
    private Integer studyPeriod;
    private Boolean joint;
    private Integer optionalStudyCredits;
    private LocalDate validFrom;
    private LocalDate validThru;

    private Long stateCurriculum;
    @ClassifierRestriction(MainClassCode.OPPEKAVAGRUPP)
    private String group;
    @ClassifierRestriction(MainClassCode.OPPEKAVA_EHIS_STAATUS)
    private String ehisStatus;
    @ClassifierRestriction(MainClassCode.EHIS_KOOL)
    private String jointMentor;
    @ClassifierRestriction(MainClassCode.OPPEKAVA_TYPE)
    private String consecution;
    @ClassifierRestriction({MainClassCode.EKR, MainClassCode.OPPEASTE})
    private String origStudyLevel;
    // Classifiers with different mainClassCode can be stored here
    // handled in CurriculumService
    @ClassifierRestriction({MainClassCode.ISCED_RYHM, MainClassCode.ISCED_VALD, MainClassCode.ISCED_SUUND})
    private String iscedClass;
    @ClassifierRestriction(MainClassCode.OPPEKAVA_STAATUS)
    private String status;
    @ClassifierRestriction(MainClassCode.OPPEKAVA_LOOMISE_VIIS)
    private String draft;

    private Set<String> studyLanguages;
    private Set<String> studyForms;
    private Set<Long> schoolDepartments;

    private Set<CurriculumFileDto> files;
    private Set<CurriculumGradeDto> grades;
    private Set<CurriculumJointPartnerDto> jointPartners;
    private Set<CurriculumSpecialityDto> specialities;
    private Set<CurriculumModuleDto> modules;
    private Set<CurriculumOccupationDto> occupations;
    private Set<CurriculumVersionDto> versions;

    private String contractEt;
    private String contractEn;
    private String supervisor;

    public Set<CurriculumJointPartnerDto> getJointPartners() {
        return jointPartners;
    }

    public void setJointPartners(Set<CurriculumJointPartnerDto> jointPartners) {
        this.jointPartners = jointPartners;
    }

    public Set<CurriculumSpecialityDto> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<CurriculumSpecialityDto> specialities) {
        this.specialities = specialities;
    }

    public Set<CurriculumModuleDto> getModules() {
        return modules;
    }

    public void setModules(Set<CurriculumModuleDto> modules) {
        this.modules = modules;
    }

    public Set<CurriculumOccupationDto> getOccupations() {
        return occupations;
    }

    public void setOccupations(Set<CurriculumOccupationDto> occupations) {
        this.occupations = occupations;
    }

    public Set<CurriculumVersionDto> getVersions() {
        return versions;
    }

    public Set<CurriculumGradeDto> getGrades() {
        return grades;
    }

    public void setGrades(Set<CurriculumGradeDto> grades) {
        this.grades = grades;
    }

    public Set<CurriculumFileDto> getFiles() {
        return files;
    }

    public void setFiles(Set<CurriculumFileDto> files) {
        this.files = files;
    }

    public Set<String> getStudyForms() {
        return studyForms;
    }

    public void setStudyForms(Set<String> studyForms) {
        this.studyForms = studyForms;
    }

    public Set<Long> getSchoolDepartments() {
        return schoolDepartments;
    }

    public void setSchoolDepartments(Set<Long> schoolDepartments) {
        this.schoolDepartments = schoolDepartments;
    }

    public void setVersions(Set<CurriculumVersionDto> versions) {
        this.versions = versions;
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

    public Integer getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Integer studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEhisStatus() {
        return ehisStatus;
    }

    public void setEhisStatus(String ehisStatus) {
        this.ehisStatus = ehisStatus;
    }

    public String getJointMentor() {
        return jointMentor;
    }

    public void setJointMentor(String jointMentor) {
        this.jointMentor = jointMentor;
    }

    public Long getStateCurriculum() {
        return stateCurriculum;
    }

    public void setStateCurriculum(Long stateCurriculum) {
        this.stateCurriculum = stateCurriculum;
    }

    public String getConsecution() {
        return consecution;
    }

    public void setConsecution(String consecution) {
        this.consecution = consecution;
    }

    public String getOrigStudyLevel() {
        return origStudyLevel;
    }

    public void setOrigStudyLevel(String origStudyLevel) {
        this.origStudyLevel = origStudyLevel;
    }

    public String getIscedClass() {
        return iscedClass;
    }

    public void setIscedClass(String iscedClass) {
        this.iscedClass = iscedClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public Set<String> getStudyLanguages() {
        return studyLanguages;
    }

    public void setStudyLanguages(Set<String> studyLanguages) {
        this.studyLanguages = studyLanguages;
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
}