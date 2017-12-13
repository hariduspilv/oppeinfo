package ee.hois.xroad.rtip.service;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.rtip.generated.TootajaPohiandmedResponseType;

public class TootajaPohiandmedResult extends LogResult<TootajaPohiandmedResponseType> {

    public TootajaPohiandmedResult(LogContext log, TootajaPohiandmedResponseType result) {
        super(log, result);
    }
}
