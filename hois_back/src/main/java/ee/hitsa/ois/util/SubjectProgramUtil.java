package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.enums.SubjectProgramStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public class SubjectProgramUtil {

    public static boolean canView(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        if (!EntityUtil.getId(program.getSubjectStudyPeriodTeacher().getTeacher()).equals(user.getTeacherId())) {
            return false;
        }
        return true;
    }

    public static void assertCanView(HoisUserDetails user, SubjectProgram program) {
        if (!canView(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static boolean canCreate(HoisUserDetails user) {
        if (!user.isTeacher()) {
            return false;
        }
        return true;
    }
    
    public static void assertCanCreate(HoisUserDetails user) {
        if (!canCreate(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static boolean canEdit(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        if (!EntityUtil.getId(program.getSubjectStudyPeriodTeacher().getTeacher()).equals(user.getTeacherId())) {
            return false;
        }
        return true;
    }

    public static void assertCanEdit(HoisUserDetails user, SubjectProgram program) {
        if (!canEdit(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static boolean canDelete(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        if (!EntityUtil.getId(program.getSubjectStudyPeriodTeacher().getTeacher()).equals(user.getTeacherId())) {
            return false;
        }
        return true;
    }

    public static void assertCanDelete(HoisUserDetails user, SubjectProgram program) {
        if (!canDelete(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static boolean canSearch(HoisUserDetails user) {
        if (!user.isTeacher()) {
            return false;
        }
        return true;
    }

    public static void assertCanSearch(HoisUserDetails user) {
        if (!canSearch(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static boolean canConfirm(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        if (!EntityUtil.getId(program.getSubjectStudyPeriodTeacher().getTeacher()).equals(user.getTeacherId())) {
            return false;
        }
        if (program.getConfirmedBy() != null || program.getConfirmed() != null) {
            return false;
        }
        return true;
    }
    
    public static void assertCanConfirm(HoisUserDetails user, SubjectProgram program) {
        if (!canConfirm(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
    public static boolean canComplete(HoisUserDetails user, SubjectProgram program) {
        if (!user.isTeacher()) {
            return false;
        }
        if (!EntityUtil.getId(program.getSubjectStudyPeriodTeacher().getTeacher()).equals(user.getTeacherId())) {
            return false;
        }
        if (!ClassifierUtil.equals(SubjectProgramStatus.AINEPROGRAMM_STAATUS_I, program.getStatus())) {
            return false;
        }
        return true;
    }

    public static void assertCanComplete(HoisUserDetails user, SubjectProgram program) {
        if (!canComplete(user, program)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
    
}
