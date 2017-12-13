package ee.hois.xroad.kutseregister.service;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.kutseregister.generated.KutsetunnistusVastus;

public class KutsetunnistusResponse extends LogResult<KutsetunnistusVastus> {

    public KutsetunnistusResponse(LogContext log, KutsetunnistusVastus result) {
        super(log, result);
    }
}
