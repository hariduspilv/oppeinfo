package ee.hitsa.ois.mock;

import java.lang.invoke.MethodHandles;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.WsEhisTeacherLog;
import ee.hitsa.ois.service.ehis.EhisLogService;
import ee.hois.soap.LogContext;

public class MockEhisLogService extends EhisLogService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public WsEhisStudentLog insert(LogContext logRecord, WsEhisStudentLog wsEhisStudentLog) {
        // do not insert log into database
        if(logRecord.getError() != null) {
            log.error("Error while testing ehis", logRecord.getError());
        }
        Assert.assertNull(logRecord.getError());
        return wsEhisStudentLog;
    }

    @Override
    public WsEhisTeacherLog insert(LogContext logRecord, WsEhisTeacherLog wsEhisTeacherLog) {
        // do not insert log into database
        if(logRecord.getError() != null) {
            log.error("Error while testing ehis", logRecord.getError());
        }
        Assert.assertNull(logRecord.getError());
        return wsEhisTeacherLog;
    }
}
