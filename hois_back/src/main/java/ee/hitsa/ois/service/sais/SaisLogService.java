package ee.hitsa.ois.service.sais;

import ee.hitsa.ois.domain.sais.WsSaisLog;
import ee.hitsa.ois.domain.sais.WsSaisLogDetail;
import ee.hitsa.ois.domain.school.School;
import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;

import java.util.Arrays;
import java.util.function.BiFunction;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SaisLogService {

    @Autowired
    private EntityManager em;

    public void insertLog(LogContext response, Long schoolId, String logTxt, boolean processingErrors) {
        WsSaisLog newLog = new WsSaisLog();
        if(schoolId != null) {
            newLog.setSchool(em.getReference(School.class, schoolId));
        }
        newLog.setWsName(response.getQueryName());
        newLog.setRequest(response.getOutgoingXml() != null ? response.getOutgoingXml() : "Päringu koostamisel tekkis viga");
        newLog.setResponse(response.getIncomingXml());
        newLog.setProcessQueryStart(response.getQueryStart());
        newLog.setProcessQueryEnd(response.getQueryEnd());
        newLog.setHasXteeErrors(Boolean.valueOf(response.getError() != null));
        newLog.setHasOtherErrors(Boolean.valueOf(processingErrors));
        newLog.setRecordCount(response.getRecordCount());
        newLog.setFirstWsSaisLog(null);

        em.persist(newLog);

        WsSaisLogDetail newDetail = new WsSaisLogDetail();
        boolean errors = processingErrors || Boolean.TRUE.equals(newLog.getHasXteeErrors());

        newDetail.setIsError(Boolean.valueOf(errors));
        newDetail.setLogTxt(logTxt != null ? logTxt : (response.getError() != null ? Arrays.toString(response.getError().getStackTrace()) : "Import edukas"));
        newDetail.setWsSaisLog(newLog);

        em.persist(newDetail);
    }

    <T> void withResponse(LogResult<T> result, Long schoolId, BiFunction<T, LogContext, String> handler) {
        LogContext log = result.getLog();
        if(result.hasError()) {
            insertLog(log, schoolId, null, false);
            return;
        }
        String logTxt = null;
        try {
            logTxt = handler.apply(result.getResult(), log);
        } catch (Exception e) {
            log.setError(e);
        }
        insertLog(log, schoolId, logTxt, true);
    }
}
