package ee.hitsa.ois.service.ehis;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
import ee.hitsa.ois.enums.StudyLoad;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.xroad.ehis.generated.KhlAkadPuhkusAlgus;
import ee.hois.xroad.ehis.generated.KhlEnnistamine;
import ee.hois.xroad.ehis.generated.KhlErivajadusedArr;
import ee.hois.xroad.ehis.generated.KhlKorgharidusLisa;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlLyhiajaliseltValismaal;
import ee.hois.xroad.ehis.generated.KhlOiendType;
import ee.hois.xroad.ehis.generated.KhlOppeasutus;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppeasutuseLopetamine;
import ee.hois.xroad.ehis.generated.KhlOppeasutusestValjaarvamine;
import ee.hois.xroad.ehis.generated.KhlOppekavaMuutus;
import ee.hois.xroad.ehis.generated.KhlOppevormiMuutus;
import ee.hois.xroad.ehis.generated.KhlOppur;

@Transactional
@Service
public class EhisDirectiveStudentService extends EhisService {

    public void updateStudents(Job job) {
        Directive directive = em.getReference(Directive.class, EntityUtil.getId(job.getDirective()));
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        Long studentId = EntityUtil.getNullableId(job.getStudent());

        for (DirectiveStudent directiveStudent : directive.getStudents()) {
            if(Boolean.TRUE.equals(directiveStudent.getCanceled()) || (studentId != null && !studentId.equals(EntityUtil.getId(directiveStudent.getStudent())))) {
                continue;
            }
            try {
                switch (directiveType) {
                case KASKKIRI_AKAD:
                    // verify ehis task is for single student only
                    if(studentId != null) {
                        startAcademicLeave(directiveStudent);
                    }
                    break;
                case KASKKIRI_AKADK:
                    // verify ehis task is for single student only
                    if(studentId != null) {
                        endAcademicLeave(directiveStudent, true);
                    }
                    break;
                case KASKKIRI_EKSMAT:
                    exmatriculation(directiveStudent);
                    break;
                case KASKKIRI_ENNIST:
                    reinstatement(directiveStudent);
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
                case KASKKIRI_TUGI:
                    setSpecialNeeds(directiveStudent);
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
        Student student = directiveStudent.getStudent();
        StudentHistory history = getStudentHistory(student);
        if (!Objects.equals(EntityUtil.getNullableCode(history.getStudyForm()), EntityUtil.getNullableCode(directiveStudent.getStudyForm()))) {
            Directive directive = directiveStudent.getDirective();
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

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
        StudentHistory history = getStudentHistory(student);

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine()
                .setOppekava(requiredCurriculumCode(history.getCurriculumVersion().getCurriculum(), student));

        KhlOppekavaMuutus khlOppekavaMuutus = new KhlOppekavaMuutus();
        khlOppekavaMuutus.setMuutusKp(date(directive.getConfirmDate()));
        khlOppekavaMuutus.setUusOppekava(requiredCurriculumCode(directiveStudent.getCurriculumVersion().getCurriculum(), student));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setOppekavaMuutus(khlOppekavaMuutus);

        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        if (!Objects.equals(EntityUtil.getNullableCode(directiveStudent.getStudyForm()), EntityUtil.getNullableCode(history.getStudyForm()))) {
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

    WsEhisStudentLog graduation(DirectiveStudent directiveStudent, String docNr, 
            String academicNr, List<String> extraNr, String academicNrEn, List<String> extraNrEn) {
        try {
            Student student = directiveStudent.getStudent();
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

            KhlOppeasutuseLopetamine oppeasutuseLopetamine = new KhlOppeasutuseLopetamine();
            oppeasutuseLopetamine.setMuutusKp(date(LocalDate.now()));
            oppeasutuseLopetamine.setLopudokumendiNr(docNr);
            KhlOiendType oiend = new KhlOiendType();
            oiend.setOiendiNr(academicNr);
            if (extraNr != null) {
                int size = extraNr.size();
                if (size > 0) {
                    oiend.setLisaleht1Nr(extraNr.get(0));
                }
                if (size > 1) {
                    oiend.setLisaleht2Nr(extraNr.get(1));
                }
                if (size > 2) {
                    oiend.setLisaleht3Nr(extraNr.get(2));
                }
                if (size > 3) {
                    oiend.setLisaleht4Nr(extraNr.get(3));
                }
            }
            oppeasutuseLopetamine.setEestikeelneAkademOiend(oiend);
            
            if (academicNrEn != null) {
                KhlOppeasutuseLopetamine oppeasutuseLopetamineEn = new KhlOppeasutuseLopetamine();
                oppeasutuseLopetamineEn.setMuutusKp(date(LocalDate.now()));
                oppeasutuseLopetamineEn.setLopudokumendiNr(docNr);
                KhlOiendType oiendEn = new KhlOiendType();
                oiendEn.setOiendiNr(academicNrEn);
                if (extraNrEn != null) {
                    int size = extraNrEn.size();
                    if (size > 0) {
                        oiendEn.setLisaleht1Nr(extraNrEn.get(0));
                    }
                    if (size > 1) {
                        oiendEn.setLisaleht2Nr(extraNrEn.get(1));
                    }
                    if (size > 2) {
                        oiendEn.setLisaleht3Nr(extraNrEn.get(2));
                    }
                    if (size > 3) {
                        oiendEn.setLisaleht4Nr(extraNrEn.get(3));
                    }
                }
                oppeasutuseLopetamine.setInglisekeelneAkademOiend(oiendEn);
            }
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
        khlEnnistamine.setUusOppekava(requiredCurriculumCode(student));
        khlEnnistamine.setMuutusKp(date(directive.getConfirmDate()));

        khlKorgharidusMuuda.setEnnistamine(khlEnnistamine);

        StudentHistory history = getStudentHistory(student);
        if (!Objects.equals(EntityUtil.getNullableCode(student.getStudyForm()), EntityUtil.getNullableCode(history.getStudyForm())) ||
            !Objects.equals(EntityUtil.getNullableCode(student.getFinSpecific()), EntityUtil.getNullableCode(history.getFinSpecific())) ||
            !Objects.equals(EntityUtil.getNullableCode(student.getStudyLoad()), EntityUtil.getNullableCode(history.getStudyLoad()))) {

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
        khlKorgharidusLisa.setOppekava(requiredCurriculumCode(student));
        khlKorgharidusLisa.setKlOppekeel(ehisValue(student.getLanguage()));
        khlKorgharidusLisa.setKlOppevorm(ehisValue(student.getStudyForm()));
        Classifier studyLoad = student.getStudyLoad() == null ? em.getReference(Classifier.class, StudyLoad.OPPEKOORMUS_MTA.name()) : student.getStudyLoad();
        khlKorgharidusLisa.setKlOppekoormus(ehisValue(studyLoad));
        khlKorgharidusLisa.setKlRahastAllikas(ehisValue(student.getFinSpecific()));
        khlKorgharidusLisa.setKlEelnevHaridus(ehisValue(student.getPreviousStudyLevel()));
        khlKorgharidusLisa.setKlYhiselamu(ehisValue(student.getDormitory()));

        khlOppur.getLisamine().setKorgharidus(khlKorgharidusLisa);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().add(khlOppur);

        makeRequest(directive, khlOppeasutusList);
    }

    public WsEhisStudentLog foreignStudy(DirectiveStudent directiveStudent, BigDecimal points, Integer nominalStudyExtension) {
        try {
            Student student = directiveStudent.getStudent();
            Directive directive = directiveStudent.getDirective();

            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
            KhlLyhiajaliseltValismaal khlLyhiajaliseltValismaal = new KhlLyhiajaliseltValismaal();
            khlLyhiajaliseltValismaal.setMuutusKp(date(directive.getConfirmDate()));
            khlLyhiajaliseltValismaal.setPerioodAlates(date(directiveStudent.getStartDate()));
            khlLyhiajaliseltValismaal.setPerioodKuni(date(directiveStudent.getEndDate()));
            khlLyhiajaliseltValismaal.setKlEesmark(ehisValue(directiveStudent.getAbroadPurpose()));
            khlLyhiajaliseltValismaal.setAinepunkte(points.toString());
            khlLyhiajaliseltValismaal.setNominaalajaPikendamine(BigInteger.valueOf(nominalStudyExtension.longValue()));
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
    
    private void setSpecialNeeds(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();
        
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
        
        KhlErivajadusedArr erivajadusedArr = new KhlErivajadusedArr();
        erivajadusedArr.setMuutusKp(date(directive.getConfirmDate()));
        List<String> erivajadused = erivajadusedArr.getKlErivajadus();
        
        erivajadused.addAll(directiveStudent.getApplication().getSupportServices().stream()
            .map(service -> ehisValue(service.getSupportService())).collect(Collectors.toList()));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setErivajadused(erivajadusedArr);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);
        makeRequest(directive, khlOppeasutusList);
    }
    
    private StudentHistory getStudentHistory(Student student) {
        return em.createQuery("select sh from StudentHistory sh where sh.student = ?1"
                + " and sh.id < ?2 order by sh.id desc", StudentHistory.class)
                .setParameter(1, student)
                .setParameter(2, EntityUtil.getId(student.getStudentHistory()))
                .getResultList().get(0);
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
