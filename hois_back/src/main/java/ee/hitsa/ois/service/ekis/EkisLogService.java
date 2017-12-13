package ee.hitsa.ois.service.ekis;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.WsEkisLog;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisLogCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EkisLogDto;
import ee.hois.soap.LogContext;
import ee.hois.soap.ekis.service.generated.EnforceContract;
import ee.hois.soap.ekis.service.generated.EnforceDirective;
import ee.hois.soap.ekis.service.generated.RejectDirective;

@Transactional(TxType.REQUIRES_NEW)
@Service
public class EkisLogService {

    @Autowired
    protected EntityManager em;

    /**
     * Method for logging EKIS requests.
     *
     * @param logRecord
     * @param school
     * @param log
     */
    public void insertLog(WsEkisLog logRecord, School school, LogContext log) {
        logRecord.setSchool(school);
        logRecord.setWsName(log.getQueryName());
        logRecord.setRequest(log.getOutgoingXml() != null ? log.getOutgoingXml() : "Päringu koostamisel tekkis viga");
        logRecord.setResponse(log.getIncomingXml());
        logRecord.setHasErrors(Boolean.valueOf(log.getError() != null));
        logRecord.setLogTxt(log.getError() != null ? ExceptionUtil.getRootCause(log.getError()).toString() : null);
        em.persist(logRecord);
    }

    /**
     * Method for logging incoming EKIS requests.
     *
     * @param logRecord
     * @param school
     * @param log
     */
    public void insertLog(LogContext log, Object request) {
        WsEkisLog logRecord = new WsEkisLog();
        School school = null;
        if(request instanceof EnforceContract) {
            logRecord.setContract(em.find(Contract.class, Long.valueOf(((EnforceContract)request).getOisContractId())));
            if(logRecord.getContract() != null) {
                school = logRecord.getContract().getStudent().getSchool();
            }
        } else if(request instanceof EnforceDirective) {
            logRecord.setDirective(em.find(Directive.class, Long.valueOf(((EnforceDirective)request).getOisDirectiveId())));
            if(logRecord.getDirective() != null) {
                school = logRecord.getDirective().getSchool();
            }
        } else if(request instanceof RejectDirective) {
            logRecord.setDirective(em.find(Directive.class, Long.valueOf(((RejectDirective)request).getOisDirectiveId())));
            if(logRecord.getDirective() != null) {
                school = logRecord.getDirective().getSchool();
            }
        }
        insertLog(logRecord, school, log);
    }

    /**
     * EKIS Log search
     *
     * @param schoolId
     * @param command
     * @param pageable
     * @return
     */
    public Page<EkisLogDto> search(Long schoolId, EhisLogCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ws_ekis_log l left outer join directive d on l.directive_id = d.id").sort(pageable);

        qb.requiredCriteria("l.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("l.ws_name = :messageType", "messageType", command.getMessageType());
        qb.optionalCriteria("l.inserted >= :startFrom", "startFrom", command.getFrom(), d -> LocalDateTime.of(d, LocalTime.MIN));
        qb.optionalCriteria("l.inserted <= :startThru", "startThru", command.getThru(), d -> LocalDateTime.of(d, LocalTime.MAX));

        if (Boolean.TRUE.equals(command.getErrors())) {
            qb.filter("l.has_errors = true");
        }

        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, "l.id, ws_name, l.inserted, l.inserted_by, has_errors, log_txt, directive_id, d.headline", em, pageable);
        return messages.map(row -> new EkisLogDto(resultAsLong(row, 0), resultAsString(row, 1),
                resultAsLocalDateTime(row, 2), PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 3)),
                resultAsBoolean(row, 4), resultAsString(row, 5), new AutocompleteResult(resultAsLong(row, 6), resultAsString(row, 7), null)));
    }

    /**
     * Get single EKIS log record for viewing.
     *
     * @param user
     * @param id
     * @param messageType
     * @return
     */
    public EkisLogDto get(HoisUserDetails user, Long id, String messageType) {
        WsEkisLog logentry = em.getReference(WsEkisLog.class, id);
        UserUtil.assertIsSchoolAdmin(user, logentry.getSchool());
        EkisLogDto dto = new EkisLogDto(null, messageType, null, null, null, null, null);

        if(INCOMING_REQUESTS.contains(logentry.getWsName())) {
            dto.setRequest(logentry.getResponse());
            dto.setResponse(logentry.getRequest());
        } else {
            dto.setRequest(logentry.getRequest());
            dto.setResponse(logentry.getResponse());
        }

        if(logentry.getDirective() != null) {
            dto.setDirective(AutocompleteResult.of(logentry.getDirective()));
        } else if(logentry.getCertificate() != null) {
            Certificate certificate = logentry.getCertificate();
            // FIXME meaningful text
            dto.setCertificate(new AutocompleteResult(certificate.getId(), certificate.getHeadline(), null));
        } else if(logentry.getContract() != null) {
            Contract contract = logentry.getContract();
            // FIXME meaningful text
            dto.setContract(new AutocompleteResult(contract.getId(), contract.getContractNr(), null));
        }
        return dto;
    }

    private static final Set<String> INCOMING_REQUESTS = new HashSet<>(Arrays.asList("enforceDirective", "rejectDirective", "enforceContract"));
}
