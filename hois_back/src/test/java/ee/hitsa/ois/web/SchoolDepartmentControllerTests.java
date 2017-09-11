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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentForm;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SchoolDepartmentControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void search() {
        // no parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/school/departments");
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // code
        uriBuilder.queryParam("code", "CODE");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // name
        uriBuilder.queryParam("name", "Nimetus");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // name in english
        uriBuilder.queryParam("lang", "EN");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // valid
        uriBuilder.queryParam("valid", "true");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void get() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/school/departments");
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/school/departments");
        String uri = uriBuilder.build().toUriString();
        SchoolDepartmentForm schoolDepartmentForm = new SchoolDepartmentForm();
        schoolDepartmentForm.setNameEt("Struktuuriüksus eesti keeles");
        schoolDepartmentForm.setValidFrom(LocalDate.now());
        ResponseEntity<SchoolDepartment> responseEntity = restTemplate.postForEntity(uri, schoolDepartmentForm, SchoolDepartment.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
        schoolDepartmentForm.setCode("Kood");
        responseEntity = restTemplate.postForEntity(uri, schoolDepartmentForm, SchoolDepartment.class);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Long schoolDepartmentId = responseEntity.getBody().getId();
        Assert.assertNotNull(schoolDepartmentId);

        // read
        uriBuilder = UriComponentsBuilder.fromUriString("/school").pathSegment("departments", schoolDepartmentId.toString());
        uri = uriBuilder.build().toUriString();
        ResponseEntity<SchoolDepartmentForm> response = restTemplate.getForEntity(uri, SchoolDepartmentForm.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // update
        schoolDepartmentForm = response.getBody();
        Assert.assertNotNull(schoolDepartmentForm);
        schoolDepartmentForm.setNameEn("Department in english");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(schoolDepartmentForm), SchoolDepartment.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // read
        responseEntity = restTemplate.getForEntity(uri, SchoolDepartment.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Long version = responseEntity.getBody().getVersion();
        Assert.assertNotNull(version);

        // try to update with wrong version
        schoolDepartmentForm.setParentSchoolDepartment(schoolDepartmentId);
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(schoolDepartmentForm), SchoolDepartment.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        // try to update with bad parent (self)
        schoolDepartmentForm.setVersion(version);
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(schoolDepartmentForm), SchoolDepartment.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // search existing departments
        UriComponentsBuilder searchUriBuilder = UriComponentsBuilder.fromUriString("/school/departments");
        ResponseEntity<Object> searchResponseEntity = restTemplate.getForEntity(searchUriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(searchResponseEntity);
        Assert.assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString("/school").pathSegment("departments", schoolDepartmentId.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }
}
