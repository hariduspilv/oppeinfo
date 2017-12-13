package ee.hitsa.ois.service.rtip;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.WsRtipLog;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisLogCommand;
import ee.hitsa.ois.web.dto.RtipLogDto;
import ee.hois.soap.LogContext;

@Transactional(TxType.REQUIRES_NEW)
@Service
public class RtipLogService {

    @Autowired
    protected EntityManager em;

    /**
     * Method for logging RTIP requests.
     *
     * @param school
     * @param log
     */
    public void insertLog(School school, LogContext log) {
        WsRtipLog logRecord = new WsRtipLog();
        logRecord.setSchool(school);
        logRecord.setWsName(log.getQueryName());
        logRecord.setRequest(log.getOutgoingXml() != null ? log.getOutgoingXml() : "Päringu koostamisel tekkis viga");
        logRecord.setResponse(log.getIncomingXml());
        logRecord.setHasErrors(Boolean.valueOf(log.getError() != null));
        logRecord.setLogTxt(log.getError() != null ? ExceptionUtil.getRootCause(log.getError()).toString() : null);
        em.persist(logRecord);
    }

    /**
     * RTIP Log search
     *
     * @param schoolId
     * @param command
     * @param pageable
     * @return
     */
    public Page<RtipLogDto> search(Long schoolId, EhisLogCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ws_rtip_log l").sort(pageable);

        qb.requiredCriteria("l.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("l.ws_name = :messageType", "messageType", command.getMessageType());
        qb.optionalCriteria("l.inserted >= :startFrom", "startFrom", command.getFrom(), d -> LocalDateTime.of(d, LocalTime.MIN));
        qb.optionalCriteria("l.inserted <= :startThru", "startThru", command.getThru(), d -> LocalDateTime.of(d, LocalTime.MAX));

        if (Boolean.TRUE.equals(command.getErrors())) {
            qb.filter("l.has_errors = true");
        }

        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, "l.id, l.ws_name, l.inserted, l.inserted_by, l.has_errors, l.log_txt", em, pageable);
        return messages.map(row -> new RtipLogDto(resultAsLong(row, 0), resultAsString(row, 1),
                resultAsLocalDateTime(row, 2), PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 3)),
                resultAsBoolean(row, 4), resultAsString(row, 5)));
    }

    /**
     * Get single RTIP log record for viewing.
     *
     * @param user
     * @param id
     * @param messageType
     * @return
     */
    public RtipLogDto get(HoisUserDetails user, Long id, String messageType) {
        WsRtipLog logentry = em.getReference(WsRtipLog.class, id);
        UserUtil.assertIsSchoolAdmin(user, logentry.getSchool());
        RtipLogDto dto = new RtipLogDto(null, messageType, null, null, null, null);
        dto.setRequest(logentry.getRequest());
        dto.setResponse(logentry.getResponse());
        return dto;
    }

}
