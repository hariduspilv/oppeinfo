package ee.hitsa.ois.web.dto.timetable;

public class TimetableVocationalCapacityDto {

    private Long studyYearPlannedLessons;
    private Long studyYearLessonsLeft;
    private Long studyYearAllocatedLessons;

    private Long studyPeriodPlannedLessons;
    private Long studyPeriodLessonsLeft;
    private Long studyPeriodAllocatedLessons;

    private Long currentWeekPlannedLessons;
    private Long currentWeekLessonsLeft;
    private Long currentWeekAllocatedLessons;

    private Long leftOverLessons;
    private String capacityType;

    public Long getStudyYearPlannedLessons() {
        return studyYearPlannedLessons != null ? studyYearPlannedLessons : Long.valueOf(0);
    }

    public void setStudyYearPlannedLessons(Long studyYearPlannedLessons) {
        this.studyYearPlannedLessons = studyYearPlannedLessons;
    }

    public Long getStudyYearLessonsLeft() {
        return studyYearLessonsLeft != null ? studyYearLessonsLeft : Long.valueOf(0);
    }

    public void setStudyYearLessonsLeft(Long studyYearLessonsLeft) {
        this.studyYearLessonsLeft = studyYearLessonsLeft;
    }

    public Long getStudyYearAllocatedLessons() {
        return studyYearAllocatedLessons != null ? studyYearAllocatedLessons : Long.valueOf(0);
    }

    public void setStudyYearAllocatedLessons(Long studyYearAllocatedLessons) {
        this.studyYearAllocatedLessons = studyYearAllocatedLessons;
    }

    public Long getStudyPeriodPlannedLessons() {
        return studyPeriodPlannedLessons != null ? studyPeriodPlannedLessons : Long.valueOf(0);
    }

    public void setStudyPeriodPlannedLessons(Long studyPeriodPlannedLessons) {
        this.studyPeriodPlannedLessons = studyPeriodPlannedLessons;
    }

    public Long getStudyPeriodLessonsLeft() {
        return studyPeriodLessonsLeft != null ? studyPeriodLessonsLeft : Long.valueOf(0);
    }

    public void setStudyPeriodLessonsLeft(Long studyPeriodLessonsLeft) {
        this.studyPeriodLessonsLeft = studyPeriodLessonsLeft;
    }

    public Long getStudyPeriodAllocatedLessons() {
        return studyPeriodAllocatedLessons != null ? studyPeriodAllocatedLessons : Long.valueOf(0);
    }

    public void setStudyPeriodAllocatedLessons(Long studyPeriodAllocatedLessons) {
        this.studyPeriodAllocatedLessons = studyPeriodAllocatedLessons;
    }

    public Long getCurrentWeekPlannedLessons() {
        return currentWeekPlannedLessons != null ? currentWeekPlannedLessons : Long.valueOf(0);
    }

    public void setCurrentWeekPlannedLessons(Long currentWeekPlannedLessons) {
        this.currentWeekPlannedLessons = currentWeekPlannedLessons;
    }

    public Long getCurrentWeekLessonsLeft() {
        return currentWeekLessonsLeft != null ? currentWeekLessonsLeft : Long.valueOf(0);
    }

    public void setCurrentWeekLessonsLeft(Long currentWeekLessonsLeft) {
        this.currentWeekLessonsLeft = currentWeekLessonsLeft;
    }

    public Long getCurrentWeekAllocatedLessons() {
        return currentWeekAllocatedLessons != null ? currentWeekAllocatedLessons : Long.valueOf(0);
    }

    public void setCurrentWeekAllocatedLessons(Long currentWeekAllocatedLessons) {
        this.currentWeekAllocatedLessons = currentWeekAllocatedLessons;
    }

    public Long getLeftOverLessons() {
        return leftOverLessons != null ? leftOverLessons : Long.valueOf(0);
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
