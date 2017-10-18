package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.service.security.HoisUserDetails;

public abstract class ModuleProtocolUtil {
    
    
    public static boolean canEdit(HoisUserDetails user, Protocol protocol) {
        if(UserUtil.isSchoolAdmin(user, protocol.getSchool())) {
            return true;
        }
        if(user.isTeacher()) {
            return !ProtocolUtil.confirmed(protocol) && isTeacherResponsible(user, protocol);
        }
        return false;
    }
    
    public static boolean canDelete(HoisUserDetails user, Protocol protocol) {
        return !ProtocolUtil.confirmed(protocol) && allResultsEmpty(protocol) && 
                (user.isSchoolAdmin() || isTeacherResponsible(user, protocol));
    }
    
    private static boolean isTeacherResponsible(HoisUserDetails user, Protocol protocol) {
        return UserUtil.isTeacher(user, protocol.getSchool()) && 
                EntityUtil.getId(protocol.getProtocolVdata().getTeacher()).equals(user.getTeacherId());
    }
    
    private static boolean allResultsEmpty(Protocol protocol) {
        return protocol.getProtocolStudents().stream().allMatch(ps -> ps.getGrade() == null);
    }
}
