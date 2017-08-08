package ee.hitsa.ois.web.dto.timetable;

public class TimetableStudentGroupCapacityDto {
    private Long studentGroup;
    private Long totalPlannedLessons;
    private Long thisPlannedLessons;
    private Long totalAllocatedLessons;
    private Long journal;
    private Long lessonsLeft;

    public TimetableStudentGroupCapacityDto(Long studentGroup, Long journal, Long totalPlannedLessons,
            Long thisPlannedLessons) {
        this.studentGroup = studentGroup;
        this.journal = journal;
        this.totalPlannedLessons = totalPlannedLessons;
        this.thisPlannedLessons = thisPlannedLessons;
        this.lessonsLeft = thisPlannedLessons;
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

}
