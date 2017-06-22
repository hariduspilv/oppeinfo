package ee.hitsa.ois.web;

import javax.transaction.Transactional;

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
import ee.hitsa.ois.web.dto.DeclarationDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DeclarationControllerTestStudent {

    private static final String ENDPOINT = "/declarations";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    
    @Before
    public void setUp() {
        testConfigurationService.userToRole(Role.ROLL_T, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
    }

    private void basicTest(String url) {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void checkIfStudentHasPreviousDeclarations() {
        basicTest(ENDPOINT + "/hasPrevious");
    }

    @Test
    public void searchStudentsPreviousDeclarations() {
        basicTest(ENDPOINT + "/previous");
    }

    public void getStudentsCurrentDeclaration() {
        basicTest(ENDPOINT + "/current");
    }

    @Test
    public void crud() {
        // create declaration
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/create");
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<DeclarationDto> responseEntity = restTemplate.postForEntity(uri, null, DeclarationDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Long id = responseEntity.getBody().getId();
        Assert.assertNotNull(id);

        getStudentsCurrentDeclaration();

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(id.toString());
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }
}
