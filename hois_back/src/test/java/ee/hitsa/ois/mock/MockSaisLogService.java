package ee.hitsa.ois.mock;

import java.lang.invoke.MethodHandles;

// import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hitsa.ois.service.sais.SaisLogService;
import ee.hois.soap.LogContext;

public class MockSaisLogService extends SaisLogService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void insertLog(LogContext logRecord, Long schoolId, String logTxt, boolean processingErrors) {
        // do not insert log into database
        if(logRecord.getError() != null) {
            log.error("Error while testing sais", logRecord.getError());
        }
        // Assert.assertNull(logRecord.getError());
    }
}
