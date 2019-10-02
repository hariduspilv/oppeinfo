package ee.hitsa.ois.web.dto.timetable;

public class TimetableVocationalCapacityDto {

    private Long totalPlannedLessons= Long.valueOf(0);
    private Long thisPlannedLessons= Long.valueOf(0);
    private Long lessonsLeft= Long.valueOf(0);
    private Long totalLessonsLeft= Long.valueOf(0);
    private Long totalAllocatedLessons = Long.valueOf(0);
    private Long leftOverLessons = Long.valueOf(0);
    private String capacityType;

    public Long getTotalPlannedLessons() {
        return totalPlannedLessons;
    }

    public void setTotalPlannedLessons(Long totalPlannedLessons) {
        this.totalPlannedLessons = totalPlannedLessons;
    }

    public Long getThisPlannedLessons() {
        return thisPlannedLessons;
    }

    public void setThisPlannedLessons(Long thisPlannedLessons) {
        this.thisPlannedLessons = thisPlannedLessons;
    }

    public Long getLessonsLeft() {
        return lessonsLeft;
    }

    public void setLessonsLeft(Long lessonsLeft) {
        this.lessonsLeft = lessonsLeft;
    }

    public Long getTotalLessonsLeft() {
        return totalLessonsLeft;
    }

    public void setTotalLessonsLeft(Long totalLessonsLeft) {
        this.totalLessonsLeft = totalLessonsLeft;
    }

    public Long getTotalAllocatedLessons() {
        return totalAllocatedLessons;
    }

    public void setTotalAllocatedLessons(Long totalAllocatedLessons) {
        this.totalAllocatedLessons = totalAllocatedLessons;
    }

    public Long getLeftOverLessons() {
        return leftOverLessons;
    }

    public void setLeftOverLessons(Long leftOverLessons) {
        this.leftOverLessons = leftOverLessons;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

}
