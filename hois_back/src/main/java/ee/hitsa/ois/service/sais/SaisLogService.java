package ee.hitsa.ois.service.sais;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.sais.WsSaisLog;
import ee.hitsa.ois.domain.sais.WsSaisLogDetail;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.sais.SaisLogCommand;
import ee.hitsa.ois.web.dto.sais.SaisLogDto;
import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;

@Transactional(TxType.REQUIRES_NEW)
@Service
@Profile("!test")
public class SaisLogService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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

        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, "l.id, l.ws_name, l.inserted, l.inserted_by, l.has_xtee_errors, l.has_other_errors", em, pageable);
        Page<SaisLogDto> result = messages.map(row -> new SaisLogDto(resultAsLong(row, 0), resultAsString(row, 1),
                resultAsLocalDateTime(row, 2), PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 3)),
                (Boolean.TRUE.equals(resultAsBoolean(row, 4)) || Boolean.TRUE.equals(resultAsBoolean(row, 5)) ? Boolean.TRUE : Boolean.FALSE)));

        Set<Long> logIds = StreamUtil.toMappedSet(SaisLogDto::getId, result.getContent());
        if(!logIds.isEmpty()) {
            List<?> data = em.createNativeQuery("select d.ws_sais_log_id, d.log_txt from ws_sais_log_detail d where d.is_error = true and d.ws_sais_log_id in ?1")
                    .setParameter(1, logIds)
                    .getResultList();
            Map<Long, List<String>> msgs = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsString(r, 1), Collectors.toList())));
            for(SaisLogDto r : result.getContent()) {
                r.setLogTxt(String.join(", ", msgs.getOrDefault(r.getId(), Collections.emptyList())));
            }
        }
        return result;
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
        newLog.setHasXteeErrors(Boolean.valueOf(response.getError() != null && !processingErrors));
        newLog.setHasOtherErrors(Boolean.valueOf(processingErrors));
        newLog.setRecordCount(response.getRecordCount());
        newLog.setFirstWsSaisLog(null);

        em.persist(newLog);

        WsSaisLogDetail newDetail = new WsSaisLogDetail();
        boolean errors = processingErrors || Boolean.TRUE.equals(newLog.getHasXteeErrors());

        newDetail.setIsError(Boolean.valueOf(errors));
        newDetail.setLogTxt(logTxt != null ? logTxt : (response.getError() != null ? ExceptionUtil.getRootCause(response.getError()).toString() : "Import edukas"));
        newDetail.setWsSaisLog(newLog);

        em.persist(newDetail);
    }

    <T> void withResponse(LogResult<T> result, Long schoolId, BiFunction<T, LogContext, String> handler) {
        LogContext log = result.getLog();
        String logTxt = null;
        boolean processingErrors = false;
        try {
            if(!result.hasError()) {
                logTxt = handler.apply(result.getResult(), log);
            }
        } catch (Exception e) {
            log.setError(e);
            processingErrors = true;
            LOG.error("Error while handling SAIS response :", e);
        } finally {
            insertLog(log, schoolId, logTxt, processingErrors);
        }
    }
}
