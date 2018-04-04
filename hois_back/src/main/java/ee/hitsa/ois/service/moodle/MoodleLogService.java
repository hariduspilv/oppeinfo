package ee.hitsa.ois.service.moodle;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.WsMoodleLog;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hois.moodle.LogContext;

@Transactional(TxType.REQUIRES_NEW)
@Service
public class MoodleLogService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;

    public WsMoodleLog insertLog(MoodleContext context, LogContext log) {
        WsMoodleLog logRecord = new WsMoodleLog();
        logRecord.setSchool(em.getReference(School.class, context.getUser().getSchoolId()));
        logRecord.setWsName(log.getQueryName());
        logRecord.setRequest(log.getRequest());
        logRecord.setResponse(log.getResponse());
        logRecord.setHasErrors(Boolean.valueOf(log.getError() != null));
        logRecord.setLogTxt(log.getError() != null ? ExceptionUtil.getRootCause(log.getError()).toString() : null);
        logRecord.setIpAddress(context.getIpAddress());
        logRecord.setJournalId(context.getJournalId());
        logRecord.setSubjectStudyPeriodId(context.getSubjectStudyPeriodId());
        logRecord.setRole(classifierRepository.getOne(context.getUser().getRole()));
        em.persist(logRecord);
        return logRecord;
    }
    
}
