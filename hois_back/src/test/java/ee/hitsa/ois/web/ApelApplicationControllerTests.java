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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import ee.hitsa.ois.TestConfigurationService;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelApplicationForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationDto;
import ee.hitsa.ois.web.dto.apelapplication.ApelApplicationSearchDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApelApplicationControllerTests {

    private static final String ENDPOINT = "/apelApplications";
    
    private static final Long STUDENT_ID = Long.valueOf(189);
    
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
        ResponseEntity<ApelApplicationSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT, ApelApplicationSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("status", ApelApplicationStatus.VOTA_STAATUS_E.name());
        uriBuilder.queryParam("insertedFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("insertedThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("confirmedFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("confirmedThru", "2017-01-01T00:00:00.000Z");
        
        responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), ApelApplicationSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void crud() {
        //create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        ApelApplicationForm form = new ApelApplicationForm();
        
        AutocompleteResult studentAutocomplete = new AutocompleteResult(STUDENT_ID, "nameEt", "nameEn");
        form.setStudent(studentAutocomplete);
        
        ResponseEntity<ApelApplicationDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, ApelApplicationDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        Long applicationId = responseEntity.getBody().getId();
        
        //read
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(applicationId.toString());
        ResponseEntity<ApelApplicationDto> response = restTemplate.getForEntity(uriBuilder.toUriString(), ApelApplicationDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        
        //update
        form.setVersion(responseEntity.getBody().getVersion());
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(applicationId.toString());
        responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.PUT, new HttpEntity<>(form), ApelApplicationDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //delete
        Long version = responseEntity.getBody().getVersion();
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(applicationId.toString());
        uriBuilder.queryParam("version", version);
        restTemplate.delete(uriBuilder.toUriString());
    }
}
