package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.security.access.AccessDeniedException;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.basemodule.BaseModule;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.student.StudentSupportService;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;

public abstract class UserUtil {

    public static boolean hasPermission(HoisUserDetails user, Permission permission, PermissionObject object) {
        String s = roleName(permission, object);
        return user.getAuthorities().stream().anyMatch(a -> s.equals(a.getAuthority()));
    }

    public static boolean canSubmitApplication(HoisUserDetails user, Application application) {
        if(ClassifierUtil.equals(ApplicationStatus.AVALDUS_STAATUS_KOOST, application.getStatus())) {
            Student student = application.getStudent();
            return isStudent(user, student) || isSchoolAdmin(user, student.getSchool()) || isStudentRepresentative(user, student) || (isStudentGroupTeacher(user, student)
                    || ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_TUGI, application.getType()));
        }
        
        return false;
    }

    public static boolean canRejectApplication(HoisUserDetails user, Application application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        if (!StudentUtil.canBeEdited(student)) {
            return false;
        }
        if (ApplicationStatus.AVALDUS_STAATUS_KOOST.name().equals(status)) {
            return Boolean.TRUE.equals(application.getNeedsRepresentativeConfirm()) && (isStudentRepresentative(user, student) || isSchoolAdmin(user, student.getSchool()));
        }
        if (ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name().equals(status)) {
            return isSchoolAdmin(user, student.getSchool()) && (!ApplicationType.AVALDUS_LIIK_TUGI.name().equals(EntityUtil.getCode(application.getType())) || application.getCommittee() == null);
        }
        return false;
    }

    public static boolean canConfirmApplication(HoisUserDetails user, Application application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        return (ApplicationUtil.CAN_BE_CONFIRMED.contains(EnumUtil.valueOf(ApplicationType.class, EntityUtil.getCode(application.getType()))))
                && (ApplicationStatus.AVALDUS_STAATUS_ESIT.name().equals(status) 
                        || ApplicationStatus.AVALDUS_STAATUS_YLEVAAT.name().equals(status)) 
                && isSchoolAdmin(user, student.getSchool()) 
                && hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_AVALDUS)
                && StudentUtil.canBeEdited(student);
    }
    
    public static boolean canConfirmApplicationConfirmation(HoisUserDetails user, Application application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        return (ApplicationUtil.REQUIRE_REPRESENTATIVE_CONFIRM.contains(EnumUtil.valueOf(ApplicationType.class, EntityUtil.getCode(application.getType()))))
                && ApplicationStatus.AVALDUS_STAATUS_KINNITAM.name().equals(status)
                && (isSchoolAdmin(user, student.getSchool()) || isAdultStudent(user, student) || isStudentRepresentative(user, student))
                && StudentUtil.canBeEdited(student);
    }
    
    public static boolean canRemoveApplicationConfirmation(HoisUserDetails user, Application application) {
        String status = EntityUtil.getCode(application.getStatus());
        Student student = application.getStudent();
        return (ApplicationUtil.REQUIRE_REPRESENTATIVE_CONFIRM.contains(EnumUtil.valueOf(ApplicationType.class, EntityUtil.getCode(application.getType()))))
                && (ApplicationStatus.AVALDUS_STAATUS_KINNITAM.name().equals(status) || ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name().equals(status)
                        || ApplicationStatus.AVALDUS_STAATUS_TAGASI.name().equals(status))
                && ((application.getIsDecided() != null && application.getCommitteeDecisionAdded() != null) // checks Boolean and LocalDateTime
                        || (application.getIsRepresentativeConfirmed() != null && application.getRepresentativeConfirmed() != null)) // checks Boolean and LocalDateTime
                && isSchoolAdmin(user, student.getSchool())
                && hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_AVALDUS) // Has to have an user right.
                && StudentUtil.canBeEdited(student);
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
        return isSchoolAdmin(user, student.getSchool()) || isStudent(user, student)
                || isActiveStudentRepresentative(user, student) || isTeacher(user, student.getSchool());
    }

    public static boolean canViewStudentSpecificData(HoisUserDetails user, Student student) {
        return isSchoolAdmin(user, student.getSchool()) || isStudent(user, student)
                || isActiveStudentRepresentative(user, student) || isStudentGroupTeacher(user, student);
    }

    public static boolean canEditStudent(HoisUserDetails user, Student student) {
        return ((isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR))
                || (isStudentGroupTeacher(user, student) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR))
                || isAdultStudent(user, student) || isActiveStudentRepresentative(user, student))
                && StudentUtil.canBeEdited(student);
    }
    
    public static boolean canUpdateStudentRR(HoisUserDetails user, Student student) {
        return StudentUtil.isActive(student) && (
                (isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_RR)) ||
                isStudentRepresentative(user, student) ||
                (isStudent(user, student) && StudentUtil.isAdultAndDoNotNeedRepresentative(student))
                );
    }

    public static boolean canViewStudentSupportServices(HoisUserDetails user, Student student) {
        return isStudent(user, student) || isActiveStudentRepresentative(user, student)
                || isTeacher(user, student.getSchool()) || isSchoolAdmin(user, student.getSchool());
    }

    public static boolean canViewPrivateStudentSupportServices(HoisUserDetails user, Student student) {
        return isStudent(user, student) ||isActiveStudentRepresentative(user, student)
                || isStudentGroupTeacher(user, student) || (isSchoolAdmin(user, student.getSchool())
                        && hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUGITEENUS));
    }
    
    public static boolean canViewStudentSupportServices(HoisUserDetails user, Student student, StudentSupportService service) {
        if (!student.equals(service.getStudent())) {
            return false;
        }
        if (isStudent(user, student) || isActiveStudentRepresentative(user, student)) {
            return true;
        }
        if (isTeacher(user, student.getSchool()) && (Boolean.TRUE.equals(service.getIsPublic()) || isStudentGroupTeacher(user, student))) {
            return true;
        }
        if (isSchoolAdmin(user, student.getSchool()) && (Boolean.TRUE.equals(service.getIsPublic()) || hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_TUGITEENUS))) {
            return true;
        }
        return false;
    }


    public static boolean canEditStudentSupportServices(HoisUserDetails user, Student student) {
        return UserUtil.isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUGITEENUS) && StudentUtil.canBeEdited(student);
    }

    /**
     * Can given user add representative to given student?
     *
     * @param user
     * @param student
     * @return
     */
    public static boolean canAddStudentRepresentative(HoisUserDetails user, Student student) {
        return ((isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR))
                || (isStudentGroupTeacher(user, student) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR))
                || isAdultStudent(user, student))
                && StudentUtil.canBeEdited(student);
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
        if (((isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR))
                || (isStudentGroupTeacher(user, student) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR))
                || isAdultStudent(user, student))
                && StudentUtil.canBeEdited(student)) {
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
        if (((isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR))
                || (isStudentGroupTeacher(user, student) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR))
                || isAdultStudent(user, student))
                && StudentUtil.canBeEdited(student)) {
            return true;
        }
        return false;
    }

    public static boolean canViewBaseModule(HoisUserDetails user) {
        return hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_BAASMOODUL);
    }
    
    public static boolean canEditBaseModule(HoisUserDetails user) {
        return hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_BAASMOODUL);
    }
    
    public static boolean canDeleteBaseModule(HoisUserDetails user, BaseModule module) {
        if (hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_BAASMOODUL) && module.getCurriculumModules().isEmpty()) {
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
    
    public static boolean isStudentAndDoNotNeedRepresentative(HoisUserDetails user, Student student) {
        return isStudent(user, student) && StudentUtil.doNotNeedRepresentative(student);
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

    /**
     * @since 10.12.2018 Before it compared Person ID which would make a possibility to get information with any role (just to be logged in with account).
     * Replaced by comparing Student ID.
     * 
     * @param user
     * @param student
     * @return
     */
    public static boolean isStudentRepresentative(HoisUserDetails user, Student student) {
        return user.isRepresentative() && user.getStudentId().equals(student.getId());
    }
    
    /**
     * Same as isStudnentRepresentative, but also controls if given student information is visible for a representative.
     * 
     * @param user
     * @param student
     * @return
     */
    public static boolean isActiveStudentRepresentative(HoisUserDetails user, Student student) {
        return isStudentRepresentative(user, student) && student.getRepresentatives().stream()
                .filter(rep -> user.getPersonId().equals(EntityUtil.getId(rep.getPerson())) && Boolean.TRUE.equals(rep.getIsStudentVisible()))
                .findFirst().isPresent();
    }

    public static boolean isStudentGroupTeacher(HoisUserDetails user, Student student) {
        if(isTeacher(user, student.getSchool()) && student.getStudentGroup() != null) {
            Teacher teacher = student.getStudentGroup().getTeacher();
            return user.getTeacherId().equals(EntityUtil.getNullableId(teacher));
        }
        return false;
    }
    
    public static boolean isStudentGroupTeacher(HoisUserDetails user, StudentGroup studentGroup) {
        if(isTeacher(user, studentGroup.getSchool())) {
            Teacher teacher = studentGroup.getTeacher();
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
    
    public static boolean isActiveUser(User user) {
        LocalDate now = LocalDate.now();
        return (user.getValidFrom() == null || !user.getValidFrom().isAfter(now)) && (user.getValidThru() == null || !user.getValidThru().isBefore(now));
    }

    public static void assertSameSchool(HoisUserDetails user, School school) {
        Long schoolId = user.getSchoolId();
        AssertionFailedException.throwIf(schoolId == null || !schoolId.equals(EntityUtil.getNullableId(school)), "School mismatch");
    }
    
    public static void assertSameSchoolOrIsMainAdminOrExternalExpert(HoisUserDetails user, School school) {
        Long schoolId = user.getSchoolId();
        AssertionFailedException.throwIf(!isMainAdminOrExternalExpert(user)
                && (schoolId == null || !schoolId.equals(EntityUtil.getNullableId(school))), "School mismatch");
    }

    public static void assertIsMainAdminOrSchoolAdmin(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isMainAdmin() && !user.isSchoolAdmin(), "User is not admin");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin(), "User is not school admin");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user, Permission permission, PermissionObject object) {
        throwAccessDeniedIf(!user.isSchoolAdmin() || !hasPermission(user, permission, object), "User is not school admin or has no rights");
    }

    public static void assertIsMainAdmin(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isMainAdmin(), "User is not main admin");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user, School school) {
        AssertionFailedException.throwIf(!isSchoolAdmin(user, school), "User is not school admin in given school");
    }

    public static void assertHasPermission(HoisUserDetails user, Permission permission, PermissionObject object) {
        throwAccessDeniedIf(!hasPermission(user, permission, object), "User has no rights");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user, School school, Permission permission, PermissionObject object) {
        throwAccessDeniedIf(!isSchoolAdmin(user, school) || !hasPermission(user, permission, object), "User is not school admin in given school or has no rights");
    }

    public static void assertIsSchoolAdminOrTeacher(HoisUserDetails user) {
        AssertionFailedException.throwIf(!user.isSchoolAdmin() && !user.isTeacher(), "User is not school admin or teacher");
    }

    public static void assertIsSchoolAdminOrTeacher(HoisUserDetails user, School school) {
        AssertionFailedException.throwIf(!isSchoolAdmin(user, school) && !isTeacher(user, school), "User is not school admin or teacher in given school");
    }

    public static void assertIsTeacher(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isTeacher(), "User is not teacher");
    }

    public static void assertIsTeacher(HoisUserDetails user, Permission permission, PermissionObject object) {
        throwAccessDeniedIf(!user.isTeacher() || !hasPermission(user, permission, object), "User is not teacher or has no rights");
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
    
    public static void assertIsStudent(HoisUserDetails user, Student student) {
        AssertionFailedException.throwIf(!isStudent(user, student), "main.messages.error.nopermission");
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

    public static void assertIsSchoolAdminOrStudentGroupTeacher(HoisUserDetails user, StudentGroup studentGroup) {
        AssertionFailedException.throwIf(!isSchoolAdmin(user, studentGroup.getSchool()) && !isStudentGroupTeacher(user, studentGroup),
                "User is not school admin or student group teacher");
    }

    public static void assertIsSchoolAdminOrStudentGroupTeacher(HoisUserDetails user, Student student) {
        AssertionFailedException.throwIf(!isSchoolAdmin(user, student.getSchool()) && !UserUtil.isStudentGroupTeacher(user, student),
                "User is not school admin or student group teacher");
    }

    public static void assertCanViewStudent(HoisUserDetails user, Student student) {
        AssertionFailedException.throwIf(!canViewStudent(user, student),
                "User is not allowed to see student's information");
    }

    public static void assertCanViewStudentSpecificData(HoisUserDetails user, Student student) {
        AssertionFailedException.throwIf(!canViewStudentSpecificData(user, student),
                "User is not allowed to see student's specific information");
    }
    
    public static void assertCanUpdateStudentRR(HoisUserDetails user, Student student) {
        ValidationFailedException.throwIf(!canUpdateStudentRR(user, student), "main.messages.error.nopermission");
    }

    public static void assertCanViewStudentSupportServices(HoisUserDetails user, Student student) {
        ValidationFailedException.throwIf(!canViewStudentSupportServices(user, student), "main.messages.error.nopermission");
    }
    
    public static void assertCanViewPrivateStudentSupportServices(HoisUserDetails user, Student student) {
        ValidationFailedException.throwIf(!canViewPrivateStudentSupportServices(user, student), "main.messages.error.nopermission");
    }

    public static void assertCanViewStudentSupportServices(HoisUserDetails user, Student student, StudentSupportService service) {
        ValidationFailedException.throwIf(!canViewStudentSupportServices(user, student, service), "main.messages.error.nopermission");
    }
    public static void assertCanEditStudentSupportServices(HoisUserDetails user, Student student) {
        ValidationFailedException.throwIf(!canEditStudentSupportServices(user, student), "main.messages.error.nopermission");
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
    
    public static void throwAccessDeniedIf(boolean expression) {
        throwAccessDeniedIf(expression, "main.messages.error.nopermission");
    }
    
    public static void throwAccessDeniedIf(boolean expression, String message) {
        if (expression) {
            throw new AccessDeniedException(message);
        }
    }
    
}
