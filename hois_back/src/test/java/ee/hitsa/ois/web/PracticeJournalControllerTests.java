package ee.hitsa.ois.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.PracticeJournalForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.PracticeJournalDto;
import ee.hitsa.ois.web.dto.PracticeJournalSearchDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PracticeJournalControllerTests {

    private static final String ENDPOINT = "/practiceJournals";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CurriculumVersionOccupationModuleRepository curriculumVersionOccupationModuleRepository;

    private Student student;
    private School userSchool;
    private PracticeJournalDto practiceJournal;

    @Before
    public void setUp() {
        Role role = Role.ROLL_A;
        if (student == null) {
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
            }).stream().findFirst().get();

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
        ResponseEntity<PracticeJournalSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT,
                PracticeJournalSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("studyYear", Long.valueOf(1L));
        uriBuilder.queryParam("studentName", "studentName");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1L));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1L));
        uriBuilder.queryParam("teacher", Long.valueOf(1L));
        String url = uriBuilder.build().toUriString();

        responseEntity = restTemplate.getForEntity(url, PracticeJournalSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        PracticeJournalForm form = new PracticeJournalForm();
        ResponseEntity<PracticeJournalDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form,
                PracticeJournalDto.class);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form = createForm();

        responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, PracticeJournalDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        practiceJournal = responseEntity.getBody();

        // read
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(practiceJournal.getId().toString());
        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), PracticeJournalDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // update
        form.setVersion(responseEntity.getBody().getVersion());
        form.setCredits(BigDecimal.TEN);
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(practiceJournal.getId().toString());
        responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.PUT, new HttpEntity<>(form),
                PracticeJournalDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        practiceJournal = responseEntity.getBody();

        // delete
        delete();
    }

    @Test
    public void saveEntriesStudent() {
        //TODO
    }

    @Test
    public void saveEntriesTeacher() {
        //TODO
    }

    @Test
    public void supervisorGet() {
        testConfigurationService.setSessionCookie(null);

        String uuid = UUID.randomUUID().toString();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                .pathSegment("supervisor").pathSegment(uuid);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
    }

    @Test
    public void saveEntriesSupervisor() {
        //TODO
    }

    private void delete() {
        if (practiceJournal != null && practiceJournal.getId() != null) {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                    .pathSegment(practiceJournal.getId().toString());
            uriBuilder.queryParam("version", practiceJournal.getVersion().toString());
            restTemplate.delete(uriBuilder.toUriString());
        }
    }

    private PracticeJournalForm createForm() {
        PracticeJournalForm form = new PracticeJournalForm();
        PageRequest SINGLE_RESULT = new PageRequest(1, 1);
        form.setStudent(AutocompleteResult.of(student));
        form.setModule(curriculumVersionOccupationModuleRepository.findAll(SINGLE_RESULT).getContent().get(0).getId());
        form.setCredits(BigDecimal.ONE);
        form.setHours(Integer.valueOf(1));
        form.setStartDate(LocalDate.now());
        form.setEndDate(LocalDate.now().plusDays(1));
        form.setPracticePlace("place");
        form.setTeacher(teacherRepository.findAll(SINGLE_RESULT).getContent().get(0).getId());
        form.setPracticePlan("plan");
        return form;
    }

}
