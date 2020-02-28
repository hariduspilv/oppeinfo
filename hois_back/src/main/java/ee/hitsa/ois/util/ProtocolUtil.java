package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.ProtocolStatus;

public abstract class ProtocolUtil {

    public static boolean confirmed(Protocol protocol) {
        return ClassifierUtil.equals(ProtocolStatus.PROTOKOLL_STAATUS_K, protocol.getStatus());
    }

    public static boolean confirmed(String protocolStatus) {
        return ProtocolStatus.PROTOKOLL_STAATUS_K.name().equals(protocolStatus);
    }

    public static boolean studentGradeCanBeChanged(ProtocolStudent ps) {
        return StudentUtil.isActive(ps.getStudent());
    }

    /**
     * Student cannot be deleted from the protocol, if he is exmatriculated and has some result
     */
    public static boolean studentCanBeDeleted(ProtocolStudent ps) {
        if(!StudentUtil.isActive(ps.getStudent())) {
            return !hasGrade(ps);
        }
        return true;
    }

    public static boolean hasGrade(ProtocolStudent ps) {
        return ps.getGrade() != null;
    }

    public static boolean allResultsEmpty(Protocol protocol) {
        return protocol.getProtocolStudents().stream().allMatch(ps -> !hasGrade(ps));
    }
}
