package ee.hitsa.ois.web;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
public class ExamControllerTests {

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
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/exams");
        uriBuilder.queryParam("studyPeriod", "1");
        uriBuilder.queryParam("from", "2017-02-16T00:00:00.00Z");
        uriBuilder.queryParam("thru", "2017-02-16T00:00:00.00Z");
        uriBuilder.queryParam("subject", "1");
        uriBuilder.queryParam("teacher", "1");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchAsTeacher() {
        testConfigurationService.userToRole(Role.ROLL_O, restTemplate);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/exams");
        uriBuilder.queryParam("studyPeriod", "1");
        uriBuilder.queryParam("from", "2017-02-16T00:00:00.00Z");
        uriBuilder.queryParam("thru", "2017-02-16T00:00:00.00Z");
        uriBuilder.queryParam("subject", "1");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.build().toUriString(), Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void examsForRegistration() {
        testConfigurationService.userToRole(Role.ROLL_T, restTemplate);
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/exams/forregistration?studyPeriod=1", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Ignore("Requires exam record")
    @Test
    public void studentsForRegistration() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/exams/studentsforregistration/1?subjectStudyPeriod=1", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void subjectStudyPeriods() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/exams/subjectstudyperiods?studyPeriod=1", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
