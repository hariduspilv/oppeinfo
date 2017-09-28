package ee.hitsa.ois.web;

import javax.transaction.Transactional;

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

import ee.hitsa.ois.web.commandobject.StudyYearScheduleDtoContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class StudyYearScheduleControllerTests {

    private static final String BASE_URL = "/studyYearSchedule";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getStudyYearSchedules() {
        String uri = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("studyPeriods", "1", "2", "3").build().toUriString();
        ResponseEntity<StudyYearScheduleDtoContainer> responseEntity = 
                restTemplate.getForEntity(uri, StudyYearScheduleDtoContainer.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
    }
}
