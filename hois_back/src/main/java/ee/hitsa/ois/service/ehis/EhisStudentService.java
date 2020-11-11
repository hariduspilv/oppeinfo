package ee.hitsa.ois.service.ehis;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsStringList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.datatype.XMLGregorianCalendar;

import ee.hitsa.ois.service.StudentResultHigherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.concurrent.AsyncMemoryManager;
import ee.hitsa.ois.concurrent.AsyncRequest;
import ee.hitsa.ois.concurrent.WrapperCallable;
import ee.hitsa.ois.concurrent.request.EhisAsyncRequest;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationFormalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalSubjectOrModule;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.domain.student.StudentLanguages;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.ApelInformalType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.DocumentStatus;
import ee.hitsa.ois.enums.EhisStudentDataType;
import ee.hitsa.ois.enums.FinSpecific;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.enums.FormType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.enums.StudyForm;
import ee.hitsa.ois.enums.StudyLanguage;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisStudentForm;
import ee.hitsa.ois.web.dto.EhisStudentReport;
import ee.hitsa.ois.web.dto.ForeignStudentDto;
import ee.hois.xroad.ehis.generated.KhlErivajadusedArr;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlKursuseMuutus;
import ee.hois.xroad.ehis.generated.KhlMuudAndmedMuutmine;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppekavaTaitmine;
import ee.hois.xroad.ehis.generated.KhlVOTA;
import ee.hois.xroad.ehis.generated.KhlVOTAArr;
import ee.hois.xroad.ehis.generated.KhlVoorkeeledArr;

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
    private static final String SQL_WHERE_CURRICULUM_JOINTMENTOR = "s.type_code != 'OPPUR_K' "
            + "and exists (select 1 from curriculum_version ch_cv "
            + "join curriculum ch_c on ch_c.id = ch_cv.curriculum_id "
            + "join school ch_sc on ch_sc.id = s.school_id "
            + "join classifier ch_ehis on ch_ehis.code = ch_sc.ehis_school_code "
            + "where ch_cv.id = s.curriculum_version_id and ch_c.is_joint and ch_c.joint_mentor_code is not null and ch_ehis.code != ch_c.joint_mentor_code) ";
    
    @Autowired
    private EhisDirectiveStudentService ehisDirectiveStudentService;
    @Autowired
    private StudentResultHigherService studentResultHigherService;
    
    public Optional<ExportStudentsRequest> findOverlappedActiveExportStudentsRequest(HoisUserDetails hoisUser, EhisStudentForm ehisStudentForm, ExportStudentsRequest origin) {
        Optional<AsyncRequest<?>> optAsyncRequest = AsyncMemoryManager.findAny(AsyncMemoryManager.EHIS_STUDENT, hoisUser.getSchoolId(), (requestsByHash) -> {
            return requestsByHash.values().stream().filter(request -> {
                if (request instanceof ExportStudentsRequest) {
                    ExportStudentsRequest castedRequest = (ExportStudentsRequest) request;
                    if (castedRequest.isDone() || castedRequest.equals(origin) || !castedRequest.getType().equals(ehisStudentForm.getDataType())) {
                        return false;
                    }
                    return ((castedRequest.getFrom() == null && ehisStudentForm.getThru() == null) || !castedRequest.getFrom().isAfter(ehisStudentForm.getThru()))
                            && ((castedRequest.getThru() == null && ehisStudentForm.getFrom() == null ) || !castedRequest.getThru().isBefore(ehisStudentForm.getFrom()));
                }
                return false;
            }).findAny().orElse(null);
        });
        
        if (optAsyncRequest.isPresent()) {
            return Optional.of((ExportStudentsRequest) optAsyncRequest.get());
        }
        return Optional.empty();
    }
    
    public Optional<ExportStudentsRequest> findOverlappedActiveExportStudentsRequest(HoisUserDetails hoisUser, EhisStudentForm ehisStudentForm) {
        return findOverlappedActiveExportStudentsRequest(hoisUser, ehisStudentForm, null);
    }
    
    public ExportStudentsRequest createRequest(HoisUserDetails hoisUser, String hash, EhisStudentForm form) {
        Optional<ExportStudentsRequest> overlapped = findOverlappedActiveExportStudentsRequest(hoisUser, form);
        if (overlapped.isPresent() && !overlapped.get().isDone()) {
            overlapped.get().cancel(hoisUser, true);
        }
        return new ExportStudentsRequest(new WrapperCallable<Queue<? extends EhisStudentReport>>() {
            
            private AtomicInteger max = new AtomicInteger();

            @Override
            public Queue<? extends EhisStudentReport> wrapperCall() throws InterruptedException {
                return exportStudents(hoisUser.getSchoolId(), form, getWrapper(), max);
            }

            @Override
            public float getProgress() {
                if (max.get() == 0) {
                    return 0;
                }
                return getWrapper().get().size() / (float) max.get();
            }

            @Override
            public String getMessage() {
                return null;
            }
        }, hash, hoisUser, form.getFrom(), form.getThru(), form.getDataType());
    }
    
    public Queue<? extends EhisStudentReport> exportStudents(Long schoolId, EhisStudentForm ehisStudentForm,
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        switch (ehisStudentForm.getDataType()) {
        case COURSE_CHANGE: return courseChange(schoolId, ehisStudentForm, wrapper, maxRequests);
        case CURRICULA_FULFILMENT: return curriculumFulfillment(schoolId, wrapper, maxRequests);
        case DORMITORY: return dormitoryChanges(schoolId, ehisStudentForm, wrapper, maxRequests);
        case FOREIGN_STUDY: return foreignStudy(schoolId, ehisStudentForm, wrapper, maxRequests);
        // for external student, only graduation is sent to ehis
        case GRADUATION: return graduation(schoolId, ehisStudentForm, wrapper, maxRequests);
        case DUPLICATE: return duplicate(schoolId, ehisStudentForm, wrapper, maxRequests);
        case VOTA: return vota(schoolId, ehisStudentForm, wrapper, maxRequests);
        case SPECIAL_NEEDS: return specialNeeds(schoolId, ehisStudentForm, wrapper, maxRequests);
        case GUEST_STUDENTS: return guestStudents(schoolId, ehisStudentForm, wrapper, maxRequests);
        default: throw new AssertionFailedException("Unknown datatype");
        }
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
            try {
                Thread.sleep(0);
                WsEhisStudentLog log = courseChange(entry.getKey(), entry.getValue().getChanged(), entry.getValue().getNewCourse());
                reports.add(new EhisStudentReport.Course(entry.getKey(), log, entry.getValue().getNewCourse(), entry.getValue().getChanged()));
                return false;
            } catch (@SuppressWarnings("unused") InterruptedException e) { }
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
                    boolean existsOtherSubject =  StreamUtil.nullSafeList(record.getInformalExperiences()).stream()
                            .anyMatch(ie -> ApelInformalType.VOTA_INFORMAAL_LIIK_M.name().equals(EntityUtil.getCode(ie.getType())));
                    // informal learning 'other subject' is counted as formal learning
                    boolean formalLearning = Boolean.TRUE.equals(record.getIsFormalLearning()) || existsOtherSubject;

                    BigInteger learningType = formalLearning ? FORMAL_LEARNING_TYPE : INFORMAL_LEARNING_TYPE;
                    if(Boolean.TRUE.equals(record.getIsFormalLearning())) {
                        List<ApelApplicationFormalSubjectOrModule> transferredSorms = StreamUtil.toFilteredList(
                                sorm -> Boolean.TRUE.equals(sorm.getTransfer()), record.getFormalSubjectsOrModules());
                        for(ApelApplicationFormalSubjectOrModule sorm : transferredSorms) {
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
                        List<ApelApplicationInformalSubjectOrModule> transferredSorms = StreamUtil.toFilteredList(
                                sorm -> Boolean.TRUE.equals(sorm.getTransfer()), record.getInformalSubjectsOrModules());
                        for(ApelApplicationInformalSubjectOrModule sorm : transferredSorms) {
                            BigDecimal credits;
                            if(vocational) {
                                if(sorm.getCurriculumVersionOmoduleTheme() != null) {
                                    credits = sorm.getCurriculumVersionOmoduleTheme().getCredits();
                                } else {
                                    credits = sorm.getCurriculumVersionOmodule().getCurriculumModule().getCredits();
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
                + " join diploma_supplement sup on sup.id = dsf.diploma_supplement_id"
                + " where dsf.diploma_supplement_id = ?1 and f.type_code in (?3) and (dsf.is_english is not true)"
                + " and case"
                    + " when sup.status_code = ?4 then f.status_code = ?5"
                    + " else f.status_code = ?2 end"
                + " order by f.numeral")
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .setParameter(3, Arrays.asList(FormType.LOPUBLANKETT_HINL.name(), FormType.LOPUBLANKETT_S.name()))
                .setParameter(4, DocumentStatus.LOPUDOK_STAATUS_C.name())
                .setParameter(5, FormStatus.LOPUBLANKETT_STAATUS_R.name());
        Query extraQueryEn = em.createNativeQuery("select f.full_code"
                + " from form f"
                + " join diploma_supplement_form dsf on dsf.form_id = f.id"
                + " join diploma_supplement sup on sup.id = dsf.diploma_supplement_id"
                + " where dsf.diploma_supplement_id = ?1 and f.type_code in (?3) and (dsf.is_english is true)"
                + " and case"
                    + " when sup.status_en_code = ?4 then f.status_code = ?5"
                    + " else f.status_code = ?2 end"
                + " order by f.numeral")
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .setParameter(3, Arrays.asList(FormType.LOPUBLANKETT_S.name()))
                .setParameter(4, DocumentStatus.LOPUDOK_STAATUS_C.name())
                .setParameter(5, FormStatus.LOPUBLANKETT_STAATUS_R.name());
        List<?> result = em.createNativeQuery("select ds.id, dip_f.full_code as dip_code, sup_f.full_code as sup_code, sup.id as sup_id,"
                + " (select sup_f_en.full_code from diploma_supplement_form dsf_en"
                + " join form sup_f_en on sup_f_en.id = dsf_en.form_id"
                + " where dsf_en.diploma_supplement_id = sup.id and dsf_en.is_english"
                + " and sup.status_en_code != ?6 and case when sup.status_en_code = ?11 then sup_f.status_code = ?12 else sup_f_en.status_code = ?7 end"
                + " and sup_f_en.type_code = ?9 ) as sup_en_code"
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
                + " and dip.status_code != ?6 and case when dip.status_code = ?11 then dip_f.status_code = ?12 else dip_f.status_code = ?7 end"
                + " and sup.status_code != ?6 and case when sup.status_code = ?11 then sup_f.status_code = ?12 else sup_f.status_code = ?7 end"
                // Only non-duplicate rows
                + " and dip.is_duplicate is not true and sup.is_duplicate is not true and sup.is_duplicate_en is not true"
                + " and sup_f.type_code in (?8) and s.type_code != ?10"
                + " and not (" + SQL_WHERE_CURRICULUM_JOINTMENTOR + ")")
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
                .setParameter(11, DocumentStatus.LOPUDOK_STAATUS_C.name())
                .setParameter(12, FormStatus.LOPUBLANKETT_STAATUS_R.name())
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
            if (directiveStudent.getStudent() != null) {
                if (StudentUtil.isExternal(directiveStudent.getStudent())) {
                    ehisDirectiveStudentService.admissionMatriculation(directiveStudent, StudyForm.OPPEVORM_E, FinSpecific.FINTAPSUSTUS_X, StudyLanguage.OPPEKEEL_E);
                }
            }
            if (!directiveStudent.getStudent().getFinalThesis().isEmpty()
                    && CurriculumUtil.isMagisterOrDoctoralOrIntegratedStudy(
                            directiveStudent.getStudent().getCurriculumVersion().getCurriculum())) {
                WsEhisStudentLog logFinalThesis = ehisDirectiveStudentService.graduationFinalThesis(directiveStudent);
                if (logFinalThesis.getHasOtherErrors().booleanValue() || logFinalThesis.getHasXteeErrors().booleanValue()) {
                    graduations.add(new EhisStudentReport.Graduation(directiveStudent, logFinalThesis, docNr, academicNr, extraNr, academicNrEn, extraNrEn));
                    continue;
                }
            }
            WsEhisStudentLog log = ehisDirectiveStudentService.graduation(directiveStudent, docNr, academicNr, extraNr, academicNrEn, extraNrEn);
            graduations.add(new EhisStudentReport.Graduation(directiveStudent, log, docNr, academicNr, extraNr, academicNrEn, extraNrEn));
        }
        return graduations;
    }

    private Queue<? extends EhisStudentReport> duplicate(Long schoolId, EhisStudentForm ehisStudentForm,
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        Queue<EhisStudentReport.Graduation> duplications = new ConcurrentLinkedQueue<>();
        wrapper.set(duplications);
        Query diplomaFormQuery = em.createNativeQuery("select f.full_code"
                + " from diploma dip"
                + " join form f on f.id = dip.form_id"
                + " where dip.id = ?1 and f.status_code = ?2")
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name());
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
        List<?> result = em.createNativeQuery("select ds.id, coalesce(dip.dip_id, sup.dip_id, sup_en.dip_id) dip_send"
                + ", sup.sup_id sup_send, sup.form sup_form, sup_en.sup_id sup_en_send, sup_en.form sup_en_form"
                + " from directive_student ds"
                + " join student s on s.id = ds.student_id"
                + " join directive d on d.id = ds.directive_id"
                + " left join ("
                    + " select"
                        + " dip.diploma_id as def_dip,"
                        + " (array_remove(array_agg(dip.id order by dip.id desc), null))[1] as dip_id"
                    + " from diploma dip"
                    + " join form f on f.id = dip.form_id"
                    + " where dip.status_code not in ?1 and f.status_code = ?8"
                    + " group by dip.diploma_id"
                + " ) dip on dip.def_dip = ds.diploma_id"
                + " left join ("
                    + " select"
                        + " sup.diploma_supplement_id as def_sup,"
                        + " (array_remove(array_agg(sup.id order by sup.id desc), null))[1] as sup_id,"
                        + " (array_remove(array_agg(f.full_code order by sup.id desc), null))[1] as form,"
                        + " (array_remove(array_agg(sup.diploma_id order by sup.id desc), null))[1] as dip_id"
                    + " from diploma_supplement sup"
                    + " join diploma_supplement_form dsf on dsf.diploma_supplement_id = sup.id"
                    + " join form f on f.id = dsf.form_id"
                    + " where sup.status_code not in ?1"
                        + " and f.status_code = ?8"
                        + " and dsf.is_english is not true"
                        + " and f.type_code in (?9)"
                    + " group by sup.diploma_supplement_id"
                + " ) sup on sup.def_sup = ds.diploma_supplement_id"
                + " left join ("
                    + " select"
                        + " sup.diploma_supplement_id as def_sup,"
                        + " (array_remove(array_agg(sup.id order by sup.id desc), null))[1] as sup_id,"
                        + " (array_remove(array_agg(f.full_code order by sup.id desc), null))[1] as form,"
                        + " (array_remove(array_agg(sup.diploma_id order by sup.id desc), null))[1] as dip_id"
                    + " from diploma_supplement sup"
                    + " join diploma_supplement_form dsf on dsf.diploma_supplement_id = sup.id"
                    + " join form f on f.id = dsf.form_id"
                    + " where sup.status_en_code not in ?1"
                        + " and f.status_code = ?8"
                        + " and dsf.is_english is true"
                        + " and f.type_code in (?10)"
                    + " group by sup.diploma_supplement_id"
                + " ) sup_en on sup_en.def_sup = ds.diploma_supplement_en_id "
                + " where d.school_id = ?2 and ds.canceled = false"
                + " and d.type_code = ?3"
                + " and d.status_code = ?4"
                + " and d.confirm_date >= ?5 and d.confirm_date <= ?6"
                + " and coalesce(dip.dip_id, sup.dip_id, sup_en.dip_id) is not null"
                + " and (ds.diploma_supplement_id is null or sup.sup_id is not null)"
                + " and (ds.diploma_supplement_en_id is null or sup_en.sup_id is not null)"
                + " and (ds.diploma_id is null or sup.sup_id is not null or sup_en.sup_id is not null)"
                + " and s.type_code not in (?7)"
                + " and not (" + SQL_WHERE_CURRICULUM_JOINTMENTOR + ")"
                + " order by d.confirm_date asc")
                .setParameter(1, EnumUtil.toNameList(DocumentStatus.LOPUDOK_STAATUS_K, DocumentStatus.LOPUDOK_STAATUS_C))
                .setParameter(2, schoolId)
                .setParameter(3, DirectiveType.KASKKIRI_DUPLIKAAT.name())
                .setParameter(4, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter(5, JpaQueryUtil.parameterAsTimestamp(ehisStudentForm.getFrom()))
                .setParameter(6, JpaQueryUtil.parameterAsTimestamp(ehisStudentForm.getThru()))
                .setParameter(7, EnumUtil.toNameList(StudentType.OPPUR_K, StudentType.OPPUR_E))
                .setParameter(8, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .setParameter(9, EnumUtil.toNameList(FormType.LOPUBLANKETT_HIN, FormType.LOPUBLANKETT_R))
                .setParameter(10, EnumUtil.toNameList(FormType.LOPUBLANKETT_DS))
                .getResultList();
        maxRequests.set(result.size());
        for (Object r : result) {
            if (Thread.interrupted()) { // Break in case if operation has been cancelled
                break;
            }
            DirectiveStudent directiveStudent = em.getReference(DirectiveStudent.class, resultAsLong(r, 0));
            Long dipId = resultAsLong(r, 1);
            Long supId = resultAsLong(r, 2);
            String supForm = resultAsString(r, 3);
            Long supEnId = resultAsLong(r, 4);
            String supEnForm = resultAsString(r, 5);
            List<?> foundForm = diplomaFormQuery.setParameter(1, dipId).getResultList();
            if (foundForm.isEmpty()) {
                continue;
            }
            String docNr = foundForm.stream().map(f -> resultAsString(f, 0)).findAny().get();
            List<?> extraResult = supId == null ? Collections.emptyList() : extraQuery
                    .setParameter(1, supId)
                    .getResultList();
            List<?> extraResultEn = supEnId == null ? Collections.emptyList() : extraQueryEn
                    .setParameter(1, supEnId)
                    .getResultList();
            List<String> extraNr = StreamUtil.toMappedList(er -> resultAsString(er, 0), extraResult);
            List<String> extraNrEn = StreamUtil.toMappedList(er -> resultAsString(er, 0), extraResultEn);
            WsEhisStudentLog log = ehisDirectiveStudentService.duplicateChanged(directiveStudent, docNr, supForm, extraNr, supEnForm, extraNrEn);
            duplications.add(new EhisStudentReport.Graduation(directiveStudent, log, docNr, supForm, extraNr, supEnForm, extraNrEn));
        }
        return duplications;
    }

    private Queue<EhisStudentReport.ForeignStudy> foreignStudy(Long schoolId, EhisStudentForm ehisStudentForm, 
            AtomicReference<Queue<? extends EhisStudentReport>> wrapper, AtomicInteger maxRequests) {
        Queue<EhisStudentReport.ForeignStudy> foreignStudies = new ConcurrentLinkedQueue<>();
        wrapper.set(foreignStudies);
        List<ForeignStudentDto> students = findForeignStudents(schoolId, ehisStudentForm);
        maxRequests.set(students.size());
        for (ForeignStudentDto foreignStudent : students) {
            if (Thread.interrupted()) { // Break in case if operation has been cancelled
                break;
            }
            DirectiveStudent directiveStudent = em.getReference(DirectiveStudent.class, foreignStudent.getId());
            WsEhisStudentLog log = ehisDirectiveStudentService.foreignStudy(directiveStudent, foreignStudent);
            if (!(Boolean.TRUE.equals(log.getHasXteeErrors()) || Boolean.TRUE.equals(log.getHasOtherErrors()))) {
                String stringNominal = foreignStudent.getNominalStudyExtension().toString();
                setEhisSentApelApplication(foreignStudent.getApelApplicationIdsAndNominal()
                        .entrySet().stream()
                        .filter(entry -> stringNominal.compareTo(entry.getValue()) >= 0)
                        .map(Entry::getKey)
                        .collect(Collectors.toSet()));
            }
            foreignStudies.add(new EhisStudentReport.ForeignStudy(directiveStudent, log, foreignStudent));
        }
        return foreignStudies;
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
            BigDecimal percentage;
            Boolean lastPeriod = Boolean.FALSE;
            if (StudentUtil.isHigher(student)) {
                BigDecimal curriculumCredits = student.getCurriculumVersion().getCurriculum().getCredits();
                if (curriculumCredits == null) {
                    continue;
                }
                percentage = studentResultHigherService.getConsideredCurriculumCompletion(student,
                        curriculumCredits.add(completion.getStudyBacklog()));
            } else {
                if (points == null) {
                    points = completion.getCreditsLastStudyPeriod();
                    lastPeriod = Boolean.TRUE;
                }
                if (points == null) {
                    continue;
                }
                percentage = StudentUtil.getCurriculumCompletion(points, student);
            }
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
        return em.createQuery("select s from Student s where s.school.id = ?1 and s.status.code in ?2 and s.type.code not in (?3) "
                + "and not (s.curriculumVersion.curriculum.joint = true and s.curriculumVersion.curriculum.jointMentor.code is not null and "
                + "s.curriculumVersion.curriculum.jointMentor.code != s.school.ehisSchool.code)", Student.class)
                .setParameter(1, schoolId)
                .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
                .setParameter(3, EnumUtil.toNameList(StudentType.OPPUR_K, StudentType.OPPUR_E))
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
                + "and ssn_cl.ehis_value is not null and s.type_code not in (?8) "
                + "and not (" + SQL_WHERE_CURRICULUM_JOINTMENTOR + ") "
                + "group by s.id")
        .setParameter(1, schoolId)
        .setParameter(2, DirectiveType.KASKKIRI_TUGI.name())
        .setParameter(3, DirectiveType.KASKKIRI_TUGILOPP.name())
        .setParameter(4, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
        .setParameter(5, JpaQueryUtil.parameterAsTimestamp(DateUtils.firstMomentOfDay(ehisStudentForm.getFrom())))
        .setParameter(6, JpaQueryUtil.parameterAsTimestamp(DateUtils.lastMomentOfDay(ehisStudentForm.getThru())))
        .setParameter(7, StudentStatus.STUDENT_STATUS_ACTIVE)
        .setParameter(8, EnumUtil.toNameList(StudentType.OPPUR_K, StudentType.OPPUR_E))
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
                + "where s.school_id = ?1 and sgyf.is_transfered and s.status_code in ?2 and sgyf.changed >= ?3 and sgyf.changed <= ?4 "
                + "and s.type_code not in (?5) and not (" + SQL_WHERE_CURRICULUM_JOINTMENTOR + ") "
                + "order by sgyf.changed asc ")
                .setParameter(1, schoolId)
                .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
                .setParameter(3, JpaQueryUtil.parameterAsTimestamp(DateUtils.firstMomentOfDay(ehisStudentForm.getFrom())))
                .setParameter(4, JpaQueryUtil.parameterAsTimestamp(DateUtils.lastMomentOfDay(ehisStudentForm.getThru())))
                .setParameter(5, EnumUtil.toNameList(StudentType.OPPUR_K, StudentType.OPPUR_E))
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
                + " and sh.inserted >= ?3 and sh.inserted <= ?4 and s.type_code not in (?5) and not (" + SQL_WHERE_CURRICULUM_JOINTMENTOR + "))"
                + " x where x.first_dormitory_code != x.last_dormitory_code")
                .setParameter(1, schoolId)
                .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
                .setParameter(3, JpaQueryUtil.parameterAsTimestamp(DateUtils.firstMomentOfDay(criteria.getFrom())))
                .setParameter(4, JpaQueryUtil.parameterAsTimestamp(DateUtils.lastMomentOfDay(criteria.getThru())))
                .setParameter(5, EnumUtil.toNameList(StudentType.OPPUR_K, StudentType.OPPUR_E))
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
    
    public JpaNativeQueryBuilder findForeignStudentsQueryBuilder() {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds "
                + "join directive d on ds.directive_id = d.id "
                + "left join study_period spEnd on spEnd.id = ds.study_period_end_id "
                + "left join study_period spStart on spStart.id = ds.study_period_start_id "
                // get curriculum version for "is higher" check
                + "join student s on s.id = ds.student_id "
                + "join curriculum_version cv on cv.id = s.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id "
                // Valis disruption
                + "left join (select ds1.id, ds1.start_date, ds1.student_id, ds1.directive_student_id "
                            + "from directive_student ds1 "
                            + "join directive d1 on ds1.directive_id = d1.id "
                            + "where d1.type_code = :DIRECTIVE_VALISKATK and "
                            + "d1.status_code = :DIRECTIVE_CONFIRMED) as VALISKATK "
                            + "on VALISKATK.directive_student_id = ds.id "
//                // nominal study and eap
                + "left join (select aa.id, aafsm.credits, coalesce(nominal_cl.value, '0') as nominal, aafsm.grade_date, aa.confirmed, aa.student_id, aa.is_ehis_sent "
                            + "from apel_application aa "
                            + "join apel_application_record aar on aar.apel_application_id = aa.id "
                            + "join apel_application_formal_subject_or_module aafsm on aafsm.apel_application_record_id = aar.id "
                            + "join apel_school aschool on aschool.id = aafsm.apel_school_id "
                            + "left join classifier nominal_cl on nominal_cl.code = aa.nominal_type_code "
                            + "where aa.status_code = :APEL_APPLICATION_STATUS and aafsm.transfer = true and aschool.country_code != :estonia) aa on "
                            + "aa.confirmed > coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, spEnd.end_date, ds.end_date) "
                            + "and aa.grade_date between coalesce(spStart.start_date, ds.start_date) "
                                + "and coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, spEnd.end_date, ds.end_date) "
                            + "and aa.student_id = ds.student_id ");
        qb.requiredCriteria("ds.country_code != :estonia", "estonia", ClassifierUtil.COUNTRY_ESTONIA);
        qb.parameter("APEL_APPLICATION_STATUS", ApelApplicationStatus.VOTA_STAATUS_C.name());
        qb.parameter("DIRECTIVE_VALISKATK", DirectiveType.KASKKIRI_VALISKATK.name());
        qb.parameter("DIRECTIVE_CONFIRMED", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.requiredCriteria("d.type_code = :directiveTypeValis", "directiveTypeValis", DirectiveType.KASKKIRI_VALIS.name());
        qb.filter("d.status_code = :DIRECTIVE_CONFIRMED");
        qb.filter("ds.canceled = false");
        qb.filter("c.is_higher");
        qb.groupBy("ds.id, VALISKATK.id");
        return qb;
    }

    /**
     * EHIS only wants foreign students whos study end is later than 01.04.2014
     * @param schoolId
     * @param criteria
     * @return
     */
    private List<ForeignStudentDto> findForeignStudents(Long schoolId, EhisStudentForm criteria) {
        LocalDate minDate = LocalDate.parse("2014-04-01");
        if (criteria.getFrom().isBefore(minDate)) {
            criteria.setFrom(minDate);
        }
        JpaNativeQueryBuilder qb = findForeignStudentsQueryBuilder();
        qb.requiredCriteria("d.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria(
                "coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, spEnd.end_date, ds.end_date) >= :endFrom",
                "endFrom", criteria.getFrom());
        qb.requiredCriteria(
                "coalesce(case when VALISKATK.start_date is not null then VALISKATK.start_date - interval '1 day' else null end, spEnd.end_date, ds.end_date) <= :endThru",
                "endThru", criteria.getThru());
        qb.filter("not (" + SQL_WHERE_CURRICULUM_JOINTMENTOR + ")");
        return JpaQueryUtil.pagingResult(qb, "coalesce(VALISKATK.id, ds.id) as directiveId, " // In case if we have VALISKATK then it should process VALISKATK directive, not just VALIS.
                // Max nominal study
                + "coalesce(max(case when aa.is_ehis_sent then aa.nominal else '0' end), '0') as nominal_sent, "
                + "coalesce(max(aa.nominal), '0') as nominal_all, "
                 // Received EAP (points)
                + "sum(aa.credits) as credits, "
                + "string_agg(aa.id\\:\\:character varying, ',') as apelApplications, "
                + "string_agg(aa.nominal, ',') as apelApplications_nominal " // sets is_ehis_sent for these aa where nominal <= sent nominal
        , em, new PageRequest(0, Integer.MAX_VALUE)).map(r -> new ForeignStudentDto(r)).getContent();
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
    
    public void sendStudentLanguages(Job job)    {
        Student student = em.getReference(Student.class, EntityUtil.getId(job.getStudent()));
        if (student.getStudentLanguages().isEmpty() || !StudentUtil.isActive(student)) {
            return;
        }
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlVoorkeeledArr khlVoorkeeledArr = new KhlVoorkeeledArr();
        khlVoorkeeledArr.setMuutusKp(date(job.getJobTime().toLocalDate()));
        for (StudentLanguages language : student.getStudentLanguages()) {
            khlVoorkeeledArr.getKlVoorkeel().add(ehisValue(language.getForeignLang()));
        }

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setVoorkeeled(khlVoorkeeledArr);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(student, khlOppeasutusList);
    }

    private List<ApelApplication> findApelApplications(Long schoolId, EhisStudentForm criteria) {
        return em.createQuery("select a from ApelApplication a where a.school.id = ?1 and a.confirmed >= ?2 and a.confirmed <= ?3 "
                + "and a.student.type.code not in (?4) and not ( "
                + "a.student.curriculumVersion.curriculum.joint = true and "
                + "a.student.curriculumVersion.curriculum.jointMentor.code is not null and "
                + "a.student.curriculumVersion.curriculum.jointMentor.code != a.student.school.ehisSchool.code)", ApelApplication.class)
                .setParameter(1, schoolId)
                .setParameter(2, DateUtils.firstMomentOfDay(criteria.getFrom()))
                .setParameter(3, DateUtils.lastMomentOfDay(criteria.getThru()))
                .setParameter(4, EnumUtil.toNameList(StudentType.OPPUR_K, StudentType.OPPUR_E))
                .getResultList();
    }
    
    private void setEhisSentApelApplication(Set<Long> applicationIds) {
        if (applicationIds == null || applicationIds.isEmpty()) {
            return;
        }
        em.createQuery("select a from ApelApplication a where a.id in ?1", ApelApplication.class)
            .setParameter(1, applicationIds)
            .getResultList()
            .forEach(apelApplication -> apelApplication.setIsEhisSent(Boolean.TRUE));
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
    public static class ExportStudentsRequest extends EhisAsyncRequest<Queue<? extends EhisStudentReport>> {
        
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
