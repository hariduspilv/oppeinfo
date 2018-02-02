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
public class SubjectControllerTests {

    @Autowired
    private TestConfigurationService testConfigurationService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_A, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    @Test
    public void get() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(String.format("/subject/%d", Long.valueOf(1)), Object.class);
        Assert.assertNotNull(responseEntity);
    }

    @Test
    public void search() {
        String uri = UriComponentsBuilder.fromUriString("/subject").build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        uri = UriComponentsBuilder.fromUriString("/subject").queryParam("code", "Kood").queryParam("name", "Nimetus").build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void initEditFormData() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/subject/initEditFormData", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void initSearchFormData() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/subject/initSearchFormData", Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
