package ee.hitsa.ois.util;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ScholarshipType;
import ee.hitsa.ois.validation.ValidationFailedException;
import org.springframework.security.access.AccessDeniedException;

import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.ScholarshipStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;

public class ScholarshipUtil {

    public static boolean isScholarship(ScholarshipTerm term) {
        return isScholarship(EntityUtil.getCode(term.getType()));
    }

    public static boolean isScholarship(String type) {
        return ScholarshipType.SCHOLARSHIPS.contains(type);
    }

    public static void assertCanSearchApplications(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        // student group teacher doesn't need to have STIPTOETUS user right
        if (!user.isTeacher() ) {
            UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        }
    }

    public static void assertCanViewApplication(HoisUserDetails user, ScholarshipApplication application) {
        if (user.isStudent()) {
            UserUtil.throwAccessDeniedIf(!user.getStudentId().equals(EntityUtil.getId(application.getStudent())),
                    "User student does not match application student");
        } else {
            boolean hasViewPerm = UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_STIPTOETUS);

            if (user.isSchoolAdmin()) {
                UserUtil.throwAccessDeniedIf(!hasViewPerm);
                UserUtil.throwAccessDeniedIf(
                        !user.getSchoolId().equals(EntityUtil.getId(application.getScholarshipTerm().getSchool())),
                        "User school does not match application scholarship term school");
            } else if (user.isLeadingTeacher()) {
                UserUtil.throwAccessDeniedIf(!hasViewPerm);
                Committee committee = application.getScholarshipTerm().getCommittee();
                Long applicationCurriculumId = EntityUtil.getId(application.getCurriculumVersion().getCurriculum());
                UserUtil.throwAccessDeniedIf(
                        !(UserUtil.isLeadingTeacher(user, applicationCurriculumId) || isInCommitte(user, committee)),
                        "Student is not in leading teacher's curriculum or part of committee");
            } else if (user.isTeacher()) {
                Committee committee = application.getScholarshipTerm().getCommittee();
                UserUtil.throwAccessDeniedIf(
                        !(isStudentGroupTeacher(user, application) || (hasViewPerm && isInCommitte(user, committee))),
                        "User teacher does not match application student group teacher or is not part of committee");
            } else {
                throw new AccessDeniedException("User is not application student or school admin or leading teacher " +
                        "or application student group teacher or part of committee");
            }
        }
    }

    public static void assertCanEditApplication(HoisUserDetails user, ScholarshipApplication application) {
        AssertionFailedException.throwIf(!applicationOfEditingStatus(application), "Invalid application status");
        assertApplicationPeriod(application.getScholarshipTerm());
        UserUtil.throwAccessDeniedIf(!hasPermissionToEdit(user, application));
    }

    public static void assertCanEditApplication(HoisUserDetails user, ScholarshipTerm term, Student student) {
        assertApplicationPeriod(term);
        UserUtil.throwAccessDeniedIf(!hasPermissionToEdit(user, student));
    }

    private static boolean hasPermissionToEdit(HoisUserDetails user, ScholarshipApplication application) {
        return hasPermissionToEdit(user, application.getStudent());
    }

    private static boolean hasPermissionToEdit(HoisUserDetails user, Student student) {
        return UserUtil.isStudent(user, student) || UserUtil.isStudentGroupTeacher(user, student)
                || (UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS)
                && UserUtil.isSchoolAdmin(user, student.getSchool()));
    }

    public static boolean canEditApplication(HoisUserDetails user, String applicationStatus,
            LocalDate applicationStart, LocalDate applicationEnd, Long studentGroupTeacherId) {
        if (applicationOfEditingStatus(applicationStatus) && applicationPeriodActive(applicationStart, applicationEnd)) {
            if ((user.isSchoolAdmin())) {
                return UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS);
            } else if (user.isTeacher())  {
                return user.getTeacherId().equals(studentGroupTeacherId);
            }
        }
        return false;
    }

    private static boolean isStudentGroupTeacher(HoisUserDetails user, ScholarshipApplication application) {
        return user.getTeacherId() != null
                && user.getTeacherId().equals(EntityUtil.getNullableId(application.getStudentGroup().getTeacher()));
    }

    private static boolean isInCommitte(HoisUserDetails user, Committee committee) {
        return committee != null && committee.getMembers().stream()
                .anyMatch(m -> user.getPersonId().equals(EntityUtil.getId(m.getPerson())));
    }

    public static void assertCanAnnulApplication(HoisUserDetails user) {
        if (user.isTeacher()) {
            UserUtil.assertIsTeacher(user);
        } else {
            UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS);
        }
    }

    public static void assertCanCreateApplication(HoisUserDetails user, ScholarshipTerm term, Student student) {
        assertApplicationPeriod(term);
        assertIsStudentAllowedNewApplications(student);
        assertCanEditApplication(user, term, student);
    }

    private static void assertIsStudentAllowedNewApplications(Student student) {
        if (!(StudentUtil.isActive(student) && !StudentUtil.isGuestStudent(student))) {
            throw new ValidationFailedException("stipend.messages.error.studentStatus");
        }
    }

    private static boolean applicationOfEditingStatus(ScholarshipApplication application) {
        return applicationOfEditingStatus(EntityUtil.getNullableCode(application.getStatus()));
    }

    private static boolean applicationOfEditingStatus(String applicationStatus) {
        return ScholarshipStatus.STIPTOETUS_STAATUS_K.name().equals(applicationStatus)
                || ScholarshipStatus.STIPTOETUS_STAATUS_T.name().equals(applicationStatus);
    }

    private static void assertApplicationPeriod(ScholarshipTerm term) {
        if (!applicationPeriodActive(term)) {
            throw new ValidationFailedException("stipend.messages.error.notApplicationPeriod");
        }
    }

    private static boolean applicationPeriodActive(ScholarshipTerm term) {
        return applicationPeriodActive(term.getApplicationStart(), term.getApplicationEnd());
    }

    private static boolean applicationPeriodActive(LocalDate applicationStart, LocalDate applicationEnd) {
        LocalDate now = LocalDate.now();
        return (applicationStart == null || now.isAfter(applicationStart) || now.isEqual(applicationStart))
                && (applicationEnd == null || now.isBefore(applicationEnd) || now.isEqual(applicationEnd));
    }

    public static boolean canApply(ScholarshipTerm term, ScholarshipApplication application) {
        return applicationPeriodActive(term) && (application == null || applicationOfEditingStatus(application));
    }

    public static boolean canViewStudentTermCompliance(HoisUserDetails user, Student student, ScholarshipTerm term) {
        if (!UserUtil.isSameSchool(user, term.getSchool())) {
            return false;
        }
        return UserUtil.isStudent(user, student) || UserUtil.isStudentGroupTeacher(user, student)
                || (UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_STIPTOETUS)
                    && UserUtil.isSchoolAdmin(user, student.getSchool()));
    }

}
