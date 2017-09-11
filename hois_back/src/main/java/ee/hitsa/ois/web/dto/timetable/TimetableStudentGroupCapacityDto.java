package ee.hitsa.ois.web.dto.timetable;

public class TimetableStudentGroupCapacityDto {
    private Long studentGroup;
    private Long totalPlannedLessons;
    private Long thisPlannedLessons;
    private Long journal;
    private Long lessonsLeft;
    // default value if the value is any different you need to set it with the
    // setter
    private Long totalAllocatedLessons = Long.valueOf(0);
    private String capacityType;

    public TimetableStudentGroupCapacityDto(Long studentGroup, Long journal, Long totalPlannedLessons,
            Long thisPlannedLessons, String capacityType/*
                                    * , Long totalAllocatedLessons, Long
                                    * thisAllocatedLessons
                                    */) {
        this.studentGroup = studentGroup;
        this.journal = journal;
        this.totalPlannedLessons = totalPlannedLessons;
        this.thisPlannedLessons = thisPlannedLessons;
        // this is the default value, if the value is any different you need to
        // set it with the setter
        this.lessonsLeft = thisPlannedLessons;
        this.capacityType = capacityType;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

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

    public Long getTotalAllocatedLessons() {
        return totalAllocatedLessons;
    }

    public void setTotalAllocatedLessons(Long totalAllocatedLessons) {
        this.totalAllocatedLessons = totalAllocatedLessons;
    }

    public Long getJournal() {
        return journal;
    }

    public void setJournal(Long journal) {
        this.journal = journal;
    }

    public Long getLessonsLeft() {
        return lessonsLeft;
    }

    public void setLessonsLeft(Long lessonsLeft) {
        this.lessonsLeft = lessonsLeft;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

}
