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
import ee.hois.xroad.ehis.generated.KhlVOTAArr;

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
                WsEhisStudentLog log = curriculumFulfillment(student);
                fulfilment.add(EhisStudentReport.CurriculaFulfilment.of(student, log));
            }
            ehisStudentReport.setFulfilments(fulfilment);
            break;
        case FOREIGN_STUDY:
            List<EhisStudentReport.ForeignStudy> foreignStudies = new ArrayList<>();
            for (DirectiveStudent directiveStudent : findForeignStudents(schoolId, ehisStudentForm)) {
                WsEhisStudentLog log = ehisDirectiveStudentService.foreignStudy(directiveStudent);
                foreignStudies.add(EhisStudentReport.ForeignStudy.of(directiveStudent, log));
            }
            ehisStudentReport.setForeignStudies(foreignStudies);
            break;
        case GRADUATION:
            List<EhisStudentReport.Graduation> graduations = new ArrayList<>();
            for (Directive directive : findDirectives(schoolId, ehisStudentForm)) {
                for (DirectiveStudent directiveStudent : directive.getStudents()) {
                    // TODO check for printed status
                    WsEhisStudentLog log = ehisDirectiveStudentService.graduation(directiveStudent);
                    graduations.add(EhisStudentReport.Graduation.of(directiveStudent, log));
                }
            }
            ehisStudentReport.setGraduations(graduations);
            break;
        case VOTA:
            throw new UnsupportedOperationException();
            // vota(Student)
        default:
            break;
        }
        return ehisStudentReport;
    }

    private WsEhisStudentLog curriculumFulfillment(Student student) {
        try {
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
        } catch (Exception e) {
            return bindingException(student, e);
        }
    }

    private WsEhisStudentLog vota(/* XXX not sure about parameter type */ Student student) {
        try {
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

            KhlVOTAArr vota = new KhlVOTAArr();
            // TODO fill vota records

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            khlKorgharidusMuuda.setVOTAKirjed(vota);
            khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

            return makeRequest(student, khlOppeasutusList);
        } catch (Exception e) {
            return bindingException(student, e);
        }
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

    private List<DirectiveStudent> findForeignStudents(Long schoolId, EhisStudentForm criteria) {
        TypedQuery<DirectiveStudent> q = em.createQuery(
                "select ds from DirectiveStudent ds left join ds.studyPeriodEnd where ds.canceled = false and ds.directive.school.id = ?1 and ds.directive.type.code = ?2 and ds.directive.status.code = ?3 and ((ds.isPeriod = false and ds.endDate >= ?4 and ds.endDate <= ?5) or (ds.isPeriod = true and ds.studyPeriodEnd.endDate >= ?6 and ds.studyPeriodEnd.endDate <= ?7))", DirectiveStudent.class);
        q.setParameter(1, schoolId);
        q.setParameter(2, DirectiveType.KASKKIRI_VALIS.name());
        q.setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        q.setParameter(4, criteria.getFrom());
        q.setParameter(5, criteria.getThru());
        q.setParameter(6, criteria.getFrom());
        q.setParameter(7, criteria.getThru());

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
