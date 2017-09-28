package ee.hitsa.ois.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.Enterprise;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.service.ekis.EkisService;
import ee.hitsa.ois.util.EnumUtil;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(/* properties= {"ekis.endpoint=local://ekis"}, */ webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EkisServiceTests {

    private static final int NUMBER_OF_ITEMS = 10;

    @Autowired
    private EkisService ekisService;
    @Autowired
    private EntityManager em;

    @Test
    public void registerCertificate() {
        Certificate certificate = new Certificate();
        certificate.setId(Long.valueOf(1));
        certificate.setSchool(schoolForTesting());

        Person person = new Person();
        person.setFirstname("Certificatefirst");
        person.setLastname("Certificatelast");
        person.setEmail("certificatefirst.certificatelast@dev.null");
        Student student = new Student();
        student.setPerson(person);
        certificate.setStudent(student);

        certificate.setHeadline("headline");
        certificate.setContent("content");
        certificate.setSignatoryIdcode("48403150000");
        Classifier type = new Classifier();
        type.setValue("OPI");
        certificate.setType(type);
        certificate.setWhom("whom");

        ekisService.registerCertificate(certificate);
    }

    // @Test
    public void registerDirective() {
        for(Directive directive : findDirectives()) {
            ekisService.registerDirective(directive);
        }
    }

    @Test
    public void registerPracticeContract() {
        Contract contract = new Contract();
        contract.setId(Long.valueOf(1));
        contract.setInserted(LocalDateTime.parse("2017-09-27T10:15:30"));

        DirectiveCoordinator manager = new DirectiveCoordinator();
        manager.setIdcode("48403150000");
        contract.setContractCoordinator(manager);

        Person person = new Person();
        person.setFirstname("Certificatefirst");
        person.setLastname("Certificatelast");
        person.setIdcode("48403150000");
        person.setEmail("certificatefirst.certificatelast@dev.null");
        Student student = new Student();
        student.setPerson(person);
        student.setSchool(schoolForTesting());

        Curriculum c = new Curriculum();
        c.setNameEt("Õppekava nimetus");
        CurriculumVersion cv = new CurriculumVersion();
        cv.setCurriculum(c);
        student.setCurriculumVersion(cv);

        Classifier studyForm = new Classifier();
        studyForm.setValue("P");
        student.setStudyForm(studyForm);
        contract.setStudent(student);

        // private String stCourse;
        // request.setStEkap(contract.get);
        contract.setHours(Short.valueOf((short)23));

        CurriculumModule cm = new CurriculumModule();
        cm.setNameEt("Õppekava moodul");
        CurriculumVersionOccupationModule module = new CurriculumVersionOccupationModule();
        module.setCurriculumModule(cm);
        contract.setModule(module);

        Enterprise enterprise = new Enterprise();
        enterprise.setName("Enterprise name");
        enterprise.setRegCode("34567890");
        enterprise.setContactPersonName("Enterprise Contactperson");
        enterprise.setContactPersonPhone("9876543");
        enterprise.setContactPersonEmail("enterprise.contactperson@dev.null");
        contract.setEnterprise(enterprise);

        contract.setSupervisorName("Supervisor Name");
        contract.setSupervisorPhone("12345678");
        contract.setSupervisorEmail("supervisor.name@dev.null");
        contract.setPracticePlan("Practice plan");

        ekisService.registerPracticeContract(contract);
    }

    private static School schoolForTesting() {
        School school = new School();
        Classifier ehisSchool = new Classifier();
        ehisSchool.setEhisValue("5");
        school.setEhisSchool(ehisSchool);
        return school;
    }

    private List<Directive> findDirectives() {
        List<Directive> directives = new ArrayList<>();
        // one directive of each type, excluding cancel directive
        for(DirectiveType type : DirectiveType.values()) {
            if(DirectiveType.KASKKIRI_TYHIST.equals(type)) {
                continue;
            }
            directives.addAll(em.createQuery("select d from Directive d where d.status.code in ?1", Directive.class)
                    .setParameter(1, EnumUtil.toNameList(DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD))
                    .setMaxResults(NUMBER_OF_ITEMS).getResultList());
        }
        return directives;
    }
}
