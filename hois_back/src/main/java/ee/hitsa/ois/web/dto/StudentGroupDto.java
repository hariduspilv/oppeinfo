package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudentGroup;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudentGroupForm;

public class StudentGroupDto extends StudentGroupForm {

    public static StudentGroupDto of(StudentGroup studentGroup) {
        return EntityUtil.bindToDto(studentGroup, new StudentGroupDto());
    }
}
