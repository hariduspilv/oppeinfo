package ee.hitsa.ois.web.dto.timetable;

public class LessonPlanSearchTeacherDto {

    private final Long id;
    private final String teacherFullname;

    public LessonPlanSearchTeacherDto(Long id, String teacherFullname) {
        this.id = id;
        this.teacherFullname = teacherFullname;
    }

    public Long getId() {
        return id;
    }

    public String getTeacherFullname() {
        return teacherFullname;
    }
}
