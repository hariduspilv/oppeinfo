package ee.hitsa.ois.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.service.PracticeJournalService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.ContractForm;
import ee.hitsa.ois.web.commandobject.ContractModuleSubjectForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ContractDto;
import ee.hitsa.ois.web.dto.ContractSearchDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContractControllerTests {

    private static final String ENDPOINT = "/contracts";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private EntityManager em;
    @Autowired
    private PracticeJournalService practiceJournalService;

    private ContractDto contract;
    private Student student;
    private School userSchool;

    @Before
    public void setUp() {
        Role role = Role.ROLL_A;
        if(student == null) {
            List<School> userSchools = testConfigurationService.personSchools(role);
            Assert.assertFalse(userSchools.isEmpty());

            student = em.createQuery("select s from Student s where s.status.code = ?1 and s.school in (?2)", Student.class)
                .setParameter(1, StudentStatus.OPPURSTAATUS_O.name())
                .setParameter(2, userSchools)
                .setMaxResults(1).getResultList().get(0);

            userSchool = student.getSchool();
        }
        testConfigurationService.userToRoleInSchool(role, EntityUtil.getId(userSchool), restTemplate);
    }

    @After
    public void cleanUp() {
        delete();
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void search() {
        ResponseEntity<ContractSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT, ContractSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("startFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("startThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("endFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("endThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("studentName", "studentName");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1L));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1L));
        uriBuilder.queryParam("enterpriseName", "enterpriseName");
        uriBuilder.queryParam("enterpriseContactPersonName", "enterpriseContactPersonName");
        uriBuilder.queryParam("teacher", Long.valueOf(1L));
        uriBuilder.queryParam("status", ContractStatus.LEPING_STAATUS_K.name());
        String url = uriBuilder.build().toUriString();

        responseEntity = restTemplate.getForEntity(url, ContractSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        ContractForm form = new ContractForm();
        ResponseEntity<ContractDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ContractDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form = createForm();

        responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ContractDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        contract = responseEntity.getBody();

        //read
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(contract.getId().toString());
        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), ContractDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //update
        form.setVersion(responseEntity.getBody().getVersion());
        form.getModuleSubjects().get(0).setCredits(BigDecimal.TEN);
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(contract.getId().toString());
        responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.PUT, new HttpEntity<>(form), ContractDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // delete
        delete();

    }

    @Test
    public void studentPracticeModules() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment("studentPracticeModules").pathSegment(student.getId().toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studentPracticeSubjects() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment("studentPracticeSubjects").pathSegment(student.getId().toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void checkForEkis() {
        // contract id is not user for now
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/checkForEkis").pathSegment("1");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void sendToEkis() {
        ContractForm form = createForm();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        ResponseEntity<ContractDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ContractDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        contract = responseEntity.getBody();

        Assert.assertEquals(contract.getStatus(), ContractStatus.LEPING_STAATUS_S.name());
        PracticeJournal practiceJournal = practiceJournalService.findByContractId(contract.getId());
        Assert.assertNull(practiceJournal);

        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/sendToEkis").pathSegment(contract.getId().toString());
        responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ContractDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        contract = responseEntity.getBody();

        Assert.assertEquals(contract.getStatus(), ContractStatus.LEPING_STAATUS_Y.name());

        practiceJournal = practiceJournalService.findByContractId(contract.getId());
        Assert.assertNotNull(practiceJournal);
    }

    private void delete() {
        if (contract != null && contract.getId() != null) {
            PracticeJournal practiceJournal = practiceJournalService.findByContractId(contract.getId());
            if (practiceJournal != null) {
                UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/practiceJournals")
                        .pathSegment(practiceJournal.getId().toString());
                uriBuilder.queryParam("version", practiceJournal.getVersion().toString());
                restTemplate.delete(uriBuilder.toUriString());
            }

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                    .pathSegment(contract.getId().toString());
            uriBuilder.queryParam("version", contract.getVersion().toString());
            restTemplate.delete(uriBuilder.toUriString());

        }
    }

    private ContractForm createForm() {
        ContractForm form = new ContractForm();
        form.setStudent(AutocompleteResult.of(student));
        ContractModuleSubjectForm moduleSubject = new ContractModuleSubjectForm();
        moduleSubject.setModule(curriculumVersionOccupationModuleId());
        moduleSubject.setCredits(BigDecimal.ONE);
        moduleSubject.setHours(Short.valueOf((short) 1));
        List<ContractModuleSubjectForm> moduleSubjects = new ArrayList<>();
        moduleSubjects.add(moduleSubject);
        form.setModuleSubjects(moduleSubjects);
        form.setStartDate(LocalDate.now());
        form.setEndDate(LocalDate.now().plusDays(1));
        form.setPracticePlace("place");
        form.setEnterprise(AutocompleteResult.of(enterprise()));
        form.setContactPersonName("person name");
        form.setContactPersonEmail("test@test.ee");
        form.setTeacher(teacherId());
        form.setContractCoordinator(directiveCoordinator().getId());
        form.setPracticePlan("plan");
        return form;
    }

    private Long curriculumVersionOccupationModuleId() {
        return JpaQueryUtil.resultAsLong(em.createNativeQuery("select id from curriculum_version_omodule").setMaxResults(1).getResultList().get(0), 0);
    }

    private DirectiveCoordinator directiveCoordinator() {
        return em.createQuery("select dc from DirectiveCoordinator dc", DirectiveCoordinator.class).setMaxResults(1).getResultList().get(0);
    }

    private Enterprise enterprise() {
        return em.createQuery("select e from Enterprise e", Enterprise.class).setMaxResults(1).getResultList().get(0);
    }

    private Long teacherId() {
        return JpaQueryUtil.resultAsLong(em.createNativeQuery("select id from teacher").setMaxResults(1).getResultList().get(0), 0);
    }
}
