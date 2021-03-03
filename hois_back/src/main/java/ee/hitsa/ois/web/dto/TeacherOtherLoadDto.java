package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.teacher.TeacherOtherLoad;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.teacher.TeacherOtherLoadForm;

public class TeacherOtherLoadDto extends TeacherOtherLoadForm {

    private Long teacher;

    public static TeacherOtherLoadDto of(TeacherOtherLoad otherLoad) {
        return EntityUtil.bindToDto(otherLoad, new TeacherOtherLoadDto());
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
}
