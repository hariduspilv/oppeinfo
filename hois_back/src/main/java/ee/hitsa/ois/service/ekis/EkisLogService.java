package ee.hitsa.ois.service.ekis;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.WsEkisLog;
import ee.hitsa.ois.domain.school.School;
import ee.hois.soap.LogContext;

@Transactional
@Service
public class EkisLogService {

    @Autowired
    protected EntityManager em;

    public void insertLog(School school, LogContext log) {
        WsEkisLog logRecord = new WsEkisLog();
        logRecord.setSchool(school);
        logRecord.setWsName(log.getQueryName());
        logRecord.setRequest(log.getOutgoingXml() != null ? log.getOutgoingXml() : "Päringu koostamisel tekkis viga");
        logRecord.setResponse(log.getIncomingXml());
        logRecord.setHasErrors(Boolean.valueOf(log.getError() != null));
        logRecord.setLogTxt(log.getError() != null ? Arrays.toString(log.getError().getStackTrace()) : null);
        em.persist(logRecord);
    }
}
