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
import ee.hitsa.ois.web.commandobject.EnterpriseForm;
import ee.hitsa.ois.web.dto.EnterpriseDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EnterpriseControllerTests {

    private static final String ENDPOINT = "/enterprises";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;

    private EnterpriseDto enterprise;

    @Before
    public void setUp() {
        Role role = Role.ROLL_A;
        testConfigurationService.userToRole(role, restTemplate);
    }

    @After
    public void cleanUp() {
        testConfigurationService.setSessionCookie(null);
        delete();
    }

    @Test
    public void crud() {
        // create
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        EnterpriseForm form = new EnterpriseForm();
        form.setName("test");

        ResponseEntity<EnterpriseDto> responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form,
                EnterpriseDto.class);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        form.setRegCode("12345");
        form.setContactPersonName("test");
        form.setContactPersonPhone("1234567");
        form.setContactPersonName("test@test.ee");

        responseEntity = restTemplate.postForEntity(uriBuilder.toUriString(), form, EnterpriseDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        enterprise = responseEntity.getBody();

        // read
        uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT).pathSegment(enterprise.getId().toString());
        String uri = uriBuilder.toUriString();
        ResponseEntity<EnterpriseDto> response = restTemplate.getForEntity(uri, EnterpriseDto.class);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        // TODO update

        // delete
        delete();
    }

    private void delete() {
        if (enterprise != null && enterprise.getId() != null) {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT)
                    .pathSegment(enterprise.getId().toString());
            uriBuilder.queryParam("version", enterprise.getVersion().toString());
            restTemplate.delete(uriBuilder.toUriString());
        }

    }

}
