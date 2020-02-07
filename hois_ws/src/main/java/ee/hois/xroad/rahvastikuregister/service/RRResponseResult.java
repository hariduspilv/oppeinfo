package ee.hois.xroad.rahvastikuregister.service;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.rahvastikuregister.generated.RR434Response;

public class RRResponseResult extends LogResult<RR434Response> {

    public RRResponseResult(LogContext log, RR434Response result) {
        super(log, result);
    }
}
