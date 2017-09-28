package ee.hitsa.ois.web.dto.curriculum;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.LocalDateXmlAdapter;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class CurriculumVersionDto extends InsertedChangedVersionDto {

    private Long id;
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

    @Size(max=4000)
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

    @Valid
    private Set<CurriculumVersionOccupationModuleDto> occupationModules;
    private Set<Long> specialitiesReferenceNumbers;

    public static CurriculumVersionDto of(CurriculumVersion version) {
        CurriculumVersionDto dto = EntityUtil.bindToDto(version, new CurriculumVersionDto(),
                "modules", "specialities", "occupationModules", "curriculumStudyForm");

        dto.setModules(StreamUtil.toMappedSet(CurriculumVersionHigherModuleDto::of, version.getModules()));
        dto.setOccupationModules(StreamUtil.toMappedSet(CurriculumVersionOccupationModuleDto::of, version.getOccupationModules()));
        dto.setSpecialitiesReferenceNumbers(StreamUtil.toMappedSet(s -> EntityUtil.getId(s.getCurriculumSpeciality()), version.getSpecialities()));

        //CurriculumVersion.curriculumStudyForm is not a Classifier
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
}
