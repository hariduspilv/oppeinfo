package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.StateCurriculumModuleDto;

public class StateCurriculumForm extends VersionedCommand {
    private String nameEt;
    private String nameEn;
    private String objectivesEt;
    private String objectivesEn;
    private String outcomesEt;
    private String outcomesEn;
    private String admissionRequirementsEt;
    private String admissionRequirementsEn;
    private String graduationRequirementsEt;
    private String graduationRequirementsEn;
    private Integer credits;
    private String practiceDescription;
    private Integer optionalStudyCredits;
    private LocalDate validFrom;
    private LocalDate validThru;
    private String description;
    private String riigiteatajaUrl;
    private String finalExamDescription;
    
    @ClassifierRestriction(MainClassCode.OPPEKAVA_STAATUS)
    private String status;
    @ClassifierRestriction(MainClassCode.ISCED_RYHM)
    private String iscedClass;
    @ClassifierRestriction(MainClassCode.EHIS_ROK)
    private String stateCurrClass;
    
    private Set<StateCurriculumModuleDto> modules = new HashSet<>();
    
    private Set<String> occupations = new HashSet<>();

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

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getPracticeDescription() {
        return practiceDescription;
    }

    public void setPracticeDescription(String practiceDescription) {
        this.practiceDescription = practiceDescription;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRiigiteatajaUrl() {
        return riigiteatajaUrl;
    }

    public void setRiigiteatajaUrl(String riigiteatajaUrl) {
        this.riigiteatajaUrl = riigiteatajaUrl;
    }

    public String getFinalExamDescription() {
        return finalExamDescription;
    }

    public void setFinalExamDescription(String finalExamDescription) {
        this.finalExamDescription = finalExamDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIscedClass() {
        return iscedClass;
    }

    public void setIscedClass(String iscedClass) {
        this.iscedClass = iscedClass;
    }

    public String getStateCurrClass() {
        return stateCurrClass;
    }

    public void setStateCurrClass(String stateCurrClass) {
        this.stateCurrClass = stateCurrClass;
    }

    public Set<StateCurriculumModuleDto> getModules() {
        return modules != null ? modules : (modules = new HashSet<>());
    }

    public void setModules(Set<StateCurriculumModuleDto> modules) {
        this.modules = modules;
    }

    public Set<String> getOccupations() {
        return occupations != null ? occupations : (occupations = new HashSet<>());
    }

    public void setOccupations(Set<String> occupations) {
        this.occupations = occupations;
    }
}
