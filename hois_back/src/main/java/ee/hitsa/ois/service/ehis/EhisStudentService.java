package ee.hitsa.ois.service.ehis;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsStringList;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.concurrent.EhisFutureTask;
import ee.hitsa.ois.concurrent.WrapperCallable;
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
import ee.hitsa.ois.enums.EhisStudentDataType;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.enums.FormType;
import ee.hitsa.ois.enums.FutureStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisStudentForm;
import ee.hitsa.ois.web.dto.EhisStudentReport;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import ee.hois.xroad.ehis.generated.KhlErivajadusedArr;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlKursuseMuutus;
import ee.hois.xroad.ehis.generated.KhlMuudAndmedMuutmine;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppekavaTaitmine;
import ee.hois.xroad.ehis.generated.KhlVOTA;
import ee.hois.xroad.ehis.generated.KhlVOTAArr;

/**
 * 
 *
 *  @since 30.07.2019
 *  
 */
@Transactional
@Service
public class EhisStudentService extends EhisService {

    private static final BigInteger FORMAL_LEARNING_TYPE = BigInteger.ONE;
    private static final BigInteger INFORMAL_LEARNING_TYPE = BigInteger.valueOf(2);
    
    /**
     *  The first key - school id
     *  The second key - request hash
     *  Value - request object
     *  
     *  Values removed when:
     *  - Future is done and its status checked in {@link #exportStudentsStatus(HoisUserDetails, String)}
     */
    private static final Map<Long, Map<String, ExportStudentsRequest>> EXPORT_STUDENTS_REQUESTS = new ConcurrentHashMap<>();
    private static final long RESULT_EXPIRATION_MINUTES = 60;
    
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;
    
    /**
     * CancellationException in case if was canceled with .cancel(false)
     * InterruptedException in case if was canceled with .cancel(true)
     * 
     * TODO: The last request can be not included because future's `done` true once interrupted.
     * 
     * @param hoisUser
     * @param key
     * @return
     */
    public FutureStatusResponse exportStudentsStatus(HoisUserDetails hoisUser, String key) {
        FutureStatusResponse response = new FutureStatusResponse();
        if (EXPORT_STUDENTS_REQUESTS.containsKey(hoisUser.getSchoolId()) && EXPORT_STUDENTS_REQUESTS.get(hoisUser.getSchoolId()).containsKey(key)) {
            ExportStudentsRequest future = EXPORT_STUDENTS_REQUESTS.get(hoisUser.getSchoolId()).get(key);
            response.setProgress(Float.valueOf(future.getProgress()));
            if (future.isDone()) {
                EXPORT_STUDENTS_REQUESTS.get(hoisUser.getSchoolId()).remove(key);
                try {
                    response.setResult(future.get());
                    response.setHasError(Boolean.FALSE);
                    response.setStatus(FutureStatus.DONE);
                } catch (ExecutionException ex) {
                    LOG.info("Error during executing: " + ex.getMessage());
                    response.setHasError(Boolean.TRUE);
                    response.setError(ex.getMessage());
                    response.setStatus(FutureStatus.EXCEPTION);
                } catch (CancellationException | InterruptedException ex) {
                    // TODO: The last request can be not included because its `done` is true once interrupted.
                    response.setHasError(Boolean.TRUE);
                    response.setError(ex.getMessage());
                    response.setCancelledBy(future.getCancelledBy());
                    if (ex instanceof CancellationException) {
                        response.setStatus(FutureStatus.CANCELLED);
                    } else {
                        response.setStatus(FutureStatus.INTERRUPTED);
                    }
                    response.setResult(future.getWrappedResult());
                }
                response.setStarted(future.getStarted());
                response.setEnded(future.getEnded());
            } else {
                response.setHasError(Boolean.FALSE);
                response.setStatus(FutureStatus.IN_PROGRESS);
            }
        } else {
            response.setHasError(Boolean.TRUE);
            response.setStatus(FutureStatus.NOT_FOUND);
        }
        return response;
    }
    
    public ExportStudentsRequest findOverlappedActiveExportStudentsRequest(HoisUserDetails hoisUser, EhisStudentForm ehisStudentForm, ExportStudentsRequest origin) {
        if (EXPORT_STUDENTS_REQUESTS.containsKey(hoisUser.getSchoolId())) {
            return EXPORT_STUDENTS_REQUESTS.get(hoisUser.getSchoolId()).values().stream().filter(request -> {
                if (request.isDone() || request.equals(origin) || !request.getType().equals(ehisStudentForm.getDataType())) {
                    return false;
                }
                return ((request.getFrom() == null && ehisStudentForm.getThru() == null) || !request.getFrom().isAfter(ehisStudentForm.getThru()))
                        && ((request.getThru() == null && ehisStudentForm.getFrom() == null ) || !request.getThru().isBefore(ehisStudentForm.getFrom()));
            }).findAny().orElse(null);
        }
        
        return null;
    }
    
    public ExportStudentsRequest findOverlappedActiveExportStudentsRequest(HoisUserDetails hoisUser, EhisStudentForm ehisStudentForm) {
        return findOverlappedActiveExportStudentsRequest(hoisUser, ehisStudentForm, null);
    }
    
    private static void removeExpiredExportStudentsResults() {
        LocalDateTime expirationLimit = LocalDateTime.now().minusMinutes(RESULT_EXPIRATION_MINUTES);
        EXPORT_STUDENTS_REQUESTS.values().forEach(map -> {
            map.values().removeIf(request -> {
                if (request.isDone()) {
                    LocalDateTime ended = request.getEnded(); // synchronized method
                    if (ended != null) {
                        return expirationLimit.isAfter(ended);
                    }
                    return false;
                }
                return false;
            });
        });
    }
    
    Queue<? extends EhisStudentReport> exportStudents(Long schoolId, EhisStudentForm ehisStudentForm,
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        switch (ehisStudentForm.getDataType()) {
        case COURSE_CHANGE: return courseChange(schoolId, ehisStudentForm, wrapper, maxRequests);
        case CURRICULA_FULFILMENT: return curriculumFulfillment(schoolId, wrapper, maxRequests);
        case DORMITORY: return dormitoryChanges(schoolId, ehisStudentForm, wrapper, maxRequests);
        case FOREIGN_STUDY: return foreignStudy(schoolId, ehisStudentForm, wrapper, maxRequests);
        case GRADUATION: return graduation(schoolId, ehisStudentForm, wrapper, maxRequests);
        case VOTA: return vota(schoolId, ehisStudentForm, wrapper, maxRequests);
        case SPECIAL_NEEDS: return specialNeeds(schoolId, ehisStudentForm, wrapper, maxRequests);
        case GUEST_STUDENTS: return guestStudents(schoolId, ehisStudentForm, wrapper, maxRequests);
        default: throw new AssertionFailedException("Unknown datatype");
        }
    }

    /**
     * Asynchronous method. Runs in different thread.
     * Has to return {@link Future}.
     * 
     * 1. Creates a future object.
     * 2. Puts into map to be able to get it. Be careful, if user checks (from frontend)
     * it right after this request (it is async, so at this moment user might receive empty answer)
     * then it might say that no request found. So better to wait ~1 second and after check if there is a request.
     * 3. Checks if there is any overlapped and then cancels it.
     * 4. Runs request.
     * 5. Removes old done requests.
     * 
     * @param hoisUser user
     * @param ehisStudentForm form
     * @param requestHash request unique hash.
     * @return Future
     */
    @Async
    public Future<Queue<? extends EhisStudentReport>> exportStudents(HoisUserDetails hoisUser, EhisStudentForm ehisStudentForm, String requestHash) {
        Long schoolId = hoisUser.getSchoolId();
        ExportStudentsRequest request = new ExportStudentsRequest(new WrapperCallable<Queue<? extends EhisStudentReport>>() {
            
            private AtomicInteger max = new AtomicInteger();
            
            @Override
            public Queue<? extends EhisStudentReport> wrapperCall() {
                return exportStudents(schoolId, ehisStudentForm, getWrapper(), max);
            }

            @Override
            public float getProgress() {
                if (max.get() == 0) {
                    return 0;
                }
                return getWrapper().get().size() / (float) max.get();
            }
            
        }, requestHash, hoisUser, ehisStudentForm.getFrom(), ehisStudentForm.getThru(), ehisStudentForm.getDataType());

        if (!EXPORT_STUDENTS_REQUESTS.containsKey(schoolId)) {
            EXPORT_STUDENTS_REQUESTS.put(schoolId, new ConcurrentHashMap<>());
        }
        
        EXPORT_STUDENTS_REQUESTS.get(schoolId).put(requestHash, request);
        
        ExportStudentsRequest overlappedRequest = findOverlappedActiveExportStudentsRequest(hoisUser, ehisStudentForm, request);
        
        if (overlappedRequest != null) {
            if (!overlappedRequest.isDone()) {
                overlappedRequest.cancel(hoisUser, true);
            }
        }
        
        request.run();
        
        removeExpiredExportStudentsResults(); // Async method runs in different thread so it will not cause problems with response time.
        return request;
    }
    
    private Queue<? extends EhisStudentReport> guestStudents(Long schoolId, EhisStudentForm ehisStudentForm,
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        ConcurrentLinkedQueue<EhisStudentReport> reports = new ConcurrentLinkedQueue<>();
        wrapper.set(reports);
        List<DirectiveStudent> directiveStudents = findGuestStudents(schoolId, ehisStudentForm);
        maxRequests.set(directiveStudents.size());
        for(DirectiveStudent directiveStudent : directiveStudents) {
            if (!Thread.interrupted()) {
                WsEhisStudentLog log;
                if (directiveStudent.getCanceled() != null && directiveStudent.getCanceled().booleanValue()) {
                    log = ehisDirectiveStudentService.deleteGuestStudent(directiveStudent);
                } else {
                    log = ehisDirectiveStudentService.sendGuestStudent(directiveStudent);
                }
                reports.add(new EhisStudentReport.GuestStudents(directiveStudent.getStudent(), log));
            }
        }
        return reports;
    }

    private Queue<? extends EhisStudentReport> specialNeeds(Long schoolId, EhisStudentForm ehisStudentForm,
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        ConcurrentLinkedQueue<EhisStudentReport> reports = new ConcurrentLinkedQueue<>();
        wrapper.set(reports);
        Map<Student, ChangedSpecialNeeds> students = findStudentsWithChangedSpecialNeeds(schoolId, ehisStudentForm);
        maxRequests.set(students.size());
        students.entrySet().stream().filter(entry -> {
            if (!Thread.interrupted()) {
                WsEhisStudentLog log = specialNeedChange(entry.getKey(), entry.getValue());
                reports.add(new EhisStudentReport.SpecialNeeds(entry.getKey(), log));
                return false;
            }
            return true;
        }).findAny();
        return reports;
    }

    private Queue<EhisStudentReport> courseChange(Long schoolId, EhisStudentForm ehisStudentForm, 
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        ConcurrentLinkedQueue<EhisStudentReport> reports = new ConcurrentLinkedQueue<>();
        wrapper.set(reports);
        Map<Student, ChangedCourse> students = findStudentsWithChangedCourse(schoolId, ehisStudentForm);
        maxRequests.set(students.size());
        students.entrySet().stream().filter(entry -> {
            if (!Thread.interrupted()) { // Break in case if operation has been cancelled
                WsEhisStudentLog log = courseChange(entry.getKey(), entry.getValue().getChanged(), entry.getValue().getNewCourse());
                reports.add(new EhisStudentReport.Course(entry.getKey(), log, entry.getValue().getNewCourse(), entry.getValue().getChanged()));
                return false;
            }
            return true;
        }).findAny();
        return reports;
    }

    private Queue<EhisStudentReport.Dormitory> dormitoryChanges(Long schoolId, EhisStudentForm ehisStudentForm, 
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        Queue<EhisStudentReport.Dormitory> dormitoryChanges = new ConcurrentLinkedQueue<>(); 
        wrapper.set(dormitoryChanges);
        Map<Student, ChangedDormitory> changes = findStudentsWithChangedDormitory(schoolId, ehisStudentForm);
        maxRequests.set(changes.size());
        for (Student student : changes.keySet()) {
            if (Thread.interrupted()) { // Break in case if operation has been cancelled
                break;
            }
            ChangedDormitory studentChange = changes.get(student);

            WsEhisStudentLog log = dormitoryChange(student, studentChange.getCode(), studentChange.getDate());
            dormitoryChanges.add(new EhisStudentReport.Dormitory(student, log,  studentChange.getCode(),
                    studentChange.getDate()));
        }
        return dormitoryChanges;
    }

    private WsEhisStudentLog dormitoryChange(Student student, String dormitory, LocalDate changeDate) {
        try {
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            KhlMuudAndmedMuutmine khlMuudAndmedMuutmine = new KhlMuudAndmedMuutmine();
            Classifier clDormitory = em.getReference(Classifier.class, dormitory);
            khlMuudAndmedMuutmine.setMuutusKp(date(changeDate));
            khlMuudAndmedMuutmine.setKlYhiselamu(ehisValue(clDormitory));
            khlKorgharidusMuuda.setMuudAndmedMuutmine(khlMuudAndmedMuutmine);
            khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

            return makeRequest(student, khlOppeasutusList);
        } catch (Exception e) {
            return bindingException(student, e);
        }
    }

    private Queue<EhisStudentReport.ApelApplication> vota(Long schoolId, EhisStudentForm ehisStudentForm, 
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        Queue<EhisStudentReport.ApelApplication> apelApplications = new ConcurrentLinkedQueue<>();
        wrapper.set(apelApplications);
        List<ApelApplication> applications = findApelApplications(schoolId, ehisStudentForm);
        maxRequests.set(applications.size());
        for(ApelApplication app : applications) {
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
                            Classifier country = apelSchool != null ? apelSchool.getCountry() : em.getReference(Classifier.class, "RIIK_EST");
                            
                            KhlVOTA vota = new KhlVOTA();
                            vota.setMuutusKp(confirmed);
                            vota.setArvestuseTyyp(learningType);
                            vota.setAinepunkte(credits != null ? credits.toString() : null);
                            vota.setOppeasutuseNimi(schoolNameEt);
                            vota.setOrigSooritAeg(date(gradeDate));
                            vota.setKlRiik(value2(country));
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

    private Queue<EhisStudentReport.Graduation> graduation(Long schoolId, EhisStudentForm ehisStudentForm, 
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        Queue<EhisStudentReport.Graduation> graduations = new ConcurrentLinkedQueue<>();
        wrapper.set(graduations);
        Query extraQuery = em.createNativeQuery("select f.full_code"
                + " from form f"
                + " join diploma_supplement_form dsf on dsf.form_id = f.id"
                + " where dsf.diploma_supplement_id = ?1 and f.status_code = ?2 and f.type_code in (?3) and (dsf.is_english is not true)"
                + " order by f.numeral")
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .setParameter(3, Arrays.asList(FormType.LOPUBLANKETT_HINL.name(), FormType.LOPUBLANKETT_S.name()));
        Query extraQueryEn = em.createNativeQuery("select f.full_code"
                + " from form f"
                + " join diploma_supplement_form dsf on dsf.form_id = f.id"
                + " where dsf.diploma_supplement_id = ?1 and f.status_code = ?2 and f.type_code in (?3) and (dsf.is_english is true)"
                + " order by f.numeral")
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .setParameter(3, Arrays.asList(FormType.LOPUBLANKETT_S.name()));
        List<?> result = em.createNativeQuery("select ds.id, dip_f.full_code as dip_code, sup_f.full_code as sup_code, sup.id as sup_id,"
                + " (select sup_f_en.full_code from diploma_supplement_form dsf_en"
                + " join form sup_f_en on sup_f_en.id = dsf_en.form_id"
                + " where dsf_en.diploma_supplement_id = sup.id and dsf_en.is_english"
                + " and sup_f_en.status_code = ?7 and sup_f_en.type_code = ?9 ) as sup_en_code"
                + " from directive_student ds"
                + " join student s on s.id = ds.student_id"
                + " join directive d on d.id = ds.directive_id"
                + " join diploma dip on dip.directive_id = ds.directive_id and dip.student_id = ds.student_id"
                + " join form dip_f on dip_f.id = dip.form_id"
                + " join diploma_supplement sup on sup.diploma_id = dip.id"
                + " join diploma_supplement_form dsf on dsf.diploma_supplement_id = sup.id"
                + " join form sup_f on sup_f.id = dsf.form_id"
                + " where ds.canceled = false and d.school_id = ?1"
                + " and d.type_code = ?2 and d.status_code = ?3"
                + " and d.confirm_date >= ?4 and d.confirm_date <= ?5"
                + " and dip.status_code != ?6 and dip_f.status_code = ?7"
                + " and sup.status_code != ?6 and sup_f.status_code = ?7"
                + " and sup_f.type_code in (?8) and s.type_code != ?10")
                .setParameter(1, schoolId)
                .setParameter(2, DirectiveType.KASKKIRI_LOPET.name())
                .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter(4, JpaQueryUtil.parameterAsTimestamp(ehisStudentForm.getFrom()))
                .setParameter(5, JpaQueryUtil.parameterAsTimestamp(ehisStudentForm.getThru()))
                .setParameter(6, DocumentStatus.LOPUDOK_STAATUS_K.name())
                .setParameter(7, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .setParameter(8, Arrays.asList(FormType.LOPUBLANKETT_HIN.name(), FormType.LOPUBLANKETT_R.name()))
                .setParameter(9, FormType.LOPUBLANKETT_DS.name())
                .setParameter(10, StudentType.OPPUR_K.name())
                .getResultList();
        maxRequests.set(result.size());
        for (Object r : result) {
            if (Thread.interrupted()) { // Break in case if operation has been cancelled
                break;
            }
            DirectiveStudent directiveStudent = em.getReference(DirectiveStudent.class, resultAsLong(r, 0));
            String docNr = resultAsString(r, 1);
            String academicNr = resultAsString(r, 2);
            String academicNrEn = resultAsString(r, 4);
            List<?> extraResult = extraQuery
                    .setParameter(1, resultAsLong(r, 3))
                    .getResultList();
            List<?> extraResultEn = extraQueryEn
                    .setParameter(1, resultAsLong(r, 3))
                    .getResultList();
            List<String> extraNr = StreamUtil.toMappedList(er -> resultAsString(er, 0), extraResult);
            List<String> extraNrEn = StreamUtil.toMappedList(er -> resultAsString(er, 0), extraResultEn);
            WsEhisStudentLog log = ehisDirectiveStudentService.graduation(directiveStudent, docNr, academicNr, extraNr, academicNrEn, extraNrEn);
            graduations.add(new EhisStudentReport.Graduation(directiveStudent, log, docNr, academicNr, extraNr, academicNrEn, extraNrEn));
        }
        return graduations;
    }

    private Queue<EhisStudentReport.ForeignStudy> foreignStudy(Long schoolId, EhisStudentForm ehisStudentForm, 
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        Queue<EhisStudentReport.ForeignStudy> foreignStudies = new ConcurrentLinkedQueue<>();
        wrapper.set(foreignStudies);
        List<DirectiveStudent> students = findForeignStudents(schoolId, ehisStudentForm);
        maxRequests.set(students.size());
        for (DirectiveStudent directiveStudent : students) {
            if (Thread.interrupted()) { // Break in case if operation has been cancelled
                break;
            }
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

    private Queue<EhisStudentReport.CurriculaFulfilment> curriculumFulfillment(Long schoolId, 
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        Queue<EhisStudentReport.CurriculaFulfilment> fulfilment = new ConcurrentLinkedQueue<>();
        wrapper.set(fulfilment);
        List<Student> students = findStudents(schoolId);
        maxRequests.set(students.size());
        for (Student student : students) {
            if (Thread.interrupted()) { // Break in case if operation has been cancelled
                break;
            }
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
    
    private WsEhisStudentLog specialNeedChange(Student student, ChangedSpecialNeeds specialNeeds) {
        try {
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

            KhlErivajadusedArr khlErivajadusedArr = new KhlErivajadusedArr();
            khlErivajadusedArr.setMuutusKp(date(LocalDate.now()));
            khlErivajadusedArr.getKlErivajadus().addAll(StreamUtil.nullSafeList(specialNeeds.getNeeds())); // List with ehis values. #findStudentsWithChangedSpecialNeeds
            khlErivajadusedArr.getKlTugiteenus().addAll(StreamUtil.nullSafeList(specialNeeds.getServices())); // List with ehis values. #findStudentsWithChangedSpecialNeeds

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            khlKorgharidusMuuda.setErivajadused(khlErivajadusedArr);
            khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);
            return makeRequest(student, khlOppeasutusList);
        } catch (Exception e) {
            return bindingException(student, e);
        }
    }

    WsEhisStudentLog courseChange(Student student, LocalDate changed, Integer newCourse) {
        try {
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

            KhlKursuseMuutus khlKursuseMuutus = new KhlKursuseMuutus();
            khlKursuseMuutus.setMuutusKp(date(changed));
            khlKursuseMuutus.setUusKursus(BigInteger.valueOf(newCourse.intValue()));

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            khlKorgharidusMuuda.setKursuseMuutus(khlKursuseMuutus);
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
        return em.createQuery("select s from Student s where s.school.id = ?1 and s.status.code in ?2 and s.type.code != ?3", Student.class)
                .setParameter(1, schoolId)
                .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
                .setParameter(3, StudentType.OPPUR_K.name())
                .getResultList();
    }

    private Map<Student, ChangedSpecialNeeds> findStudentsWithChangedSpecialNeeds(Long schoolId, EhisStudentForm ehisStudentForm) {
        List<?> data = em.createNativeQuery("select s.id, string_agg(distinct ssn_cl.ehis_value, ';') as ssn_values, string_agg(distinct ass_cl.ehis_value, ';') as ass_values "
                + "from student s "
                + "join student_special_need ssn on ssn.student_id = s.id "
                + "join classifier ssn_cl on ssn_cl.code = ssn.special_need_code "
                + "left join directive_student ds on ds.student_id = s.id "
                + "left join directive d on d.id = ds.directive_id "
                + "left join application a on a.id = ds.application_id "
                + "left join application_support_service ass on ass.application_id = a.id "
                + "left join directive_student ds2 on ds.id = ds2.directive_student_id and ds2.canceled = false "
                + "left join directive d2 on d2.id = ds2.directive_id and d2.type_code = ?3 and d2.status_code = ?4 "
                + "left join classifier ass_cl on ass_cl.code = ass.support_service_code and d.type_code = ?2 "
                + "and d.status_code = ?4 and ds.canceled = false and ds.start_date <= ?6 "
                + "and coalesce ( ds2.start_date, ds.end_date ) >= ?5 "
                + "where s.school_id = ?1 and s.status_code in ?7 "
                + "and exists (select 1 from student_special_need ssn2 where ssn2.student_id = s.id and coalesce(ssn2.changed, ssn2.inserted) >= ?5 and coalesce(ssn2.changed, ssn2.inserted) <= ?6) "
                + "and ssn_cl.ehis_value is not null and s.type_code != ?8 "
                + "group by s.id")
        .setParameter(1, schoolId)
        .setParameter(2, DirectiveType.KASKKIRI_TUGI.name())
        .setParameter(3, DirectiveType.KASKKIRI_TUGILOPP.name())
        .setParameter(4, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
        .setParameter(5, JpaQueryUtil.parameterAsTimestamp(DateUtils.firstMomentOfDay(ehisStudentForm.getFrom())))
        .setParameter(6, JpaQueryUtil.parameterAsTimestamp(DateUtils.lastMomentOfDay(ehisStudentForm.getThru())))
        .setParameter(7, StudentStatus.STUDENT_STATUS_ACTIVE)
        .setParameter(8, StudentType.OPPUR_K.name())
        .getResultList();

        Map<Long, ChangedSpecialNeeds> mappedData = data.stream().collect(Collectors.toMap(
                    r -> resultAsLong(r, 0),
                    r -> new ChangedSpecialNeeds(resultAsStringList(r, 1, ";"), resultAsStringList(r, 2, ";")),
                    (o, n) -> o));
        
        if (!mappedData.isEmpty()) {
            return em.createQuery("select s from Student s where s.id in ?1", Student.class)
                    .setParameter(1, mappedData.keySet()).getResultList().stream().collect(Collectors.toMap(s -> s, s -> mappedData.get(s.getId()), (o, n) -> o));
        }
        
        return Collections.emptyMap();
    }

    Map<Student, ChangedCourse> findStudentsWithChangedCourse(Long schoolId, EhisStudentForm ehisStudentForm) {
        List<?> data = em.createNativeQuery("select s.id, sgyf.new_course, sgyf.changed "
                + "from student_group_year_transfer sgyf "
                + "join student_group sg on sg.id = sgyf.student_group_id "
                + "join student s on s.student_group_id = sg.id "
                + "where s.school_id = ?1 and s.status_code in ?2 and sgyf.changed >= ?3 and sgyf.changed <= ?4 "
                + "and s.type_code != ?5 "
                + "order by sgyf.changed asc ")
                .setParameter(1, schoolId)
                .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
                .setParameter(3, JpaQueryUtil.parameterAsTimestamp(DateUtils.firstMomentOfDay(ehisStudentForm.getFrom())))
                .setParameter(4, JpaQueryUtil.parameterAsTimestamp(DateUtils.lastMomentOfDay(ehisStudentForm.getThru())))
                .setParameter(5, StudentType.OPPUR_K.name())
                .getResultList();
        
        Map<Long, ChangedCourse> mappedData = data.stream().collect(Collectors.toMap(
                    r -> resultAsLong(r, 0),
                    r -> new ChangedCourse(resultAsInteger(r, 1), resultAsLocalDate(r, 2)),
                    (o, n) -> o));

        if (!data.isEmpty()) {
            return em.createQuery("select s from Student s where s.id in ?1", Student.class)
                    .setParameter(1, mappedData.keySet()).getResultList().stream().collect(Collectors.toMap(s -> s, s -> mappedData.get(s.getId()), (o, n) -> o));
        }
        return Collections.emptyMap();
    }
    
    private Map<Student, ChangedDormitory> findStudentsWithChangedDormitory(Long schoolId, EhisStudentForm criteria) {
        Map<Student, ChangedDormitory> studentChanges = new HashMap<>();
        List<?> data = em.createNativeQuery("select student_id, last_dormitory_code, first_dormitory_code,"
                + " (select min(sh.inserted) from student_history sh where sh.student_id = x.student_id and sh.dormitory_code = x.last_dormitory_code"
                + " and sh.inserted >= ?3 and sh.inserted <= ?4) as change_date from ("
                + " select distinct sh.student_id, first_value(sh.dormitory_code) over (partition by sh.student_id order by sh.inserted desc) as last_dormitory_code,"
                + " first_value(coalesce(sh2.dormitory_code, 'x')) over (partition by sh2.student_id order by sh2.inserted asc) as first_dormitory_code"
                + " from student_history sh"
                + " join student s on sh.student_id = s.id"
                + " left join student_history sh2 on sh.student_id = sh2.student_id and sh2.inserted <= ?3"
                + " where s.school_id = ?1 and s.status_code in ?2 and coalesce(sh.dormitory_code, 'x') != 'x'"
                + " and sh.inserted >= ?3 and sh.inserted <= ?4 and s.type_code != ?5) x where x.first_dormitory_code != x.last_dormitory_code")
                .setParameter(1, schoolId)
                .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
                .setParameter(3, JpaQueryUtil.parameterAsTimestamp(DateUtils.firstMomentOfDay(criteria.getFrom())))
                .setParameter(4, JpaQueryUtil.parameterAsTimestamp(DateUtils.lastMomentOfDay(criteria.getThru())))
                .setParameter(5, StudentType.OPPUR_K.name())
                .getResultList();

        Map<Long, ChangedDormitory> changedDormitories = StreamUtil.toMap(r -> resultAsLong(r, 0),
                r -> new ChangedDormitory(resultAsString(r, 1), resultAsLocalDate(r, 3)), data);
        if (!data.isEmpty()) {
            List<Student> students = em.createQuery("select s from Student s where s.id in ?1", Student.class)
                    .setParameter(1, changedDormitories.keySet()).getResultList();
            for (Student s : students) {
                studentChanges.put(s, changedDormitories.get(s.getId()));
            }
        }
        return studentChanges;
    }

    private List<DirectiveStudent> findForeignStudents(Long schoolId, EhisStudentForm criteria) {
        return em.createQuery(
                "select ds from DirectiveStudent ds left join ds.studyPeriodEnd where ds.canceled = false and ds.directive.school.id = ?1 "
                + "and ds.directive.type.code = ?2 and ds.directive.status.code = ?3 "
                + "and ((ds.isPeriod = false and ds.endDate >= ?4 and ds.endDate <= ?5) "
                + "or (ds.isPeriod = true and ds.studyPeriodEnd.endDate >= ?6 and ds.studyPeriodEnd.endDate <= ?7)) "
                + "and ds.student.type.code != ?8", DirectiveStudent.class)
                .setParameter(1, schoolId)
                .setParameter(2, DirectiveType.KASKKIRI_VALIS.name())
                .setParameter(3, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter(4, criteria.getFrom())
                .setParameter(5, criteria.getThru())
                .setParameter(6, criteria.getFrom())
                .setParameter(7, criteria.getThru())
                .setParameter(8, StudentType.OPPUR_K.name())
                .getResultList();
    }
    
    private List<DirectiveStudent> findGuestStudents(Long schoolId, EhisStudentForm criteria) {
        return em.createQuery(
                "select ds from DirectiveStudent ds where ds.directive.school.id = ?1 "
                + "and ds.country.code != ?2 "
                + "and ds.directive.type.code = ?3 "
                + "and (ds.directive.status.code = ?4 or ds.directive.status.code = ?5 ) "
                + "and ds.endDate >= ?6 and ds.endDate <= ?7 "
                + "and ds.curriculumVersion.curriculum.higher = true", DirectiveStudent.class)
                .setParameter(1, schoolId)
                .setParameter(2, "RIIK_EST")
                .setParameter(3, DirectiveType.KASKKIRI_KYLALIS.name())
                .setParameter(4, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter(5, DirectiveStatus.KASKKIRI_STAATUS_TYHISTATUD.name())
                .setParameter(6, criteria.getFrom())
                .setParameter(7, criteria.getThru())
                .getResultList();
    }

    private List<ApelApplication> findApelApplications(Long schoolId, EhisStudentForm criteria) {
        return em.createQuery("select a from ApelApplication a where a.school.id = ?1 and a.confirmed >= ?2 and a.confirmed <= ?3 and a.student.type.code != ?4", ApelApplication.class)
                .setParameter(1, schoolId)
                .setParameter(2, DateUtils.firstMomentOfDay(criteria.getFrom()))
                .setParameter(3, DateUtils.lastMomentOfDay(criteria.getThru()))
                .setParameter(4, StudentType.OPPUR_K.name())
                .getResultList();
    }

    @Override
    protected String getServiceCode() {
        return LAE_KORGHARIDUS_SERVICE_CODE;
    }

    public static class ChangedDormitory {
        private final String code;
        private final LocalDate date;

        public ChangedDormitory(String code, LocalDate date) {
            this.code = code;
            this.date = date;
        }

        public String getCode() {
            return code;
        }

        public LocalDate getDate() {
            return date;
        }

    }
    
    public static class ChangedCourse {
        
        private final Integer newCourse;
        private final LocalDate changed;
        
        public ChangedCourse(Integer newCourse, LocalDate changed) {
            this.newCourse = newCourse;
            this.changed = changed;
        }

        public ChangedCourse(Integer newCourse, LocalDateTime changed) {
            this(newCourse, changed.toLocalDate());
        }

        public Integer getNewCourse() {
            return newCourse;
        }

        public LocalDate getChanged() {
            return changed;
        }
    }
    
    public static class ChangedSpecialNeeds {
        
        // EHIS value
        private final List<String> needs;
        // EHIS value
        private final List<String> services;
        
        public ChangedSpecialNeeds(List<String> needs, List<String> services) {
            this.needs = needs;
            this.services = services;
        }
        
        public List<String> getNeeds() {
            return needs;
        }
        
        public List<String> getServices() {
            return services;
        }
    }
    
    /**
     * Being a Future.
     */
    public static class ExportStudentsRequest extends EhisFutureTask<Queue<? extends EhisStudentReport>> {
        
        private final LocalDate from;
        private final LocalDate thru;
        private final EhisStudentDataType type;
        
        public ExportStudentsRequest(WrapperCallable<Queue<? extends EhisStudentReport>> callable, String hash,
                HoisUserDetails user, LocalDate from, LocalDate thru, EhisStudentDataType type) {
            super(callable, hash, user);
            this.from = from;
            this.thru = thru;
            this.type = type;
        }
        
        public LocalDate getFrom() {
            return from;
        }

        public LocalDate getThru() {
            return thru;
        }

        public EhisStudentDataType getType() {
            return type;
        }
    }
}
