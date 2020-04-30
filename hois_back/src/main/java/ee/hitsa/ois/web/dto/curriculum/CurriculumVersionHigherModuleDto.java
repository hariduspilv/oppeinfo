package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.CurriculumValidator.HigherModule;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionHigherModuleDto extends VersionedCommand {

    private Long id;
    @NotBlank
    @Size(max = 255)
    private String nameEt;

    @NotNull
    private Long curriculumVersion;

    @NotBlank
    @Size(max = 255)
    private String nameEn;

    @Min(0)
    @Max(32767)
    private Short orderNr;
    private String objectivesEt;
    private String objectivesEn;
    private String outcomesEt;
    private String outcomesEn;
    @Size(max = 255)
    private String typeNameEt;
    @Size(max = 255)
    private String typeNameEn;
    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal totalCredits;
    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal optionalStudyCredits;
    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal compulsoryStudyCredits;
    @NotNull
    @Min(0)
    @Max(999)
    private Short electiveModulesNumber;
    @NotNull
    private Boolean minorSpeciality;
    @Required
    @ClassifierRestriction(MainClassCode.KORGMOODUL)
    private String type;
    
    private Short studyYears;

    private Set<CurriculumVersionHigherModuleSubjectDto> subjects;

    private Set<CurriculumVersionElectiveModuleDto> electiveModules;

    /**
     * These are actually curriculum version specialties.
     * Used for saving
     */
    @Required(groups = {HigherModule.class})
    private Set<Long> specialitiesReferenceNumbers;
    
    /**
     * Used for displaying modules by specialties on curriculum version form
     */
    private Set<Long> curriculumSpecialities;
    

    public static CurriculumVersionHigherModuleDto onlyId(CurriculumVersionHigherModule module) {
        CurriculumVersionHigherModuleDto dto = new CurriculumVersionHigherModuleDto();
        dto.setId(EntityUtil.getId(module));
        return dto;
    }
    

    public static CurriculumVersionHigherModuleDto credits(CurriculumVersionHigherModule module) {
        CurriculumVersionHigherModuleDto dto = new CurriculumVersionHigherModuleDto();
        dto.setId(EntityUtil.getId(module));
        
        dto.setCompulsoryStudyCredits(module.getCompulsoryStudyCredits());
        dto.setTotalCredits(module.getTotalCredits());
        return dto;
    }

    public static CurriculumVersionHigherModuleDto of(CurriculumVersionHigherModule module) {
        CurriculumVersionHigherModuleDto dto = EntityUtil.bindToDto(module, new CurriculumVersionHigherModuleDto(),
                "electiveModules", "specialities", "subjects", "curriculumVersion");
        dto.setCurriculumVersion(EntityUtil.getId(module.getCurriculumVersion()));
        dto.setElectiveModules(StreamUtil.toMappedSet(CurriculumVersionElectiveModuleDto::of, module.getElectiveModules()));
        dto.setSubjects(StreamUtil.toMappedSet(s -> CurriculumVersionHigherModuleSubjectDto.of(s), module.getSubjects()));
        if(!Boolean.TRUE.equals(module.getMinorSpeciality())) {
            dto.setSpecialitiesReferenceNumbers(StreamUtil.toMappedSet(m ->
            EntityUtil.getId(m.getSpeciality()), module.getSpecialities()));
            dto.setCurriculumSpecialities(StreamUtil.toMappedSet(m ->
            EntityUtil.getId(m.getSpeciality().getCurriculumSpeciality()), module.getSpecialities()));
        }
        dto.setStudyYears(Short.valueOf((short) Math.ceil(module.getCurriculumVersion().getCurriculum().getStudyPeriod().doubleValue() / 12)));
        return dto;
    }
    
    public Set<Long> getSpecialitiesReferenceNumbers() {
        return specialitiesReferenceNumbers != null ? specialitiesReferenceNumbers : (specialitiesReferenceNumbers = new HashSet<>());
    }

    public void setSpecialitiesReferenceNumbers(Set<Long> specialitiesReferenceNumbers) {
        this.specialitiesReferenceNumbers = specialitiesReferenceNumbers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Short getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Short orderNr) {
        this.orderNr = orderNr;
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

    public String getTypeNameEt() {
        return typeNameEt;
    }

    public void setTypeNameEt(String typeNameEt) {
        this.typeNameEt = typeNameEt;
    }

    public String getTypeNameEn() {
        return typeNameEn;
    }

    public void setTypeNameEn(String typeNameEn) {
        this.typeNameEn = typeNameEn;
    }

    public BigDecimal getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(BigDecimal totalCredits) {
        this.totalCredits = totalCredits;
    }

    public BigDecimal getOptionalStudyCredits() {
        return optionalStudyCredits;
    }

    public void setOptionalStudyCredits(BigDecimal optionalStudyCredits) {
        this.optionalStudyCredits = optionalStudyCredits;
    }

    public BigDecimal getCompulsoryStudyCredits() {
        return compulsoryStudyCredits;
    }

    public void setCompulsoryStudyCredits(BigDecimal compulsoryStudyCredits) {
        this.compulsoryStudyCredits = compulsoryStudyCredits;
    }

    public Short getElectiveModulesNumber() {
        return electiveModulesNumber;
    }

    public void setElectiveModulesNumber(Short electiveModulesNumber) {
        this.electiveModulesNumber = electiveModulesNumber;
    }

    public Boolean getMinorSpeciality() {
        return minorSpeciality;
    }

    public void setMinorSpeciality(Boolean minorSpeciality) {
        this.minorSpeciality = minorSpeciality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<CurriculumVersionHigherModuleSubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<CurriculumVersionHigherModuleSubjectDto> subjects) {
        this.subjects = subjects;
    }

    public Set<CurriculumVersionElectiveModuleDto> getElectiveModules() {
        return electiveModules != null ? electiveModules : (electiveModules = new HashSet<>());
    }

    public void setElectiveModules(Set<CurriculumVersionElectiveModuleDto> electiveModules) {
        this.electiveModules = electiveModules;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public Short getStudyYears() {
        return studyYears;
    }

    public void setStudyYears(Short studyYears) {
        this.studyYears = studyYears;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Set<Long> getCurriculumSpecialities() {
        return curriculumSpecialities;
    }

    public void setCurriculumSpecialities(Set<Long> curriculumSpecialities) {
        this.curriculumSpecialities = curriculumSpecialities;
    }
}
