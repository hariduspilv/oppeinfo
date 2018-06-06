package ee.hitsa.ois.web;

import static org.junit.Assert.*;

import org.junit.After;
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
import ee.hitsa.ois.enums.Role;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DocumentControllerTests {

    private static final String ENDPOINT = "/documents";
    
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
    public void diplomaDirectives() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("diploma", "directives")
                .toUriString(), Object.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void supplementDirectives() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("supplement", "directives")
                .toUriString(), Object.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void supplementStudents() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("supplement", "students")
                .toUriString(), Object.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void signers() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(
                UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment("signers")
                .toUriString(), Object.class);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
