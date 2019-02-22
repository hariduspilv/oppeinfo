package ee.hois.xroad.ariregister.service;

import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.xroad.ariregister.generated.LihtandmedV1Response;

public class LihtandmedResponse extends LogResult<LihtandmedV1Response> {
	
	public LihtandmedResponse(LogContext log, LihtandmedV1Response result) {
        super(log, result);
    }
}
