package ee.hitsa.ois.web.dto.curriculum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.CurriculumVersionYearCapacitiesUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class CurriculumVersionDto extends InsertedChangedVersionDto {

    private Long id;
    
    @NotNull
    private Long curriculum;
    @NotBlank
    @Size(max=255)
    private String code;
    @Min(0)
    @Max(10000)
    private Short admissionYear;

    @Size(max=4000)
    private String targetGroup;

    private Boolean individual;

    @Size(max=4000)
    private String teachers;

    @Size(max=20000)
    private String description;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.OPPEKAVA_VERSIOON_LIIK)
    private String type;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.OPPEKAVA_VERSIOON_STAATUS)
    private String status;
    private Long schoolDepartment;

    @ClassifierRestriction(MainClassCode.OPPEVORM)
    private String curriculumStudyForm;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Set<CurriculumVersionHigherModuleDto> modules;

    /**
     * They are created on distinct form, so no need for validation
     */
    private Set<CurriculumVersionOccupationModuleDto> occupationModules;
    private Set<Long> specialitiesReferenceNumbers;
    
    private List<BigDecimal> yearCapacities;
    
    private Boolean canChange;
    private Boolean canConfirm;
    private Boolean canClose;
    private Boolean canDelete;
    
    /**
     * After creating new curriculum version only id and curriculum id are required 
     * for redirecting to edit page
     */
    public static CurriculumVersionDto created(CurriculumVersion version) {
        CurriculumVersionDto dto = new CurriculumVersionDto();
        dto.setId(EntityUtil.getId(version));
        dto.setCurriculum(EntityUtil.getId(version.getCurriculum()));
        return dto;
    }
    
    public static CurriculumVersionDto forCurriculumForm(CurriculumVersion version) {
        CurriculumVersionDto dto = new CurriculumVersionDto();
        dto.setId(EntityUtil.getId(version));
        dto.setCurriculum(EntityUtil.getId(version.getCurriculum()));
        dto.setCode(version.getCode());
        dto.setStatus(EntityUtil.getCode(version.getStatus()));
        return dto;
    }

    public static CurriculumVersionDto of(CurriculumVersion version) {
        CurriculumVersionDto dto = EntityUtil.bindToDto(version, new CurriculumVersionDto(),
                "modules", "specialities", "occupationModules", "curriculumStudyForm", "curriculum");

        dto.setCurriculum(EntityUtil.getId(version.getCurriculum()));

        if(CurriculumUtil.isVocational(version.getCurriculum())) {
            dto.setOccupationModules(StreamUtil.toMappedSet(CurriculumVersionOccupationModuleDto::forCurriculumVersionForm, version.getOccupationModules()));
            dto.setYearCapacities(new ArrayList<>());
            dto.getYearCapacities().add(CurriculumVersionYearCapacitiesUtil.calculate(version.getOccupationModules(), Short.valueOf((short) 1)));
            dto.getYearCapacities().add(CurriculumVersionYearCapacitiesUtil.calculate(version.getOccupationModules(), Short.valueOf((short) 2)));
            dto.getYearCapacities().add(CurriculumVersionYearCapacitiesUtil.calculate(version.getOccupationModules(), Short.valueOf((short) 3)));
        } else {
            dto.setModules(StreamUtil.toMappedSet(CurriculumVersionHigherModuleDto::of, version.getModules()));
            dto.setSpecialitiesReferenceNumbers(StreamUtil.toMappedSet(s -> EntityUtil.getId(s.getCurriculumSpeciality()), version.getSpecialities()));
        }
        if (version.getCurriculumStudyForm() != null) {
            dto.setCurriculumStudyForm(EntityUtil.getNullableCode(version.getCurriculumStudyForm().getStudyForm()));
        }
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Short getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(Short admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public Boolean getIndividual() {
        return individual;
    }

    public void setIndividual(Boolean individual) {
        this.individual = individual;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CurriculumVersionHigherModuleDto> getModules() {
        return modules != null ? modules : (modules = new HashSet<>());
    }

    public void setModules(Set<CurriculumVersionHigherModuleDto> modules) {
        this.modules = modules;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(Long schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }

    public String getCurriculumStudyForm() {
        return curriculumStudyForm;
    }

    public void setCurriculumStudyForm(String curriculumStudyForm) {
        this.curriculumStudyForm = curriculumStudyForm;
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

    public Set<CurriculumVersionOccupationModuleDto> getOccupationModules() {
        return occupationModules != null ? occupationModules : (occupationModules = new HashSet<>());
    }

    public void setOccupationModules(Set<CurriculumVersionOccupationModuleDto> occupationModules) {
        this.occupationModules = occupationModules;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public List<BigDecimal> getYearCapacities() {
        return yearCapacities;
    }

    public void setYearCapacities(List<BigDecimal> yearCapacities) {
        this.yearCapacities = yearCapacities;
    }

    public Boolean getCanChange() {
        return canChange;
    }

    public void setCanChange(Boolean canChange) {
        this.canChange = canChange;
    }

    public Boolean getCanConfirm() {
        return canConfirm;
    }

    public void setCanConfirm(Boolean canConfirm) {
        this.canConfirm = canConfirm;
    }

    public Boolean getCanClose() {
        return canClose;
    }

    public void setCanClose(Boolean canClose) {
        this.canClose = canClose;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }
}
