package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.TeacherOccupationForm;

public class TeacherOccupationDto extends TeacherOccupationForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static TeacherOccupationDto of(TeacherOccupation teacherOccupation) {
        return EntityUtil.bindToDto(teacherOccupation, new TeacherOccupationDto());
    }
}
