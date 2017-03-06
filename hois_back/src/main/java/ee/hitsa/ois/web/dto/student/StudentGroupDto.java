package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.student.StudentGroupForm;

public class StudentGroupDto extends StudentGroupForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static StudentGroupDto of(StudentGroup studentGroup) {
        return EntityUtil.bindToDto(studentGroup, new StudentGroupDto());
    }
}
