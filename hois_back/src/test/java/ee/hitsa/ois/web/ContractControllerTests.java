package ee.hitsa.ois.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleRepository;
import ee.hitsa.ois.repository.DirectiveCoordinatorRepository;
import ee.hitsa.ois.repository.EnterpriseRepository;
import ee.hitsa.ois.repository.PracticeJournalRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.ContractForm;
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
    private StudentRepository studentRepository;
    @Autowired
    private CurriculumVersionOccupationModuleRepository curriculumVersionOccupationModuleRepository;
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private DirectiveCoordinatorRepository directiveCoordinatorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PracticeJournalRepository practiceJournalRepository;

    private ContractDto contract;
    private Student student;
    private School userSchool;

    @Before
    public void setUp() {
        Role role = Role.ROLL_A;
        if(student == null) {
            List<School> userSchools = userRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                filters.add(cb.isNotNull(root.get("school").get("id")));
                filters.add(cb.equal(root.get("role").get("code"), role.name()));
                filters.add(cb.equal(root.get("person").get("idcode"), TestConfiguration.USER_ID));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            }).stream().map(User::getSchool).collect(Collectors.toList());

            Assert.assertFalse(userSchools.isEmpty());

            student = studentRepository.findAll((root, query, cb) -> {
                List<Predicate> filters = new ArrayList<>();
                filters.add(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()));
                filters.add(root.get("school").in(userSchools));
                return cb.and(filters.toArray(new Predicate[filters.size()]));
            }).get(0);

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
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        ContractForm form = new ContractForm();
        ResponseEntity<ContractDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ContractDto.class);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form = createForm();

        responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ContractDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        contract = responseEntity.getBody();

        //read
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(contract.getId().toString());
        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), ContractDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //update
        form.setVersion(responseEntity.getBody().getVersion());
        form.setCredits(BigDecimal.TEN);
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
    public void sendToEkis() {
        ContractForm form = createForm();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        ResponseEntity<ContractDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ContractDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        contract = responseEntity.getBody();

        Assert.assertEquals(contract.getStatus(), ContractStatus.LEPING_STAATUS_S.name());
        PracticeJournal practiceJournal = practiceJournalRepository.findByContractId(contract.getId());
        Assert.assertNull(practiceJournal);

        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/sendToEkis").pathSegment(contract.getId().toString());
        responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ContractDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        contract = responseEntity.getBody();

        Assert.assertEquals(contract.getStatus(), ContractStatus.LEPING_STAATUS_Y.name());

        practiceJournal = practiceJournalRepository.findByContractId(contract.getId());
        Assert.assertNotNull(practiceJournal);

    }

    private void delete() {
        if (contract != null && contract.getId() != null) {
            PracticeJournal practiceJournal = practiceJournalRepository.findByContractId(contract.getId());
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
        PageRequest SINGLE_RESULT = new PageRequest(1, 1);
        form.setStudent(AutocompleteResult.of(student));
        form.setModule(curriculumVersionOccupationModuleRepository.findAll(SINGLE_RESULT).getContent().get(0).getId());
        form.setCredits(BigDecimal.ONE);
        form.setHours(Short.valueOf((short) 1));
        form.setStartDate(LocalDate.now());
        form.setEndDate(LocalDate.now().plusDays(1));
        form.setPracticePlace("place");
        form.setEnterprise(enterpriseRepository.findAll(SINGLE_RESULT).getContent().get(0).getId());
        form.setContactPersonName("person name");
        form.setContactPersonEmail("test@test.ee");
        form.setSupervisorName("supervisor");
        form.setSupervisorEmail("test@test.ee");
        form.setTeacher(teacherRepository.findAll(SINGLE_RESULT).getContent().get(0).getId());
        form.setContractCoordinator(directiveCoordinatorRepository.findAll(SINGLE_RESULT).getContent().get(0).getId());
        form.setPracticePlan("plan");
        return form;
    }

}
