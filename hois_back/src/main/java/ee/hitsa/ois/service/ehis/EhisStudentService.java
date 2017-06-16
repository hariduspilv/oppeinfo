package ee.hitsa.ois.service.ehis;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.repository.WsEhisStudentLogRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.xroad.ehis.generated.KhlAkadPuhkusAlgus;
import ee.hois.xroad.ehis.generated.KhlEnnistamine;
import ee.hois.xroad.ehis.generated.KhlIsikuandmedLisa;
import ee.hois.xroad.ehis.generated.KhlKorgharidusLisa;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlLisamine;
import ee.hois.xroad.ehis.generated.KhlMuutmine;
import ee.hois.xroad.ehis.generated.KhlOppeasutus;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppeasutusestValjaarvamine;
import ee.hois.xroad.ehis.generated.KhlOppekavaMuutus;
import ee.hois.xroad.ehis.generated.KhlOppevormiMuutus;
import ee.hois.xroad.ehis.generated.KhlOppur;
import ee.hois.xroad.ehis.service.EhisLaeKorgharidusedResponse;
import ee.hois.xroad.ehis.service.EhisXroadService;
import ee.hois.xroad.helpers.XRoadHeaderV4;

@Transactional
@Service
public class EhisStudentService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EhisXroadService ehisXroadService;

    @Autowired
    private DirectiveRepository directiveRepository;

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

    private static final String LAE_KORGHARIDUS_SERIVCE_CODE = "laeKorgharidus";
    private static final String LAE_KORGHARIDUS_SERVICE = "ehis."+ LAE_KORGHARIDUS_SERIVCE_CODE + ".v1";
    private static final String birthDateEntered = "SS";

    @Async
    public void updateEhis(Directive directive) {
        updateStudents(directive.getId());
    }

    @Transactional
    private void updateStudents(Long directiveId) {
        Directive directive = directiveRepository.getOne(directiveId);
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        switch (directiveType) {
            case KASKKIRI_AKAD:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        startAcademicLeave(directiveStudent, directive);
                    } catch (Exception e) {
                        bindingException(directive, e);
                    }
                }
                break;
            case KASKKIRI_AKADK:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        endAcademicLeave(directiveStudent, directive);
                    } catch (Exception e) {
                        bindingException(directive, e);
                    }
                }
                break;
            case KASKKIRI_EKSMAT:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        exmatriculation(directiveStudent, directive);
                    } catch (Exception e) {
                        bindingException(directive, e);
                    }
                }
                break;
            case KASKKIRI_ENNIST:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        reinstatement(directiveStudent, directive);
                    } catch (Exception e) {
                        bindingException(directive, e);
                    }
                }
                break;
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
            case KASKKIRI_IMMATV:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        admissionMatriculation(directiveStudent, directive);
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
        log.error("Binding failed: ", e);

        wsEhisStudentLogRepository.save(studentLog);
    }

    private void changeStudyLoad(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();

        khlOppevormiMuutus.setMuutusKp(getDate(directive.getConfirmDate(), directive));

        khlOppevormiMuutus.setKlOppevorm(student.getStudyForm().getEhisValue());
        khlOppevormiMuutus.setKlOppekoormus(directiveStudent.getStudyLoad().getEhisValue());

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppevormiMuutus(khlOppevormiMuutus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
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
            khlOppevormiMuutus.setMuutusKp(getDate(directive.getConfirmDate(), directive));

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
        khlOppevormiMuutus.setMuutusKp(getDate(directive.getConfirmDate(), directive));

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
                .setOppekava(getCurriculum(directiveStudent.getStudentHistory().getCurriculumVersion()));

        KhlOppekavaMuutus khlOppekavaMuutus = new KhlOppekavaMuutus();
        khlOppekavaMuutus.setMuutusKp(getDate(directive.getConfirmDate(), directive));

        khlOppekavaMuutus.setUusOppekava(getCurriculum(directiveStudent.getCurriculumVersion()));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppekavaMuutus(khlOppekavaMuutus);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        if (!EntityUtil.getCode(directiveStudent.getStudyForm()).equals(EntityUtil.getCode(directiveStudent.getStudentHistory().getStudyForm()))) {

            KhlOppur khlOppur = getKhlOppurMuutmine(student, true);

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

    private void exmatriculation(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppeasutusestValjaarvamine khlOppeasutusestValjaarvamine = new KhlOppeasutusestValjaarvamine();
        khlOppeasutusestValjaarvamine.setMuutusKp(getDate(directive.getConfirmDate(), directive));

        khlOppeasutusestValjaarvamine.setPohjus(directiveStudent.getReason().getEhisValue());

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppeasutusestValjaarvamine(khlOppeasutusestValjaarvamine);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
    }

    private void reinstatement(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();

        KhlOppeasutusList khlOppeasutusList = new KhlOppeasutusList();
        KhlOppeasutus khlOppeasutus = new KhlOppeasutus();
        KhlOppur khlOppurOppekava = getKhlOppurMuutmine(student, false);
        khlOppeasutus.getOppur().add(khlOppurOppekava);

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlOppurOppekava.getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        KhlEnnistamine khlEnnistamine = new KhlEnnistamine();
        khlEnnistamine.setMuutusKp(getDate(directive.getConfirmDate(), directive));

        khlKorgharidusMuuda.setEnnistamine(khlEnnistamine);

        if (!EntityUtil.getCode(directiveStudent.getStudyForm()).equals(EntityUtil.getCode(directiveStudent.getStudentHistory().getStudyForm())) ||
                !EntityUtil.getCode(directiveStudent.getFin()).equals(EntityUtil.getCode(directiveStudent.getStudentHistory().getFin())) ||
                !EntityUtil.getCode(directiveStudent.getStudyLoad()).equals(EntityUtil.getCode(directiveStudent.getStudentHistory().getStudyLoad()))) {

            KhlOppur khlOppurVormiMuutus = getKhlOppurMuutmine(student, true);
            KhlKorgharidusMuuda khlKorgharidusMuudaOppevorm = new KhlKorgharidusMuuda();
            khlOppurVormiMuutus.getMuutmine().setKorgharidus(khlKorgharidusMuudaOppevorm);
            KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
            khlKorgharidusMuudaOppevorm.setOppevormiMuutus(khlOppevormiMuutus);

            khlOppevormiMuutus.setMuutusKp(getDate(directive.getConfirmDate(), directive));
            khlOppevormiMuutus.setKlOppevorm(student.getStudyForm().getEhisValue());
            khlOppevormiMuutus.setKlOppekoormus(student.getStudyLoad().getEhisValue());
            khlOppevormiMuutus.setKlRahastAllikas(student.getFin().getEhisValue());

            khlOppeasutus.getOppur().add(khlOppurVormiMuutus);
        }

        khlOppeasutusList.getOppeasutus().add(khlOppeasutus);
        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
    }

    private void startAcademicLeave(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlAkadPuhkusAlgus khlAkadPuhkusAlgus = new KhlAkadPuhkusAlgus();
        khlAkadPuhkusAlgus.setMuutusKp(getDate(directive.getConfirmDate(), directive));
        khlAkadPuhkusAlgus.setEeldatavLoppKuupaev(getDate(directiveStudent.getEndDate(), directive));
        khlAkadPuhkusAlgus.setPohjus(directiveStudent.getReason().getEhisValue());

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setAkadPuhkusAlgus(khlAkadPuhkusAlgus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
    }

    private void endAcademicLeave(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setAkadPuhkusLopp(getDate(directiveStudent.getEndDate(), directive));

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
    }

    private void admissionMatriculation(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppur khlOppur = getKhlOppurLisamine(student, directive);
        KhlKorgharidusLisa khlKorgharidusLisa = new KhlKorgharidusLisa();

        khlKorgharidusLisa.setOppimaAsumKp(getDate(student.getStudyStart(), directive));
        khlKorgharidusLisa.setKursus(BigInteger.valueOf(student.getStudentGroup().getCourse().longValue()));

        khlKorgharidusLisa.setOppekava(getCurriculum(student.getCurriculumVersion()));
        khlKorgharidusLisa.setKlOppekeel(student.getLanguage().getEhisValue());
        khlKorgharidusLisa.setKlOppevorm(student.getStudyForm().getEhisValue());
        khlKorgharidusLisa.setKlOppekoormus(student.getStudyLoad().getEhisValue());
        khlKorgharidusLisa.setKlRahastAllikas(student.getFin().getEhisValue());

        khlKorgharidusLisa.setKlEelnevHaridus(student.getPreviousStudyLevel().getEhisValue());

        khlOppur.getLisamine().setKorgharidus(khlKorgharidusLisa);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().add(khlOppur);

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
    }

    private static KhlOppeasutusList getKhlOppeasutusList(Student student) {
        KhlOppeasutusList khlOppeasutusList = new KhlOppeasutusList();
        KhlOppeasutus khlOppeasutus = new KhlOppeasutus();

        khlOppeasutus.setKoolId(new BigInteger(student.getSchool().getEhisSchool().getEhisValue()));

        khlOppeasutus.getOppur().add(getKhlOppurMuutmine(student, true));
        khlOppeasutusList.getOppeasutus().add(khlOppeasutus);
        return khlOppeasutusList;
    }

    private static KhlOppur getKhlOppurLisamine(Student student, Directive directive) throws DatatypeConfigurationException {
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
        if (person.getSex() == null) {
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

    private static KhlOppur getKhlOppurMuutmine(Student student, boolean setOppekava) {
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

    private static XMLGregorianCalendar getDate(LocalDate date, Directive directive) throws DatatypeConfigurationException {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
        } catch (DatatypeConfigurationException e) {
            if (log.isErrorEnabled()) {
                log.error(String.format("Converting date failed on directive %s :", directive.getDirectiveNr()), e);
            }
            throw e;
        }
    }

    private static BigInteger getCurriculum(CurriculumVersion curriculumVersion) {
        return new BigInteger(curriculumVersion.getCurriculum().getMerCode());
    }

    private static String getPersonId(Person person) {
        return (person.getIdcode() != null) ? person.getIdcode() :
                ((person.getForeignIdcode() != null) ? person.getForeignIdcode() :
                        person.getBirthdate().toString());
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
        service.setServiceCode(LAE_KORGHARIDUS_SERIVCE_CODE);
        service.setSubsystemCode(serviceSubsystemCode);

        XRoadHeaderV4 header = new XRoadHeaderV4();
        header.setClient(client);
        header.setService(service);
        header.setEndpoint(endpoint);
        header.setUserId(user);
        return header;
    }
}
