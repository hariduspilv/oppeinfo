package ee.hitsa.ois.web;

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

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.enums.Role;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LessonPlanControllerTests {

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
    public void searchformData() {
        String url = "/lessonplans/searchFormData";
        ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void search() {
        String url = "/lessonplans";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("studyYear", Long.valueOf(1));
        uriBuilder.queryParam("schoolDepartment", Long.valueOf(1));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1));
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1));

        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchByTeacher() {
        String url = "/lessonplans/byteacher";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("studyYear", Long.valueOf(1));
        uriBuilder.queryParam("teacher", Long.valueOf(1));

        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
