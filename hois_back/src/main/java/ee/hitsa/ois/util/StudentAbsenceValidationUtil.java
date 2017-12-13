package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class StudentAbsenceValidationUtil extends StudentAbsenceUtil {
    
    public static void assertCanSearch(HoisUserDetails user) {
        AssertionFailedException.throwIf(!hasPermissionToSearch(user), "User cannot search student absences");
    }
    
    public static void assertCanCreate(HoisUserDetails user, Student student) {
        AssertionFailedException.throwIf(!canCreate(user, student), "User cannot add student absence");
    }

    public static void assertCanEdit(HoisUserDetails user, StudentAbsence absence) {
        AssertionFailedException.throwIf(!canEdit(user, absence), "User cannot edit student absence");
    }
    
    public static void assertCanAccept(HoisUserDetails user, StudentAbsence absence) {
        AssertionFailedException.throwIf(!canAccept(user, absence), "User cannot accept student absence");

    }
}
