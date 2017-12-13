package ee.hitsa.ois.service.sais;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.BiFunction;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.sais.WsSaisLog;
import ee.hitsa.ois.domain.sais.WsSaisLogDetail;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.sais.SaisLogCommand;
import ee.hitsa.ois.web.dto.sais.SaisLogDto;
import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;

@Transactional(TxType.REQUIRES_NEW)
@Service
public class SaisLogService {

    @Autowired
    private EntityManager em;

    /**
     * Sais Log search
     *
     * @param schoolId
     * @param command
     * @param pageable
     * @return
     */
    public Page<SaisLogDto> search(Long schoolId, SaisLogCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ws_sais_log l").sort(pageable);

        qb.requiredCriteria("l.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("l.ws_name = :messageType", "messageType", command.getMessageType());
        qb.optionalCriteria("l.inserted >= :startFrom", "startFrom", command.getFrom(), d -> LocalDateTime.of(d, LocalTime.MIN));
        qb.optionalCriteria("l.inserted <= :startThru", "startThru", command.getThru(), d -> LocalDateTime.of(d, LocalTime.MAX));

        if (Boolean.TRUE.equals(command.getErrors())) {
            qb.filter("(l.has_xtee_errors = true or l.has_other_errors = true)");
        }

        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, "l.id, ws_name, l.inserted, l.inserted_by, has_xtee_errors, has_other_errors", em, pageable);
        return messages.map(row -> new SaisLogDto(resultAsLong(row, 0), resultAsString(row, 1),
                resultAsLocalDateTime(row, 2), PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 3)),
                (Boolean.TRUE.equals(resultAsBoolean(row, 4)) || Boolean.TRUE.equals(resultAsBoolean(row, 5)) ? Boolean.TRUE : Boolean.FALSE)));
    }

    /**
     * Get single Sais log record for viewing.
     *
     * @param user
     * @param id
     * @param messageType
     * @return
     */
    public SaisLogDto get(HoisUserDetails user, Long id, String messageType) {
        WsSaisLog logentry = em.getReference(WsSaisLog.class, id);
        UserUtil.assertIsSchoolAdmin(user, logentry.getSchool());
        SaisLogDto dto = new SaisLogDto(null, messageType, null, null, null);

        dto.setRequest(logentry.getRequest());
        dto.setResponse(logentry.getResponse());
        return dto;
   }

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
