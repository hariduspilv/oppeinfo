package ee.hois.xroad.rtip.service;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.rtip.generated.ZEMPLOEESResponseType;

public class ZemploeesResult extends LogResult<ZEMPLOEESResponseType> {

    public ZemploeesResult(LogContext log, ZEMPLOEESResponseType result) {
        super(log, result);
    }
}
