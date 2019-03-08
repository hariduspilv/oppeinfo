package ee.hitsa.ois.web.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacherCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class SubjectStudyPeriodCapacityDto {

    private Long id;

    @Min(0)
    @Max(Short.MAX_VALUE)
    private Short hours;

    @ClassifierRestriction(MainClassCode.MAHT)
    private String capacityType;

    public static SubjectStudyPeriodCapacityDto of(SubjectStudyPeriodCapacity c) {
        SubjectStudyPeriodCapacityDto dto = new SubjectStudyPeriodCapacityDto();
        dto.setId(EntityUtil.getId(c));
        dto.setHours(c.getHours());
        dto.setCapacityType(EntityUtil.getCode(c.getCapacityType()));
        return dto;
    }

    public static SubjectStudyPeriodCapacityDto of(SubjectStudyPeriodTeacherCapacity c) {
        SubjectStudyPeriodCapacityDto dto = new SubjectStudyPeriodCapacityDto();
        dto.setId(EntityUtil.getId(c));
        dto.setHours(c.getHours());
        dto.setCapacityType(EntityUtil.getCode(c.getSubjectStudyPeriodCapacity().getCapacityType()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
