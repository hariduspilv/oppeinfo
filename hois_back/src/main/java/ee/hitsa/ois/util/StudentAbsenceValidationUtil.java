package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class StudentAbsenceValidationUtil {

    public static void assertCanSearch(HoisUserDetails user) {
        AssertionFailedException.throwIf(!StudentAbsenceUtil.hasPermissionToSearch(user), "User cannot search student absences");
    }

    public static void assertCanCreate(HoisUserDetails user, Student student) {
        AssertionFailedException.throwIf(!StudentAbsenceUtil.canCreate(user, student), "User cannot add student absence");
    }

    public static void assertCanEdit(HoisUserDetails user, StudentAbsence absence) {
        AssertionFailedException.throwIf(!StudentAbsenceUtil.canEdit(user, absence), "User cannot edit student absence");
    }

    public static void assertCanAccept(HoisUserDetails user, StudentAbsence absence) {
        AssertionFailedException.throwIf(!StudentAbsenceUtil.canAccept(user, absence), "User cannot accept student absence");
    }
}
