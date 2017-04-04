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
public class SubjectStudyPeriodControllerTest {
    
    private static final String BASE_URL = "/subjectStudyPeriod";
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void searchReceived() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(BASE_URL);
        uriBuilder.queryParam("subjectNameAndCode", "3211212");
        uriBuilder.queryParam("teachersFullname", "Name");
        uriBuilder.queryParam("studyPeriods", "1,2,3");
        uriBuilder.queryParam("sort", "p.lastname,p.firstname,asc");
        String url = uriBuilder.build().toUriString();
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
