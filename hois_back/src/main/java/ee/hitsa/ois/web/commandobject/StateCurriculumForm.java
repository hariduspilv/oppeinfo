package ee.hitsa.ois.web.commandobject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.StateCurriculumValidator.Confirmed;
import ee.hitsa.ois.web.dto.StateCurriculumModuleDto;

public class StateCurriculumForm extends VersionedCommand {
    @NotEmpty
    @Size(max=255)
    private String nameEt;
    @Size(max=255)
    private String nameEn;
    @NotEmpty(groups = {Confirmed.class})
    @Size(max=20000)
    private String objectivesEt;
    @Size(max=20000)
    private String objectivesEn;
    @NotEmpty(groups = {Confirmed.class})
    @Size(max=20000)
    private String outcomesEt;
    @Size(max=20000)
    private String outcomesEn;
    @NotEmpty(groups = {Confirmed.class})
    @Size(max=20000)
    private String admissionRequirementsEt;
    @Size(max=20000)
    private String admissionRequirementsEn;
    @NotEmpty(groups = {Confirmed.class})
    @Size(max=20000)
    private String graduationRequirementsEt;
    @Size(max=20000)
    private String graduationRequirementsEn;
    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal credits;
    @Size(max=20000)
    private String practiceDescription;
    @NotNull(groups = {Confirmed.class})
    @Min(0)
    @Max(999)
    private BigDecimal optionalStudyCredits;
    @NotNull(groups = {Confirmed.class})
    private LocalDate validFrom;
    private LocalDate validThru;
    @Size(max=20000)
    private String description;
    @Size(max=4000)
    private String riigiteatajaUrl;
    @Size(max=4000)
    private String finalExamDescription;

    @NotNull
    @ClassifierRestriction(MainClassCode.ISCED_RYHM)
    private String iscedClass;
    @NotNull
    @ClassifierRestriction(MainClassCode.EHIS_ROK)
    private String stateCurrClass;
    
    @Valid
    @NotEmpty(groups = {Confirmed.class})
    private Set<StateCurriculumModuleDto> modules = new HashSet<>();
    
    @ClassifierRestriction(MainClassCode.KUTSE)
    @NotEmpty(groups = {Confirmed.class})
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

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getPracticeDescription() {
        return practiceDescription;
    }

    public void setPracticeDescription(String practiceDescription) {
        this.practiceDescription = practiceDescription;
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
