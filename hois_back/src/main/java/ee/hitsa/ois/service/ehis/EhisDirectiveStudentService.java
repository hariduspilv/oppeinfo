package ee.hitsa.ois.service.ehis;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.xroad.ehis.generated.KhlAkadPuhkusAlgus;
import ee.hois.xroad.ehis.generated.KhlEnnistamine;
import ee.hois.xroad.ehis.generated.KhlKorgharidusLisa;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlOppeasutus;
import ee.hois.xroad.ehis.generated.KhlOppeasutuseLopetamine;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppeasutusestValjaarvamine;
import ee.hois.xroad.ehis.generated.KhlOppekavaMuutus;
import ee.hois.xroad.ehis.generated.KhlOppevormiMuutus;
import ee.hois.xroad.ehis.generated.KhlOppur;

@Transactional
@Service
public class EhisDirectiveStudentService extends EhisService {

    public void updateStudents(Long directiveId) {
        Directive directive = em.getReference(Directive.class, directiveId);
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));

        for (DirectiveStudent directiveStudent : directive.getStudents()) {
            try {
                switch (directiveType) {
                case KASKKIRI_AKAD:
                    startAcademicLeave(directiveStudent);
                    break;
                case KASKKIRI_AKADK:
                    endAcademicLeave(directiveStudent);
                    break;
                case KASKKIRI_EKSMAT:
                    exmatriculation(directiveStudent);
                    break;
                case KASKKIRI_ENNIST:
                    reinstatement(directiveStudent);
                    break;
                case KASKKIRI_LOPET:
                    graduation(directiveStudent);
                    break;
                case KASKKIRI_OKOORM:
                    changeStudyLoad(directiveStudent);
                    break;
                case KASKKIRI_OKAVA:
                    changeCurriculum(directiveStudent);
                    break;
                case KASKKIRI_FINM:
                    changeFinance(directiveStudent);
                    break;
                case KASKKIRI_OVORM:
                    changeStudyForm(directiveStudent);
                    break;
                case KASKKIRI_IMMAT:
                case KASKKIRI_IMMATV:
                    admissionMatriculation(directiveStudent);
                    break;
                default:
                    break;
                }
            } catch (Exception e) {
                bindingException(directive, e);
            }
        }
    }

    private void changeStudyLoad(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
        khlOppevormiMuutus.setMuutusKp(date(directive.getConfirmDate()));
        khlOppevormiMuutus.setKlOppevorm(student.getStudyForm().getEhisValue());
        khlOppevormiMuutus.setKlOppekoormus(directiveStudent.getStudyLoad().getEhisValue());

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppevormiMuutus(khlOppevormiMuutus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, khlOppeasutusList);
    }

    private void changeStudyForm(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        if (!EntityUtil.getCode(student.getStudyForm()).equals(EntityUtil.getCode(directiveStudent.getStudyForm()))) {
            Directive directive = directiveStudent.getDirective();

            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

            KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
            khlOppevormiMuutus.setMuutusKp(date(directive.getConfirmDate()));
            khlOppevormiMuutus.setKlOppevorm(directiveStudent.getStudyForm().getEhisValue());

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            khlKorgharidusMuuda.setOppevormiMuutus(khlOppevormiMuutus);

            khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

            makeRequest(directive, khlOppeasutusList);
        }
    }

    private void changeFinance(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
        khlOppevormiMuutus.setMuutusKp(date(directive.getConfirmDate()));
        // TODO is this correct
        khlOppevormiMuutus.setKlOppevorm(student.getStudyForm().getEhisValue());
        khlOppevormiMuutus.setKlRahastAllikas(directiveStudent.getFinSpecific().getEhisValue());

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppevormiMuutus(khlOppevormiMuutus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, khlOppeasutusList);
    }

    private void changeCurriculum(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine()
                .setOppekava(getCurriculum(directiveStudent.getStudentHistory().getCurriculumVersion()));

        KhlOppekavaMuutus khlOppekavaMuutus = new KhlOppekavaMuutus();
        khlOppekavaMuutus.setMuutusKp(date(directive.getConfirmDate()));
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

        makeRequest(directive, khlOppeasutusList);
    }

    private void exmatriculation(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppeasutusestValjaarvamine khlOppeasutusestValjaarvamine = new KhlOppeasutusestValjaarvamine();
        khlOppeasutusestValjaarvamine.setMuutusKp(date(directive.getConfirmDate()));
        khlOppeasutusestValjaarvamine.setPohjus(directiveStudent.getReason().getEhisValue());

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppeasutusestValjaarvamine(khlOppeasutusestValjaarvamine);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, khlOppeasutusList);
    }

    WsEhisStudentLog graduation(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlOppeasutuseLopetamine oppeasutuseLopetamine = new KhlOppeasutuseLopetamine();
        oppeasutuseLopetamine.setMuutusKp(date(LocalDate.now()));
        // TODO Fix when actually we have a way to get value
        oppeasutuseLopetamine.setLopudokumendiNr("FIXME000001");
        oppeasutuseLopetamine.setCumLaude(yesNo(directiveStudent.getIsCumLaude()));

        Optional.ofNullable(directiveStudent.getCurriculumGrade())
                .map(CurriculumGrade::getEhisGrade)
                .map(Classifier::getEhisValue)
                .ifPresent(oppeasutuseLopetamine::setKlAkadKraad);

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppeasutuseLopetamine(oppeasutuseLopetamine);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        return makeRequest(directiveStudent.getDirective(), khlOppeasutusList);
    }

    private void reinstatement(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = new KhlOppeasutusList();
        KhlOppeasutus khlOppeasutus = new KhlOppeasutus();
        KhlOppur khlOppurOppekava = getKhlOppurMuutmine(student, false);
        khlOppeasutus.getOppur().add(khlOppurOppekava);

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlOppurOppekava.getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        KhlEnnistamine khlEnnistamine = new KhlEnnistamine();
        khlEnnistamine.setMuutusKp(date(directive.getConfirmDate()));

        khlKorgharidusMuuda.setEnnistamine(khlEnnistamine);

        StudentHistory history = directiveStudent.getStudentHistory();
        if (!EntityUtil.getCode(directiveStudent.getStudyForm()).equals(EntityUtil.getCode(history.getStudyForm())) ||
                !EntityUtil.getCode(directiveStudent.getFinSpecific()).equals(EntityUtil.getCode(history.getFinSpecific())) ||
                !EntityUtil.getCode(directiveStudent.getStudyLoad()).equals(EntityUtil.getCode(history.getStudyLoad()))) {

            KhlOppur khlOppurVormiMuutus = getKhlOppurMuutmine(student, true);
            KhlKorgharidusMuuda khlKorgharidusMuudaOppevorm = new KhlKorgharidusMuuda();
            khlOppurVormiMuutus.getMuutmine().setKorgharidus(khlKorgharidusMuudaOppevorm);
            KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
            khlKorgharidusMuudaOppevorm.setOppevormiMuutus(khlOppevormiMuutus);

            // todo: only add changed
            khlOppevormiMuutus.setMuutusKp(date(directive.getConfirmDate()));
            khlOppevormiMuutus.setKlOppevorm(student.getStudyForm().getEhisValue());
            khlOppevormiMuutus.setKlOppekoormus(student.getStudyLoad().getEhisValue());
            khlOppevormiMuutus.setKlRahastAllikas(student.getFinSpecific().getEhisValue());

            khlOppeasutus.getOppur().add(khlOppurVormiMuutus);
        }

        khlOppeasutusList.getOppeasutus().add(khlOppeasutus);
        makeRequest(directive, khlOppeasutusList);
    }

    private void startAcademicLeave(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlAkadPuhkusAlgus khlAkadPuhkusAlgus = new KhlAkadPuhkusAlgus();
        khlAkadPuhkusAlgus.setMuutusKp(date(directive.getConfirmDate()));
        khlAkadPuhkusAlgus.setEeldatavLoppKuupaev(date(directiveStudent.getEndDate()));
        khlAkadPuhkusAlgus.setPohjus(directiveStudent.getReason().getEhisValue());

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setAkadPuhkusAlgus(khlAkadPuhkusAlgus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, khlOppeasutusList);
    }

    private void endAcademicLeave(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setAkadPuhkusLopp(date(directiveStudent.getStartDate()));

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, khlOppeasutusList);
    }

    private void admissionMatriculation(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
        // clear muutmine
        khlOppeasutusList.getOppeasutus().get(0).getOppur().clear();

        KhlOppur khlOppur = getKhlOppurLisamine(student);
        KhlKorgharidusLisa khlKorgharidusLisa = new KhlKorgharidusLisa();

        khlKorgharidusLisa.setOppimaAsumKp(date(student.getStudyStart()));
        khlKorgharidusLisa.setKursus(BigInteger.valueOf(student.getStudentGroup().getCourse().longValue()));
        khlKorgharidusLisa.setOppekava(getCurriculum(student.getCurriculumVersion()));
        khlKorgharidusLisa.setKlOppekeel(student.getLanguage().getEhisValue());
        khlKorgharidusLisa.setKlOppevorm(student.getStudyForm().getEhisValue());
        khlKorgharidusLisa.setKlOppekoormus(student.getStudyLoad().getEhisValue());
        khlKorgharidusLisa.setKlRahastAllikas(student.getFinSpecific().getEhisValue());

        khlKorgharidusLisa.setKlEelnevHaridus(student.getPreviousStudyLevel().getEhisValue());

        khlOppur.getLisamine().setKorgharidus(khlKorgharidusLisa);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().add(khlOppur);

        makeRequest(directive, khlOppeasutusList);
    }

    private WsEhisStudentLog makeRequest(Directive directive, KhlOppeasutusList khlOppeasutusList) {
        WsEhisStudentLog wsEhisStudentLog = new WsEhisStudentLog();
        wsEhisStudentLog.setDirective(directive);
        wsEhisStudentLog.setSchool(directive.getSchool());

        return laeKorgharidused(khlOppeasutusList, wsEhisStudentLog);
    }

    @Override
    protected String getServiceCode() {
        return LAE_KORGHARIDUS_SERVICE_CODE;
    }
}
