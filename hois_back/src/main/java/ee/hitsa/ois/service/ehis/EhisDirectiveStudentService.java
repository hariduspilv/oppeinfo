package ee.hitsa.ois.service.ehis;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JobType;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.xroad.ehis.generated.KhlAkadPuhkusAlgus;
import ee.hois.xroad.ehis.generated.KhlEnnistamine;
import ee.hois.xroad.ehis.generated.KhlKorgharidusLisa;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlLyhiajaliseltValismaal;
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

    private static final String SUBJECT_POINTS = "0";

    public void updateStudents(Long directiveId) {
        Directive directive = em.getReference(Directive.class, directiveId);
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));

        for (DirectiveStudent directiveStudent : directive.getStudents()) {
            if(Boolean.TRUE.equals(directiveStudent.getCanceled())) {
                continue;
            }
            try {
                switch (directiveType) {
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

    public void updateStudentStatus(Job job) {
        JobType jobType = JobType.valueOf(EntityUtil.getCode(job.getType()));
        Directive directive = em.getReference(Directive.class, EntityUtil.getId(job.getDirective()));

        for (DirectiveStudent directiveStudent : directive.getStudents()) {
            if(Boolean.TRUE.equals(directiveStudent.getCanceled())) {
                continue;
            }
            try {
                switch(jobType) {
                case JOB_AKAD_MINEK:
                    startAcademicLeave(directiveStudent);
                    break;
                case JOB_AKAD_KATK:
                    endAcademicLeave(directiveStudent, true);
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
        khlOppevormiMuutus.setKlOppevorm(ehisValue(student.getStudyForm()));
        khlOppevormiMuutus.setKlOppekoormus(ehisValue(directiveStudent.getStudyLoad()));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppevormiMuutus(khlOppevormiMuutus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, khlOppeasutusList);
    }

    private void changeStudyForm(DirectiveStudent directiveStudent) {
        if (!Objects.equals(EntityUtil.getNullableCode(directiveStudent.getStudentHistory().getStudyForm()), EntityUtil.getNullableCode(directiveStudent.getStudyForm()))) {
            Directive directive = directiveStudent.getDirective();
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(directiveStudent.getStudent());

            KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
            khlOppevormiMuutus.setMuutusKp(date(directive.getConfirmDate()));
            khlOppevormiMuutus.setKlOppevorm(ehisValue(directiveStudent.getStudyForm()));

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
        khlOppevormiMuutus.setKlOppevorm(ehisValue(student.getStudyForm()));
        khlOppevormiMuutus.setKlRahastAllikas(ehisValue(directiveStudent.getFinSpecific()));

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
                .setOppekava(curriculumCode(directiveStudent.getStudentHistory().getCurriculumVersion().getCurriculum()));

        KhlOppekavaMuutus khlOppekavaMuutus = new KhlOppekavaMuutus();
        khlOppekavaMuutus.setMuutusKp(date(directive.getConfirmDate()));
        khlOppekavaMuutus.setUusOppekava(curriculumCode(directiveStudent.getCurriculumVersion().getCurriculum()));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppekavaMuutus(khlOppekavaMuutus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        if (!Objects.equals(EntityUtil.getNullableCode(directiveStudent.getStudyForm()), EntityUtil.getNullableCode(directiveStudent.getStudentHistory().getStudyForm()))) {
            KhlOppur khlOppur = getKhlOppurMuutmine(student, true);

            KhlKorgharidusMuuda korgharidusMuuda = new KhlKorgharidusMuuda();
            KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
            khlOppevormiMuutus.setMuutusKp(khlOppekavaMuutus.getMuutusKp());
            khlOppevormiMuutus.setKlOppevorm(ehisValue(directiveStudent.getStudyForm()));

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
        khlOppeasutusestValjaarvamine.setPohjus(ehisValue(directiveStudent.getReason()));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppeasutusestValjaarvamine(khlOppeasutusestValjaarvamine);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, khlOppeasutusList);
    }

    WsEhisStudentLog graduation(DirectiveStudent directiveStudent) {
        try {
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
        } catch (Exception e) {
            return bindingException(directiveStudent.getDirective(), e);
        }
    }

    private void reinstatement(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = new KhlOppeasutusList();
        KhlOppeasutus khlOppeasutus = new KhlOppeasutus();
        String koolId = ehisValue(student.getSchool().getEhisSchool());
        khlOppeasutus.setKoolId(koolId != null ? new BigInteger(koolId) : null);
        KhlOppur khlOppurOppekava = getKhlOppurMuutmine(student, false);
        khlOppeasutus.getOppur().add(khlOppurOppekava);

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlOppurOppekava.getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        KhlEnnistamine khlEnnistamine = new KhlEnnistamine();
        khlEnnistamine.setUusOppekava(curriculumCode(student.getCurriculumVersion().getCurriculum()));
        khlEnnistamine.setMuutusKp(date(directive.getConfirmDate()));

        khlKorgharidusMuuda.setEnnistamine(khlEnnistamine);

        StudentHistory history = directiveStudent.getStudentHistory();
        if (!Objects.equals(EntityUtil.getNullableCode(directiveStudent.getStudyForm()), EntityUtil.getNullableCode(history.getStudyForm())) ||
            !Objects.equals(EntityUtil.getNullableCode(directiveStudent.getFinSpecific()), EntityUtil.getNullableCode(history.getFinSpecific())) ||
            !Objects.equals(EntityUtil.getNullableCode(directiveStudent.getStudyLoad()), EntityUtil.getNullableCode(history.getStudyLoad()))) {

            KhlOppur khlOppurVormiMuutus = getKhlOppurMuutmine(student, true);
            KhlKorgharidusMuuda khlKorgharidusMuudaOppevorm = new KhlKorgharidusMuuda();
            khlOppurVormiMuutus.getMuutmine().setKorgharidus(khlKorgharidusMuudaOppevorm);
            KhlOppevormiMuutus khlOppevormiMuutus = new KhlOppevormiMuutus();
            khlKorgharidusMuudaOppevorm.setOppevormiMuutus(khlOppevormiMuutus);

            // todo: only add changed
            khlOppevormiMuutus.setMuutusKp(date(directive.getConfirmDate()));
            khlOppevormiMuutus.setKlOppevorm(ehisValue(student.getStudyForm()));
            khlOppevormiMuutus.setKlOppekoormus(ehisValue(student.getStudyLoad()));
            khlOppevormiMuutus.setKlRahastAllikas(ehisValue(student.getFinSpecific()));

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
        khlAkadPuhkusAlgus.setMuutusKp(date(DateUtils.periodStart(directiveStudent)));
        khlAkadPuhkusAlgus.setEeldatavLoppKuupaev(date(DateUtils.periodEnd(directiveStudent)));
        khlAkadPuhkusAlgus.setPohjus(ehisValue(directiveStudent.getReason()));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setAkadPuhkusAlgus(khlAkadPuhkusAlgus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        makeRequest(directive, khlOppeasutusList);
    }

    private void endAcademicLeave(DirectiveStudent directiveStudent, boolean cancel) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setAkadPuhkusLopp(date(cancel ? directiveStudent.getStartDate() : DateUtils.periodEnd(directiveStudent)));

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
        Short course = student.getStudentGroup().getCourse();
        khlKorgharidusLisa.setKursus(course != null ? BigInteger.valueOf(course.longValue()) : null);
        khlKorgharidusLisa.setOppekava(curriculumCode(student.getCurriculumVersion().getCurriculum()));
        khlKorgharidusLisa.setKlOppekeel(ehisValue(student.getLanguage()));
        khlKorgharidusLisa.setKlOppevorm(ehisValue(student.getStudyForm()));
        khlKorgharidusLisa.setKlOppekoormus(ehisValue(student.getStudyLoad()));
        khlKorgharidusLisa.setKlRahastAllikas(ehisValue(student.getFinSpecific()));

        khlKorgharidusLisa.setKlEelnevHaridus(ehisValue(student.getPreviousStudyLevel()));

        khlOppur.getLisamine().setKorgharidus(khlKorgharidusLisa);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().add(khlOppur);

        makeRequest(directive, khlOppeasutusList);
    }

    public WsEhisStudentLog foreignStudy(DirectiveStudent directiveStudent) {
        try {
            Student student = directiveStudent.getStudent();
            Directive directive = directiveStudent.getDirective();

            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
            KhlLyhiajaliseltValismaal khlLyhiajaliseltValismaal = new KhlLyhiajaliseltValismaal();
            khlLyhiajaliseltValismaal.setMuutusKp(date(directive.getConfirmDate()));
            khlLyhiajaliseltValismaal.setPerioodAlates(date(directiveStudent.getStartDate()));
            khlLyhiajaliseltValismaal.setPerioodKuni(date(directiveStudent.getEndDate()));
            khlLyhiajaliseltValismaal.setKlEesmark(ehisValue(directiveStudent.getAbroadPurpose()));
            // TODO replace with real data
            khlLyhiajaliseltValismaal.setAinepunkte(SUBJECT_POINTS);
            khlLyhiajaliseltValismaal.setNominaalajaPikendamine(BigInteger.ZERO);
            khlLyhiajaliseltValismaal.setOppeasutuseNimi(Boolean.TRUE.equals(directiveStudent.getIsAbroad()) ? directiveStudent.getAbroadSchool() : name(directiveStudent.getEhisSchool()));
            khlLyhiajaliseltValismaal.setKlSihtriik(value2(directiveStudent.getCountry()));
            khlLyhiajaliseltValismaal.setKlProgramm(ehisValue(directiveStudent.getAbroadProgramme()));

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            khlKorgharidusMuuda.setLyhiajaliseltValismaal(khlLyhiajaliseltValismaal);
            khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

            return makeRequest(directive, khlOppeasutusList);
        } catch(Exception e) {
            return bindingException(directiveStudent.getDirective(), e);
        }
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
