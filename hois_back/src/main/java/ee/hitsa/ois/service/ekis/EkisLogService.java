package ee.hitsa.ois.service.ekis;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.WsEkisLog;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisLogCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EkisLogDto;
import ee.hois.soap.LogContext;

@Transactional
@Service
public class EkisLogService {

    @Autowired
    protected EntityManager em;

    public void insertLog(WsEkisLog logRecord, School school, LogContext log) {
        logRecord.setSchool(school);
        logRecord.setWsName(log.getQueryName());
        logRecord.setRequest(log.getOutgoingXml() != null ? log.getOutgoingXml() : "Päringu koostamisel tekkis viga");
        logRecord.setResponse(log.getIncomingXml());
        logRecord.setHasErrors(Boolean.valueOf(log.getError() != null));
        logRecord.setLogTxt(log.getError() != null ? log.getError().toString() : null);
        em.persist(logRecord);
    }

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

    public EkisLogDto get(HoisUserDetails user, Long id, String messageType) {
        WsEkisLog logentry = em.getReference(WsEkisLog.class, id);
        UserUtil.assertIsSchoolAdmin(user, logentry.getSchool());
        EkisLogDto dto = new EkisLogDto(null, messageType, null, null, null, null, null);
        dto.setRequest(logentry.getRequest());
        dto.setResponse(logentry.getResponse());
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
}
