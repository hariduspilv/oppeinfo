package ee.hitsa.ois.service.ehis;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentSupportService;
import ee.hitsa.ois.enums.SupportServiceValidity;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hois.xroad.ehis.generated.InnoveAjalugu;
import ee.hois.xroad.ehis.generated.InnoveAjaluguResponse;
import ee.hois.xroad.ehis.service.EhisInnoveAjaluguResponse;

@Service
public class EhisInnoveService extends EhisService {
    
    @Autowired
    private EhisLogService logService;

    @Transactional(TxType.REQUIRES_NEW)
    public WsEhisStudentLog innoveHistory(Student student) {
        WsEhisStudentLog studentLog;
        try {
            InnoveAjalugu innoveAjalugu = new InnoveAjalugu();
            if (student.getPerson() == null || student.getPerson().getIdcode() == null) {
                throw new HoisException("i18n puudub isikukood");
            }
            innoveAjalugu.setIsikukood(student.getPerson().getIdcode());
            
            String ehisSchool = ehisValue(student.getSchool().getEhisSchool());
            innoveAjalugu.setKoolId(ehisSchool != null ? new BigInteger(ehisSchool) : null);
            
            studentLog = makeRequest(student, innoveAjalugu);
        } catch (Exception e) {
            studentLog = bindingException(student, e, "ehis." + getServiceCode());
        }
        return studentLog;
    }

    private WsEhisStudentLog innoveAjalugu(Student student, InnoveAjalugu request, WsEhisStudentLog log) {
        EhisInnoveAjaluguResponse response = ehisClient.innoveAjalugu(getXroadHeader(), request);
        
        if (response.hasError()) {
            log.setHasXteeErrors(Boolean.TRUE);
            log.setLogTxt(ExceptionUtil.getRootCause(response.getLog().getError()).toString());
        } else {
            updateStudentServices(student, response);
        }
        
        return logService.insert(response.getLog(), log);
    }
    
    private WsEhisStudentLog makeRequest(Student student, InnoveAjalugu request) {
        WsEhisStudentLog wsEhisStudentLog = new WsEhisStudentLog();
        wsEhisStudentLog.setSchool(student.getSchool());

        return innoveAjalugu(student, request, wsEhisStudentLog);
    }

    @Transactional(TxType.REQUIRES_NEW)
    private void updateStudentServices(Student student, EhisInnoveAjaluguResponse response) {
        deletePreviousEhisServices(student);
        createNewEhisServices(student, response);
    }
    
    private static void deletePreviousEhisServices(Student student) {
        student.getSupportServices().removeIf(service -> Boolean.TRUE.equals(service.getEhis()));
    }
    
    private void createNewEhisServices(Student student, EhisInnoveAjaluguResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        InnoveAjaluguResponse result = response.getResult();
        LocalDate now = LocalDate.now();
        result.getAjalugu().getKirje().forEach(kirje -> {
            StudentSupportService entity = new StudentSupportService();
            LocalDate startDate = convertGregorianCalendar(kirje.getAlgusKp());
            LocalDate endDate = convertGregorianCalendar(kirje.getLoppKp());
            entity.setEntryDate(startDate);
            entity.setNameEt(String.format("EHIS - %s", kirje.getKirjeLiik()));
            
            StringBuilder content = new StringBuilder();
            content.append(kirje.getNimetus() != null ? kirje.getNimetus() : "-");
            if (startDate != null) {
                content.append(" (");
                content.append(formatter.format(startDate));
                if (endDate != null) {
                    content.append(" - ");
                    content.append(formatter.format(endDate));
                }
                content.append(")");
            }
            content.append(". ");
            content.append(kirje.getOppeasutusNimi() != null ? kirje.getOppeasutusNimi() : "-");
            
            entity.setContent(content.toString());
            entity.setValidity(em.getReference(Classifier.class, endDate != null && !endDate.isAfter(now) ? SupportServiceValidity.TUGIKEHTIV_L.name() : SupportServiceValidity.TUGIKEHTIV_K.name()));
            entity.setIsPublic(Boolean.FALSE);
            entity.setEhis(Boolean.TRUE);
            entity.setStudent(student);
            student.getSupportServices().add(entity);
        });
    }
    
    private static LocalDate convertGregorianCalendar(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return LocalDate.of(calendar.getYear(), calendar.getMonth(), calendar.getDay());
    }

    @Override
    protected String getServiceCode() {
        return "innoveAjalugu";
    }

}
