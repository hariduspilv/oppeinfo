package ee.hitsa.ois.service.ehis;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.repository.WsEhisStudentLogRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.xroad.ehis.generated.KhlIsikuandmedLisa;
import ee.hois.xroad.ehis.generated.KhlLisamine;
import ee.hois.xroad.ehis.generated.KhlMuutmine;
import ee.hois.xroad.ehis.generated.KhlOppeasutus;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppur;
import ee.hois.xroad.ehis.service.EhisLaeKorgharidusedResponse;
import ee.hois.xroad.ehis.service.EhisXroadService;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.time.LocalDate;

abstract class EhisService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EhisXroadService ehisXroadService;

    @Autowired
    private WsEhisStudentLogRepository wsEhisStudentLogRepository;

    @Value("${ehis.endpoint}")
    protected String endpoint;

    @Value("${ehis.user}")
    protected String user;

    @Value("${ehis.client.xRoadInstance}")
    protected String clientXRoadInstance;
    @Value("${ehis.client.memberClass}")
    protected String clientMemberClass;
    @Value("${ehis.client.memberCode}")
    protected String clientMemberCode;
    @Value("${ehis.client.subsystemCode}")
    protected String clientSubsystemCode;

    @Value("${ehis.service.xRoadInstance}")
    protected String serviceXRoadInstance;
    @Value("${ehis.service.memberClass}")
    protected String serviceMemberClass;
    @Value("${ehis.service.memberCode}")
    protected String serviceMemberCode;
    @Value("${ehis.service.subsystemCode}")
    protected String serviceSubsystemCode;

    static final String LAE_KORGHARIDUS_SERIVCE_CODE = "laeKorgharidus";
    private static final String LAE_KORGHARIDUS_SERVICE = "ehis."+ LAE_KORGHARIDUS_SERIVCE_CODE + ".v1";
    private static final String birthDateEntered = "SS";

    static final String YES = "jah";
    static final String NO = "ei";

    WsEhisStudentLog laeKorgharidused(XRoadHeaderV4 xRoadHeaderV4, KhlOppeasutusList khlOppeasutusList, WsEhisStudentLog wsEhisStudentLog) {
        EhisLaeKorgharidusedResponse ehisLaeKorgharidusedResponse = ehisXroadService.laeKorgharidused(xRoadHeaderV4, khlOppeasutusList);

        wsEhisStudentLog.setRequest(ehisLaeKorgharidusedResponse.getRequest());
        wsEhisStudentLog.setResponse(ehisLaeKorgharidusedResponse.getResponse());
        wsEhisStudentLog.setHasOtherErrors(ehisLaeKorgharidusedResponse.isHasOtherErrors());
        wsEhisStudentLog.setHasXteeErrors(ehisLaeKorgharidusedResponse.isHasXRoadErrors());

        wsEhisStudentLog.setWsName(LAE_KORGHARIDUS_SERVICE);

        String txt = ehisLaeKorgharidusedResponse.getLogTxt().toLowerCase();
        if (txt.contains("viga") || txt.contains("error")) {
            wsEhisStudentLog.setHasOtherErrors(Boolean.TRUE);
        }
        wsEhisStudentLog.setLogTxt(ehisLaeKorgharidusedResponse.getLogTxt());
        return wsEhisStudentLogRepository.save(wsEhisStudentLog);
    }

    static KhlOppeasutusList getKhlOppeasutusList(Student student) {
        KhlOppeasutusList khlOppeasutusList = new KhlOppeasutusList();
        KhlOppeasutus khlOppeasutus = new KhlOppeasutus();

        khlOppeasutus.setKoolId(new BigInteger(student.getSchool().getEhisSchool().getEhisValue()));

        khlOppeasutus.getOppur().add(getKhlOppurMuutmine(student, true));
        khlOppeasutusList.getOppeasutus().add(khlOppeasutus);
        return khlOppeasutusList;
    }

    static KhlOppur getKhlOppurMuutmine(Student student, boolean setOppekava) {
        KhlOppur khlOppur = new KhlOppur();
        KhlMuutmine muutmine = new KhlMuutmine();
        Person person = student.getPerson();

        String personId = getPersonId(person);
        muutmine.setIsikukood(personId);

        if (person.getIdcode() == null) {
            muutmine.setKlIsikukoodRiik(birthDateEntered);
        }

        if (setOppekava) {
            muutmine.setOppekava(getCurriculum(student.getCurriculumVersion()));
        }
        khlOppur.setMuutmine(muutmine);
        return khlOppur;
    }

    static KhlOppur getKhlOppurLisamine(Student student, Directive directive) throws DatatypeConfigurationException {
        Person person = student.getPerson();
        KhlOppur khlOppur = new KhlOppur();
        KhlLisamine lisamine = new KhlLisamine();
        KhlIsikuandmedLisa isikuandmedLisa = new KhlIsikuandmedLisa();

        isikuandmedLisa.setIsikukood(getPersonId(person));

        if (person.getIdcode() == null) {
            isikuandmedLisa.setKlIsikukoodRiik(birthDateEntered);
        }
        if (isikuandmedLisa.getIsikukood() == null) {
            isikuandmedLisa.setSynniKp(getDate(person.getBirthdate(), directive));
        }
        if (person.getSex() != null) {
            isikuandmedLisa.setKlSugu(person.getSex().getEhisValue());
        }
        if ((person.getFirstname() != null) && !person.getFirstname().isEmpty()) {
            isikuandmedLisa.setEesnimi(person.getFirstname());
        }
        if ((person.getLastname() != null) && !person.getLastname().isEmpty()) {
            isikuandmedLisa.setPerenimi(person.getLastname());
        }

        if (person.getCitizenship() != null) {
            isikuandmedLisa.setKlKodakondsus(EntityUtil.getCode(person.getCitizenship()));
        }

        if (person.getResidenceCountry() != null) {
            isikuandmedLisa.setKlElukohamaa(person.getCitizenship().getValue2());
        }

        lisamine.setIsikuandmed(isikuandmedLisa);

        khlOppur.setLisamine(lisamine);
        return khlOppur;
    }

    static XMLGregorianCalendar getDate(LocalDate date, BaseEntityWithId baseEntity) throws DatatypeConfigurationException {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
        } catch (DatatypeConfigurationException e) {
            if (log.isErrorEnabled()) {
                log.error(String.format("Converting date failed on object %s of %s:", baseEntity.getId(), baseEntity.getClass()), e);
            }
            throw e;
        }
    }

    static BigInteger getCurriculum(CurriculumVersion curriculumVersion) {
        return new BigInteger(curriculumVersion.getCurriculum().getMerCode());
    }

    private static String getPersonId(Person person) {
        return (person.getIdcode() != null) ? person.getIdcode() :
                ((person.getForeignIdcode() != null) ? person.getForeignIdcode() :
                        person.getBirthdate().toString());
    }

    WsEhisStudentLog bindingException(Directive directive, Exception e) {
        WsEhisStudentLog studentLog = baseBindingException(e);
        studentLog.setDirective(directive);
        studentLog.setSchool(directive.getSchool());
        return wsEhisStudentLogRepository.save(studentLog);
    }

    WsEhisStudentLog bindingException(Student student, Exception e) {
        WsEhisStudentLog studentLog = baseBindingException(e);
        studentLog.setSchool(student.getSchool());
        return wsEhisStudentLogRepository.save(studentLog);
    }

    private static WsEhisStudentLog baseBindingException(Exception e) {
        WsEhisStudentLog studentLog = new WsEhisStudentLog();
        studentLog.setHasOtherErrors(Boolean.TRUE);
        studentLog.setWsName(LAE_KORGHARIDUS_SERVICE);
        studentLog.setLogTxt(e.toString());
        log.error("Binding failed: ", e);
        return studentLog;
    }

    XRoadHeaderV4 getXroadHeader() {
        XRoadHeaderV4.Client client = new XRoadHeaderV4.Client();
        client.setXRoadInstantce(clientXRoadInstance);
        client.setMemberClass(clientMemberClass);
        client.setMemberCode(clientMemberCode);
        client.setSubSystemCode(clientSubsystemCode);

        XRoadHeaderV4.Service service = new XRoadHeaderV4.Service();
        service.setxRoadInstance(serviceXRoadInstance);
        service.setMemberClass(serviceMemberClass);
        service.setMemberCode(serviceMemberCode);
        service.setSubsystemCode(serviceSubsystemCode);

        XRoadHeaderV4 header = new XRoadHeaderV4();
        header.setClient(client);
        header.setService(service);
        header.setEndpoint(endpoint);
        header.setUserId(user);
        return header;
    }
}
