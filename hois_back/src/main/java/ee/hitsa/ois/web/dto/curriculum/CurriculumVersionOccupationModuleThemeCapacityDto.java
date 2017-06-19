package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleThemeCapacity;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionOccupationModuleThemeCapacityDto extends VersionedCommand {

    private Long id;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.MAHT)
    private String capacityType;

    @NotNull
    @Min(0)
    @Max(10000)
    private Integer hours;

    private Boolean contact = Boolean.FALSE;

    public static CurriculumVersionOccupationModuleThemeCapacityDto of(CurriculumVersionOccupationModuleThemeCapacity capacity) {
        CurriculumVersionOccupationModuleThemeCapacityDto dto =
                EntityUtil.bindToDto(capacity, new CurriculumVersionOccupationModuleThemeCapacityDto());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Boolean getContact() {
        return contact;
    }

    public void setContact(Boolean contact) {
        this.contact = contact;
    }

}
