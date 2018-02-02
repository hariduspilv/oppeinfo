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
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.commandobject.apelapplication.ApelSchoolForm;
import ee.hitsa.ois.web.dto.apelapplication.ApelSchoolDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApelSchoolControllerTests {

    private static final String ENDPOINT = "/apelSchool";
    
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
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        String uri = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // school name
        uriBuilder.queryParam("name", "Nimetus");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // country
        uriBuilder.queryParam("country", "RIIK_AFG", "RIIK_ALB", "RIIK_ALA");
        uri = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(uri, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void crud() {
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        String uri = uriBuilder.build().toUriString();
        ApelSchoolForm form = new ApelSchoolForm();
        form.setCountry("RIIK_ALB");
        form.setNameEt("Albaania Ülikool");
        form.setNameEn("Albanian University");
        ResponseEntity<ApelSchoolDto> responseEntity = restTemplate.postForEntity(uri, form, ApelSchoolDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Long id = responseEntity.getBody().getId();
        Assert.assertNotNull(id);

        // read
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(id.toString());
        uri = uriBuilder.build().toUriString();
        ResponseEntity<ApelSchoolDto> response = restTemplate.getForEntity(uri, ApelSchoolDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // update
        form = response.getBody();
        Assert.assertNotNull(form);
        form.setNameEt("Albanian Tehnikaülikool");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), ApelSchoolDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // read
        responseEntity = restTemplate.getForEntity(uri, ApelSchoolDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Long version = responseEntity.getBody().getVersion();
        Assert.assertNotNull(version);

        // try to update with wrong version
        form.setNameEt("Albanian University of Technology");
        responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(form), ApelSchoolDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        // delete
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(id.toString());
        uriBuilder.queryParam("version", version);
        uri = uriBuilder.build().toUriString();
        restTemplate.delete(uri);
    }

}
