package ee.hitsa.ois.util;

import java.util.EnumMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class UserUtil {

    public static boolean hasPermission(HoisUserDetails user, Permission permission, PermissionObject object) {
        String s = roleName(permission, object);
        return user.getAuthorities().stream().anyMatch(a -> s.equals(a.getAuthority()));
    }

    public static boolean canSubmitApplication(HoisUserDetails user, Application application) {
        if(ClassifierUtil.equals(ApplicationStatus.AVALDUS_STAATUS_KOOST, application.getStatus())) {
            Student student = application.getStudent();
            return isSame(user, student) || isSchoolAdmin(user, student.getSchool()) || isStudentRepresentative(user, student);
        }
        return false;
    }

    public static boolean canRejectApplication(HoisUserDetails user, Application application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApplicationStatus.AVALDUS_STAATUS_KOOST.name().equals(status)) {
            return Boolean.TRUE.equals(application.getNeedsRepresentativeConfirm()) && (isStudentRepresentative(user, student) || isSchoolAdmin(user, student.getSchool()));
        }
        if (ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name().equals(status)) {
            return isSchoolAdmin(user, student.getSchool());
        }
        return false;
    }

    public static boolean canUpdateInformalSubjectOrModuleTransferStatus(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (!(ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status))) {
            return Boolean.TRUE.equals(Boolean.valueOf(isSchoolAdmin(user, student.getSchool())
                    && hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_VOTA)));
        }
        return false;
    }

    public static boolean canViewApelApplication(HoisUserDetails user, ApelApplication application) {
        return isSchoolAdmin(user, application.getSchool()) || isSame(user, application.getStudent());
    }

    public static boolean canEditApelApplication(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status) || ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return isSchoolAdmin(user, student.getSchool()) || isSame(user, student);
        }
        return false;
    }

    public static boolean canSubmitApelApplication(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_K.name().equals(status)) {
            return isSchoolAdmin(user, student.getSchool()) || isSame(user, student);
        }
        return false;
    }

    public static boolean canSendToConfirmApelApplication(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return isSchoolAdmin(user, student.getSchool());
        }
        return false;
    }

    public static boolean canSendBackToCreation(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status)) {
            return isSchoolAdmin(user, student.getSchool());
        }
        return false;
    }

    public static boolean canConfirmApelApplication(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_Y.name().equals(status)) {
            return Boolean.TRUE.equals(Boolean.valueOf(isSchoolAdmin(user, student.getSchool())
                    && hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_VOTA)));
        }
        return false;
    }

    public static boolean canRemoveConfirmationApelApplication(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_C.name().equals(status)) {
            return Boolean.TRUE.equals(Boolean.valueOf(isSchoolAdmin(user, student.getSchool())
                    && hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_VOTA)));
        }
        return false;
    }

    public static boolean canSendBackApelApplication(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_Y.name().equals(status)) {
            return isSchoolAdmin(user, student.getSchool());
        }
        return false;
    }

    public static boolean canRejectApelApplication(HoisUserDetails user, ApelApplication application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (ApelApplicationStatus.VOTA_STAATUS_E.name().equals(status) || ApelApplicationStatus.VOTA_STAATUS_Y.name().equals(status)) {
            return isSchoolAdmin(user, student.getSchool());
        }
        return false;
    }

    public static boolean canCancelDirective(HoisUserDetails user, Directive directive) {
        return !ClassifierUtil.equals(DirectiveType.KASKKIRI_TYHIST, directive.getType())
            && ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD, directive.getStatus())
            && isSchoolAdmin(user, directive.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASKKIRI);
    }

    public static boolean canEditDirective(HoisUserDetails user, Directive directive) {
        return ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL, directive.getStatus())
            && isSchoolAdmin(user, directive.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASKKIRI);
    }

    public static boolean canViewStudent(HoisUserDetails user, Student student) {
        return isSchoolAdmin(user, student.getSchool()) || isSame(user, student) || isStudentRepresentative(user, student) || isTeacher(user, student.getSchool());
    }

    public static boolean canEditStudent(HoisUserDetails user, Student student) {
        return (isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR)) || isAdultStudent(user, student)|| isStudentRepresentative(user, student);
    }

    /**
     * Can given user add representative to given student?
     *
     * @param user
     * @param student
     * @return
     */
    public static boolean canAddStudentRepresentative(HoisUserDetails user, Student student) {
        return (isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR)) || isAdultStudent(user, student);
    }

    /**
     * Can given user edit given student representative record?
     *
     * @param user
     * @param representative
     * @return
     */
    public static boolean canEditStudentRepresentative(HoisUserDetails user, StudentRepresentative representative) {
        Student student = representative.getStudent();
        if((isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR)) || isAdultStudent(user, student)) {
            return true;
        }
        // representative can edit it's own record even if student's data is not visible to him/her
        return user.isRepresentative() && EntityUtil.getId(representative.getPerson()).equals(user.getPersonId());
    }

    /**
     * Can given user delete given student representative record?
     *
     * @param user
     * @param representative
     * @return
     */
    public static boolean canDeleteStudentRepresentative(HoisUserDetails user, StudentRepresentative representative) {
        Student student = representative.getStudent();
        if((isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR)) || isAdultStudent(user, student)) {
            return true;
        }
        return false;
    }

    /**
     * Is given user admin of given school?
     *
     * @param user
     * @param school
     * @return
     */
    public static boolean isSchoolAdmin(HoisUserDetails user, School school) {
        return user.isSchoolAdmin() && EntityUtil.getId(school).equals(user.getSchoolId());
    }

    public static boolean isStudent(HoisUserDetails user, School school) {
        return user.isStudent() && EntityUtil.getId(school).equals(user.getSchoolId());
    }

    public static boolean isAdultStudent(HoisUserDetails user, Student student) {
        return isStudent(user, student) && StudentUtil.isAdultAndDoNotNeedRepresentative(student);
    }

    public static boolean isSame(HoisUserDetails user, Student student) {
        return user.getPersonId().equals(EntityUtil.getId(student.getPerson()));
    }

    public static boolean isSamePerson(HoisUserDetails user, Person person) {
        return user.getPersonId().equals(EntityUtil.getId(person));
    }

    public static boolean isStudent(HoisUserDetails user, Student student) {
        return user.isStudent() && user.getStudentId().equals(EntityUtil.getId(student));
    }

    public static boolean isStudentRepresentative(HoisUserDetails user, Student student) {
        return user.isRepresentative() && student.getRepresentatives().stream().anyMatch(r -> EntityUtil.getId(r.getPerson()).equals(user.getPersonId()));
    }

    public static boolean isStudentGroupTeacher(HoisUserDetails user, Student student) {
        if(isTeacher(user, student.getSchool()) && student.getStudentGroup() != null) {
            Teacher teacher = student.getStudentGroup().getTeacher();
            return user.getTeacherId().equals(EntityUtil.getNullableId(teacher));
        }
        return false;
    }

    public static boolean isMainAdminOrExternalExpert(HoisUserDetails user) {
        return user.isMainAdmin() || user.isExternalExpert();
    }

    /**
     * Is given user teacher in given school?
     * @param user
     * @param school
     * @return
     */
    public static boolean isTeacher(HoisUserDetails user, School school) {
        return user.isTeacher() && EntityUtil.getId(school).equals(user.getSchoolId());
    }

    public static boolean isSchoolAdminOrStudent(HoisUserDetails user, School school) {
        return isSchoolAdmin(user, school) || isStudent(user, school);
    }

    public static boolean isSameSchool(HoisUserDetails user, School school) {
        Long schoolId = user.getSchoolId();
        return schoolId != null && schoolId.equals(EntityUtil.getNullableId(school));
    }

    public static void assertSameSchool(HoisUserDetails user, School school) {
        Long schoolId = user.getSchoolId();
        AssertionFailedException.throwIf(schoolId == null || !schoolId.equals(EntityUtil.getNullableId(school)), "School mismatch");
    }

    public static void assertIsMainAdminOrSchoolAdmin(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isMainAdmin() && !user.isSchoolAdmin(), "User is not admin");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(), "User is not school admin");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user, Permission permission, PermissionObject object) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin() || !hasPermission(user, permission, object), "User is not school admin or has no rights");
    }

    public static void assertIsMainAdmin(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isMainAdmin(), "User is not main admin");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user, School school) {
        AssertionFailedException.throwIf(!isSchoolAdmin(user, school), "User is not school admin in given school");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user, School school, Permission permission, PermissionObject object) {
        AssertionFailedException.throwIf(!isSchoolAdmin(user, school) || !hasPermission(user, permission, object), "User is not school admin in given school or has no rights");
    }

    public static void assertIsSchoolAdminOrTeacher(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin() && !user.isTeacher(), "User is not school admin or teacher");
    }

    public static void assertIsSchoolAdminOrTeacher(HoisUserDetails user, School school) {
        AssertionFailedException.throwIf(!isSchoolAdmin(user, school) && !isTeacher(user, school), "User is not school admin or teacher in given school");
    }

    public static void assertCanUpdateUser(String role) {
        AssertionFailedException.throwIf(role.equals(Role.ROLL_T.name()) || role.equals(Role.ROLL_L.name()),"Invalid role");
    }

    public static void assertUserBelongsToPerson(User user, Person person) {
        AssertionFailedException.throwIf(!EntityUtil.getId(person).equals(EntityUtil.getId(user.getPerson())), "Person and user don't match");
    }

    public static void assertIsPerson(HoisUserDetails user, Person person) {
        AssertionFailedException.throwIf(!user.getPersonId().equals(EntityUtil.getNullableId(person)), "Person and user don't match");
    }

    public static void assertIsStudent(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isStudent(), "User is not school student");
    }

    public static void assertIsSchoolAdminOrStudent(HoisUserDetails user, School school) {
        AssertionFailedException.throwIf(!isSchoolAdminOrStudent(user, school), "User is not school admin or student in given school");
    }

    public static void assertIsSchoolAdminOrStudent(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin() && !user.isStudent(), "User is not school admin or student");
    }

    public static void assertIsSchoolAdminOrStudentOrRepresentative(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin() && !user.isStudent() && !user.isRepresentative(),
                "User is not school admin, student, or student representative");
    }

    public static void assertCanViewStudent(HoisUserDetails user, Student student) {
        AssertionFailedException.throwIf(!canViewStudent(user, student),
                "User is not allowed to see student's information");
    }

    private static String roleName(Permission permission, PermissionObject object) {
        return ROLE_NAME_CACHE.get(permission).computeIfAbsent(object, o ->  "ROLE_" + permission.name() + "_" + o.name());
    }

    private static final EnumMap<Permission, ConcurrentMap<PermissionObject, String>> ROLE_NAME_CACHE = new EnumMap<>(Permission.class);
    static {
        for(Permission p : Permission.values()) {
            ROLE_NAME_CACHE.put(p, new ConcurrentHashMap<>());
        }
    }
}
