package ee.hitsa.ois.xml.curriculum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ee.hitsa.ois.LocalDateTimeXmlAdapter;
import ee.hitsa.ois.LocalDateXmlAdapter;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

@XmlRootElement(name="curriculum")
public class CurriculumXml {

    private Long id;
    private Long version;
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;
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
    private String objectivesEt;
    private String objectivesEn;
    private String addInfo;
    private LocalDate merRegDate;
    private LocalDate accreditationDate;
    private String accreditationResolution;
    private LocalDate accreditationValidDate;
    private String accreditationNr;
    private Boolean occupation = Boolean.FALSE;
    private Integer studyPeriod;
    private Boolean joint;
    private BigDecimal optionalStudyCredits;
    private LocalDate validFrom;
    private LocalDate validThru;
    private String contractEt;
    private String contractEn;
    private String supervisor;
    
    // classifiers
    private String status;
    private String draft;
    private String iscedClass;
    private String origStudyLevel;
    private String consecution;
    private String jointMentor;
    private String group;
    private String ehisStatus;
    
    // objects
    private String school;
    private Set<String> schoolDepartments;
    private Set<String> studyLanguages;

    private Set<CurriculumJointPartnerXml> jointPartners;
    private Set<CurriculumFileXml> files;
    private Set<CurriculumGradeXml> grades;
    private Set<CurriculumSpecialityXml> specialities;
    private Set<CurriculumVersionXml> versions;
    
    public static CurriculumXml ofWithoutVersions(Curriculum curriculum) {
        CurriculumXml xml = EntityUtil.bindToDto
                (curriculum, new CurriculumXml(), 
                 "versions", "studyLanguages", "studyForms", "schoolDepartments", "files", 
                 "jointPartners", "specialities", "modules", "occupations", "grades", 
                 "status", "draft", "iscedClass", "origStudyLevel", "consecution", "jointMentor", "group", "ehisStatus", "school");
        xml.setStatus(ClassifierUtil.getNullableNameEt(curriculum.getStatus()));
        xml.setDraft(ClassifierUtil.getNullableNameEt(curriculum.getDraft()));
        xml.setIscedClass(ClassifierUtil.getNullableNameEt(curriculum.getIscedClass()));
        xml.setOrigStudyLevel(ClassifierUtil.getNullableNameEt(curriculum.getOrigStudyLevel()));
        xml.setConsecution(ClassifierUtil.getNullableNameEt(curriculum.getConsecution()));
        xml.setJointMentor(ClassifierUtil.getNullableNameEt(curriculum.getJointMentor()));
        xml.setGroup(ClassifierUtil.getNullableNameEt(curriculum.getGroup()));
        xml.setEhisStatus(ClassifierUtil.getNullableNameEt(curriculum.getEhisStatus()));
        
        xml.setSchool(curriculum.getSchool().getNameEt());
        xml.setSchoolDepartments(StreamUtil.toMappedSet(d -> d.getSchoolDepartment().getNameEt(), curriculum.getDepartments()));
        xml.setStudyLanguages(StreamUtil.toMappedSet(d -> ClassifierUtil.getNullableNameEt(d.getStudyLang()), curriculum.getStudyLanguages()));
        
        xml.setJointPartners(StreamUtil.toMappedSet(CurriculumJointPartnerXml::of, curriculum.getJointPartners()));
        xml.setFiles(StreamUtil.toMappedSet(CurriculumFileXml::of, curriculum.getFiles()));
        xml.setGrades(StreamUtil.toMappedSet(CurriculumGradeXml::of, curriculum.getGrades()));
        xml.setSpecialities(StreamUtil.toMappedSet(CurriculumSpecialityXml::of, curriculum.getSpecialities()));
        return xml;
    }

    public static CurriculumXml of(Curriculum curriculum) {
        CurriculumXml xml = ofWithoutVersions(curriculum);
        xml.setVersions(StreamUtil.toMappedSet(CurriculumVersionXml::of, curriculum.getVersions()));
        return xml;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    @XmlJavaTypeAdapter(type=LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)
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

    @XmlJavaTypeAdapter(type=LocalDateTime.class, value = LocalDateTimeXmlAdapter.class)
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

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
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
    
    @XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateXmlAdapter.class)
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

    @XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateXmlAdapter.class)
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
    
    @XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateXmlAdapter.class)
    public LocalDate getMerRegDate() {
        return merRegDate;
    }

    public void setMerRegDate(LocalDate merRegDate) {
        this.merRegDate = merRegDate;
    }

    @XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateXmlAdapter.class)
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

    @XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateXmlAdapter.class)
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

    public Boolean getOccupation() {
        return occupation;
    }

    public void setOccupation(Boolean occupation) {
        this.occupation = occupation;
    }

    public Integer getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Integer studyPeriod) {
        this.studyPeriod = studyPeriod;
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

    @XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateXmlAdapter.class)
    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    @XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateXmlAdapter.class)
    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
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

    public String getIscedClass() {
        return iscedClass;
    }

    public void setIscedClass(String iscedClass) {
        this.iscedClass = iscedClass;
    }

    public String getOrigStudyLevel() {
        return origStudyLevel;
    }

    public void setOrigStudyLevel(String origStudyLevel) {
        this.origStudyLevel = origStudyLevel;
    }

    public String getConsecution() {
        return consecution;
    }

    public void setConsecution(String consecution) {
        this.consecution = consecution;
    }

    public String getJointMentor() {
        return jointMentor;
    }

    public void setJointMentor(String jointMentor) {
        this.jointMentor = jointMentor;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @XmlElementWrapper(name = "schoolDepartments")
    @XmlElement(name="nameEt")
    public Set<String> getSchoolDepartments() {
        return schoolDepartments;
    }

    public void setSchoolDepartments(Set<String> schoolDepartments) {
        this.schoolDepartments = schoolDepartments;
    }

    @XmlElementWrapper(name = "studyLanguages")
    @XmlElement(name="nameEt")
    public Set<String> getStudyLanguages() {
        return studyLanguages;
    }

    public void setStudyLanguages(Set<String> studyLanguages) {
        this.studyLanguages = studyLanguages;
    }

    @XmlElementWrapper(name = "jointPartners")
    @XmlElement(name="jointPartner")
    public Set<CurriculumJointPartnerXml> getJointPartners() {
        return jointPartners;
    }

    public void setJointPartners(Set<CurriculumJointPartnerXml> jointPartners) {
        this.jointPartners = jointPartners;
    }

    @XmlElementWrapper(name = "files")
    @XmlElement(name="file")
    public Set<CurriculumFileXml> getFiles() {
        return files;
    }

    public void setFiles(Set<CurriculumFileXml> files) {
        this.files = files;
    }
    
    @XmlElementWrapper(name = "grades")
    @XmlElement(name="grade")
    public Set<CurriculumGradeXml> getGrades() {
        return grades;
    }

    public void setGrades(Set<CurriculumGradeXml> grades) {
        this.grades = grades;
    }
    
    @XmlElementWrapper(name = "specialities")
    @XmlElement(name="speciality")
    public Set<CurriculumSpecialityXml> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<CurriculumSpecialityXml> specialities) {
        this.specialities = specialities;
    }
    
    @XmlElementWrapper(name = "curriculumVersions")
    @XmlElement(name="curriculumVersion")
    public Set<CurriculumVersionXml> getVersions() {
        return versions;
    }

    public void setVersions(Set<CurriculumVersionXml> versions) {
        this.versions = versions;
    }
}
