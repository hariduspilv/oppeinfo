package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.util.EntityUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private TestRestTemplate restTemplate;

    private Student student;

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

            student = studentRepository.findAll((root, query, cb) -> root.get("school").in(userSchools)).stream().findFirst().get();
        }

        testConfigurationService.userToRoleInSchool(role, EntityUtil.getId(student.getSchool()), restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void search() {
        String url = "/students";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/students");
        uriBuilder.queryParam("idcode", "3211212");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString("/students");
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
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/students").pathSegment(id.toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void documents() {
        Assert.assertNotNull(student);

        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/students").pathSegment(id.toString(), "documents");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void absences() {
        Assert.assertNotNull(student);

        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/students").pathSegment(id.toString(), "absences");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void subjects() {
        Assert.assertNotNull(student);

        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/students").pathSegment(id.toString(), "subjects");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
