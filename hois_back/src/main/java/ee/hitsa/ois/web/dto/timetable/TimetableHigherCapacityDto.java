package ee.hitsa.ois.web.dto.timetable;

public class TimetableHigherCapacityDto {

    private String capacityType;
    private Long totalPlannedLessons = Long.valueOf(0);
    private Long totalAllocatedLessons = Long.valueOf(0);

    public TimetableHigherCapacityDto() {
    }

    public TimetableHigherCapacityDto(String capacityType, Long totalPlannedLessons, Long totalAllocatedLessons) {
        this.capacityType = capacityType;
        this.totalPlannedLessons = totalPlannedLessons;
        this.totalAllocatedLessons = totalAllocatedLessons;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    public Long getTotalPlannedLessons() {
        return totalPlannedLessons;
    }

    public void setTotalPlannedLessons(Long totalPlannedLessons) {
        this.totalPlannedLessons = totalPlannedLessons;
    }

    public Long getTotalAllocatedLessons() {
        return totalAllocatedLessons;
    }

    public void setTotalAllocatedLessons(Long totalAllocatedLessons) {
        this.totalAllocatedLessons = totalAllocatedLessons;
    }

}
