package ee.hitsa.ois.service.ehis;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.WsEhisTeacherLog;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherContinuingEducation;
import ee.hitsa.ois.domain.teacher.TeacherMobility;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.domain.teacher.TeacherQualification;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
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
    private static final String SERVICE_VERSION = "v1";
    public static final String LAE_OPPEJOUD_SERVICE = String.format("%s.%s.%s", SUBSYSTEM_CODE, LAE_OPPEJOUD_SERVICE_CODE, SERVICE_VERSION);
    public static final String LAE_PEDAGOOGID_SERVICE = String.format("%s.%s.%s", SUBSYSTEM_CODE, LAE_PEDAGOOGID_SERVICE_CODE, SERVICE_VERSION);

    private static final String EHIS_TOOSUHE_MUU = "EHIS_TOOSUHE_MUU";
    private static final String EHIS_KVALIFIKATSIOON_NIMI_MUU = "EHIS_KVALIFIKATSIOON_NIMI_MUU";
    private static final String EHIS_AMETIKOHT_MUU = "EHIS_AMETIKOHT_MUU";

    @Autowired
    private StudyYearService studyYearService;

    public List<EhisTeacherExportResultDto> exportToEhis(Long schoolId, boolean higher, EhisTeacherExportForm form) {
        List<EhisTeacherExportResultDto> resultList = new ArrayList<>();

        for (RequestObject request : createRequests(schoolId, higher, form)) {
            LogContext queryLog;
            WsEhisTeacherLog wsEhisTeacherLog = new WsEhisTeacherLog();
            XRoadHeaderV4 xRoadHeader = getXroadHeader();
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
                    wsEhisTeacherLog.setLogTxt(String.join(";", StreamUtil.nullSafeList(response.getResult())));
                } else {
                    wsEhisTeacherLog.setHasXteeErrors(Boolean.TRUE);
                }
            } else {
                queryLog = xRoadHeader.logContext();
                wsEhisTeacherLog.setHasOtherErrors(Boolean.TRUE);
                wsEhisTeacherLog.setLogTxt(request.getError());
            }
            wsEhisTeacherLog.setTeacher(request.getTeacher());
            wsEhisTeacherLog.setSchool(request.getTeacher().getSchool());
            ehisLogService.insert(queryLog, wsEhisTeacherLog);

            resultList.add(new EhisTeacherExportResultDto(request.getTeacher().getPerson().getFullname(), wsEhisTeacherLog.getLogTxt()));
        }
        return resultList;
    }

    private List<RequestObject> createRequests(Long schoolId, boolean higher, EhisTeacherExportForm form) {
        List<RequestObject> result = new ArrayList<>();
        Set<Long> schoolTeachers = getTeacherIds(schoolId, higher, form);
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
            // for every teacher we make a separate request
            try {
                RequestObject request;
                if(higher) {
                    Oppejoud oppejoud = createOppejoud(teacher, subjectByTeacher.getOrDefault(EntityUtil.getId(teacher), Collections.emptyList()));
                    OppejoudList oppejoudList = new OppejoudList();
                    oppejoudList.getItem().add(oppejoud);
                    request = new RequestObject(teacher, oppejoudList);
                } else {
                    Oppeasutus oppeasutus = createOppeasutus(teacher, subjectByTeacher.getOrDefault(EntityUtil.getId(teacher), Collections.emptyList()));
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

    private Oppejoud createOppejoud(Teacher teacher, List<TeacherWithSubject> subjects) {
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

    private void addPositionEhis(List<OppejoudAmetikoht> resultList, Teacher teacher, List<TeacherWithSubject> subjectStudyPeriodTeacher) {
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
                    oppeaine.getOkKood().add(StringUtils.hasText(curriculum.getMerCode()) ? new BigInteger(curriculum.getMerCode()) : null);
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

    private Oppeasutus createOppeasutus(Teacher teacher, List<TeacherWithSubject> subjects) {
        Oppeasutus oppeasutus = new Oppeasutus();
        oppeasutus.setKoolId(ehisValue(teacher.getSchool().getEhisSchool()));

        Person person = teacher.getPerson();
        Pedagoog pedagoog = new Pedagoog();
        pedagoog.setIsikukood(person.getIdcode());

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

            for(TeacherWithSubject subject : subjects) {
                // TODO
            }

            pedagoog.getAmetikoht().add(ametikoht);
        }

        // pedagoogTaiendkoolitus
        for(TeacherContinuingEducation e : teacher.getTeacherContinuingEducation()) {
            PedagoogTaiendkoolitus koolitus = new PedagoogTaiendkoolitus();
            koolitus.setTaiendOppeas(e.getSchool() != null ? code(e.getSchool()) : e.getOtherSchool());
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
            PedagoogTasemekoolitus koolitus = new PedagoogTasemekoolitus();
            koolitus.setKlKvalDok(ehisValue(q.getQualification()));
            koolitus.setKlKval(ehisValue(q.getQualificationName()));
            koolitus.setKlRiik(value2(q.getState()));
            koolitus.setTasemeOppeas(ehisValue(q.getSchool()));
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

    private Set<Long> getTeacherIds(Long schoolId, boolean higher, EhisTeacherExportForm form) {
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
        if(higher) {
            qb.filter("t.is_higher = true");
        } else {
            qb.filter("t.is_vocational = true");
        }
        if(form.isAllDates()) {
            qb.filter("t.is_active = true");
        } else {
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
}
