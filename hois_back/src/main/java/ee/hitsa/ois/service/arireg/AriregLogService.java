package ee.hitsa.ois.service.arireg;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.WsAriregLog;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hois.soap.LogContext;

@Transactional(TxType.REQUIRES_NEW)
@Service
@Profile("!test")
public class AriregLogService {
    
    @Autowired
    protected EntityManager em;
    
    /**Method for logging Arireg queries
     * 
     * @param logRecord
     * @param school
     * @param log
     */
    public void insertLog(WsAriregLog logRecord, School school, LogContext log) {
        logRecord.setSchool(school);
        logRecord.setWsName(log.getQueryName());
        logRecord.setRequest(log.getOutgoingXml() != null ? log.getOutgoingXml() : "Päringu koostamisel tekkis viga");
        logRecord.setResponse(log.getIncomingXml());
        logRecord.setHasErrors(Boolean.valueOf(log.getError() != null));
        logRecord.setLogTxt(log.getError() != null ? ExceptionUtil.getRootCause(log.getError()).toString() : null);
        em.persist(logRecord);
    }

}
