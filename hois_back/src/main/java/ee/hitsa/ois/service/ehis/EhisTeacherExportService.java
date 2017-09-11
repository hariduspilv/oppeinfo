package ee.hitsa.ois.service.ehis;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.WsEhisTeacherLog;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherMobility;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.domain.teacher.TeacherQualification;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisTeacherExportForm;
import ee.hitsa.ois.web.dto.EhisTeacherExportResultDto;
import ee.hois.xroad.ehis.generated.Oppeaine;
import ee.hois.xroad.ehis.generated.Oppejoud;
import ee.hois.xroad.ehis.generated.OppejoudAmetikoht;
import ee.hois.xroad.ehis.generated.OppejoudIsikuandmed;
import ee.hois.xroad.ehis.generated.OppejoudKvalifikatsioon;
import ee.hois.xroad.ehis.generated.OppejoudList;
import ee.hois.xroad.ehis.generated.OppejoudLyhiajalineMobiilsus;
import ee.hois.xroad.ehis.service.EhisLaeOppejoudResponse;
import ee.hois.xroad.ehis.service.EhisXroadService;
import ee.hois.xroad.helpers.XRoadHeaderV4;

@Transactional
@Service
public class EhisTeacherExportService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String LAE_OPPEJOUD_SERIVCE_CODE = "laeOppejoud";
    public static final String LAE_OPPEJOUD_SERVICE = "ehis." + LAE_OPPEJOUD_SERIVCE_CODE + ".v1";

    private static final String EHIS_TOOSUHE_MUU = "EHIS_TOOSUHE_MUU";
    private static final String EHIS_KVALIFIKATSIOON_NIMI_MUU = "EHIS_KVALIFIKATSIOON_NIMI_MUU";
    private static final String EHIS_AMETIKOHT_MUU = "EHIS_AMETIKOHT_MUU";

    @Autowired
    private EntityManager em;
    @Autowired
    private EhisXroadService ehisXroadService;
    @Autowired
    private StudyYearService studyYearService;

    @Value("${ehis.endpoint}")
    private String endpoint;

    @Value("${ehis.user}")
    private String user;

    @Value("${ehis.client.xRoadInstance}")
    private String clientXRoadInstance;
    @Value("${ehis.client.memberClass}")
    private String clientMemberClass;
    @Value("${ehis.client.memberCode}")
    private String clientMemberCode;
    @Value("${ehis.client.subsystemCode}")
    private String clientSubsystemCode;

    @Value("${ehis.service.xRoadInstance}")
    private String serviceXRoadInstance;
    @Value("${ehis.service.memberClass}")
    private String serviceMemberClass;
    @Value("${ehis.service.memberCode}")
    private String serviceMemberCode;
    @Value("${ehis.service.subsystemCode}")
    private String serviceSubsystemCode;

    public List<EhisTeacherExportResultDto> exportToEhis(Long schoolId, EhisTeacherExportForm form) {
        List<EhisTeacherExportResultDto> resultList = new ArrayList<>();
        List<RequestObject> requestList = getRequest(schoolId, form);

        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();

        for (RequestObject requestObject : requestList) {
            EhisLaeOppejoudResponse ehisLaeOppejoudResponse;
            if(requestObject.getError() == null) {
                ehisLaeOppejoudResponse = ehisXroadService.laeOppejoud(xRoadHeaderV4, requestObject.getOppejoudList());
            } else {
                ehisLaeOppejoudResponse = new EhisLaeOppejoudResponse();
                ehisLaeOppejoudResponse.setHasOtherErrors(Boolean.TRUE);
                ehisLaeOppejoudResponse.setLogTxt(requestObject.getError());
                ehisLaeOppejoudResponse.setRequest("Could not generate request");
            }
            WsEhisTeacherLog wsEhisTeacherLog = new WsEhisTeacherLog();
            wsEhisTeacherLog.setRequest(ehisLaeOppejoudResponse.getRequest());
            wsEhisTeacherLog.setResponse(ehisLaeOppejoudResponse.getResponse());
            wsEhisTeacherLog.setHasOtherErrors(ehisLaeOppejoudResponse.isHasOtherErrors());
            wsEhisTeacherLog.setHasXteeErrors(ehisLaeOppejoudResponse.isHasXRoadErrors());
            wsEhisTeacherLog.setTeacher(requestObject.getTeacher());
            wsEhisTeacherLog.setWsName(LAE_OPPEJOUD_SERVICE);
            wsEhisTeacherLog.setSchool(requestObject.getTeacher().getSchool());
            String txt = ehisLaeOppejoudResponse.getLogTxt().toLowerCase();
            if (txt.contains("viga") || txt.contains("error") || txt.contains("exception")) {
                wsEhisTeacherLog.setHasOtherErrors(Boolean.TRUE);
            }
            wsEhisTeacherLog.setLogTxt(ehisLaeOppejoudResponse.getLogTxt());
            em.persist(wsEhisTeacherLog);

            resultList.add(new EhisTeacherExportResultDto(requestObject.getTeacher().getPerson().getFullname(),
                    ehisLaeOppejoudResponse.getLogTxt()));
        }
        return resultList;
    }

    private List<RequestObject> getRequest(Long schoolId, EhisTeacherExportForm form) {
        List<RequestObject> result = new ArrayList<>();
        Set<Long> schoolTeachers = getTeacherIds(schoolId, form);
        if(schoolTeachers.isEmpty()) {
            return result;
        }

        List<Long> periods = new ArrayList<>();
        Long periodId = studyYearService.getCurrentStudyPeriod(schoolId);
        if(periodId != null) {
            periods.add(periodId);
        }
        periodId = studyYearService.getPreviousStudyPeriod(schoolId);
        if(periodId != null) {
            periods.add(periodId);
        }

        List<TeacherWithSubject> subjects = form.isSubjectData() && !periods.isEmpty() ? getSubjectStudyPeriods(schoolTeachers, periods) : Collections.emptyList();
        Map<Long, List<TeacherWithSubject>> subjectByTeacher = subjects.stream().collect(Collectors.groupingBy(TeacherWithSubject::getTeacherId));

        List<Teacher> teachers = em.createQuery("select t from Teacher t where t.id in ?1", Teacher.class)
                .setParameter(1,  schoolTeachers).getResultList();
        for (Teacher teacher : teachers) {
            try {
                // for every teacher we make a separate request
                Oppejoud oppejoud = getOppejoud(teacher, subjectByTeacher.getOrDefault(EntityUtil.getId(teacher), Collections.emptyList()));
                OppejoudList oppejoudList = new OppejoudList();
                oppejoudList.getItem().add(oppejoud);
                String error = validateOppejoud(oppejoud);

                result.add(error != null ? new RequestObject(teacher, error) : new RequestObject(teacher, oppejoudList));
            } catch (Exception e) {
                log.error("Generating request object on teacher with id {} failed :", EntityUtil.getId(teacher), e);
                result.add(new RequestObject(teacher, "Antud õpetaja päringu genereerimine ebaõnnestus"));
            }
        }
        return result;
    }

    private Oppejoud getOppejoud(Teacher teacher, List<TeacherWithSubject> subjectStudyPeriodTeacher)
            throws DatatypeConfigurationException {
        Oppejoud oppejoud = new Oppejoud();
        Person person = teacher.getPerson();

        // FIXME strings are allowed values too
        oppejoud.setKoolId(new BigInteger(ehisValue(teacher.getSchool().getEhisSchool())));
        oppejoud.setIsikukood(person.getIdcode());
        oppejoud.setTelefon(teacher.getPhone());
        oppejoud.setEmail(teacher.getEmail());

        OppejoudIsikuandmed isikuandmed = new OppejoudIsikuandmed();
        isikuandmed.setEesnimi(person.getFirstname());
        isikuandmed.setPerenimi(person.getLastname());
        isikuandmed.setSynniKp(getXMLGregorianCalendarDate(person.getBirthdate(),
                String.format("birthdate for teacher with id %s", EntityUtil.getId(teacher))));
        isikuandmed.setKlSugu(ehisValue(person.getSex()));
        isikuandmed.setKlKodakondsus(person.getCitizenship() != null ? person.getCitizenship().getValue() : null);

        oppejoud.setIsikuandmed(isikuandmed);
        addPositionEhis(oppejoud.getAmetikoht(), teacher, subjectStudyPeriodTeacher);
        addQualifications(oppejoud.getKvalifikatsioon(), teacher);
        addMobilities(oppejoud.getLyhiajalineMobiilsus(), teacher);
        return oppejoud;
    }

    private void addPositionEhis(List<OppejoudAmetikoht> resultList, Teacher teacher,
            List<TeacherWithSubject> subjectStudyPeriodTeacher) throws DatatypeConfigurationException {
        for (TeacherPositionEhis positionEhis : teacher.getTeacherPositionEhis()) {
            if (Boolean.TRUE.equals(positionEhis.getIsVocational())) {
                continue;
            }

            OppejoudAmetikoht ametikoht = new OppejoudAmetikoht();
            ametikoht.setOnOppejoud(Boolean.TRUE.equals(positionEhis.getIsTeacher()) ? "jah" : "ei");
            ametikoht.setKlAmetikoht(ehisValue(positionEhis.getPosition()));
            if (EHIS_AMETIKOHT_MUU.equals(code(positionEhis.getPosition()))) {
                ametikoht.setAmetikohtMuu(positionEhis.getPositionSpecificationEt());
            }
            ametikoht.setKlToosuhe(ehisValue(positionEhis.getEmploymentType()));
            if(EHIS_TOOSUHE_MUU.equals(code(positionEhis.getEmploymentType()))) {
                ametikoht.setToosuheMuu(positionEhis.getEmploymentTypeSpecification());
            }
            ametikoht.setKlLepinguLiik(ehisValue(positionEhis.getContractType()));
            ametikoht.setLepingAlgKp(getXMLGregorianCalendarDate(positionEhis.getContractStart(), String.format(
                    "contract start for teacher_position_ehis with id %s", EntityUtil.getId(positionEhis))));
            ametikoht.setLepingLoppKp(getXMLGregorianCalendarDate(positionEhis.getContractEnd(), String
                    .format("contract end for teacher_position_ehis with id %s", EntityUtil.getId(positionEhis))));
            ametikoht.setKoormus(positionEhis.getLoad().doubleValue());
            ametikoht.setLepingOnLopetatud(Boolean.TRUE.equals(positionEhis.getIsContractEnded()) ? "jah" : "ei");
            if(positionEhis.getSchoolDepartment() != null) {
                ametikoht.setStruktNimi(positionEhis.getSchoolDepartment().getNameEt());
                ametikoht.setStruktKood(positionEhis.getSchoolDepartment().getCode());
            }
            ametikoht.setToosuheKood(positionEhis.getEmploymentCode());
            ametikoht.setAmetikohtTapsustusEn(positionEhis.getPositionSpecificationEn());
            resultList.add(ametikoht);

            // group curriculums by subject id
            Map<Long, List<Long>> subjects = subjectStudyPeriodTeacher.stream().collect(
                    Collectors.groupingBy(TeacherWithSubject::getSubjectId, Collectors.mapping(TeacherWithSubject::getCurriculumId, Collectors.toList())));

            for (Map.Entry<Long, List<Long>> tws : subjects.entrySet()) {
                Oppeaine oppeaine = new Oppeaine();
                Subject subject = em.getReference(Subject.class, tws.getKey());
                oppeaine.setNimetus(subject.getNameEt());
                oppeaine.setKlOppekeel(!subject.getSubjectLanguages().isEmpty() ? subject.getSubjectLanguages().iterator().next().getLanguage().getExtraval2() : null);
                oppeaine.setAineKood(subject.getCode());
                for(Long curriculumId : tws.getValue()) {
                    Curriculum curriculum = em.getReference(Curriculum.class, curriculumId);
                    // FIXME strings are allowed values too
                    oppeaine.getOkKood().add(curriculum.getMerCode() != null && !curriculum.getMerCode().isEmpty() ? new BigInteger(curriculum.getMerCode()) : null);
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
            oKvalifikatsioon.setKlKvalifikatsioonNimetus(ehisValue(hoisQualification.getQualification()));
            if (EHIS_KVALIFIKATSIOON_NIMI_MUU.equals(code(hoisQualification.getQualificationName()))) {
                oKvalifikatsioon.setKvalifikatsioonNimetusMuu(hoisQualification.getQualificationOther());
            }
            oKvalifikatsioon.setKlRiik(value2(hoisQualification.getState()));
            oKvalifikatsioon.setAasta(BigInteger.valueOf(hoisQualification.getYear().longValue()));
            oKvalifikatsioon.setKlEestiOppeasutus(ehisValue(hoisQualification.getSchool()));
            oKvalifikatsioon.setKlEestiOppeasutusEndine(ehisValue(hoisQualification.getExSchool()));
            oKvalifikatsioon.setOppeasutusMuu(hoisQualification.getSchoolOther());
            resultList.add(oKvalifikatsioon);
        }
    }

    private static void addMobilities(List<OppejoudLyhiajalineMobiilsus> resultList, Teacher teacher)
            throws DatatypeConfigurationException {
        for (TeacherMobility mob : teacher.getTeacherMobility()) {
            OppejoudLyhiajalineMobiilsus mobiilsus = new OppejoudLyhiajalineMobiilsus();
            mobiilsus.setPerioodiAlgus(getXMLGregorianCalendarDate(mob.getStart(),
                    String.format("mobiilsus start for teacher_mobility with id %s", EntityUtil.getId(mob))));
            mobiilsus.setPerioodiLopp(getXMLGregorianCalendarDate(mob.getEnd(),
                    String.format("mobiilsus end for teacher_mobility with id %s", EntityUtil.getId(mob))));
            mobiilsus.setKlEesmark(ehisValue(mob.getTarget()));
            mobiilsus.setSihtoppeasutus(mob.getSchool());
            mobiilsus.setKlSihtriik(value2(mob.getState()));
            resultList.add(mobiilsus);
        }
    }

    private static XMLGregorianCalendar getXMLGregorianCalendarDate(LocalDate date, String errorCode)
            throws DatatypeConfigurationException {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
        } catch (DatatypeConfigurationException e) {
            log.error("Converting date failed on {} :", errorCode, e);
            throw e;
        }
    }

    private static String code(Classifier classifier) {
        return EntityUtil.getNullableCode(classifier);
    }

    private static String ehisValue(Classifier classifier) {
        return classifier != null ? classifier.getEhisValue() : null;
    }

    private static String value2(Classifier classifier) {
        return classifier != null ? classifier.getValue2() : null;
    }

    private Set<Long> getTeacherIds(Long schoolId, EhisTeacherExportForm form) {
        JpaQueryUtil.NativeQueryBuilder qb;
        if (form.isAllDates()) {
            qb = new JpaQueryUtil.NativeQueryBuilder("from teacher t");
        } else {
            qb = new JpaQueryUtil.NativeQueryBuilder(
                    "from teacher t left join teacher_mobility tm on tm.teacher_id = t.id"
                            + " left join teacher_position_ehis tpe on tpe.teacher_id = t.id"
                            + " left join teacher_qualification tq on tq.teacher_id = t.id");
        }

        qb.requiredCriteria("t.school_id = :school", "school", schoolId);
        qb.filter("t.is_active = true");
        if (!form.isAllDates()) {
            qb.parameter("dateTo", parameterAsTimestamp(form.getChangeDateTo().atStartOfDay()));
            qb.parameter("dateFrom", parameterAsTimestamp(form.getChangeDateFrom().atStartOfDay()));
            String filter = "(t.changed between :dateFrom and :dateTo or tm.changed between :dateFrom and :dateTo "
                    + "or tpe.changed between :dateFrom and :dateTo or tq.changed between :dateFrom and :dateTo)";
            qb.filter(filter);
        }

        List<?> result = qb.select("t.id", em).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result);
    }

    private List<TeacherWithSubject> getSubjectStudyPeriods(Set<Long> teachers, List<Long> periods) {
        String from = "from subject_study_period_teacher sspt inner join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                + "inner join curriculum_version_hmodule_subject cvhs on cvhs.subject_id = ssp.subject_id "
                + "inner join curriculum_version_hmodule cvh on cvh.id = cvhs.curriculum_version_hmodule_id "
                + "inner join curriculum_version cv on cv.id = cvh.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);

        qb.requiredCriteria("sspt.teacher_id in (:teachers)", "teachers", teachers);
        qb.requiredCriteria("ssp.study_period_id in (:periods)", "periods", periods);

        List<?> result = qb.select("ssp.subject_id, sspt.teacher_id, c.id", em).getResultList();
        return StreamUtil.toMappedList(
                r -> new TeacherWithSubject(resultAsLong(r, 0), resultAsLong(r, 1), resultAsLong(r, 2)), result);
    }

    private static String validateOppejoud(Oppejoud oppejoud) {
        String error = "Puudub/puuduvad jargmised parameetrid: ";
        List<String> errors = new ArrayList<>();

        OppejoudIsikuandmed isikuandmed = oppejoud.getIsikuandmed();
        if(isikuandmed.getEesnimi() == null) {
            errors.add("eesnimi");
        }
        if(isikuandmed.getPerenimi() == null) {
            errors.add("perekonnanimi");
        }
        if(isikuandmed.getSynniKp() == null) {
            errors.add("sünnikuupäev");
        }
        if(isikuandmed.getKlSugu() == null) {
            errors.add("sugu");
        }
        if(isikuandmed.getKlKodakondsus() == null) {
            errors.add("kodakondsus");
        }

        if(!errors.isEmpty()) {
            return error + String.join(", ", errors);
        }

        return null;
    }

    private static class TeacherWithSubject {
        private final Long subjectId;
        private final Long teacherId;
        private final Long curriculumId;

        public TeacherWithSubject(Long subjectId, Long teacherId, Long curriculumId) {
            this.subjectId = subjectId;
            this.teacherId = teacherId;
            this.curriculumId = curriculumId;
        }

        public Long getSubjectId() {
            return subjectId;
        }

        public Long getTeacherId() {
            return teacherId;
        }

        public Long getCurriculumId() {
            return curriculumId;
        }
    }

    private static class RequestObject {
        private final Teacher teacher;
        private final OppejoudList oppejoudList;
        private final String error;

        public RequestObject(Teacher teacher, OppejoudList oppejoudList) {
            this.teacher = teacher;
            this.oppejoudList = oppejoudList;
            this.error = null;
        }

        public RequestObject(Teacher teacher, String error) {
            this.teacher = teacher;
            this.error = error;
            this.oppejoudList = null;
        }

        public Teacher getTeacher() {
            return teacher;
        }

        public OppejoudList getOppejoudList() {
            return oppejoudList;
        }

        public String getError() {
            return error;
        }
    }

    private XRoadHeaderV4 getXroadHeader() {
        XRoadHeaderV4.Client client = new XRoadHeaderV4.Client();
        client.setXRoadInstantce(clientXRoadInstance);
        client.setMemberClass(clientMemberClass);
        client.setMemberCode(clientMemberCode);
        client.setSubSystemCode(clientSubsystemCode);

        XRoadHeaderV4.Service service = new XRoadHeaderV4.Service();
        service.setxRoadInstance(serviceXRoadInstance);
        service.setMemberClass(serviceMemberClass);
        service.setMemberCode(serviceMemberCode);
        service.setServiceCode(LAE_OPPEJOUD_SERIVCE_CODE);
        service.setSubsystemCode(serviceSubsystemCode);

        XRoadHeaderV4 header = new XRoadHeaderV4();
        header.setClient(client);
        header.setService(service);
        header.setEndpoint(endpoint);
        header.setUserId(user);
        return header;
    }
}
