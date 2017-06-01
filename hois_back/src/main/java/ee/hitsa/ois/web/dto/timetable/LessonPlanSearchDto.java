package ee.hitsa.ois.web.dto.timetable;

public class LessonPlanSearchDto {

    private final Long id;
    private final String studentGroup;
    private final String curriculumVersion;

    public LessonPlanSearchDto(Long id, String studentGroup, String curriculumVersion) {
        this.id = id;
        this.studentGroup = studentGroup;
        this.curriculumVersion = curriculumVersion;
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
}
