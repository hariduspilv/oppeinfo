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

    private HoisUserDetails user;
    private SchoolDepartment schoolDepartment;

    @Before
    public void setUp() {
        if(user == null) {
            user = hoisUserDetailsService.loadUserByUsername(TestConfiguration.USER_ID);
        }
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
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("mainClassCode", "OPPURSTAATUS");
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("mainClassCodes", "OPPURSTAATUS");
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void classifiersWithParents() {
        String uri = "/autocomplete/classifiers/withparents";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("mainClassCode", "OPPURSTAATUS");
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("mainClassCodes", "OPPURSTAATUS");
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

        uri = "/autocomplete/curriculumversions?valid=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = "/autocomplete/curriculumversions?sais=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = "/autocomplete/curriculumversions?higher=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void directiveCoordinators() {
        String uri = "/autocomplete/directivecoordinators";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = "/autocomplete/directivecoordinators?isDirective=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = "/autocomplete/directivecoordinators?isCertificate=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void journals() {
        String uri = "/autocomplete/journals";
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

        uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("idcode", TestConfiguration.USER_ID);
        uriBuilder.queryParam("role", "student");
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertTrue(HttpStatus.NOT_FOUND.equals(responseEntity.getStatusCode()) || HttpStatus.OK.equals(responseEntity.getStatusCode()));
    }

    @Test
    public void personForTeacher() {
        String uri = "/autocomplete/persons";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("idcode", "48908209998");
        uriBuilder.queryParam("role", "forteacher");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertTrue(HttpStatus.NOT_FOUND.equals(responseEntity.getStatusCode()) || HttpStatus.OK.equals(responseEntity.getStatusCode()));

        uriBuilder = UriComponentsBuilder.fromUriString(uri);
        uriBuilder.queryParam("idcode", TestConfiguration.USER_ID);
        uriBuilder.queryParam("role", "forteacher");
        responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertTrue(HttpStatus.NOT_FOUND.equals(responseEntity.getStatusCode()) || HttpStatus.OK.equals(responseEntity.getStatusCode()));
    }

    @Test
    public void rooms() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/autocomplete/rooms");
        uriBuilder.queryParam("buildingIds", "6&buildingIds=26");
        uriBuilder.queryParam("name", "aq");
        ResponseEntity<Object> response = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void saisAdmissionCodes() {
        ResponseEntity<Object> response = restTemplate.getForEntity("/autocomplete/saisAdmissionCodes", Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void saisClassifiers() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/autocomplete/saisClassifiers");
        uriBuilder.queryParam("parentCode", "3e5a369f-6d6f-467c-a407-45d6d0df2dfb");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
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
    public void studentgroups() {
        String uri = "/autocomplete/studentgroups?valid=true";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = "/autocomplete/studentgroups?higher=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
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

        uri = "/autocomplete/students?active=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = "/autocomplete/students?studyinge=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = "/autocomplete/students?academicLeave=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = "/autocomplete/students?nominalStudy=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = "/autocomplete/students?higher=true";
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studyPeriods() {
        String uri = "/autocomplete/studyPeriods";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void studyYears() {
        String uri = "/autocomplete/studyYears";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void teachers() {
        String uri = "/autocomplete/teachers?valid=true";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void vocationalModules() {
        ResponseEntity<Object> response = restTemplate.getForEntity("/autocomplete/vocationalmodules", Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void journalsAndSubjects() {
        String uri = "/autocomplete/journalsAndSubjects";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
