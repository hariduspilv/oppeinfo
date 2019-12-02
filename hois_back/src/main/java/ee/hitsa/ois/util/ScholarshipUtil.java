package ee.hitsa.ois.util;

import java.time.LocalDate;

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

    public static void assertCanViewApplication(HoisUserDetails user, ScholarshipApplication application) {
        if (user.isStudent()) {
            UserUtil.throwAccessDeniedIf(!user.getStudentId().equals(EntityUtil.getId(application.getStudent())),
                    "User student does not match application student");
        } else if (user.isSchoolAdmin()) {
            UserUtil.throwAccessDeniedIf(
                    !user.getSchoolId().equals(EntityUtil.getId(application.getScholarshipTerm().getSchool())),
                    "User school does not match application scholarship term school");
        } else if (user.isLeadingTeacher()) {
            Committee committee = application.getScholarshipTerm().getCommittee();
            Long applicationCurriculumId = EntityUtil.getId(application.getCurriculumVersion().getCurriculum());
            UserUtil.throwAccessDeniedIf(
                    !(UserUtil.isLeadingTeacher(user, applicationCurriculumId) || isInCommitte(user, committee)),
                    "Student is not in leading teacher's curriculum or part of committee");
        } else if (user.isTeacher()) {
            Committee committee = application.getScholarshipTerm().getCommittee();
            UserUtil.throwAccessDeniedIf(!(isStudentGroupTeacher(user, application) || isInCommitte(user, committee)),
                    "User teacher does not match application student group teacher or is not part of committee");
        } else {
            throw new AccessDeniedException(
                    "User is not application student or school admin or application student group teacher or part of committee");
        }
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

    public static void assertCanCreateApplication(ScholarshipTerm term) {
        AssertionFailedException.throwIf(!applicationPeriodActive(term), "Application period is not active");
    }

    public static void assertCanEditApplication(HoisUserDetails user, ScholarshipApplication application) {
        AssertionFailedException.throwIf(
                user.getStudentId().longValue() != EntityUtil.getId(application.getStudent()).longValue(),
                "Invalid student");
        AssertionFailedException.throwIf(!applicationOfEditingStatus(application), "Invalid application status");
        AssertionFailedException.throwIf(!applicationPeriodActive(application.getScholarshipTerm()),
                "Application period is not active");
    }

    private static boolean applicationOfEditingStatus(ScholarshipApplication application) {
        String applicationStatus = EntityUtil.getNullableCode(application.getStatus());
        return ScholarshipStatus.STIPTOETUS_STAATUS_K.name().equals(applicationStatus)
                || ScholarshipStatus.STIPTOETUS_STAATUS_T.name().equals(applicationStatus);
    }

    public static boolean applicationPeriodActive(ScholarshipTerm term) {
        LocalDate now = LocalDate.now();
        LocalDate applicationStart = term.getApplicationStart();
        LocalDate applicationEnd = term.getApplicationEnd();

        return (applicationStart == null || now.isAfter(applicationStart) || now.isEqual(applicationStart))
                && (applicationEnd == null || now.isBefore(applicationEnd) || now.isEqual(applicationEnd));
    }

    public static boolean canApply(ScholarshipTerm term, ScholarshipApplication application) {
        return applicationPeriodActive(term) && (application == null || applicationOfEditingStatus(application));
    }

}
