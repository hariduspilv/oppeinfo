package ee.hitsa.ois.util;

import java.util.Set;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.HigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSaveForm;
import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;

public abstract class HigherProtocolUtil {
    
    public static boolean canChange(HoisUserDetails user, Protocol protocol) {
        return user.isTeacher() && !isConfirmed(protocol) || user.isSchoolAdmin();
    }
    
    public static boolean canConfirm(HoisUserDetails user, Protocol protocol) {
        if(isConfirmed(protocol)) {
            return false;
        }
        if(user.isSchoolAdmin()) {
            return true;
        }
        if(!user.isTeacher()) {
            return false;
        }
        return protocol.getProtocolHdata().getSubjectStudyPeriod().getTeachers().stream().anyMatch(t -> Boolean.TRUE.equals(t.getIsSignatory()) && 
                EntityUtil.getId(t.getTeacher()).equals(user.getTeacherId()));
    }
    
    public static void assertCanChange(HoisUserDetails user, Protocol protocol) {
        UserUtil.assertSameSchool(user, protocol.getSchool());
        UserUtil.assertIsSchoolAdminOrTeacher(user);

        if(!canChange(user, protocol)) {
            throw new ValidationFailedException("higherProtocol.error.noRightsToChange");
        }
    }
    
    public static void assertCanConfirm(HoisUserDetails user, Protocol protocol) {
        UserUtil.assertSameSchool(user, protocol.getSchool());
        UserUtil.assertIsSchoolAdminOrTeacher(user);

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
        if(isConfirmed(protocol) && addInfoMissing(dto)) {
            throw new ValidationFailedException("higherProtocol.error.addInfoRequired");
        }
    }
    
    public static boolean isConfirmed(Protocol protocol) {
        return ClassifierUtil.equals(ProtocolStatus.PROTOKOLL_STAATUS_K, protocol.getStatus());
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
