package ee.hitsa.ois.web.dto.timetable;

public class LessonPlanSearchDto {

    private final Long id;
    private final String studentGroup;
    private final String curriculumVersion;
    private final Long plannedHours;

    public LessonPlanSearchDto(Long id, String studentGroup, String curriculumVersion, Long plannedHours) {
        this.id = id;
        this.studentGroup = studentGroup;
        this.curriculumVersion = curriculumVersion;
        this.plannedHours = plannedHours;
    }

    public Long getId() {
        return id;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public String getCurriculumVersion() {
        return curriculumVersion;
    }

    public Long getPlannedHours() {
        return plannedHours;
    }
    
}
