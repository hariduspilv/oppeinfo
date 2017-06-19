package ee.hitsa.ois.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

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

import ee.hitsa.ois.TestConfiguration;
import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeApplicationDeclineForm;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeApplicationForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentRepresentativeControllerTests {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestConfigurationService testConfigurationService;

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

    @Test
    public void search() {
        String url = "/studentrepresentatives/applications";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/studentrepresentatives/applications");
        uriBuilder.queryParam("idcode", "3211212");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString("/studentrepresentatives/applications");
        uriBuilder.queryParam("idcode", "48908209998");
        uriBuilder.queryParam("name", "NIMI");
        uriBuilder.queryParam("status", "AVALDUS_ESINDAJA_STAATUS_E", "AVALDUS_ESINDAJA_STAATUS_T");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getRepresentatives() {
        Long id = student.getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/studentrepresentatives").pathSegment(id.toString());
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void createApplication() {
        String url = "/studentrepresentatives/applications";
        StudentRepresentativeApplicationForm form = new StudentRepresentativeApplicationForm();
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form.setPhone("555-321456");
        form.setEmail("a@b.c");
        form.setStudentIdcode("12345");
        responseEntity = restTemplate.postForEntity(url, form, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
    }

    @Test
    public void declineApplication() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/studentrepresentatives/applications/0/decline");
        StudentRepresentativeApplicationDeclineForm form = new StudentRepresentativeApplicationDeclineForm();
        ResponseEntity<Object> responseEntity = restTemplate.exchange(uriBuilder.build().toString(), HttpMethod.PUT, new HttpEntity<>(form), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void acceptApplication() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/studentrepresentatives/applications/0/accept");
        StudentRepresentativeApplicationDeclineForm form = new StudentRepresentativeApplicationDeclineForm();
        ResponseEntity<Object> responseEntity = restTemplate.exchange(uriBuilder.build().toString(), HttpMethod.PUT, new HttpEntity<>(form), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
