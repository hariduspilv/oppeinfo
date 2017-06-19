package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.sais.WsSaisLog;
import ee.hitsa.ois.domain.sais.WsSaisLogDetail;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hois.xroad.helpers.XRoadResponse;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SaisLogService {

    @Autowired
    private EntityManager em;
    
    public void insertLog(XRoadResponse response, HoisUserDetails user, String logTxt) {
        WsSaisLog newLog = new WsSaisLog();
        
        if(logTxt != null) {
            newLog.setSchool(em.getReference(School.class, user.getSchoolId()));
        }
        newLog.setWsName(response.getQueryName());
        newLog.setRequest(response.getXmlQuery());
        newLog.setResponse(response.getXmlResponse());
        newLog.setProcessQueryStart(response.getQueryStart());
        newLog.setProcessQueryEnd(response.getQueryEnd());
        newLog.setHasXteeErrors(response.getxRoadErrors() != null ? response.getxRoadErrors() : Boolean.FALSE);
        newLog.setHasOtherErrors(response.getProcessingErrors() != null ? response.getProcessingErrors() : Boolean.FALSE);
        newLog.setRecordCount(response.getRecordCount());
        newLog.setFirstWsSaisLog(null);

        em.persist(newLog);

        WsSaisLogDetail newDetail = new WsSaisLogDetail();
        boolean errors = Boolean.TRUE.equals(response.getProcessingErrors());
        errors = errors || Boolean.TRUE.equals(response.getxRoadErrors());

        newDetail.setIsError(Boolean.valueOf(errors));
        newDetail.setLogTxt(logTxt != null ? logTxt : (response.getError() != null ? response.getError() : "Import edukas"));
        newDetail.setWsSaisLog(newLog);

        em.persist(newDetail);
    }
}