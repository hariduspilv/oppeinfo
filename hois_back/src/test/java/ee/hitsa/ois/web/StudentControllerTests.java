package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.EhisStudentDataType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.ehis.EhisStudentForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentControllerTests {

    private static final String ENDPOINT = "/students";

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private TestRestTemplate restTemplate;

    private Student student;

    @Before
    public void setUp() {
        Role role = Role.ROLL_A;
        if(student == null) {
            List<School> userSchools = testConfigurationService.personSchools(role);
            Assert.assertFalse(userSchools.isEmpty());

            student = studentRepository.findAll((root, query, cb) -> root.get("school").in(userSchools)).get(0);
        }

        testConfigurationService.userToRoleInSchool(role, EntityUtil.getId(student.getSchool()), restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void search() {
        String url = ENDPOINT;
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("idcode", "3211212");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("name", "NIMI");
        uriBuilder.queryParam("idcode", "48908209998");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1));
        uriBuilder.queryParam("studyForm", "OPPEVORM_P", "OPPEVORM_K");
        uriBuilder.queryParam("status", "OPPURSTATUS_AKAD", "OPPURSTATUS_OPIB");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void get() {
        Assert.assertNotNull(student);

        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(id.toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void documents() {
        Assert.assertNotNull(student);

        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(id.toString(), "documents");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void absences() {
        Assert.assertNotNull(student);

        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(id.toString(), "absences");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void subjects() {
        Assert.assertNotNull(student);

        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(id.toString(), "subjects");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void vocationalResults() {
        Assert.assertNotNull(student);

        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(id.toString(), "vocationalResults");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void higherResults() {
        Assert.assertNotNull(student);

        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(id.toString(), "higherResults");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void ehisStudentExport() {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("ehisStudentExport");

        EhisStudentForm form = new EhisStudentForm();
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form.setDataType(EhisStudentDataType.CURRICULA_FULFILMENT);
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        LocalDate now = LocalDate.now();
        form.setDataType(EhisStudentDataType.FOREIGN_STUDY);
        form.setFrom(now);
        form.setThru(now);
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        form.setDataType(EhisStudentDataType.GRADUATION);
        form.setFrom(now);
        form.setThru(now);
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        form.setDataType(EhisStudentDataType.VOTA);
        form.setFrom(now.plusDays(1));
        form.setThru(now.plusDays(1));
        responseEntity = restTemplate.postForEntity(uri.build().toUriString(), form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
