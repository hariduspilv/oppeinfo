package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.sais.WsSaisLog;
import ee.hitsa.ois.domain.sais.WsSaisLogDetail;
import ee.hitsa.ois.repository.SchoolRepository;
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
    EntityManager em;
    @Autowired
    SchoolRepository schoolRepo;
    
    public void insertLog(XRoadResponse response, HoisUserDetails user) {
        
       /* private School school;
        private String wsName;
        private String request;
        private String response;
        private Boolean hasXteeErrors;
        private Boolean hasOtherErrors;
        private Integer recordCount;
        private WsSaisLog firstWsSaisLogId;*/
        
        WsSaisLog newLog = new WsSaisLog();
        
        newLog.setSchool(schoolRepo.getOne(user.getSchoolId()));
        newLog.setWsName(response.getQueryName());
        newLog.setRequest(response.getXmlQuery());
        newLog.setResponse(response.getXmlResponse());
        newLog.setProcessQueryStart(response.getQueryStart());
        newLog.setProcessQueryEnd(response.getQueryEnd());
        newLog.setHasXteeErrors(response.getxRoadErrors() != null ? response.getxRoadErrors() : false);
        newLog.setHasOtherErrors(response.getProcessingErrors() != null ? response.getProcessingErrors() : false);
        newLog.setRecordCount(response.getRecordCount());
        newLog.setFirstWsSaisLog(null);
        
        em.persist(newLog);
        
        
        WsSaisLogDetail newDetail = new WsSaisLogDetail();
        boolean errors = false;
        if(response.getProcessingErrors() != null) {
            errors = response.getProcessingErrors();
        } 
        if(response.getxRoadErrors() != null && !errors) {
            errors = response.getxRoadErrors();
        }
        newDetail.setIsError(errors);
        newDetail.setLogTxt(response.getError() != null ? response.getError() : "Import edukas");
        newDetail.setWsSaisLog(newLog);
        
        em.persist(newDetail);
        
    }
    
}
