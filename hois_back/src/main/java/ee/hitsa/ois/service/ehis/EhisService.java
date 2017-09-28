package ee.hitsa.ois.service.ehis;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.exception.BadConfigurationException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hois.soap.LogContext;
import ee.hois.xroad.ehis.generated.KhlIsikuandmedLisa;
import ee.hois.xroad.ehis.generated.KhlLisamine;
import ee.hois.xroad.ehis.generated.KhlMuutmine;
import ee.hois.xroad.ehis.generated.KhlOppeasutus;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppur;
import ee.hois.xroad.ehis.service.EhisClient;
import ee.hois.xroad.ehis.service.EhisLaeKorgharidusedResponse;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.time.LocalDate;

public abstract class EhisService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    static final String LAE_KORGHARIDUS_SERVICE_CODE = "laeKorgharidus";
    public static final String LAE_KORGHARIDUS_SERVICE = "ehis."+ LAE_KORGHARIDUS_SERVICE_CODE + ".v1";
    private static final String birthDateEntered = "SS";

    private DatatypeFactory datatypeFactory;
    @Autowired
    protected EhisClient ehisClient;
    @Autowired
    protected EhisLogService ehisLogService;
    @Autowired
    protected EntityManager em;

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

    @PostConstruct
    public void postConstruct() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new BadConfigurationException("Unable to create data type factory", e);
        }
    }

    protected WsEhisStudentLog laeKorgharidused(KhlOppeasutusList khlOppeasutusList, WsEhisStudentLog wsEhisStudentLog) {
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        EhisLaeKorgharidusedResponse response = ehisClient.laeKorgharidused(xRoadHeaderV4, khlOppeasutusList);

        LogContext queryLog = response.getLog();
        wsEhisStudentLog.setHasOtherErrors(Boolean.FALSE);
        wsEhisStudentLog.setHasXteeErrors(Boolean.valueOf(queryLog.getError() != null));

        if(!response.hasError()) {
            wsEhisStudentLog.setLogTxt(String.join(";", StreamUtil.nullSafeList(response.getResult())));
        }
        return ehisLogService.insert(queryLog, wsEhisStudentLog);
    }

    protected static KhlOppeasutusList getKhlOppeasutusList(Student student) {
        KhlOppeasutusList khlOppeasutusList = new KhlOppeasutusList();
        KhlOppeasutus khlOppeasutus = new KhlOppeasutus();

        khlOppeasutus.setKoolId(new BigInteger(student.getSchool().getEhisSchool().getEhisValue()));

        khlOppeasutus.getOppur().add(getKhlOppurMuutmine(student, true));
        khlOppeasutusList.getOppeasutus().add(khlOppeasutus);
        return khlOppeasutusList;
    }

    protected static KhlOppur getKhlOppurMuutmine(Student student, boolean setOppekava) {
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

    protected KhlOppur getKhlOppurLisamine(Student student) {
        Person person = student.getPerson();
        KhlOppur khlOppur = new KhlOppur();
        KhlLisamine lisamine = new KhlLisamine();
        KhlIsikuandmedLisa isikuandmedLisa = new KhlIsikuandmedLisa();

        isikuandmedLisa.setIsikukood(getPersonId(person));

        if (person.getIdcode() == null) {
            isikuandmedLisa.setKlIsikukoodRiik(birthDateEntered);
        }
        if (isikuandmedLisa.getIsikukood() == null) {
            isikuandmedLisa.setSynniKp(date(person.getBirthdate()));
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

    protected XMLGregorianCalendar date(LocalDate date) {
        if(date == null) {
            return null;
        }
        return datatypeFactory.newXMLGregorianCalendar(date.toString());
    }

    protected static String code(Classifier classifier) {
        return EntityUtil.getNullableCode(classifier);
    }

    protected static String ehisValue(Classifier classifier) {
        return classifier != null ? classifier.getEhisValue() : null;
    }

    protected static String value(Classifier classifier) {
        return classifier != null ? classifier.getValue() : null;
    }

    protected static String value2(Classifier classifier) {
        return classifier != null ? classifier.getValue2() : null;
    }

    protected static String yesNo(Boolean value) {
        return Boolean.TRUE.equals(value) ? "jah" : "ei";
    }

    protected static BigInteger getCurriculum(CurriculumVersion curriculumVersion) {
        String merCode = curriculumVersion.getCurriculum().getMerCode();
        return merCode != null ? new BigInteger(merCode) : null;
    }

    private static String getPersonId(Person person) {
        return (person.getIdcode() != null) ? person.getIdcode() :
                ((person.getForeignIdcode() != null) ? person.getForeignIdcode() :
                        person.getBirthdate().toString());
    }

    protected WsEhisStudentLog bindingException(Directive directive, Exception e) {
        WsEhisStudentLog studentLog = baseBindingException(e);
        studentLog.setDirective(directive);
        studentLog.setSchool(directive.getSchool());
        LogContext logContext = new LogContext(null, LAE_KORGHARIDUS_SERVICE);
        logContext.setError(e);
        return ehisLogService.insert(logContext, studentLog);
    }

    protected WsEhisStudentLog bindingException(Student student, Exception e) {
        WsEhisStudentLog studentLog = baseBindingException(e);
        studentLog.setSchool(student.getSchool());
        LogContext logContext = new LogContext(null, LAE_KORGHARIDUS_SERVICE);
        logContext.setError(e);
        return ehisLogService.insert(logContext, studentLog);
    }

    private static WsEhisStudentLog baseBindingException(Exception e) {
        WsEhisStudentLog studentLog = new WsEhisStudentLog();
        studentLog.setHasOtherErrors(Boolean.TRUE);
        studentLog.setLogTxt(e.toString());
        log.error("Binding failed: ", e);
        return studentLog;
    }

    protected XRoadHeaderV4 getXroadHeader() {
        XRoadHeaderV4.Client client = new XRoadHeaderV4.Client();
        client.setXRoadInstantce(clientXRoadInstance);
        client.setMemberClass(clientMemberClass);
        client.setMemberCode(clientMemberCode);
        client.setSubSystemCode(clientSubsystemCode);

        XRoadHeaderV4.Service service = new XRoadHeaderV4.Service();
        service.setxRoadInstance(serviceXRoadInstance);
        service.setMemberClass(serviceMemberClass);
        service.setMemberCode(serviceMemberCode);
        service.setServiceCode(getServiceCode());
        service.setSubsystemCode(serviceSubsystemCode);

        XRoadHeaderV4 header = new XRoadHeaderV4();
        header.setClient(client);
        header.setService(service);
        header.setEndpoint(endpoint);
        header.setUserId(user);
        return header;
    }

    protected abstract String getServiceCode();
}
