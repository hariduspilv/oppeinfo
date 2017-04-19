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
public class LessonPlanControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void search() {
        String url = "/lessonplans";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("studyYear", Long.valueOf(1));
        uriBuilder.queryParam("schoolDepartment", Long.valueOf(1));
        uriBuilder.queryParam("studentGroup", Long.valueOf(1));

        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void searchByTeacher() {
        String url = "/lessonplans/byteacher";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        uriBuilder.queryParam("studyYear", Long.valueOf(1));
        uriBuilder.queryParam("teacher", Long.valueOf(1));

        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
