package ee.hitsa.ois.service.ehis;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsStringList;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.domain.FinalThesisSupervisor;
import ee.hitsa.ois.domain.Job;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.WsEhisStudentLog;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.domain.student.StudentSpecialNeed;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.FinalThesisStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.ScholarshipType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudyLoad;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.StudentResultHigherService;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.ForeignStudentDto;
import ee.hois.xroad.ehis.generated.KhlAkadPuhkusAlgus;
import ee.hois.xroad.ehis.generated.KhlEnnistamine;
import ee.hois.xroad.ehis.generated.KhlErivajadusedArr;
import ee.hois.xroad.ehis.generated.KhlJuhendamineArr;
import ee.hois.xroad.ehis.generated.KhlJuhendamineType;
import ee.hois.xroad.ehis.generated.KhlKorgharidusLisa;
import ee.hois.xroad.ehis.generated.KhlKorgharidusMuuda;
import ee.hois.xroad.ehis.generated.KhlLyhiAjaValisOppur;
import ee.hois.xroad.ehis.generated.KhlLyhiAjaValisOppurKustutamine;
import ee.hois.xroad.ehis.generated.KhlLyhiAjaValisOppurSalvestamine;
import ee.hois.xroad.ehis.generated.KhlLyhiajaliseltValismaal;
import ee.hois.xroad.ehis.generated.KhlOiendType;
import ee.hois.xroad.ehis.generated.KhlOppeasutus;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.KhlOppeasutuseLopetamine;
import ee.hois.xroad.ehis.generated.KhlOppeasutusestValjaarvamine;
import ee.hois.xroad.ehis.generated.KhlOppejoudIsikuandmedType;
import ee.hois.xroad.ehis.generated.KhlOppejoudType;
import ee.hois.xroad.ehis.generated.KhlOppekavaMuutus;
import ee.hois.xroad.ehis.generated.KhlOppevormiMuutus;
import ee.hois.xroad.ehis.generated.KhlOppur;
import ee.hois.xroad.ehis.generated.KhlStipendium;
import ee.hois.xroad.ehis.generated.KhlStipendiumArr;

@Transactional
@Service
public class EhisDirectiveStudentService extends EhisService {
    
    @Autowired 
    private StudentResultHigherService studentResultHigherService;

    public void updateStudents(Job job) {
        Directive directive = em.getReference(Directive.class, EntityUtil.getId(job.getDirective()));
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        Long studentId = EntityUtil.getNullableId(job.getStudent());

        for (DirectiveStudent directiveStudent : directive.getStudents()) {
            if(Boolean.TRUE.equals(directiveStudent.getCanceled()) || (studentId != null && !studentId.equals(EntityUtil.getId(directiveStudent.getStudent())))) {
                continue;
            }
            if (directiveStudent.getStudent() != null && !StudentUtil.isGuestStudent(directiveStudent.getStudent())) {
                Curriculum curriculum = directiveStudent.getStudent().getCurriculumVersion().getCurriculum();
                if (Boolean.TRUE.equals(curriculum.getJoint()) && curriculum.getJointMentor() != null
                        && !directiveStudent.getStudent().getSchool().getEhisSchool().equals(curriculum.getJointMentor())) {
                    continue;
                }
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
                case KASKKIRI_VALIS:
                    if (!ClassifierUtil.COUNTRY_ESTONIA.equals(EntityUtil.getNullableCode(directiveStudent.getCountry()))) {
                        foreignStudy(directiveStudent);
                    }
                    break;
                case KASKKIRI_VALISKATK:
                    if (directiveStudent.getDirectiveStudent() != null
                            && directiveStudent.getDirectiveStudent().getEhisId() != null
                            && !ClassifierUtil.COUNTRY_ESTONIA.equals(
                                    EntityUtil.getNullableCode(directiveStudent.getDirectiveStudent().getCountry()))) {
                        foreignStudy(directiveStudent);
                    }
                    break;
                case KASKKIRI_STIPTOET:
                    if (directiveStudent.getDirective().getScholarshipEhis() != null
                        || ClassifierUtil.oneOf(directiveStudent.getDirective().getScholarshipType(), ScholarshipType.STIPTOETUS_DOKTOR,
                                ScholarshipType.STIPTOETUS_ERIALA, ScholarshipType.STIPTOETUS_TULEMUS, ScholarshipType.STIPTOETUS_MUU)) {
                        sendScholarship(directiveStudent);
                    }
                    break;
                case KASKKIRI_STIPTOETL:
                    if (directiveStudent.getDirective().getScholarshipEhis() != null
                        || ClassifierUtil.oneOf(directiveStudent.getDirective().getScholarshipType(), ScholarshipType.STIPTOETUS_DOKTOR,
                                ScholarshipType.STIPTOETUS_ERIALA, ScholarshipType.STIPTOETUS_TULEMUS, ScholarshipType.STIPTOETUS_MUU)) {
                        sendScholarshipEnd(directiveStudent);
                    }
                    break;
                case KASKKIRI_TUGI:
                    setSpecialNeeds(directiveStudent);
                    break;
                case KASKKIRI_TYHIST:
                    // ONLY FOR TUGI
                    if (ClassifierUtil.oneOf(directive.getCanceledDirective().getType(), DirectiveType.KASKKIRI_TUGI)) {
                        Optional<DirectiveStudent> dsOpt = StreamUtil.nullSafeList(directive.getCanceledDirective().getStudents()).stream()
                                .filter(ds -> Boolean.TRUE.equals(ds.getCanceled()) && EntityUtil.getId(ds.getStudent()).equals(EntityUtil.getId(directiveStudent.getStudent())))
                                .findAny();
                        if (dsOpt.isPresent()) {
                            setSpecialNeeds(directiveStudent);
                        }
                    }
                    break;
                default:
                    break;
                }
            } catch (Exception e) {
                bindingException(directive, e);
            }
        }
    }
    
    WsEhisStudentLog sendGuestStudent(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Person person = student.getPerson();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusListGuestStudent(student);
        KhlLyhiAjaValisOppur oppur = new KhlLyhiAjaValisOppur();
        KhlLyhiAjaValisOppurSalvestamine salvestamine = new KhlLyhiAjaValisOppurSalvestamine();
        
        salvestamine.setOppeasutuseKirjeId(String.valueOf(EntityUtil.getId(student)));
        if (person != null) {
            salvestamine.setIsikukood(person.getIdcode());
            salvestamine.setSynniKp(date(person.getBirthdate()));
            if (person.getSex() != null) salvestamine.setKlSugu(person.getSex().getEhisValue());
            if (person.getCitizenship() != null) salvestamine.setKlKodakondsus(person.getCitizenship().getValue());            
        }
        if (directiveStudent.getApelSchool() != null) salvestamine.setKoduOppeasutus(directiveStudent.getApelSchool().getNameEt());
        if (directiveStudent.getCountry() != null) salvestamine.setKlKoduoppeasutuseRiik(directiveStudent.getCountry().getValue2());
        if (directiveStudent.getPreviousStudyLevel() != null) salvestamine.setKlKoduOppeaste(directiveStudent.getPreviousStudyLevel().getEhisValue());
        if (directiveStudent.getAbroadProgramme() != null) salvestamine.setKlProgramm(directiveStudent.getAbroadProgramme().getEhisValue());
        if (directiveStudent.getAbroadPurpose() != null) salvestamine.setKlEesmark(directiveStudent.getAbroadPurpose().getEhisValue());
        salvestamine.setPerioodAlates(date(directiveStudent.getStartDate()));
        salvestamine.setPerioodKuni(date(directiveStudent.getEndDate()));
        Long totalCredits = studentResultHigherService.getTotalPositiveGradeCredits(student);
        salvestamine.setAinepunkte(String.valueOf(totalCredits));
        
        oppur.setSalvestamine(salvestamine);
        khlOppeasutusList.getOppeasutus().get(0).getLyhiajaValisoppur().add(oppur);
        return makeRequest(student, khlOppeasutusList);
    }
    
    WsEhisStudentLog deleteGuestStudent(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Person person = student.getPerson();
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusListGuestStudent(student);
        KhlLyhiAjaValisOppur oppur = new KhlLyhiAjaValisOppur();
        KhlLyhiAjaValisOppurKustutamine kustutamine = new KhlLyhiAjaValisOppurKustutamine();
        
        kustutamine.setOppeasutuseKirjeId(String.valueOf(EntityUtil.getId(student)));
        if (person != null)  kustutamine.setSynniKp(date(person.getBirthdate()));
        if (directiveStudent.getCountry() != null) kustutamine.setKlKoduoppeasutuseRiik(directiveStudent.getCountry().getValue2());
        kustutamine.setPerioodAlates(date(directiveStudent.getStartDate()));
        
        oppur.setKustutamine(kustutamine);
        khlOppeasutusList.getOppeasutus().get(0).getLyhiajaValisoppur().add(oppur);
        return makeRequest(student, khlOppeasutusList);
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
    
    public WsEhisStudentLog graduationFinalThesis(DirectiveStudent directiveStudent) {
        try {
            Student student = directiveStudent.getStudent();
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);

            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            khlKorgharidusMuuda.setJuhendamised(graduationFinalThesis(directiveStudent,
                    !student.getFinalThesis().isEmpty() ? student.getFinalThesis().get(0) : null));
            khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

            return makeRequest(directiveStudent.getDirective(), khlOppeasutusList);
        } catch (Exception e) {
            return bindingException(directiveStudent.getDirective(), e);
        }
    }
    
    private KhlJuhendamineArr graduationFinalThesis(DirectiveStudent ds, FinalThesis ft) {
        if (ft == null) {
            throw new HoisException(TranslateUtil.optionalTranslate("ehis.graduation.noFinalThesis", Language.ET));
        }
        if (!ClassifierUtil.oneOf(ft.getStatus(), FinalThesisStatus.LOPUTOO_STAATUS_K)) {
            throw new HoisException(TranslateUtil.optionalTranslate("ehis.graduation.finalThesisIsNotConfirmed", Language.ET));
        }
        if (ft.getSupervisors().isEmpty()) {
            throw new HoisException(TranslateUtil.optionalTranslate("ehis.graduation.noSupervisors", Language.ET));
        }
        if (ft.getCercses().isEmpty()) {
            throw new HoisException(TranslateUtil.optionalTranslate("ehis.graduation.noCercsClassifiers", Language.ET));
        }
        
        // First should be primary supervisor
        List<FinalThesisSupervisor> supervisors = ft.getSupervisors().stream().sorted(new Comparator<FinalThesisSupervisor>() {
            @Override
            public int compare(FinalThesisSupervisor o1, FinalThesisSupervisor o2) {
                return o1.getIsPrimary().compareTo(o2.getIsPrimary()) * -1;
            }
        }).collect(Collectors.toList());

        KhlJuhendamineType khlJuhendamineType = new KhlJuhendamineType();
        for (FinalThesisSupervisor supervisor : supervisors) {
            KhlOppejoudType teacher = new KhlOppejoudType();
            if (supervisor.getTeacher() != null && supervisor.getTeacher().getPerson() != null) {
                Person person = supervisor.getTeacher().getPerson();
                if (person.getIdcode() != null) {
                    teacher.setIsikukood(person.getIdcode());
                } else {
                    KhlOppejoudIsikuandmedType personalData = new KhlOppejoudIsikuandmedType();
                    personalData.setEesnimi(supervisor.getFirstname());
                    personalData.setPerenimi(supervisor.getLastname());
                    personalData.setKlSugu(ehisValue(person.getSex()));
                    personalData.setSynniKp(date(person.getBirthdate()));
                    teacher.setIsikuandmed(personalData);
                }
            } else {
                if (supervisor.getIdcode() != null) {
                    teacher.setIsikukood(supervisor.getIdcode());
                } else {
                    KhlOppejoudIsikuandmedType personalData = new KhlOppejoudIsikuandmedType();
                    personalData.setEesnimi(supervisor.getFirstname());
                    personalData.setPerenimi(supervisor.getLastname());
                    personalData.setKlSugu(supervisor.getSex() != null ? ehisValue(supervisor.getSex()) : "M");
                    personalData.setSynniKp(date(supervisor.getBirthdate()));
                    teacher.setIsikuandmed(personalData);
                }
            }
            teacher.setJuhendamiseAlgus(date(supervisor.getInserted().toLocalDate()));
            khlJuhendamineType.getJuhendaja().add(teacher);
        }
        
        khlJuhendamineType.setOppekava(new BigInteger(ds.getStudent().getCurriculumVersion().getCurriculum().getMerCode()));
        khlJuhendamineType.setLoputooNimetusEesti(ft.getThemeEt());
        khlJuhendamineType.setLoputooNimetusInglise(ft.getThemeEn());
        if (ft.getLanguage() != null) {
            khlJuhendamineType.setLoputooOriginaalkeel(ehisValue(ft.getLanguage()));
            khlJuhendamineType.setNimetusOriginaalkeeles("et".equals(khlJuhendamineType.getLoputooOriginaalkeel()) ? ft.getThemeEt() : ft.getThemeEn());
        } else {
            khlJuhendamineType.setLoputooOriginaalkeel("en");
            khlJuhendamineType.setNimetusOriginaalkeeles(ft.getThemeEn());
        }
        String code = ds.getStudent().getCurriculumVersion().getCurriculum().getDepartments().stream().findFirst().map(d -> d.getSchoolDepartment().getCode()).orElse(null);
        khlJuhendamineType.setStruktyksuseId(code);
        khlJuhendamineType.getKlTeaduseriala().addAll(ft.getCercses().stream().map(c -> ehisValue(c.getCercs()))
                .filter(Objects::nonNull).distinct().collect(Collectors.toList()));

        KhlJuhendamineArr juhendamised = new KhlJuhendamineArr();
        juhendamised.setJuhendamine(khlJuhendamineType);
        juhendamised.setMuutusKp(date(ds.getDirective().getConfirmDate()));
        return juhendamised;
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
    
    private WsEhisStudentLog foreignStudyInternal(DirectiveStudent directiveStudent, ForeignStudentDto foreignStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();
        DirectiveStudent originStudent = ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_VALISKATK)
                ? directiveStudent.getDirectiveStudent()
                : directiveStudent;

        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
        KhlLyhiajaliseltValismaal khlLyhiajaliseltValismaal = new KhlLyhiajaliseltValismaal();
        khlLyhiajaliseltValismaal.setLyhiajaliseltValismaalId(originStudent.getEhisId() != null ? new BigInteger(originStudent.getEhisId()) : null);
        khlLyhiajaliseltValismaal.setMuutusKp(date(directive.getConfirmDate()));
        if (originStudent.getStartDate() != null && originStudent.getEndDate() != null) {
            khlLyhiajaliseltValismaal.setPerioodAlates(date(originStudent.getStartDate()));
            // Set VALISKATK startDate in case if we are having VALISKATK directive instead of VALIS
            khlLyhiajaliseltValismaal.setPerioodKuni(date(directiveStudent.equals(originStudent)
                    ? originStudent.getEndDate()
                    : directiveStudent.getStartDate().minusDays(1)));
        } else if (originStudent.getStudyPeriodStart() != null && originStudent.getStudyPeriodEnd() != null) {
            khlLyhiajaliseltValismaal.setPerioodAlates(date(originStudent.getStudyPeriodStart().getStartDate()));
            // Set VALISKATK startDate in case if we are having VALISKATK directive instead of VALIS
            khlLyhiajaliseltValismaal.setPerioodKuni(date(directiveStudent.equals(originStudent)
                    ? originStudent.getStudyPeriodEnd().getEndDate()
                    : directiveStudent.getStartDate().minusDays(1)));
        }
        khlLyhiajaliseltValismaal.setKlEesmark(ehisValue(originStudent.getAbroadPurpose()));
        if (foreignStudent != null) {
            khlLyhiajaliseltValismaal.setAinepunkte(foreignStudent.getPoints() != null ? foreignStudent.getPoints() : "0"); // by default should be 0
            khlLyhiajaliseltValismaal.setNominaalajaPikendamine(foreignStudent.getNominalStudyExtension() != null ? foreignStudent.getNominalStudyExtension() : BigInteger.valueOf(0L));   
        } else {
            khlLyhiajaliseltValismaal.setAinepunkte("0");
            khlLyhiajaliseltValismaal.setNominaalajaPikendamine(BigInteger.ZERO);
        }
        khlLyhiajaliseltValismaal.setOppeasutuseNimi(Boolean.TRUE.equals(originStudent.getIsAbroad()) ? 
                (originStudent.getAbroadSchool() != null ? originStudent.getAbroadSchool() : name(originStudent.getApelSchool())) : name(originStudent.getEhisSchool()));
        khlLyhiajaliseltValismaal.setKlSihtriik(value2(originStudent.getCountry()));
        khlLyhiajaliseltValismaal.setKlProgramm(ehisValue(originStudent.getAbroadProgramme()));

        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setLyhiajaliseltValismaal(khlLyhiajaliseltValismaal);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);

        WsEhisStudentLog result = makeRequest(directive, khlOppeasutusList);
        
        Pattern pattern = Pattern.compile(".+Andmeid on edukalt laetud\\. Uus lyhiajaliselt valismaal id on (\\d+).+$",
                Pattern.DOTALL);
        Matcher matcher = pattern.matcher(result.getResponse());
        if (matcher.matches()) {
            originStudent.setEhisId(matcher.group(1));
        }
        
        return result;
    }
    
    private WsEhisStudentLog foreignStudy(DirectiveStudent directiveStudent) {
        return foreignStudyInternal(directiveStudent, null);
    }

    /**
     * Used to send manually from EhisStudentService
     * 
     * @param directiveStudent
     * @param foreignStudent
     * @return
     */
    public WsEhisStudentLog foreignStudy(DirectiveStudent directiveStudent, ForeignStudentDto foreignStudent) {
        try {
            return foreignStudyInternal(directiveStudent, foreignStudent);
        } catch(Exception e) {
            return bindingException(directiveStudent.getDirective(), e);
        }
    }

    private void setSpecialNeeds(Student student, Collection<String> supportServices) {
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
        
        KhlErivajadusedArr erivajadusedArr = new KhlErivajadusedArr();
        erivajadusedArr.setMuutusKp(date(LocalDate.now()));
        erivajadusedArr.getKlTugiteenus().addAll(new HashSet<>(supportServices));
        
        erivajadusedArr.getKlErivajadus().addAll(student.getSpecialNeeds().stream()
            .map(StudentSpecialNeed::getSpecialNeed)
            .filter(cl -> StringUtils.isNotBlank(cl.getEhisValue()))
            .map(Classifier::getEhisValue)
            .collect(Collectors.toList()));
        
        if (erivajadusedArr.getKlErivajadus().isEmpty()) {
            return; // In case if we do not have any ERIVAJADUS then we should not send it.
        }
        
        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setErivajadused(erivajadusedArr);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);
        makeRequest(student, khlOppeasutusList);
    }
    
    /**
     * TUGI - application services + active services
     * TYHIST - only active services are sent
     * 
     * @param directiveStudent
     */
    private void setSpecialNeeds(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        Directive directive = directiveStudent.getDirective();
        
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
        
        KhlErivajadusedArr erivajadusedArr = new KhlErivajadusedArr();
        erivajadusedArr.setMuutusKp(date(directive.getConfirmDate()));

        // `Set` to exclude duplicates
        Set<String> supportServices = new HashSet<>();
        if (ClassifierUtil.oneOf(directive.getType(), DirectiveType.KASKKIRI_TUGI)) {
            supportServices.addAll(directiveStudent.getApplication().getSupportServices().stream()
                .map(service -> ehisValue(service.getSupportService()))
                .collect(Collectors.toSet()));
        }
        
        List<String> studentActiveSupportServices = getStudentsWithActiveSupportServiceDirectives(Collections.singleton(student.getId())).get(student);
        //log.info("[Special Needs] Directive special needs: " + supportServices.size());
        //log.info("[Special Needs] Active special needs: " + (studentActiveSupportServices != null ? studentActiveSupportServices.size() : 0));
        if (studentActiveSupportServices != null) {
            supportServices.addAll(studentActiveSupportServices);
        }
        
        //log.info("[Special Needs] Directive special needs + active: " + supportServices.size());
        
        erivajadusedArr.getKlTugiteenus().addAll(supportServices);
        
        erivajadusedArr.getKlErivajadus().addAll(student.getSpecialNeeds().stream()
            .map(StudentSpecialNeed::getSpecialNeed)
            .filter(cl -> StringUtils.isNotBlank(cl.getEhisValue()))
            .map(Classifier::getEhisValue)
            .collect(Collectors.toList()));
        
        if (erivajadusedArr.getKlErivajadus().isEmpty()) {
            return; // In case if we do not have any ERIVAJADUS then we should not send it.
        }
        
        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setErivajadused(erivajadusedArr);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);
        makeRequest(directive, khlOppeasutusList);
        // Additional check for TYHIST directive. It should not send nominaalseOppeagaPikendamine.
        if (Boolean.FALSE.equals(directiveStudent.getCanceled())) {
            reportNominalStudyDateChange(directiveStudent);
        }
    }
    
    private void reportNominalStudyDateChange(DirectiveStudent directiveStudent) {
        Student student = directiveStudent.getStudent();
        StudentHistory history = getStudentHistory(student);
        Directive directive = directiveStudent.getDirective();

        if (history.getNominalStudyEnd() != null && student.getNominalStudyEnd().isAfter(history.getNominalStudyEnd())) {
            KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
            
            KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
            if (student.getNominalStudyEnd().minusMonths(6).isAfter(history.getNominalStudyEnd())) {
                khlKorgharidusMuuda.setNominaalseOppeajaPikendamine60EKAP(date(directive.getConfirmDate()));
            } else {
                khlKorgharidusMuuda.setNominaalseOppeajaPikendamine30EKAP(date(directive.getConfirmDate()));
            }
            khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);
            makeRequest(directive, khlOppeasutusList);
        }
    }
    
    /**
     * Special needs are set per student because it can be several directives in one day, but only 1 student.
     */
    public void sendEndedSupportServices() {
        LocalDate date = LocalDate.now().minusDays(1);
        List<?> data = em.createNativeQuery("select s.id "
                + "from student s "
                + "join student_special_need ssn on ssn.student_id = s.id "
                + "join directive_student ds on ds.student_id = s.id "
                + "join directive d on d.id = ds.directive_id "
                + "join application a on a.id = ds.application_id "
                + "join application_support_service ass on ass.application_id = a.id "
                + "left join directive_student ds2 on ds.id = ds2.directive_student_id and ds2.canceled = false "
                + "left join directive d2 on d2.id = ds2.directive_id and d2.type_code = ?3 and d2.status_code = ?4 "
                + "join classifier ass_cl on ass_cl.code = ass.support_service_code "
                + "where s.status_code in ?1 and d.type_code = ?2 and d.status_code = ?4 "
                + "and ds.canceled = false and coalesce ( ds2.start_date, ds.end_date ) = ?5 "
                + "group by s.id")
        .setParameter(1, StudentStatus.STUDENT_STATUS_ACTIVE)
        .setParameter(2, DirectiveType.KASKKIRI_TUGI.name())
        .setParameter(3, DirectiveType.KASKKIRI_TUGILOPP.name())
        .setParameter(4, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
        .setParameter(5, JpaQueryUtil.parameterAsTimestamp(date))
        .getResultList();

        List<Long> studentIds = data.stream().map(r -> resultAsLong(r, 0)).collect(Collectors.toList());
        Map<Student, List<String>> mappedStudents = getStudentsWithActiveSupportServiceDirectives(studentIds);
        mappedStudents.forEach((student, services) -> {
            if (studentIds.contains(student.getId())) {
                studentIds.remove(student.getId());
            }
        });
        if (!studentIds.isEmpty()) {
            mappedStudents.putAll(em.createQuery("select s from Student s where s.id in ?1", Student.class)
                    .setParameter(1, studentIds).getResultList().stream().collect(Collectors.toMap(s -> s, s -> Collections.emptyList(), (o, n) -> o)));
        }
        mappedStudents.forEach(this::setSpecialNeeds);
    }

    private Map<Student, List<String>> getStudentsWithActiveSupportServiceDirectives(Collection<Long> studentIds) {
        return getStudentsWithSupportServiceDirectives(studentIds, LocalDate.now());
    }

    private Map<Student, List<String>> getStudentsWithSupportServiceDirectives(Collection<Long> studentIds, LocalDate date) {
        Query q = em.createNativeQuery("select s.id, string_agg(distinct ass_cl.ehis_value, ';') as ass_values "
                + "from student s "
                + "join directive_student ds on ds.student_id = s.id "
                + "join directive d on d.id = ds.directive_id "
                + "join application a on a.id = ds.application_id "
                + "join application_support_service ass on ass.application_id = a.id "
                + "left join directive_student ds2 on ds.id = ds2.directive_student_id and ds2.canceled = false "
                + "left join directive d2 on d2.id = ds2.directive_id and d2.type_code = ?3 and d2.status_code = ?4 "
                + "join classifier ass_cl on ass_cl.code = ass.support_service_code "
                + "where s.status_code in ?1 and d.type_code = ?2 and d.status_code = ?4 and ass_cl.ehis_value is not null " // TUGITEENUS should have ehis_value to be sent.
                + "and ds.canceled = false and ds.start_date <= ?6 and coalesce ( ds2.start_date, ds.end_date ) >= ?5 "
                + (studentIds != null && !studentIds.isEmpty() ? "and s.id in ?7 " : "")
                + "group by s.id")
        .setParameter(1, StudentStatus.STUDENT_STATUS_ACTIVE)
        .setParameter(2, DirectiveType.KASKKIRI_TUGI.name())
        .setParameter(3, DirectiveType.KASKKIRI_TUGILOPP.name())
        .setParameter(4, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
        .setParameter(5, JpaQueryUtil.parameterAsTimestamp(DateUtils.firstMomentOfDay(date)))
        .setParameter(6, JpaQueryUtil.parameterAsTimestamp(DateUtils.lastMomentOfDay(date)));
        
        if (studentIds != null && !studentIds.isEmpty()) {
            q.setParameter(7, studentIds);
        }
        
        List<?> data = q.getResultList();

        Map<Long, List<String>> mappedData = data.stream().collect(Collectors.toMap(
                    r -> resultAsLong(r, 0),
                    r -> resultAsStringList(r, 1, ";"),
                    (o, n) -> o));
        Map<Student, List<String>> mappedStudents = new HashMap<>();
        if (!mappedData.isEmpty()) {
            mappedStudents.putAll(em.createQuery("select s from Student s where s.id in ?1", Student.class)
                    .setParameter(1, mappedData.keySet()).getResultList().stream().collect(Collectors.toMap(s -> s, s -> mappedData.get(s.getId()), (o, n) -> o)));
        }
        return mappedStudents;
    }
    
    private void sendScholarship(DirectiveStudent ds) {
        Student student = ds.getStudent();
        Directive directive = ds.getDirective();
        ScholarshipApplication scholarshipApplication = ds.getScholarshipApplication();
        
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
        boolean isDoctoral = ClassifierUtil.equals(ScholarshipType.STIPTOETUS_DOKTOR, directive.getScholarshipType());
        // Possible NPE student.getStudyStart, any problem with it?
        LocalDate startDate = isDoctoral && student.getStudyStart() != null
                && student.getStudyStart().isAfter(ds.getStartDate()) ? student.getStudyStart() : ds.getStartDate();
        
        KhlStipendium khlStipendium = new KhlStipendium();
        khlStipendium.setKlStipendiumLiik(scholarshipApplication != null
                ? ehisValue(scholarshipApplication.getScholarshipTerm().getScholarshipEhis())
                : ehisValue(directive.getScholarshipEhis()));
        khlStipendium.setStipendiumAlgusKp(date(startDate));
        khlStipendium.setStipendiumLoppKp(date(ds.getEndDate()));
        if (isDoctoral) {
            khlStipendium.setDoktoranditoetusSumma(ds.getAmountPaid().toString());
            khlStipendium.setViimaneDoktoranditoetuseValjamakseKp(date(ds.getEndDate()));
        }
        khlStipendium.setOppeasutuseKirjeId(String.format("%d_%d", directive.getId(), student.getId()));

        KhlStipendiumArr khlStipendiumArr = new KhlStipendiumArr();
        khlStipendiumArr.getStipendium().add(khlStipendium);
        khlStipendiumArr.setMuutusKp(date(directive.getConfirmDate()));
        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setStipendiumid(khlStipendiumArr);
        khlOppeasutusList.getOppeasutus().get(0).getOppur().get(0).getMuutmine().setKorgharidus(khlKorgharidusMuuda);
        makeRequest(directive, khlOppeasutusList);
    }
    
    private void sendScholarshipEnd(DirectiveStudent ds) {
        Student student = ds.getStudent();
        Directive directive = ds.getDirective();
        DirectiveStudent stipDirectiveStudent = ds.getDirectiveStudent();
        Directive stipDirective = stipDirectiveStudent.getDirective();
        ScholarshipApplication scholarshipApplication = stipDirectiveStudent.getScholarshipApplication();
        
        KhlOppeasutusList khlOppeasutusList = getKhlOppeasutusList(student);
        boolean isDoctoral = ClassifierUtil.equals(ScholarshipType.STIPTOETUS_DOKTOR, stipDirective.getScholarshipType());
        LocalDate startDate = isDoctoral && student.getStudyStart() != null
                && student.getStudyStart().isAfter(stipDirectiveStudent.getStartDate()) ? student.getStudyStart()
                        : stipDirectiveStudent.getStartDate();
        
        KhlStipendium khlStipendium = new KhlStipendium();
        khlStipendium.setKlStipendiumLiik(scholarshipApplication != null
                ? ehisValue(scholarshipApplication.getScholarshipTerm().getScholarshipEhis())
                : ehisValue(stipDirective.getScholarshipEhis()));
        khlStipendium.setStipendiumAlgusKp(date(startDate)); // TODO
        khlStipendium.setStipendiumLoppKp(date(directive.getConfirmDate()));
        if (isDoctoral) {
            khlStipendium.setDoktoranditoetusSumma(stipDirectiveStudent.getAmountPaid().toString());
            khlStipendium.setViimaneDoktoranditoetuseValjamakseKp(date(directive.getConfirmDate()));
        }
        khlStipendium.setOppeasutuseKirjeId(String.format("%d_%d", stipDirective.getId(), student.getId()));

        KhlStipendiumArr khlStipendiumArr = new KhlStipendiumArr();
        khlStipendiumArr.getStipendium().add(khlStipendium);
        khlStipendiumArr.setMuutusKp(date(directive.getConfirmDate()));
        KhlKorgharidusMuuda khlKorgharidusMuuda = new KhlKorgharidusMuuda();
        khlKorgharidusMuuda.setStipendiumid(khlStipendiumArr);
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

    private WsEhisStudentLog makeRequest(Student student, KhlOppeasutusList khlOppeasutusList) {
        WsEhisStudentLog wsEhisStudentLog = new WsEhisStudentLog();
        wsEhisStudentLog.setSchool(student.getSchool());

        return laeKorgharidused(khlOppeasutusList, wsEhisStudentLog);
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
