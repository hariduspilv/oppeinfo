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
public class StudentControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void search() {
        String url = "/students";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/students");
        uriBuilder.queryParam("idcode", "3211212");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());

        uriBuilder = UriComponentsBuilder.fromUriString("/students");
        uriBuilder.queryParam("name", "NIMI");
        uriBuilder.queryParam("idcode", "48908209998");
        uriBuilder.queryParam("curriculumVersion", Long.valueOf(1));
        uriBuilder.queryParam("studyForm", "OPPEVORM_P", "OPPEVORM_K");
        uriBuilder.queryParam("status", "OPPURSTATUS_AKAD", "OPPURSTATUS_OPIB");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
