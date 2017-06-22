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
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.dto.ContractSearchDto;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContractControllerTests {

    private static final String ENDPOINT = "/contracts";

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
        ResponseEntity<ContractSearchDto> responseEntity = restTemplate.getForEntity(ENDPOINT, ContractSearchDto.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("startFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("startThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("endFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("endThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("studentName", "studentName");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1L));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1L));
        uriBuilder.queryParam("enterpriseName", "enterpriseName");
        uriBuilder.queryParam("enterpriseContactPersonName", "enterpriseContactPersonName");
        uriBuilder.queryParam("teacher", Long.valueOf(1L));
        uriBuilder.queryParam("status", ContractStatus.LEPING_STAATUS_K.name());
        String url = uriBuilder.build().toUriString();

        responseEntity = restTemplate.getForEntity(url, ContractSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void crud() {
        //TODO
    }

}
