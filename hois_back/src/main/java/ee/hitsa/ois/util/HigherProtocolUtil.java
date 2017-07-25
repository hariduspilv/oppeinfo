package ee.hitsa.ois.util;

import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class HigherProtocolUtil {
    
    public static Boolean canChange(HoisUserDetails user, Protocol protocol) {
        return user.isTeacher() && ProtocolStatus.PROTOKOLL_STAATUS_S.name()
                .equals(EntityUtil.getCode(protocol.getStatus())) || user.isSchoolAdmin();
    }
    
    public static Boolean canConfirm(HoisUserDetails user, Protocol protocol) {
        if(!ProtocolStatus.PROTOKOLL_STAATUS_S.name().equals(EntityUtil.getCode(protocol.getStatus()))) {
            return false;
        }
        if(user.isSchoolAdmin()) {
            return true;
        }
        Set<Long> teachersWhoCanConfirm = protocol.getProtocolHdata().getSubjectStudyPeriod().getTeachers().stream()
                .filter(t -> t.getIsSignatory()).map(t -> t.getTeacher().getPerson().getId()).collect(Collectors.toSet());
        return user.isTeacher() && teachersWhoCanConfirm.contains(user.getPersonId());
    }
    
    public static void assertCanChange(HoisUserDetails user, Protocol protocol) {
        UserUtil.assertSameSchool(user, protocol.getSchool());
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        AssertionFailedException.throwIf(!canChange(user, protocol), "You cannot change protocol!");
    }
    
    public static void assertCanConfirm(HoisUserDetails user, Protocol protocol) {
        UserUtil.assertSameSchool(user, protocol.getSchool());
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        AssertionFailedException.throwIf(!canConfirm(user, protocol), "You cannot confirm protocol!");
    }
}
