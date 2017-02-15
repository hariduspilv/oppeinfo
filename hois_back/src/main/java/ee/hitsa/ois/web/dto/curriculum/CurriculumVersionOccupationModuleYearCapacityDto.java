package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleYearCapacity;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionOccupationModuleYearCapacityDto  extends VersionedCommand {

    private Long id;

    @NotNull
    @Min(1)
    private Integer studyYearNumber;

    @NotNull
    private Double credits;

    public static CurriculumVersionOccupationModuleYearCapacityDto of(CurriculumVersionOccupationModuleYearCapacity capacity) {
        CurriculumVersionOccupationModuleYearCapacityDto dto =
                EntityUtil.bindToDto(capacity, new CurriculumVersionOccupationModuleYearCapacityDto());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStudyYearNumber() {
        return studyYearNumber;
    }

    public void setStudyYearNumber(Integer studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

}
