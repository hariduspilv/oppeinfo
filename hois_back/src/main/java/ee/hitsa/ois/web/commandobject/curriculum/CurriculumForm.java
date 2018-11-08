package ee.hitsa.ois.web.commandobject.curriculum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.CurriculumValidator.Confirmed;
import ee.hitsa.ois.validation.CurriculumValidator.ConfirmedHigher;
import ee.hitsa.ois.validation.CurriculumValidator.ConfirmedVocational;
import ee.hitsa.ois.validation.CurriculumValidator.Joint;
import ee.hitsa.ois.validation.CurriculumValidator.Vocational;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.curriculum.CurriculumFileUpdateDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumJointPartnerDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSpecialityDto;

@DateRange
public class CurriculumForm extends VersionedCommand {
    
    @NotNull
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
    private String merCode; // XXX for EHIS this should be number (see EhisStudentService)
    @NotNull(groups = {Confirmed.class})
    private LocalDate approval;
    @Size(max = 50)
    @NotNull(groups = {Confirmed.class})
    private String approvalDokNr;
    @Size(max = 20000)
    @NotNull(groups = {Confirmed.class})
    private String outcomesEt;
    @Size(max = 20000)
    @NotNull(groups = {ConfirmedHigher.class})
    private String outcomesEn;
    @Size(max = 20000)
    private String structure;
    @Size(max = 20000)
    @NotNull(groups = {Confirmed.class})
    private String admissionRequirementsEt;
    @Size(max = 20000)
    @NotNull(groups = {ConfirmedHigher.class})
    private String admissionRequirementsEn;
    @Size(max = 20000)
    @NotNull(groups = {Confirmed.class})
    private String graduationRequirementsEt;
    @Size(max = 20000)
    @NotNull(groups = {ConfirmedHigher.class})
    private String graduationRequirementsEn;
    @Min(0)
    @Max(999)
    private BigDecimal credits;
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
    @NotNull(groups = {ConfirmedHigher.class})
    private String objectivesEt;
    @Size(max = 20000)
    @NotNull(groups = {ConfirmedHigher.class})
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
    @Size(max = 255)
    private String final21;
    @Size(max = 255)
    private String final31;
    @Size(max = 255)
    private String final33;
    @Size(max = 255)
    private String final51;
    @Size(max = 255)
    private String final52;
    @Size(max = 255)
    private String final61;
    @Size(max = 255)
    private String finalEn31;
    @Size(max = 255)
    private String finalEn33;
    @Size(max = 255)
    private String finalEn51;
    @Size(max = 255)
    private String finalEn52;
    @Size(max = 255)
    private String finalEn61;
    private Boolean occupation = Boolean.FALSE;
    @NotNull
    @Min(0)
    @Max(10000)
    private Integer studyPeriod;
    @NotNull
    private Boolean joint;
    @Min(0)
    @Max(999)
    private BigDecimal optionalStudyCredits;
    @NotNull(groups = {Confirmed.class})
    private LocalDate validFrom;
    private LocalDate validThru;

    @ClassifierRestriction(MainClassCode.OPPEKAVAGRUPP)
    private String group;
    @ClassifierRestriction(MainClassCode.OPPEKAVA_EHIS_STAATUS)
    private String ehisStatus;
    @ClassifierRestriction(MainClassCode.EHIS_KOOL)
    private String jointMentor;
    @NotNull
    @ClassifierRestriction(MainClassCode.OPPEKAVA_TYPE)
    private String consecution;
    @NotNull
    @ClassifierRestriction({MainClassCode.EKR, MainClassCode.OPPEASTE})
    private String origStudyLevel;
    @NotNull(groups = {Vocational.class})
    @ClassifierRestriction({MainClassCode.ISCED_RYHM, MainClassCode.ISCED_VALD, MainClassCode.ISCED_SUUN})
    private String iscedClass;
    @NotNull
    @ClassifierRestriction(MainClassCode.OPPEKAVA_LOOMISE_VIIS)
    private String draft;
    @NotEmpty
    private Set<String> studyLanguages;
    @NotEmpty(groups = {ConfirmedVocational.class})
    private Set<String> studyForms;
    @NotEmpty(groups = {Confirmed.class})
    private Set<CurriculumAddressForm> addresses;
    @NotEmpty(groups = {Confirmed.class})
    private Set<Long> schoolDepartments;
    @NotEmpty(groups = {Confirmed.class})
    private Set<CurriculumFileUpdateDto> files;
    @Valid
    private Set<CurriculumGradeDto> grades;
    @Valid
    @NotEmpty(groups = {Joint.class})
    private Set<CurriculumJointPartnerDto> jointPartners;
    @Valid
    @NotEmpty(groups = {ConfirmedHigher.class})
    private Set<CurriculumSpecialityDto> specialities;
    @NotEmpty(groups = {ConfirmedVocational.class})
    @Valid
    private Set<CurriculumModuleDto> modules;

    private String contractEt;
    private String contractEn;
    private String supervisor;

    public Set<CurriculumFileUpdateDto> getFiles() {
        return files;
    }

    public void setFiles(Set<CurriculumFileUpdateDto> files) {
        this.files = files;
    }

    public Set<CurriculumJointPartnerDto> getJointPartners() {
        return jointPartners != null ? jointPartners : (jointPartners = new HashSet<>());
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

    public Set<CurriculumGradeDto> getGrades() {
        return grades;
    }

    public void setGrades(Set<CurriculumGradeDto> grades) {
        this.grades = grades;
    }

    public Set<String> getStudyForms() {
        return studyForms;
    }

    public void setStudyForms(Set<String> studyForms) {
        this.studyForms = studyForms;
    }

    public Set<CurriculumAddressForm> getAddresses() {
        return addresses != null ? addresses : (addresses = new HashSet<>());
    }

    public void setAddresses(Set<CurriculumAddressForm> addresses) {
        this.addresses = addresses;
    }

    public Set<Long> getSchoolDepartments() {
        return schoolDepartments;
    }

    public void setSchoolDepartments(Set<Long> schoolDepartments) {
        this.schoolDepartments = schoolDepartments;
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

    public String getFinal21() {
        return final21;
    }

    public void setFinal21(String final21) {
        this.final21 = final21;
    }

    public String getFinal31() {
        return final31;
    }

    public void setFinal31(String final31) {
        this.final31 = final31;
    }

    public String getFinal33() {
        return final33;
    }

    public void setFinal33(String final33) {
        this.final33 = final33;
    }

    public String getFinal51() {
        return final51;
    }

    public void setFinal51(String final51) {
        this.final51 = final51;
    }

    public String getFinal52() {
        return final52;
    }

    public void setFinal52(String final52) {
        this.final52 = final52;
    }

    public String getFinal61() {
        return final61;
    }

    public void setFinal61(String final61) {
        this.final61 = final61;
    }

    public String getFinalEn31() {
        return finalEn31;
    }

    public void setFinalEn31(String finalEn31) {
        this.finalEn31 = finalEn31;
    }

    public String getFinalEn33() {
        return finalEn33;
    }

    public void setFinalEn33(String finalEn33) {
        this.finalEn33 = finalEn33;
    }

    public String getFinalEn51() {
        return finalEn51;
    }

    public void setFinalEn51(String finalEn51) {
        this.finalEn51 = finalEn51;
    }

    public String getFinalEn52() {
        return finalEn52;
    }

    public void setFinalEn52(String finalEn52) {
        this.finalEn52 = finalEn52;
    }

    public String getFinalEn61() {
        return finalEn61;
    }

    public void setFinalEn61(String finalEn61) {
        this.finalEn61 = finalEn61;
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

    public BigDecimal getOptionalStudyCredits() {
        return optionalStudyCredits;
    }

    public void setOptionalStudyCredits(BigDecimal optionalStudyCredits) {
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
