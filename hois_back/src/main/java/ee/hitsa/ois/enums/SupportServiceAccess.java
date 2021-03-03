package ee.hitsa.ois.enums;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentSupportService;
import ee.hitsa.ois.service.security.HoisUserDetails;

import static ee.hitsa.ois.util.UserUtil.hasPermission;
import static ee.hitsa.ois.util.UserUtil.isActiveStudentRepresentative;
import static ee.hitsa.ois.util.UserUtil.isSchoolAdmin;
import static ee.hitsa.ois.util.UserUtil.isStudent;
import static ee.hitsa.ois.util.UserUtil.isStudentGroupTeacher;
import static ee.hitsa.ois.util.UserUtil.isTeacher;

public enum SupportServiceAccess {
    NONE(0),
    PUBLIC(1),
    VISIBLE(2),
    PRIVATE(3);

    private final int priority;

    SupportServiceAccess(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public boolean hasAccess(SupportServiceAccess access) {
        return access != null && access.priority >= priority;
    }

    public static SupportServiceAccess getSupportServiceAccessLevel(StudentSupportService supportService) {
        if (Boolean.TRUE.equals(supportService.getIsPublic())) {
            return SupportServiceAccess.PUBLIC;
        }
        if (Boolean.TRUE.equals(supportService.getVisible())) {
            return SupportServiceAccess.VISIBLE;
        }
        return SupportServiceAccess.PRIVATE;
    }

    public static SupportServiceAccess getSupportServiceAccessForUser(HoisUserDetails user, Student student) {
        if (isSchoolAdmin(user, student.getSchool())
                && hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUGITEENUS)) {
            return SupportServiceAccess.PRIVATE;
        }
        if (isStudent(user, student) || isActiveStudentRepresentative(user, student) || isStudentGroupTeacher(user, student)) {
            return SupportServiceAccess.VISIBLE;
        }
        if (isTeacher(user, student.getSchool())) {
            return SupportServiceAccess.PUBLIC;
        }
        return SupportServiceAccess.NONE;
    }
}
