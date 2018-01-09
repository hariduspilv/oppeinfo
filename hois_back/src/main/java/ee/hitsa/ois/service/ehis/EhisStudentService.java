package ee.hitsa.ois.service.ehis;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisStudentForm;
import ee.hitsa.ois.web.dto.EhisStudentReport;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppekavaTaitmine;
import ee.hois.xroad.ehis.generated.KhlVOTA;
import ee.hois.xroad.ehis.generated.KhlVOTAArr;

@Transactional
@Service
public class EhisStudentService extends EhisService {

    private static BigInteger FORMAL_LEARNING_TYPE = BigInteger.ONE;
    private static BigInteger INFORMAL_LEARNING_TYPE = BigInteger.valueOf(2);

    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;

    public EhisStudentReport exportStudents(Long schoolId, EhisStudentForm ehisStudentForm) {
        EhisStudentReport ehisStudentReport = new EhisStudentReport();
        switch (ehisStudentForm.getDataType()) {
        case CURRICULA_FULFILMENT:
            List<EhisStudentReport.CurriculaFulfilment> fulfilment = new ArrayList<>();
            for (Student student : findStudents(schoolId)) {
                WsEhisStudentLog log = curriculumFulfillment(student);
                fulfilment.add(new EhisStudentReport.CurriculaFulfilment(student, log));
            }
            ehisStudentReport.setFulfilments(fulfilment);
            break;
        case FOREIGN_STUDY:
            List<EhisStudentReport.ForeignStudy> foreignStudies = new ArrayList<>();
            for (DirectiveStudent directiveStudent : findForeignStudents(schoolId, ehisStudentForm)) {
                WsEhisStudentLog log = ehisDirectiveStudentService.foreignStudy(directiveStudent);
                foreignStudies.add(new EhisStudentReport.ForeignStudy(directiveStudent, log));
            }
            ehisStudentReport.setForeignStudies(foreignStudies);
            break;
        case GRADUATION:
            List<EhisStudentReport.Graduation> graduations = new ArrayList<>();
            for (Directive directive : findDirectives(schoolId, ehisStudentForm)) {
                for (DirectiveStudent directiveStudent : directive.getStudents()) {
                    // TODO check for printed status
                    WsEhisStudentLog log = ehisDirectiveStudentService.graduation(directiveStudent);
                    graduations.add(new EhisStudentReport.Graduation(directiveStudent, log));
                }
            }
            ehisStudentReport.setGraduations(graduations);
            break;
        case VOTA:
            List<EhisStudentReport.ApelApplication> apelApplications = new ArrayList<>();
            for(ApelApplication app : findApelApplications(schoolId, ehisStudentForm)) {
                WsEhisStudentLog log = vota(app);
                apelApplications.add(new EhisStudentReport.ApelApplication(app, log));
            }
            ehisStudentReport.setApelApplications(apelApplications);
            break;
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

    private WsEhisStudentLog vota(ApelApplication application) {
        Student student = application.getStudent();
        try {
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
            KhlVOTAArr votaRecords = new KhlVOTAArr();

            XMLGregorianCalendar confirmed = date(application.getConfirmed().toLocalDate());
            boolean vocational = Boolean.TRUE.equals(application.getIsVocational());

            for(ApelApplicationRecord record : StreamUtil.nullSafeList(application.getRecords())) {
                boolean formalLearning = Boolean.TRUE.equals(record.getIsFormalLearning());
                BigInteger learningType = formalLearning ? FORMAL_LEARNING_TYPE : INFORMAL_LEARNING_TYPE;
                if(formalLearning) {
                    for(ApelApplicationFormalSubjectOrModule sorm : StreamUtil.nullSafeList(record.getFormalSubjectsOrModules())) {
                        KhlVOTA vota = new KhlVOTA();
                        vota.setMuutusKp(confirmed);
                        vota.setArvestuseTyyp(learningType);
                        vota.setAinepunkte(sorm.getCredits() != null ? sorm.getCredits().toString() : null);
                        ApelSchool apelSchool = sorm.getApelSchool();
                        vota.setOppeasutuseNimi(apelSchool != null ? apelSchool.getNameEt() : null);
                        vota.setKlRiik(apelSchool != null ? ehisValue(apelSchool.getCountry()) : null);
                        votaRecords.getVOTA().add(vota);
                    }
                } else {
                    for(ApelApplicationInformalSubjectOrModule sorm : StreamUtil.nullSafeList(record.getInformalSubjectsOrModules())) {
                        KhlVOTA vota = new KhlVOTA();
                        vota.setMuutusKp(confirmed);
                        vota.setArvestuseTyyp(learningType);
                        BigDecimal credits;
                        if(vocational) {
                            if(sorm.getCurriculumVersionOmoduleTheme() != null) {
                                credits = sorm.getCurriculumVersionOmoduleTheme().getCredits();
                            } else {
                                credits = sorm.getCurriculumVersionOmodule().getThemes().stream().map(r -> r.getCredits()).filter(r -> r != null).reduce(BigDecimal.ZERO, BigDecimal::add);
                            }
                        } else {
                            credits = sorm.getSubject().getCredits();
                        }
                        vota.setAinepunkte(credits != null ? credits.toString() : null);
                        votaRecords.getVOTA().add(vota);
                    }
                }
            }

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            khlKorgharidusMuuda.setVOTAKirjed(votaRecords);
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
        return em.createQuery("select s from Student s where s.school.id = ?1 and s.status.code = ?2", Student.class)
                .setParameter(1, schoolId)
                .setParameter(2, StudentStatus.OPPURSTAATUS_O.name())
                .getResultList();
    }

    private List<DirectiveStudent> findForeignStudents(Long schoolId, EhisStudentForm criteria) {
        return em.createQuery(
                "select ds from DirectiveStudent ds left join ds.studyPeriodEnd where ds.canceled = false and ds.directive.school.id = ?1 and ds.directive.type.code = ?2 and ds.directive.status.code = ?3 and ((ds.isPeriod = false and ds.endDate >= ?4 and ds.endDate <= ?5) or (ds.isPeriod = true and ds.studyPeriodEnd.endDate >= ?6 and ds.studyPeriodEnd.endDate <= ?7))", DirectiveStudent.class)
                .setParameter(1, schoolId)
                .setParameter(2, DirectiveType.KASKKIRI_VALIS.name())
                .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter(4, criteria.getFrom())
                .setParameter(5, criteria.getThru())
                .setParameter(6, criteria.getFrom())
                .setParameter(7, criteria.getThru())
                .getResultList();
    }

    private List<Directive> findDirectives(Long schoolId, EhisStudentForm criteria) {
        return em.createQuery("select d from Directive d where d.school.id = ?1 and d.type.code = ?2 and d.status.code = ?3 and d.confirmDate >= ?4 and d.confirmDate <= ?5", Directive.class)
                .setParameter(1, schoolId)
                .setParameter(2, DirectiveType.KASKKIRI_LOPET.name())
                .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter(4, criteria.getFrom())
                .setParameter(5, criteria.getThru())
                .getResultList();
    }

    private List<ApelApplication> findApelApplications(Long schoolId, EhisStudentForm criteria) {
        return em.createQuery("select a from ApelApplication a where a.school.id = ?1 and a.confirmed >= ?2 and a.confirmed <= ?3", ApelApplication.class)
                .setParameter(1, schoolId)
                .setParameter(2, criteria.getFrom())
                .setParameter(3, criteria.getThru())
                .getResultList();
    }

    @Override
    protected String getServiceCode() {
        return LAE_KORGHARIDUS_SERVICE_CODE;
    }
}
