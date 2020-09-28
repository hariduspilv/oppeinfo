package ee.hitsa.ois.service.ehis;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import ee.hitsa.ois.domain.schoolcapacity.SchoolCapacityType;
import ee.hitsa.ois.service.SchoolCapacityTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.concurrent.AsyncMemoryManager;
import ee.hitsa.ois.concurrent.AsyncRequest;
import ee.hitsa.ois.concurrent.WrapperCallable;
import ee.hitsa.ois.concurrent.request.EhisAsyncRequest;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.WsEhisTeacherLog;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumStudyLanguage;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherContinuingEducation;
import ee.hitsa.ois.domain.teacher.TeacherMobility;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.domain.teacher.TeacherQualification;
import ee.hitsa.ois.domain.timetable.JournalCapacity;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.domain.timetable.JournalTeacherCapacity;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudyLanguage;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ehis.EhisTeacherExportForm;
import ee.hitsa.ois.web.dto.EhisTeacherExportResultDto;
import ee.hois.soap.LogContext;
import ee.hois.xroad.ehis.generated.Oppeaine;
import ee.hois.xroad.ehis.generated.Oppeasutus;
import ee.hois.xroad.ehis.generated.OppeasutusList;
import ee.hois.xroad.ehis.generated.Oppejoud;
import ee.hois.xroad.ehis.generated.OppejoudAmetikoht;
import ee.hois.xroad.ehis.generated.OppejoudIsikuandmed;
import ee.hois.xroad.ehis.generated.OppejoudKvalifikatsioon;
import ee.hois.xroad.ehis.generated.OppejoudList;
import ee.hois.xroad.ehis.generated.OppejoudLyhiajalineMobiilsus;
import ee.hois.xroad.ehis.generated.Pedagoog;
import ee.hois.xroad.ehis.generated.PedagoogAine;
import ee.hois.xroad.ehis.generated.PedagoogAmetikohtType;
import ee.hois.xroad.ehis.generated.PedagoogTaiendkoolitus;
import ee.hois.xroad.ehis.generated.PedagoogTasemekoolitus;
import ee.hois.xroad.ehis.service.EhisResponse;
import ee.hois.xroad.helpers.XRoadHeaderV4;

@Transactional
@Service
public class EhisTeacherExportService extends EhisService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String LAE_OPPEJOUD_SERVICE_CODE = "laeOppejoud";
    private static final String LAE_PEDAGOOGID_SERVICE_CODE = "laePedagoogid";
    private static final String SUBSYSTEM_CODE = "ehis";
    public static final String LAE_OPPEJOUD_SERVICE = String.format("%s.%s", SUBSYSTEM_CODE, LAE_OPPEJOUD_SERVICE_CODE);
    public static final String LAE_PEDAGOOGID_SERVICE = String.format("%s.%s", SUBSYSTEM_CODE, LAE_PEDAGOOGID_SERVICE_CODE);

    private static final String EHIS_TOOSUHE_MUU = "EHIS_TOOSUHE_MUU";
    private static final String EHIS_KVALIFIKATSIOON_NIMI_MUU = "EHIS_KVALIFIKATSIOON_NIMI_MUU";
    private static final String EHIS_AMETIKOHT_MUU = "EHIS_AMETIKOHT_MUU";
    
    private static final LocalDate DONT_SEND_QUALIFICATION_AFTER = LocalDate.of(2004, 12, 31);

    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private SchoolCapacityTypeService schoolCapacityTypeService;
    
    public Optional<ExportTeacherRequest> findOverlappedActiveExportTeacherRequest(HoisUserDetails hoisUser, EhisTeacherExportForm form, ExportTeacherRequest origin) {
        Optional<AsyncRequest<?>> optAsyncRequest = AsyncMemoryManager.findAny(AsyncMemoryManager.EHIS_TEACHER, hoisUser.getSchoolId(), (requestsByHash) -> {
            return requestsByHash.values().stream().filter(request -> {
                if (request instanceof ExportTeacherRequest) {
                    ExportTeacherRequest castedRequest = (ExportTeacherRequest) request;
                    if (castedRequest.isDone() || castedRequest.equals(origin)) {
                        return false;
                    }
                    return false;
//                  return castedRequest.isAllDates() || form.isAllDates() ? true :  TODO
//                          ((castedRequest.getFrom() == null || form.getChangeDateTo() == null) || !castedRequest.getFrom().isAfter(form.getChangeDateTo()))
//                          && ((castedRequest.getThru() == null || form.getChangeDateFrom() == null ) || !castedRequest.getThru().isBefore(form.getChangeDateFrom()));
                }
                return false;
            }).findAny().orElse(null);
        });
        
        if (optAsyncRequest.isPresent()) {
            return Optional.of((ExportTeacherRequest) optAsyncRequest.get());
        }
        return Optional.empty();
    }
    
    public Optional<ExportTeacherRequest> findOverlappedActiveExportTeacherRequest(HoisUserDetails hoisUser, EhisTeacherExportForm ehisStudentForm) {
        return findOverlappedActiveExportTeacherRequest(hoisUser, ehisStudentForm, null);
    }
    
    public ExportTeacherRequest createRequest(HoisUserDetails hoisUser, boolean higher, EhisTeacherExportForm form, String key) {
        Optional<ExportTeacherRequest> overlappedRequest = findOverlappedActiveExportTeacherRequest(hoisUser, form);
        if (overlappedRequest.isPresent() && !overlappedRequest.get().isDone()) {
            overlappedRequest.get().cancel(hoisUser, true);
        }
        return new ExportTeacherRequest(new WrapperCallable<Queue<EhisTeacherExportResultDto>>() {
            
            private AtomicInteger max = new AtomicInteger();
            
            @Override
            public Queue<EhisTeacherExportResultDto> wrapperCall() {
                return exportToEhis(hoisUser.getSchoolId(), higher, form, getWrapper(), max);
            }

            @Override
            public float getProgress() {
                if (max.get() == 0) {
                    return 0;
                }
                return getWrapper().get().size() / (float) max.get();
            }
            
        }, key, hoisUser, form);
    }
    
    /**
     * Exports teachers to ehis based on form data
     * @param schoolId
     * @param higher
     * @param form
     * @param max 
     * @param wrapper 
     * @return
     */
    public Queue<EhisTeacherExportResultDto> exportToEhis(Long schoolId, boolean higher, EhisTeacherExportForm form,
            AtomicReference<Queue<EhisTeacherExportResultDto>> wrapper, AtomicInteger max) {
        Set<Long> schoolTeachers = form.isAllDates() ? activeTeacherIds(schoolId, higher) : changedTeacherIds(schoolId, higher, form);
        List<RequestObject> requests = createRequests(schoolId, schoolTeachers, higher, form);
        max.set(requests.size());
        wrapper.set(new ConcurrentLinkedQueue<EhisTeacherExportResultDto>());
        return sendRequests(requests, higher, wrapper.get());
    }

    /**
     * Export all data of single teacher to ehis
     * @param teacher
     * @throws ValidationFailedException if export fails
     */
    public void exportToEhis(Teacher teacher) {
        List<Long> teacherId = Collections.singletonList(teacher.getId());
        EhisTeacherExportForm form = new EhisTeacherExportForm();
        form.setSubjectData(true);
        Long schoolId = EntityUtil.getId(teacher.getSchool());

        if(Boolean.TRUE.equals(teacher.getIsHigher())) {
            List<RequestObject> requests = createRequests(schoolId, teacherId, true, form);
            EhisTeacherExportResultDto result = sendRequests(requests, true).peek();
            if(result.isError()) {
                throw new ValidationFailedException(result.getMessage());
            }
        }
        if(Boolean.TRUE.equals(teacher.getIsVocational())) {
            List<RequestObject> requests = createRequests(schoolId, teacherId, false, form);
            EhisTeacherExportResultDto result = sendRequests(requests, false).peek();
            if(result.isError()) {
                throw new ValidationFailedException(result.getMessage());
            }
        }
    }

    private Queue<? extends EhisTeacherExportResultDto> sendRequests(List<RequestObject> requests, boolean higher) {
        return sendRequests(requests, higher, null);
    }

    private Queue<EhisTeacherExportResultDto> sendRequests(List<RequestObject> requests, boolean higher, Queue<EhisTeacherExportResultDto> queue) {
        Queue<EhisTeacherExportResultDto> resultList = queue != null ? queue : new ConcurrentLinkedQueue<>();
        for (RequestObject request : requests) {
            LogContext queryLog;
            WsEhisTeacherLog wsEhisTeacherLog = new WsEhisTeacherLog();
            XRoadHeaderV4 xRoadHeader = getXroadHeader();
            boolean error = false;
            if(request.getError() == null) {
                EhisResponse<List<String>> response;
                if(higher) {
                    response = ehisClient.laeOppejoud(xRoadHeader, request.getOppejoudList());
                } else {
                    xRoadHeader.getService().setServiceCode(LAE_PEDAGOOGID_SERVICE_CODE);
                    response = ehisClient.laePedagoogid(xRoadHeader, request.getOppeasutusList());
                }
                queryLog = response.getLog();
                if(!response.hasError()) {
                    error = messageHasError(response.getTeade()) || resultHasError(response.getResult());
                    wsEhisTeacherLog.setHasOtherErrors(Boolean.valueOf(error));
                    wsEhisTeacherLog.setLogTxt(String.join(";", StreamUtil.nullSafeList(response.getResult())));
                } else {
                    wsEhisTeacherLog.setHasXteeErrors(Boolean.TRUE);
                    wsEhisTeacherLog.setLogTxt(getErrorMsg(queryLog));
                    error = true;
                }
            } else {
                queryLog = xRoadHeader.logContext();
                wsEhisTeacherLog.setHasOtherErrors(Boolean.TRUE);
                wsEhisTeacherLog.setLogTxt(request.getError());
                error = true;
            }
            wsEhisTeacherLog.setTeacher(request.getTeacher());
            wsEhisTeacherLog.setSchool(request.getTeacher().getSchool());
            ehisLogService.insert(queryLog, wsEhisTeacherLog);

            resultList.add(new EhisTeacherExportResultDto(request.getTeacher().getPerson().getFullname(), wsEhisTeacherLog.getLogTxt(), error));
        }
        return resultList;
    }

    private List<RequestObject> createRequests(Long schoolId, Collection<Long> schoolTeachers, boolean higher, EhisTeacherExportForm form) {
        if(schoolTeachers.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> periods = new ArrayList<>();
        if(form.isSubjectData()) {
            if(higher) {
                Long periodId = studyYearService.getCurrentStudyPeriod(schoolId);
                if(periodId != null) {
                    periods.add(periodId);
                }
                periodId = studyYearService.getPreviousStudyPeriod(schoolId);
                if(periodId != null) {
                    periods.add(periodId);
                }
            } else {
                StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
                if(studyYear != null) {
                    periods.add(EntityUtil.getId(studyYear));
                }
            }
        }

        List<RequestObject> result = new ArrayList<>();
        List<TeacherSubject> subjects = higher ? teacherSubjects(schoolTeachers, periods) : teacherModules(schoolTeachers, periods);
        Map<Long, List<TeacherSubject>> subjectByTeacher = subjects.stream().collect(Collectors.groupingBy(TeacherSubject::getTeacherId));
        Map<String, SchoolCapacityType> schoolCapacitiesByTypeCode = schoolCapacityTypeService
                .getSchoolCapacitiesByTypeCode(schoolId, Boolean.TRUE.equals(higher));

        List<Teacher> teachers = em.createQuery("select t from Teacher t where t.id in ?1", Teacher.class)
                .setParameter(1,  schoolTeachers).getResultList();
        for (Teacher teacher : teachers) {
            // for every teacher we make a separate request
            try {
                RequestObject request;
                if(higher) {
                    Oppejoud oppejoud = createOppejoud(teacher, subjectByTeacher.get(EntityUtil.getId(teacher)));
                    OppejoudList oppejoudList = new OppejoudList();
                    oppejoudList.getItem().add(oppejoud);
                    request = new RequestObject(teacher, oppejoudList);
                } else {
                    Oppeasutus oppeasutus = createOppeasutus(teacher, subjectByTeacher.get(EntityUtil.getId(teacher)), schoolCapacitiesByTypeCode);
                    OppeasutusList oppeasutusList = new OppeasutusList();
                    oppeasutusList.getItem().add(oppeasutus);
                    request = new RequestObject(teacher, oppeasutusList);
                }
                result.add(request);
            } catch (Exception e) {
                log.error("Generating request object on teacher with id {} failed :", EntityUtil.getId(teacher), e);
                result.add(new RequestObject(teacher, "Antud õpetaja päringu genereerimine ebaõnnestus"));
            }
        }
        return result;
    }

    private Oppejoud createOppejoud(Teacher teacher, List<TeacherSubject> subjects) {
        Oppejoud oppejoud = new Oppejoud();
        String koolId = ehisValue(teacher.getSchool().getEhisSchool());
        // FIXME strings are allowed values too
        oppejoud.setKoolId(koolId != null ? new BigInteger(koolId) : null);

        Person person = teacher.getPerson();
        if(StringUtils.hasText(person.getIdcode())) {
            oppejoud.setIsikukood(person.getIdcode());
        } else {
            OppejoudIsikuandmed isikuandmed = new OppejoudIsikuandmed();
            isikuandmed.setEesnimi(person.getFirstname());
            isikuandmed.setPerenimi(person.getLastname());
            isikuandmed.setSynniKp(date(person.getBirthdate()));
            isikuandmed.setKlSugu(ehisValue(person.getSex()));
            isikuandmed.setKlKodakondsus(value(person.getCitizenship()));
            oppejoud.setIsikuandmed(isikuandmed);
        }

        oppejoud.setTelefon(teacher.getPhone());
        oppejoud.setEmail(teacher.getEmail());

        addPositionEhis(oppejoud.getAmetikoht(), teacher, subjects);
        addQualifications(oppejoud.getKvalifikatsioon(), teacher);
        addMobilities(oppejoud.getLyhiajalineMobiilsus(), teacher);
        return oppejoud;
    }

    private void addPositionEhis(List<OppejoudAmetikoht> resultList, Teacher teacher, List<TeacherSubject> teacherSubjects) {
        for (TeacherPositionEhis positionEhis : teacher.getTeacherPositionEhis()) {
            if (Boolean.TRUE.equals(positionEhis.getIsVocational())) {
                continue;
            }

            OppejoudAmetikoht ametikoht = new OppejoudAmetikoht();
            ametikoht.setOnOppejoud(yesNo(positionEhis.getIsTeacher()));
            ametikoht.setKlAmetikoht(ehisValue(positionEhis.getPosition()));
            if (EHIS_AMETIKOHT_MUU.equals(code(positionEhis.getPosition()))) {
                ametikoht.setAmetikohtMuu(positionEhis.getPositionSpecificationEt());
            }
            ametikoht.setKlToosuhe(ehisValue(positionEhis.getEmploymentType()));
            if(EHIS_TOOSUHE_MUU.equals(code(positionEhis.getEmploymentType()))) {
                ametikoht.setToosuheMuu(positionEhis.getEmploymentTypeSpecification());
            }
            ametikoht.setKlLepinguLiik(ehisValue(positionEhis.getContractType()));
            ametikoht.setLepingAlgKp(date(positionEhis.getContractStart()));
            ametikoht.setLepingLoppKp(date(positionEhis.getContractEnd()));
            ametikoht.setKoormus(positionEhis.getLoad().doubleValue());
            ametikoht.setLepingOnLopetatud(yesNo(positionEhis.getIsContractEnded()));
            if(positionEhis.getSchoolDepartment() != null) {
                ametikoht.setStruktNimi(positionEhis.getSchoolDepartment().getNameEt());
                ametikoht.setStruktKood(positionEhis.getSchoolDepartment().getCode());
            }
            ametikoht.setToosuheKood(positionEhis.getEmploymentCode());
            ametikoht.setAmetikohtTapsustusEn(positionEhis.getPositionSpecificationEn());
            resultList.add(ametikoht);

            // group curriculums by subject id
            Map<Long, Set<Long>> subjects = StreamUtil.nullSafeList(teacherSubjects).stream().collect(
                    Collectors.groupingBy(TeacherSubject::getSubjectId, Collectors.mapping(TeacherSubject::getCurriculumId, Collectors.toSet())));

            Classifier clDefaultLanguage = em.getReference(Classifier.class, StudyLanguage.OPPEKEEL_E.name());
            for (Map.Entry<Long, Set<Long>> tws : subjects.entrySet()) {
                Oppeaine oppeaine = new Oppeaine();
                Subject subject = em.getReference(Subject.class, tws.getKey());
                oppeaine.setNimetus(subject.getNameEt());
                oppeaine.setKlOppekeel(!subject.getSubjectLanguages().isEmpty()
                        ? subject.getSubjectLanguages().iterator().next().getLanguage().getExtraval2()
                        : clDefaultLanguage.getExtraval2()); // HITSAOIS-639 if not exists then should be "Eesti keel"
                oppeaine.setAineKood(subject.getCode());
                for(Long curriculumId : tws.getValue()) {
                    Curriculum curriculum = em.getReference(Curriculum.class, curriculumId);
                    String merCode = curriculum.getMerCode();
                    if(StringUtils.hasText(merCode)) {
                        // FIXME strings are allowed values too
                        oppeaine.getOkKood().add(new BigInteger(curriculum.getMerCode()));
                    }
                }
                oppeaine.setMaht(subject.getCredits().toString());
                ametikoht.getOppeained().add(oppeaine);
            }
        }
    }

    private static void addQualifications(List<OppejoudKvalifikatsioon> resultList, Teacher teacher) {
        for (TeacherQualification hoisQualification : teacher.getTeacherQualification()) {
            OppejoudKvalifikatsioon oKvalifikatsioon = new OppejoudKvalifikatsioon();
            oKvalifikatsioon.setKlKvalifikatsioon(ehisValue(hoisQualification.getQualification()));
            oKvalifikatsioon.setKlKvalifikatsioonNimetus(ehisValue(hoisQualification.getQualificationName()));
            if (EHIS_KVALIFIKATSIOON_NIMI_MUU.equals(code(hoisQualification.getQualificationName()))) {
                oKvalifikatsioon.setKvalifikatsioonNimetusMuu(hoisQualification.getQualificationOther());
            }
            oKvalifikatsioon.setKlRiik(value2(hoisQualification.getState()));
            LocalDate endDate = hoisQualification.getEndDate();
            if (endDate != null) {
                oKvalifikatsioon.setAasta(BigInteger.valueOf(endDate.getYear()));
            }
            oKvalifikatsioon.setKlEestiOppeasutus(ehisValue(hoisQualification.getSchool()));
            oKvalifikatsioon.setKlEestiOppeasutusEndine(ehisValue(hoisQualification.getExSchool()));
            oKvalifikatsioon.setOppeasutusMuu(hoisQualification.getSchoolOther());
            resultList.add(oKvalifikatsioon);
        }
    }

    private void addMobilities(List<OppejoudLyhiajalineMobiilsus> resultList, Teacher teacher) {
        for (TeacherMobility mob : teacher.getTeacherMobility()) {
            OppejoudLyhiajalineMobiilsus mobiilsus = new OppejoudLyhiajalineMobiilsus();
            mobiilsus.setPerioodiAlgus(date(mob.getStart()));
            mobiilsus.setPerioodiLopp(date(mob.getEnd()));
            mobiilsus.setKlEesmark(ehisValue(mob.getTarget()));
            mobiilsus.setSihtoppeasutus(mob.getSchool());
            mobiilsus.setKlSihtriik(value2(mob.getState()));
            resultList.add(mobiilsus);
        }
    }

    private Oppeasutus createOppeasutus(Teacher teacher, List<TeacherSubject> modules,
                                        Map<String, SchoolCapacityType> schoolCapacitiesByTypeCode) {
        Oppeasutus oppeasutus = new Oppeasutus();
        oppeasutus.setKoolId(ehisValue(teacher.getSchool().getEhisSchool()));

        Person person = teacher.getPerson();
        Pedagoog pedagoog = new Pedagoog();
        pedagoog.setIsikukood(person.getIdcode());
        pedagoog.setKlEmakeel(ehisValue(teacher.getNativeLanguage()));

        for(TeacherPositionEhis position : teacher.getTeacherPositionEhis()) {
            if(!Boolean.TRUE.equals(position.getIsVocational())) {
                continue;
            }

            PedagoogAmetikohtType ametikoht = new PedagoogAmetikohtType();
            ametikoht.setKlAmetikoht(ehisValue(position.getPosition()));
            ametikoht.setKlLepinguLiik(ehisValue(position.getContractType()));
            ametikoht.setLepingAlgKp(date(position.getContractStart()));
            ametikoht.setLepingLoppKp(date(position.getContractEnd()));
            ametikoht.setOnLopetatud(yesNo(position.getIsContractEnded()));
            if(position.getLoad() != null) {
                ametikoht.setKoormus(position.getLoad().doubleValue());
            }
            ametikoht.setOnLapsepuhkus(yesNo(position.getIsChildCare()));
            ametikoht.setVastavusKval(yesNo(position.getMeetsQualification()));
            ametikoht.setKlassiJuhataja(yesNo(position.getIsClassTeacher()));

            StreamUtil.nullSafeList(modules).stream().collect(Collectors.groupingBy(TeacherSubject::getJournalTeacherId)).forEach((jtId, jtModules) -> {
                Set<Long> moduleIds = StreamUtil.nullSafeList(jtModules).stream().map(TeacherSubject::getCurriculumModuleId).collect(Collectors.toSet());
                
                PedagoogAine aine = new PedagoogAine();
                
                Set<String> uniqueName = new HashSet<>();
                StringBuilder subjectName = new StringBuilder();
                Set<String> uniqueMerCode = new HashSet<>();
                Map<String, Classifier> studyLanguages = new HashMap<>();
                
                moduleIds.forEach(moduleId -> {
                    CurriculumModule cm = em.getReference(CurriculumModule.class, moduleId);
                    if (!uniqueName.contains(cm.getNameEt().toUpperCase())) {
                        if (subjectName.length() > 0) {
                            subjectName.append(", ");
                        }
                        subjectName.append(cm.getNameEt());
                        uniqueName.add(cm.getNameEt().toUpperCase());
                    }
                    Curriculum c = cm.getCurriculum();

                    studyLanguages.putAll(c.getStudyLanguages().stream()
                        .map(CurriculumStudyLanguage::getStudyLang)
                        .collect(Collectors.toMap(cl -> cl.getCode(), cl -> cl, (o, n) -> o)));
                    
                    String merCode = c.getMerCode();
                    if(StringUtils.hasText(merCode) && !uniqueMerCode.contains(merCode.toUpperCase())) {
                        // FIXME strings are allowed values too
                        aine.getOppekavaKood().add(new BigInteger(merCode));
                        uniqueMerCode.add(merCode.toUpperCase());
                    }
                });
                aine.setAineNimetus(subjectName.toString());
                if(!studyLanguages.isEmpty()) {
                    if (studyLanguages.containsKey(StudyLanguage.OPPEKEEL_E.name())) {
                        aine.setKlKeel(ehisValue(studyLanguages.get(StudyLanguage.OPPEKEEL_E.name())));
                    } else {
                        aine.setKlKeel(ehisValue(studyLanguages.values().iterator().next()));
                    }
                }

                JournalTeacher journalTeacher = em.getReference(JournalTeacher.class, jtId);
                if (Boolean.TRUE.equals(journalTeacher.getJournal().getCapacityDiff())) {
                    aine.setTunde(journalTeacher.getJournalTeacherCapacities()
                        .stream()
                        .filter(cap -> {
                            SchoolCapacityType capacityType = schoolCapacitiesByTypeCode
                                    .get(EntityUtil.getCode(cap.getJournalCapacityType().getCapacityType()));
                            return Boolean.TRUE.equals(capacityType.getIsContact());
                        })
                        .mapToLong(JournalTeacherCapacity::getHours).sum());
                } else {
                    aine.setTunde(journalTeacher.getJournal().getJournalCapacities()
                        .stream()
                        .filter(cap -> {
                            SchoolCapacityType capacityType = schoolCapacitiesByTypeCode
                                    .get(EntityUtil.getCode(cap.getJournalCapacityType().getCapacityType()));
                            return Boolean.TRUE.equals(capacityType.getIsContact());
                        })
                        .mapToLong(JournalCapacity::getHours).sum());
                }
                
                aine.setTunnidErivajadus(yesNo(Boolean.FALSE));
                ametikoht.getAine().add(aine);
            });

            pedagoog.getAmetikoht().add(ametikoht);
        }

        // pedagoogTaiendkoolitus
        for(TeacherContinuingEducation e : teacher.getTeacherContinuingEducation()) {
            PedagoogTaiendkoolitus koolitus = new PedagoogTaiendkoolitus();
            koolitus.setTaiendOppeas(e.getSchool() != null ? e.getSchool().getNameEt() : e.getOtherSchool());
            koolitus.setKlTaiendDoc(ehisValue(e.getDiploma()));
            koolitus.setTaiendDocKp(date(e.getDiplomaDate()));
            koolitus.setKlTaiendValdkond(ehisValue(e.getField()));
            koolitus.setTaiendkoolitus(e.getNameEt());
            koolitus.setTaiendkoolitusMahtH(e.getCapacity() != null ? BigInteger.valueOf(e.getCapacity().longValue()): null);
            koolitus.setTaiendDocNr(e.getDiplomaNr());
            koolitus.setOnTaiendvalisriigis(yesNo(e.getIsAbroad()));
            koolitus.setTaiendValisriigisSisu(e.getAbroadDesc());
            pedagoog.getTaiendkoolitus().add(koolitus);
        }
        // pedagoogTasemekoolitus
        for(TeacherQualification q : teacher.getTeacherQualification()) {
            if (ClassifierUtil.isEstonia(q.getState()) && q.getEndDate() != null 
                    && q.getEndDate().isAfter(DONT_SEND_QUALIFICATION_AFTER)) {
                continue;
            }
            PedagoogTasemekoolitus koolitus = new PedagoogTasemekoolitus();
            koolitus.setKlKvalDok(ehisValue(q.getQualification()));
            if(q.getQualification() != null) {
                Classifier qualification = ClassifierUtil.parentFor(q.getQualification(), MainClassCode.EHIS_OPJ_KVAL).orElse(null);
                koolitus.setKlKval(ehisValue(qualification));
            }
            koolitus.setKlRiik(value2(q.getState()));
            koolitus.setTasemeOppeas(ClassifierUtil.isEstonia(q.getState()) ? name(q.getSchool()) : q.getSchoolOther());
            koolitus.setKlHaridustase(ehisValue(q.getStudyLevel()));
            koolitus.setTasemeDokNr(q.getDiplomaNr());
            koolitus.setTasemeLopetKp(date(q.getEndDate()));
            koolitus.setErialaOppekava(q.getSpecialty());
            koolitus.setKlKeel(ehisValue(q.getLanguage()));
            koolitus.setKommentaar(q.getAddInfo());
            pedagoog.getTasemekoolitus().add(koolitus);
        }

        oppeasutus.getPedagoog().add(pedagoog);
        return oppeasutus;
    }

    private Set<Long> changedTeacherIds(Long schoolId, boolean higher, EhisTeacherExportForm form) {
        String from = "from teacher t left join teacher_position_ehis tpe on tpe.teacher_id = t.id and tpe.is_vocational = :is_vocational"
                    + " left join teacher_qualification tq on tq.teacher_id = t.id";
        String changed = "t.changed between :dateFrom and :dateTo or tpe.changed between :dateFrom and :dateTo"
                    + " or tq.changed between :dateFrom and :dateTo";

        if(higher) {
            from += " left outer join teacher_mobility tm on tm.teacher_id = t.id";
            changed += " or tm.changed between :dateFrom and :dateTo";
        } else {
            from += " left outer join teacher_continuing_education tce on tce.teacher_id = t.id";
            changed += " or tce.changed between :dateFrom and :dateTo";
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("t.school_id = :school", "school", schoolId);
        qb.filter(higher ? "t.is_higher = true" : "t.is_vocational = true");
        qb.filter("(" + changed + ")");
        qb.parameter("is_vocational", Boolean.valueOf(!higher));
        qb.parameter("dateFrom", DateUtils.firstMomentOfDay(form.getChangeDateFrom()));
        qb.parameter("dateTo", DateUtils.lastMomentOfDay(form.getChangeDateTo()));

        List<?> result = qb.select("t.id", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result);
    }

    private Set<Long> activeTeacherIds(Long schoolId, boolean higher) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t");
        qb.requiredCriteria("t.school_id = :school", "school", schoolId);
        qb.filter("t.is_active = true");
        qb.filter(higher ? "t.is_higher = true" : "t.is_vocational = true");

        List<?> result = qb.select("t.id", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result);
    }

    private List<TeacherSubject> teacherSubjects(Collection<Long> teachers, List<Long> periods) {
        if(periods.isEmpty()) {
            return Collections.emptyList();
        }

        String from = "from subject_study_period_teacher sspt inner join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                + "inner join curriculum_version_hmodule_subject cvhs on cvhs.subject_id = ssp.subject_id "
                + "inner join curriculum_version_hmodule cvh on cvh.id = cvhs.curriculum_version_hmodule_id "
                + "inner join curriculum_version cv on cv.id = cvh.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("sspt.teacher_id in (:teachers)", "teachers", teachers);
        qb.requiredCriteria("ssp.study_period_id in (:periods)", "periods", periods);

        List<?> result = qb.select("ssp.subject_id, sspt.teacher_id, c.id", em).getResultList();
        return StreamUtil.toMappedList(
                r -> new TeacherSubject(resultAsLong(r, 0), null, resultAsLong(r, 1), resultAsLong(r, 2), null), result);
    }

    private List<TeacherSubject> teacherModules(Collection<Long> teachers, List<Long> periods) {
        if(periods.isEmpty()) {
            return Collections.emptyList();
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt inner join journal j on jt.journal_id = j.id "
                + "inner join journal_omodule_theme jot on jot.journal_id = j.id "
                + "inner join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                + "join curriculum_version_omodule cvom on cvom.id = cvot.curriculum_version_omodule_id ");

        qb.requiredCriteria("jt.teacher_id in (:teachers)", "teachers", teachers);
        qb.requiredCriteria("j.study_year_id in (:studyYears)", "studyYears", periods);
        List<?> result = qb.select("cvom.curriculum_module_id, jt.teacher_id, jt.id", em).getResultList();
        return StreamUtil.toMappedList(
                r -> new TeacherSubject(null, resultAsLong(r, 0), resultAsLong(r, 1), null, resultAsLong(r, 2)), result);
    }
    
    private static String getErrorMsg(LogContext cxt) {
        if (cxt.getIncomingXml() != null) {
            if (Pattern.compile(".*<spring-ws:ValidationError.*>.*One of '\\{tasemeOppeas\\}' is expected.</spring-ws:ValidationError>.*", Pattern.DOTALL).matcher(cxt.getIncomingXml()).matches()) {
                return "Viga! Kvalifikatsioonis \"Muu õppeasutus\" on kohustuslik, kui kvalifikatsiooni riik ei ole \"Eesti\"";
            } else if (Pattern.compile(".*<spring-ws:ValidationError.*>.*One of '\\{isikukood\\}' is expected.</spring-ws:ValidationError>.*", Pattern.DOTALL).matcher(cxt.getIncomingXml()).matches()) {
                return "Viga! Isikukood on kohustuslik";
            }
        }
        
        return ExceptionUtil.getRootCause(cxt.getError()).toString();
    }

    private static class TeacherSubject {
        private final Long subjectId;
        private final Long curriculumModuleId;
        private final Long teacherId;
        private final Long curriculumId;
        private final Long journalTeacherId;

        public TeacherSubject(Long subjectId, Long curriculumModuleId, Long teacherId, Long curriculumId, Long journalTeacherId) {
            this.subjectId = subjectId;
            this.curriculumModuleId = curriculumModuleId;
            this.teacherId = teacherId;
            this.curriculumId = curriculumId;
            this.journalTeacherId = journalTeacherId;
        }

        public Long getSubjectId() {
            return subjectId;
        }

        public Long getCurriculumModuleId() {
            return curriculumModuleId;
        }

        public Long getTeacherId() {
            return teacherId;
        }

        public Long getCurriculumId() {
            return curriculumId;
        }

        public Long getJournalTeacherId() {
            return journalTeacherId;
        }
    }

    private static class RequestObject {
        private final Teacher teacher;
        private final OppejoudList oppejoudList;
        private final OppeasutusList oppeasutusList;
        private final String error;

        public RequestObject(Teacher teacher, OppejoudList oppejoudList) {
            this.teacher = teacher;
            this.oppejoudList = oppejoudList;
            this.oppeasutusList = null;
            this.error = null;
        }

        public RequestObject(Teacher teacher, OppeasutusList oppeasutusList) {
            this.teacher = teacher;
            this.oppejoudList = null;
            this.oppeasutusList = oppeasutusList;
            this.error = null;
        }

        public RequestObject(Teacher teacher, String error) {
            this.teacher = teacher;
            this.oppejoudList = null;
            this.oppeasutusList = null;
            this.error = error;
        }

        public Teacher getTeacher() {
            return teacher;
        }

        public OppejoudList getOppejoudList() {
            return oppejoudList;
        }

        public OppeasutusList getOppeasutusList() {
            return oppeasutusList;
        }

        public String getError() {
            return error;
        }
    }

    @Override
    protected String getServiceCode() {
        return LAE_OPPEJOUD_SERVICE_CODE;
    }
    
    public static class ExportTeacherRequest extends EhisAsyncRequest<Queue<EhisTeacherExportResultDto>> {
        
        private final boolean allDates;
        private final boolean subjectData;
        private final LocalDate from;
        private final LocalDate thru;

        public ExportTeacherRequest(WrapperCallable<Queue<EhisTeacherExportResultDto>> callable, String key,
                HoisUserDetails user, EhisTeacherExportForm form) {
            super(callable, key, user);
            allDates = form.isAllDates();
            subjectData = form.isSubjectData();
            from = form.getChangeDateFrom();
            thru = form.getChangeDateTo();
        }

        public boolean isAllDates() {
            return allDates;
        }

        public boolean isSubjectData() {
            return subjectData;
        }

        public LocalDate getFrom() {
            return from;
        }

        public LocalDate getThru() {
            return thru;
        }
        
    }
}
