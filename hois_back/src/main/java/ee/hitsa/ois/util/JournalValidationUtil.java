package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class JournalValidationUtil {

    public static void assertCanView(HoisUserDetails user) {
        if(!JournalUtil.hasPermissionToView(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanView(HoisUserDetails user, Journal journal) {
        if(!JournalUtil.hasPermissionToView(user, journal.getSchool())) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void asssertCanChange(HoisUserDetails user, Journal journal) {
        if(!JournalUtil.hasPermissionToChange(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void asssertCanConfirm(HoisUserDetails user, Journal journal) {
        if(!JournalUtil.canConfirm(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void asssertCanUnconfirm(HoisUserDetails user, Journal journal) {
        if(!JournalUtil.canUnconfirm(user, journal)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanRemoveStudent(HoisUserDetails user, Journal journal) {
        if (!JournalUtil.canRemoveStudent(user, journal)) {
            String message = "journal.messages.removingStudentIsNotAllowed";
            if(user.isTeacher() && !JournalUtil.teacherCanRemoveStudent(journal)) {
                message = "journal.messages.removingStudentIsNotAllowedForTeacher";
            }
            throw new ValidationFailedException(message);
        }
    }

    public static void assertCanAddStudent(HoisUserDetails user, Journal journal) {
        if (!JournalUtil.hasPermissionToChange(user, journal)) {
            throw new ValidationFailedException("journal.messages.addingStudentIsNotAllowed");
        }
    }

    public static void assertCanConfirmAll(HoisUserDetails user, StudyYear studyYear) {
        if(!JournalUtil.canConfirmAll(user, studyYear)) {
            throw new ValidationFailedException("journal.messages.confirmAllNotAllowed");
        }
    }
}
