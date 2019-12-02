package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.studymaterial.StudyMaterial;
import ee.hitsa.ois.domain.studymaterial.StudyMaterialConnect;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class StudyMaterialUserRights {

    private static boolean canView(HoisUserDetails user, StudyMaterial material) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPPEMATERJAL)) {
            return false;
        }

        if (user.isLeadingTeacher()) {
            return Boolean.TRUE.equals(material.getIsPublic()) || hasLeadingTeacherConnectedEntities(user, material);
        } else if (user.isTeacher()) {
            return Boolean.TRUE.equals(material.getIsPublic()) || isStudyMaterialTeacher(user, material)
                    || isStudyMaterialJournalTeacher(user, material) || isStudyMaterialSubjectTeacher(user, material);
        }
        return UserUtil.isSchoolAdmin(user, material.getSchool());
    }

    private static boolean hasLeadingTeacherConnectedEntities(HoisUserDetails user, StudyMaterial material) {
        return material.getStudyMaterialConnect().stream().anyMatch(smc -> canLeadingTeacherView(user, smc));
    }

    private static boolean canLeadingTeacherView(HoisUserDetails user, StudyMaterialConnect materialConnect) {
        return materialConnect.getJournal() != null && UserUtil.isLeadingTeacher(user, materialConnect.getJournal())
                || materialConnect.getSubjectStudyPeriod() != null
                        && UserUtil.isLeadingTeacher(user, materialConnect.getSubjectStudyPeriod().getSubject());
    }

    private static boolean isStudyMaterialTeacher(HoisUserDetails user, StudyMaterial material) {
        return user.getTeacherId().equals(EntityUtil.getId(material.getTeacher()));
    }

    private static boolean isStudyMaterialJournalTeacher(HoisUserDetails user, StudyMaterial material) {
        return material.getStudyMaterialConnect().stream().filter(con -> con.getJournal() != null)
                .flatMap(con -> con.getJournal().getJournalTeachers().stream())
                .anyMatch(t -> user.getTeacherId().equals(EntityUtil.getId(t.getTeacher())));
    }

    private static boolean isStudyMaterialSubjectTeacher(HoisUserDetails user, StudyMaterial material) {
        return material.getStudyMaterialConnect().stream().filter(con -> con.getSubjectStudyPeriod() != null)
                .flatMap(con -> con.getSubjectStudyPeriod().getTeachers().stream())
                .anyMatch(t -> user.getTeacherId().equals(EntityUtil.getId(t.getTeacher())));
    }

    public static void assertCanView(HoisUserDetails user, StudyMaterial material) {
        UserUtil.throwAccessDeniedIf(!canView(user, material));
    }

    public static boolean canEdit(HoisUserDetails user, StudyMaterial material) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPEMATERJAL)) {
            return false;
        }

        if (user.isLeadingTeacher()) {
            return hasLeadingTeacherConnectedEntities(user, material);
        } else if (user.isTeacher()) {
            return isStudyMaterialTeacher(user, material);
        }
        return UserUtil.isSchoolAdmin(user, material.getSchool());
    }

    public static void assertCanEdit(HoisUserDetails user, StudyMaterial material) {
        UserUtil.throwAccessDeniedIf(!canEdit(user, material));
    }

    public static boolean canEditSubjectStudyPeriod(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        if (user == null || !UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPEMATERJAL)) {
            return false;
        }

        if (user.isLeadingTeacher()) {
            return UserUtil.isLeadingTeacher(user, subjectStudyPeriod.getSubject());
        } else if (user.isTeacher()) {
            return isSubjectStudyPeriodTeacher(user, subjectStudyPeriod);
        }
        return UserUtil.isSchoolAdmin(user, subjectStudyPeriod.getSubject().getSchool());
    }

    private static boolean isSubjectStudyPeriodTeacher(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        return subjectStudyPeriod.getTeachers().stream()
                .anyMatch(t -> user.getTeacherId().equals(EntityUtil.getId(t.getTeacher())));
    }

    public static void assertCanEditSubjectStudyPeriod(HoisUserDetails user, SubjectStudyPeriod subjectStudyPeriod) {
        UserUtil.throwAccessDeniedIf(!canEditSubjectStudyPeriod(user, subjectStudyPeriod));
    }

    public static boolean canEditJournal(HoisUserDetails user, Journal journal) {
        if (user == null || !UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPEMATERJAL)) {
            return false;
        }

        if (user.isLeadingTeacher()) {
            return UserUtil.isLeadingTeacher(user, journal);
        } else if (user.isTeacher()) {
            return isJournalTeacher(user, journal);
        }
        return UserUtil.isSchoolAdmin(user, journal.getSchool());
    }

    private static boolean isJournalTeacher(HoisUserDetails user, Journal journal) {
        return journal.getJournalTeachers().stream()
                .anyMatch(t -> user.getTeacherId().equals(EntityUtil.getId(t.getTeacher())));
    }

    public static void assertCanEditJournal(HoisUserDetails user, Journal journal) {
        UserUtil.throwAccessDeniedIf(!canEditJournal(user, journal));
    }

}