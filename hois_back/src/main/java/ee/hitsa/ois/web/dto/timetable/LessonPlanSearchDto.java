package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonPlanSearchDto {

    private final Long id;
    private final AutocompleteResult studentGroup;
    private final String teacherFullname;

    public LessonPlanSearchDto(Long id, AutocompleteResult studentGroup, String teacherFullname) {
        this.id = id;
        this.studentGroup = studentGroup;
        this.teacherFullname = teacherFullname;
    }

    public Long getId() {
        return id;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public String getTeacherFullname() {
        return teacherFullname;
    }
}
