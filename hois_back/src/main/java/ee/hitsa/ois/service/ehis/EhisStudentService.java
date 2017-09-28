package ee.hitsa.ois.service.ehis;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.web.commandobject.ehis.EhisStudentForm;
import ee.hitsa.ois.web.dto.EhisStudentReport;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppekavaTaitmine;

@Transactional
@Service
public class EhisStudentService extends EhisService {

    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;

    public EhisStudentReport exportStudents(Long schoolId, EhisStudentForm ehisStudentForm) {
        EhisStudentReport ehisStudentReport = new EhisStudentReport();
        switch (ehisStudentForm.getDataType()) {
        case CURRICULA_FULFILMENT:
            List<EhisStudentReport.CurriculaFulfilment> fulfilment = new ArrayList<>();
            for (Student student : findStudents(schoolId)) {
                WsEhisStudentLog log;
                try {
                    log = curriculumFulfillment(student);
                } catch (Exception e) {
                    log = bindingException(student, e);
                }
                fulfilment.add(EhisStudentReport.CurriculaFulfilment.of(student, log));
            }
            ehisStudentReport.setFulfilments(fulfilment);
            break;
        case FOREIGN_STUDY:
            throw new UnsupportedOperationException();
        case GRADUATION:
            List<EhisStudentReport.Graduation> graduations = new ArrayList<>();
            for (Directive directive : findDirectives(schoolId, ehisStudentForm)) {
                for (DirectiveStudent directiveStudent : directive.getStudents()) {
                    // TODO check for printed status
                    WsEhisStudentLog log;
                    try {
                        log = ehisDirectiveStudentService.graduation(directiveStudent);
                    } catch (Exception e) {
                        log = bindingException(directive, e);
                    }
                    graduations.add(EhisStudentReport.Graduation.of(directiveStudent, log));
                }
            }
            ehisStudentReport.setGraduations(graduations);
            break;
        case VOTA:
            throw new UnsupportedOperationException();
        default:
            break;
        }
        return ehisStudentReport;
    }

    private WsEhisStudentLog curriculumFulfillment(Student student) {
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppekavaTaitmine oppekavaTaitmine = new KhlOppekavaTaitmine();
        oppekavaTaitmine.setMuutusKp(date(LocalDate.now()));
        // TODO currently no way to find
        oppekavaTaitmine.setTaitmiseProtsent(new BigDecimal(100));
        // TODO currently no way to find
        oppekavaTaitmine.setAinepunkte(new BigDecimal(50));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppekavaTaitmine(oppekavaTaitmine);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        return makeRequest(student, khlOppeasutusList);
    }

    private WsEhisStudentLog makeRequest(Student student, KhlOppeasutusList khlOppeasutusList) {
        WsEhisStudentLog wsEhisStudentLog = new WsEhisStudentLog();
        wsEhisStudentLog.setSchool(student.getSchool());

        return laeKorgharidused(khlOppeasutusList, wsEhisStudentLog);
    }

    private List<Student> findStudents(Long schoolId) {
        TypedQuery<Student> q = em.createQuery("select s from Student s where s.school.id = ?1 and s.status.code = ?2", Student.class);
        q.setParameter(1, schoolId);
        q.setParameter(2, StudentStatus.OPPURSTAATUS_O.name());

        return q.getResultList();
    }

    private List<Directive> findDirectives(Long schoolId, EhisStudentForm criteria) {
        TypedQuery<Directive> q = em.createQuery("select d from Directive d where d.school.id = ?1 and d.type.code = ?2 and d.status.code = ?3 and d.confirmDate >= ?4 and d.confirmDate <= ?5", Directive.class);
        q.setParameter(1, schoolId);
        q.setParameter(2, DirectiveType.KASKKIRI_LOPET.name());
        q.setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        q.setParameter(4, criteria.getFrom());
        q.setParameter(5, criteria.getThru());

        return q.getResultList();
    }

    @Override
    protected String getServiceCode() {
        return LAE_KORGHARIDUS_SERVICE_CODE;
    }
}
