package ee.hitsa.ois.web.dto.timetable;

public class TimetableHigherCapacityDto {

    private String capacityType;
    private Long totalPlannedLessons;
    private Long totalAllocatedLessons;

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
        return totalPlannedLessons != null ? totalPlannedLessons : Long.valueOf(0);
    }

    public void setTotalPlannedLessons(Long totalPlannedLessons) {
        this.totalPlannedLessons = totalPlannedLessons;
    }

    public Long getTotalAllocatedLessons() {
        return totalAllocatedLessons != null ? totalAllocatedLessons : Long.valueOf(0);
    }

    public void setTotalAllocatedLessons(Long totalAllocatedLessons) {
        this.totalAllocatedLessons = totalAllocatedLessons;
    }

}
