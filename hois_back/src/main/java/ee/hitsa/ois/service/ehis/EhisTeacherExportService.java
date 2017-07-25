package ee.hitsa.ois.service.ehis;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
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
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.EhisTeacherExportForm;
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
    private static final String LAE_OPPEJOUD_SERVICE = "ehis." + LAE_OPPEJOUD_SERIVCE_CODE + ".v1";

    private static final String EHIS_TOOSUHE_MUU = "EHIS_TOOSUHE_MUU";
    private static final String EHIS_KVALIFIKATSIOON_NIMI_MUU = "EHIS_KVALIFIKATSIOON_NIMI_MUU";
    private static final String EHIS_AMETIKOHT_MUU = "EHIS_AMETIKOHT_MUU";

    @Autowired
    private EntityManager em;
    @Autowired
    private TeacherRepository teacherRepository;
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

    public List<EhisTeacherExportResultDto> exportToEhis(EhisTeacherExportForm form, HoisUserDetails userDetails) {
        List<EhisTeacherExportResultDto> resultList = new ArrayList<>();
        List<RequestObject> requestList = getRequest(form, userDetails);

        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();

        for (RequestObject requestObject : requestList) {
            EhisLaeOppejoudResponse ehisLaeOppejoudResponse;
            if(requestObject.getError() == null) {
                ehisLaeOppejoudResponse = ehisXroadService.laeOppejoud(xRoadHeaderV4,
                        requestObject.getOppejoudList());
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

    private List<RequestObject> getRequest(EhisTeacherExportForm form, HoisUserDetails userDetails) {
        List<RequestObject> result = new ArrayList<>();
        List<Long> schoolTeachers = getTeachers(userDetails.getSchoolId(), form);
        List<Long> periods = new ArrayList<>();
        periods.add(studyYearService.getCurrentStudyPeriod(userDetails.getSchoolId()));
        periods.add(studyYearService.getPreviousStudyPeriod(userDetails.getSchoolId()));
        List<TeacherWithSubject> subjectStudyPeriodTeacher;
        if (form.isSubjectData()) {
            subjectStudyPeriodTeacher = getSubjectStudyPeriods(schoolTeachers, periods);
        } else {
            subjectStudyPeriodTeacher = new ArrayList<>();
        }
        Map<Long, Teacher> teacherMap = StreamUtil.toMap(t -> EntityUtil.getId(t), t -> t,
                teacherRepository.findAll((root, query, cb) -> {
                    List<Predicate> filters = new ArrayList<>();
                    filters.add(root.get("id").in(schoolTeachers));
                    return cb.and(filters.toArray(new Predicate[filters.size()]));
                }));

        for (Teacher teacher : teacherMap.values()) {
            try {
                // for every teacher we make a seperate request
                Oppejoud oppejoud = getOppejoud(teacher,
                        subjectStudyPeriodTeacher.stream()
                                .filter(tws -> tws.getTeacherId().equals(EntityUtil.getId(teacher)))
                                .collect(Collectors.toList()));
                OppejoudList oppejoudList = new OppejoudList();
                oppejoudList.getItem().add(oppejoud);
                String error = validateOppejoud(oppejoud);
                if(error != null) {
                    result.add(new RequestObject(teacher, error));
                } else {
                    result.add(new RequestObject(teacher, oppejoudList));
                }
            } catch (Exception e) {
                log.error(String.format("Generating request object on teacher with id %s failed :", EntityUtil.getId(teacher)), e);
                result.add(new RequestObject(teacher, "Antud õpetaja päringu genereerimine ebaõnnestus"));
            }
        }
        return result;
    }

    private Oppejoud getOppejoud(Teacher teacher, List<TeacherWithSubject> subjectStudyPeriodTeacher)
            throws DatatypeConfigurationException {
        Oppejoud oppejoud = new Oppejoud();
        Person person = teacher.getPerson();

        oppejoud.setKoolId(new BigInteger(teacher.getSchool().getEhisSchool().getValue()));
        oppejoud.setIsikukood(person.getIdcode());
        oppejoud.setTelefon(teacher.getPhone());
        oppejoud.setEmail(teacher.getEmail());

        OppejoudIsikuandmed isikuandmed = new OppejoudIsikuandmed();
        isikuandmed.setEesnimi(person.getFirstname());
        isikuandmed.setPerenimi(person.getLastname());
        isikuandmed.setSynniKp(getXMLGregorianCalendarDate(person.getBirthdate(),
                String.format("birthdate for teacher with id %s", EntityUtil.getId(teacher))));
        isikuandmed.setKlSugu(person.getSex() != null ? person.getSex().getEhisValue() : null);
        isikuandmed.setKlKodakondsus(person.getCitizenship() != null ? person.getCitizenship().getValue() : null);

        oppejoud.setIsikuandmed(isikuandmed);
        addPostionEhis(oppejoud.getAmetikoht(), teacher, subjectStudyPeriodTeacher);
        addQualifications(oppejoud.getKvalifikatsioon(), teacher);
        addMobilities(oppejoud.getLyhiajalineMobiilsus(), teacher);
        return oppejoud;
    }

    private void addPostionEhis(List<OppejoudAmetikoht> resultList, Teacher teacher,
            List<TeacherWithSubject> subjectStudyPeriodTeacher) throws DatatypeConfigurationException {
        for (TeacherPositionEhis positionEhis : teacher.getTeacherPositionEhis()) {
            if (!positionEhis.getIsVocational().booleanValue()) {
                OppejoudAmetikoht ametikoht = new OppejoudAmetikoht();

                ametikoht.setOnOppejoud(positionEhis.getIsTeacher().booleanValue() ? "jah" : "ei");
                ametikoht.setKlAmetikoht(positionEhis.getPosition().getEhisValue());
                if (getCode(positionEhis.getPosition()) != null
                        && EHIS_AMETIKOHT_MUU.equals(getCode(positionEhis.getPosition()))) {
                    ametikoht.setAmetikohtMuu(positionEhis.getPositionSpecificationEt());
                }
                ametikoht.setKlToosuhe(getCode(positionEhis.getEmploymentType()));
                ametikoht.setToosuheMuu(positionEhis.getEmploymentTypeSpecification() != null && positionEhis.getEmploymentTypeSpecification().equals(EHIS_TOOSUHE_MUU)
                        ? EHIS_TOOSUHE_MUU : null);
                ametikoht.setKlLepinguLiik(getCode(positionEhis.getContractType()));
                ametikoht.setLepingAlgKp(getXMLGregorianCalendarDate(positionEhis.getContractStart(), String.format(
                        "contract start for teacher_position_ehis with id %s", EntityUtil.getId(positionEhis))));
                ametikoht.setLepingLoppKp(getXMLGregorianCalendarDate(positionEhis.getContractStart(), String
                        .format("contract end for teacher_position_ehis with id %s", EntityUtil.getId(positionEhis))));
                ametikoht.setKoormus(positionEhis.getLoad().doubleValue());
                ametikoht.setLepingOnLopetatud(positionEhis.getIsContractEnded().booleanValue() ? "jah" : "ei");
                if(positionEhis.getSchoolDepartment() != null) {
                    ametikoht.setStruktNimi(positionEhis.getSchoolDepartment().getNameEt());
                    ametikoht.setStruktKood(positionEhis.getSchoolDepartment().getCode());
                }
                ametikoht.setToosuheKood(positionEhis.getEmploymentCode());
                ametikoht.setAmetikohtTapsustusEn(positionEhis.getPositionSpecificationEn());
                resultList.add(ametikoht);

                for (TeacherWithSubject tws : subjectStudyPeriodTeacher) {
                    Subject subject = em.getReference(Subject.class, tws.getSubjectId());
                    Oppeaine oppeaine = new Oppeaine();
                    oppeaine.setNimetus(subject.getNameEt());
                    oppeaine.setKlOppekeel(!subject.getSubjectLanguages().isEmpty() ? getCode(subject.getSubjectLanguages().iterator().next().getLanguage()) : null);
                    oppeaine.setAineKood(subject.getCode());
                    Curriculum curriculum = em.getReference(Curriculum.class, tws.getCurriculumId());
                    oppeaine.getOkKood().add(curriculum.getMerCode() != null && !curriculum.getMerCode().isEmpty() ? new BigInteger(curriculum.getMerCode()) : null);
                    oppeaine.setMaht(subject.getCredits().toString());
                    ametikoht.getOppeained().add(oppeaine);
                }
            }
        }
    }

    private static void addQualifications(List<OppejoudKvalifikatsioon> resultList, Teacher teacher) {
        for (TeacherQualification hoisQualification : teacher.getTeacherQualification()) {
            OppejoudKvalifikatsioon oKvalifikatsioon = new OppejoudKvalifikatsioon();
            oKvalifikatsioon.setKlKvalifikatsioon(getCode(hoisQualification.getQualification()));
            oKvalifikatsioon.setKlKvalifikatsioonNimetus(getCode(hoisQualification.getQualification()));
            if (getCode(hoisQualification.getQualificationName()) != null
                    && EHIS_KVALIFIKATSIOON_NIMI_MUU.equals(getCode(hoisQualification.getQualificationName()))) {
                oKvalifikatsioon.setKvalifikatsioonNimetusMuu(hoisQualification.getQualificationOther());
            }
            oKvalifikatsioon.setKlRiik(getCode(hoisQualification.getState()));
            oKvalifikatsioon.setAasta(new BigInteger(hoisQualification.getYear().toString()));
            oKvalifikatsioon.setKlEestiOppeasutus(getCode(hoisQualification.getSchool()));
            oKvalifikatsioon.setKlEestiOppeasutusEndine(getCode(hoisQualification.getExSchool()));
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
            mobiilsus.setKlEesmark(getCode(mob.getTarget()));
            mobiilsus.setSihtoppeasutus(mob.getSchool());
            mobiilsus.setKlSihtriik(mob.getState().getValue2());
            resultList.add(mobiilsus);
        }
    }

    private static XMLGregorianCalendar getXMLGregorianCalendarDate(LocalDate date, String errorCode)
            throws DatatypeConfigurationException {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
        } catch (DatatypeConfigurationException e) {
            if (log.isErrorEnabled()) {
                log.error(String.format("Converting date failed on %s :", errorCode), e);
            }
            throw e;
        }
    }

    private static String getCode(Classifier classifier) {
        return EntityUtil.getNullableCode(classifier);
    }

    private List<Long> getTeachers(Long school, EhisTeacherExportForm form) {
        JpaQueryUtil.NativeQueryBuilder qb;
        if (form.isAllDates()) {
            qb = new JpaQueryUtil.NativeQueryBuilder("from teacher t");
        } else {
            qb = new JpaQueryUtil.NativeQueryBuilder(
                    "from teacher t left join teacher_mobility tm on tm.teacher_id = t.id"
                            + " left join teacher_position_ehis tpe on tpe.teacher_id = t.id"
                            + " left join teacher_qualification tq on tq.teacher_id = t.id");
        }

        qb.requiredCriteria("t.school_id = :school", "school", school);
        qb.filter("t.is_active = true");
        if (!form.isAllDates()) {
            qb.parameter("dateTo", Timestamp.valueOf(form.getChangeDateTo().atStartOfDay()));
            qb.parameter("dateFrom", Timestamp.valueOf(form.getChangeDateFrom().atStartOfDay()));
            String filter = "(t.changed between :dateFrom and :dateTo or tm.changed between :dateFrom and :dateTo "
                    + "or tpe.changed between :dateFrom and :dateTo or tq.changed between :dateFrom and :dateTo)";
            qb.filter(filter);
        }

        List<?> result = qb.select("t.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), result);
    }

    private List<TeacherWithSubject> getSubjectStudyPeriods(List<Long> teachers, List<Long> periods) {
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
