package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import ee.hitsa.ois.auth.LoginMethod;
import org.springframework.security.access.AccessDeniedException;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.student.StudentSupportService;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.Role;
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
        return !ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_TYHIST, DirectiveType.KASKKIRI_VALISKATK, DirectiveType.KASKKIRI_DUPLIKAAT)
            && ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD, directive.getStatus())
            && isSchoolAdmin(user, directive.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASKKIRI);
    }

    public static boolean canEditDirective(HoisUserDetails user, Directive directive) {
        return ClassifierUtil.equals(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL, directive.getStatus())
            && isSchoolAdmin(user, directive.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_KASKKIRI)
            && !((ClassifierUtil.equals(DirectiveType.KASKKIRI_TUGI, directive.getType()) || ClassifierUtil.equals(DirectiveType.KASKKIRI_TUGILOPP, directive.getType()))
            && !hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUGITEENUS));
    }

    public static boolean canViewStudent(HoisUserDetails user, Student student) {
        return isSchoolAdmin(user, student.getSchool()) || isStudent(user, student)
                || isActiveStudentRepresentative(user, student) || isTeacher(user, student.getSchool())
                || isLeadingTeacher(user, student);
    }

    public static boolean canViewStudentSpecificData(HoisUserDetails user, Student student) {
        return isSchoolAdmin(user, student.getSchool()) || isStudent(user, student)
                || isActiveStudentRepresentative(user, student) || isStudentGroupTeacher(user, student)
                || isLeadingTeacher(user, student);
    }

    public static boolean canEditStudent(HoisUserDetails user, Student student) {
        if (StudentUtil.canBeEdited(student)) {
            if (isAdultStudent(user, student) || isActiveStudentRepresentative(user, student)) {
                return true;
            }
            if (isSchoolAdmin(user, student.getSchool()) || isStudentGroupTeacher(user, student)
                    || isLeadingTeacher(user, student)) {
                return hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR);
            }
        }
        return false;
    }

    public static boolean canUpdateStudentRR(HoisUserDetails user, Student student) {
        return StudentUtil.isActive(student) && (
                (isSchoolAdmin(user, student.getSchool()) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_RR)) ||
                isStudentRepresentative(user, student) ||
                (isStudent(user, student) && StudentUtil.isAdultAndDoNotNeedRepresentative(student))
                );
    }

    public static boolean canRequestFotoBoxPhotos(HoisUserDetails user) {
        return user.isSchoolAdmin() && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR);
    }

    public static boolean canRequestStudentFotoBoxPhoto(HoisUserDetails user, Student student) {
        return StudentUtil.isActive(student) && isSchoolAdmin(user, student.getSchool())
                && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR);
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
    
    public static boolean canViewStudentSupportService(HoisUserDetails user, Student student, StudentSupportService service) {
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

    public static boolean canEditStudentSupportService(HoisUserDetails user, Student student, StudentSupportService service) {
        return canEditStudentSupportServices(user, student) && !Boolean.TRUE.equals(service.getEhis());
    }

    public static boolean canEditStudentSupportServices(HoisUserDetails user, Student student) {
        return isSchoolAdmin(user, student.getSchool())
                && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_TUGITEENUS)
                && StudentUtil.canBeEdited(student);
    }

    /**
     * Can given user add representative to given student?
     *
     * @param user
     * @param student
     * @return
     */
    public static boolean canAddStudentRepresentative(HoisUserDetails user, Student student) {
        if (StudentUtil.canBeEdited(student)) {
            if (isAdultStudent(user, student)) {
                return true;
            }
            if (isSchoolAdmin(user, student.getSchool()) || isStudentGroupTeacher(user, student)
                    || isLeadingTeacher(user, student)) {
                return hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR);
            }
        }
        return false;
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
        if (StudentUtil.canBeEdited(student)) {
            if (isAdultStudent(user, student)) {
                return true;
            }
            if (isSchoolAdmin(user, student.getSchool()) || isStudentGroupTeacher(user, student)
                    || isLeadingTeacher(user, student)) {
                return hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR);
            }
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
        if (StudentUtil.canBeEdited(student)) {
            if (isAdultStudent(user, student)) {
                return true;
            }
            if (isSchoolAdmin(user, student.getSchool()) || isStudentGroupTeacher(user, student)
                    || isLeadingTeacher(user, student)) {
                return hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR);
            }
        }
        return false;
    }

    /**
     * Can given user change given student modules?
     *
     * @param user
     * @param student
     * @return
     */
    public static boolean canChangeStudentModules(HoisUserDetails user, Student student) {
        return StudentUtil.isActive(student) && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR)
                && (isSchoolAdmin(user, student.getSchool())
                        || isLeadingTeacher(user, student));
    }

    private static boolean canMarkStudentModuleComplete(HoisUserDetails user, Student student,
            CurriculumVersionHigherModule module) {
        return canChangeModuleCompletion(user, student)
                && !HigherModuleType.CAN_NOT_MARK_AS_COMPLETE.contains(EntityUtil.getCode(module.getType()));
    }

    private static boolean canRemoveModuleCompletion(HoisUserDetails user, Student student) {
        return canChangeModuleCompletion(user, student);
    }

    public static boolean canChangeModuleCompletion(HoisUserDetails user, Student student) {
        return StudentUtil.isActive(student) && isSchoolAdmin(user, student.getSchool())
                && hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OKTAITMINE);
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

    public static boolean isSchoolAdminOrLeadingTeacher(HoisUserDetails user, School school) {
        return isSchoolAdmin(user, school) || isLeadingTeacher(user, school);
    }

    public static boolean isSchoolAdminOrLeadingTeacher(HoisUserDetails user, StudentGroup studentGroup) {
        return isSchoolAdmin(user, studentGroup.getSchool()) || isLeadingTeacher(user, studentGroup.getCurriculum());
    }

    public static boolean isSchoolAdminOrLeadingTeacher(HoisUserDetails user, Journal journal) {
        return isSchoolAdmin(user, journal.getSchool()) || isLeadingTeacher(user, journal);
    }

    public static boolean isSchoolAdminOrLeadingTeacher(HoisUserDetails user, Student student) {
        return isSchoolAdmin(user, student.getSchool()) || isLeadingTeacher(user, student);
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

    public static boolean isJournalTeacher(HoisUserDetails user, Journal journal) {
        return user.isTeacher() && journal.getJournalTeachers().stream()
                .anyMatch(t -> user.getTeacherId().equals(EntityUtil.getId(t.getTeacher())));
    }

    public static boolean isLeadingTeacher(HoisUserDetails user, School school) {
        return user.isLeadingTeacher() && EntityUtil.getId(school).equals(user.getSchoolId());
    }

    public static boolean isLeadingTeacher(HoisUserDetails user, Student student) {
        if (student.getCurriculumVersion() == null) return false;
        return isLeadingTeacher(user, EntityUtil.getId(student.getCurriculumVersion().getCurriculum()));
    }

    public static boolean isLeadingTeacher(HoisUserDetails user, Subject subject) {
        Set<Long> subjectCurriculumIds = subject.getCurriculumVersionHigherModuleSubjects().stream()
                .map(s -> EntityUtil.getId(s.getModule().getCurriculumVersion().getCurriculum()))
                .collect(Collectors.toSet());
        return isLeadingTeacher(user, subjectCurriculumIds);
    }

    public static boolean isLeadingTeacher(HoisUserDetails user, Journal journal) {
        Set<Long> journalCurriculumIds = journal
                .getJournalOccupationModuleThemes().stream().map(jot -> EntityUtil.getId(jot
                        .getCurriculumVersionOccupationModuleTheme().getModule().getCurriculumModule().getCurriculum()))
                .collect(Collectors.toSet());
        return UserUtil.isLeadingTeacher(user, journalCurriculumIds);
    }

    public static boolean isLeadingTeacher(HoisUserDetails user, Long curriculumId) {
        return user.isLeadingTeacher() && user.getCurriculumIds().stream().anyMatch(c -> c.equals(curriculumId));
    }
    
    public static boolean isLeadingTeacher(HoisUserDetails user, Curriculum curriculum) {
        if (curriculum == null) return false;
        return isLeadingTeacher(user, EntityUtil.getId(curriculum));
    }


    public static boolean isLeadingTeacher(HoisUserDetails user, Set<Long> curriculumIds) {
        return user.isLeadingTeacher() && user.getCurriculumIds().stream().anyMatch(c -> curriculumIds.contains(c));
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

    public static boolean isOAuthLoginType(HoisUserDetails user) {
        return EnumUtil.toNameList(LoginMethod.LOGIN_TYPE_T, LoginMethod.LOGIN_TYPE_H).contains(user.getLoginMethod().name());
    }

    public static void assertSameSchool(HoisUserDetails user, School school) {
        Long schoolId = user.getSchoolId();
        throwAccessDeniedIf(schoolId == null || !schoolId.equals(EntityUtil.getNullableId(school)), "School mismatch");
    }

    public static void assertSameSchoolOrIsMainAdminOrExternalExpert(HoisUserDetails user, School school) {
        Long schoolId = user.getSchoolId();
        throwAccessDeniedIf(!isMainAdminOrExternalExpert(user)
                && (schoolId == null || !schoolId.equals(EntityUtil.getNullableId(school))), "School mismatch");
    }

    public static void assertIsMainAdminOrSchoolAdmin(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isMainAdmin() && !user.isSchoolAdmin(), "User is not admin");
    }

    public static void assertIsMainAdminOrSchoolAdminOrLeadingTeacher(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isMainAdmin() && !user.isSchoolAdmin() && !user.isLeadingTeacher(),
                "User is not admin or leading teacher");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isSchoolAdmin(), "User is not school admin");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user, Permission permission, PermissionObject object) {
        throwAccessDeniedIf(!user.isSchoolAdmin() || !hasPermission(user, permission, object), "User is not school admin or has no rights");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user, School school, Permission permission, PermissionObject object) {
        throwAccessDeniedIf(!isSchoolAdmin(user, school) || !hasPermission(user, permission, object), "User is not school admin in given school or has no rights");
    }

    public static void assertIsMainAdmin(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isMainAdmin(), "User is not main admin");
    }

    public static void assertIsSchoolAdmin(HoisUserDetails user, School school) {
        throwAccessDeniedIf(!isSchoolAdmin(user, school), "User is not school admin in given school");
    }

    public static void assertHasPermission(HoisUserDetails user, Permission permission, PermissionObject object) {
        throwAccessDeniedIf(!hasPermission(user, permission, object), "User has no rights");
    }

    public static void assertIsSchoolAdminOrLeadingTeacher(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isSchoolAdmin() && !user.isLeadingTeacher(),
                "User is not school admin or leading teacher");
    }

    public static void assertIsSchoolAdminOrLeadingTeacher(HoisUserDetails user, School school) {
        throwAccessDeniedIf(!isSchoolAdminOrLeadingTeacher(user, school),
                "User is not school admin in given school or student's curriculum leading teacher");
    }

    public static void assertIsSchoolAdminOrLeadingTeacher(HoisUserDetails user, StudentGroup studentGroup) {
        throwAccessDeniedIf(!isSchoolAdminOrLeadingTeacher(user, studentGroup),
                "User is not school admin in given school or student group's curriculum leading teacher");
    }

    public static void assertIsSchoolAdminOrLeadingTeacher(HoisUserDetails user, Journal journal) {
        throwAccessDeniedIf(!isSchoolAdminOrLeadingTeacher(user, journal),
                "User is not school admin in given school or journal's curriculum leading teacher");
    }

    public static void assertIsSchoolAdminOrLeadingTeacher(HoisUserDetails user, Permission permission, PermissionObject object) {
        throwAccessDeniedIf(!(user.isSchoolAdmin() || user.isLeadingTeacher()) || !hasPermission(user, permission, object),
                "User is not school admin or leading teacher or has no rights");
    }

    public static void assertIsSchoolAdminOrLeadingTeacherOrTeacher(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isSchoolAdmin() && !user.isLeadingTeacher() && !user.isTeacher(),
                "User is not school admin or leading teacher or teacher");
    }

    public static void assertIsSchoolAdminOrLeadingTeacherOrTeacher(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!isSchoolAdmin(user, student.getSchool())
                && !isLeadingTeacher(user, student)
                && !isTeacher(user, student.getSchool()),
                "User is not school admin in given school or student's curriculum leading teacher or teacher");
    }

    public static void assertIsSchoolAdminOrLeadingTeacherOrTeacher(HoisUserDetails user, Curriculum curriculum) {
        throwAccessDeniedIf(!isSchoolAdmin(user, curriculum.getSchool())
                && !isLeadingTeacher(user, EntityUtil.getId(curriculum))
                && !isTeacher(user, curriculum.getSchool()),
                "User is not school admin in given school or student's curriculum leading teacher or teacher");
    }

    public static void assertIsSchoolAdminOrLeadingTeacher(HoisUserDetails user, School school, Permission permission,
            PermissionObject object) {
        throwAccessDeniedIf(!(isSchoolAdmin(user, school) || isLeadingTeacher(user, school)) || !hasPermission(user, permission, object),
                "User is not school admin in given school or has no rights");
    }

    public static void assertIsSchoolAdminOrLeadingTeacher(HoisUserDetails user, Student student, Permission permission,
            PermissionObject object) {
        throwAccessDeniedIf(!(isSchoolAdmin(user, student.getSchool())
                        || isLeadingTeacher(user, student))
                        || !hasPermission(user, permission, object),
                "User is not school admin in given school or student's curriculum leading teacher or has no rights");
    }

    public static void assertIsSchoolAdminOrLeadingTeacherOrStudentOrRepresentative(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isSchoolAdmin() && !user.isLeadingTeacher() && !user.isStudent() && !user.isRepresentative(),
                "User is not school admin, leading teacher, student, or student representative");
    }

    public static void assertIsSchoolAdminOrLeadingTeacherOrStudentGroupTeacher(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!isSchoolAdmin(user, student.getSchool()) 
                && !isLeadingTeacher(user, student)
                && !isStudentGroupTeacher(user, student),
                "User is not school admin or student's curriculum leading teacher or student group teacher");
    }

    public static void assertIsSchoolAdminOrTeacher(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isSchoolAdmin() && !user.isTeacher(), "User is not school admin or teacher");
    }

    public static void assertIsSchoolAdminOrTeacher(HoisUserDetails user, School school) {
        throwAccessDeniedIf(!isSchoolAdmin(user, school) && !isTeacher(user, school), "User is not school admin or teacher in given school");
    }

    public static void assertIsLeadingTeacher(HoisUserDetails user, School school) {
        throwAccessDeniedIf(!isLeadingTeacher(user, school), "User is not school admin");
    }

    public static void assertIsTeacher(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isTeacher(), "User is not teacher");
    }

    public static void assertIsTeacher(HoisUserDetails user, Permission permission, PermissionObject object) {
        throwAccessDeniedIf(!user.isTeacher() || !hasPermission(user, permission, object), "User is not teacher or has no rights");
    }

    public static void assertCanUpdateUser(String role) {
        throwAccessDeniedIf(role.equals(Role.ROLL_T.name()) || role.equals(Role.ROLL_L.name()),"Invalid role");
    }

    public static void assertUserBelongsToPerson(User user, Person person) {
        throwAccessDeniedIf(!EntityUtil.getId(person).equals(EntityUtil.getId(user.getPerson())), "Person and user don't match");
    }

    public static void assertIsPerson(HoisUserDetails user, Person person) {
        throwAccessDeniedIf(!user.getPersonId().equals(EntityUtil.getNullableId(person)), "Person and user don't match");
    }

    public static void assertIsStudent(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isStudent(), "User is not school student");
    }

    public static void assertIsStudent(HoisUserDetails user, School school) {
        throwAccessDeniedIf(!isStudent(user, school), "User is not school student");
    }
    
    public static void assertIsStudent(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!isStudent(user, student));
    }

    public static void assertIsSchoolAdminOrStudent(HoisUserDetails user, School school) {
        throwAccessDeniedIf(!isSchoolAdminOrStudent(user, school), "User is not school admin or student in given school");
    }

    public static void assertIsSchoolAdminOrStudent(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isSchoolAdmin() && !user.isStudent(), "User is not school admin or student");
    }

    public static void assertIsSchoolAdminOrStudentOrRepresentative(HoisUserDetails user) {
        throwAccessDeniedIf(!user.isSchoolAdmin() && !user.isStudent() && !user.isRepresentative(),
                "User is not school admin, student, or student representative");
    }

    public static void assertIsSchoolAdminOrStudentGroupTeacher(HoisUserDetails user, StudentGroup studentGroup) {
        throwAccessDeniedIf(!isSchoolAdmin(user, studentGroup.getSchool()) && !isStudentGroupTeacher(user, studentGroup),
                "User is not school admin or student group teacher");
    }

    public static void assertIsSchoolAdminOrStudentGroupTeacher(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!isSchoolAdmin(user, student.getSchool()) && !isStudentGroupTeacher(user, student),
                "User is not school admin or student group teacher");
    }

    public static void assertIsSchoolWithoutEkis(School school) {
        throwAccessDeniedIf(school.getIsWithoutEkis() == null || !school.getIsWithoutEkis().booleanValue(), 
                "School should not be using ekis");
    }

    public static void assertCanViewStudent(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!canViewStudent(user, student),
                "User is not allowed to see student's information");
    }
    
    public static void assertCanViewStudentAddInfo(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!canViewStudentAddInfo(user, student),
                "User is not allowed to see student's additional information");
    }

    public static void assertCanViewStudentSpecificData(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!canViewStudentSpecificData(user, student),
                "User is not allowed to see student's specific information");
    }

    public static void assertCanUpdateStudentRR(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!canUpdateStudentRR(user, student), "main.messages.error.nopermission");
    }

    public static void assertCanViewStudentSupportServices(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!canViewStudentSupportServices(user, student));
    }

    public static void assertCanViewPrivateStudentSupportServices(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!canViewPrivateStudentSupportServices(user, student));
    }

    public static void assertCanViewStudentSupportService(HoisUserDetails user, Student student,
            StudentSupportService service) {
        throwAccessDeniedIf(!canViewStudentSupportService(user, student, service));
    }

    public static void assertCanEditStudentSupportServices(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!canEditStudentSupportServices(user, student));
    }

    public static void assertCanEditStudentSupportService(HoisUserDetails user, Student student, StudentSupportService service) {
        throwAccessDeniedIf(!canEditStudentSupportService(user, student, service));
    }

    public static void assertCanDeleteStudentSupportService(HoisUserDetails user, Student student, StudentSupportService service) {
        throwAccessDeniedIf(!canEditStudentSupportService(user, student, service));
    }

    public static void assertCanMarkStudentModuleComplete(HoisUserDetails user, Student student,
            CurriculumVersionHigherModule module) {
        throwAccessDeniedIf(!canMarkStudentModuleComplete(user, student, module));
    }

    public static void assertCanRemoveModuleCompletion(HoisUserDetails user, Student student) {
        throwAccessDeniedIf(!canRemoveModuleCompletion(user, student));
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

    public static void assertIsNotGuestStudent(Student student) {
        throwAccessDeniedIf(StudentUtil.isGuestStudent(student), "main.messages.error.guestStudent");
    }

    public static void assertOneSchoolAdminPerSchoolUser(Person person, Long schoolId) {
        assertOneSchoolAdminPerSchoolUser(person, schoolId, null);
    }

    public static void assertOneSchoolAdminPerSchoolUser(Person person, Long schoolId, User user) {
        if (user != null && EntityUtil.getNullableCode(user.getRole()).equals(Role.ROLL_A.name())) {
            // ignored in case if user is already an admin
            return;
        }
        ValidationFailedException.throwIf(person.getUsers().stream()
                .filter(u -> EntityUtil.getCode(u.getRole()).equals(Role.ROLL_A.name()) && u.getSchool() != null)
                .filter(u -> EntityUtil.getId(u.getSchool()).equals(schoolId))
                .filter(u -> user == null || !user.getId().equals(EntityUtil.getId(u)))
                .filter(u -> DateUtils.isValid(u.getValidFrom(), u.getValidThru()))
                .findAny().isPresent(), "user.shouldBeUniqueAdmin");
    }

    public static boolean canViewStudentAddInfo(HoisUserDetails user, Student student) {
        if (StudentUtil.canBeEdited(student)) {
            if (isSchoolAdmin(user, student.getSchool()) || isStudentGroupTeacher(user, student)
                    || isLeadingTeacher(user, student)) {
                return hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_OPPUR);
            }
        }
        return false;
    }
    
}
