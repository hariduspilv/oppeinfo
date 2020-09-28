package ee.hitsa.ois.web.dto.timetable;

public class LessonPlanSearchTeacherDto {

    private final Long id;
    private final String teacherFullname;
    private final Long plannedHours;
    private final Long contractPlannedHours;
    private final Long studyYear;

    public LessonPlanSearchTeacherDto(Long id, String teacherFullname, Long plannedHours, Long contractPlannedHours, Long studyYear) {
        this.id = id;
        this.teacherFullname = teacherFullname;
        this.plannedHours = plannedHours != null ? plannedHours : Long.valueOf(0);
        this.contractPlannedHours = contractPlannedHours;
        this.studyYear = studyYear;
    }

    public Long getId() {
        return id;
    }

    public String getTeacherFullname() {
        return teacherFullname;
    }

    public Long getPlannedHours() {
        return plannedHours;
    }

    public Long getContractPlannedHours() {
        return contractPlannedHours;
    }

    public Long getStudyYear() {
        return studyYear;
    }
}
