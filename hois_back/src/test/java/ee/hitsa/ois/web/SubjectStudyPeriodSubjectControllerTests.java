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
public class SubjectStudyPeriodSubjectControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void search() {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("/subjectStudyPeriods/subjects");
        uri.queryParam("subject", Long.valueOf(1));
        uri.queryParam("teacher", Long.valueOf(1));
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
    }

    @Test
    public void getSubjectsList() {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("/subjectStudyPeriods/subjects/list");
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(uri.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);

        uri = UriComponentsBuilder.fromUriString("/subjectStudyPeriods/subjects/list/limited/1");
        responseEntity = restTemplate.getForEntity(uri.toUriString(), Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity);
    }
}
