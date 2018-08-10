package ee.hitsa.ois.service.ehis;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.DocumentStatus;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.enums.FormType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
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

    private static final BigInteger FORMAL_LEARNING_TYPE = BigInteger.ONE;
    private static final BigInteger INFORMAL_LEARNING_TYPE = BigInteger.valueOf(2);

    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;

    public List<? extends EhisStudentReport> exportStudents(Long schoolId, EhisStudentForm ehisStudentForm) {
        switch (ehisStudentForm.getDataType()) {
        case CURRICULA_FULFILMENT: return curriculumFulfillment(schoolId);
        case FOREIGN_STUDY: return foreignStudy(schoolId, ehisStudentForm);
        case GRADUATION: return graduation(schoolId, ehisStudentForm);
        case VOTA: return vota(schoolId, ehisStudentForm);
        default: throw new AssertionFailedException("Unknown datatype");
        }
    }

    private List<EhisStudentReport.ApelApplication> vota(Long schoolId, EhisStudentForm ehisStudentForm) {
        List<EhisStudentReport.ApelApplication> apelApplications = new ArrayList<>();
        for(ApelApplication app : findApelApplications(schoolId, ehisStudentForm)) {
            WsEhisStudentLog log;
            Student student = app.getStudent();
            List<EhisStudentReport.ApelApplication.StudyRecord> reportRecords = new ArrayList<>();
            try {
                KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
                KhlVOTAArr votaRecords = new KhlVOTAArr();

                XMLGregorianCalendar confirmed = date(app.getConfirmed().toLocalDate());
                boolean vocational = Boolean.TRUE.equals(app.getIsVocational());

                for(ApelApplicationRecord record : StreamUtil.nullSafeList(app.getRecords())) {
                    boolean formalLearning = Boolean.TRUE.equals(record.getIsFormalLearning());
                    BigInteger learningType = formalLearning ? FORMAL_LEARNING_TYPE : INFORMAL_LEARNING_TYPE;
                    if(formalLearning) {
                        for(ApelApplicationFormalSubjectOrModule sorm : StreamUtil.nullSafeList(record.getFormalSubjectsOrModules())) {
                            BigDecimal credits = sorm.getCredits();
                            LocalDate gradeDate = sorm.getGradeDate();
                            ApelSchool apelSchool = sorm.getApelSchool();
                            String schoolNameEt = apelSchool != null ? apelSchool.getNameEt() : null;
                            Classifier country = apelSchool != null ? apelSchool.getCountry() : null;
                            
                            KhlVOTA vota = new KhlVOTA();
                            vota.setMuutusKp(confirmed);
                            vota.setArvestuseTyyp(learningType);
                            vota.setAinepunkte(credits != null ? credits.toString() : null);
                            vota.setOppeasutuseNimi(schoolNameEt);
                            vota.setOrigSooritAeg(date(gradeDate));
                            vota.setKlRiik(ehisValue(country));
                            votaRecords.getVOTA().add(vota);
                            
                            EhisStudentReport.ApelApplication.StudyRecord reportRecord = new EhisStudentReport.ApelApplication.StudyRecord(credits, Boolean.valueOf(formalLearning));
                            reportRecord.setGradeDate(gradeDate);
                            reportRecord.setSchoolNameEt(schoolNameEt);
                            reportRecord.setCountryCode(EntityUtil.getNullableCode(country));
                            reportRecords.add(reportRecord);
                        }
                    } else {
                        for(ApelApplicationInformalSubjectOrModule sorm : StreamUtil.nullSafeList(record.getInformalSubjectsOrModules())) {
                            BigDecimal credits;
                            if(vocational) {
                                if(sorm.getCurriculumVersionOmoduleTheme() != null) {
                                    credits = sorm.getCurriculumVersionOmoduleTheme().getCredits();
                                } else {
                                    credits = sorm.getCurriculumVersionOmodule().getThemes().stream()
                                            .map(r -> r.getCredits()).filter(r -> r != null)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                                }
                            } else {
                                credits = sorm.getSubject().getCredits();
                            }
                            
                            KhlVOTA vota = new KhlVOTA();
                            vota.setMuutusKp(confirmed);
                            vota.setArvestuseTyyp(learningType);
                            vota.setAinepunkte(credits != null ? credits.toString() : null);
                            votaRecords.getVOTA().add(vota);
                            
                            EhisStudentReport.ApelApplication.StudyRecord reportRecord = new EhisStudentReport.ApelApplication.StudyRecord(credits, Boolean.valueOf(formalLearning));
                            reportRecords.add(reportRecord);
                        }
                    }
                }

                KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
                khlKorgharidusMuuda.setVOTAKirjed(votaRecords);
                khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

                log = makeRequest(student, khlOppeasutusList);
            } catch (Exception e) {
                log = bindingException(student, e);
            }
            apelApplications.add(new EhisStudentReport.ApelApplication(app, log, reportRecords));
        }
        return apelApplications;
    }

    private List<EhisStudentReport.Graduation> graduation(Long schoolId, EhisStudentForm ehisStudentForm) {
        List<EhisStudentReport.Graduation> graduations = new ArrayList<>();
        Query extraQuery = em.createNativeQuery("select f.full_code"
                + " from form f"
                + " join diploma_supplement_form dsf on dsf.form_id = f.id"
                + " where dsf.diploma_supplement_id = ?1 and f.status_code = ?2 and f.type_code = ?3"
                + " order by f.numeral")
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .setParameter(3, FormType.LOPUBLANKETT_HINL.name());
        List<?> result = em.createNativeQuery("select ds.id, dip_f.full_code as dip_code, sup_f.full_code as sup_code, sup.id as sup_id"
                + " from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " left join study_period spe on spe.id = ds.study_period_end_id"
                + " join diploma dip on dip.directive_id = ds.directive_id and dip.student_id = ds.student_id"
                + " join form dip_f on dip_f.id = dip.form_id"
                + " join diploma_supplement sup on sup.diploma_id = dip.id"
                + " join diploma_supplement_form dsf on dsf.diploma_supplement_id = sup.id"
                + " join form sup_f on sup_f.id = dsf.form_id"
                + " where ds.canceled = false and d.school_id = ?1"
                + " and d.type_code = ?2 and d.status_code = ?3"
                + " and d.confirm_date >= ?4 and d.confirm_date <= ?5"
                + " and dip.status_code != ?6 and dip_f.status_code = ?7"
                + " and sup.status_code != ?6 and sup_f.status_code = ?7 and sup_f.type_code = ?8")
                .setParameter(1, schoolId)
                .setParameter(2, DirectiveType.KASKKIRI_LOPET.name())
                .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter(4, JpaQueryUtil.parameterAsTimestamp(ehisStudentForm.getFrom()))
                .setParameter(5, JpaQueryUtil.parameterAsTimestamp(ehisStudentForm.getThru()))
                .setParameter(6, DocumentStatus.LOPUDOK_STAATUS_K.name())
                .setParameter(7, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .setParameter(8, FormType.LOPUBLANKETT_HIN.name())
                .getResultList();
        for (Object r : result) {
            DirectiveStudent directiveStudent = em.getReference(DirectiveStudent.class, resultAsLong(r, 0));
            String docNr = resultAsString(r, 1);
            String academicNr = resultAsString(r, 2);
            List<?> extraResult = extraQuery
                    .setParameter(1, resultAsLong(r, 3))
                    .getResultList();
            List<String> extraNr = StreamUtil.toMappedList(er -> resultAsString(er, 0), extraResult);
            WsEhisStudentLog log = ehisDirectiveStudentService.graduation(directiveStudent, docNr, academicNr, extraNr);
            graduations.add(new EhisStudentReport.Graduation(directiveStudent, log, docNr, academicNr, extraNr));
        }
        return graduations;
    }

    private List<EhisStudentReport.ForeignStudy> foreignStudy(Long schoolId, EhisStudentForm ehisStudentForm) {
        List<EhisStudentReport.ForeignStudy> foreignStudies = new ArrayList<>();
        for (DirectiveStudent directiveStudent : findForeignStudents(schoolId, ehisStudentForm)) {
            BigDecimal points = BigDecimal.ZERO; // TODO get real value
            Integer nominalStudyExtension = Integer.valueOf(getNominalStudyExtension(directiveStudent));
            WsEhisStudentLog log = ehisDirectiveStudentService.foreignStudy(directiveStudent, points, nominalStudyExtension);
            foreignStudies.add(new EhisStudentReport.ForeignStudy(directiveStudent, log, points, nominalStudyExtension));
        }
        return foreignStudies;
    }
    
    private static int getNominalStudyExtension(DirectiveStudent directiveStudent) {
        LocalDate fromDate = DateUtils.periodStart(directiveStudent);
        LocalDate toDate = DateUtils.periodEnd(directiveStudent);
        long months = ChronoUnit.MONTHS.between(fromDate, toDate.plusDays(1));
        if (months >= 8) {
            return 2;
        } else if (months >= 3) {
            return 1;
        }
        return 0;
    }

    private List<EhisStudentReport.CurriculaFulfilment> curriculumFulfillment(Long schoolId) {
        List<EhisStudentReport.CurriculaFulfilment> fulfilment = new ArrayList<>();
        for (Student student : findStudents(schoolId)) {
            StudentCurriculumCompletion completion = getStudentCurriculumCompletion(student);
            if (completion == null) {
                continue;
            }
            BigDecimal points = completion.getCredits();
            Boolean lastPeriod = Boolean.FALSE;
            if (points == null) {
                points = completion.getCreditsLastStudyPeriod();
                lastPeriod = Boolean.TRUE;
            }
            if (points == null) {
                continue;
            }
            BigDecimal percentage = StudentUtil.getCurriculumCompletion(points, student);
            WsEhisStudentLog log = curriculumFulfillment(student, percentage, points, lastPeriod);
            fulfilment.add(new EhisStudentReport.CurriculaFulfilment(student, log, percentage, points, lastPeriod));
        }
        return fulfilment;
    }

    private StudentCurriculumCompletion getStudentCurriculumCompletion(Student student) {
        List<StudentCurriculumCompletion> result = em.createQuery("select scc"
                + " from StudentCurriculumCompletion scc where scc.student = ?1", 
                StudentCurriculumCompletion.class)
                .setParameter(1, student)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    private WsEhisStudentLog curriculumFulfillment(Student student, BigDecimal percentage,
            BigDecimal points, Boolean lastPeriod) {
        try {
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

            KhlOppekavaTaitmine oppekavaTaitmine = new KhlOppekavaTaitmine();
            oppekavaTaitmine.setMuutusKp(date(LocalDate.now()));
            oppekavaTaitmine.setTaitmiseProtsent(percentage);
            oppekavaTaitmine.setAinepunkte(points);
            oppekavaTaitmine.setEelminePeriood(yesNo(lastPeriod));

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            khlKorgharidusMuuda.setOppekavaTaitmine(oppekavaTaitmine);
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
        return em.createQuery("select s from Student s where s.school.id = ?1 and s.status.code in ?2", Student.class)
                .setParameter(1, schoolId)
                .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
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

    private List<ApelApplication> findApelApplications(Long schoolId, EhisStudentForm criteria) {
        return em.createQuery("select a from ApelApplication a where a.school.id = ?1 and a.confirmed >= ?2 and a.confirmed <= ?3", ApelApplication.class)
                .setParameter(1, schoolId)
                .setParameter(2, DateUtils.firstMomentOfDay(criteria.getFrom()))
                .setParameter(3, DateUtils.lastMomentOfDay(criteria.getThru()))
                .getResultList();
    }

    @Override
    protected String getServiceCode() {
        return LAE_KORGHARIDUS_SERVICE_CODE;
    }
}
