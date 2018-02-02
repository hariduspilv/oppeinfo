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
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FinalExamHigherProtocolControllerTests {
    
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestConfigurationService testConfigurationService;
    
    private static final String ENDPOINT = "/finalExamHigherProtocols";
    
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
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("status", ProtocolStatus.PROTOKOLL_STAATUS_S.name());
        uriBuilder.queryParam("teacher", Long.valueOf(1L));
        uriBuilder.queryParam("studyPeriod", Long.valueOf(1L));
        uriBuilder.queryParam("protocolNr", "180001");
        uriBuilder.queryParam("subject", "name");
        uriBuilder.queryParam("insertedFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("insertedThru", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("confirmDateFrom", "2017-01-01T00:00:00.000Z");
        uriBuilder.queryParam("confirmDateThru", "2017-01-01T00:00:00.000Z");

        ResponseEntity<HigherProtocolSearchDto> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), HigherProtocolSearchDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    

}
