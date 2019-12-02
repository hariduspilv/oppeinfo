package ee.hitsa.ois.util;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.web.dto.PracticeJournalDto;

public final class PracticeJournalUserRights {

    private static final int DAYS_AFTER_END_CAN_EDIT = 30;

    private static boolean hasPracticeJournalViewPermission(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PRAKTIKAPAEVIK);
    }

    private static boolean hasPracticeJournalEditPermission(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PRAKTIKAPAEVIK);
    }

    private static boolean hasPracticeJournalConfirmPermission(HoisUserDetails user) {
        return UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PRAKTIKAPAEVIK);
    }

    public static boolean canSearch(HoisUserDetails user) {
        if (user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher()) {
            return hasPracticeJournalViewPermission(user);
        }
        return user.isStudent() || user.isRepresentative();
    }

    public static boolean canView(HoisUserDetails user, PracticeJournal practiceJournal) {
        if (UserUtil.isSchoolAdmin(user, practiceJournal.getSchool())
                || UserUtil.isLeadingTeacher(user, practiceJournal.getStudent())) {
            return hasPracticeJournalViewPermission(user);
        } else if (user.isTeacher()) {
            return hasPracticeJournalViewPermission(user)
                    && EntityUtil.getId(practiceJournal.getTeacher()).equals(user.getTeacherId());
        } else if (user.isStudent() || user.isRepresentative()) {
            return EntityUtil.getId(practiceJournal.getStudent()).equals(user.getStudentId());
        }
        return false;
    }

    public static boolean canEdit(HoisUserDetails user, PracticeJournal practiceJournal) {
        if (StudentUtil.isActive(practiceJournal.getStudent()) && (user.isSchoolAdmin() || user.isTeacher())) {
            boolean canEdit = UserUtil.isSameSchool(user, practiceJournal.getSchool())
                    && hasPracticeJournalEditPermission(user) && isBeforeDaysAfterCanEdit(practiceJournal.getEndDate());
            if (user.isTeacher()) {
                canEdit = canEdit && EntityUtil.getId(practiceJournal.getTeacher()).equals(user.getTeacherId());
            }
            return canEdit;
        }
        return false;
    }

    public static boolean canEdit(HoisUserDetails user, LocalDate endDate, String studentStatus) {
        if (StudentUtil.isActive(studentStatus) && (user.isSchoolAdmin() || user.isTeacher())) {
            return hasPracticeJournalEditPermission(user) && isBeforeDaysAfterCanEdit(endDate);
        }
        return false;
    }

    public static boolean canConfirm(HoisUserDetails user, PracticeJournal practiceJournal) {
        return StudentUtil.isActive(practiceJournal.getStudent())
                && UserUtil.isSchoolAdmin(user, practiceJournal.getSchool())
                && hasPracticeJournalConfirmPermission(user) && isBeforeDaysAfterCanEdit(practiceJournal.getEndDate());
    }

    public static boolean canConfirm(HoisUserDetails user, LocalDate endDate, String studentStatus) {
        return StudentUtil.isActive(studentStatus) && user.isSchoolAdmin() && hasPracticeJournalConfirmPermission(user)
                && isBeforeDaysAfterCanEdit(endDate);
    }

    public static boolean isBeforeDaysAfterCanEdit(LocalDate endDate) {
        return LocalDate.now().isBefore(endDate.plusDays(DAYS_AFTER_END_CAN_EDIT));
    }

    private static boolean isConfirmed(String status) {
        return JournalStatus.PAEVIK_STAATUS_K.name().equals(status);
    }

    public static boolean canAddEntries(HoisUserDetails user, Boolean canStudentAddEntries, String studentStatus) {
        if (StudentUtil.isActive(studentStatus)) {
            if (user.isStudent()) {
                return Boolean.TRUE.equals(canStudentAddEntries);
            } else if (user.isSchoolAdmin() || user.isTeacher()) {
                return true;
            }
        }
        return false;
    }

    public static boolean canStudentAddEntries(HoisUserDetails user, PracticeJournal practiceJournal) {
        return UserUtil.isStudent(user, practiceJournal.getStudent())
                && canStudentAddEntries(EntityUtil.getCode(practiceJournal.getStatus()), practiceJournal.getEndDate(),
                        hasOpinion(practiceJournal.getSupervisorOpinion()));
    }

    public static boolean canStudentAddEntries(String status, LocalDate endDate, Boolean hasSupervisorOpinion) {
        return !isConfirmed(status) && isBeforeDaysAfterCanEdit(endDate) && Boolean.FALSE.equals(hasSupervisorOpinion);
    }

    public static boolean canAdminOrTeacherAddEntries(HoisUserDetails user, PracticeJournal practiceJournal) {
        return UserUtil.isSameSchool(user, practiceJournal.getSchool())
                && (user.isSchoolAdmin() || EntityUtil.getId(practiceJournal.getTeacher()).equals(user.getTeacherId()));
    }

    public static boolean canDelete(HoisUserDetails user, PracticeJournal practiceJournal) {
        Contract contract = practiceJournal.getContract();
        boolean canDelete = canEdit(user, practiceJournal) && !user.isStudent();
        if (contract != null) {
            canDelete = canDelete && practiceJournal.getPracticeJournalEntries().isEmpty()
                    && practiceJournal.getPracticeJournalEvaluations().isEmpty() && ContractStatus.LEPING_STAATUS_T
                            .name().equals(EntityUtil.getNullableCode(practiceJournal.getContract().getStatus()));
        }
        return canDelete;
    }

    private static Boolean hasOpinion(String opinion) {
        return opinion == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public static boolean canAddEntries(HoisUserDetails user, PracticeJournalDto dto) {
        if (StudentUtil.isActive(dto.getStudentStatus())) {
            if (user.isStudent()) {
                return canStudentAddEntries(dto.getStatus(), dto.getEndDate(), hasOpinion(dto.getSupervisorOpinion()));
            } else if (user.isSchoolAdmin() || user.isTeacher()) {
                return true;
            }
        }
        return false;
    }

}
