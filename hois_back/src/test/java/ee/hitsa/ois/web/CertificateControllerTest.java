package ee.hitsa.ois.web;

import java.util.Arrays;

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
public class CertificateControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void search() {
        String url = "/certificate";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/certificate");
        uriBuilder.queryParam("student.person.name", "test1");
        uriBuilder.queryParam("certificateNr", "123");
        uriBuilder.queryParam("idcode", "39008114578");
        uriBuilder.queryParam("name", "Alice");
        uriBuilder.queryParam("headline", "3211212");
        uriBuilder.queryParam("type", Arrays.asList("TOEND_LIIK_OPI", "TOEND_LIIK_LOPET"));
        uriBuilder.queryParam("insertedFrom", "2016-12-31T22:00:00.000Z");
        uriBuilder.queryParam("insertedThru", "2017-01-31T22:00:00.000Z");
        uriBuilder.queryParam("sort", "student.person.lastname,student.person.firstname,asc");
        url = uriBuilder.build().toUriString();
        responseEntity = restTemplate.getForEntity(url, Object.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
