package ee.hitsa.ois.util;

import java.util.Set;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.HigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSaveForm;
import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;

public abstract class HigherProtocolUtil {

    private static boolean canSearch(HoisUserDetails user) {
        return (user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PROTOKOLL);
    }

    private static boolean canView(HoisUserDetails user, Protocol protocol) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_PROTOKOLL)) {
            return false;
        }
        return UserUtil.isSchoolAdmin(user, protocol.getSchool()) || UserUtil.isTeacher(user, protocol.getSchool())
                || UserUtil.isLeadingTeacher(user, protocol.getProtocolHdata().getSubjectStudyPeriod().getSubject());
    }

    private static boolean canCreate(HoisUserDetails user) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PROTOKOLL)) {
            return false;
        }
        return user.isSchoolAdmin() || user.isTeacher();
    }

    public static boolean canChange(HoisUserDetails user, Protocol protocol) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PROTOKOLL)) {
            return false;
        }
        if(UserUtil.isSchoolAdmin(user, protocol.getSchool())) {
            return true;
        }
        if(UserUtil.isTeacher(user, protocol.getSchool())) {
            return !ProtocolUtil.confirmed(protocol);
        }
        return false;
    }

    public static boolean canConfirm(HoisUserDetails user, Protocol protocol) {
        if(!UserUtil.hasPermission(user, Permission.OIGUS_K, PermissionObject.TEEMAOIGUS_PROTOKOLL)) {
            return false;
        }
        if(UserUtil.isSchoolAdmin(user, protocol.getSchool())) {
            return true;
        }
        if(UserUtil.isTeacher(user, protocol.getSchool())) {
            return protocol.getProtocolHdata().getSubjectStudyPeriod().getTeachers().stream().anyMatch(t -> Boolean.TRUE.equals(t.getIsSignatory()) && 
                    EntityUtil.getId(t.getTeacher()).equals(user.getTeacherId()));
        }
        return false;
    }

    public static void assertCanSearch(HoisUserDetails user) {
        if(!canSearch(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanView(HoisUserDetails user, Protocol protocol) {
        if(!canView(user, protocol)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanCreate(HoisUserDetails user) {
        if(!canCreate(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public static void assertCanChange(HoisUserDetails user, Protocol protocol) {
        if(!canChange(user, protocol)) {
            throw new ValidationFailedException("higherProtocol.error.noRightsToChange");
        }
    }

    public static void assertCanConfirm(HoisUserDetails user, Protocol protocol) {
        if(!canConfirm(user, protocol)) {
            throw new ValidationFailedException("higherProtocol.error.noRightsToConfirm");
        }
    }

    public static void validate(HigherProtocolSaveForm form, Protocol protocol) {
        assertProtocolStudentsNotAddedOrRemoved(protocol, form);
    }

    private static void assertProtocolStudentsNotAddedOrRemoved(Protocol protocol, HigherProtocolSaveForm form) {
        String message = "Protocol students should not be added or removed";
        Set<Long> formProtocolStudents = StreamUtil.toMappedSet(ps -> ps.getId(), form.getProtocolStudents());
        Set<Long> entityProtocolStudents = StreamUtil.toMappedSet(EntityUtil::getId, protocol.getProtocolStudents());
        AssertionFailedException.throwIf(formProtocolStudents.size() 
                != entityProtocolStudents.size(), message);
        for(Long fPm : formProtocolStudents) {
            // TODO remove duplicate code?
            AssertionFailedException.throwIf(!entityProtocolStudents.contains(fPm), message);
            if(!entityProtocolStudents.contains(fPm)) {
                throw new ValidationFailedException("higherProtocol.error.protocolStudentsAddedOrRemoved");
            }
        }
    }

    public static void assertHasAddInfoIfProtocolConfirmed(HigherProtocolStudentDto dto, Protocol protocol) {
        if(ProtocolUtil.confirmed(protocol) && addInfoMissing(dto)) {
            throw new ValidationFailedException("higherProtocol.error.addInfoRequired");
        }
    }

    private static boolean addInfoMissing(HigherProtocolStudentDto dto) {
        return dto.getAddInfo() == null || dto.getAddInfo().isEmpty();
    }

    public static void assertStudentsAdded(HigherProtocolCreateForm form) {
        if(form.getStudents() == null || form.getStudents().isEmpty()) {
            throw new ValidationFailedException("higherProtocol.error.noStudents");
        }
    }
}
