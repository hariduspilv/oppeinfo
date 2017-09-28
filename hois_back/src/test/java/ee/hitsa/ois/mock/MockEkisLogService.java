package ee.hitsa.ois.mock;

import java.lang.invoke.MethodHandles;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.ekis.EkisLogService;
import ee.hois.soap.LogContext;

public class MockEkisLogService extends EkisLogService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void insertLog(School school, LogContext logRecord) {
        // do not insert log into database
        if(logRecord.getError() != null) {
            log.error("Error while testing ekis", logRecord.getError());
        }
        Assert.assertNull(logRecord.getError());
    }
}
