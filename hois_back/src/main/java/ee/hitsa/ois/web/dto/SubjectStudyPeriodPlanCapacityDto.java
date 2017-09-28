package ee.hitsa.ois.web.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodPlanCapacity;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class SubjectStudyPeriodPlanCapacityDto {

    private Long id;
    @NotNull
    private Boolean isContact;
    @Min(0)
    @Max(Short.MAX_VALUE)
    private Short hours;
    @NotNull
    @ClassifierRestriction(MainClassCode.MAHT)
    private String capacityType;

    public static SubjectStudyPeriodPlanCapacityDto of (SubjectStudyPeriodPlanCapacity capacity) {
        SubjectStudyPeriodPlanCapacityDto dto = new SubjectStudyPeriodPlanCapacityDto();
        dto.setId(EntityUtil.getId(capacity));
        dto.setIsContact(capacity.getIsContact());
        dto.setHours(capacity.getHours());
        dto.setCapacityType(EntityUtil.getCode(capacity.getCapacityType()));
        return dto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getIsContact() {
        return isContact;
    }
    public void setIsContact(Boolean isContact) {
        this.isContact = isContact;
    }
    public Short getHours() {
        return hours;
    }
    public void setHours(Short hours) {
        this.hours = hours;
    }
    public String getCapacityType() {
        return capacityType;
    }
    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }
}
