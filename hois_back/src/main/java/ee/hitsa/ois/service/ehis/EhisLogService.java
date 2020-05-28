package ee.hitsa.ois.service.ehis;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.BaseLog;
import ee.hitsa.ois.domain.WsEhisCurriculumLog;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.WsEhisTeacherLog;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisLogCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EhisLogDto;
import ee.hois.soap.LogContext;

@Transactional(TxType.REQUIRES_NEW)
@Service
@Profile("!test")
public class EhisLogService {

    private static final String CURRICULUM_QUERY = "oisOppekava";
    private static final String CURRICULUM_STATUS_QUERY = "oisOppekavaStaatus";
    private static final String STUDENT_QUERY = "laeKorgharidus";
    private static final String TEACHER_QUERY_HIGHER = "laeOppejoud";
    private static final String TEACHER_QUERY_VOCATIONAL = "laePedagoogid";
    private static final String INNOVE_HISTORY_QUERY = "innoveAjalugu";

    @Autowired
    protected EntityManager em;

    /**
     * get EHIS log record. Only some fields are filled
     *
     * @param user
     * @param id
     * @param messageType
     * @return
     * @throws AssertionFailedException if log type is unknown
     */
    public EhisLogDto get(HoisUserDetails user, Long id, String messageType) {
        Class<? extends BaseLog> logentryClass;
        switch(messageType) {
        case CURRICULUM_QUERY:
        case CURRICULUM_STATUS_QUERY:
            logentryClass = WsEhisCurriculumLog.class;
            break;
        case STUDENT_QUERY:
        case INNOVE_HISTORY_QUERY:
            logentryClass = WsEhisStudentLog.class;
            break;
        case TEACHER_QUERY_HIGHER:
        case TEACHER_QUERY_VOCATIONAL:
            logentryClass = WsEhisTeacherLog.class;
            break;
        default:
            throw new AssertionFailedException("Unknown message type");
        }

        BaseLog logentry = em.getReference(logentryClass, id);
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, logentry.getSchool());

        EhisLogDto dto = new EhisLogDto(null, messageType, null, null, null, null, null);
        dto.setRequest(logentry.getRequest());
        dto.setResponse(logentry.getResponse());
        if(logentry instanceof WsEhisStudentLog) {
            Directive directive = ((WsEhisStudentLog) logentry).getDirective();
            if(directive != null) {
                dto.setDirective(AutocompleteResult.of(directive));
            }
        } else if(logentry instanceof WsEhisTeacherLog) {
            dto.setTeacher(AutocompleteResult.of(((WsEhisTeacherLog) logentry).getTeacher()));
        }
        return dto;
    }

    /**
     * Search EHIS log records
     *
     * @param schoolId
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<EhisLogDto> search(Long schoolId, EhisLogCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb;
        String messageType = criteria.getMessageType();
        List<String> from = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();
        if((messageType == null && criteria.getTeacher() == null) || CURRICULUM_QUERY.equals(messageType)) {
            qb = oisOppekavaQuery(schoolId, criteria);
            from.add(qb.querySql("cl.id, cast('"+CURRICULUM_QUERY+"' as text) as ws_name, cl.inserted, cl.inserted_by, cl.has_xtee_errors, cl.has_other_errors, cl.log_txt, cast(null as text) as directive_nr, cast(null as text) as name_et, cast(null as text) as name_en", false));
            parameters.putAll(qb.queryParameters());
        }
        if((messageType == null && criteria.getTeacher() == null) || CURRICULUM_STATUS_QUERY.equals(messageType)) {
            qb = oisOppekavaStaatusQuery(schoolId, criteria);
            from.add(qb.querySql("cl.id, cast('"+CURRICULUM_STATUS_QUERY+"' as text) as ws_name, cl.inserted, cl.inserted_by, cl.has_xtee_errors, cl.has_other_errors, cl.log_txt, cast(null as text) as directive_nr, cast(null as text) as name_et, cast(null as text) as name_en", false));
            parameters.putAll(qb.queryParameters());
        }
        if((messageType == null && criteria.getTeacher() == null) || STUDENT_QUERY.equals(messageType)) {
            qb = laeKorgharidusQuery(schoolId, criteria);
            from.add(qb.querySql("sl.id, cast('"+STUDENT_QUERY+"' as text) as ws_name, sl.inserted, sl.inserted_by, sl.has_xtee_errors, sl.has_other_errors, sl.log_txt, d.directive_nr, c.name_et, c.name_en", false));
            parameters.putAll(qb.queryParameters());
        }
        if(messageType == null || TEACHER_QUERY_HIGHER.equals(messageType)) {
            qb = laeOppejoudQuery(schoolId, true, criteria);
            from.add(qb.querySql("tl.id, cast('"+TEACHER_QUERY_HIGHER+"' as text) as ws_name, tl.inserted, tl.inserted_by, tl.has_xtee_errors, tl.has_other_errors, tl.log_txt, cast(null as text) as directive_nr, cast(null as text) as name_et, cast(null as text) as name_en", false));
            parameters.putAll(qb.queryParameters());
        }
        if(messageType == null || TEACHER_QUERY_VOCATIONAL.equals(messageType)) {
            qb = laeOppejoudQuery(schoolId, false, criteria);
            from.add(qb.querySql("tl.id, cast('"+TEACHER_QUERY_VOCATIONAL+"' as text) as ws_name, tl.inserted, tl.inserted_by, tl.has_xtee_errors, tl.has_other_errors, tl.log_txt, cast(null as text) as directive_nr, cast(null as text) as name_et, cast(null as text) as name_en", false));
            parameters.putAll(qb.queryParameters());
        }
        if (messageType == null || INNOVE_HISTORY_QUERY.equals(messageType)) {
            qb = innoveAjaluguQuery(schoolId, criteria);
            from.add(qb.querySql("sl.id, cast('"+INNOVE_HISTORY_QUERY+"' as text) as ws_name, sl.inserted, sl.inserted_by, sl.has_xtee_errors, sl.has_other_errors, sl.log_txt, cast(null as text) as directive_nr, cast(null as text) as name_et, cast(null as text) as name_en", false));
            parameters.putAll(qb.queryParameters());
        }

        qb = new JpaNativeQueryBuilder("from (" + String.join(" union all ", from) + ") logs").sort(pageable);
        for(Map.Entry<String, Object> me : parameters.entrySet()) {
            qb.parameter(me.getKey(), me.getValue());
        }

        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, "id, ws_name, inserted, inserted_by, has_xtee_errors, has_other_errors, log_txt, directive_nr, name_et, name_en", em, pageable);
        return messages.map(row -> new EhisLogDto(resultAsLong(row, 0), resultAsString(row, 1),
                resultAsLocalDateTime(row, 2), PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(row, 3)),
                (Boolean.TRUE.equals(resultAsBoolean(row, 4)) || Boolean.TRUE.equals(resultAsBoolean(row, 5)) ? Boolean.TRUE : Boolean.FALSE),
                resultAsString(row, 6),
                directiveText(resultAsString(row, 7), resultAsString(row, 8), resultAsString(row, 9))));
    }

    public <T extends BaseLog> T insert(LogContext logCtx, T logRecord) {
        logRecord.setWsName(logCtx.getQueryName());
        logRecord.setRequest(logCtx.getOutgoingXml() != null ? logCtx.getOutgoingXml() : "Could not generate request");
        logRecord.setResponse(logCtx.getIncomingXml());
        em.persist(logRecord);
        return logRecord;
    }

    private static JpaNativeQueryBuilder laeOppejoudQuery(Long schoolId, boolean higher, EhisLogCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ws_ehis_teacher_log tl");

        String queryNameParam = higher ? "teacherWsHigherName" : "teacherWsVocationalName";
        qb.requiredCriteria("tl.ws_name = :" + queryNameParam, queryNameParam, higher ?
                EhisTeacherExportService.LAE_OPPEJOUD_SERVICE : EhisTeacherExportService.LAE_PEDAGOOGID_SERVICE);
        qb.requiredCriteria("tl.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("tl.inserted >= :startFrom", "startFrom", command.getFrom(), d -> LocalDateTime.of(d, LocalTime.MIN));
        qb.optionalCriteria("tl.inserted <= :startThru", "startThru", command.getThru(), d -> LocalDateTime.of(d, LocalTime.MAX));
        qb.optionalCriteria("tl.teacher_id = :teacherId", "teacherId", command.getTeacher());

        if (Boolean.TRUE.equals(command.getErrors())) {
            qb.filter("(tl.has_xtee_errors = true or tl.has_other_errors = true)");
        }

        return qb;
    }

    private static JpaNativeQueryBuilder laeKorgharidusQuery(Long schoolId, EhisLogCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ws_ehis_student_log sl " +
                "left outer join directive d on sl.directive_id = d.id " +
                "left outer join classifier c on d.type_code = c.code");

        qb.requiredCriteria("sl.ws_name = :studentWsName", "studentWsName", EhisService.LAE_KORGHARIDUS_SERVICE);
        qb.requiredCriteria("sl.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("sl.inserted >= :startFrom", "startFrom", command.getFrom(), d -> LocalDateTime.of(d, LocalTime.MIN));
        qb.optionalCriteria("sl.inserted <= :startThru", "startThru", command.getThru(), d -> LocalDateTime.of(d, LocalTime.MAX));

        if (Boolean.TRUE.equals(command.getErrors())) {
            qb.filter("(sl.has_xtee_errors = true or sl.has_other_errors = true)");
        }

        return qb;
    }

    private static JpaNativeQueryBuilder oisOppekavaQuery(Long schoolId, EhisLogCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ws_ehis_curriculum_log cl");

        qb.requiredCriteria("cl.ws_name = :curriculumWsName", "curriculumWsName", EhisCurriculumService.OIS_OPPEKAVA_SERVICE);
        qb.requiredCriteria("cl.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("cl.inserted >= :startFrom", "startFrom", command.getFrom(), d -> LocalDateTime.of(d, LocalTime.MIN));
        qb.optionalCriteria("cl.inserted <= :startThru", "startThru", command.getThru(), d -> LocalDateTime.of(d, LocalTime.MAX));

        if (Boolean.TRUE.equals(command.getErrors())) {
            qb.filter("(cl.has_xtee_errors = true or cl.has_other_errors = true)");
        }

        return qb;
    }

    private static JpaNativeQueryBuilder oisOppekavaStaatusQuery(Long schoolId, EhisLogCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ws_ehis_curriculum_log cl");

        qb.requiredCriteria("cl.ws_name = :curriculumStatusWsName", "curriculumStatusWsName", EhisCurriculumService.OIS_OPPEKAVA_STAATUS_SERVICE);
        qb.requiredCriteria("cl.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("cl.inserted >= :startFrom", "startFrom", command.getFrom(), d -> LocalDateTime.of(d, LocalTime.MIN));
        qb.optionalCriteria("cl.inserted <= :startThru", "startThru", command.getThru(), d -> LocalDateTime.of(d, LocalTime.MAX));

        if (Boolean.TRUE.equals(command.getErrors())) {
            qb.filter("(cl.has_xtee_errors = true or cl.has_other_errors = true)");
        }

        return qb;
    }
    
    private static JpaNativeQueryBuilder innoveAjaluguQuery(Long schoolId, EhisLogCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ws_ehis_student_log sl");

        qb.requiredCriteria("sl.ws_name = :innoveHistoryWsName", "innoveHistoryWsName", "ehis.innoveAjalugu"); // TODO PSF variable
        qb.requiredCriteria("sl.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("sl.inserted >= :startFrom", "startFrom", command.getFrom(), d -> LocalDateTime.of(d, LocalTime.MIN));
        qb.optionalCriteria("sl.inserted <= :startThru", "startThru", command.getThru(), d -> LocalDateTime.of(d, LocalTime.MAX));

        if (Boolean.TRUE.equals(command.getErrors())) {
            qb.filter("(sl.has_xtee_errors = true or sl.has_other_errors = true)");
        }

        return qb;
    }

    private static AutocompleteResult directiveText(String directiveNr, String typeEt, String typeEn) {
        return new AutocompleteResult(null, directiveNr != null ? directiveNr + ", " + typeEt : typeEt,
                directiveNr != null ? directiveNr + ", " + typeEn : typeEn);
    }
}
