package ee.hitsa.ois.web;

import org.junit.Assert;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MidtermTaskControllerTest {
    
    private static String ENDPOINT = "/midtermTasks";

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void searchSubjectStudyPeriods() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/subjectStudyPeriods");
        uriBuilder.queryParam("studyPeriod", Long.valueOf(1L));
        uriBuilder.queryParam("subject", Long.valueOf(1L));
        uriBuilder.queryParam("teacher", Long.valueOf(1L));
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
