package ee.hitsa.ois.web.dto.directive;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveStudentForm;

public class DirectiveStudentDto extends DirectiveStudentForm {

    public static DirectiveStudentDto of(Application application) {
        return EntityUtil.bindToDto(application, new DirectiveStudentDto());
    }

    public static DirectiveStudentDto of(DirectiveStudent student) {
        return EntityUtil.bindToDto(student, new DirectiveStudentDto());
    }

    public static DirectiveStudentDto of(Student student) {
        return EntityUtil.bindToDto(student, new DirectiveStudentDto());
    }
}
