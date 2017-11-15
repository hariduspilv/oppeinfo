package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import ee.hitsa.ois.domain.Classifier;
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

    /**
     * null values are filtered in back end
     */
//    @NotNull
    @Min(0)
    @Max(10000)
    private Short hours;

    private Boolean contact = Boolean.FALSE;
    
    private String nameEt;
    private String nameEn;

    public static CurriculumVersionOccupationModuleThemeCapacityDto of(CurriculumVersionOccupationModuleThemeCapacity capacity) {
        CurriculumVersionOccupationModuleThemeCapacityDto dto =
                EntityUtil.bindToDto(capacity, new CurriculumVersionOccupationModuleThemeCapacityDto());
        dto.setNameEt(capacity.getCapacityType().getNameEt());
        dto.setNameEn(capacity.getCapacityType().getNameEn());
        return dto;
    }
    
    public static CurriculumVersionOccupationModuleThemeCapacityDto empty(Classifier capacityType) {
        CurriculumVersionOccupationModuleThemeCapacityDto dto = new CurriculumVersionOccupationModuleThemeCapacityDto();
        dto.setNameEt(capacityType.getNameEt());
        dto.setNameEn(capacityType.getNameEn());
        dto.setCapacityType(EntityUtil.getCode(capacityType));
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

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }

    public Boolean getContact() {
        return contact;
    }

    public void setContact(Boolean contact) {
        this.contact = contact;
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
}
