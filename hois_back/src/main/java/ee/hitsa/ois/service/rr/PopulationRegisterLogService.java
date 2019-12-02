package ee.hitsa.ois.service.rr;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.rr.WsRrChangeLog;
import ee.hitsa.ois.domain.rr.WsRrChangeLogSchool;
import ee.hitsa.ois.domain.rr.WsRrLog;
import ee.hitsa.ois.domain.rr.WsRrLogSchool;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.rr.PopulationRegisterChangesSearchDto;
import ee.hitsa.ois.web.dto.rr.PopulationRegisterSearchDto;
import ee.hitsa.ois.web.dto.rr.WsRrChangeLogDto;
import ee.hitsa.ois.web.dto.rr.WsRrLogDto;
import ee.hois.soap.LogContext;

@Transactional(TxType.REQUIRES_NEW)
@Service
@Profile("!test")
public class PopulationRegisterLogService {
    
    @Autowired
    private EntityManager em;

    public Page<WsRrChangeLogDto> searchChanges(HoisUserDetails user, PopulationRegisterChangesSearchDto cmd, Pageable pageable) {
        StringBuilder from = new StringBuilder("from ws_rr_change_log_school wrcls ");
        from.append("join ws_rr_change_log wrcl on wrcl.id = wrcls.ws_rr_change_log_id ");
        from.append("join student s on s.id = wrcls.student_id ");
        from.append("join person p on p.id = s.person_id ");
        from.append("left join student_group sg on sg.id = s.student_group_id ");
        StringBuilder select = new StringBuilder("s.id as sid, (p.firstname || ' ' || p.lastname) as fullname, sg.id as sgid, sg.code, ");
        select.append("wrcl.inserted, wrcl.inserted_by, (wrcl.new_firstname || ' ' || wrcl.new_lastname) as new_fullname, wrcl.new_address, ");
        select.append("(wrcl.old_firstname || ' ' || wrcl.old_lastname) as old_fullname, wrcl.old_address, s.type_code as studentType");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort(pageable);
        qb.requiredCriteria("wrcls.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("exists (select c.id from curriculum_version cv join curriculum c on c.id = cv.curriculum_id"
                    + " where s.curriculum_version_id = cv.id and c.id in (:userCurriculumIds))", 
                    "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria("s.id = :studentId", "studentId", cmd.getStudent() != null ? cmd.getStudent().getId() : null);
        qb.optionalCriteria("sg.id = :groupId", "groupId", cmd.getGroup() != null ? cmd.getGroup().getId() : null);
        qb.optionalCriteria("wrcl.inserted >= :validFrom", "validFrom", cmd.getFrom());
        qb.optionalCriteria("wrcl.inserted < :validThru", "validThru", cmd.getThru() != null ? cmd.getThru().plusDays(1) : null);
        return JpaQueryUtil.pagingResult(qb, select.toString(), em, pageable).map(r -> {
            WsRrChangeLogDto dto = new WsRrChangeLogDto();
            String studentName = PersonUtil.fullnameOptionalGuest(resultAsString(r, 1), resultAsString(r, 10));
            dto.setStudent(new AutocompleteResult(resultAsLong(r, 0), studentName, studentName));
            Long groupId = resultAsLong(r, 2);
            if (groupId != null) {
                dto.setGroup(new AutocompleteResult(groupId, resultAsString(r, 3), resultAsString(r, 3)));
            }
            dto.setChanged(resultAsLocalDateTime(r, 4));
            dto.setChangedBy(resultAsString(r, 5));
            dto.setNewName(resultAsString(r, 6));
            dto.setNewAddress(resultAsString(r, 7));
            dto.setOldName(resultAsString(r, 8));
            dto.setOldAddress(resultAsString(r, 9));
            dto.setSameName(Boolean.valueOf(StringUtils.equalsIgnoreCase(dto.getOldName(), dto.getNewName())));
            dto.setSameAddress(Boolean.valueOf(StringUtils.equalsIgnoreCase(dto.getOldAddress(), dto.getNewAddress())));
            return dto;
        });
    }
    
    public Page<WsRrLogDto> search(HoisUserDetails user, PopulationRegisterSearchDto cmd, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from ws_rr_log_school wrls "
                + "join ws_rr_log wrl on wrl.id = wrls.ws_rr_log_id").sort(pageable);
        qb.requiredCriteria("wrls.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (Boolean.TRUE.equals(cmd.getErrors())) {
            qb.filter("wrl.has_errors");
        }
        qb.optionalCriteria("wrl.idcode = :idcode", "idcode", cmd.getIdcode());
        qb.optionalCriteria("wrl.inserted >= :validFrom", "validFrom", cmd.getFrom());
        qb.optionalCriteria("wrl.inserted < :validThru", "validThru", cmd.getThru() != null ? cmd.getThru().plusDays(1) : null);
        return JpaQueryUtil.pagingResult(qb, "wrls.id, wrl.ws_name, wrl.log_txt, wrl.has_errors, wrl.inserted, wrl.inserted_by", em, pageable).map(r -> {
            WsRrLogDto dto = new WsRrLogDto();
            dto.setId(resultAsLong(r, 0));
            dto.setWsName(resultAsString(r, 1));
            dto.setLogTxt(resultAsString(r, 2));
            dto.setError(resultAsBoolean(r, 3));
            dto.setInserted(resultAsLocalDateTime(r, 4));
            dto.setInsertedBy(resultAsString(r, 5));
            return dto;
        });
    }
    
    public WsRrLogDto get(WsRrLogSchool log) {
        WsRrLogDto dto = new WsRrLogDto();
        EntityUtil.bindToDto(log.getWsRrLog(), dto);
        return dto;
    }

    public Boolean hasRecentChangeLogs(HoisUserDetails user) {
        return Boolean.valueOf(!em.createNativeQuery("select wrcls.id from ws_rr_change_log_school wrcls where wrcls.inserted >= ?1\\:\\:timestamp and wrcls.school_id = ?2 limit 1")
                .setParameter(1, LocalDateTime.now().minusDays(14).toString()).setParameter(2, user.getSchoolId()).getResultList().isEmpty());
    }

    public void insertLog(LogContext log, Person person, Set<School> schools) {
        WsRrLog wsRrLog = new WsRrLog();
        wsRrLog.setIdcode(person.getIdcode());
        wsRrLog.setWsName(log.getQueryName());
        wsRrLog.setRequest(log.getOutgoingXml() != null ? log.getOutgoingXml() : "i18n:EmptyXml");
        wsRrLog.setResponse(log.getIncomingXml());
        wsRrLog.setHasErrors(Boolean.valueOf(log.getError() != null));
        wsRrLog.setLogTxt(log.getError() != null ? ExceptionUtil.getRootCause(log.getError()).getMessage() : null);
        em.persist(wsRrLog);
        
        schools.forEach(school -> {
            WsRrLogSchool logSchool = new WsRrLogSchool();
            logSchool.setSchool(school);
            logSchool.setWsRrLog(wsRrLog);
            em.persist(logSchool);
        });
    }
    
    public void insertChangeLog(WsRrChangeLog log, Set<Student> students) {
        assert log.getId() != null : "You cannot update WsRrChangeLog object";
        em.persist(log);
        
        students.forEach(student -> {
            WsRrChangeLogSchool logSchool = new WsRrChangeLogSchool();
            logSchool.setStudent(student);
            logSchool.setSchool(student.getSchool());
            logSchool.setWsRrChangeLog(log);
            em.persist(logSchool);
        });
    }

    public void savePersonData(Person person, String firstname, String lastname, Classifier citizenship, Classifier residence,
            String address, String addressAds, String addressAdsOid, String postcode) {
        if (!StringUtils.equalsIgnoreCase(person.getFirstname(), firstname)) {
            person.setFirstname(PersonUtil.initCapName(firstname));
        }
        if (!StringUtils.equalsIgnoreCase(person.getLastname(), lastname)) {
            person.setLastname(PersonUtil.initCapName(lastname));
        }
        person.setCitizenship(citizenship);
        person.setResidenceCountry(residence);
        person.setAddress(address);
        //person.setAddressAds(elukoht == null? null : elukoht.getElukohtKoodaadress()); XXX
        person.setAddressAdsOid(addressAdsOid);
        person.setPostcode(postcode);
        em.merge(person);
    }
}
