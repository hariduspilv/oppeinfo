package ee.hitsa.ois.mock;

import java.lang.invoke.MethodHandles;

// import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hitsa.ois.domain.BaseLog;
import ee.hitsa.ois.service.ehis.EhisLogService;
import ee.hois.soap.LogContext;

public class MockEhisLogService extends EhisLogService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public <T extends BaseLog> T insert(LogContext logContext, T logRecord) {
        // do not insert log into database
        if(logContext.getError() != null) {
            log.error("Error while testing ehis", logContext.getError());
        }
        // System.out.print(logRecord.getError());
        // Assert.assertNull(logRecord.getError());
        return logRecord;
    }
}
