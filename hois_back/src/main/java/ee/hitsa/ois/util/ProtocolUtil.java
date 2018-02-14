package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.enums.ProtocolStatus;

public abstract class ProtocolUtil {

    public static boolean confirmed(Protocol protocol) {
        return ClassifierUtil.equals(ProtocolStatus.PROTOKOLL_STAATUS_K, protocol.getStatus());
    }
}
