package ee.hitsa.ois.service.ehis;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.repository.WsEhisStudentLogRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlMuutmine;
import ee.hois.xroad.ehis.generated.KhlOppeasutus;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppekavaMuutus;
import ee.hois.xroad.ehis.generated.KhlOppevormiMuutus;
import ee.hois.xroad.ehis.generated.KhlOppur;
import ee.hois.xroad.ehis.service.EhisLaeKorgharidusedResponse;
import ee.hois.xroad.ehis.service.EhisXroadService;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;

@Transactional
@Service
public class EhisStudentService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EhisXroadService ehisXroadService;

    @Autowired
    private WsEhisStudentLogRepository wsEhisStudentLogRepository;

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

    private static final String LAE_KORGHARIDUS_SERVICE = "ehis.laeKorgharidus.v1";
    private static final String LAE_KORGHARIDUS_SERIVCE_CODE = "laeKorgharidus";

    @Async
    public void updateStudents(Directive directive) {
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        switch (directiveType) {
            case KASKKIRI_OKOORM:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        changeStudyLoad(directiveStudent, directive);
                    } catch (Exception e) {
                        bindingException(directive, e);
                    }
                }
                break;
            case KASKKIRI_OKAVA:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        changeCurriculum(directiveStudent, directive);
                    } catch (Exception e) {
                        bindingException(directive, e);
                    }
                }
                break;
            case KASKKIRI_FINM:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        changeFinance(directiveStudent, directive);
                    } catch (Exception e) {
                        bindingException(directive, e);
                    }
                }
                break;
            case KASKKIRI_OVORM:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        changeStudyForm(directiveStudent, directive);
                    } catch (Exception e) {
                        bindingException(directive, e);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void bindingException(Directive directive, Exception e) {
        WsEhisStudentLog studentLog = new WsEhisStudentLog();
        studentLog.setDirective(directive);
        studentLog.setHasOtherErrors(Boolean.TRUE);
        studentLog.setWsName(LAE_KORGHARIDUS_SERVICE);
        studentLog.setLogTxt(e.toString());
        studentLog.setSchool(directive.getSchool());
        if (log.isErrorEnabled()) {
            log.error("Binding failed: ", e);
        }
        wsEhisStudentLogRepository.save(studentLog);
    }

    private void changeStudyLoad(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
        setMuutusKp(directive, khlOppevormiMuutus);

        // todo is this correct
        khlOppevormiMuutus.setKlOppevorm(student.getStudyForm().getEhisValue());
        khlOppevormiMuutus.setKlOppekoormus(directiveStudent.getStudyLoad().getEhisValue());

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppevormiMuutus(khlOppevormiMuutus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
    }

    private static void setMuutusKp(Directive directive, KhlOppevormiMuutus khlOppevormiMuutus) throws DatatypeConfigurationException {
        try {
            khlOppevormiMuutus.setMuutusKp(DatatypeFactory.newInstance().newXMLGregorianCalendar(directive.getConfirmDate().toString()));
        } catch (DatatypeConfigurationException e) {
            if (log.isErrorEnabled()) {
                log.error(String.format("Converting date failed on directive %s :", directive.getDirectiveNr()), e);
            }
            throw e;
        }
    }

    private void makeRequest(Directive directive, XRoadHeaderV4 xRoadHeaderV4, KhlOppeasutusList khlOppeasutusList) {
        EhisLaeKorgharidusedResponse ehisLaeKorgharidusedResponse = ehisXroadService.laeKorgharidused(xRoadHeaderV4, khlOppeasutusList);
        WsEhisStudentLog wsEhisStudentLog = new WsEhisStudentLog();
        wsEhisStudentLog.setRequest(ehisLaeKorgharidusedResponse.getRequest());
        wsEhisStudentLog.setResponse(ehisLaeKorgharidusedResponse.getResponse());
        wsEhisStudentLog.setHasOtherErrors(ehisLaeKorgharidusedResponse.isHasOtherErrors());
        wsEhisStudentLog.setHasXteeErrors(ehisLaeKorgharidusedResponse.isHasXRoadErrors());
        wsEhisStudentLog.setDirective(directive);
        wsEhisStudentLog.setWsName(LAE_KORGHARIDUS_SERVICE);
        wsEhisStudentLog.setSchool(directive.getSchool());
        String txt = ehisLaeKorgharidusedResponse.getLogTxt().toLowerCase();
        if (txt.contains("viga") || txt.contains("error")) {
            wsEhisStudentLog.setHasOtherErrors(Boolean.TRUE);
        }
        wsEhisStudentLog.setLogTxt(ehisLaeKorgharidusedResponse.getLogTxt());
        wsEhisStudentLogRepository.save(wsEhisStudentLog);
    }


    private void changeStudyForm(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        if (!EntityUtil.getCode(student.getStudyForm()).equals(EntityUtil.getCode(directiveStudent.getStudyForm()))) {
            XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

            KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
            setMuutusKp(directive, khlOppevormiMuutus);

            khlOppevormiMuutus.setKlOppevorm(directiveStudent.getStudyForm().getEhisValue());

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            khlKorgharidusMuuda.setOppevormiMuutus(khlOppevormiMuutus);

            khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

            makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
        }
    }

    private void changeFinance(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
        setMuutusKp(directive, khlOppevormiMuutus);

        // todo is this correct
        khlOppevormiMuutus.setKlOppevorm(student.getStudyForm().getEhisValue());
        khlOppevormiMuutus.setKlRahastAllikas(directiveStudent.getFinSpecific().getEhisValue());

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppevormiMuutus(khlOppevormiMuutus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);

    }

    private void changeCurriculum(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine()
                .setOppekava(new BigInteger(directiveStudent.getStudentHistory().getCurriculumVersion().getCurriculum().getMerCode()));

        KhlOppekavaMuutus khlOppekavaMuutus = new KhlOppekavaMuutus();
        try {
            khlOppekavaMuutus.setMuutusKp(DatatypeFactory.newInstance().newXMLGregorianCalendar(directive.getConfirmDate().toString()));
        } catch (DatatypeConfigurationException e) {
            if (log.isErrorEnabled()) {
                log.error(String.format("Converting date failed on directive %s :", directive.getDirectiveNr()), e);
            }
            throw e;
        }

        khlOppekavaMuutus.setUusOppekava(new BigInteger(directiveStudent.getCurriculumVersion().getCurriculum().getMerCode()));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppekavaMuutus(khlOppekavaMuutus);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        if (!EntityUtil.getCode(directiveStudent.getStudyForm()).equals(EntityUtil.getCode(directiveStudent.getStudentHistory().getStudyForm()))) {

            KhlOppur khlOppur = getKhlOppur(student);

            KhlKorgharidusMuuda korgharidusMuuda = new KhlKorgharidusMuuda();
            KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
            khlOppevormiMuutus.setMuutusKp(khlOppekavaMuutus.getMuutusKp());
            khlOppevormiMuutus.setKlOppevorm(directiveStudent.getStudyForm().getEhisValue());

            korgharidusMuuda.setOppevormiMuutus(khlOppevormiMuutus);
            khlOppur.getMuutmine().setKorgharidus(korgharidusMuuda);

            khlOppeasutusList.getOppeasutus().get(0).getOppur().add(khlOppur);
        }

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
    }

    private static KhlOppeasutusList getKhlOppeasutusList(Student student) {
        KhlOppeasutusList khlOppeasutusList = new KhlOppeasutusList();
        KhlOppeasutus khlOppeasutus = new KhlOppeasutus();

        khlOppeasutus.setKoolId(new BigInteger(student.getSchool().getEhisSchool().getEhisValue()));

        khlOppeasutus.getOppur().add(getKhlOppur(student));
        khlOppeasutusList.getOppeasutus().add(khlOppeasutus);
        return khlOppeasutusList;
    }

    private static KhlOppur getKhlOppur(Student student) {
        KhlOppur khlOppur = new KhlOppur();
        KhlMuutmine muutmine = new KhlMuutmine();
        Person person = student.getPerson();

        String personId = (person.getIdcode() != null) ? person.getIdcode() :
                ((person.getForeignIdcode() != null) ? person.getForeignIdcode() :
                        person.getBirthdate().toString());
        muutmine.setIsikukood(personId);
        /*
        if (person.getIdcode() == null) {
            // todo: where to get the value
            //muutmine.setKlIsikukoodRiik("");
        }
        */
        muutmine.setOppekava(new BigInteger(student.getCurriculumVersion().getCurriculum().getMerCode()));
        khlOppur.setMuutmine(muutmine);
        return khlOppur;
    }

    private XRoadHeaderV4 getXroadHeader() {
        XRoadHeaderV4 header = new XRoadHeaderV4();
        XRoadHeaderV4.Client client = new XRoadHeaderV4.Client();
        client.setXRoadInstantce(clientXRoadInstance);
        client.setMemberClass(clientMemberClass);
        client.setMemberCode(clientMemberCode);
        client.setSubSystemCode(clientSubsystemCode);
        XRoadHeaderV4.Service service = new XRoadHeaderV4.Service();
        service.setxRoadInstance(serviceXRoadInstance);
        service.setMemberClass(serviceMemberClass);
        service.setMemberCode(serviceMemberCode);
        service.setServiceCode(LAE_KORGHARIDUS_SERIVCE_CODE);
        service.setSubsystemCode(serviceSubsystemCode);
        header.setClient(client);
        header.setService(service);
        header.setEndpoint(endpoint);
        header.setUserId(user);
        return header;
    }
}
