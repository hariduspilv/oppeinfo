package ee.hitsa.ois.web;

import java.time.LocalDate;

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
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.SchoolDepartmentService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutocompleteControllerTests {

    @Autowired
    private HoisUserDetailsService hoisUserDetailsService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SchoolDepartmentService schoolDepartmentService;

    @Autowired
    private SchoolRepository schoolRepository;

    private SchoolDepartment schoolDepartment;

    @Before
    public void setUp() {
        HoisUserDetails user = hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID);
        SchoolDepartmentForm schoolDepartmentForm = new SchoolDepartmentForm();
        schoolDepartmentForm.setNameEt("Struktuuriüksus");
        schoolDepartmentForm.setValidFrom(LocalDate.now());
        schoolDepartment = schoolDepartmentService.create(user, schoolDepartmentForm);
    }

    @After
    public void cleanUp() {
        schoolDepartmentService.delete(schoolDepartment);
    }

    @Test
    public void buildings() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/autocomplete/buildings", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void classifiers() {
        String uri = "/autocomplete/classifiers";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("mainClassCode", "OPPURSTAATUS");
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void curriculums() {
        String uri = "/autocomplete/curriculums";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void curriculumVersions() {
        String uri = "/autocomplete/curriculumversions";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void directiveCoordinators() {
        String uri = "/autocomplete/directivecoordinators";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void person() {
        // idcode not given
        String uri = "/autocomplete/persons";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        // bad idcode
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("idcode", "12NIMI");
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("idcode", "48908209998");
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertTrue(HttpStatus.NOT_FOUND.equals(responseEntity.getStatusCode()) || HttpStatus.OK.equals(responseEntity.getStatusCode()));
    }

    @Test
    public void personAsStudent() {
        String uri = "/autocomplete/persons";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("idcode", "48908209998");
        uriBuilder.queryParam("role", "student");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertTrue(HttpStatus.NOT_FOUND.equals(responseEntity.getStatusCode()) || HttpStatus.OK.equals(responseEntity.getStatusCode()));
    }

    @Test
    public void schools() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/autocomplete/schools", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void schoolDepartments() {
        String uri = "/autocomplete/schooldepartments?lang=EN&excludedId=1";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void subjects() {
        String uri = "/autocomplete/subjects";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void students() {
        String uri = "/autocomplete/students";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void teachers() {
        String uri = "/autocomplete/teachers";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
