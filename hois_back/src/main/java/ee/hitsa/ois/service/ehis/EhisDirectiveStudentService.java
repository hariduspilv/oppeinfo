package ee.hitsa.ois.service.ehis;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hois.xroad.ehis.generated.KhlOppeasutuseLopetamine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.repository.WsEhisStudentLogRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.xroad.ehis.generated.KhlAkadPuhkusAlgus;
import ee.hois.xroad.ehis.generated.KhlEnnistamine;
import ee.hois.xroad.ehis.generated.KhlKorgharidusLisa;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlOppeasutus;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppeasutusestValjaarvamine;
import ee.hois.xroad.ehis.generated.KhlOppekavaMuutus;
import ee.hois.xroad.ehis.generated.KhlOppevormiMuutus;
import ee.hois.xroad.ehis.generated.KhlOppur;
import ee.hois.xroad.helpers.XRoadHeaderV4;

@Transactional
@Service
public class EhisDirectiveStudentService extends EhisService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private DirectiveRepository directiveRepository;

    @Autowired
    private WsEhisStudentLogRepository wsEhisStudentLogRepository;

    @Async
    public void updateEhis(Directive directive) {
        updateStudents(directive.getId());
    }

    @Transactional
    void updateStudents(Long directiveId) {
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
            case KASKKIRI_LOPET:
                for (DirectiveStudent directiveStudent: directive.getStudents()) {
                    try {
                        graduation(directiveStudent, directive);
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
            case KASKKIRI_IMMAT:
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
        WsEhisStudentLog wsEhisStudentLog = new WsEhisStudentLog();
        wsEhisStudentLog.setDirective(directive);
        wsEhisStudentLog.setSchool(directive.getSchool());

        laeKorgharidused(xRoadHeaderV4, khlOppeasutusList, wsEhisStudentLog);
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

    private void graduation(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppeasutuseLopetamine oppeasutuseLopetamine = new KhlOppeasutuseLopetamine();
        oppeasutuseLopetamine.setMuutusKp(getDate(LocalDate.now(), student));
        // todo: Fix when actually we have a way to get value
        oppeasutuseLopetamine.setLopudokumendiNr("FIXME000001");
        oppeasutuseLopetamine.setCumLaude(Boolean.TRUE.equals(directiveStudent.getIsCumLaude()) ? YES : NO);

        Optional.ofNullable(directiveStudent.getCurriculumGrade())
                .map(CurriculumGrade::getEhisGrade)
                .map(Classifier::getEhisValue)
                .ifPresent(oppeasutuseLopetamine::setKlAkadKraad);


        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppeasutuseLopetamine(oppeasutuseLopetamine);
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
                !EntityUtil.getCode(directiveStudent.getFinSpecific()).equals(EntityUtil.getCode(directiveStudent.getStudentHistory().getFinSpecific())) ||
                !EntityUtil.getCode(directiveStudent.getStudyLoad()).equals(EntityUtil.getCode(directiveStudent.getStudentHistory().getStudyLoad()))) {

            KhlOppur khlOppurVormiMuutus = getKhlOppurMuutmine(student, true);
            KhlKorgharidusMuuda khlKorgharidusMuudaOppevorm = new KhlKorgharidusMuuda();
            khlOppurVormiMuutus.getMuutmine().setKorgharidus(khlKorgharidusMuudaOppevorm);
            KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
            khlKorgharidusMuudaOppevorm.setOppevormiMuutus(khlOppevormiMuutus);

            khlOppevormiMuutus.setMuutusKp(getDate(directive.getConfirmDate(), directive));
            khlOppevormiMuutus.setKlOppevorm(student.getStudyForm().getEhisValue());
            khlOppevormiMuutus.setKlOppekoormus(student.getStudyLoad().getEhisValue());
            khlOppevormiMuutus.setKlRahastAllikas(student.getFinSpecific().getEhisValue());

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
        khlKorgharidusMuuda.setAkadPuhkusLopp(getDate(directiveStudent.getStartDate(), directive));

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
    }

    private void admissionMatriculation(DirectiveStudent directiveStudent, Directive directive) throws DatatypeConfigurationException {
        Student student = directiveStudent.getStudent();
        XRoadHeaderV4 xRoadHeaderV4 = getXroadHeader();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
        // clear muutmine
        khlOppeasutusList.getOppeasutus().get(0).getOppur().clear();

        KhlOppur khlOppur = getKhlOppurLisamine(student, directive);
        KhlKorgharidusLisa khlKorgharidusLisa = new KhlKorgharidusLisa();

        khlKorgharidusLisa.setOppimaAsumKp(getDate(student.getStudyStart(), directive));
        khlKorgharidusLisa.setKursus(BigInteger.valueOf(student.getStudentGroup().getCourse()));

        khlKorgharidusLisa.setOppekava(getCurriculum(student.getCurriculumVersion()));
        khlKorgharidusLisa.setKlOppekeel(student.getLanguage().getEhisValue());
        khlKorgharidusLisa.setKlOppevorm(student.getStudyForm().getEhisValue());
        khlKorgharidusLisa.setKlOppekoormus(student.getStudyLoad().getEhisValue());
        khlKorgharidusLisa.setKlRahastAllikas(student.getFinSpecific().getEhisValue());

        khlKorgharidusLisa.setKlEelnevHaridus(student.getPreviousStudyLevel().getEhisValue());

        khlOppur.getLisamine().setKorgharidus(khlKorgharidusLisa);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().add(khlOppur);

        makeRequest(directive, xRoadHeaderV4, khlOppeasutusList);
    }

    protected XRoadHeaderV4 getXroadHeader() {
        XRoadHeaderV4 xRoadHeaderV4 = super.getXroadHeader();
        xRoadHeaderV4.getService().setServiceCode(LAE_KORGHARIDUS_SERIVCE_CODE);
        return xRoadHeaderV4;
    }
}
