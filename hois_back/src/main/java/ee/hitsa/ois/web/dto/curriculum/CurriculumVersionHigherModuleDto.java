package ee.hitsa.ois.web.dto.curriculum;

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
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionHigherModuleDto extends VersionedCommand {

    private Long id;
    @NotBlank
    @Size(max = 255)
    private String nameEt;

    @NotBlank
    @Size(max = 255)
    private String nameEn;

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
    private Double totalCredits;
    @NotNull
    @Min(0)
    @Max(999)
    private Double optionalStudyCredits;
    @NotNull
    @Min(0)
    @Max(999)
    private Double compulsoryStudyCredits;
    @NotNull
    @Min(0)
    @Max(999)
    private Integer electiveModulesNumber;
    @NotNull
    private Boolean minorSpeciality;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.KORGMOODUL)
    private String type;

    private Set<CurriculumVersionHigherModuleSubjectDto> subjects;

    private Set<CurriculumVersionElectiveModuleDto> electiveModules;

    private Set<CurriculumVersionHigherModuleSpecialityDto> specialities;

    private Set<Long> specialitiesReferenceNumbers;

    public static CurriculumVersionHigherModuleDto of(CurriculumVersionHigherModule module) {
        CurriculumVersionHigherModuleDto dto = EntityUtil.bindToDto(module, new CurriculumVersionHigherModuleDto(),
                "electiveModules", "specialities", "subjects");

        dto.setElectiveModules(StreamUtil.toMappedSet(CurriculumVersionElectiveModuleDto::of, module.getElectiveModules()));
        dto.setSubjects(StreamUtil.toMappedSet(s -> CurriculumVersionHigherModuleSubjectDto.of(s, dto.getElectiveModules()), module.getSubjects()));
        dto.setSpecialitiesReferenceNumbers(StreamUtil.toMappedSet(m -> EntityUtil.getId(m.getSpeciality().getCurriculumSpeciality()), module.getSpecialities()));
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

    public Double getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Double totalCredits) {
        this.totalCredits = totalCredits;
    }

    public Double getOptionalStudyCredits() {
        return optionalStudyCredits;
    }

    public void setOptionalStudyCredits(Double optionalStudyCredits) {
        this.optionalStudyCredits = optionalStudyCredits;
    }

    public Double getCompulsoryStudyCredits() {
        return compulsoryStudyCredits;
    }

    public void setCompulsoryStudyCredits(Double compulsoryStudyCredits) {
        this.compulsoryStudyCredits = compulsoryStudyCredits;
    }

    public Integer getElectiveModulesNumber() {
        return electiveModulesNumber;
    }

    public void setElectiveModulesNumber(Integer electiveModulesNumber) {
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

    public Set<CurriculumVersionHigherModuleSpecialityDto> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<CurriculumVersionHigherModuleSpecialityDto> specialities) {
        this.specialities = specialities;
    }
}
